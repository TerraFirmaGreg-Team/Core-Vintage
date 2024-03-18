package su.terrafirmagreg.modules.device;


import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.device.data.BlocksDevice;
import su.terrafirmagreg.modules.device.data.ItemsDevice;
import su.terrafirmagreg.modules.device.data.SoundDevice;

import javax.annotation.Nonnull;

@ModuleTFG(moduleID = "Device", name = "TFG Module Device")
public final class ModuleDevice extends ModuleBase {

	public static final Logger LOGGER = LogManager.getLogger(ModuleDevice.class.getSimpleName());
	public static final CreativeTabs DEVICES_TAB = new CreativeTabBase("device", "device/firestarter");


	public ModuleDevice() {
		super(5);
		this.enableAutoRegistry(DEVICES_TAB);

	}

	@Override
	public void onRegister() {
		BlocksDevice.onRegister(registryManager);
		ItemsDevice.onRegister(registryManager);
		SoundDevice.onRegister(registryManager);
	}


	@Nonnull
	@Override
	public Logger getLogger() {
		return LOGGER;
	}

}
