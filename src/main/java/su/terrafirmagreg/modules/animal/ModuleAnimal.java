package su.terrafirmagreg.modules.animal;

import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.animal.data.BlocksAnimal;
import su.terrafirmagreg.modules.animal.data.ItemsAnimal;

import javax.annotation.Nonnull;

@ModuleTFG(moduleID = "Animal", name = "TFG Module Animal")
public class ModuleAnimal extends ModuleBase {
	public static final Logger LOGGER = LogManager.getLogger(ModuleAnimal.class.getSimpleName());

	public static final CreativeTabs ANIMAL_TAB = new CreativeTabBase("misc", "animal/wand");

	public ModuleAnimal() {
		super(8);
		this.enableAutoRegistry(ANIMAL_TAB);
	}

	@Override
	public void onRegister() {
		BlocksAnimal.onRegister(registryManager);
		ItemsAnimal.onRegister(registryManager);
	}

	@Nonnull
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
}
