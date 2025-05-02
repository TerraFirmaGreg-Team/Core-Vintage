package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  id = "rock",
  author = "Xikaro",
  version = "1.0.0"
)
public final class ModuleRock extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleRock.class.getSimpleName());


  public ModuleRock() {

  }


  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
