package net.dries007.tfc.api.types.rock.variant.item;

import net.dries007.tfc.common.objects.items.rock.ItemRock;
import net.dries007.tfc.common.objects.items.rock.ItemRockBrick;

import static net.dries007.tfc.api.types.rock.variant.item.RockItemVariants.BRICK;
import static net.dries007.tfc.api.types.rock.variant.item.RockItemVariants.ROCK;

public class RockItemVariantHandler {

    public static void init() {
        ROCK = new RockItemVariant("rock", ItemRock::new);
        BRICK = new RockItemVariant("brick", ItemRockBrick::new);
    }
}
