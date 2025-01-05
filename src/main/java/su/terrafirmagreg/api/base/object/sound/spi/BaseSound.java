package su.terrafirmagreg.api.base.object.sound.spi;

import su.terrafirmagreg.api.base.object.sound.api.ISoundSettings;

import net.minecraft.util.SoundEvent;

import lombok.Getter;

@Getter
public abstract class BaseSound extends SoundEvent implements ISoundSettings {

  public final Settings settings;

  public BaseSound(Settings settings) {
    super(settings.getName());

    this.settings = settings;
  }
}
