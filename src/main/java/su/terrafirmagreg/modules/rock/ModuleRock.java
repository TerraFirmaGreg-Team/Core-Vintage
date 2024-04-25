package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypeHandler;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


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
        //MinecraftForge.EVENT_BUS.register(new MaterialEventHandler());
    }

    @Override
    public void onRegister() {
        RockTypeHandler.init();

        BlocksRock.onRegister(registryManager);
        ItemsRock.onRegister(registryManager);
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {

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
