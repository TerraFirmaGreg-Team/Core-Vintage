package su.terrafirmagreg.modules.plant;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.plant.init.ItemsPlant;

import net.minecraft.creativetab.CreativeTabs;

import org.jetbrains.annotations.NotNull;

//@Module(moduleID = "Agriculture", name = "TFG Module Agriculture")
public final class ModulePlant extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModulePlant.class.getSimpleName());

  public final CreativeTabs AGRICULTURE_TAB;

  public ModulePlant() {
    this.AGRICULTURE_TAB = BaseCreativeTab.of("flora", "flora/crop/seed/rice");

    this.enableAutoRegistry(AGRICULTURE_TAB);
  }

  @Override
  public void onRegister() {
    BlocksPlant.onRegister(registryManager);
    ItemsPlant.onRegister(registryManager);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
