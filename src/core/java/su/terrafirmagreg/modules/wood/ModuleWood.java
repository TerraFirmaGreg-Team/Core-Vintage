package su.terrafirmagreg.modules.wood;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;

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
  public void onRegistry(IRegistryRegistrar registrar) {
    registrar.group("log/aspen");


  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
