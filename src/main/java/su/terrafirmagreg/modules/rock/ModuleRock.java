package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategoryHandler;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypeHandler;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariantHandler;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariantHandler;
import su.terrafirmagreg.modules.rock.data.BlocksRock;
import su.terrafirmagreg.modules.rock.data.ItemsRock;

import net.minecraft.creativetab.CreativeTabs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

@ModuleTFG(moduleID = "Rock", name = "TFG Module Rock")
public final class ModuleRock extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger(ModuleRock.class.getSimpleName());

    public static final CreativeTabs ROCK_TAB = new CreativeTabBase("rock", "rock/raw/basalt");

    public ModuleRock() {
        super(2);
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
    public @NotNull Logger getLogger() {
        return LOGGER;
    }

    @NotNull
    @Override
    public List<Class<?>> getEventBusSubscribers() {
        return Collections.singletonList(ModuleRock.class);
    }
}
