package net.dries007.tfc.module.core.submodule.wood.api.variant.item;

import net.dries007.tfc.module.core.submodule.wood.common.items.ItemWoodBoat;
import net.dries007.tfc.module.core.submodule.wood.common.items.ItemWoodLumber;

import static net.dries007.tfc.module.core.submodule.wood.api.variant.item.WoodItemVariants.*;

public class WoodItemVariantHandler {
    public static void init() {

        BOAT = new WoodItemVariant("boat", ItemWoodBoat::new);
        LUMBER = new WoodItemVariant("lumber", ItemWoodLumber::new);
    }
}
