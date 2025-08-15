package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.BaseModule;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Rock module."
)
public class ModuleRock extends BaseModule {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleRock.class);


  public ModuleRock() {
    super(ModuleSettings.of()
      .registryKey("rock")
    );


  }


  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
