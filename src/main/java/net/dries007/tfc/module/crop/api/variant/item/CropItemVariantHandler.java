package net.dries007.tfc.module.crop.api.variant.item;

import net.dries007.tfc.module.crop.common.items.ItemCropSeed;

import static net.dries007.tfc.module.crop.api.variant.item.CropItemVariants.SEED;

public class CropItemVariantHandler {
    public static void init() {
        SEED = new CropItemVariant("seed", ItemCropSeed::new);
    }
}
