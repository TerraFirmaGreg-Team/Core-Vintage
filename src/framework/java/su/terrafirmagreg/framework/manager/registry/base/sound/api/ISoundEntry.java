package su.terrafirmagreg.framework.manager.registry.base.sound.api;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.base.sound.api.ISoundEntry.SoundSettings;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

public interface ISoundEntry extends IRegistryEntry<SoundSettings, SoundEvent> {

  @Getter
  @NoArgsConstructor(access = AccessLevel.PROTECTED)
  class SoundSettings extends RegistrySettings<SoundSettings> {

    protected ResourceLocation name;

    public static SoundSettings of() {
      return new SoundSettings();
    }

    public SoundSettings name(ResourceLocation name) {
      this.name = name;
      return this.self();
    }

    public SoundSettings name(String modId, String name) {
      this.name = ModUtils.resource(modId, name);
      return this.self();
    }

    public SoundSettings name(String name) {
      this.name = ModUtils.resource(name);
      return this.self();
    }

  }
}
