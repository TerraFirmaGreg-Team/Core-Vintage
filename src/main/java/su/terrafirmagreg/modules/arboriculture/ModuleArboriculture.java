package su.terrafirmagreg.modules.arboriculture;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.modules.arboriculture.init.BlocksArboriculture;
import su.terrafirmagreg.modules.arboriculture.init.ItemsArboriculture;

import net.minecraft.creativetab.CreativeTabs;


import org.jetbrains.annotations.NotNull;

//@Module(moduleID = "Arboriculture", name = "TFG Module Arboriculture")
public final class ModuleArboriculture extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(
      ModuleArboriculture.class.getSimpleName());

  public final CreativeTabs ARBORICULTURE_TAB;

  public ModuleArboriculture() {
    this.ARBORICULTURE_TAB = BaseCreativeTab.of("arboriculture", "arboriculture/log/pine");
    this.enableAutoRegistry(ARBORICULTURE_TAB);

  }

  @Override
  public void onRegister() {
    BlocksArboriculture.onRegister(registryManager);
    ItemsArboriculture.onRegister(registryManager);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
