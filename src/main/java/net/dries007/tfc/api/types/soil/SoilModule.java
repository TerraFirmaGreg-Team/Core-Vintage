package net.dries007.tfc.api.types.soil;

import net.dries007.tfc.api.types.soil.type.SoilTypeHandler;
import net.dries007.tfc.api.types.soil.variant.block.SoilBlockVariantHandler;
import net.dries007.tfc.api.types.soil.variant.item.SoilItemVariantHandler;

public class SoilModule {

    public static void preInit() {
        SoilTypeHandler.init();
        SoilBlockVariantHandler.init();
        SoilItemVariantHandler.init();
    }
}
