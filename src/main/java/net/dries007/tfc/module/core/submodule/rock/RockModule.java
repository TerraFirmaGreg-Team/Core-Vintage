package net.dries007.tfc.module.core.submodule.rock;

import net.dries007.tfc.module.core.submodule.rock.api.category.RockCategoryHandler;
import net.dries007.tfc.module.core.submodule.rock.api.type.RockTypeHandler;
import net.dries007.tfc.module.core.submodule.rock.api.variant.block.RockBlockVariantHandler;
import net.dries007.tfc.module.core.submodule.rock.api.variant.item.RockItemVariantHandler;

public class RockModule {

    public static void preInit() {
        RockCategoryHandler.init();
        RockTypeHandler.init();
        RockBlockVariantHandler.init();
        RockItemVariantHandler.init();
    }

    public static void init() {
    }

    public static void postInit() {
    }
}
