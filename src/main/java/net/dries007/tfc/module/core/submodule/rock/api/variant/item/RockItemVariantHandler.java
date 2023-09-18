package net.dries007.tfc.module.core.submodule.rock.api.variant.item;

import net.dries007.tfc.module.core.submodule.rock.common.items.ItemRockBrick;
import net.dries007.tfc.module.core.submodule.rock.common.items.ItemRockLoose;

import static net.dries007.tfc.module.core.submodule.rock.api.variant.item.RockItemVariants.BRICK;
import static net.dries007.tfc.module.core.submodule.rock.api.variant.item.RockItemVariants.LOOSE;

public class RockItemVariantHandler {

    public static void init() {
        LOOSE = new RockItemVariant("loose", ItemRockLoose::new);
        BRICK = new RockItemVariant("brick", ItemRockBrick::new);
    }
}
