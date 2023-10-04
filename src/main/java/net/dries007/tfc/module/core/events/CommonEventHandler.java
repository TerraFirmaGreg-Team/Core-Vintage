package net.dries007.tfc.module.core.events;

import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import gregtech.api.unification.material.event.MaterialEvent;
import net.dries007.tfc.Tags;
import net.dries007.tfc.api.capability.damage.DamageType;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.egg.EggHandler;
import net.dries007.tfc.api.capability.food.*;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.heat.IItemHeat;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.metal.IMaterialItem;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.api.capability.player.PlayerDataHandler;
import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.capability.worldtracker.CapabilityWorldTracker;
import net.dries007.tfc.api.capability.worldtracker.WorldTracker;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialHandler;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefixHandler;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.animal.api.type.IAnimal;
import net.dries007.tfc.module.animal.api.type.ICreature;
import net.dries007.tfc.module.animal.api.type.IPredator;
import net.dries007.tfc.module.core.ModuleCore;
import net.dries007.tfc.module.core.api.util.FallingBlockManager;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.init.BlocksCore;
import net.dries007.tfc.module.core.init.EffectsCore;
import net.dries007.tfc.module.core.init.ItemsCore;
import net.dries007.tfc.module.core.objects.blocks.fluid.BlockFluidBase;
import net.dries007.tfc.module.core.objects.container.CapabilityContainerListener;
import net.dries007.tfc.module.core.objects.items.ItemQuiver;
import net.dries007.tfc.module.devices.objects.blocks.BlockQuern;
import net.dries007.tfc.module.metal.objects.blocks.BlockMetalAnvil;
import net.dries007.tfc.module.rock.StorageRock;
import net.dries007.tfc.module.rock.api.types.variant.block.IRockBlock;
import net.dries007.tfc.module.rock.objects.blocks.BlockRockAnvil;
import net.dries007.tfc.module.rock.objects.blocks.BlockRockRaw;
import net.dries007.tfc.module.wood.api.types.variant.block.IWoodBlock;
import net.dries007.tfc.module.wood.objects.blocks.BlockWoodSupport;
import net.dries007.tfc.network.PacketCalendarUpdate;
import net.dries007.tfc.network.PacketPlayerDataUpdate;
import net.dries007.tfc.util.Constants;
import net.dries007.tfc.util.DamageSourcesTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.CalendarWorldData;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.util.skills.SmithingSkill;
import net.dries007.tfc.world.classic.WorldTypeTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.GameRuleChangeEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import su.terrafirmagreg.tfc.TerraFirmaCraft;

