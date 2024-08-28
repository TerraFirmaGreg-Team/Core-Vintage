package lyeoj.tfcthings.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;


import static su.terrafirmagreg.data.Constants.MODID_TFCTHINGS;

public class TFCThingsSoundEvents {

    public static final SoundEvent WHETSTONE_SHARPEN = new SoundEvent(new ResourceLocation(MODID_TFCTHINGS, "whetstone.sharpen")).setRegistryName(
            "sharpen");

    public static final SoundEvent[] SOUND_EVENTS = {
            WHETSTONE_SHARPEN
    };
}
