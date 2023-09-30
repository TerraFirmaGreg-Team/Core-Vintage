package net.dries007.tfc.module.wood.tree.type;

import com.ferreusveritas.dynamictrees.ModTrees;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenVine;
import net.dries007.tfc.module.wood.api.types.type.WoodTypes;
import net.dries007.tfc.world.classic.worldgen.WorldGenMound;

public class TreeTypeHandler {

    public static void init() {
        TreeTypes.ACACIA_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.ACACIA)
                .setTemp(19f, 31f).setRain(30f, 210f)
                .setHeight(12)
                .setMinGrowthTime(11)
                .setDensity(0.1f, 0.6f)
                .setParamMap(0.10f, 14f, 6, 6, 0.90f)
                .setCellKit("acacia")
                .build();

        TreeTypes.ASH_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.ASH)
                .setTemp(-6f, 12f).setRain(60f, 140f)
                .setParamMap(0.25f, 12f, 4, 3, 1.00f)
                .setCellKit("deciduous")
                .build();

        TreeTypes.ASPEN_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.ASPEN)
                .setTemp(-10f, 16f).setRain(10f, 80f)
                .setMinGrowthTime(8)
                .setParamMap(0.30f, 16f, 8, 2, 1.50f)
                .setGrowthLogicKit(ModTrees.CONIFER)
                .setCellKit("deciduous")
                .build();

        TreeTypes.BIRCH_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.BIRCH)
                .setTemp(-15f, 7f).setRain(20f, 180f)
                .setParamMap(0.25f, 12f, 5, 5, 1.15f)
                .setCellKit("deciduous")
                .build();

        TreeTypes.BLACKWOOD_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.BLACKWOOD)
                .setTemp(4f, 33f).setRain(0f, 120f)
                .setHeight(12).setMinGrowthTime(8)
                .setParamMap(0.20f, 13f, 3, 4, 0.90f)
                .setGrowthLogicKit(ModTrees.DARKOAK)
                .setCellKit("darkoak")
                .build();

        TreeTypes.CHESTNUT_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.CHESTNUT)
                .setTemp(11f, 35f).setRain(160f, 320f)
                .setParamMap(0.20f, 10f, 3, 3, 1.00f)
                .setGrowthLogicKit(ModTrees.DARKOAK)
                .setCellKit("deciduous")
                .build();

        TreeTypes.DOUGLAS_FIR_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.DOUGLAS_FIR)
                .setTemp(-2f, 14f).setRain(280f, 480f)
                .setDominance(5.2f).setHeight(16).setDensity(0.25f, 2f)
                .setParamMap(0.15f, 20f, 5, 3, 1.15f)
                .setGrowthLogicKit(ModTrees.CONIFER)
                .setCellKit("conifer")
                .setBushes()
                .setConifer()
                .build();

        TreeTypes.HICKORY_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.HICKORY)
                .setTemp(7f, 29f).setRain(80f, 250f)
                .setMinGrowthTime(10)
                .setParamMap(0.20f, 14f, 5, 3, 0.80f)
                .setGrowthLogicKit(ModTrees.DARKOAK)
                .setCellKit("deciduous")
                .build();

        TreeTypes.MAPLE_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.MAPLE)
                .setTemp(3f, 20f).setRain(140f, 360f)
                .setDominance(6.3f)
                .setParamMap(0.15f, 15f, 6, 3, 0.95f)
                .setCellKit("deciduous")
                .build();

        TreeTypes.OAK_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.OAK)
                .setTemp(-8f, 12f).setRain(180f, 430f)
                .setHeight(16).setMinGrowthTime(10)
                .setParamMap(0.30f, 16f, 3, 3, 0.85f)
                .setGrowthLogicKit(ModTrees.DARKOAK)
                .setCellKit("deciduous")
                .build();

        TreeTypes.PALM_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.PALM)
                .setTemp(16f, 35f).setRain(280f, 500f)
                .setDecayDist(6)
                .setParamMap(0.05f, 16f, 5, 4, 1.10f)
                .setGrowthLogicKit(ModTrees.JUNGLE)
                .setCellKit("palm")
                .build();

        TreeTypes.PINE_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.PINE)
                .setTemp(-15f, 7f).setRain(60f, 250f)
                .setDensity(0.1f, 0.8f)
                .setParamMap(0.20f, 18f, 6, 2, 1.20f)
                .setGrowthLogicKit(ModTrees.CONIFER)
                .setCellKit("conifer")
                .setConifer()
                .build();

        TreeTypes.ROSEWOOD_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.ROSEWOOD)
                .setTemp(8f, 18f).setRain(10f, 190f)
                .setHeight(12).setMinGrowthTime(8)
                .setParamMap(0.35f, 15f, 7, 3, 1.00f)
                .setCellKit("deciduous")
                .build();

        TreeTypes.SEQUOIA_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.SEQUOIA)
                .setTemp(-5f, 12f).setRain(250f, 420f)
                .setRadius(3).setHeight(24).setDecayDist(6).setMinGrowthTime(18).setDensity(0.4f, 0.9f)
                .setParamMap(0.20f, 36f, 9, 4, 0.70f)
                .setGrowthLogicKit(ModTrees.CONIFER)
                .setCellKit("conifer")
                .setConifer().setBushes()
                .setThick()
                .setGenFeature(new FeatureGenVine().setQuantity(8).setMaxLength(32).setRayDistance(32))
                .setGenFeature(new WorldGenMound(2))
                .build();

        TreeTypes.SPRUCE_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.SPRUCE)
                .setTemp(-11f, 6f).setRain(120f, 380f)
                .setDensity(0.1f, 0.8f)
                .setParamMap(0.15f, 12f, 6, 3, 1.10f)
                .setGrowthLogicKit(ModTrees.CONIFER)
                .setCellKit("conifer")
                .setConifer()
                .build();

        TreeTypes.SYCAMORE_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.SYCAMORE)
                .setTemp(17f, 33f).setRain(120f, 290f)
                .setMinGrowthTime(8).setDensity(0.25f, 2f)
                .setParamMap(0.20f, 10f, 4, 3, 0.90f)
                .setCellKit("deciduous")
                .setBushes()
                .build();

        TreeTypes.WHITE_CEDAR_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.WHITE_CEDAR)
                .setTemp(-8f, 17f).setRain(10f, 240f)
                .setHeight(16)
                .setParamMap(0.15f, 20f, 6, 2, 1.10f)
                .setGrowthLogicKit(ModTrees.CONIFER)
                .setCellKit("deciduous")
                .setConifer()
                .build();

        TreeTypes.WILLOW_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.WILLOW)
                .setTemp(15f, 32f).setRain(230f, 400f)
                .setMinGrowthTime(11).setDensity(0.7f, 2f)
                .setParamMap(0.55f, 15f, 2, 2, 1.40f)
                .setGrowthLogicKit(ModTrees.DARKOAK)
                .setCellKit("deciduous")
                .setBushes()
                .build();

        TreeTypes.KAPOK_TREE = new TreeType.Builder()
                .setWoodType(WoodTypes.KAPOK)
                .setTemp(15f, 35f).setRain(210f, 500f)
                .setDominance(8.5f).setRadius(3).setHeight(24).setDecayDist(6)
                .setMinGrowthTime(18).setDensity(0.6f, 2f)
                .setParamMap(0.10f, 30f, 7, 4, 0.85f)
                .setGrowthLogicKit(ModTrees.JUNGLE)
                .setCellKit("deciduous")
                .setBushes()
                .setThick()
                .setGenFeature(new FeatureGenVine().setQuantity(8).setMaxLength(32).setRayDistance(32))
                .build();


