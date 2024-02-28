package su.terrafirmagreg.modules.food;

import net.minecraft.creativetab.CreativeTabs;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.food.data.BlocksFood;
import su.terrafirmagreg.modules.food.data.ItemsFood;

import javax.annotation.Nonnull;

//@Module(moduleID = "Food", name = "TFG Module Food")
public class ModuleFood extends ModuleBase {
	public static final Logger LOGGER = LogManager.getLogger(ModuleFood.class.getSimpleName());
	public static final CreativeTabs FOOD_TAB = new CreativeTabBase("food", "food/ingredient/wheat_bread_sandwich");

	public ModuleFood() {
		super(8);
		this.enableAutoRegistry(FOOD_TAB);
	}

	@Override
	public void onRegister() {
		BlocksFood.onRegister(registryManager);
		ItemsFood.onRegister(registryManager);
	}

	@Nonnull
	@Override
	public Logger getLogger() {
		return LOGGER;
	}
}
