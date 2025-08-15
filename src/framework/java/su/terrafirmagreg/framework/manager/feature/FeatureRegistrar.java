package su.terrafirmagreg.framework.manager.feature;

import su.terrafirmagreg.framework.manager.feature.api.IFeatureEntry;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureManager;
import su.terrafirmagreg.framework.manager.feature.api.IFeatureRegistrar;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import net.minecraftforge.common.MinecraftForge;

import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class FeatureRegistrar implements IFeatureRegistrar {

  private final IModuleEntry module;
  private final Multimap<Class<?>, IFeatureEntry> mapEntry;

  public FeatureRegistrar(IFeatureManager manager) {

    this.module = manager.getModule();
    this.mapEntry = manager.getMapEntry();
  }

  @Override
  public <E extends IFeatureEntry> void addFeature(E entry) {
    var settings = entry.getSettings();

    if (settings.isHasSubscriptions()) {
      MinecraftForge.EVENT_BUS.register(entry.getClass());
    }

    addEntry(entry);
  }
}
