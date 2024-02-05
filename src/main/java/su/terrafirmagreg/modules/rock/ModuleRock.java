package su.terrafirmagreg.modules.rock;


import net.minecraft.creativetab.CreativeTabs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.objects.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategoryHandler;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypeHandler;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariantHandler;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariantHandler;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import java.util.Collections;
import java.util.List;

@Module(moduleID = "Rock", name = "TFG Rock")
public class ModuleRock extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger("Module Rock");

    public static final CreativeTabs ROCK_TAB = new CreativeTabBase("rock", "rock/chiseled/basalt", false);

    public ModuleRock() {
        super(1);
        this.enableAutoRegistry(ROCK_TAB);

    }

    @Override
    public void onRegister() {
        RockCategoryHandler.init();
        RockTypeHandler.init();
        RockBlockVariantHandler.init();
        RockItemVariantHandler.init();

        BlocksRock.onRegister(registry);
        ItemsRock.onRegister(registry);
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
