package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.EntitiesDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.PacketsDevice;
import su.terrafirmagreg.modules.device.init.RecipesDevice;
import su.terrafirmagreg.modules.device.init.RegistriesDevice;
import su.terrafirmagreg.modules.device.init.SoundsDevice;
import su.terrafirmagreg.modules.device.plugin.top.TheOneProbeDevice;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.ModuleContainer.DEVICE;

@ModuleInfo(moduleID = DEVICE)
public final class ModuleDevice extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleDevice.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;
  public static IPacketService PACKET_SERVICE;

  public ModuleDevice() {
    TAB = BaseCreativeTab.of("device", "device/bellows");
    REGISTRY = enableAutoRegistry(TAB);
    PACKET_SERVICE = enableNetwork();
  }

  @Override
  public void onInit(FMLInitializationEvent event) {

    TheOneProbeDevice.init();
  }

  @Override
  public void onNetworkRegister() {

    PacketsDevice.onRegister(packetRegistry);
  }

  @Override
  public void onNewRegister() {

    RegistriesDevice.onRegister();
  }

  @Override
  public void onRegister() {
    BlocksDevice.onRegister(registryManager);
    ItemsDevice.onRegister(registryManager);
    EntitiesDevice.onRegister(registryManager);
    SoundsDevice.onRegister(registryManager);
  }

  @SideOnly(Side.CLIENT)
  public void onClientRegister() {
    EntitiesDevice.onClientRegister(registryManager);

  }

  @Override
  public void onRecipesRegister() {

    RecipesDevice.onRegister();
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }

}
