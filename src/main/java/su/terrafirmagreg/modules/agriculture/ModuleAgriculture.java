package su.terrafirmagreg.modules.agriculture;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.agriculture.init.BlocksAgriculture;
import su.terrafirmagreg.modules.agriculture.init.ItemsAgriculture;

import net.minecraft.creativetab.CreativeTabs;

import org.jetbrains.annotations.NotNull;

//@Module(moduleID = "Agriculture", name = "TFG Module Agriculture")
public final class ModuleAgriculture extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(
    ModuleAgriculture.class.getSimpleName());

  public final CreativeTabs AGRICULTURE_TAB;

  public ModuleAgriculture() {

    this.AGRICULTURE_TAB = BaseCreativeTab.of("agriculture", "agriculture/crop/seed/rice");
    this.enableAutoRegistry(AGRICULTURE_TAB);

  }

  @Override
  public void onRegister() {
    BlocksAgriculture.onRegister(registryManager);
    ItemsAgriculture.onRegister(registryManager);
  }

  @NotNull
  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
