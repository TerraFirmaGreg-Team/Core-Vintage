package su.terrafirmagreg.framework.manager.api;

import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import com.google.common.collect.Multimap;

@SuppressWarnings("rawtypes")
public interface IBaseManager<E extends IBaseEntry> {

  IModuleEntry getModule();

  Multimap<Class<?>, E> getMapEntry();

  LoggingHelper getLogger();

}
