package su.terrafirmagreg.modules.integration;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.spi.ModuleBase;

import net.minecraft.util.ResourceLocation;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.modules.ModulesContainer.INTEGRATION;

@ModuleInfo(
  moduleID = INTEGRATION,
  containerID = MOD_ID,
  name = "Integration",
  author = "Xikaro",
  version = "1.0.0"
)
public class ModuleIntegration extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(INTEGRATION);

  public ModuleIntegration() {}

  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }

  public abstract static class SubModule extends ModuleIntegration {

    @Override
    public @NotNull Set<ResourceLocation> getDependencyUids() {
      return Collections.singleton(ModUtils.resource(INTEGRATION));
    }
  }
}
