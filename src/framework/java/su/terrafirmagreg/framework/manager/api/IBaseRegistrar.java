package su.terrafirmagreg.framework.manager.api;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

import net.minecraft.util.ResourceLocation;

import com.google.common.collect.Multimap;

public interface IBaseRegistrar<E extends IBaseEntry<?, ?>> {

  IModuleEntry getModule();

  Multimap<Class<?>, E> getMapEntry();

  default ResourceLocation getIdentifier(String identifier) {

    return ModUtils.resource(getModule().getIdentifier(), identifier);
  }

  default boolean validate(E entry) {
    var entryClass = entry.getClass();
    var settings = entry.getSettings();

    if (!settings.isEnabled()) {
      getModule().getLogger().debug("Entry {} is disabled: {}", entry.asClassEntry(), entryClass.getSimpleName());
      return false;
    }

    return true;
  }

  default void addEntry(E entry) {

    var settings = entry.getSettings();

    if (!validate(entry)) {
      getModule().getLogger().debug("Entry {} validation failed", entry.getClass().getSimpleName());
      return;
    }
    entry.setIdentifier(getIdentifier(settings.getRegistryKey()));
    getMapEntry().put(entry.asClassEntry(), entry);
    getModule().getLogger().info("Added entry {}: {}", entry.asClassEntry(), entry.getClass().getSimpleName());
  }

}
