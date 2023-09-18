package net.dries007.tfc.module.crop.api.variant.block;

import net.dries007.tfc.module.crop.common.blocks.BlockCropDead;
import net.dries007.tfc.module.crop.common.blocks.BlockCropGrowing;

import static net.dries007.tfc.module.crop.api.variant.block.CropBlockVariants.DEAD;
import static net.dries007.tfc.module.crop.api.variant.block.CropBlockVariants.GROWING;

public class CropBlockVariantHandler {
    public static void init() {
        GROWING = new CropBlockVariant("growing", BlockCropGrowing::new);
        DEAD = new CropBlockVariant("dead", BlockCropDead::new);
    }
}
