package su.terrafirmagreg.modules.wood;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.ModuleBase;
import su.terrafirmagreg.modules.wood.init.BlocksWood;
import su.terrafirmagreg.modules.wood.init.FeaturesWood;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Wood module."
)
public class ModuleWood extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleWood.class);

  public ModuleWood() {
    super("wood");

    enableRegistry();
    enableNetwork();
    enableFeature();
  }

  @Override
  public void onRegistryRegistrar(IRegistryRegistrar registrar) {
    registrar.group("log/aspen");

    BlocksWood.onRegister(registrar);

  }

  @Override
  public void onFeatureRegistrar(IFeatureRegistrar registrar) {

    FeaturesWood.onRegister(registrar);
  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
