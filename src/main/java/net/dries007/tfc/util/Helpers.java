package net.dries007.tfc.util;

import su.terrafirmagreg.data.Constants;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.registries.IForgeRegistryEntry;


import com.google.common.base.Joiner;
import io.netty.buffer.ByteBuf;
import net.dries007.tfc.objects.entity.animal.EntityChickenTFC;
import net.dries007.tfc.objects.entity.animal.EntityCowTFC;
import net.dries007.tfc.objects.entity.animal.EntityDonkeyTFC;
import net.dries007.tfc.objects.entity.animal.EntityHorseTFC;
import net.dries007.tfc.objects.entity.animal.EntityLlamaTFC;
import net.dries007.tfc.objects.entity.animal.EntityMuleTFC;
import net.dries007.tfc.objects.entity.animal.EntityOcelotTFC;
import net.dries007.tfc.objects.entity.animal.EntityParrotTFC;
import net.dries007.tfc.objects.entity.animal.EntityPigTFC;
import net.dries007.tfc.objects.entity.animal.EntityPolarBearTFC;
import net.dries007.tfc.objects.entity.animal.EntityRabbitTFC;
import net.dries007.tfc.objects.entity.animal.EntitySheepTFC;
import net.dries007.tfc.objects.entity.animal.EntityWolfTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class Helpers {

    private static final Joiner JOINER_DOT = Joiner.on('.');
    /**
     * Vanilla entities that are replaced to TFC counterparts on spawn
     */
    private static final Map<Class<? extends Entity>, Class<? extends Entity>> VANILLA_REPLACEMENTS;

    /**
     * Extra entities that are prevented to spawn on surface and aren't considered monsters (like Skeleton Horses)
     */
    private static final Set<Class<? extends Entity>> PREVENT_ON_SURFACE;

    static {
        PREVENT_ON_SURFACE = new HashSet<>();
        PREVENT_ON_SURFACE.add(EntityZombieHorse.class);
        PREVENT_ON_SURFACE.add(EntitySkeletonHorse.class);
        VANILLA_REPLACEMENTS = new HashMap<>();
        VANILLA_REPLACEMENTS.put(EntityCow.class, EntityCowTFC.class);
        VANILLA_REPLACEMENTS.put(EntitySheep.class, EntitySheepTFC.class);
        VANILLA_REPLACEMENTS.put(EntityPig.class, EntityPigTFC.class);
        VANILLA_REPLACEMENTS.put(EntityMule.class, EntityMuleTFC.class);
        VANILLA_REPLACEMENTS.put(EntityHorse.class, EntityHorseTFC.class);
        VANILLA_REPLACEMENTS.put(EntityDonkey.class, EntityDonkeyTFC.class);
        VANILLA_REPLACEMENTS.put(EntityChicken.class, EntityChickenTFC.class);
        VANILLA_REPLACEMENTS.put(EntityRabbit.class, EntityRabbitTFC.class);
        VANILLA_REPLACEMENTS.put(EntityWolf.class, EntityWolfTFC.class);
        VANILLA_REPLACEMENTS.put(EntityOcelot.class, EntityOcelotTFC.class);
        VANILLA_REPLACEMENTS.put(EntityPolarBear.class, EntityPolarBearTFC.class);
        VANILLA_REPLACEMENTS.put(EntityParrot.class, EntityParrotTFC.class);
        VANILLA_REPLACEMENTS.put(EntityLlama.class, EntityLlamaTFC.class);
    }

    /**
     * Return true if the entity is from vanilla and have a TFC counterpart
     *
     * @param entity the entity to check
     * @return true if it has a TFC counterpart, false otherwise
     */
    public static boolean isVanillaAnimal(Entity entity) {
        return VANILLA_REPLACEMENTS.get(entity.getClass()) != null;
    }

    @Nullable
    public static Entity getTFCReplacement(Entity entity) {
        Class<? extends Entity> animalClass = VANILLA_REPLACEMENTS.get(entity.getClass());
        if (animalClass != null) {
            try {
                return animalClass.getConstructor(World.class).newInstance(entity.world);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    public static boolean shouldPreventOnSurface(Entity entity) {
        return PREVENT_ON_SURFACE.contains(entity.getClass()) || entity.isCreatureType(EnumCreatureType.MONSTER, false);
    }

    public static boolean containsAnyOfCaseInsensitive(Collection<String> input, String... items) {
        Set<String> itemsSet = Arrays.stream(items).map(String::toLowerCase).collect(Collectors.toSet());
        return input.stream().map(String::toLowerCase).anyMatch(itemsSet::contains);
    }

    public static String getEnumName(Enum<?> anEnum) {
        return JOINER_DOT.join(Constants.MODID_TFC, "enum", anEnum.getDeclaringClass().getSimpleName(), anEnum).toLowerCase();
    }

    public static String getTypeName(IForgeRegistryEntry<?> type) {
        //noinspection ConstantConditions
        return JOINER_DOT.join(Constants.MODID_TFC, "types", type.getRegistryType()
                        .getSimpleName(), type.getRegistryName().getPath())
                .toLowerCase();
    }

    /**
     *
     */
    public static void handleRightClickBlockPostEventWithCallbacks(PlayerInteractEvent.RightClickBlock event,
                                                                   @Nullable Supplier<EnumActionResult> onItemUseCallback) {
        event.setCanceled(true);
        EnumActionResult result = EnumActionResult.PASS;
        // todo: verify stack is correct
        ItemStack stack = event.getEntityPlayer().getHeldItem(event.getHand());
        // todo: find hit pos from ray trace
        int hitX = 0, hitY = 0, hitZ = 0;
        EnumFacing face = event.getFace() == null ? EnumFacing.UP : event.getFace();
        if (event.getUseItem() != Event.Result.DENY) {
            result = stack.onItemUseFirst(event.getEntityPlayer(), event.getWorld(), event.getPos(), event.getHand(), face, hitX, hitY, hitZ);
            if (result != EnumActionResult.PASS) {
                event.setCancellationResult(result);
                return;
            }
        }

        boolean bypass = event.getEntityPlayer()
                .getHeldItemMainhand()
                .doesSneakBypassUse(event.getWorld(), event.getPos(), event.getEntityPlayer()) && event.getEntityPlayer()
                .getHeldItemOffhand()
                .doesSneakBypassUse(event.getWorld(), event.getPos(), event.getEntityPlayer());

        if (!event.getEntityPlayer().isSneaking() || bypass || event.getUseBlock() == Event.Result.ALLOW) {
            IBlockState iblockstate = event.getWorld().getBlockState(event.getPos());
            if (event.getUseBlock() != Event.Result.DENY)
                if (iblockstate.getBlock()
                        .onBlockActivated(event.getWorld(), event.getPos(), iblockstate, event.getEntityPlayer(), event.getHand(), face, hitX, hitY,
                                hitZ)) {
                    result = EnumActionResult.SUCCESS;
                }
        }

        if (stack.isEmpty()) {
            event.setCancellationResult(EnumActionResult.PASS);
        } else if (event.getEntityPlayer().getCooldownTracker().hasCooldown(stack.getItem())) {
            event.setCancellationResult(EnumActionResult.PASS);
        } else {
            if (stack.getItem() instanceof ItemBlock && !event.getEntityPlayer().canUseCommandBlock()) {
                Block block = ((ItemBlock) stack.getItem()).getBlock();

                if (block instanceof BlockCommandBlock || block instanceof BlockStructure) {
                    event.setCancellationResult(EnumActionResult.FAIL);
                    return;
                }
            }

            if (event.getEntityPlayer().isCreative()) {
                int j = stack.getMetadata();
                int i = stack.getCount();
                if (result != EnumActionResult.SUCCESS && event.getUseItem() != Event.Result.DENY
                        || result == EnumActionResult.SUCCESS && event.getUseItem() == Event.Result.ALLOW) {
                    EnumActionResult enumactionresult;
                    if (onItemUseCallback != null) {
                        enumactionresult = onItemUseCallback.get();
                    } else {
                        enumactionresult = stack.onItemUse(event.getEntityPlayer(), event.getWorld(), event.getPos(), event.getHand(), face, hitX,
                                hitY, hitZ);
                    }
                    stack.setItemDamage(j);
                    stack.setCount(i);
                    event.setCancellationResult(enumactionresult);
                } else {
                    event.setCancellationResult(result);
                }
            } else {
                if (result != EnumActionResult.SUCCESS && event.getUseItem() != Event.Result.DENY
                        || result == EnumActionResult.SUCCESS && event.getUseItem() == Event.Result.ALLOW) {
                    ItemStack copyBeforeUse = stack.copy();
                    result = stack.onItemUse(event.getEntityPlayer(), event.getWorld(), event.getPos(), event.getHand(), event.getFace(), hitX, hitY,
                            hitZ);
                    if (stack.isEmpty())
                        net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(event.getEntityPlayer(), copyBeforeUse, event.getHand());
                }
                event.setCancellationResult(result);
            }
        }
    }

    public static void writeResourceLocation(ByteBuf buf, @Nullable ResourceLocation loc) {
        buf.writeBoolean(loc != null);
        if (loc != null) {
            ByteBufUtils.writeUTF8String(buf, loc.toString());
        }
    }

    @Nullable
    public static ResourceLocation readResourceLocation(ByteBuf buf) {
        if (buf.readBoolean()) {
            return new ResourceLocation(ByteBufUtils.readUTF8String(buf));
        }
        return null;
    }

    /**
     * This is meant to avoid Intellij's warnings about null fields that are injected to at runtime Use this for things like @ObjectHolder,
     *
     * @param <T> anything and everything
     * @return null, but not null
     * @CapabilityInject, etc. AKA - The @Nullable is intentional. If it crashes your dev env, then fix your dev env, not this. :)
     */
    @NotNull
    @SuppressWarnings("ConstantConditions")
    public static <T> T getNull() {
        return null;
    }

}
