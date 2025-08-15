package su.terrafirmagreg.framework.manager.plugin.base;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry;

import lombok.Getter;

@Getter
public abstract class BasePlugin implements IPluginEntry {

  private final PluginSettings settings;

  public BasePlugin() {
    this(PluginSettings.of());

  }

  public BasePlugin(PluginSettings settings) {

    this.settings = settings;
  }
}
