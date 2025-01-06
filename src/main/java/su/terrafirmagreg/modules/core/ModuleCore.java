package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.capabilities.ambiental.CapabilityAmbiental;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHandlerHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.CapabilityHeat;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.core.init.PacketsCore;
import su.terrafirmagreg.temp.modules.ambiental.TFCAmbientalEventHandler;
import su.terrafirmagreg.temp.modules.ambiental.TFCAmbientalGuiRenderer;

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

  public static final LoggingHelper LOGGER = LoggingHelper.of(CORE);

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
  }

  @Override
  public void onNetworkRegister(INetworkManager network) {

    PacketsCore.onRegister(network);
  }

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {

    CapabilityHeat.register();
    CapabilityAmbiental.register();

    // Common Events
    MinecraftForge.EVENT_BUS.register(new TFCAmbientalEventHandler());
  }

  @Override
  public void onClientPreInit(FMLPreInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(new TFCAmbientalGuiRenderer());
  }

  @Override
  public void onInit(FMLInitializationEvent event) {
    CapabilityHandlerHeat.init();
  }

  @Override
  public @NotNull List<Class<?>> getEventBusSubscribers() {
    ObjectList<Class<?>> list = new ObjectArrayList<>();

    return list;
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
