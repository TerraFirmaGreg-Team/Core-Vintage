package su.terrafirmagreg.modules.soil;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.modules.soil.api.types.type.SoilTypeHandler;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.modules.ModuleContainer.SOIL;

@Module(moduleID = SOIL)
public final class ModuleSoil extends ModuleBase {

    public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleSoil.class.getSimpleName());

    public final CreativeTabs SOIL_TAB;

    public ModuleSoil() {
        this.SOIL_TAB = BaseCreativeTab.of("soil", "soil/grass/humus");

        this.enableAutoRegistry(SOIL_TAB);
    }

    @Override
    public void onRegister() {
        SoilTypeHandler.init();

        BlocksSoil.onRegister(registryManager);
        ItemsSoil.onRegister(registryManager);
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }

    @NotNull
    @Override
    public List<Class<?>> getEventBusSubscribers() {
        return Collections.singletonList(ModuleSoil.class);
    }
}
