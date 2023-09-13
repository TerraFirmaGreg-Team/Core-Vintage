package net.dries007.tfc.api.types.crop.variant.item;

import net.dries007.tfc.common.objects.items.crop.ItemCropSeed;

import static net.dries007.tfc.api.types.crop.variant.item.CropItemVariants.SEED;

public class CropItemVariantHandler {
    public static void init() {

        SEED = new CropItemVariant("seed", ItemCropSeed::new);
    }
}
