package BananaFructa.deathdairydespair;

import net.dries007.tfc.CommonEventHandler;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;
import net.dries007.tfc.api.capability.food.IFoodStatsTFC;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.player.IPlayerData;
import net.dries007.tfc.compat.patchouli.TFCPatchouliPlugin;
import net.dries007.tfc.network.PacketPlayerDataUpdate;
import net.dries007.tfc.objects.container.CapabilityContainerListener;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.GameRuleChangeEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TFCCommonEventHandlerWrapper {

    public static void init() {
        MinecraftForge.EVENT_BUS.unregister(CommonEventHandler.class);
        MinecraftForge.EVENT_BUS.register(TFCCommonEventHandlerWrapper.class);
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onNeighborNotify(BlockEvent.NeighborNotifyEvent event) {
        CommonEventHandler.onNeighborNotify(event);
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onBlockPlaced(BlockEvent.EntityPlaceEvent event) {
        CommonEventHandler.onBlockPlaced(event);
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void onExplosionDetonate(ExplosionEvent.Detonate event) {
        CommonEventHandler.onExplosionDetonate(event);
    }

    @SubscribeEvent
    public static void onEntityUseItem(LivingEntityUseItemEvent.Finish event) {
        CommonEventHandler.onEntityUseItem(event);
    }

    @SubscribeEvent(
            priority = EventPriority.HIGHEST
    )
    public static void breakEvent(BlockEvent.BreakEvent event) {
        CommonEventHandler.breakEvent(event);
    }

    @SubscribeEvent
    public static void onBlockHarvestDrops(BlockEvent.HarvestDropsEvent event) {
        CommonEventHandler.onBlockHarvestDrops(event);
    }

    @SubscribeEvent
    public static void onBreakProgressEvent(PlayerEvent.BreakSpeed event) {
        CommonEventHandler.onBreakProgressEvent(event);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        CommonEventHandler.onRightClickBlock(event);
    }

    @SubscribeEvent
    public static void onUseHoe(UseHoeEvent event) {
        CommonEventHandler.onUseHoe(event);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        CommonEventHandler.onLivingHurt(event);
    }

    @SubscribeEvent
    public static void attachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        CommonEventHandler.attachItemCapabilities(event);
    }

    @SubscribeEvent
    public static void onAttachEntityCapabilities(AttachCapabilitiesEvent<Entity> event) {
        CommonEventHandler.onAttachEntityCapabilities(event);
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event) {
        CommonEventHandler.onPlayerLoggedIn(event);
    }

    @SubscribeEvent
    public static void onPlayerLoggedOut(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event) {
        CommonEventHandler.onPlayerLoggedOut(event);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event) {
        if (event.player instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP)event.player;
            CapabilityContainerListener.addTo(player.inventoryContainer, player);
            FoodStatsTFC.replaceFoodStats(player);

            // ==========================================
            // Loads the saved player food stats

            if (player.getFoodStats() instanceof IFoodStatsTFC) {
                MinecraftServer server = player.world.getMinecraftServer();
                if (server != null) {
                    NBTTagCompound nbt = server.getPlayerList().getPlayerNBT(player);
                    if (nbt != null) {
                        player.getFoodStats().readNBT(nbt);

                        FoodStatsTFC tfc = (FoodStatsTFC) player.getFoodStats();

                        tfc.setFoodLevel(Config.respawnHungerLevel/5);
                        tfc.setThirst(Config.respawnThirstLevel); // Why isn't this also on an 0 - 20 interval ?
                    }
                }
            }

            // =========================================

            IPlayerData cap = (IPlayerData)player.getCapability(CapabilityPlayerData.CAPABILITY, (EnumFacing)null);
            if (cap != null) {
                if (Loader.isModLoaded("patchouli") && !event.isEndConquered() && !player.world.getGameRules().getBoolean("keepInventory") && ConfigTFC.General.MISC.giveBook) {
                    TFCPatchouliPlugin.giveBookToPlayer(player);
                    cap.setHasBook(true);
                }

                TerraFirmaCraft.getNetwork().sendTo(new PacketPlayerDataUpdate((NBTTagCompound)cap.serializeNBT()), player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        CommonEventHandler.onPlayerClone(event);
    }

    @SubscribeEvent
    public static void onChangeDimension(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event) {
        CommonEventHandler.onChangeDimension(event);
    }

    @SubscribeEvent
    public static void onContainerOpen(PlayerContainerEvent.Open event) {
        CommonEventHandler.onContainerOpen(event);
    }

    @SubscribeEvent
    public static void onLivingSpawnEvent(LivingSpawnEvent.CheckSpawn event) {
        CommonEventHandler.onLivingSpawnEvent(event);
    }

    @SubscribeEvent
    public static void onEntityJoinWorldEvent(EntityJoinWorldEvent event) {
        CommonEventHandler.onEntityJoinWorldEvent(event);
    }

    @SubscribeEvent
    public static void onItemEntityExpire(ItemExpireEvent event) {
        CommonEventHandler.onItemEntityExpire(event);
    }

    @SubscribeEvent
    public static void onProjectileImpactEvent(ProjectileImpactEvent.Throwable event) {
        CommonEventHandler.onProjectileImpactEvent(event);
    }

    @SubscribeEvent
    public static void onGameRuleChange(GameRuleChangeEvent event) {
        CommonEventHandler.onGameRuleChange(event);
    }

    @SubscribeEvent
    public static void onWorldLoad(WorldEvent.Load event) {
        CommonEventHandler.onWorldLoad(event);
    }

    @SubscribeEvent
    public static void onCreateSpawn(WorldEvent.CreateSpawnPosition event) {
        CommonEventHandler.onCreateSpawn(event);
    }

    @SubscribeEvent
    public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
        CommonEventHandler.onFluidPlaceBlock(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        CommonEventHandler.onPlayerTick(event);
    }

    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void checkArrowFill(ArrowNockEvent event) {
        CommonEventHandler.checkArrowFill(event);
    }

    @SubscribeEvent(
            priority = EventPriority.LOWEST
    )
    public static void pickupQuiverItems(EntityItemPickupEvent event) {
        CommonEventHandler.pickupQuiverItems(event);
    }

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        CommonEventHandler.onEntityInteract(event);
    }

    @SubscribeEvent
    public static void attachWorldCapabilities(AttachCapabilitiesEvent<World> event) {
        CommonEventHandler.attachWorldCapabilities(event);
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        CommonEventHandler.onWorldTick(event);
    }

    @SubscribeEvent
    public static void onServerChatEvent(ServerChatEvent event) {
        CommonEventHandler.onServerChatEvent(event);
    }


}
