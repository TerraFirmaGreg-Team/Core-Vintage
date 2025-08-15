package su.terrafirmagreg.framework.manager.registry.base.sound.api;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.sound.api.ISoundEntry.SoundSettings;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import lombok.Getter;

public interface ISoundEntry extends IRegistryEntry<SoundSettings, SoundEvent> {

  @Getter
  class SoundSettings extends RegistrySettings<SoundSettings> {


    final ResourceLocation name;

    protected SoundSettings(ResourceLocation name) {

      this.name = name;
      this.registryKey(name.getPath());
    }

    public static SoundSettings of(ResourceLocation name) {
      return new SoundSettings(name);
    }

    public static SoundSettings of(String modId, String name) {
      return new SoundSettings(ModUtils.resource(modId, name));
    }

    public static SoundSettings of(String name) {
      return new SoundSettings(ModUtils.resource(name));
    }

  }
}
