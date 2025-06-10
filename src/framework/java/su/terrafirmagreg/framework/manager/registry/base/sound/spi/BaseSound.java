package su.terrafirmagreg.framework.manager.registry.base.sound.spi;

import su.terrafirmagreg.framework.manager.registry.base.sound.api.ISoundEntry;

import net.minecraft.util.SoundEvent;

import lombok.Getter;

@Getter
public abstract class BaseSound extends SoundEvent implements ISoundEntry {

  public final Settings settings;

  public BaseSound(Settings settings) {
    super(settings.getName());

    this.settings = settings;
  }
}
