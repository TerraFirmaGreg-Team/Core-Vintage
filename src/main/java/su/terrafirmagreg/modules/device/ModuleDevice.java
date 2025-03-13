package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.PacketsDevice;
import su.terrafirmagreg.modules.device.init.RecipesDevice;
import su.terrafirmagreg.modules.device.init.SoundsDevice;
import su.terrafirmagreg.modules.device.plugin.top.TheOneProbeDevice;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.modules.ModulesContainer.DEVICE;

@ModuleInfo(
  moduleID = DEVICE,
  containerID = MOD_ID,
  name = "Device",
  author = "Xikaro",
  version = "1.0.0",
  description = "Device module"
)
public final class ModuleDevice extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleDevice.class.getSimpleName());

  public static Supplier<BaseItemGroup> GROUP;
  public static IRegistryManager REGISTRY;
  public static INetworkManager NETWORK;

  public ModuleDevice() {

    GROUP = BaseItemGroup.of(this, "bellows");
    REGISTRY = enableRegistry().group(GROUP);
    NETWORK = enableNetwork();

  }

  @Override
  public void onNetworkRegister(INetworkManager network) {

    PacketsDevice.onRegister(network);
  }


  @Override
  public void onRegister(IRegistryManager registry) {
    BlocksDevice.onRegister(registry);
    ItemsDevice.onRegister(registry);
    SoundsDevice.onRegister(registry);

  }

  @Override
  public void onInit(FMLInitializationEvent event) {

    TheOneProbeDevice.init();
  }

  @Override
  public void onRecipeRegister() {
    RecipesDevice.onRegister();
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
