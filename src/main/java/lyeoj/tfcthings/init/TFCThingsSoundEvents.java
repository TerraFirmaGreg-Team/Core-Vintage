package lyeoj.tfcthings.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import su.terrafirmagreg.api.data.enums.Mods;

public class TFCThingsSoundEvents {

  public static final SoundEvent WHETSTONE_SHARPEN = new SoundEvent(new ResourceLocation(Mods.Names.TFCTHINGS, "whetstone.sharpen")).setRegistryName("sharpen");

  public static final SoundEvent[] SOUND_EVENTS = {
    WHETSTONE_SHARPEN
  };
}
