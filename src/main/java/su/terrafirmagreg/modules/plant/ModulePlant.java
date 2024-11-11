package su.terrafirmagreg.modules.plant;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.modules.plant.api.types.type.PlantTypeHandler;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.plant.init.ItemsPlant;

import net.minecraft.creativetab.CreativeTabs;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.ModuleContainer.PLANT;

@ModuleInfo(moduleID = PLANT)
public final class ModulePlant extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModulePlant.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;

  public ModulePlant() {
    TAB = BaseCreativeTab.of("flora", "plant/crop/seed/rice");
    REGISTRY = enableAutoRegistry(TAB);
  }

  @Override
  public void onRegister() {
    PlantTypeHandler.init();

    BlocksPlant.onRegister(registryManager);
    ItemsPlant.onRegister(registryManager);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
