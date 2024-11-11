package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.TerraFirmaGreg;
import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.modules.ModuleContainer;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.CapabilityChunkData;
import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.damage.HandlerDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.modules.core.capabilities.egg.HandlerEgg;
import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.HandlerFood;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.HandlerHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.CapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.metal.HandlerMetal;
import su.terrafirmagreg.modules.core.capabilities.player.CapabilityPlayer;
import su.terrafirmagreg.modules.core.capabilities.pull.CapabilityPull;
import su.terrafirmagreg.modules.core.capabilities.sharpness.CapabilitySharpness;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.HandlerSize;
import su.terrafirmagreg.modules.core.capabilities.temperature.CapabilityTemperature;
import su.terrafirmagreg.modules.core.capabilities.worldtracker.CapabilityWorldTracker;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.core.client.gui.overlay.OverlayPlayerData;
import su.terrafirmagreg.modules.core.client.gui.overlay.OverlayTemperature;
import su.terrafirmagreg.modules.core.event.EventHandlerAmbiental;
import su.terrafirmagreg.modules.core.event.EventHandlerCapabilitiesChunk;
import su.terrafirmagreg.modules.core.event.EventHandlerCapabilitiesEntity;
import su.terrafirmagreg.modules.core.event.EventHandlerCapabilitiesItemStack;
import su.terrafirmagreg.modules.core.event.EventHandlerCapabilitiesWorld;
import su.terrafirmagreg.modules.core.event.EventHandlerConfigChanged;
import su.terrafirmagreg.modules.core.event.EventHandlerDebugInfo;
import su.terrafirmagreg.modules.core.event.EventHandlerPuddles;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.CommandsCore;
import su.terrafirmagreg.modules.core.init.DataSerializersCore;
import su.terrafirmagreg.modules.core.init.EntitiesCore;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.core.init.LootTablesCore;
import su.terrafirmagreg.modules.core.init.PacketsCore;
import su.terrafirmagreg.modules.core.init.PotionsCore;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import org.jetbrains.annotations.NotNull;

import java.util.List;

@ModuleInfo(
  moduleID = ModuleContainer.CORE,
  desc = "Core TFG content. Disabling this disables the entire mod and all its module.",
  coreModule = true
)
public final class ModuleCore extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleCore.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;
  public static IPacketService PACKET_SERVICE;

  public ModuleCore() {
    TAB = BaseCreativeTab.of("misc", "core/wand");
    REGISTRY = enableAutoRegistry(TAB);
    PACKET_SERVICE = enableNetwork();
  }

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    FluidRegistry.enableUniversalBucket();
    NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaGreg.getInstance(), new GuiHandler());

    CapabilityChunkData.register();
    CapabilityWorldTracker.register();
    CapabilityEgg.register();
    CapabilityHeat.register();
    CapabilityFood.register();
    CapabilityMetal.register();
    CapabilityPull.register();
    CapabilitySharpness.register();
    CapabilitySize.register();
    CapabilityPlayer.register();
    CapabilityTemperature.register();
    CapabilityDamageResistance.register();

  }

  @Override
  public void onInit(FMLInitializationEvent event) {
    HandlerSize.init();
    HandlerFood.init();
    HandlerEgg.init();
    HandlerMetal.init();
    HandlerHeat.init();
    HandlerDamageResistance.init();
  }

  @Override
  public void onClientPreInit(FMLPreInitializationEvent event) {

    MinecraftForge.EVENT_BUS.register(new EventHandlerDebugInfo());
    MinecraftForge.EVENT_BUS.register(new OverlayTemperature());
    MinecraftForge.EVENT_BUS.register(new OverlayPlayerData());
  }

  @Override
  public void onNetworkRegister() {

    PacketsCore.onRegister(packetRegistry);
  }

  @Override
  public void onRegister() {

    DataSerializersCore.onRegister(registryManager);
    BlocksCore.onRegister(registryManager);
    EntitiesCore.onRegister(registryManager);
    FluidsCore.onRegister(registryManager);
    ItemsCore.onRegister(registryManager);
    PotionsCore.onRegister(registryManager);
    LootTablesCore.onRegister(registryManager);
    CommandsCore.onRegister(registryManager);
  }

  public void onClientRegister() {
    EntitiesCore.onClientRegister(registryManager);

  }

  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    ObjectList<Class<?>> list = new ObjectArrayList<>();

    list.add(EventHandlerAmbiental.class);
    list.add(EventHandlerCapabilitiesChunk.class);
    list.add(EventHandlerCapabilitiesWorld.class);
    list.add(EventHandlerCapabilitiesItemStack.class);
    list.add(EventHandlerCapabilitiesEntity.class);
    list.add(EventHandlerPuddles.class);
    list.add(EventHandlerConfigChanged.class);

    return list;
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
