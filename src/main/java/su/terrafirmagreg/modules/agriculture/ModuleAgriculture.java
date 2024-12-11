package su.terrafirmagreg.modules.agriculture;

import su.terrafirmagreg.api.base.creativetab.BaseItemGroup;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleInfo;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.agriculture.api.types.berrybush.BerryBushTypeHandler;
import su.terrafirmagreg.modules.agriculture.init.BlocksAgriculture;
import su.terrafirmagreg.modules.agriculture.init.ItemsAgriculture;

import net.minecraft.creativetab.CreativeTabs;

import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.Modules.AGRICULTURE;

@ModuleInfo(moduleID = AGRICULTURE)
public final class ModuleAgriculture extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleAgriculture.class.getSimpleName());

  public static CreativeTabs TAB;
  public static RegistryManager REGISTRY;

  public ModuleAgriculture() {

    TAB = BaseItemGroup.of("agriculture", "agriculture/crop/seed/rice");
    REGISTRY = enableAutoRegistry(TAB);

    BerryBushTypeHandler.init();
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
