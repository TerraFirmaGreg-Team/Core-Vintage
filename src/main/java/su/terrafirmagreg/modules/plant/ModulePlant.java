package su.terrafirmagreg.modules.plant;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.plant.init.ItemsPlant;

import net.minecraft.creativetab.CreativeTabs;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.ModuleContainer.PLANT;

@Module(moduleID = PLANT)
public final class ModulePlant extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModulePlant.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;

  public ModulePlant() {
    TAB = BaseCreativeTab.of("plant", "plant/crop/seed/rice");
    REGISTRY = enableAutoRegistry(TAB);
  }

  @Override
  public void onRegister() {
    //PlantTypeHandler.init();

    BlocksPlant.onRegister(registryManager);
    ItemsPlant.onRegister(registryManager);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
