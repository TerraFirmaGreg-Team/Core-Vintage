package su.terrafirmagreg.framework.manager.plugin.base;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry;

import lombok.Getter;

@Getter
public abstract class BasePlugin implements IPluginEntry {

  private final Settings settings;

  public BasePlugin() {
    this(Settings.of());

  }

  public BasePlugin(Settings settings) {

    this.settings = settings;
  }


}
