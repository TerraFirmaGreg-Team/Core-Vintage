package su.terrafirmagreg.framework.manager.registry.base.sound.api;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.sound.api.ISoundEntry.Settings;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import lombok.Getter;

public interface ISoundEntry extends IRegistryEntry<Settings, SoundEvent> {

  @Getter
  class Settings extends BaseSettings<Settings> {


    final ResourceLocation name;

    protected Settings(ResourceLocation name) {

      this.name = name;
      this.registryKey = name.getPath();
    }

    public static Settings of(ResourceLocation name) {
      return new Settings(name);
    }

    public static Settings of(String modId, String name) {
      return new Settings(ModUtils.resource(modId, name));
    }

    public static Settings of(String name) {
      return new Settings(ModUtils.resource(name));
    }

  }
}
