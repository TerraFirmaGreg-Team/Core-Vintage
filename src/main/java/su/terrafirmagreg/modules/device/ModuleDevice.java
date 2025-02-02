package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.framework.network.api.INetworkManager;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.modules.ModulesContainer.DEVICE;

@ModuleInfo(
  moduleID = DEVICE,
  containerID = MOD_ID,
  name = "Device",
  author = "Xikaro",
  version = "1.0.0"
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
  public void onRegister(IRegistryManager registry) {
    BlocksDevice.onRegister(registry);
    ItemsDevice.onRegister(registry);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
