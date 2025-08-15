package su.terrafirmagreg.framework.manager.plugin.api;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry.PluginSettings;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;

import lombok.Getter;


public interface IPluginEntry extends IBaseEntry<PluginSettings, BasePlugin> {


  @Getter
  class PluginSettings extends BaseSettings<PluginSettings> {

    String modRequired;
    boolean hasSubscriptions = true;


    public static PluginSettings of() {
      return new PluginSettings();
    }


    public PluginSettings modRequired(String modRequired) {
      this.modRequired = modRequired;
      this.enabled = enabled && ModUtils.isModLoaded(modRequired);
      return this.self();
    }

    public PluginSettings disableSubscriptions() {
      this.hasSubscriptions = false;
      return this.self();
    }


  }
}
