package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.FeaturesDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.PacketsDevice;
import su.terrafirmagreg.modules.device.init.SoundsDevice;
import su.terrafirmagreg.modules.device.plugin.top.TheOneProbeDevice;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  id = "device",
  author = "Xikaro",
  version = "1.0.0",
  description = "Device module"
)
public class ModuleDevice extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleDevice.class);


  public ModuleDevice() {

    enableRegistry();
    enableNetwork();
    enableCommand();
    enableFeature();
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
  public void onInit(FMLInitializationEvent event) {

    TheOneProbeDevice.init();
  }


  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
