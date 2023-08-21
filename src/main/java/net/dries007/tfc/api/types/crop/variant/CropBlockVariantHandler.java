package net.dries007.tfc.api.types.crop.variant;

import net.dries007.tfc.common.objects.blocks.crop.BlockCropDead;
import net.dries007.tfc.common.objects.blocks.crop.BlockCropGrowing;

import static net.dries007.tfc.api.types.crop.variant.CropBlockVariants.DEAD;
import static net.dries007.tfc.api.types.crop.variant.CropBlockVariants.GROWING;

public class CropBlockVariantHandler {
    public static void init() {
        GROWING = new CropBlockVariant("growing", BlockCropGrowing::new);
        DEAD = new CropBlockVariant("dead", BlockCropDead::new);
    }
}
