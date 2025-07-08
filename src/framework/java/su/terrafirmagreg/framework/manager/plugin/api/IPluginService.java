package su.terrafirmagreg.framework.manager.plugin.api;

public interface IPluginService {

  // ===== FML Lifecycle

  void onPreInit();

  void onInit();

  void onPostInit();

  void onLoadComplete();

  // ===== FML Lifecycle: Server

  void onServerAboutToStart();

  void onServerStarting();

  void onServerStarted();

  void onServerStopping();

  void onServerStopped();
}
