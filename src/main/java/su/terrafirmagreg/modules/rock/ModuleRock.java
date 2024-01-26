package su.terrafirmagreg.modules.rock;


import net.minecraft.creativetab.CreativeTabs;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.Tags;
import su.terrafirmagreg.api.modules.ModuleBase;
import su.terrafirmagreg.api.modules.ModuleTFG;
import su.terrafirmagreg.api.objects.creativetab.CreativeTabBase;
import su.terrafirmagreg.api.modules.ModuleContainerTFG;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import java.util.Collections;
import java.util.List;

@ModuleTFG(
        moduleID = ModuleContainerTFG.MODULE_ROCK,
        containerID = Tags.MOD_ID,
        name = "TFG Rock")
public class ModuleRock extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger("TFG ModuleRock");

    public static final CreativeTabs ROCK_TAB = new CreativeTabBase("rock", "wand", false);

    public ModuleRock() {
        this.enableAutoRegistry(ROCK_TAB);

    }

    @Override
    public void onRegister() {
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
