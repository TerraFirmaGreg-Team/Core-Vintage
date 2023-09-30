package net.dries007.tfc.module.rock.api.types.variant.item;

import net.dries007.tfc.module.rock.objects.items.ItemRockBrick;
import net.dries007.tfc.module.rock.objects.items.ItemRockLoose;

public class RockItemVariantHandler {

    public static void init() {
        RockItemVariants.LOOSE = new RockItemVariant("loose", ItemRockLoose::new);
        RockItemVariants.BRICK = new RockItemVariant("brick", ItemRockBrick::new);
    }
}
