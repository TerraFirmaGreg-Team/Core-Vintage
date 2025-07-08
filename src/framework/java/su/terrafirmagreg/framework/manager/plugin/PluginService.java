package su.terrafirmagreg.framework.manager.plugin;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginManager;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginService;
import su.terrafirmagreg.framework.module.api.IModule;

import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class PluginService implements IPluginService {

  private final IModule module;
  private final IPluginRegistrar registrar;
  private final PluginMap map;


  public PluginService(IPluginManager manager) {

    this.module = manager.getModule();
    this.registrar = manager.getRegistrar();
    this.map = manager.getMap();
  }

  @Override
  public void onPreInit() {

    fireEvent(IPluginEntry::onPreInit);
  }

  @Override
  public void onInit() {

    fireEvent(IPluginEntry::onInit);
  }

  @Override
  public void onPostInit() {

    fireEvent(IPluginEntry::onPostInit);
  }

  @Override
  public void onLoadComplete() {

    fireEvent(IPluginEntry::onLoadComplete);
  }

  @Override
  public void onServerAboutToStart() {

    fireEvent(IPluginEntry::onServerAboutToStart);
  }

  @Override
  public void onServerStarting() {

    fireEvent(IPluginEntry::onServerStarting);
  }

  @Override
  public void onServerStarted() {

    fireEvent(IPluginEntry::onServerStarted);
  }

  @Override
  public void onServerStopping() {

    fireEvent(IPluginEntry::onServerStopping);
  }

  @Override
  public void onServerStopped() {

    fireEvent(IPluginEntry::onServerStopped);
  }

  protected void fireEvent(Consumer<IPluginEntry> consumer) {

    this.map.values().forEach(consumer);
  }
}
