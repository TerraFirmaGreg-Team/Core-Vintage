package su.terrafirmagreg.framework.module.base;

import su.terrafirmagreg.framework.module.api.IModuleEntry;

import lombok.Getter;

@Getter
public abstract class BaseModule implements IModuleEntry {

  protected final ModuleSettings settings;

  public BaseModule() {
    this(ModuleSettings.of());
  }

  public BaseModule(ModuleSettings settings) {
    this.settings = settings;
  }

}
