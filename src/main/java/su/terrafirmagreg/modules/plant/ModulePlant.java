package su.terrafirmagreg.modules.plant;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.BaseCreativeTab;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.plant.init.ItemsPlant;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

//@Module(moduleID = "Agriculture", name = "TFG Module Agriculture")
public final class ModulePlant extends ModuleBase {

    public static final CreativeTabs AGRICULTURE_TAB = new BaseCreativeTab("flora", "flora/crop/seed/rice");
    public static final LoggingHelper LOGGER = LoggingHelper.of(ModulePlant.class.getSimpleName());

    public ModulePlant() {
        this.enableAutoRegistry(AGRICULTURE_TAB);
    }

    @Override
    public void onRegister() {
        BlocksPlant.onRegister(registryManager);
        ItemsPlant.onRegister(registryManager);
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }
}
