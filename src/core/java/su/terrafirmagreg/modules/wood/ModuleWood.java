package su.terrafirmagreg.modules.wood;

import su.terrafirmagreg.framework.FrameworkLogger;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.packet.api.IPacketRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.module.api.ModuleInfo;
import su.terrafirmagreg.framework.module.base.BaseModule;
import su.terrafirmagreg.modules.wood.init.BlocksWood;
import su.terrafirmagreg.modules.wood.init.EntitiesWood;
import su.terrafirmagreg.modules.wood.init.FeaturesWood;
import su.terrafirmagreg.modules.wood.init.ItemsWood;
import su.terrafirmagreg.modules.wood.init.PacketsWood;

import org.jetbrains.annotations.NotNull;

@ModuleInfo(
  author = "Xikaro",
  version = "1.0.0",
  description = "Wood module."
)
public class ModuleWood extends BaseModule {

  public static final FrameworkLogger LOGGER = FrameworkLogger.of(ModuleWood.class);

  public ModuleWood() {
    super(ModuleSettings.of()
      .registryKey("wood")
    );
  }

  @Override
  public void onRegistryRegistrar(IRegistryRegistrar registrar) {
    registrar.group("log/aspen");

    BlocksWood.onRegister(registrar);
    ItemsWood.onRegister(registrar);
    EntitiesWood.onRegister(registrar);

  }

  @Override
  public void onFeatureRegistrar(IFeatureRegistrar registrar) {

    FeaturesWood.onRegister(registrar);
  }

  @Override
  public void onPacketRegistrar(IPacketRegistrar registrar) {

    PacketsWood.onRegister(registrar);
  }

  @Override
  public @NotNull FrameworkLogger getLogger() {
    return LOGGER;
  }
}
