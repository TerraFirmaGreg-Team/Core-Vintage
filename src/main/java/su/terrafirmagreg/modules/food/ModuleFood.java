package su.terrafirmagreg.modules.food;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.BaseCreativeTab;
import su.terrafirmagreg.modules.food.init.BlocksFood;
import su.terrafirmagreg.modules.food.init.ItemsFood;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

//@ModuleTFG(moduleID = "Food", name = "TFG Module Food")
public final class ModuleFood extends ModuleBase {

    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleFood.class.getSimpleName());
    public static final CreativeTabs FOOD_TAB = new BaseCreativeTab("food", "food/ingredient/wheat_bread_sandwich");

    public ModuleFood() {
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
