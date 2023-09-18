package net.dries007.tfc.module.core.submodule.wood;

import net.dries007.tfc.module.core.submodule.wood.api.type.WoodTypeHandler;
import net.dries007.tfc.module.core.submodule.wood.api.variant.block.WoodBlockVariantHandler;
import net.dries007.tfc.module.core.submodule.wood.api.variant.item.WoodItemVariantHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class ModuleWood {

    public static void preInit() {
        WoodTypeHandler.init();
        WoodBlockVariantHandler.init();
        WoodItemVariantHandler.init();
    }

    public void init(FMLInitializationEvent event) {
    }

    public void postInit(FMLPostInitializationEvent event) {
    }
}
