package su.terrafirmagreg.modules.metal;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.BaseModule;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Metal module."
)
public class ModuleMetal extends BaseModule {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleMetal.class);


  public ModuleMetal() {
    super("metal");

  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
