package su.terrafirmagreg.modules.metal;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  id = "metal",
  author = "Xikaro",
  version = "1.0.0"
)
public class ModuleMetal extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleMetal.class.getSimpleName());


  public ModuleMetal() {

  }

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
