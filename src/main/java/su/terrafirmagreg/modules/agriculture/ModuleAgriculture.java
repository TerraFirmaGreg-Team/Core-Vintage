package su.terrafirmagreg.modules.agriculture;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.BaseCreativeTab;
import su.terrafirmagreg.modules.agriculture.init.BlocksAgriculture;
import su.terrafirmagreg.modules.agriculture.init.ItemsAgriculture;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

//@Module(moduleID = "Agriculture", name = "TFG Module Agriculture")
public final class ModuleAgriculture extends ModuleBase {

    public static final CreativeTabs AGRICULTURE_TAB = new BaseCreativeTab("agriculture", "agriculture/crop/seed/rice");
    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleAgriculture.class.getSimpleName());

    public ModuleAgriculture() {
        this.enableAutoRegistry(AGRICULTURE_TAB);
    }

    @Override
    public void onRegister() {
        BlocksAgriculture.onRegister(registryManager);
        ItemsAgriculture.onRegister(registryManager);
    }

    @NotNull
    @Override
    public LoggingHelper getLogger() {
        return LOGGER;
    }
}
