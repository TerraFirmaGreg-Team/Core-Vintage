package su.terrafirmagreg.modules.arboriculture;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.arboriculture.init.BlocksArboriculture;
import su.terrafirmagreg.modules.arboriculture.init.ItemsArboriculture;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

//@Module(moduleID = "Arboriculture", name = "TFG Module Arboriculture")
public final class ModuleArboriculture extends ModuleBase {

    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleArboriculture.class.getSimpleName());
    public static final CreativeTabs ARBORICULTURE_TAB = new CreativeTabBase("arboriculture", "arboriculture/log/pine");

    public ModuleArboriculture() {
        this.enableAutoRegistry(ARBORICULTURE_TAB);

    }

    @Override
    public void onRegister() {
        BlocksArboriculture.onRegister(registryManager);
        ItemsArboriculture.onRegister(registryManager);
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }
}
