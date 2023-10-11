package net.dries007.tfc.module.plant.api.types.type;

import static net.dries007.tfc.module.plant.api.types.type.PlantTypes.*;
import static net.dries007.tfc.module.plant.api.types.variant.block.PlantEnumVariant.*;

public class PlantTypeHandler {

    public static void init() {
        PlantTypes.ALLIUM = new PlantType.Builder("allium", PlantEnumVariant.STANDARD)
                .setStages(new int[]{6, 6, 7, 0, 1, 1, 2, 2, 3, 4, 5, 6})
                .setGrowthTemp(8f, 20f)
                .setTemp(-40f, 33f)
                .setRain(150f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.ATHYRIUM_FERN = new PlantType.Builder("athyrium_fern", PlantEnumVariant.STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setClayMarking()
                .setGrowthTemp(13f, 25f)
                .setTemp(-35f, 31f)
                .setRain(200f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.BARREL_CACTUS = new PlantType.Builder("barrel_cactus", PlantEnumVariant.CACTUS)
                .setStages(new int[]{0, 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 0})
                .setGrowthTemp(18f, 40f)
                .setTemp(-6f, 50f)
                .setRain(0f, 75f)
                .setSun(12, 15)
                .setMaxHeight(3)
                .setOreDictName("blockCactus")
                .build();

        PlantTypes.BLACK_ORCHID = new PlantType.Builder("black_orchid", PlantEnumVariant.STANDARD)
                .setStages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
                .setGrowthTemp(20f, 35f)
                .setTemp(10f, 50f)
                .setRain(300f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.BLOOD_LILY = new PlantType.Builder("blood_lily", PlantEnumVariant.STANDARD)
                .setStages(new int[]{3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2})
                .setGrowthTemp(18f, 30f)
                .setTemp(10f, 50f)
                .setRain(200f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.BLUE_ORCHID = new PlantType.Builder("blue_orchid", PlantEnumVariant.STANDARD)
                .setStages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
                .setGrowthTemp(20f, 35f)
                .setTemp(10f, 50f)
                .setRain(300f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.BUTTERFLY_MILKWEED = new PlantType.Builder("butterfly_milkweed", PlantEnumVariant.STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
                .setGrowthTemp(18f, 24f)
                .setTemp(-40f, 32f)
                .setRain(75f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.CALENDULA = new PlantType.Builder("calendula", PlantEnumVariant.STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
                .setGrowthTemp(15f, 20f)
                .setTemp(-46f, 30f)
                .setRain(130f, 300f)
                .setSun(9, 15)
                .setMovementMod(1)
                .build();

        PlantTypes.CANNA = new PlantType.Builder("canna", PlantEnumVariant.STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
                .setClayMarking()
                .setGrowthTemp(18f, 30f)
                .setTemp(-12f, 36f)
                .setRain(150f, 500f)
                .setSun(9, 15)
                .setMovementMod(1)
                .build();

        PlantTypes.DANDELION = new PlantType.Builder("dandelion", PlantEnumVariant.STANDARD)
                .setStages(new int[]{9, 9, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8})
                .setGrowthTemp(10f, 25f)
                .setTemp(-40f, 40f)
                .setRain(75f, 400f)
                .setSun(10, 15)
                .setMovementMod(1)
                .build();

        PlantTypes.DUCKWEED = new PlantType.Builder("duckweed", PlantEnumVariant.FLOATING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(11f, 20f)
                .setTemp(-34f, 38f)
                .setRain(0f, 500f)
                .setSun(4, 15)
                .setMovementMod(2)
                .build();

        PlantTypes.FIELD_HORSETAIL = new PlantType.Builder("field_horsetail", PlantEnumVariant.STANDARD)
                .setStages(new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1})
                .setClayMarking()
                .setSwampPlant()
                .setGrowthTemp(5f, 20f)
                .setTemp(-40f, 33f)
                .setRain(300f, 500f)
                .setSun(9, 15)
                .setMovementMod(1)
                .setOreDictName("reed")
                .build();

        PlantTypes.FOUNTAIN_GRASS = new PlantType.Builder("fountain_grass", PlantEnumVariant.SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(15f, 35f)
                .setTemp(-12f, 40f)
                .setRain(75f, 150f)
                .setSun(12, 15)
                .setMovementMod(1)
                .build();

        PlantTypes.FOXGLOVE = new PlantType.Builder("foxglove", PlantEnumVariant.TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 34f)
                .setRain(150f, 300f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.GOLDENROD = new PlantType.Builder("goldenrod", PlantEnumVariant.STANDARD)
                .setStages(new int[]{4, 4, 4, 0, 0, 0, 1, 2, 2, 2, 2, 3})
                .setClayMarking()
                .setGrowthTemp(15f, 23f)
                .setTemp(-29f, 32f)
                .setRain(75f, 300f)
                .setSun(9, 15)
                .setMovementMod(0.6D)
                .build();

        PlantTypes.GRAPE_HYACINTH = new PlantType.Builder("grape_hyacinth", PlantEnumVariant.STANDARD)
                .setStages(new int[]{3, 3, 3, 0, 1, 1, 2, 3, 3, 3, 3, 3})
                .setGrowthTemp(4f, 18f)
                .setTemp(-34f, 32f)
                .setRain(150f, 250f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.GUZMANIA = new PlantType.Builder("guzmania", PlantEnumVariant.EPIPHYTE)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(0f, 40f)
                .setTemp(15f, 50f)
                .setRain(300f, 500f)
                .setSun(4, 11)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.HOUSTONIA = new PlantType.Builder("houstonia", PlantEnumVariant.STANDARD)
                .setStages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
                .setGrowthTemp(15f, 30f)
                .setTemp(-46f, 36f)
                .setRain(150f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.LABRADOR_TEA = new PlantType.Builder("labrador_tea", PlantEnumVariant.STANDARD)
                .setStages(new int[]{0, 0, 1, 2, 3, 4, 4, 5, 6, 0, 0, 0})
                .setGrowthTemp(1f, 25f)
                .setTemp(-50f, 33f)
                .setRain(300f, 500f)
                .setSun(10, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.LADY_FERN = new PlantType.Builder("lady_fern", PlantEnumVariant.STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(13f, 25f)
                .setTemp(-34f, 32f)
                .setRain(200f, 500f)
                .setSun(9, 11)
                .setMovementMod(0.6D)
                .build();

        PlantTypes.LICORICE_FERN = new PlantType.Builder("licorice_fern", PlantEnumVariant.EPIPHYTE)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(5f, 18f)
                .setTemp(-29f, 25f)
                .setRain(300f, 500f)
                .setSun(4, 11)
                .setMovementMod(0.7D)
                .build();

        PlantTypes.LOTUS = new PlantType.Builder("lotus", PlantEnumVariant.FLOATING)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 0, 0})
                .setGrowthTemp(20f, 40f)
                .setTemp(10f, 50f)
                .setRain(0f, 500f)
                .setSun(4, 15)
                .setWaterDepth(1, 1)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.MEADS_MILKWEED = new PlantType.Builder("meads_milkweed", PlantEnumVariant.STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
                .setGrowthTemp(13f, 25f)
                .setTemp(-23f, 31f)
                .setRain(130f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.MORNING_GLORY = new PlantType.Builder("morning_glory", PlantEnumVariant.CREEPING)
                .setStages(new int[]{2, 2, 2, 0, 0, 1, 1, 1, 1, 1, 2, 2})
                .setGrowthTemp(15f, 30f)
                .setTemp(-40f, 31f)
                .setRain(150f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.MOSS = new PlantType.Builder("moss", PlantEnumVariant.CREEPING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(-2f, 15f)
                .setTemp(-7f, 36f)
                .setRain(250f, 500f)
                .setSun(0, 11)
                .setMovementMod(0.7D)
                .build();

        PlantTypes.NASTURTIUM = new PlantType.Builder("nasturtium", PlantEnumVariant.STANDARD)
                .setStages(new int[]{4, 4, 4, 0, 1, 2, 2, 2, 2, 2, 3, 3})
                .setGrowthTemp(18f, 30f)
                .setTemp(-46f, 38f)
                .setRain(150f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.ORCHARD_GRASS = new PlantType.Builder("orchard_grass", PlantEnumVariant.SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(13f, 20f)
                .setTemp(-29f, 30f)
                .setRain(75f, 300f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.OSTRICH_FERN = new PlantType.Builder("ostrich_fern", PlantEnumVariant.TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 4, 0})
                .setGrowthTemp(10f, 18f)
                .setTemp(-40f, 33f)
                .setRain(300f, 500f)
                .setSun(4, 11)
                .setMaxHeight(2)
                .setMovementMod(0.6D)
                .build();

        PlantTypes.OXEYE_DAISY = new PlantType.Builder("oxeye_daisy", PlantEnumVariant.STANDARD)
                .setStages(new int[]{5, 5, 5, 0, 1, 2, 3, 3, 3, 4, 4, 5})
                .setGrowthTemp(18f, 30f)
                .setTemp(-40f, 33f)
                .setRain(120f, 300f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.PAMPAS_GRASS = new PlantType.Builder("pampas_grass", PlantEnumVariant.TALL_GRASS)
                .setStages(new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1})
                .setClayMarking()
                .setGrowthTemp(20f, 30f)
                .setTemp(-12f, 36f)
                .setRain(75f, 200f)
                .setSun(9, 15)
                .setMaxHeight(3)
                .setMovementMod(0.6D)
                .build();

        PlantTypes.PEROVSKIA = new PlantType.Builder("perovskia", PlantEnumVariant.DRY)
                .setStages(new int[]{5, 5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4})
                .setClayMarking()
                .setGrowthTemp(18f, 35f)
                .setTemp(-29f, 32f)
                .setRain(0f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.PISTIA = new PlantType.Builder("pistia", PlantEnumVariant.FLOATING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(20f, 40f)
                .setTemp(10f, 50f)
                .setRain(0f, 500f)
                .setSun(4, 15)
                .setWaterDepth(2, 32)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.POPPY = new PlantType.Builder("poppy", PlantEnumVariant.STANDARD)
                .setStages(new int[]{4, 4, 4, 0, 1, 2, 2, 3, 3, 3, 3, 4})
                .setGrowthTemp(17f, 30f)
                .setTemp(-40f, 36f)
                .setRain(150f, 250f)
                .setSun(12, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.PORCINI = new PlantType.Builder("porcini", PlantEnumVariant.MUSHROOM)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(13f, 20f)
                .setTemp(0f, 30f)
                .setRain(300f, 500f)
                .setSun(0, 12)
                .setMovementMod(0.8D)
                .setOreDictName("mushroomBrown")
                .build();

        PlantTypes.PRIMROSE = new PlantType.Builder("primrose", PlantEnumVariant.STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(150f, 300f)
                .setSun(9, 11)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.PULSATILLA = new PlantType.Builder("pulsatilla", PlantEnumVariant.STANDARD)
                .setStages(new int[]{0, 1, 2, 3, 3, 4, 5, 5, 5, 0, 0, 0})
                .setGrowthTemp(1f, 25f)
                .setTemp(-50f, 30f)
                .setRain(50f, 200f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.REINDEER_LICHEN = new PlantType.Builder("reindeer_lichen", PlantEnumVariant.CREEPING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(1f, 25f)
                .setTemp(-50f, 33f)
                .setRain(50f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.7D)
                .build();

        PlantTypes.ROSE = new PlantType.Builder("rose", PlantEnumVariant.TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(11f, 21f)
                .setTemp(-29f, 34f)
                .setRain(150f, 300f)
                .setSun(9, 15)
                .setMaxHeight(2)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.ROUGH_HORSETAIL = new PlantType.Builder("rough_horsetail", PlantEnumVariant.REED)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(5f, 20f)
                .setTemp(-40f, 33f)
                .setRain(200f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.7D)
                .setOreDictName("reed")
                .build();

        PlantTypes.RYEGRASS = new PlantType.Builder("ryegrass", PlantEnumVariant.SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(10f, 18f)
                .setTemp(-46f, 32f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.SACRED_DATURA = new PlantType.Builder("sacred_datura", PlantEnumVariant.STANDARD)
                .setStages(new int[]{3, 3, 3, 0, 1, 2, 2, 2, 2, 2, 2, 2})
                .setGrowthTemp(20f, 30f)
                .setTemp(5f, 33f)
                .setRain(75f, 150f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.SAGEBRUSH = new PlantType.Builder("sagebrush", PlantEnumVariant.DRY)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0})
                .setGrowthTemp(18f, 35f)
                .setTemp(-34f, 50f)
                .setRain(0f, 100f)
                .setSun(12, 15)
                .setMovementMod(0.5D)
                .build();

        PlantTypes.SAPPHIRE_TOWER = new PlantType.Builder("sapphire_tower", PlantEnumVariant.TALL_PLANT)
                .setStages(new int[]{2, 3, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2})
                .setGrowthTemp(21f, 31f)
                .setTemp(-6f, 38f)
                .setRain(75f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.6D)
                .build();

        PlantTypes.SARGASSUM = new PlantType.Builder("sargassum", PlantEnumVariant.FLOATING_SEA)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(18f, 30f)
                .setTemp(0f, 38f)
                .setRain(0f, 500f)
                .setSun(12, 15)
                .setWaterDepth(5, 256)
                .setMovementMod(0.9D)
                .setOreDictName("seaweed")
                .build();

        PlantTypes.SCUTCH_GRASS = new PlantType.Builder("scutch_grass", PlantEnumVariant.SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(18f, 35f)
                .setTemp(-17f, 50f)
                .setRain(150f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.7D)
                .build();

        PlantTypes.SNAPDRAGON_PINK = new PlantType.Builder("snapdragon_pink", PlantEnumVariant.STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
                .setGrowthTemp(15f, 25f)
                .setTemp(-28f, 36f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.SNAPDRAGON_RED = new PlantType.Builder("snapdragon_red", PlantEnumVariant.STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
                .setGrowthTemp(15f, 25f)
                .setTemp(-28f, 36f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.SNAPDRAGON_WHITE = new PlantType.Builder("snapdragon_white", PlantEnumVariant.STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
                .setGrowthTemp(15f, 25f)
                .setTemp(-28f, 36f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.SNAPDRAGON_YELLOW = new PlantType.Builder("snapdragon_yellow", PlantEnumVariant.STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
                .setGrowthTemp(15f, 25f)
                .setTemp(-28f, 36f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.SPANISH_MOSS = new PlantType.Builder("spanish_moss", PlantEnumVariant.HANGING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setSwampPlant()
                .setGrowthTemp(20f, 40f)
                .setTemp(0f, 40f)
                .setRain(300f, 500f)
                .setSun(4, 15)
                .setMaxHeight(3)
                .setMovementMod(0.7D)
                .build();

        PlantTypes.STRELITZIA = new PlantType.Builder("strelitzia", PlantEnumVariant.STANDARD)
                .setStages(new int[]{0, 0, 1, 1, 2, 2, 0, 0, 1, 1, 2, 2})
                .setGrowthTemp(20f, 40f)
                .setTemp(5f, 50f)
                .setRain(50f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.SWITCHGRASS = new PlantType.Builder("switchgrass", PlantEnumVariant.TALL_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0})
                .setGrowthTemp(13f, 25f)
                .setTemp(-29f, 32f)
                .setRain(100f, 300f)
                .setSun(9, 15)
                .setMaxHeight(2)
                .setMovementMod(0.7D)
                .build();

        PlantTypes.SWORD_FERN = new PlantType.Builder("sword_fern", PlantEnumVariant.STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(18f, 25f)
                .setTemp(-40f, 30f)
                .setRain(100f, 500f)
                .setSun(4, 11)
                .setMovementMod(0.7D)
                .build();

        PlantTypes.TALL_FESCUE_GRASS = new PlantType.Builder("tall_fescue_grass", PlantEnumVariant.TALL_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(15f, 25f)
                .setTemp(-29f, 30f)
                .setRain(300f, 500f)
                .setSun(12, 15)
                .setMaxHeight(2)
                .setMovementMod(0.5D)
                .build();

        PlantTypes.TIMOTHY_GRASS = new PlantType.Builder("timothy_grass", PlantEnumVariant.SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(15f, 25f)
                .setTemp(-46f, 30f)
                .setRain(300f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.TOQUILLA_PALM = new PlantType.Builder("toquilla_palm", PlantEnumVariant.TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(20f, 40f)
                .setTemp(10f, 50f)
                .setRain(250f, 500f)
                .setSun(9, 15)
                .setMaxHeight(2)
                .setMovementMod(0.4D)
                .build();

        PlantTypes.TREE_FERN = new PlantType.Builder("tree_fern", PlantEnumVariant.TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(20f, 40f)
                .setTemp(10f, 50f)
                .setRain(300f, 500f)
                .setSun(9, 15)
                .setMaxHeight(4)
                .setMovementMod(0D)
                .build();

        PlantTypes.TRILLIUM = new PlantType.Builder("trillium", PlantEnumVariant.STANDARD)
                .setStages(new int[]{5, 5, 5, 0, 1, 2, 3, 3, 4, 4, 4, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(150f, 300f)
                .setSun(4, 11)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.TROPICAL_MILKWEED = new PlantType.Builder("tropical_milkweed", PlantEnumVariant.STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3, 0})
                .setGrowthTemp(20f, 35f)
                .setTemp(-6f, 36f)
                .setRain(120f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.TULIP_ORANGE = new PlantType.Builder("tulip_orange", PlantEnumVariant.STANDARD)
                .setStages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(100f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.TULIP_PINK = new PlantType.Builder("tulip_pink", PlantEnumVariant.STANDARD)
                .setStages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(100f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.TULIP_RED = new PlantType.Builder("tulip_red", PlantEnumVariant.STANDARD)
                .setStages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(100f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.TULIP_WHITE = new PlantType.Builder("tulip_white", PlantEnumVariant.STANDARD)
                .setStages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(100f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        PlantTypes.VRIESEA = new PlantType.Builder("vriesea", PlantEnumVariant.EPIPHYTE)
                .setStages(new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0})
                .setGrowthTemp(0f, 40f)
                .setTemp(15f, 50f)
                .setRain(300f, 500f)
                .setSun(4, 11)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.WATER_CANNA = new PlantType.Builder("water_canna", PlantEnumVariant.FLOATING)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
                .setGrowthTemp(18f, 30f)
                .setTemp(-12f, 36f)
                .setRain(150f, 500f)
                .setSun(9, 15)
                .setWaterDepth(1, 1)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.WATER_LILY = new PlantType.Builder("water_lily", PlantEnumVariant.FLOATING)
                .setStages(new int[]{5, 5, 6, 0, 1, 2, 2, 2, 2, 3, 4, 5})
                .setGrowthTemp(15f, 30f)
                .setTemp(-34f, 38f)
                .setRain(0f, 500f)
                .setSun(4, 15)
                .setWaterDepth(1, 1)
                .setMovementMod(0.8D)
                .build();

        PlantTypes.YUCCA = new PlantType.Builder("yucca", PlantEnumVariant.DESERT)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 3})
                .setGrowthTemp(20f, 30f)
                .setTemp(-34f, 36f)
                .setRain(0f, 75f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();
    }
}
