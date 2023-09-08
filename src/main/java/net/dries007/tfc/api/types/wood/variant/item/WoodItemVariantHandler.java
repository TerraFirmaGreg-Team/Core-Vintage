package net.dries007.tfc.api.types.wood.variant.item;

import net.dries007.tfc.compat.dynamictrees.items.ItemWoodSeed;
import net.dries007.tfc.common.objects.items.wood.ItemWoodBoat;
import net.dries007.tfc.common.objects.items.wood.ItemWoodLumber;

import static net.dries007.tfc.api.types.wood.variant.item.WoodItemVariants.*;

public class WoodItemVariantHandler {
    public static void init() {

        BOAT = new WoodItemVariant("boat", ItemWoodBoat::new);
        LUMBER = new WoodItemVariant("lumber", ItemWoodLumber::new);
        SEED = new WoodItemVariant("seed", ItemWoodSeed::new);
    }
}
