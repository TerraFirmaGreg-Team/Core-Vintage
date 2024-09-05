package su.terrafirmagreg.api.registry.spi;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public interface IRegistrySound
    extends IRegistryBase {

  /**
   * Registers a new sound with the game. The sound must also exist in the sounds.json file.
   *
   * @param name The name of the sound file. No upper case chars!
   */
  default SoundEvent sound(String name) {

    final ResourceLocation soundName = new ResourceLocation(this.getModID(), name);

    return sound(new SoundEvent(soundName), name);
  }

  default SoundEvent sound(SoundEvent sound, String name) {
    sound.setRegistryName(this.getModID(), name);

    this.getRegistry().getSounds().add(sound);

    return sound;
  }
}
