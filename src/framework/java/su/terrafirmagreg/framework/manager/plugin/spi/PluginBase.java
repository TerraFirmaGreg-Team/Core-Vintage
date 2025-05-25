package su.terrafirmagreg.framework.manager.plugin.spi;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.plugin.api.IPlugin;

import lombok.Getter;

@Getter
public abstract class PluginBase implements IPlugin {

  private final String modRequired;

  public PluginBase(String modRequired) {

    this.modRequired = modRequired;
  }

  public boolean isEnabled() {
    return ModUtils.isModLoaded(modRequired);
  }

  public boolean hasSubscriptions() {
    return true;
  }

}
