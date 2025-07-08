package su.terrafirmagreg.framework.manager.plugin.api;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.plugin.api.IPluginEntry.Settings;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;

import lombok.Getter;


public interface IPluginEntry extends IBaseEntry<Settings, BasePlugin> {

  // ===== FML Lifecycle

  default void onPreInit() {}

  default void onInit() {}

  default void onPostInit() {}

  default void onLoadComplete() {}

  // ===== FML Lifecycle: Server

  default void onServerAboutToStart() {}

  default void onServerStarting() {}

  default void onServerStarted() {}

  default void onServerStopping() {}

  default void onServerStopped() {}

  @Getter
  class Settings extends BaseSettings<Settings> {

    String modRequired;
    boolean enabled = true;
    boolean hasSubscriptions = true;

    protected Settings() {}

    public static Settings of() {
      return new Settings();
    }

    public Settings enabled(boolean enabled) {
      this.enabled = enabled;
      return this;
    }

    public Settings modRequired(String modRequired) {
      this.modRequired = modRequired;
      this.enabled = ModUtils.isModLoaded(modRequired);
      return this;
    }

    public Settings disableSubscriptions() {
      this.hasSubscriptions = false;
      return this;
    }


  }
}
