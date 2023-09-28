package net.dries007.tfc.module.agriculture.api.crop.variant.item;

import net.dries007.tfc.module.agriculture.common.items.ItemCropSeed;

public class CropItemVariantHandler {
    public static void init() {
        CropItemVariants.SEED = new CropItemVariant("seed", ItemCropSeed::new);
    }
}
