package su.terrafirmagreg.modules.soil;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.ModuleBase;

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
  public void onRegistry(IRegistryRegistrar registrar) {
    registrar.group("grass/humus");


  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
