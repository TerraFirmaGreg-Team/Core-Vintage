package su.terrafirmagreg.modules.devices;


import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.devices.data.BlocksDevice;
import su.terrafirmagreg.modules.devices.data.ItemsDevice;

import javax.annotation.Nonnull;

@Module(moduleID = "Device", name = "TFG Module Device")
public class ModuleDevice extends ModuleBase {

	public static final Logger LOGGER = LogManager.getLogger("Module Rock");
	public static final CreativeTabs DEVICES_TAB = new CreativeTabBase("device", "device/bellows", false);


	public ModuleDevice() {
		super(5);
		this.enableAutoRegistry(DEVICES_TAB);

	}

	@Override
	public void onRegister() {
		BlocksDevice.onRegister(registryManager);
		ItemsDevice.onRegister(registryManager);
	}


	@Nonnull
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
}
