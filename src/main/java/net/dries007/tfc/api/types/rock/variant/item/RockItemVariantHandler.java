package net.dries007.tfc.api.types.rock.variant.item;

import net.dries007.tfc.common.objects.items.rock.ItemRockBrick;
import net.dries007.tfc.common.objects.items.rock.ItemRockLoose;

import static net.dries007.tfc.api.types.rock.variant.item.RockItemVariants.BRICK;
import static net.dries007.tfc.api.types.rock.variant.item.RockItemVariants.LOOSE;

public class RockItemVariantHandler {

    public static void init() {
        LOOSE = new RockItemVariant("loose", ItemRockLoose::new);
        BRICK = new RockItemVariant("brick", ItemRockBrick::new);
    }
}
