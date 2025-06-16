package su.terrafirmagreg.modules.animal;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.ModuleBase;
import su.terrafirmagreg.modules.animal.init.BlocksAnimal;
import su.terrafirmagreg.modules.animal.init.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.init.FeaturesAnimal;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.init.LootTablesAnimal;
import su.terrafirmagreg.modules.animal.init.PluginsAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Animal module."
)
public class ModuleAnimal extends ModuleBase {

  public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleAnimal.class);

  public ModuleAnimal() {
    super("animal");

    enableRegistry();
    enableNetwork();
    enablePlugin();
    enableFeature();
  }


  @Override
  public void onRegistryRegistrar(IRegistryRegistrar registrar) {
    registrar.group("halter");

    BlocksAnimal.onRegister(registrar);
    ItemsAnimal.onRegister(registrar);
    EntitiesAnimal.onRegister(registrar);
    SoundsAnimal.onRegister(registrar);
    LootTablesAnimal.onRegister(registrar);
  }

  @Override
  public void onFeatureRegistrar(IFeatureRegistrar registrar) {

    FeaturesAnimal.onRegister(registrar);
  }

  @Override
  public void onPluginRegistrar(IPluginRegistrar registrar) {

    PluginsAnimal.onRegister(registrar);
  }


  @Override
  public @NotNull LoggingHelper getLogger() {
    return LOGGER;
  }
}
