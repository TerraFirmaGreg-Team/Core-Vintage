package su.terrafirmagreg.modules.animal.object.sound;

import su.terrafirmagreg.framework.manager.registry.base.sound.spi.BaseSound;

import net.minecraft.util.ResourceLocation;

public class SoundAnimal extends BaseSound {

  public SoundAnimal(ResourceLocation identifier) {
    super(SoundSettings.of(identifier));
  }
}
