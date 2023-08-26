package net.dries007.tfc.api.types.crop.variant.block;

import net.dries007.tfc.common.objects.blocks.crop.BlockCropDead;
import net.dries007.tfc.common.objects.blocks.crop.BlockCropGrowing;

import static net.dries007.tfc.api.types.crop.variant.block.CropBlockVariants.DEAD;
import static net.dries007.tfc.api.types.crop.variant.block.CropBlockVariants.GROWING;

public class CropBlockVariantHandler {
    public static void init() {
        GROWING = new CropBlockVariant("growing", BlockCropGrowing::new);
        DEAD = new CropBlockVariant("dead", BlockCropDead::new);
    }
}
