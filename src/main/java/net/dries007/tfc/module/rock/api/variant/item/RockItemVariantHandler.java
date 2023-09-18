package net.dries007.tfc.module.rock.api.variant.item;

import net.dries007.tfc.module.rock.common.items.ItemRockBrick;
import net.dries007.tfc.module.rock.common.items.ItemRockLoose;

public class RockItemVariantHandler {

    public static void init() {
        RockItemVariants.LOOSE = new RockItemVariant("loose", ItemRockLoose::new);
        RockItemVariants.BRICK = new RockItemVariant("brick", ItemRockBrick::new);
    }
}
