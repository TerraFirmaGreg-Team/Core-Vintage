package su.terrafirmagreg.modules.soil;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.ModuleBase;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;
import su.terrafirmagreg.modules.soil.init.FeaturesSoil;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import org.jetbrains.annotations.NotNull;


@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Soil module."
)
public class ModuleSoil extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleSoil.class);

  public ModuleSoil() {
    super("soil");

    enableRegistry();
    enableNetwork();
    enableFeature();
  }

  @Override
  public void onRegistryRegistrar(IRegistryRegistrar registrar) {
    registrar.group("grass/humus");

    BlocksSoil.onRegister(registrar);
    ItemsSoil.onRegister(registrar);


  }

  @Override
  public void onFeatureRegistrar(IFeatureRegistrar registrar) {

    FeaturesSoil.onRegister(registrar);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
