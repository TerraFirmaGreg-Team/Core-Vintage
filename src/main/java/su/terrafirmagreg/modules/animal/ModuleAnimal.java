package su.terrafirmagreg.modules.animal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.modules.animal.data.BlocksAnimal;
import su.terrafirmagreg.modules.animal.data.ItemsAnimal;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.modules.core.ModuleCore.MISC_TAB;

//@Module(moduleID = "Animal", name = "TFG Module Animal")
public class ModuleAnimal extends ModuleBase {
	public static final Logger LOGGER = LogManager.getLogger(ModuleAnimal.class.getSimpleName());

	public ModuleAnimal() {
		super(8);
		this.enableAutoRegistry(MISC_TAB);
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
