package su.terrafirmagreg.modules.soil;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.BaseCreativeTab;
import su.terrafirmagreg.modules.soil.api.types.type.SoilTypeHandler;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariantHandler;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariantHandler;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.modules.Modules.SOIL;

@Module(moduleID = SOIL)
public final class ModuleSoil extends ModuleBase {

    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleSoil.class.getSimpleName());

    public static final CreativeTabs SOIL_TAB = new BaseCreativeTab("soil", "soil/grass/humus");

    public ModuleSoil() {
        this.enableAutoRegistry(SOIL_TAB);
    }

    @Override
    public void onRegister() {
        SoilTypeHandler.init();
        SoilBlockVariantHandler.init();
        SoilItemVariantHandler.init();

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
