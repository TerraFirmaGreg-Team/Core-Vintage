package su.terrafirmagreg.modules.agriculture;

import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.agriculture.data.BlocksAgriculture;
import su.terrafirmagreg.modules.agriculture.data.ItemsAgriculture;

import javax.annotation.Nonnull;

//@Module(moduleID = "Agriculture", name = "TFG Module Agriculture")
public class ModuleAgriculture extends ModuleBase {
	public static final CreativeTabs AGRICULTURE_TAB = new CreativeTabBase("agriculture", "tfc:agriculture/crop/seed/rice");
	public static final Logger LOGGER = LogManager.getLogger(ModuleAgriculture.class.getSimpleName());

	public ModuleAgriculture() {
		super(7);
		this.enableAutoRegistry(AGRICULTURE_TAB);
	}

	@Override
	public void onRegister() {
		BlocksAgriculture.onRegister(registryManager);
		ItemsAgriculture.onRegister(registryManager);
	}

	@Nonnull
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
}
