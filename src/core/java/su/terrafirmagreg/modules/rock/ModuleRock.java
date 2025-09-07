package su.terrafirmagreg.modules.rock;

import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.BaseModule;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.FeatureRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;
import su.terrafirmagreg.modules.rock.init.PluginRock;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Rock module."
)
public class ModuleRock extends BaseModule {

  public static final FrameworkLogger LOGGER = FrameworkLogger.of(ModuleRock.class);


  public ModuleRock() {
    super(ModuleSettings.of()
      .registryKey("rock")
    );


  }

  @Override
  public void onRegistryRegistrar(IContentRegistrar registrar) {
    registrar.group("raw/basalt");

    BlocksRock.onRegister(registrar);
    ItemsRock.onRegister(registrar);


  }

  @Override
  public void onFeatureRegistrar(IFeatureRegistrar registrar) {

    FeatureRock.onRegister(registrar);
  }

  @Override
  public void onPluginRegistrar(IPluginRegistrar registrar) {

    PluginRock.onRegister(registrar);
  }

  @Override
  public @NotNull FrameworkLogger getLogger() {
    return LOGGER;
  }
}
