package su.terrafirmagreg.modules.food;

import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.BaseModule;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Food TerraFirmaGreg content."
)
public class ModuleFood extends BaseModule {

  public static final FrameworkLogger LOGGER = FrameworkLogger.of(ModuleFood.class);

  public ModuleFood() {
    super(ModuleSettings.of()
      .registryKey("food")
    );
  }

  @Override
  public FrameworkLogger getLogger() {
    return LOGGER;
  }
}
