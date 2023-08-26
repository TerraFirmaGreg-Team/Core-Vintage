package net.dries007.tfc.api.types.wood.variant.item;

import net.dries007.tfc.common.objects.items.wood.ItemWoodBoat;
import net.dries007.tfc.common.objects.items.wood.ItemWoodLumber;

import static net.dries007.tfc.api.types.wood.variant.item.WoodItemVariants.BOAT;
import static net.dries007.tfc.api.types.wood.variant.item.WoodItemVariants.LUMBER;

public class WoodItemVariantHandler {
    public static void init() {

        BOAT = new WoodItemVariant("boat", ItemWoodBoat::new);
        LUMBER = new WoodItemVariant("lumber", ItemWoodLumber::new);
    }
}
