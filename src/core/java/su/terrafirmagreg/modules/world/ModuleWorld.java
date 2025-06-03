package su.terrafirmagreg.modules.world;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;
import su.terrafirmagreg.modules.world.init.BiomesWorld;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "World module."
)
public class ModuleWorld extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleWorld.class);

  public ModuleWorld() {
    super("world");

    enableRegistry();
    enableNetwork();
    enableFeature();
  }

  @Override
  public void onRegistry(IRegistryRegistrar registrar) {

    BiomesWorld.onRegister(registrar);
  }

  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
