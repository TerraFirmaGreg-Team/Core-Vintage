package su.terrafirmagreg.framework.module;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.IModuleEntry;
import su.terrafirmagreg.framework.module.api.IModuleManager;
import su.terrafirmagreg.framework.module.api.IModuleRegistrar;

import lombok.Getter;

import java.util.Map;

@Getter
public class ModuleRegistrar implements IModuleRegistrar {

  private final String modId;
  private final Map<Class<?>, IModuleEntry> mapEntry;

  public ModuleRegistrar(IModuleManager manager) {
    this.modId = manager.getModId();
    this.mapEntry = manager.getMap();

  }

  private <E extends IModuleEntry> boolean validate(E entry) {
    var entryClass = entry.getClass();
    var settings = entry.getSettings();

    if (!settings.isEnabled()) {
      entry.getLogger().debug("Entry {} is disabled: {}", entry.asClassEntry(), entryClass.getSimpleName());
      return false;
    }

    return true;
  }

  @Override
  public <T extends IModuleEntry> void addModule(T entry) {

    var entryClass = entry.getClass();
    var settings = entry.getSettings();

    if (!validate(entry)) {
      entry.getLogger().info("Entry {} validation failed", entry.getClass().getSimpleName());
      return;
    }

    settings.identifier(ModUtils.resource(modId, settings.getRegistryKey()));
    getMapEntry().put(entryClass, entry);
    entry.getLogger().info("Added entry {}: {}", entry.asClassEntry(), entry.getClass().getSimpleName());
  }


}
