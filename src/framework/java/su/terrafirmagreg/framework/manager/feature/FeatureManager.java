package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureEntry;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureManager;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class FeatureManager implements IFeatureManager {

  public static final LoggingHelper LOGGER = LoggingHelper.of(FeatureManager.class);

  private final IModuleEntry module;
  private final Multimap<Class<?>, IFeatureEntry> mapEntry;

  private final IFeatureRegistrar registrar;

  private FeatureManager(IModuleEntry module) {

    this.module = module;
    this.mapEntry = LinkedHashMultimap.create();

    this.registrar = new FeatureRegistrar(this);

    MinecraftForge.EVENT_BUS.register(this);
  }

  public static synchronized IFeatureManager of(IModuleEntry module) {

    return MANAGER_MAP.computeIfAbsent(module, FeatureManager::new);
  }

  @Override
  public LoggingHelper getLogger() {
    return LOGGER;
  }
}
