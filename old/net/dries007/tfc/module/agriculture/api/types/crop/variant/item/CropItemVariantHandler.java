package net.dries007.tfc.module.agriculture.api.types.crop.variant.item;

import net.dries007.tfc.module.agriculture.objects.items.ItemCropSeed;

public class CropItemVariantHandler {
    public static void init() {
        CropItemVariants.SEED = new CropItemVariant("seed", ItemCropSeed::new);
    }
}
