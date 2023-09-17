package net.dries007.tfc.module.core.submodule.soil;

import net.dries007.tfc.module.core.submodule.soil.api.variant.item.SoilItemVariantHandler;
import net.dries007.tfc.module.core.submodule.soil.api.type.SoilTypeHandler;
import net.dries007.tfc.module.core.submodule.soil.api.variant.block.SoilBlockVariantHandler;

public class ModuleSoil {
    public static void preInit() {
        SoilTypeHandler.init();
        SoilBlockVariantHandler.init();
        SoilItemVariantHandler.init();
    }

    public static void init() {
    }

    public static void postInit() {
    }
}
