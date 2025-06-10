package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.ModuleBase;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.FeaturesDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.PacketsDevice;
import su.terrafirmagreg.modules.device.init.PluginsDevice;
import su.terrafirmagreg.modules.device.init.SoundsDevice;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Device module"
)
public class ModuleDevice extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleDevice.class);


  public ModuleDevice() {
    super("device");

    enableRegistry();
    enableNetwork();
    enableCommand();
    enableFeature();
    enablePlugin();
  }

  @Override
  public void onNetwork(IPacketRegistrar registrar) {

    PacketsDevice.onRegister(registrar);
  }


  @Override
  public void onRegistry(IRegistryRegistrar registrar) {
    registrar.group("bellows");

    BlocksDevice.onRegister(registrar);
    ItemsDevice.onRegister(registrar);
    SoundsDevice.onRegister(registrar);

  }

  @Override
  public void onFeature(IFeatureRegistrar registrar) {

    FeaturesDevice.onRegister(registrar);
  }

  @Override
  public void onPlugin(IPluginRegistrar registrar) {

    PluginsDevice.onRegister(registrar);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
