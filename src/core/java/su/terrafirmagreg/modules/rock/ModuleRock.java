package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Rock module."
)
public class ModuleRock extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleRock.class);


  public ModuleRock() {
    super("rock");

    enableFeature();

  }


  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
