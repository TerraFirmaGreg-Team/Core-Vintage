package su.terrafirmagreg.framework.manager.feature.api;

import su.terrafirmagreg.framework.module.spi.EventState;

public interface IFeatureService {

  // ===== FML Lifecycle

  void onPreInit(EventState.PreInitialization event);

  void onInit(EventState.Initialization event);

  void onPostInit(EventState.PostInitialization event);

  void onLoadComplete(EventState.LoadComplete event);

  // ===== FML Lifecycle: Server

  void onServerAboutToStart(EventState.ServerAboutToStart event);

  void onServerStarting(EventState.ServerStarting event);

  void onServerStarted(EventState.ServerStarted event);

  void onServerStopping(EventState.ServerStopping event);

  void onServerStopped(EventState.ServerStopped event);
}
