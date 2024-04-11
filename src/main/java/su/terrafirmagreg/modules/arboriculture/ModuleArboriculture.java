package su.terrafirmagreg.modules.arboriculture;

import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.api.util.LoggingUtils;
import su.terrafirmagreg.modules.arboriculture.init.BlocksArboriculture;
import su.terrafirmagreg.modules.arboriculture.init.ItemsArboriculture;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

//@Module(moduleID = "Arboriculture", name = "TFG Module Arboriculture")
public final class ModuleArboriculture extends ModuleBase {

    public static final LoggingUtils LOGGER = new LoggingUtils(ModuleArboriculture.class.getSimpleName());
    public static final CreativeTabs ARBORICULTURE_TAB = new CreativeTabBase("arboriculture", "arboriculture/log/pine");

    public ModuleArboriculture() {
        super(6);
        this.enableAutoRegistry(ARBORICULTURE_TAB);

    }

    @Override
    public void onRegister() {
        BlocksArboriculture.onRegister(registryManager);
        ItemsArboriculture.onRegister(registryManager);
    }

    @Override
    public @NotNull LoggingUtils getLogger() {
        return LOGGER;
    }
}
