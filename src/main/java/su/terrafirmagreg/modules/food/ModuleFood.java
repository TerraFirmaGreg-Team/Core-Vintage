package su.terrafirmagreg.modules.food;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.food.init.BlocksFood;
import su.terrafirmagreg.modules.food.init.ItemsFood;

import net.minecraft.creativetab.CreativeTabs;

import org.jetbrains.annotations.NotNull;

//@ModuleTFG(moduleID = "Food", name = "TFG Module Food")
public final class ModuleFood extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleFood.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;

  public ModuleFood() {
    TAB = BaseCreativeTab.of("food", "food/ingredient/wheat_bread_sandwich");
    REGISTRY = enableAutoRegistry(TAB);
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
