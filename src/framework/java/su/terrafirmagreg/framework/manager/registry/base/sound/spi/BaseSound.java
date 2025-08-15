package su.terrafirmagreg.framework.manager.registry.base.sound.spi;

import su.terrafirmagreg.framework.manager.registry.base.sound.api.ISoundEntry;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import lombok.Getter;

@Getter
public class BaseSound extends SoundEvent implements ISoundEntry {

  public final SoundSettings settings;

  public BaseSound(SoundSettings settings) {
    super(settings.getName());

    this.settings = settings;
  }

  public BaseSound(ResourceLocation identifier) {
    this(SoundSettings.of(identifier));
  }

}
