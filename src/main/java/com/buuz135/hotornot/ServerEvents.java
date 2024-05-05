package com.buuz135.hotornot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


import com.buuz135.hotornot.config.HotConfig;
import com.buuz135.hotornot.config.HotLists;
import com.buuz135.hotornot.object.item.ItemHotHolder;
import com.buuz135.hotornot.util.ItemEffect;

import java.util.HashMap;
import java.util.Map;

import static su.terrafirmagreg.api.data.Constants.MODID_HOTORNOT;

@EventBusSubscriber(modid = MODID_HOTORNOT)
public class ServerEvents {

    private static final Map<EntityPlayer, Boolean> PLAYER_REPLACE_BROKEN_HOTHOLDER_MAP = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerLogin(final PlayerLoggedInEvent event) {
        // TODO need to sync more values
        HotOrNot.getNetwork().sendTo(HotLists.getServerConfigPacket(), (EntityPlayerMP) event.player);
        HotOrNot.getLog().info("Synced server lists with {}", event.player.getName());
    }

    @SubscribeEvent
    public static void onPlayerLogout(final PlayerLoggedOutEvent event) {
        PLAYER_REPLACE_BROKEN_HOTHOLDER_MAP.remove(event.player);
    }

    /**
     * Saves the player hot holder replacement config
     *
     * @param player                 The player
     * @param replaceBrokenHotHolder If this player wants their hot holders to be replaced or not
     */
    public static void setReplaceHotHolderConfigForPlayer(final EntityPlayer player, final boolean replaceBrokenHotHolder) {
        PLAYER_REPLACE_BROKEN_HOTHOLDER_MAP.put(player, replaceBrokenHotHolder);
    }

    /**
     * Checks if the given player wants their hot holders to be replaced on break
     *
     * @param player The player to check
     * @return If the hot holder should be replaced for this player
     */
    public static boolean shouldReplaceHotHolder(final EntityPlayer player) {
        return PLAYER_REPLACE_BROKEN_HOTHOLDER_MAP.get(player);
    }

    @SubscribeEvent
    public static void onTick(final WorldTickEvent event) {
        if (event.phase != TickEvent.Phase.START) return;

        if (event.side != Side.SERVER) return;

        for (final EntityPlayer player : event.world.playerEntities) {
            if (player.isCreative()) return;

            final IItemHandler playerItemHandler = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            // If players don't have the item handler capability somebody did some nasty mixin and messed things up for more than just us
            assert playerItemHandler != null;

            final ItemStack heldItemOffhand = player.getHeldItemOffhand();

            final boolean preventedEffect;
            {
                final boolean hasHotHolder = heldItemOffhand.getItem() instanceof ItemHotHolder;

                // Badly named, we want this so preventedEffect can be final once we leave this scope
                boolean preventedEffectInner = false;
                preventedEffect:
                for (int slotIndex = 0; slotIndex < playerItemHandler.getSlots(); slotIndex++) {
                    final ItemStack slotStack = playerItemHandler.getStackInSlot(slotIndex);

                    final boolean checkContents = slotStack.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
                            null) && HotConfig.EFFECT_HANDLING.checkItemContainerContents;

                    if (slotStack.isEmpty()) continue;

                    for (final ItemEffect effect : ItemEffect.values()) {
                        if (!effect.stackHasEffect(slotStack)) {
                            if (!checkContents) continue;

                            final IItemHandler internalHandler = slotStack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                            // Checked this in order to reach here
                            assert internalHandler != null;

                            if (!ItemEffect.contentsHaveEffect(internalHandler, effect)) continue;
                        }

                        // Skip handing more effects as we've found one to handle and have a hot holder so are immune
                        if (hasHotHolder) {
                            preventedEffectInner = true;
                            // Should break out both loops to skip extra work
                            break preventedEffect;
                        }

                        // Only do effects and tossing once every 20 game ticks (1 second = 20 ticks)
                        if (event.world.getTotalWorldTime() % 20 == 0) {
                            effect.doEffect(player);
                            if (effect.doToss && HotConfig.EFFECT_HANDLING.tossItems) {
                                final ItemStack extractedStack = playerItemHandler.extractItem(slotIndex, slotStack.getCount(), false);
                                player.dropItem(extractedStack, true);
                                // Must break out of internal loop, slotStack is now invalid
                                break;
                            }
                        }
                    }
                }
                preventedEffect = preventedEffectInner;
            }

            if (preventedEffect) {
                // Prevent divide by 0 & disable the tool damage functionality
                if (HotConfig.EFFECT_HANDLING.damageRate == 0) continue;

                // Respect damage rate config
                if (event.world.getTotalWorldTime() % HotConfig.EFFECT_HANDLING.damageRate != 0) continue;

                final float damageChance = ((ItemHotHolder) heldItemOffhand.getItem()).damageChance(heldItemOffhand);

                // Chance we damage the tool
                if (!(event.world.rand.nextFloat() < damageChance)) continue;

                heldItemOffhand.damageItem(1, player);

                // If it's empty the item broke so we should skip
                if (!heldItemOffhand.isEmpty()) continue;

                if (!shouldReplaceHotHolder(player)) continue;

                if (HotConfig.EFFECT_HANDLING.replaceBrokenHotHolder) {
                    final ItemStack extractedHotHolder = findAndExtractHotHolder(playerItemHandler);

                    if (extractedHotHolder.isEmpty()) continue;

                    player.setHeldItem(EnumHand.OFF_HAND, extractedHotHolder);
                    // Plays a pickup sound to help notify that the item was swapped
                    event.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP,
                            SoundCategory.PLAYERS, 0.8F, 0.8F + player.world.rand.nextFloat() * 0.4F);
                }
            }
        }
    }

    /**
     * Searches through the player inventory for a {@link ItemHotHolder} if one is found it sets the offhand to this stack
     *
     * @param playerItemHandler The players Item Handler
     * @return The extracted hot holder or {@link ItemStack#EMPTY} if one couldn't be found
     */
    private static ItemStack findAndExtractHotHolder(final IItemHandler playerItemHandler) {
        for (int slotIndex = 0; slotIndex < playerItemHandler.getSlots(); slotIndex++) {
            final ItemStack slotStack = playerItemHandler.getStackInSlot(slotIndex);

            // Not a Hot Holder item
            if (!(slotStack.getItem() instanceof ItemHotHolder)) continue;

            return playerItemHandler.extractItem(slotIndex, slotStack.getCount(), false);
        }

        return ItemStack.EMPTY;
    }
}
