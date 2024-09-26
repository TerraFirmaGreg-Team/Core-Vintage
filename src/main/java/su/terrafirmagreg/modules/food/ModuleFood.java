package su.terrafirmagreg.modules.food;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.food.init.BlocksFood;
import su.terrafirmagreg.modules.food.init.ItemsFood;

import net.minecraft.creativetab.CreativeTabs;

import org.jetbrains.annotations.NotNull;

//@ModuleTFG(moduleID = "Food", name = "TFG Module Food")
public final class ModuleFood extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleFood.class.getSimpleName());

  public final CreativeTabs FOOD_TAB;

  public ModuleFood() {
    this.FOOD_TAB = BaseCreativeTab.of("food", "food/ingredient/wheat_bread_sandwich");

    this.enableAutoRegistry(FOOD_TAB);
  }

  @Override
  public void onRegister() {
    BlocksFood.onRegister(registryManager);
    ItemsFood.onRegister(registryManager);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
