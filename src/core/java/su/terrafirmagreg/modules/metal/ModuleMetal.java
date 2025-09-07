package su.terrafirmagreg.modules.metal;

import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.BaseModule;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Metal module."
)
public class ModuleMetal extends BaseModule {

  public static final FrameworkLogger LOGGER = FrameworkLogger.of(ModuleMetal.class);


  public ModuleMetal() {
    super(ModuleSettings.of()
      .registryKey("metal")
    );

  }

  @Override
  public @NotNull FrameworkLogger getLogger() {
    return LOGGER;
  }
}