import static net.dries007.tfc.module.rock.api.types.type.RockTypes.BASALT;
import static net.dries007.tfc.module.rock.api.types.type.RockTypes.RHYOLITE;
import static net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariants.RAW;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class CommonEventHandler {
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void registerMaterials(MaterialEvent event) {
        TFGMaterialHandler.init();
        TFGOrePrefixHandler.init();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        IBlockState state = event.getState();
        FallingBlockManager.Specification spec = FallingBlockManager.getSpecification(state);
        if (spec != null && !spec.isCollapsable()) {
            if (FallingBlockManager.checkFalling(event.getWorld(), event.getPos(), state)) {
                event.getWorld().playSound(null, event.getPos(), spec.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        } else {
            for (EnumFacing notifiedSide : event.getNotifiedSides()) {
                BlockPos offsetPos = event.getPos().offset(notifiedSide);
                IBlockState notifiedState = event.getWorld().getBlockState(offsetPos);
                FallingBlockManager.Specification notifiedSpec = FallingBlockManager.getSpecification(notifiedState);
                if (notifiedSpec != null && !notifiedSpec.isCollapsable()) {
                    if (FallingBlockManager.checkFalling(event.getWorld(), offsetPos, notifiedState)) {
                        event.getWorld().playSound(null, offsetPos, notifiedSpec.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        if (event.getWorld().isRemote) {
            return;
        }
        IBlockState state = event.getPlacedBlock();
        FallingBlockManager.Specification spec = FallingBlockManager.getSpecification(state);
        if (spec != null && !spec.isCollapsable()) {
            if (FallingBlockManager.checkFalling(event.getWorld(), event.getPos(), state)) {
                event.getWorld().playSound(null, event.getPos(), spec.getSoundEvent(), SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        if (ConfigTFC.General.FALLABLE.explosionCausesCollapse) {
            for (BlockPos pos : event.getAffectedBlocks()) {
                if (FallingBlockManager.checkCollapsingArea(event.getWorld(), pos)) {
                    break;
                }
            }
        }
    }

    /**
     * Fill thirst after drinking vanilla water bottles or milk
     */
    @SubscribeEvent
    public static void onEntityUseItem(LivingEntityUseItemEvent.Finish event) {
        ItemStack usedItem = event.getItem();
        if (usedItem.getItem() == Items.MILK_BUCKET || PotionUtils.getPotionFromItem(usedItem) == PotionTypes.WATER) {
            if (event.getEntityLiving() instanceof EntityPlayerMP player) {
                if (player.getFoodStats() instanceof FoodStatsTFC) {
                    ((FoodStatsTFC) player.getFoodStats()).addThirst(40); //Same as jug
                }
            }
        }
    }

    /**
     * Update harvesting tool before it takes damage
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void breakEvent(BlockEvent.BreakEvent event) {
        final EntityPlayer player = event.getPlayer();
        final ItemStack heldItem = player == null ? ItemStack.EMPTY : player.getHeldItemMainhand();

        if (player != null) {
            IPlayerData cap = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
            if (cap != null) {
                cap.setHarvestingTool(player.getHeldItemMainhand());
            }
        }

        FallingBlockManager.Specification spec = FallingBlockManager.getSpecification(event.getState());
        if (spec != null && spec.isCollapsable()) {
            FallingBlockManager.checkCollapsingArea(event.getWorld(), event.getPos());
        }
    }

    @SubscribeEvent
    public static void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        final var player = event.getHarvester();
        final var heldItem = player == null ? ItemStack.EMPTY : player.getHeldItemMainhand();
        final var state = event.getState();
        final var block = state.getBlock();

        // Сделайте из листьев палочки
        if (!event.isSilkTouching() && block instanceof BlockLeaves) {
            // Выполнено через событие, поэтому оно применяется ко всем листьям.
            double chance = ConfigTFC.General.TREE.leafStickDropChance;
            if (!heldItem.isEmpty() && Helpers.containsAnyOfCaseInsensitive(heldItem.getItem().getToolClasses(heldItem), ConfigTFC.General.TREE.leafStickDropChanceBonusClasses)) {
                chance = ConfigTFC.General.TREE.leafStickDropChanceBonus;
            }
            if (Constants.RNG.nextFloat() < chance) {
                event.getDrops().add(new ItemStack(Items.STICK));
            }
        }

        // Выбрасывать осколки из стекла
        var stackAt = new ItemStack(Item.getItemFromBlock(state.getBlock()), 1, state.getBlock().damageDropped(state));
        if (!event.isSilkTouching() && OreDictionaryHelper.doesStackMatchOre(stackAt, "blockGlass")) {
            event.getDrops().add(new ItemStack(ItemsCore.GLASS_SHARD));
        }

        // Применить модификатор прочности к инструментам
        if (player != null) {
            var tool = ItemStack.EMPTY;
            var cap = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
            if (cap != null) {
                tool = cap.getHarvestingTool();
            }
            if (!tool.isEmpty()) {
                float skillModifier = SmithingSkill.getSkillBonus(tool, SmithingSkill.Type.TOOLS) / 2.0F;
                if (skillModifier > 0 && Constants.RNG.nextFloat() < skillModifier) {
                    // Снижение урона до 50 % для двойной прочности.
                    player.setHeldItem(EnumHand.MAIN_HAND, tool);
                }
            }
        }

        // не объединять частичные стопки веток на земле
        if (player != null && event.getState().getBlock() instanceof BlockBranch) {
            var held = player.getHeldItemMainhand();
            if (OreDictionaryHelper.doesStackMatchOre(held, "axeStone")) {
                for (ItemStack s : event.getDrops()) {
                    if (OreDictionaryHelper.doesStackMatchOre(s, "logWood")) {
                        s.setCount((int) (s.getCount() * ConfigTFC.General.TREE.stoneAxeReturnRate));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onBreakProgressEvent(BreakSpeed event) {
        EntityPlayer player = event.getEntityPlayer();
        if (player != null) {
            ItemStack stack = player.getHeldItemMainhand();
            float skillModifier = SmithingSkill.getSkillBonus(stack, SmithingSkill.Type.TOOLS);
            if (skillModifier > 0) {
                // Up to 2x modifier for break speed for skill bonuses on tools
                // New speed, so it will take into account other mods' modifications
                event.setNewSpeed(event.getNewSpeed() + (event.getNewSpeed() * skillModifier));
            }
        }
        if (event.getState().getBlock() instanceof IRockBlock) {
            event.setNewSpeed((float) (event.getNewSpeed() / ConfigTFC.General.MISC.rockMiningTimeModifier));
        }
        if (event.getState().getBlock() instanceof IWoodBlock) {
            event.setNewSpeed((float) (event.getNewSpeed() / ConfigTFC.General.MISC.logMiningTimeModifier));
        }
    }

    /**
     * Handles drinking water when right clicking an underwater block
     */
    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        final World world = event.getWorld();
        final BlockPos pos = event.getPos();
        final IBlockState state = world.getBlockState(pos);
        final ItemStack stack = event.getItemStack();
        final EntityPlayer player = event.getEntityPlayer();

        // Fire onBlockActivated for in world crafting devices
        if (state.getBlock() instanceof BlockMetalAnvil
                || state.getBlock() instanceof BlockRockAnvil
                || state.getBlock() instanceof BlockQuern
                || state.getBlock() instanceof BlockWoodSupport) {
            event.setUseBlock(Event.Result.ALLOW);
        }

        // Try to drink water
        // Only possible with main hand - fixes attempting to drink even when it doesn't make sense
        if (!player.isCreative() && stack.isEmpty() && player.getFoodStats() instanceof IFoodStatsTFC foodStats && event.getHand() == EnumHand.MAIN_HAND) {
            RayTraceResult result = Helpers.rayTrace(event.getWorld(), player, true);
            if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
                IBlockState waterState = world.getBlockState(result.getBlockPos());
                boolean isFreshWater = Helpers.isFreshWater(waterState), isSaltWater = Helpers.isSaltWater(waterState);
                if ((isFreshWater && foodStats.attemptDrink(10, true)) || (isSaltWater && foodStats.attemptDrink(-1, true))) {
                    //Simulated so client will check if he would drink before updating stats
                    if (!world.isRemote) {
                        player.world.playSound(null, player.getPosition(), SoundEvents.ENTITY_GENERIC_DRINK, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        if (isFreshWater) {
                            foodStats.attemptDrink(10, false);
                        } else {
                            foodStats.attemptDrink(-1, false);
                        }
                    } else {
                        foodStats.resetCooldown();
                    }
                    event.setCancellationResult(EnumActionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        float actualDamage = event.getAmount();
        // Add damage bonus for weapons
        Entity entity = event.getSource().getTrueSource();
        if (entity instanceof EntityLivingBase damager) {
            ItemStack stack = damager.getHeldItemMainhand();
            float skillModifier = SmithingSkill.getSkillBonus(stack, SmithingSkill.Type.WEAPONS);
            if (skillModifier > 0) {
                // Up to 1.5x damage
                actualDamage *= 1 + (skillModifier / 2.0F);
            }
        }
        // Modifier for damage type + damage resistance
        actualDamage *= DamageType.getModifier(event.getSource(), event.getEntityLiving());
        if (event.getEntityLiving() instanceof EntityPlayer player) {
            if (player.getFoodStats() instanceof IFoodStatsTFC) {
                float healthModifier = ((IFoodStatsTFC) player.getFoodStats()).getHealthModifier();
                if (healthModifier < ConfigTFC.General.PLAYER.minHealthModifier) {
                    healthModifier = (float) ConfigTFC.General.PLAYER.minHealthModifier;
                }
                if (healthModifier > ConfigTFC.General.PLAYER.maxHealthModifier) {
                    healthModifier = (float) ConfigTFC.General.PLAYER.maxHealthModifier;
                }
                actualDamage /= healthModifier;
            }
        }
        event.setAmount(actualDamage);
    }

    @SubscribeEvent
    public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        ItemStack stack = event.getObject();
        Item item = stack.getItem();
        if (!stack.isEmpty()) {
            // Size
            if (CapabilityItemSize.getIItemSize(stack) == null) {
                ICapabilityProvider sizeHandler = CapabilityItemSize.getCustomSize(stack);
                event.addCapability(CapabilityItemSize.KEY, sizeHandler);
                if (sizeHandler instanceof IItemSize) {
                    // Only modify the stack size if the item was stackable in the first place
                    // Note: this is called in many cases BEFORE all custom capabilities are added.
                    int prevStackSize = stack.getMaxStackSize();
                    if (prevStackSize != 1) {
                        item.setMaxStackSize(((IItemSize) sizeHandler).getStackSize(stack));
                    }
                }
            }

            // Food
            // Because our foods supply a custom capability in Item#initCapabilities, we need to avoid attaching a duplicate, otherwise it breaks food stacking recipes.
            // This problem goes away in 1.15 as all of these definitions (including ours) become tags)
            // We allow custom defined capabilities to attach to non-food items, that should have rot (such as eggs).
            ICapabilityProvider foodHandler = CapabilityFood.getCustomFood(stack);
            if (foodHandler != null || stack.getItem() instanceof ItemFood) {
                if (stack.getItem() instanceof IItemFoodTFC) {
                    foodHandler = ((IItemFoodTFC) stack.getItem()).getCustomFoodHandler();
                }
                if (foodHandler == null) {
                    foodHandler = new FoodHandler(stack.getTagCompound(), new FoodData());
                }
                event.addCapability(CapabilityFood.KEY, foodHandler);
            }

            // Forge / Metal / Heat. Try forge first, because it's more specific
            ICapabilityProvider forgeHandler = CapabilityForgeable.getCustomForgeable(stack);
            boolean isForgeable = false;
            boolean isHeatable = false;
            if (forgeHandler != null) {
                isForgeable = true;
                event.addCapability(CapabilityForgeable.KEY, forgeHandler);
                isHeatable = forgeHandler instanceof IItemHeat;
            }
            // Metal
            ICapabilityProvider metalCapability = CapabilityMetalItem.getCustomMetalItem(stack);
            if (metalCapability != null) {
                event.addCapability(CapabilityMetalItem.KEY, metalCapability);
                if (!isForgeable) {
                    // Add a forgeable capability for this item, if none is found
                    IMaterialItem cap = (IMaterialItem) metalCapability;
                    var metal = cap.getMaterial(stack);
                    if (metal != null) {
                        var property = metal.getProperty(TFGPropertyKey.HEAT);

                        if (property == null)
                            throw new RuntimeException(String.format("No heat property for: %s", metal));

                        event.addCapability(CapabilityForgeable.KEY, new ForgeableHeatableHandler(null, property.getSpecificHeat(), property.getMeltTemp()));
                        isHeatable = true;
                    }
                }
            }
            // If one of the above is also heatable, skip this
            if (!isHeatable) {
                ICapabilityProvider heatHandler = CapabilityItemHeat.getCustomHeat(stack);
                if (heatHandler != null) {
                    event.addCapability(CapabilityItemHeat.KEY, heatHandler);
                }
            }

            // Eggs
            if (stack.getItem() == Items.EGG) {
                event.addCapability(CapabilityEgg.KEY, new EggHandler());
            }
        }
    }

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof EntityPlayer player) {
            // Player skills
            if (!player.hasCapability(CapabilityPlayerData.CAPABILITY, null)) {
                event.addCapability(CapabilityPlayerData.KEY, new PlayerDataHandler(player));
            }
        }
    }

    /**
     * Fired on server only when a player logs in
     *
     * @param event {@link PlayerEvent.PlayerLoggedInEvent}
     */
    @SubscribeEvent
    public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player instanceof EntityPlayerMP player) {
            // Capability Sync Handler
            CapabilityContainerListener.addTo(player.inventoryContainer, player);

            // Food Stats
            FoodStatsTFC.replaceFoodStats(player);
            if (player.getFoodStats() instanceof IFoodStatsTFC) {
                // Also need to read the food stats from nbt, as they were not present when the player was loaded
                MinecraftServer server = player.world.getMinecraftServer();
                if (server != null) {
                    NBTTagCompound nbt = server.getPlayerList().getPlayerNBT(player);
                    // This can be null if the server is unable to read the file
                    //noinspection ConstantConditions
                    if (nbt != null) {
                        player.foodStats.readNBT(nbt);
                    }
                }
            }

            // layer Data
            IPlayerData playerData = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
            if (playerData != null) {
                // Sync
                TerraFirmaCraft.network.sendTo(new PacketPlayerDataUpdate(playerData.serializeNBT()), player);
            }
        }
    }

    /**
     * Fired on server only when a player logs out
     *
     * @param event {@link PlayerEvent.PlayerLoggedOutEvent}
     */
    @SubscribeEvent
    public static void onPlayerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            // Capability sync handler, we can remove it now
            CapabilityContainerListener.removeFrom((EntityPlayerMP) event.player);
        }
    }

    /**
     * Fired on server only when a player dies and respawns, or travels through dimensions
     *
     * @param event {@link PlayerEvent.PlayerRespawnEvent event}
     */
    @SubscribeEvent
    public static void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event) {
        if (event.player instanceof EntityPlayerMP player) {
            // Capability Sync Handler
            CapabilityContainerListener.addTo(player.inventoryContainer, player);

            // Food Stats
            FoodStatsTFC.replaceFoodStats(player);

            // Skills / Player data
            IPlayerData cap = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
            if (cap != null) {
                TerraFirmaCraft.network.sendTo(new PacketPlayerDataUpdate(cap.serializeNBT()), player);
            }
        }
    }

    /**
     * Fired on server only when a player dies and respawns.
     * Used to copy skill level before respawning since we need the original (AKA the body) player entity
     *
     * @param event {@link net.minecraftforge.event.entity.player.PlayerEvent.Clone}
     */
    @SubscribeEvent
    public static void onPlayerClone(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        if (event.getEntityPlayer() instanceof EntityPlayerMP player) {

            // Skills
            IPlayerData newSkills = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
            IPlayerData originalSkills = event.getOriginal().getCapability(CapabilityPlayerData.CAPABILITY, null);
            if (newSkills != null && originalSkills != null) {
                newSkills.deserializeNBT(originalSkills.serializeNBT());
                // To properly sync, we need to use PlayerRespawnEvent
            }
        }
    }

    /*
     * Fired on server, sync capabilities to client whenever player changes dimension.
     */
    @SubscribeEvent
    public static void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        if (event.player instanceof EntityPlayerMP player) {
            // Capability Sync Handler
            CapabilityContainerListener.addTo(player.inventoryContainer, player);

            // Food Stats
            FoodStatsTFC.replaceFoodStats(player);

            // Skills
            IPlayerData skills = player.getCapability(CapabilityPlayerData.CAPABILITY, null);
            if (skills != null) {
                TerraFirmaCraft.network.sendTo(new PacketPlayerDataUpdate(skills.serializeNBT()), player);
            }
        }
    }

    /**
     * Only fired on server
     */
    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        if (event.getEntityPlayer() instanceof EntityPlayerMP) {
            // Capability Sync Handler
            CapabilityContainerListener.addTo(event.getContainer(), (EntityPlayerMP) event.getEntityPlayer());
        }
    }

    @SubscribeEvent
    public static void onLivingSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        World world = event.getWorld();
        BlockPos pos = new BlockPos(event.getX(), event.getY(), event.getZ());
        if (world.getWorldType() == TerraFirmaCraft.WORLD_TYPE_TFC && event.getWorld().provider.getDimensionType() == DimensionType.OVERWORLD) {
            if (ConfigTFC.General.SPAWN_PROTECTION.preventMobs && event.getEntity().isCreatureType(EnumCreatureType.MONSTER, false)) {
                // Prevent Mobs
                ChunkDataTFC data = ChunkDataTFC.get(event.getWorld(), pos);
                int minY = ConfigTFC.General.SPAWN_PROTECTION.minYMobs;
                int maxY = ConfigTFC.General.SPAWN_PROTECTION.maxYMobs;
                if (data.isSpawnProtected() && minY <= maxY && event.getY() >= minY && event.getY() <= maxY) {
                    event.setResult(Event.Result.DENY);
                }
            }

            if (ConfigTFC.General.SPAWN_PROTECTION.preventPredators && event.getEntity() instanceof IPredator) {
                // Prevent Predators
                ChunkDataTFC data = ChunkDataTFC.get(event.getWorld(), pos);
                int minY = ConfigTFC.General.SPAWN_PROTECTION.minYPredators;
                int maxY = ConfigTFC.General.SPAWN_PROTECTION.maxYPredators;
                if (data.isSpawnProtected() && minY <= maxY && event.getY() >= minY && event.getY() <= maxY) {
                    event.setResult(Event.Result.DENY);
                }
            }

            if (event.getEntity() instanceof EntitySquid && world.getBlockState(pos).getBlock() instanceof BlockFluidBase) {
                // Prevents squids spawning outside of salt water (eg: oceans)
                Fluid fluid = ((BlockFluidBase) world.getBlockState(pos).getBlock()).getFluid();
                if (FluidRegistry.getFluid("salt_water") != fluid) {
                    event.setResult(Event.Result.DENY);
                }
            }

            // Check creature spawning - Prevents vanilla's respawning mechanic to spawn creatures outside their allowed conditions
            if (event.getEntity() instanceof ICreature creature) {
                float rainfall = ChunkDataTFC.getRainfall(world, pos);
                float temperature = ClimateTFC.getAvgTemp(world, pos);
                float floraDensity = ChunkDataTFC.getFloraDensity(world, pos);
                float floraDiversity = ChunkDataTFC.getFloraDiversity(world, pos);
                Biome biome = world.getBiome(pos);

                // We don't roll spawning again since vanilla is handling it
                if (creature.getSpawnWeight(biome, temperature, rainfall, floraDensity, floraDiversity) <= 0) {
                    event.setResult(Event.Result.DENY);
                }
            }

            // Stop mob spawning on surface
            if (ConfigTFC.General.DIFFICULTY.preventMobsOnSurface) {
                if (Helpers.shouldPreventOnSurface(event.getEntity())) {
                    int maximumY = (WorldTypeTFC.SEALEVEL - WorldTypeTFC.ROCKLAYER2) / 2 + WorldTypeTFC.ROCKLAYER2; // Half through rock layer 1
                    if (pos.getY() >= maximumY || world.canSeeSky(pos)) {
                        event.setResult(Event.Result.DENY);
                    }
                }
            }
        }

        // Stop mob spawning in thatch - the list of non-spawnable light-blocking, non-collidable blocks is hardcoded in WorldEntitySpawner#canEntitySpawnBody
        // This is intentionally outside the previous world type check as this is a fix for the thatch block, not a generic spawning check.
        if (event.getWorld().getBlockState(pos).getBlock() == BlocksCore.THATCH || event.getWorld().getBlockState(pos.up()).getBlock() == BlocksCore.THATCH) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        Entity entity = event.getEntity();
        if (event.getWorld().getWorldType() == TerraFirmaCraft.WORLD_TYPE_TFC && event.getWorld().provider.getDimensionType() == DimensionType.OVERWORLD) {
            // Fix skeleton rider traps spawning during thunderstorms
            if (entity instanceof EntitySkeletonHorse && ConfigTFC.General.DIFFICULTY.preventMobsOnSurface && ((EntitySkeletonHorse) entity).isTrap()) {
                entity.setDropItemsWhenDead(false);
                entity.setDead();
                event.setCanceled(true);
            }

            // Fix chickens spawning in caves (which is caused by zombie jockeys)
            if (entity instanceof EntityMob) {
                if (entity.isRiding()) {

                    Entity rider = entity.getRidingEntity();
                    //so we don't kill spider jockey's cause they are hilarious.
                    if (rider instanceof EntityChicken) {

                        entity.setDropItemsWhenDead(false);
                        entity.setDead();
                        rider.setDropItemsWhenDead(false);
                        rider.setDead();
                        event.setCanceled(true);
                    }

                }
            }
            //Just nuke them from orbit. It's the only way to be sure
            if (entity instanceof EntityChicken) {
                entity.setDropItemsWhenDead(false);
                entity.setDead();
                event.setCanceled(true); // NO!
            }

            // Prevent vanilla animals (that have a TFC counterpart) from mob spawners / egg throws / other mod mechanics
            if (ConfigTFC.General.OVERRIDES.forceReplaceVanillaAnimals && Helpers.isVanillaAnimal(entity)) {
                Entity TFCReplacement = Helpers.getTFCReplacement(entity);
                if (TFCReplacement != null) {
                    TFCReplacement.setPositionAndRotation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
                    event.getWorld().spawnEntity(TFCReplacement); // Fires another spawning event for the TFC replacement
                }
                event.setCanceled(true); // Cancel the vanilla spawn
            }
            if (ConfigTFC.General.DIFFICULTY.giveVanillaMobsEquipment) {
                /*
                // Set equipment to some mobs
                MonsterEquipment equipment = MonsterEquipment.get(entity);
                if (equipment != null)
                {
                    for (EntityEquipmentSlot slot : EntityEquipmentSlot.values())
                    {
                        equipment.getEquipment(slot, Constants.RNG).ifPresent(stack -> entity.setItemStackToSlot(slot, stack));
                    }
                }*/
            }
        }
        if (ConfigTFC.Devices.TEMPERATURE.coolHeatablesInWorld && entity instanceof EntityItem entityItem) {
            ItemStack stack = entityItem.getItem();
            IItemHeat heatCap = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
            if (heatCap != null) {
                // Add a NBT tag here to make sure our ItemExpireEvent listener picks this entity up as valid (and as an extra check)
                entityItem.addTag("TFCHeatableItem");
                entityItem.lifespan = ConfigTFC.Devices.TEMPERATURE.ticksBeforeAttemptToCool;
            }
        }
    }

    /**
     * This implementation utilizes EntityJoinWorldEvent and ItemExpireEvent, they go hand-in-hand with each other.
     * <p>
     * By manually editing the tag of the EntityItem upon spawning, we can identify what EntityItems should be subjected to the cooling process.
     * We also apply an extremely short lifespan to mimic the speed of the barrel recipe, albeit slightly longer (half a second, but modifiable via config).
     * Then all the checks are done in ItemExpireEvent to set a new cooler temperature depending on if the conditions are met.
     * <p>
     * First of all, if temperature is 0 or less, then nothing needs to be done and the original lifespan is restored/added on.
     * <p>
     * If no conditions are met, the same, short length of lifespan is added on so a quick check can be done once again in case the condition will be met later.
     * <p>
     * Now if, this has been repeated for a long time up until it meets the item's normal lifespan, it will despawn.
     * <p>
     * Finally, if a condition is met, we extend its lifespan once again so more checks can be done to cool down even further.
     */
    @SubscribeEvent
    public static void onItemEntityExpire(ItemExpireEvent event) {
        EntityItem entityItem = event.getEntityItem();
        ItemStack stack = entityItem.getItem();
        IItemHeat heatCap;
        if (ConfigTFC.Devices.TEMPERATURE.coolHeatablesInWorld && entityItem.getTags().contains("TFCHeatableItem") && (heatCap = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null)) != null) {
            int lifespan = stack.getItem().getEntityLifespan(stack, entityItem.world);
            if (entityItem.lifespan >= lifespan) {
                return; // If the ItemEntity has been there for as long or if not longer than the original unmodified lifespan, we return and setDead
            }
            float itemTemp = heatCap.getTemperature();
            if (itemTemp > 0) {
                float rand = Constants.RNG.nextFloat();
                BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos((int) entityItem.posX, (int) entityItem.posY, (int) entityItem.posZ);
                IBlockState state;
                if ((state = entityItem.world.getBlockState(pos)).getBlock() instanceof net.minecraftforge.fluids.BlockFluidBase) {
                    int fluidTemp = ((net.minecraftforge.fluids.BlockFluidBase) state.getBlock()).getFluid().getTemperature();
                    if (fluidTemp <= 300) {
                        heatCap.setTemperature(Math.max(0, Math.min(itemTemp, itemTemp - 350 + fluidTemp)));
                        entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5f, 0.8f + rand * 0.4f);
                        ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
                        if (rand <= 0.001F) {
                            entityItem.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2); // 1/1000 chance of the fluid being used up. Attempts to match the barrel recipe as it takes 1mb of water per operation.
                        }
                    }
                    event.setExtraLife(ConfigTFC.Devices.TEMPERATURE.ticksBeforeAttemptToCool); // Set half a second onto the lifespan
                    event.setCanceled(true);
                    entityItem.setNoPickupDelay(); // For some reason when lifespan is added, pickup delay is reset, we disable this to make the experience seamless
                    return;
                } else if (state.getPropertyKeys().contains(BlockSnow.LAYERS)) {
                    heatCap.setTemperature(Math.max(0, itemTemp - 70));
                    entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.55f, 0.8f + rand * 0.4f);
                    ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
                    if (rand <= 0.1F) {
                        if (state.getValue(BlockSnow.LAYERS) > 1) {
                            entityItem.world.setBlockState(pos, state.withProperty(BlockSnow.LAYERS, state.getValue(BlockSnow.LAYERS) - 1), 2);
                        } else {
                            entityItem.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        }
                    }
                }
                pos.setY(pos.getY() - 1);
                if ((state = entityItem.world.getBlockState(pos)).getMaterial() == Material.SNOW || state.getMaterial() == Material.CRAFTED_SNOW) {
                    heatCap.setTemperature(Math.max(0, itemTemp - 75));
                    entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.65f, 0.8f + rand * 0.4f);
                    ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
                    if (rand <= 0.01F) {
                        entityItem.world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2); // 1/100 chance of the snow block evaporating.
                    }
                } else if (state.getMaterial() == Material.ICE) {
                    heatCap.setTemperature(Math.max(0, itemTemp - 100));
                    entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.8f, 0.8f + rand * 0.4f);
                    ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
                    if (rand <= 0.01F) {
                        entityItem.world.setBlockState(pos, FluidRegistry.getFluid("fresh_water").getBlock().getDefaultState(), 2); // 1/100 chance of the ice turning into water.
                    }
                } else if (state.getMaterial() == Material.PACKED_ICE) {
                    heatCap.setTemperature(Math.max(0, itemTemp - 125));
                    entityItem.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1f, 0.8f + rand * 0.4f);
                    ((WorldServer) entityItem.world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, entityItem.posX, entityItem.posY, entityItem.posZ, 42, 0.0D, 0.15D, 0.0D, 0.08D);
                    if (rand <= 0.005F) {
                        entityItem.world.setBlockState(pos, FluidRegistry.getFluid("fresh_water").getBlock().getDefaultState(), 2); // 1/200 chance of the packed ice turning into water.
                    }
                }
                event.setExtraLife(itemTemp == 0 ? lifespan : ConfigTFC.Devices.TEMPERATURE.ticksBeforeAttemptToCool); // Set lifespan accordingly
                entityItem.setNoPickupDelay(); // For some reason when lifespan is added, pickup delay is reset, we disable this to make the experience seamless
            } else {
                event.setExtraLife(lifespan); // Sets the original lifespan, next time it expires it will be setDead
            }
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onProjectileImpactEvent(ProjectileImpactEvent.Throwable event) {
        if (event.getThrowable() instanceof EntityEgg) {
            // Only way of preventing EntityEgg from spawning a chicken is to cancel the impact altogether
            // Side effect: The impact will not hurt entities
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onGameRuleChange(GameRuleChangeEvent event) {
        GameRules rules = event.getRules();
        if ("naturalRegeneration".equals(event.getRuleName()) && ConfigTFC.General.OVERRIDES.forceNoVanillaNaturalRegeneration) {
            // Natural regeneration should be disabled, allows TFC to have custom regeneration
            event.getRules().setOrCreateGameRule("naturalRegeneration", "false");
            ModuleCore.LOGGER.warn("Something tried to set natural regeneration to true, reverting!");
        }
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        final World world = event.getWorld();

        if (world.provider.getDimension() == 0 && !world.isRemote) {
            // Calendar Sync / Initialization
            CalendarWorldData data = CalendarWorldData.get(world);
            CalendarTFC.INSTANCE.resetTo(data.getCalendar());
            TerraFirmaCraft.network.sendToAll(new PacketCalendarUpdate(CalendarTFC.INSTANCE));
        }

        if (ConfigTFC.General.OVERRIDES.forceNoVanillaNaturalRegeneration) {
            // Natural regeneration should be disabled, allows TFC to have custom regeneration
            event.getWorld().getGameRules().setOrCreateGameRule("naturalRegeneration", "false");
            ModuleCore.LOGGER.warn("Updating gamerule naturalRegeneration to false!");
        }
    }

    /**
     * This will disable the bonus chest, cheaty cheaty players >:(
     *
     * @param event {@link net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition}
     */
    @SubscribeEvent
    public static void onCreateSpawn(WorldEvent.CreateSpawnPosition event) {
        event.getSettings().bonusChestEnabled = false;
        ModuleCore.LOGGER.info("Disabling bonus chest, you cheaty cheater!");
    }

    @SubscribeEvent
    public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
        // Since cobble is a gravity block, placing it can lead to world crashes, so we avoid doing that and place rhyolite instead
        if (ConfigTFC.General.OVERRIDES.enableLavaWaterPlacesTFCBlocks) {
            if (event.getNewState().getBlock() == Blocks.STONE) {
                event.setNewState(StorageRock.getRockBlock(RAW, BASALT).getDefaultState().withProperty(BlockRockRaw.CAN_FALL, false));
            }
            if (event.getNewState().getBlock() == Blocks.COBBLESTONE) {
                event.setNewState(StorageRock.getRockBlock(RAW, RHYOLITE).getDefaultState().withProperty(BlockRockRaw.CAN_FALL, false));
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.START && event.player.ticksExisted % 100 == 0) {
            // Add spawn protection to surrounding chunks
            BlockPos basePos = new BlockPos(event.player);
            for (int i = -2; i <= 2; i++) {
                for (int j = -2; j <= 2; j++) {
                    BlockPos chunkPos = basePos.add(16 * i, 0, 16 * j);
                    World world = event.player.getEntityWorld();
                    if (world.isBlockLoaded(basePos)) {
                        ChunkDataTFC data = ChunkDataTFC.get(world, chunkPos);
                        data.addSpawnProtection(1);
                    }
                }
            }
        }

        if (event.phase == TickEvent.Phase.START && !(event.player.isCreative() || event.player.isSpectator()) && event.player.ticksExisted % 20 == 0) {
            // Update overburdened state
            int hugeHeavyCount = countPlayerOverburdened(event.player.inventory);
            if (hugeHeavyCount >= 1) {
                // Add extra exhaustion from carrying a heavy item
                // This is equivalent to an additional 25% of passive exhaustion
                event.player.addExhaustion(FoodStatsTFC.PASSIVE_EXHAUSTION * 20 * 0.25f / 0.4f);
            }
            if (hugeHeavyCount >= 2) {
                // Player is barely able to move
                event.player.addPotionEffect(new PotionEffect(EffectsCore.OVERBURDENED, 25, 125, false, false));
            }
        }

        if (event.phase == TickEvent.Phase.START && event.player.openContainer != null && event.player instanceof EntityPlayerMP) {
            // Sync capability only changes in the player's container
            // We do this for containers (such as the player's inventory), which don't sync capability changes through detectAndSendChanges
            CapabilityContainerListener.syncCapabilityOnlyChanges(event.player.openContainer, (EntityPlayerMP) event.player);
        }
    }

    //go last, so if other mods handle this event, we don't.
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void checkArrowFill(ArrowNockEvent event) {
        //if we didn't have ammo in main inventory and no other mod has handled the event
        if (!event.hasAmmo() && event.getAction() == null) {
            final EntityPlayer player = event.getEntityPlayer();
            if (player != null && !player.capabilities.isCreativeMode) {
                if (ItemQuiver.replenishArrow(player)) {
                    event.setAction(new ActionResult<>(EnumActionResult.PASS, event.getBow()));
                }
            }
        }
    }

    //go last to avoid cancelled events
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void pickupQuiverItems(EntityItemPickupEvent event) //only pickups of EntityItem, not EntityArrow
    {
        if (!event.isCanceled()) {
            if (ItemQuiver.pickupAmmo(event)) {
                event.setResult(Event.Result.ALLOW);
                event.getItem().getItem().setCount(0);
            }
        }
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ResourceLocation entityType = EntityList.getKey(event.getTarget());
        Entity target = event.getTarget();
        EntityPlayer player = event.getEntityPlayer();

        if (entityType != null && target.hurtResistantTime == 0 && !target.getEntityWorld().isRemote && player.getHeldItemMainhand().isEmpty() && player.isSneaking()) {
            String entityTypeName = entityType.toString();
            for (String pluckable : ConfigTFC.General.MISC.pluckableEntities) {
                if (pluckable.equals(entityTypeName)) {
                    target.dropItem(Items.FEATHER, 1);
                    target.attackEntityFrom(DamageSourcesTFC.PLUCKING, (float) ConfigTFC.General.MISC.damagePerFeather);
                    if (target instanceof IAnimal animalTarget) {
                        animalTarget.setFamiliarity(animalTarget.getFamiliarity() - 0.04f);
                    }
                    return;
                }
            }
        }
    }

    @SubscribeEvent
    public static void attachWorldCapabilities(AttachCapabilitiesEvent<World> event) {
        event.addCapability(CapabilityWorldTracker.KEY, new WorldTracker());
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            WorldTracker tracker = event.world.getCapability(CapabilityWorldTracker.CAPABILITY, null);
            if (tracker != null) {
                tracker.tick(event.world);
            }
        }
    }

    @SubscribeEvent
    public static void onServerChatEvent(ServerChatEvent event) {
        IPlayerData cap = event.getPlayer().getCapability(CapabilityPlayerData.CAPABILITY, null);
        if (cap != null) {
            long intoxicatedTicks = cap.getIntoxicatedTime() - 6 * ICalendar.TICKS_IN_HOUR; // Only apply intoxication after 6 hr
            if (intoxicatedTicks > 0) {
                float drunkChance = MathHelper.clamp((float) intoxicatedTicks / PlayerDataHandler.MAX_INTOXICATED_TICKS, 0, 0.7f);
                String originalMessage = event.getMessage();
                String[] words = originalMessage.split(" ");
                for (int i = 0; i < words.length; i++) {
                    String word = words[i];
                    if (word.isEmpty()) {
                        continue;
                    }

                    // Swap two letters
                    if (Constants.RNG.nextFloat() < drunkChance && word.length() >= 2) {
                        int pos = Constants.RNG.nextInt(word.length() - 1);
                        word = word.substring(0, pos) + word.charAt(pos + 1) + word.charAt(pos) + word.substring(pos + 2);
                    }

                    // Repeat / slur letters
                    if (Constants.RNG.nextFloat() < drunkChance) {
                        int pos = Constants.RNG.nextInt(word.length());
                        char repeat = word.charAt(pos);
                        int amount = 1 + Constants.RNG.nextInt(3);
                        word = word.substring(0, pos) + new String(new char[amount]).replace('\0', repeat) + (pos + 1 < word.length() ? word.substring(pos + 1) : "");
                    }

                    // Add additional letters
                    if (Constants.RNG.nextFloat() < drunkChance) {
                        int pos = Constants.RNG.nextInt(word.length());
                        char replacement = ALPHABET.charAt(Constants.RNG.nextInt(ALPHABET.length()));
                        if (Character.isUpperCase(word.charAt(Constants.RNG.nextInt(word.length())))) {
                            replacement = Character.toUpperCase(replacement);
                        }
                        word = word.substring(0, pos) + replacement + (pos + 1 < word.length() ? word.substring(pos + 1) : "");
                    }

                    words[i] = word;
                }
                event.setComponent(new TextComponentTranslation("<" + event.getUsername() + "> " + String.join(" ", words)));
            }
        }
    }

    private static int countPlayerOverburdened(InventoryPlayer inventory) {
        // This is just optimized (probably uselessly, but whatever) for use in onPlayerTick
        int hugeHeavyCount = 0;
        for (ItemStack stack : inventory.mainInventory) {
            if (CapabilityItemSize.checkItemSize(stack, Size.HUGE, Weight.VERY_HEAVY)) {
                hugeHeavyCount++;
                if (hugeHeavyCount >= 2) {
                    return hugeHeavyCount;
                }
            }
        }
        for (ItemStack stack : inventory.armorInventory) {
            if (CapabilityItemSize.checkItemSize(stack, Size.HUGE, Weight.VERY_HEAVY)) {
                hugeHeavyCount++;
                if (hugeHeavyCount >= 2) {
                    return hugeHeavyCount;
                }
            }
        }
        for (ItemStack stack : inventory.offHandInventory) {
            if (CapabilityItemSize.checkItemSize(stack, Size.HUGE, Weight.VERY_HEAVY)) {
                hugeHeavyCount++;
                if (hugeHeavyCount >= 2) {
                    return hugeHeavyCount;
                }
            }
        }
        return hugeHeavyCount;
    }
}
