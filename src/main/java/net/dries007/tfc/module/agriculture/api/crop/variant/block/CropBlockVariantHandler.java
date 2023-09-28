package net.dries007.tfc.module.agriculture.api.crop.variant.block;

import net.dries007.tfc.module.agriculture.common.blocks.BlockCropDead;
import net.dries007.tfc.module.agriculture.common.blocks.BlockCropGrowing;

public class CropBlockVariantHandler {
    public static void init() {
        CropBlockVariants.GROWING = new CropBlockVariant("growing", BlockCropGrowing::new);
        CropBlockVariants.DEAD = new CropBlockVariant("dead", BlockCropDead::new);
    }
}
