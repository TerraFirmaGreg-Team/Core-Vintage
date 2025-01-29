package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityAmbiental;
import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityHandlerAmbiental;
import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.damage.CapabilityHandlerDamageResistance;
import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityEgg;
import su.terrafirmagreg.modules.core.capabilities.egg.CapabilityHandlerEgg;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHandlerHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityHandlerSize;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.worldtracker.CapabilityWorldTracker;
import su.terrafirmagreg.modules.core.client.gui.overlay.OverlayAmbiental;
import su.terrafirmagreg.modules.core.event.EventHandlerAmbiental;
import su.terrafirmagreg.modules.core.event.EventHandlerCapabilitiesEntity;
import su.terrafirmagreg.modules.core.event.EventHandlerCapabilitiesItemStack;
import su.terrafirmagreg.modules.core.event.EventHandlerCapabilitiesWorld;
import su.terrafirmagreg.modules.core.event.EventHandlerConfigChanged;
import su.terrafirmagreg.modules.core.event.EventHandlerFallingBlock;
import su.terrafirmagreg.modules.core.event.EventHandlerPortalSpawn;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.core.init.LootTablesCore;
import su.terrafirmagreg.modules.core.init.PacketsCore;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.modules.ModulesContainer.CORE;

@ModuleInfo(
  moduleID = CORE,
  containerID = MOD_ID,
  name = "Core",
  coreModule = true,
  author = "Xikaro",
  version = "1.0.0",
  description = {
    "Core TerraFirmaGreg content. ",
    "Disabling this disables the entire mod and all its module."
  })
public final class ModuleCore extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleCore.class.getSimpleName());

  public static IRegistryManager REGISTRY;
  public static INetworkManager NETWORK;

  public static Supplier<BaseItemGroup> GROUP;

  public ModuleCore() {

    GROUP = BaseItemGroup.of(this, "wand");
    REGISTRY = enableRegistry().group(GROUP);
    NETWORK = enableNetwork();

  }

  @Override
  public void onRegister(IRegistryManager registry) {
    BlocksCore.onRegister(registry);
    ItemsCore.onRegister(registry);
    EffectsCore.onRegister(registry);
    LootTablesCore.onRegister(registry);
  }

  @Override
  public void onNetworkRegister(INetworkManager network) {

    PacketsCore.onRegister(network);
  }

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    CapabilityWorldTracker.register();
    CapabilityEgg.register();
    CapabilityHeat.register();
    CapabilitySize.register();
    CapabilityAmbiental.register();
    CapabilityDamageResistance.register();

  }

  @Override
  public void onClientPreInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(OverlayAmbiental.getInstance());
  }

  @Override
  public void onInit(FMLInitializationEvent event) {

    CapabilityHandlerEgg.init();
    CapabilityHandlerHeat.init();
    CapabilityHandlerSize.init();
    CapabilityHandlerAmbiental.init();
    CapabilityHandlerDamageResistance.init();
  }

  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    ObjectList<Class<?>> list = new ObjectArrayList<>();

    list.add(EventHandlerCapabilitiesItemStack.class);
    list.add(EventHandlerCapabilitiesEntity.class);
    list.add(EventHandlerCapabilitiesWorld.class);

    list.add(EventHandlerConfigChanged.class);
    list.add(EventHandlerAmbiental.class);
    list.add(EventHandlerFallingBlock.class);
    list.add(EventHandlerPortalSpawn.class);

    return list;
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
