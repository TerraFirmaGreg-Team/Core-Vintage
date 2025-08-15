package su.terrafirmagreg.modules.world;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.BaseModule;
import su.terrafirmagreg.modules.world.init.BiomesWorld;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "World module."
)
public class ModuleWorld extends BaseModule {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleWorld.class);

  public ModuleWorld() {
    super(ModuleSettings.of()
      .registryKey("world")
    );
  }

  @Override
  public void onRegistryRegistrar(IRegistryRegistrar registrar) {

    BiomesWorld.onRegister(registrar);
  }

  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
