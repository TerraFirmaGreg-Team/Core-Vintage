package net.dries007.tfc.api.types.crop.variant;

import net.dries007.tfc.common.objects.blocks.crop.BlockCrop;
import net.dries007.tfc.common.objects.blocks.crop.BlockCropDead;

import static net.dries007.tfc.api.types.crop.variant.CropBlockVariants.DEAD;
import static net.dries007.tfc.api.types.crop.variant.CropBlockVariants.GROWING;

public class CropBlockVariantHandler {
    public static void init() {
        GROWING = new CropBlockVariant("growing", BlockCrop::new);
        DEAD = new CropBlockVariant("dead", BlockCropDead::new);
    }
}
