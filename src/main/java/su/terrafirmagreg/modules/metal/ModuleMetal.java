package su.terrafirmagreg.modules.metal;

import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.metal.data.BlocksMetal;
import su.terrafirmagreg.modules.metal.data.ItemsMetal;

import javax.annotation.Nonnull;

//@Module(moduleID = "Metal", name = "TFG Module Metal")
public class ModuleMetal extends ModuleBase {
	public static final Logger LOGGER = LogManager.getLogger(ModuleMetal.class.getSimpleName());
	public static final CreativeTabs METAL_TAB = new CreativeTabBase("metal", "metal/anvil/red_steel");

	public ModuleMetal() {
		super(8);
		this.enableAutoRegistry(METAL_TAB);
	}

	@Override
	public void onRegister() {
		BlocksMetal.onRegister(registryManager);
		ItemsMetal.onRegister(registryManager);
	}

	@Nonnull
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
}
