package su.terrafirmagreg.modules.food;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;

@ModuleInfo(
  id = "food",
  author = "Xikaro",
  version = "1.0.0",
  description = "Food TerraFirmaGreg content."
)
public class ModuleFood extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleFood.class.getSimpleName());

  public ModuleFood() {
    enableRegistry();
  }

  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
