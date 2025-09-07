package su.terrafirmagreg.framework.manager.content.api;

import su.terrafirmagreg.framework.manager.api.IBaseManager;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Map;

public interface IContentManager extends IBaseManager<IContentEntry<?, ?>> {

  Map<IModuleEntry, IContentManager> MANAGER_MAP = new Object2ObjectOpenHashMap<>();
  
  IContentRegistrar getRegistrar();


}
