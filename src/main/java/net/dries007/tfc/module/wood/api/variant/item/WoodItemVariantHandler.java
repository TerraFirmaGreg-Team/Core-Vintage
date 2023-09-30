package net.dries007.tfc.module.wood.api.variant.item;

import net.dries007.tfc.module.wood.objects.items.ItemWoodBoat;
import net.dries007.tfc.module.wood.objects.items.ItemWoodLumber;

public class WoodItemVariantHandler {
    public static void init() {

        WoodItemVariants.BOAT = new WoodItemVariant("boat", ItemWoodBoat::new);
        WoodItemVariants.LUMBER = new WoodItemVariant("lumber", ItemWoodLumber::new);
    }
}
