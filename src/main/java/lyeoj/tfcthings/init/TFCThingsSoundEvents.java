package lyeoj.tfcthings.init;

import su.terrafirmagreg.old.api.data.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class TFCThingsSoundEvents {

  public static final SoundEvent WHETSTONE_SHARPEN = new SoundEvent(new ResourceLocation(Reference.TFCTHINGS, "whetstone.sharpen")).setRegistryName("sharpen");

  public static final SoundEvent[] SOUND_EVENTS = {
    WHETSTONE_SHARPEN
  };
}
