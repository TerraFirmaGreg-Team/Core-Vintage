package su.terrafirmagreg.modules.plant.api.types.type;

import su.terrafirmagreg.modules.plant.api.types.category.PlantCategories;

public class PlantTypeHandler {

  public static void init() {
    PlantTypes.ALLIUM = PlantType
      .builder("allium")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{6, 6, 7, 0, 1, 1, 2, 2, 3, 4, 5, 6})
      .growthTemp(8f, 20f)
      .temp(-40f, 33f)
      .rain(150f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.ATHYRIUM_FERN = PlantType
      .builder("athyrium_fern")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .isClayMarking()
      .growthTemp(13f, 25f)
      .temp(-35f, 31f)
      .rain(200f, 500f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.BARREL_CACTUS = PlantType
      .builder("barrel_cactus")
      .category(PlantCategories.CACTUS)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 0})
      .growthTemp(18f, 40f)
      .temp(-6f, 50f)
      .rain(0f, 75f)
      .sun(12, 15)
      .maxHeight(3)
      .addOreDict("blockCactus")
      .build();

    PlantTypes.BLACK_ORCHID = PlantType
      .builder("black_orchid")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
      .growthTemp(20f, 35f)
      .temp(10f, 50f)
      .rain(300f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.BLOOD_LILY = PlantType
      .builder("blood_lily")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2})
      .growthTemp(18f, 30f)
      .temp(10f, 50f)
      .rain(200f, 500f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.BLUE_ORCHID = PlantType
      .builder("blue_orchid")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
      .growthTemp(20f, 35f)
      .temp(10f, 50f)
      .rain(300f, 500f)
      .sun(12, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.BUTTERFLY_MILKWEED = PlantType
      .builder("butterfly_milkweed")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(18f, 24f)
      .temp(-40f, 32f)
      .rain(75f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.CALENDULA = PlantType
      .builder("calendula")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(15f, 20f)
      .temp(-46f, 30f)
      .rain(130f, 300f)
      .sun(9, 15)
      .movementMod(1)
      .build();

    PlantTypes.CANNA = PlantType
      .builder("canna")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
      .isClayMarking()
      .growthTemp(18f, 30f)
      .temp(-12f, 36f)
      .rain(150f, 500f)
      .sun(9, 15)
      .movementMod(1)
      .build();

    PlantTypes.DANDELION = PlantType
      .builder("dandelion")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{9, 9, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8})
      .growthTemp(10f, 25f)
      .temp(-40f, 40f)
      .rain(75f, 400f)
      .sun(10, 15)
      .movementMod(1)
      .build();

    PlantTypes.DUCKWEED = PlantType
      .builder("duckweed")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(11f, 20f)
      .temp(-34f, 38f)
      .rain(0f, 500f)
      .sun(4, 15)
      .movementMod(2)
      .build();

    PlantTypes.FIELD_HORSETAIL = PlantType
      .builder("field_horsetail")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1})
      .isClayMarking()
      .isSwampPlant()
      .growthTemp(5f, 20f)
      .temp(-40f, 33f)
      .rain(300f, 500f)
      .sun(9, 15)
      .movementMod(1)
      .addOreDict("reed")
      .build();

    PlantTypes.FOUNTAIN_GRASS = PlantType
      .builder("fountain_grass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 35f)
      .temp(-12f, 40f)
      .rain(75f, 150f)
      .sun(12, 15)
      .movementMod(1)
      .build();

    PlantTypes.FOXGLOVE = PlantType
      .builder("foxglove")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 34f)
      .rain(150f, 300f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.GOLDENROD = PlantType
      .builder("goldenrod")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{4, 4, 4, 0, 0, 0, 1, 2, 2, 2, 2, 3})
      .isClayMarking()
      .growthTemp(15f, 23f)
      .temp(-29f, 32f)
      .rain(75f, 300f)
      .sun(9, 15)
      .movementMod(0.6D)
      .build();

    PlantTypes.GRAPE_HYACINTH = PlantType
      .builder("grape_hyacinth")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{3, 3, 3, 0, 1, 1, 2, 3, 3, 3, 3, 3})
      .growthTemp(4f, 18f)
      .temp(-34f, 32f)
      .rain(150f, 250f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.GUZMANIA = PlantType
      .builder("guzmania")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(15f, 50f)
      .rain(300f, 500f)
      .sun(4, 11)
      .movementMod(0.9D)
      .build();

    PlantTypes.HOUSTONIA = PlantType
      .builder("houstonia")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
      .growthTemp(15f, 30f)
      .temp(-46f, 36f)
      .rain(150f, 500f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.LABRADOR_TEA = PlantType
      .builder("labrador_tea")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 1, 2, 3, 4, 4, 5, 6, 0, 0, 0})
      .growthTemp(1f, 25f)
      .temp(-50f, 33f)
      .rain(300f, 500f)
      .sun(10, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.LADY_FERN = PlantType
      .builder("lady_fern")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(13f, 25f)
      .temp(-34f, 32f)
      .rain(200f, 500f)
      .sun(9, 11)
      .movementMod(0.6D)
      .build();

    PlantTypes.LICORICE_FERN = PlantType
      .builder("licorice_fern")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(5f, 18f)
      .temp(-29f, 25f)
      .rain(300f, 500f)
      .sun(4, 11)
      .movementMod(0.7D)
      .build();

    PlantTypes.LOTUS = PlantType
      .builder("lotus")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(0f, 500f)
      .sun(4, 15)
      .waterDepth(1, 1)
      .movementMod(0.9D)
      .build();

    PlantTypes.MEADS_MILKWEED = PlantType
      .builder("meads_milkweed")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(13f, 25f)
      .temp(-23f, 31f)
      .rain(130f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.MORNING_GLORY = PlantType
      .builder("morning_glory")
      .category(PlantCategories.CREEPING)
      .stages(new int[]{2, 2, 2, 0, 0, 1, 1, 1, 1, 1, 2, 2})
      .growthTemp(15f, 30f)
      .temp(-40f, 31f)
      .rain(150f, 500f)
      .sun(12, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.MOSS = PlantType
      .builder("moss")
      .category(PlantCategories.CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-2f, 15f)
      .temp(-7f, 36f)
      .rain(250f, 500f)
      .sun(0, 11)
      .movementMod(0.7D)
      .build();

    PlantTypes.NASTURTIUM = PlantType
      .builder("nasturtium")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{4, 4, 4, 0, 1, 2, 2, 2, 2, 2, 3, 3})
      .growthTemp(18f, 30f)
      .temp(-46f, 38f)
      .rain(150f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.ORCHARD_GRASS = PlantType
      .builder("orchard_grass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(13f, 20f)
      .temp(-29f, 30f)
      .rain(75f, 300f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.OSTRICH_FERN = PlantType
      .builder("ostrich_fern")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 4, 0})
      .growthTemp(10f, 18f)
      .temp(-40f, 33f)
      .rain(300f, 500f)
      .sun(4, 11)
      .maxHeight(2)
      .movementMod(0.6D)
      .build();

    PlantTypes.OXEYE_DAISY = PlantType
      .builder("oxeye_daisy")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{5, 5, 5, 0, 1, 2, 3, 3, 3, 4, 4, 5})
      .growthTemp(18f, 30f)
      .temp(-40f, 33f)
      .rain(120f, 300f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.PAMPAS_GRASS = PlantType
      .builder("pampas_grass")
      .category(PlantCategories.TALL_GRASS)
      .stages(new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1})
      .isClayMarking()
      .growthTemp(20f, 30f)
      .temp(-12f, 36f)
      .rain(75f, 200f)
      .sun(9, 15)
      .maxHeight(3)
      .movementMod(0.6D)
      .build();

    PlantTypes.PEROVSKIA = PlantType
      .builder("perovskia")
      .category(PlantCategories.DRY)
      .stages(new int[]{5, 5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4})
      .isClayMarking()
      .growthTemp(18f, 35f)
      .temp(-29f, 32f)
      .rain(0f, 200f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.PISTIA = PlantType
      .builder("pistia")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(0f, 500f)
      .sun(4, 15)
      .waterDepth(2, 32)
      .movementMod(0.8D)
      .build();

    PlantTypes.POPPY = PlantType
      .builder("poppy")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{4, 4, 4, 0, 1, 2, 2, 3, 3, 3, 3, 4})
      .growthTemp(17f, 30f)
      .temp(-40f, 36f)
      .rain(150f, 250f)
      .sun(12, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.PORCINI = PlantType
      .builder("porcini")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(13f, 20f)
      .temp(0f, 30f)
      .rain(300f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroomBrown")
      .build();

    PlantTypes.PRIMROSE = PlantType
      .builder("primrose")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(150f, 300f)
      .sun(9, 11)
      .movementMod(0.9D)
      .build();

    PlantTypes.PULSATILLA = PlantType
      .builder("pulsatilla")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 1, 2, 3, 3, 4, 5, 5, 5, 0, 0, 0})
      .growthTemp(1f, 25f)
      .temp(-50f, 30f)
      .rain(50f, 200f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.REINDEER_LICHEN = PlantType
      .builder("reindeer_lichen")
      .category(PlantCategories.CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(1f, 25f)
      .temp(-50f, 33f)
      .rain(50f, 500f)
      .sun(9, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.ROSE = PlantType
      .builder("rose")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(11f, 21f)
      .temp(-29f, 34f)
      .rain(150f, 300f)
      .sun(9, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .build();

    PlantTypes.ROUGH_HORSETAIL = PlantType
      .builder("rough_horsetail")
      .category(PlantCategories.REED)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(5f, 20f)
      .temp(-40f, 33f)
      .rain(200f, 500f)
      .sun(9, 15)
      .movementMod(0.7D)
      .addOreDict("reed")
      .build();

    PlantTypes.RYEGRASS = PlantType
      .builder("ryegrass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0})
      .growthTemp(10f, 18f)
      .temp(-46f, 32f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.SACRED_DATURA = PlantType
      .builder("sacred_datura")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{3, 3, 3, 0, 1, 2, 2, 2, 2, 2, 2, 2})
      .growthTemp(20f, 30f)
      .temp(5f, 33f)
      .rain(75f, 150f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.SAGEBRUSH = PlantType
      .builder("sagebrush")
      .category(PlantCategories.DRY)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0})
      .growthTemp(18f, 35f)
      .temp(-34f, 50f)
      .rain(0f, 100f)
      .sun(12, 15)
      .movementMod(0.5D)
      .build();

    PlantTypes.SAPPHIRE_TOWER = PlantType
      .builder("sapphire_tower")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{2, 3, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2})
      .growthTemp(21f, 31f)
      .temp(-6f, 38f)
      .rain(75f, 200f)
      .sun(9, 15)
      .movementMod(0.6D)
      .build();

    PlantTypes.SARGASSUM = PlantType
      .builder("sargassum")
      .category(PlantCategories.FLOATING_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 30f)
      .temp(0f, 38f)
      .rain(0f, 500f)
      .sun(12, 15)
      .waterDepth(5, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.SCUTCH_GRASS = PlantType
      .builder("scutch_grass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 35f)
      .temp(-17f, 50f)
      .rain(150f, 500f)
      .sun(12, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.SNAPDRAGON_PINK = PlantType
      .builder("snapdragon_pink")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
      .growthTemp(15f, 25f)
      .temp(-28f, 36f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.SNAPDRAGON_RED = PlantType
      .builder("snapdragon_red")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
      .growthTemp(15f, 25f)
      .temp(-28f, 36f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.SNAPDRAGON_WHITE = PlantType
      .builder("snapdragon_white")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
      .growthTemp(15f, 25f)
      .temp(-28f, 36f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.SNAPDRAGON_YELLOW = PlantType
      .builder("snapdragon_yellow")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
      .growthTemp(15f, 25f)
      .temp(-28f, 36f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.SPANISH_MOSS = PlantType
      .builder("spanish_moss")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .isSwampPlant()
      .growthTemp(20f, 40f)
      .temp(0f, 40f)
      .rain(300f, 500f)
      .sun(4, 15)
      .maxHeight(3)
      .movementMod(0.7D)
      .build();

    PlantTypes.STRELITZIA = PlantType
      .builder("strelitzia")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 1, 1, 2, 2, 0, 0, 1, 1, 2, 2})
      .growthTemp(20f, 40f)
      .temp(5f, 50f)
      .rain(50f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.SWITCHGRASS = PlantType
      .builder("switchgrass")
      .category(PlantCategories.TALL_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0})
      .growthTemp(13f, 25f)
      .temp(-29f, 32f)
      .rain(100f, 300f)
      .sun(9, 15)
      .maxHeight(2)
      .movementMod(0.7D)
      .build();

    PlantTypes.SWORD_FERN = PlantType
      .builder("sword_fern")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 25f)
      .temp(-40f, 30f)
      .rain(100f, 500f)
      .sun(4, 11)
      .movementMod(0.7D)
      .build();

    PlantTypes.TALL_FESCUE_GRASS = PlantType
      .builder("tall_fescue_grass")
      .category(PlantCategories.TALL_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 25f)
      .temp(-29f, 30f)
      .rain(300f, 500f)
      .sun(12, 15)
      .maxHeight(2)
      .movementMod(0.5D)
      .build();

    PlantTypes.TIMOTHY_GRASS = PlantType
      .builder("timothy_grass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 25f)
      .temp(-46f, 30f)
      .rain(300f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.TOQUILLA_PALM = PlantType
      .builder("toquilla_palm")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(9, 15)
      .maxHeight(2)
      .movementMod(0.4D)
      .build();

    PlantTypes.TREE_FERN = PlantType
      .builder("tree_fern")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(300f, 500f)
      .sun(9, 15)
      .maxHeight(4)
      .movementMod(0D)
      .build();

    PlantTypes.TRILLIUM = PlantType
      .builder("trillium")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{5, 5, 5, 0, 1, 2, 3, 3, 4, 4, 4, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(150f, 300f)
      .sun(4, 11)
      .movementMod(0.8D)
      .build();

    PlantTypes.TROPICAL_MILKWEED = PlantType
      .builder("tropical_milkweed")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3, 0})
      .growthTemp(20f, 35f)
      .temp(-6f, 36f)
      .rain(120f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.TULIP_ORANGE = PlantType
      .builder("tulip_orange")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(100f, 200f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.TULIP_PINK = PlantType
      .builder("tulip_pink")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(100f, 200f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.TULIP_RED = PlantType
      .builder("tulip_red")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(100f, 200f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.TULIP_WHITE = PlantType
      .builder("tulip_white")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(100f, 200f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.VRIESEA = PlantType
      .builder("vriesea")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0})
      .growthTemp(0f, 40f)
      .temp(15f, 50f)
      .rain(300f, 500f)
      .sun(4, 11)
      .movementMod(0.8D)
      .build();

    PlantTypes.WATER_CANNA = PlantType
      .builder("water_canna")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
      .growthTemp(18f, 30f)
      .temp(-12f, 36f)
      .rain(150f, 500f)
      .sun(9, 15)
      .waterDepth(1, 1)
      .movementMod(0.8D)
      .build();

    PlantTypes.WATER_LILY = PlantType
      .builder("water_lily")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{5, 5, 6, 0, 1, 2, 2, 2, 2, 3, 4, 5})
      .growthTemp(15f, 30f)
      .temp(-34f, 38f)
      .rain(0f, 500f)
      .sun(4, 15)
      .waterDepth(1, 1)
      .movementMod(0.8D)
      .build();

    PlantTypes.YUCCA = PlantType
      .builder("yucca")
      .category(PlantCategories.DESERT)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 3})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();
  }
}