//        BANANA_TREE = new TreeType
//                .Builder("banana")
//                .setColor(0xBB9C7D).setTemp(23f, 35f).setRain(280f, 400f)
//                .setBurnInfo(720f, 1750)
//                .setHeight(5).setDecayDist(2)
//                .setFruitTree(FoodTypes.BANANA, 0.33f)
//                .setFlowerMonth(Month.APRIL, 2)
//                .setHarvestMonth(Month.SEPTEMBER, 1)
//                .build();

//
//        CHERRY_TREE = new TreeType
//                .Builder("cherry")
//                .setColor(0xDC5E2C).setTemp(5f, 21f).setRain(100f, 350f)
//                .setBurnInfo(720f, 1750)
//                .setHeight(5).setDecayDist(2)
//                .setFruitTree(FoodTypes.CHERRY, 0.33f)
//                .setFlowerMonth(Month.APRIL, 1)
//                .setHarvestMonth(Month.JUNE, 1)
//                .build();

//
//        GREEN_APPLE_TREE = new TreeType
//                .Builder("green_apple")
//                .setColor(0xCFC498).setTemp(8f, 25f).setRain(110f, 280f)
//                .setBurnInfo(720f, 1750)
//                .setHeight(5).setDecayDist(2)
//                .setFruitTree(FoodTypes.GREEN_APPLE, 0.33f)
//                .setFlowerMonth(Month.MAY, 2)
//                .setHarvestMonth(Month.OCTOBER, 2)
//                .build();

