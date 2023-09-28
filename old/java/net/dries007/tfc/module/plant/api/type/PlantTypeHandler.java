package net.dries007.tfc.module.plant.api.type;

import static net.dries007.tfc.module.plant.api.type.PlantTypes.*;
import static net.dries007.tfc.module.plant.api.variant.block.PlantEnumVariant.*;

public class PlantTypeHandler {

    public static void init() {
        ALLIUM = new PlantType.Builder("allium", STANDARD)
                .setStages(new int[]{6, 6, 7, 0, 1, 1, 2, 2, 3, 4, 5, 6})
                .setGrowthTemp(8f, 20f)
                .setTemp(-40f, 33f)
                .setRain(150f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        ATHYRIUM_FERN = new PlantType.Builder("athyrium_fern", STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setClayMarking()
                .setGrowthTemp(13f, 25f)
                .setTemp(-35f, 31f)
                .setRain(200f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        BARREL_CACTUS = new PlantType.Builder("barrel_cactus", CACTUS)
                .setStages(new int[]{0, 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 0})
                .setGrowthTemp(18f, 40f)
                .setTemp(-6f, 50f)
                .setRain(0f, 75f)
                .setSun(12, 15)
                .setMaxHeight(3)
                .setOreDictName("blockCactus")
                .build();

        BLACK_ORCHID = new PlantType.Builder("black_orchid", STANDARD)
                .setStages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
                .setGrowthTemp(20f, 35f)
                .setTemp(10f, 50f)
                .setRain(300f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        BLOOD_LILY = new PlantType.Builder("blood_lily", STANDARD)
                .setStages(new int[]{3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2})
                .setGrowthTemp(18f, 30f)
                .setTemp(10f, 50f)
                .setRain(200f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        BLUE_ORCHID = new PlantType.Builder("blue_orchid", STANDARD)
                .setStages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
                .setGrowthTemp(20f, 35f)
                .setTemp(10f, 50f)
                .setRain(300f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.9D)
                .build();

        BUTTERFLY_MILKWEED = new PlantType.Builder("butterfly_milkweed", STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
                .setGrowthTemp(18f, 24f)
                .setTemp(-40f, 32f)
                .setRain(75f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        CALENDULA = new PlantType.Builder("calendula", STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
                .setGrowthTemp(15f, 20f)
                .setTemp(-46f, 30f)
                .setRain(130f, 300f)
                .setSun(9, 15)
                .setMovementMod(1)
                .build();

        CANNA = new PlantType.Builder("canna", STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
                .setClayMarking()
                .setGrowthTemp(18f, 30f)
                .setTemp(-12f, 36f)
                .setRain(150f, 500f)
                .setSun(9, 15)
                .setMovementMod(1)
                .build();

        DANDELION = new PlantType.Builder("dandelion", STANDARD)
                .setStages(new int[]{9, 9, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8})
                .setGrowthTemp(10f, 25f)
                .setTemp(-40f, 40f)
                .setRain(75f, 400f)
                .setSun(10, 15)
                .setMovementMod(1)
                .build();

        DUCKWEED = new PlantType.Builder("duckweed", FLOATING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(11f, 20f)
                .setTemp(-34f, 38f)
                .setRain(0f, 500f)
                .setSun(4, 15)
                .setMovementMod(2)
                .build();

        FIELD_HORSETAIL = new PlantType.Builder("field_horsetail", STANDARD)
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

        FOUNTAIN_GRASS = new PlantType.Builder("fountain_grass", SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(15f, 35f)
                .setTemp(-12f, 40f)
                .setRain(75f, 150f)
                .setSun(12, 15)
                .setMovementMod(1)
                .build();

        FOXGLOVE = new PlantType.Builder("foxglove", TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 34f)
                .setRain(150f, 300f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        GOLDENROD = new PlantType.Builder("goldenrod", STANDARD)
                .setStages(new int[]{4, 4, 4, 0, 0, 0, 1, 2, 2, 2, 2, 3})
                .setClayMarking()
                .setGrowthTemp(15f, 23f)
                .setTemp(-29f, 32f)
                .setRain(75f, 300f)
                .setSun(9, 15)
                .setMovementMod(0.6D)
                .build();

        GRAPE_HYACINTH = new PlantType.Builder("grape_hyacinth", STANDARD)
                .setStages(new int[]{3, 3, 3, 0, 1, 1, 2, 3, 3, 3, 3, 3})
                .setGrowthTemp(4f, 18f)
                .setTemp(-34f, 32f)
                .setRain(150f, 250f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        GUZMANIA = new PlantType.Builder("guzmania", EPIPHYTE)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(0f, 40f)
                .setTemp(15f, 50f)
                .setRain(300f, 500f)
                .setSun(4, 11)
                .setMovementMod(0.9D)
                .build();

        HOUSTONIA = new PlantType.Builder("houstonia", STANDARD)
                .setStages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
                .setGrowthTemp(15f, 30f)
                .setTemp(-46f, 36f)
                .setRain(150f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        LABRADOR_TEA = new PlantType.Builder("labrador_tea", STANDARD)
                .setStages(new int[]{0, 0, 1, 2, 3, 4, 4, 5, 6, 0, 0, 0})
                .setGrowthTemp(1f, 25f)
                .setTemp(-50f, 33f)
                .setRain(300f, 500f)
                .setSun(10, 15)
                .setMovementMod(0.8D)
                .build();

        LADY_FERN = new PlantType.Builder("lady_fern", STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(13f, 25f)
                .setTemp(-34f, 32f)
                .setRain(200f, 500f)
                .setSun(9, 11)
                .setMovementMod(0.6D)
                .build();

        LICORICE_FERN = new PlantType.Builder("licorice_fern", EPIPHYTE)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(5f, 18f)
                .setTemp(-29f, 25f)
                .setRain(300f, 500f)
                .setSun(4, 11)
                .setMovementMod(0.7D)
                .build();

        LOTUS = new PlantType.Builder("lotus", FLOATING)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 0, 0})
                .setGrowthTemp(20f, 40f)
                .setTemp(10f, 50f)
                .setRain(0f, 500f)
                .setSun(4, 15)
                .setWaterDepth(1, 1)
                .setMovementMod(0.9D)
                .build();

        MEADS_MILKWEED = new PlantType.Builder("meads_milkweed", STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
                .setGrowthTemp(13f, 25f)
                .setTemp(-23f, 31f)
                .setRain(130f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        MORNING_GLORY = new PlantType.Builder("morning_glory", CREEPING)
                .setStages(new int[]{2, 2, 2, 0, 0, 1, 1, 1, 1, 1, 2, 2})
                .setGrowthTemp(15f, 30f)
                .setTemp(-40f, 31f)
                .setRain(150f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.9D)
                .build();

        MOSS = new PlantType.Builder("moss", CREEPING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(-2f, 15f)
                .setTemp(-7f, 36f)
                .setRain(250f, 500f)
                .setSun(0, 11)
                .setMovementMod(0.7D)
                .build();

        NASTURTIUM = new PlantType.Builder("nasturtium", STANDARD)
                .setStages(new int[]{4, 4, 4, 0, 1, 2, 2, 2, 2, 2, 3, 3})
                .setGrowthTemp(18f, 30f)
                .setTemp(-46f, 38f)
                .setRain(150f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        ORCHARD_GRASS = new PlantType.Builder("orchard_grass", SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(13f, 20f)
                .setTemp(-29f, 30f)
                .setRain(75f, 300f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        OSTRICH_FERN = new PlantType.Builder("ostrich_fern", TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 4, 0})
                .setGrowthTemp(10f, 18f)
                .setTemp(-40f, 33f)
                .setRain(300f, 500f)
                .setSun(4, 11)
                .setMaxHeight(2)
                .setMovementMod(0.6D)
                .build();

        OXEYE_DAISY = new PlantType.Builder("oxeye_daisy", STANDARD)
                .setStages(new int[]{5, 5, 5, 0, 1, 2, 3, 3, 3, 4, 4, 5})
                .setGrowthTemp(18f, 30f)
                .setTemp(-40f, 33f)
                .setRain(120f, 300f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        PAMPAS_GRASS = new PlantType.Builder("pampas_grass", TALL_GRASS)
                .setStages(new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1})
                .setClayMarking()
                .setGrowthTemp(20f, 30f)
                .setTemp(-12f, 36f)
                .setRain(75f, 200f)
                .setSun(9, 15)
                .setMaxHeight(3)
                .setMovementMod(0.6D)
                .build();

        PEROVSKIA = new PlantType.Builder("perovskia", DRY)
                .setStages(new int[]{5, 5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4})
                .setClayMarking()
                .setGrowthTemp(18f, 35f)
                .setTemp(-29f, 32f)
                .setRain(0f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();

        PISTIA = new PlantType.Builder("pistia", FLOATING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(20f, 40f)
                .setTemp(10f, 50f)
                .setRain(0f, 500f)
                .setSun(4, 15)
                .setWaterDepth(2, 32)
                .setMovementMod(0.8D)
                .build();

        POPPY = new PlantType.Builder("poppy", STANDARD)
                .setStages(new int[]{4, 4, 4, 0, 1, 2, 2, 3, 3, 3, 3, 4})
                .setGrowthTemp(17f, 30f)
                .setTemp(-40f, 36f)
                .setRain(150f, 250f)
                .setSun(12, 15)
                .setMovementMod(0.9D)
                .build();

        PORCINI = new PlantType.Builder("porcini", MUSHROOM)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(13f, 20f)
                .setTemp(0f, 30f)
                .setRain(300f, 500f)
                .setSun(0, 12)
                .setMovementMod(0.8D)
                .setOreDictName("mushroomBrown")
                .build();

        PRIMROSE = new PlantType.Builder("primrose", STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(150f, 300f)
                .setSun(9, 11)
                .setMovementMod(0.9D)
                .build();

        PULSATILLA = new PlantType.Builder("pulsatilla", STANDARD)
                .setStages(new int[]{0, 1, 2, 3, 3, 4, 5, 5, 5, 0, 0, 0})
                .setGrowthTemp(1f, 25f)
                .setTemp(-50f, 30f)
                .setRain(50f, 200f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        REINDEER_LICHEN = new PlantType.Builder("reindeer_lichen", CREEPING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(1f, 25f)
                .setTemp(-50f, 33f)
                .setRain(50f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.7D)
                .build();

        ROSE = new PlantType.Builder("rose", TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(11f, 21f)
                .setTemp(-29f, 34f)
                .setRain(150f, 300f)
                .setSun(9, 15)
                .setMaxHeight(2)
                .setMovementMod(0.9D)
                .build();

        ROUGH_HORSETAIL = new PlantType.Builder("rough_horsetail", REED)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(5f, 20f)
                .setTemp(-40f, 33f)
                .setRain(200f, 500f)
                .setSun(9, 15)
                .setMovementMod(0.7D)
                .setOreDictName("reed")
                .build();

        RYEGRASS = new PlantType.Builder("ryegrass", SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(10f, 18f)
                .setTemp(-46f, 32f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        SACRED_DATURA = new PlantType.Builder("sacred_datura", STANDARD)
                .setStages(new int[]{3, 3, 3, 0, 1, 2, 2, 2, 2, 2, 2, 2})
                .setGrowthTemp(20f, 30f)
                .setTemp(5f, 33f)
                .setRain(75f, 150f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        SAGEBRUSH = new PlantType.Builder("sagebrush", DRY)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0})
                .setGrowthTemp(18f, 35f)
                .setTemp(-34f, 50f)
                .setRain(0f, 100f)
                .setSun(12, 15)
                .setMovementMod(0.5D)
                .build();

        SAPPHIRE_TOWER = new PlantType.Builder("sapphire_tower", TALL_PLANT)
                .setStages(new int[]{2, 3, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2})
                .setGrowthTemp(21f, 31f)
                .setTemp(-6f, 38f)
                .setRain(75f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.6D)
                .build();

        SARGASSUM = new PlantType.Builder("sargassum", FLOATING_SEA)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(18f, 30f)
                .setTemp(0f, 38f)
                .setRain(0f, 500f)
                .setSun(12, 15)
                .setWaterDepth(5, 256)
                .setMovementMod(0.9D)
                .setOreDictName("seaweed")
                .build();

        SCUTCH_GRASS = new PlantType.Builder("scutch_grass", SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(18f, 35f)
                .setTemp(-17f, 50f)
                .setRain(150f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.7D)
                .build();

        SNAPDRAGON_PINK = new PlantType.Builder("snapdragon_pink", STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
                .setGrowthTemp(15f, 25f)
                .setTemp(-28f, 36f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        SNAPDRAGON_RED = new PlantType.Builder("snapdragon_red", STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
                .setGrowthTemp(15f, 25f)
                .setTemp(-28f, 36f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        SNAPDRAGON_WHITE = new PlantType.Builder("snapdragon_white", STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
                .setGrowthTemp(15f, 25f)
                .setTemp(-28f, 36f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        SNAPDRAGON_YELLOW = new PlantType.Builder("snapdragon_yellow", STANDARD)
                .setStages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
                .setGrowthTemp(15f, 25f)
                .setTemp(-28f, 36f)
                .setRain(150f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        SPANISH_MOSS = new PlantType.Builder("spanish_moss", HANGING)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setSwampPlant()
                .setGrowthTemp(20f, 40f)
                .setTemp(0f, 40f)
                .setRain(300f, 500f)
                .setSun(4, 15)
                .setMaxHeight(3)
                .setMovementMod(0.7D)
                .build();

        STRELITZIA = new PlantType.Builder("strelitzia", STANDARD)
                .setStages(new int[]{0, 0, 1, 1, 2, 2, 0, 0, 1, 1, 2, 2})
                .setGrowthTemp(20f, 40f)
                .setTemp(5f, 50f)
                .setRain(50f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        SWITCHGRASS = new PlantType.Builder("switchgrass", TALL_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0})
                .setGrowthTemp(13f, 25f)
                .setTemp(-29f, 32f)
                .setRain(100f, 300f)
                .setSun(9, 15)
                .setMaxHeight(2)
                .setMovementMod(0.7D)
                .build();

        SWORD_FERN = new PlantType.Builder("sword_fern", STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(18f, 25f)
                .setTemp(-40f, 30f)
                .setRain(100f, 500f)
                .setSun(4, 11)
                .setMovementMod(0.7D)
                .build();

        TALL_FESCUE_GRASS = new PlantType.Builder("tall_fescue_grass", TALL_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(15f, 25f)
                .setTemp(-29f, 30f)
                .setRain(300f, 500f)
                .setSun(12, 15)
                .setMaxHeight(2)
                .setMovementMod(0.5D)
                .build();

        TIMOTHY_GRASS = new PlantType.Builder("timothy_grass", SHORT_GRASS)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(15f, 25f)
                .setTemp(-46f, 30f)
                .setRain(300f, 500f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        TOQUILLA_PALM = new PlantType.Builder("toquilla_palm", TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(20f, 40f)
                .setTemp(10f, 50f)
                .setRain(250f, 500f)
                .setSun(9, 15)
                .setMaxHeight(2)
                .setMovementMod(0.4D)
                .build();

        TREE_FERN = new PlantType.Builder("tree_fern", TALL_PLANT)
                .setStages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
                .setGrowthTemp(20f, 40f)
                .setTemp(10f, 50f)
                .setRain(300f, 500f)
                .setSun(9, 15)
                .setMaxHeight(4)
                .setMovementMod(0D)
                .build();

        TRILLIUM = new PlantType.Builder("trillium", STANDARD)
                .setStages(new int[]{5, 5, 5, 0, 1, 2, 3, 3, 4, 4, 4, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(150f, 300f)
                .setSun(4, 11)
                .setMovementMod(0.8D)
                .build();

        TROPICAL_MILKWEED = new PlantType.Builder("tropical_milkweed", STANDARD)
                .setStages(new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3, 0})
                .setGrowthTemp(20f, 35f)
                .setTemp(-6f, 36f)
                .setRain(120f, 300f)
                .setSun(12, 15)
                .setMovementMod(0.8D)
                .build();

        TULIP_ORANGE = new PlantType.Builder("tulip_orange", STANDARD)
                .setStages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(100f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        TULIP_PINK = new PlantType.Builder("tulip_pink", STANDARD)
                .setStages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(100f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        TULIP_RED = new PlantType.Builder("tulip_red", STANDARD)
                .setStages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(100f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        TULIP_WHITE = new PlantType.Builder("tulip_white", STANDARD)
                .setStages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
                .setGrowthTemp(15f, 25f)
                .setTemp(-34f, 33f)
                .setRain(100f, 200f)
                .setSun(9, 15)
                .setMovementMod(0.9D)
                .build();

        VRIESEA = new PlantType.Builder("vriesea", EPIPHYTE)
                .setStages(new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0})
                .setGrowthTemp(0f, 40f)
                .setTemp(15f, 50f)
                .setRain(300f, 500f)
                .setSun(4, 11)
                .setMovementMod(0.8D)
                .build();

        WATER_CANNA = new PlantType.Builder("water_canna", FLOATING)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
                .setGrowthTemp(18f, 30f)
                .setTemp(-12f, 36f)
                .setRain(150f, 500f)
                .setSun(9, 15)
                .setWaterDepth(1, 1)
                .setMovementMod(0.8D)
                .build();

        WATER_LILY = new PlantType.Builder("water_lily", FLOATING)
                .setStages(new int[]{5, 5, 6, 0, 1, 2, 2, 2, 2, 3, 4, 5})
                .setGrowthTemp(15f, 30f)
                .setTemp(-34f, 38f)
                .setRain(0f, 500f)
                .setSun(4, 15)
                .setWaterDepth(1, 1)
                .setMovementMod(0.8D)
                .build();

        YUCCA = new PlantType.Builder("yucca", DESERT)
                .setStages(new int[]{0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 3})
                .setGrowthTemp(20f, 30f)
                .setTemp(-34f, 36f)
                .setRain(0f, 75f)
                .setSun(9, 15)
                .setMovementMod(0.8D)
                .build();
    }
}
