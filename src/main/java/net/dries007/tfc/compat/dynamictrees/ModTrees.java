package net.dries007.tfc.compat.dynamictrees;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.block.SoilBlockVariant;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;


public class ModTrees {


    public static void postInit() {
        for (var soil : SoilType.getSoilTypes()) {
            for (var variant : SoilBlockVariant.getSoilBlockVariants()) {
                var def = TFCBlocks.getSoilBlock(variant, soil).getDefaultState();
                if (TFCBlocks.isGrowableSoil(def)) {
                    DirtHelper.registerSoil(def.getBlock(), DirtHelper.DIRTLIKE);
                } else if (TFCBlocks.isSand(def)) {
                    DirtHelper.registerSoil(def.getBlock(), DirtHelper.SANDLIKE);
                } else if (TFCBlocks.isSoilOrGravel(def)) // soil caught above
                {
                    DirtHelper.registerSoil(def.getBlock(), DirtHelper.GRAVELLIKE);
                }
            }
        }
    }

}
