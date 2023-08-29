package net.dries007.tfc.compat.dynamictrees;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.block.SoilBlockVariant;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.registries.IForgeRegistry;


public class ModTrees {


    public static void registerBlocks(IForgeRegistry<Block> registry) {


    }


    public static void registerItems(IForgeRegistry<Item> registry) //has to wait until TFC Items have been registered
    {
//        WoodType.getWoodTypes().forEach(t -> {
//            String treeName = t.toString();
//            ((WoodTreeFamily) tfcSpecies.get(treeName).getFamily()).setPrimitiveLog(TFCBlocks.getWoodBlock(LOG, t).getDefaultState());
//        });
    }

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
        DirtHelper.registerSoil(FluidRegistry.getFluid("fresh_water").getBlock(), DirtHelper.WATERLIKE);
        DirtHelper.registerSoil(FluidRegistry.getFluid("salt_water").getBlock(), DirtHelper.WATERLIKE); // maybe?
        // "hot spring water" won't grow trees, I expect
    }

}
