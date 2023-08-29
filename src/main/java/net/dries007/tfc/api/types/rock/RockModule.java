package net.dries007.tfc.api.types.rock;

import net.dries007.tfc.api.types.rock.category.RockCategoryHandler;
import net.dries007.tfc.api.types.rock.type.RockTypeHandler;
import net.dries007.tfc.api.types.rock.variant.block.RockBlockVariantHandler;
import net.dries007.tfc.api.types.rock.variant.item.RockItemVariantHandler;

public class RockModule {

    public static void preInit() {

        RockCategoryHandler.init();
        RockTypeHandler.init();
        RockBlockVariantHandler.init();
        RockItemVariantHandler.init();
    }
}
