package net.dries007.tfc.compat.dynamictrees;

import com.ferreusveritas.dynamictrees.api.TreeRegistry;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.growthlogic.ConiferLogic;
import com.ferreusveritas.dynamictrees.growthlogic.GrowthLogicKits;
import com.ferreusveritas.dynamictrees.growthlogic.IGrowthLogicKit;
import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenConiferTopper;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.compat.dynamictrees.trees.TreeFamilyTFC;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.wood.variant.WoodBlockVariants.SAPLING;


public class ModTrees {
    public static ArrayList<TreeFamilyTFC> tfcTrees = new ArrayList<>();
    public static Map<String, Species> tfcSpecies = new HashMap<>();

    public static void preInit() {
    }

    public static void registerBlocks(IForgeRegistry<Block> registry) {

        ArrayList<Block> treeBlocks = new ArrayList<>();

        Map<String, float[]> paramMap = new HashMap<>();
        Map<String, IGrowthLogicKit> logicMap = new HashMap<>();
        fillMaps(paramMap, logicMap);

        for (var t1 : WoodType.getWoodTypes()) {
            String treeName = t1.toString();

            var resLoc = TerraFirmaCraft.identifier(treeName);
            var family = new TreeFamilyTFC(resLoc, t1);

            tfcTrees.add(family);

            float[] map = paramMap.get(treeName) == null ? new float[]{0.20f, 10f, 3, 3, 1.00f} : paramMap.get(treeName);

            Species species = family.getCommonSpecies().setGrowthLogicKit(logicMap.get(treeName) == null ? GrowthLogicKits.nullLogic : logicMap.get(treeName)).
                    setBasicGrowingParameters(map[0], map[1], (int) map[2], (int) map[3], map[4]);

            tfcSpecies.put(treeName, species);
            Species.REGISTRY.register(species);
        }

        //Set up a map of species and their sapling types
        Map<String, Block> saplingMap = new HashMap<>();
        for (var type : WoodType.getWoodTypes()) {
            saplingMap.put(type.toString(), TFCBlocks.getWoodBlock(SAPLING, type));
        }


        for (Map.Entry<String, Species> entry : tfcSpecies.entrySet()) {
            TreeRegistry.registerSaplingReplacer(saplingMap.get(entry.getKey()).getDefaultState(), entry.getValue());
        }

        tfcTrees.forEach(t -> {
            String treeName = t.getName().getPath();
            ModBlocks.leafMap.get(treeName).setTree(t);
            Species species = tfcSpecies.get(treeName);
            species.setLeavesProperties(ModBlocks.leafMap.get(treeName));

            switch (treeName) {
                case "acacia" -> species.addAcceptableSoils(DirtHelper.HARDCLAYLIKE); //match base DT
                case "douglas_fir", "spruce", "pine", "sequoia", "white_cedar" -> {
                    species.addGenFeature(new FeatureGenConiferTopper(ModBlocks.leafMap.get(treeName)));
                    t.hasConiferVariants = true;
                }
            }
        });

        tfcTrees.forEach(tree -> tree.getRegisterableBlocks(treeBlocks));

        treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(MOD_ID).values());
        registry.registerAll(treeBlocks.toArray(new Block[0]));
    }


    public static void registerItems(IForgeRegistry<Item> registry) //has to wait until TFC Items have been registered
    {
//        WoodType.getWoodTypes().forEach(t -> {
//            String treeName = t.toString();
//            ((TreeFamilyTFC) tfcSpecies.get(treeName).getFamily()).setPrimitiveLog(TFCBlocks.getWoodBlock(LOG, t).getDefaultState());
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

    private static void fillMaps(Map<String, float[]> paramMap, Map<String, IGrowthLogicKit> logicMap) {
        paramMap.put("acacia", new float[]{0.10f, 14f, 6, 6, 0.90f});
        paramMap.put("ash", new float[]{0.25f, 12f, 4, 3, 1.00f});
        paramMap.put("aspen", new float[]{0.30f, 16f, 8, 2, 1.50f});
        paramMap.put("birch", new float[]{0.25f, 12f, 5, 5, 1.15f});
        paramMap.put("blackwood", new float[]{0.20f, 13f, 3, 4, 0.90f});
        paramMap.put("chestnut", new float[]{0.20f, 10f, 3, 3, 1.00f});
        paramMap.put("douglas_fir", new float[]{0.15f, 20f, 5, 3, 1.15f});
        paramMap.put("hickory", new float[]{0.20f, 14f, 5, 3, 0.80f});
        paramMap.put("kapok", new float[]{0.10f, 30f, 7, 4, 0.85f});
        paramMap.put("maple", new float[]{0.15f, 15f, 6, 3, 0.95f});
        paramMap.put("oak", new float[]{0.30f, 16f, 3, 3, 0.85f});
        paramMap.put("palm", new float[]{0.05f, 16f, 5, 4, 1.10f});
        paramMap.put("pine", new float[]{0.20f, 18f, 6, 2, 1.20f});
        paramMap.put("rosewood", new float[]{0.35f, 15f, 7, 3, 1.00f});
        paramMap.put("sequoia", new float[]{0.20f, 36f, 9, 4, 0.70f});
        paramMap.put("spruce", new float[]{0.15f, 12f, 6, 3, 1.10f});
        paramMap.put("sycamore", new float[]{0.20f, 10f, 4, 3, 0.90f});
        paramMap.put("white_cedar", new float[]{0.15f, 20f, 6, 2, 1.10f});
        paramMap.put("willow", new float[]{0.55f, 15f, 2, 2, 1.40f});
        //TFCTech
        paramMap.put("hevea", new float[]{0.20f, 13f, 3, 7, 1.25f});

        logicMap.put("acacia", GrowthLogicKits.nullLogic);
        logicMap.put("ash", GrowthLogicKits.nullLogic);
        logicMap.put("aspen", TreeRegistry.findGrowthLogicKit("Conifer"));
        logicMap.put("birch", GrowthLogicKits.nullLogic);
        logicMap.put("blackwood", TreeRegistry.findGrowthLogicKit("DarkOak"));
        logicMap.put("chestnut", TreeRegistry.findGrowthLogicKit("DarkOak"));
        logicMap.put("douglas_fir", TreeRegistry.findGrowthLogicKit("Conifer"));
        logicMap.put("hickory", TreeRegistry.findGrowthLogicKit("DarkOak"));
        logicMap.put("kapok", TreeRegistry.findGrowthLogicKit("Jungle"));
        logicMap.put("maple", GrowthLogicKits.nullLogic);
        logicMap.put("oak", TreeRegistry.findGrowthLogicKit("DarkOak"));
        logicMap.put("palm", TreeRegistry.findGrowthLogicKit("Jungle"));
        logicMap.put("pine", TreeRegistry.findGrowthLogicKit("Conifer"));
        logicMap.put("rosewood", GrowthLogicKits.nullLogic);
        logicMap.put("sequoia", new ConiferLogic(5.0f));
        logicMap.put("spruce", TreeRegistry.findGrowthLogicKit("Conifer"));
        logicMap.put("sycamore", GrowthLogicKits.nullLogic);
        logicMap.put("white_cedar", TreeRegistry.findGrowthLogicKit("Conifer"));
        logicMap.put("willow", TreeRegistry.findGrowthLogicKit("DarkOak"));
        //TFCTech
        logicMap.put("hevea", TreeRegistry.findGrowthLogicKit("DarkOak"));
    }

}
