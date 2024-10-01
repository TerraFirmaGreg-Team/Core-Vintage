package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypeHandler;
import su.terrafirmagreg.modules.rock.event.MaterialEventHandler;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;
import su.terrafirmagreg.modules.rock.plugin.top.TheOneProbeRock;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.modules.ModuleContainer.ROCK;

@Module(moduleID = ROCK)
public final class ModuleRock extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleRock.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;

  public ModuleRock() {
    TAB = BaseCreativeTab.of("rock", "rock/raw/basalt");
    REGISTRY = enableAutoRegistry(TAB);

    MinecraftForge.EVENT_BUS.register(new MaterialEventHandler());
  }


  @Override
  public void onInit(FMLInitializationEvent event) {

    TheOneProbeRock.init();
  }

  @Override
  public void onRegister() {
    RockTypeHandler.init();

    BlocksRock.onRegister(registryManager);
    ItemsRock.onRegister(registryManager);
  }

  @NotNull
  @Override
  public List<Class<?>> getEventBusSubscribers() {
    return Collections.singletonList(ModuleRock.class);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