//
//        LEMON_TREE = new TreeType
//                .Builder("lemon")
//                .setColor(0xE8DD8C).setTemp(10f, 30f).setRain(180f, 400f)
//                .setBurnInfo(720f, 1750)
//                .setHeight(5).setDecayDist(2)
//                .setFruitTree(FoodTypes.LEMON, 0.33f)
//                .setFlowerMonth(Month.MAY, 2)
//                .setHarvestMonth(Month.AUGUST, 1)
//                .build();

//
//        OLIVE_TREE = new TreeType
//                .Builder("olive")
//                .setColor(0x97492B).setTemp(13f, 30f).setRain(150f, 380f)
//                .setBurnInfo(720f, 1750)
//                .setHeight(5).setDecayDist(2)
//                .setFruitTree(FoodTypes.OLIVE, 0.33f)
//                .setFlowerMonth(Month.JUNE, 1)
//                .setHarvestMonth(Month.OCTOBER, 2)
//                .build();

//
//        ORANGE_TREE = new TreeType
//                .Builder("orange")
//                .setColor(0xC18C52).setTemp(23f, 36f).setRain(250f, 400f)
//                .setBurnInfo(720f, 1750)
//                .setHeight(5).setDecayDist(2)
//                .setFruitTree(FoodTypes.ORANGE, 0.33f)
//                .setFlowerMonth(Month.FEBRUARY, 3)
//                .setHarvestMonth(Month.NOVEMBER, 1)
//                .build();

//
//        PEACH_TREE = new TreeType
//                .Builder("peach")
//                .setColor(0x9A5B37).setTemp(9f, 27f).setRain(60f, 230f)
//                .setBurnInfo(720f, 1750)
//                .setHeight(5).setDecayDist(2)
//                .setFruitTree(FoodTypes.PEACH, 0.33f)
//                .setFlowerMonth(Month.APRIL, 2)
//                .setHarvestMonth(Month.SEPTEMBER, 1)
//                .build();

//
//        PLUM_TREE = new TreeType
//                .Builder("plum")
//                .setColor(0x9F573B).setTemp(18f, 31f).setRain(250f, 400f)
//                .setBurnInfo(720f, 1750)
//                .setHeight(5).setDecayDist(2)
//                .setFruitTree(FoodTypes.PLUM, 0.33f)
//                .setFlowerMonth(Month.MAY, 2)
//                .setHarvestMonth(Month.JULY, 2)
//                .build();

//
//        RED_APPLE_TREE = new TreeType
//                .Builder("red_apple")
//                .setColor(0x6E150E).setTemp(9f, 25f).setRain(100f, 280f)
//                .setBurnInfo(720f, 1750)
//                .setHeight(5).setDecayDist(2)
//                .setFruitTree(FoodTypes.RED_APPLE, 0.33f)
//                .setFlowerMonth(Month.MAY, 2)
//                .setHarvestMonth(Month.OCTOBER, 2)
//                .build();

        // TODO доделать
//        HEVEA_TREE = new TreeType
//                .Builder("hevea")
//                .setColor(0x8B3929).setTemp(19f, 31f).setRain(30f, 210f)
//                .setBurnInfo(650f, 1000)
//                .setHeight(12).setMinGrowthTime(11).setDensity(0.1f, 0.6f)
//                .setGenerator(GEN_ACACIA)
//                .setParamMap(0.20f, 13f, 3, 7, 1.25f})
//                .setGrowthLogicKit("DarkOak"))
//                .setCellKit("deciduous"))
//                .build();

    }
}
