package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureManager;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureService;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraftforge.common.MinecraftForge;

import lombok.Getter;

@Getter
public class FeatureManager implements IFeatureManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(FeatureManager.class);

  private final IModule module;
  private final FeatureMap map;

  private final IFeatureRegistrar registrar;
  private final IFeatureService service;

  private FeatureManager(IModule module) {

    this.module = module;
    this.map = FeatureMap.of();

    this.registrar = new FeatureRegistrar(this);
    this.service = new FeatureService(this);

    MinecraftForge.EVENT_BUS.register(this.service);
  }

  public static synchronized IFeatureManager of(IModule module) {

    return MANAGER_MAP.computeIfAbsent(module, FeatureManager::new);
  }

  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
