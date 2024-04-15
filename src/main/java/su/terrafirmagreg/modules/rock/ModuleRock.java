package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategoryHandler;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypeHandler;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariantHandler;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariantHandler;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.modules.Modules.Module_Rock;

@Module(moduleID = Module_Rock)
public final class ModuleRock extends ModuleBase {

    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleRock.class.getSimpleName());

    public static final CreativeTabs ROCK_TAB = new CreativeTabBase("rock", "rock/raw/basalt");

    public ModuleRock() {
        this.enableAutoRegistry(ROCK_TAB);
    }

    @Override
    public void onRegister() {
        RockCategoryHandler.init();
        RockTypeHandler.init();
        RockBlockVariantHandler.init();
        RockItemVariantHandler.init();

        BlocksRock.onRegister(registryManager);
        ItemsRock.onRegister(registryManager);
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }

    @NotNull
    @Override
    public List<Class<?>> getEventBusSubscribers() {
        return Collections.singletonList(ModuleRock.class);
    }
}
