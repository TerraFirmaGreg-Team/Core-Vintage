package su.terrafirmagreg.modules.plant.api.types.type;

import su.terrafirmagreg.modules.plant.api.types.category.PlantCategories;
import su.terrafirmagreg.modules.plant.api.types.category.PlantCategoryHandler;

public final class PlantTypeHandler {

  public static void init() {
    PlantCategoryHandler.init();

    PlantTypes.BASIL = PlantType
      .builder("basil")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0})
      .growthTemp(13.0f, 25.0f)
      .temp(13.0f, 25.0f)
      .rain(200.0F, 500.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.BAY_LAUREL = PlantType
      .builder("bay_laurel")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0})
      .growthTemp(5.0F, 18.0F)
      .temp(5.0F, 18.0F)
      .rain(200.0F, 500.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.CARDAMOM = PlantType
      .builder("cardamom")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(16.0F, 30.0F)
      .temp(16.0F, 30.0F)
      .rain(100.0F, 400.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.CILANTRO = PlantType
      .builder("cilantro")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0})
      .growthTemp(14.0F, 31.0F)
      .temp(14.0F, 31.0F)
      .rain(300.0F, 500.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.CUMIN = PlantType
      .builder("cumin")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0})
      .growthTemp(10.0F, 30.0F)
      .temp(10.0F, 30.0F)
      .rain(150.0F, 350.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.OREGANO = PlantType
      .builder("oregano")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0})
      .growthTemp(14.0F, 28.0F)
      .temp(14.0F, 28.0F)
      .rain(50.0F, 300.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.PIMENTO = PlantType
      .builder("pimento")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(10.0F, 18.0F)
      .temp(10.0F, 18.0F)
      .rain(100.0F, 300.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.VANILLA = PlantType
      .builder("vanilla")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0})
      .growthTemp(13.0F, 29.0F)
      .temp(13.0F, 29.0F)
      .rain(200.0F, 450.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

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
      .movementMod(0.8D)
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
      .movementMod(0.8D)
      .build();

    PlantTypes.DANDELION = PlantType
      .builder("dandelion")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{9, 9, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8})
      .growthTemp(10f, 25f)
      .temp(-40f, 40f)
      .rain(75f, 400f)
      .sun(10, 15)
      .movementMod(0.9D)
      .build();

    PlantTypes.DUCKWEED = PlantType
      .builder("duckweed")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(11f, 20f)
      .temp(-34f, 38f)
      .rain(0f, 500f)
      .sun(4, 15)
      .waterDepth(2, 32)
      .movementMod(0.8D)
      .build();

    PlantTypes.FIELD_HORSETAIL = PlantType
      .builder("field_horsetail")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1})
      .growthTemp(5f, 20f)
      .temp(-40f, 33f)
      .rain(300f, 500f)
      .sun(9, 15)
      .movementMod(0.7D)
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
      .movementMod(0.8D)
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

//    PlantTypes.PAMPAS_GRASS = PlantType
//      .builder("pampas_grass")
//      .category(PlantCategories.TALL_GRASS)
//      .stages(new int[]{1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1})
//      .isClayMarking()
//      .growthTemp(20f, 30f)
//      .temp(-12f, 36f)
//      .rain(75f, 200f)
//      .sun(9, 15)
//      .maxHeight(3)
//      .movementMod(0.6D)
//      .build();

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
      .isClayMarking()
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
      .maxHeight(2)
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

//    PlantTypes.SWITCHGRASS = PlantType
//      .builder("switchgrass")
//      .category(PlantCategories.TALL_GRASS)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0})
//      .growthTemp(13f, 25f)
//      .temp(-29f, 32f)
//      .rain(100f, 300f)
//      .sun(9, 15)
//      .maxHeight(2)
//      .movementMod(0.7D)
//      .build();

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

//    PlantTypes.TALL_FESCUE_GRASS = PlantType
//      .builder("tall_fescue_grass")
//      .category(PlantCategories.TALL_GRASS)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(15f, 25f)
//      .temp(-29f, 30f)
//      .rain(300f, 500f)
//      .sun(12, 15)
//      .maxHeight(2)
//      .movementMod(0.5D)
//      .build();

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
      .isClayMarking()
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

    PlantTypes.BAMBOO = PlantType
      .builder("bamboo")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(270f, 500f)
      .sun(6, 15)
      .maxHeight(12)
      .movementMod(0D)
      .addOreDict("bamboo")
      .build();

    PlantTypes.BROMELIA_HEMISPHERICA = PlantType
      .builder("bromelia_hemispherica")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(16f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.BROMELIA_LACINIOSA = PlantType
      .builder("bromelia_laciniosa")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(16f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.BUNCH_GRASS_FLOATING = PlantType
      .builder("bunch_grass_floating")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(-10f, 35f)
      .temp(-15f, 45f)
      .rain(100f, 400f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.7D)
      .addOreDict("reed")
      .build();

    PlantTypes.BUNCH_GRASS_REED = PlantType
      .builder("bunch_grass_reed")
      .category(PlantCategories.TALL_REED)
      .stages(new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0})
      .growthTemp(-10f, 35f)
      .temp(-15f, 45f)
      .rain(100f, 400f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.7D)
      .addOreDict("reed")
      .build();

    PlantTypes.BURNING_BUSH = PlantType
      .builder("burning_bush")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0})
      .growthTemp(11f, 21f)
      .temp(-29f, 34f)
      .rain(125f, 300f)
      .sun(6, 15)
      .movementMod(0.5D)
      .build();

    PlantTypes.CATTAIL = PlantType
      .builder("cattail")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{4, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4})
      .growthTemp(-16f, 28f)
      .temp(-36f, 40f)
      .rain(150f, 500f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.5D)
      .addOreDict("cattail")
      .build();

    PlantTypes.CAT_GRASS = PlantType
      .builder("cat_grass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(10f, 18f)
      .temp(-46f, 32f)
      .rain(150f, 300f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.CAVE_VINES = PlantType
      .builder("cave_vines")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-50f, 50f)
      .temp(-50f, 50f)
      .rain(0f, 500f)
      .sun(0, 5)
      .maxHeight(32)
      .movementMod(0.7D)
      .build();

    PlantTypes.CHAMOMILE = PlantType
      .builder("chamomile")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 0, 0})
      .growthTemp(5f, 25f)
      .temp(-40f, 40f)
      .rain(75f, 400f)
      .sun(7, 15)
      .movementMod(0.9D)
      .addOreDict("chamomile")
      .build();

    PlantTypes.CHAPARRAL_SHRUB = PlantType
      .builder("chaparral_shrub")
      .category(PlantCategories.DRY)
      .stages(new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0})
      .growthTemp(10f, 40f)
      .temp(0f, 60f)
      .rain(100f, 250f)
      .sun(0, 15)
      .movementMod(0.1D)
      .addOreDict("shrub")
      .build();

    PlantTypes.CINNAMON_FERN = PlantType
      .builder("cinnamon_fern")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 25f)
      .temp(-40f, 30f)
      .rain(190f, 500f)
      .sun(5, 15)
      .movementMod(0.5D)
      .build();

    PlantTypes.CORD_GRASS = PlantType
      .builder("cord_grass")
      .category(PlantCategories.TALL_REED)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(17f, 40f)
      .temp(0f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.5D)
      .build();

    PlantTypes.CROWNGRASS = PlantType
      .builder("crowngrass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 30f)
      .temp(-46f, 38f)
      .rain(150f, 500f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.DEVILS_TONGUE = PlantType
      .builder("devils_tongue")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(16f, 45f)
      .temp(10f, 50f)
      .rain(270f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("devils_tongue")
      .build();

//    PlantTypes.GLOW_VINES = PlantType
//      .builder("glow_vines")
//      .category(PlantCategories.HANGING)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(-50f, 50f)
//      .temp(-50f, 50f)
//      .rain(0f, 500f)
//      .sun(0, 5)
//      .maxHeight(32)
//      .movementMod(0.7D)
//      .build();

    PlantTypes.GOOSEGRASS = PlantType
      .builder("goosegrass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(13f, 20f)
      .temp(-29f, 30f)
      .rain(75f, 300f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.HALFA_GRASS = PlantType
      .builder("halfa_grass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(3f, 25f)
      .temp(-15f, 32f)
      .rain(100f, 150f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.HYDRANGEA = PlantType
      .builder("hydrangea")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(11f, 31f)
      .temp(-29f, 34f)
      .rain(125f, 300f)
      .sun(8, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("hydrangea")
      .build();

    PlantTypes.ICICLE = PlantType
      .builder("icicle")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-50f, 0f)
      .temp(-50f, 0f)
      .rain(0f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .build();

//    PlantTypes.IVY = PlantType
//      .builder("ivy")
//      .category(PlantCategories.CREEPING)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(-4f, 14f)
//      .temp(-7f, 17f)
//      .rain(90f, 470f)
//      .sun(0, 15)
//      .maxHeight(14)
//      .movementMod(0.5D)
//      .build();

    PlantTypes.JAPANESE_PIERIS = PlantType
      .builder("japanese_pieris")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 0, 0})
      .growthTemp(15f, 25f)
      .temp(-29f, 30f)
      .rain(220f, 500f)
      .sun(6, 15)
      .maxHeight(1)
      .movementMod(0.5D)
      .build();

    PlantTypes.KAIETEUR_FALLS = PlantType
      .builder("kaieteur_falls")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(16f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.8D)
      .build();

    PlantTypes.LAVANDULA = PlantType
      .builder("lavandula")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(5f, 35f)
      .temp(-29f, 40f)
      .rain(0f, 300f)
      .sun(7, 15)
      .movementMod(0.8D)
      .addOreDict("lavender")
      .build();

    PlantTypes.LEAF_LITTER = PlantType
      .builder("leaf_litter")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-20f, 50f)
      .temp(-30f, 50f)
      .rain(70f, 500f)
      .sun(0, 14)
      .movementMod(0.9D)
      .build();

    PlantTypes.LEYMUS = PlantType
      .builder("leymus")
      .category(PlantCategories.DRY_TALL_PLANT)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 35f)
      .temp(-12f, 40f)
      .rain(80f, 180f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.LILAC = PlantType
      .builder("lilac")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(11f, 31f)
      .temp(-29f, 34f)
      .rain(125f, 300f)
      .sun(8, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("lilac")
      .build();

    PlantTypes.LILY_OF_THE_VALLEY = PlantType
      .builder("lily_of_the_valley")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(5f, 35f)
      .temp(-29f, 40f)
      .rain(0f, 300f)
      .sun(8, 15)
      .movementMod(0.8D)
      .addOreDict("lily_of_the_valley")
      .build();

    PlantTypes.UNDERGROWTH_LOW = PlantType
      .builder("undergrowth_low")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 40f)
      .temp(-1f, 50f)
      .rain(250f, 500f)
      .sun(0, 14)
      .movementMod(0.9D)
      .build();

    PlantTypes.MARRAM_GRASS = PlantType
      .builder("marram_grass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 50f)
      .temp(-10f, 50f)
      .rain(70f, 120f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.MATTEUCCIA = PlantType
      .builder("matteuccia")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(0f, 50f)
      .rain(210f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.5D)
      .build();

    PlantTypes.OCOTILLO = PlantType
      .builder("ocotillo")
      .category(PlantCategories.DRY_TALL_PLANT)
      .stages(new int[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 0, 0, 0})
      .growthTemp(15f, 50f)
      .temp(-10f, 50f)
      .rain(70f, 90f)
      .sun(9, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("ocotillo")
      .build();

    PlantTypes.PAPYRUS = PlantType
      .builder("papyrus")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(17f, 40f)
      .temp(10f, 50f)
      .rain(150f, 400f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("papyrus")
      .build();

    PlantTypes.PEONY = PlantType
      .builder("peony")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(11f, 21f)
      .temp(-29f, 34f)
      .rain(125f, 300f)
      .sun(6, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("peony")
      .build();

    PlantTypes.PRAIRIE_JUNEGRASS = PlantType
      .builder("prairie_junegrass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0})
      .growthTemp(13f, 25f)
      .temp(-29f, 32f)
      .rain(75f, 200f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.REED_MANNAGRASS = PlantType
      .builder("reed_mannagrass")
      .category(PlantCategories.TALL_REED)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 40f)
      .temp(0f, 210f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    PlantTypes.RESIN = PlantType
      .builder("resin")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(10f, 45f)
      .temp(5f, 50f)
      .rain(50f, 500f)
      .sun(0, 14)
      .movementMod(0.0D)
      .addOreDict("resin")
      .build();

    PlantTypes.ROOTS = PlantType
      .builder("roots")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-50f, 50f)
      .temp(-50f, 50f)
      .rain(0f, 500f)
      .sun(0, 5)
      .maxHeight(2)
      .movementMod(0.9D)
      .build();

    PlantTypes.SEA_OATS = PlantType
      .builder("sea_oats")
      .category(PlantCategories.DESERT_TALL_PLANT)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(3f, 25f)
      .temp(-15f, 32f)
      .rain(100f, 300f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.7D)
      .build();

//    PlantTypes.SHRUB = PlantType
//      .builder("shrub")
//      .category(PlantCategories.TALL_GRASS)
//      .stages(new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0})
//      .growthTemp(-10f, 40f)
//      .temp(0f, 50f)
//      .rain(70f, 500f)
//      .sun(0, 15)
//      .movementMod(0.1D)
//      .build();

    PlantTypes.SUGAR_CANE = PlantType
      .builder("sugar_cane")
      .category(PlantCategories.TALL_REED)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 40f)
      .temp(10f, 40f)
      .rain(300f, 500f)
      .sun(0, 15)
      .maxHeight(3)
      .movementMod(0.5D)
      .addOreDict("sugarcane")
      .build();

    PlantTypes.SUNFLOWER = PlantType
      .builder("sunflower")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{9, 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9})
      .growthTemp(8f, 31f)
      .temp(-29f, 34f)
      .rain(100f, 300f)
      .sun(6, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("sunflower")
      .build();

    PlantTypes.TACKWEED = PlantType
      .builder("tackweed")
      .category(PlantCategories.CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-2f, 25f)
      .temp(-10f, 40f)
      .rain(250f, 500f)
      .sun(0, 14)
      .movementMod(0.7D)
      .addOreDict("moss")
      .build();

    PlantTypes.TAKAKIA = PlantType
      .builder("takakia")
      .category(PlantCategories.CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-2f, 25f)
      .temp(-10f, 40f)
      .rain(250f, 500f)
      .sun(0, 14)
      .movementMod(0.7D)
      .addOreDict("moss")
      .build();

    PlantTypes.UNDERGROWTH_SHRUB = PlantType
      .builder("undergrowth_shrub")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{4, 4, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4})
      .growthTemp(-10f, 40f)
      .temp(0f, 50f)
      .rain(70f, 500f)
      .sun(0, 15)
      .maxHeight(3)
      .movementMod(0.2D)
      .build();

    PlantTypes.UNDERGROWTH_SHRUB_SMALL = PlantType
      .builder("undergrowth_shrub_small")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{4, 4, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4})
      .growthTemp(-10f, 40f)
      .temp(0f, 50f)
      .rain(70f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.3D)
      .build();

    PlantTypes.VOODOO_LILY = PlantType
      .builder("voodoo_lily")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(16f, 45f)
      .temp(10f, 50f)
      .rain(270f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("voodoo_lily")
      .build();

    PlantTypes.WHEATGRASS = PlantType
      .builder("wheatgrass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(3f, 25f)
      .temp(-15f, 32f)
      .rain(100f, 300f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

//    PlantTypes.WILD_BARLEY = PlantType
//      .builder("wild_barley")
//      .category(PlantCategories.SHORT_GRASS)
//      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0})
//      .growthTemp(1f, 26f)
//      .temp(-2f, 33f)
//      .rain(70f, 310f)
//      .sun(0, 15)
//      .movementMod(0.7D)
//      .build();
//
//    PlantTypes.WILD_RICE = PlantType
//      .builder("wild_rice")
//      .category(PlantCategories.SHORT_GRASS)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0})
//      .isSwampPlant()
//      .growthTemp(22f, 40f)
//      .temp(20f, 45f)
//      .rain(300f, 450f)
//      .sun(0, 15)
//      .movementMod(0.7D)
//      .build();
//
//    PlantTypes.WILD_WHEAT = PlantType
//      .builder("wild_wheat")
//      .category(PlantCategories.SHORT_GRASS)
//      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0})
//      .growthTemp(0f, 30f)
//      .temp(-2f, 34f)
//      .rain(100f, 350f)
//      .sun(0, 15)
//      .movementMod(0.7D)
//      .build();

    PlantTypes.WOOLLY_BUSH = PlantType
      .builder("woolly_bush")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(5f, 33f)
      .rain(85f, 190f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

//    PlantTypes.SAWGRASS = PlantType
//      .builder("sawgrass")
//      .category(PlantCategories.TALL_GRASS)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(-16f, 32f)
//      .temp(-36f, 50f)
//      .rain(100f, 500f)
//      .sun(0, 15)
//      .maxHeight(2)
//      .waterDepth(1, 1)
//      .movementMod(0.7D)
//      .build();

    PlantTypes.APACHE_DWARF = PlantType
      .builder("apache_dwarf")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(5f, 50f)
      .rain(100f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.ARTISTS_CONK = PlantType
      .builder("artists_conk")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(-12f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("epiphyte_artists_conk")
      .build();

    PlantTypes.CLIMBING_CACTUS = PlantType
      .builder("climbing_cactus")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(11f, 31f)
      .temp(-29f, 34f)
      .rain(0f, 150f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.CRIMSON_CATTLEYA = PlantType
      .builder("crimson_cattleya")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(270f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.CREEPING_MISTLETOE = PlantType
      .builder("creeping_mistletoe")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(5f, 35f)
      .temp(-29f, 40f)
      .rain(0f, 300f)
      .sun(0, 15)
      .maxHeight(1)
      .movementMod(0.8D)
      .build();

    PlantTypes.CUTHBERTS_DENDROBIUM = PlantType
      .builder("cuthberts_dendrobium")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(270f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.FISH_BONE_CACTUS = PlantType
      .builder("fish_bone_cactus")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(130f, 300f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.FRAGRANT_FERN = PlantType
      .builder("fragrant_fern")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.HARLEQUIN_MISTLETOE = PlantType
      .builder("harlequin_mistletoe")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-12f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.KING_ORCHID = PlantType
      .builder("king_orchid")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.LANTERN_OF_THE_FOREST = PlantType
      .builder("lantern_of_the_forest")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.LARGE_FOOT_DENDROBIUM = PlantType
      .builder("large_foot_dendrobium")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.COMMON_MISTLETOE = PlantType
      .builder("common_mistletoe")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(5f, 35f)
      .temp(-29f, 40f)
      .rain(0f, 300f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.SKY_PLANT = PlantType
      .builder("sky_plant")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(130f, 300f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.SULPHUR_SHELF = PlantType
      .builder("sulphur_shelf")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(-12f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("epiphyte_sulphur_shelf")
      .build();

    PlantTypes.TAMPA_BUTTERFLY_ORCHID = PlantType
      .builder("tampa_butterfly_orchid")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.TURKEY_TAIL = PlantType
      .builder("turkey_tail")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(-12f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("epiphyte_turkey_tail")
      .build();

    PlantTypes.WILDFIRE = PlantType
      .builder("wildfire")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(130f, 300f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.AMANITA = PlantType
      .builder("amanita")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_red")
      .build();

    PlantTypes.BLACK_POWDERPUFF = PlantType
      .builder("black_powderpuff")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_black_powderpuff")
      .build();

    PlantTypes.CHANTERELLE = PlantType
      .builder("chanterelle")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_chanterelle")
      .build();

    PlantTypes.DEATH_CAP = PlantType
      .builder("death_cap")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_death_cap")
      .build();

    PlantTypes.GIANT_CLUB = PlantType
      .builder("giant_club")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_giant_club")
      .build();

    PlantTypes.PARASOL_MUSHROOM = PlantType
      .builder("parasol_mushroom")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_parasol")
      .build();

    PlantTypes.STINKHORN = PlantType
      .builder("stinkhorn")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_stinkhorn")
      .build();

    PlantTypes.WEEPING_MILK_CAP = PlantType
      .builder("weeping_milk_cap")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_weeping_milk_cap")
      .build();

    PlantTypes.WOOD_BLEWIT = PlantType
      .builder("wood_blewit")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_wood_blewit")
      .build();

    PlantTypes.WOOLLY_GOMPHUS = PlantType
      .builder("woolly_gomphus")
      .category(PlantCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_woolly_gomphus")
      .build();

//    PlantTypes.GLOWSHROOM = PlantType
//      .builder("glowshroom")
//      .category(PlantCategories.MUSHROOM)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(-11f, 48f)
//      .temp(-13f, 50f)
//      .rain(250f, 500f)
//      .sun(0, 5)
//      .movementMod(0.8D)
//      .build();

    PlantTypes.BELL_TREE_DAHLIA = PlantType
      .builder("bell_tree_dahlia")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-12f, 36f)
      .rain(75f, 200f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.BIG_LEAF_PALM = PlantType
      .builder("big_leaf_palm")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.DRAKENSBERG_CYCAD = PlantType
      .builder("drakensberg_cycad")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.DWARF_SUGAR_PALM = PlantType
      .builder("dwarf_sugar_palm")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(300f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.GIANT_CANE = PlantType
      .builder("giant_cane")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 30f)
      .temp(-12f, 36f)
      .rain(150f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .addOreDict("sugarcane")
      .build();

    PlantTypes.GIANT_ELEPHANT_EAR = PlantType
      .builder("giant_elephant_ear")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.GIANT_FEATHER_GRASS = PlantType
      .builder("giant_feather_grass")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(15f, 23f)
      .temp(-29f, 32f)
      .rain(75f, 300f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.MADAGASCAR_OCOTILLO = PlantType
      .builder("madagascar_ocotillo")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(0, 15)
      .maxHeight(3)
      .movementMod(0.2D)
      .build();

    PlantTypes.MALAGASY_TREE_ALOE = PlantType
      .builder("malagasy_tree_aloe")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.MOUNTAIN_CABBAGE_TREE = PlantType
      .builder("mountain_cabbage_tree")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.PYGMY_DATE_PALM = PlantType
      .builder("pygmy_date_palm")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.QUEEN_SAGO = PlantType
      .builder("queen_sago")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.RED_SEALING_WAX_PALM = PlantType
      .builder("red_sealing_wax_palm")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.SUMMER_ASPHODEL = PlantType
      .builder("summer_asphodel")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(150f, 300f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.ZIMBABWE_ALOE = PlantType
      .builder("zimbabwe_aloe")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    PlantTypes.ANTHURIUM = PlantType
      .builder("anthurium")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1})
      .growthTemp(12f, 40f)
      .temp(-3f, 50f)
      .rain(290f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("anthurium")
      .build();

    PlantTypes.ARROWHEAD = PlantType
      .builder("arrowhead")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-10f, 22f)
      .temp(-25f, 37f)
      .rain(180f, 500f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.6D)
      .addOreDict("arrowhead")
      .build();

    PlantTypes.ARUNDO = PlantType
      .builder("arundo")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 30f)
      .temp(0f, 45f)
      .rain(120f, 500f)
      .sun(0, 15)
      .maxHeight(8)
      .movementMod(0.5D)
      .addOreDict("arundo")
      .build();

//    PlantTypes.AZURE_BLUET = PlantType
//      .builder("azure_bluet")
//      .category(PlantCategories.STANDARD)
//      .stages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
//      .growthTemp(-12f, 10f)
//      .temp(-27f, 25f)
//      .rain(150f, 500f)
//      .sun(0, 15)
//      .movementMod(0.9D)
//      .addOreDict("azure_bluet")
//      .build();

    PlantTypes.BLUEGRASS = PlantType
      .builder("bluegrass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0})
      .growthTemp(-4f, 12f)
      .temp(-19f, 27f)
      .rain(110f, 280f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    PlantTypes.BLUE_GINGER = PlantType
      .builder("blue_ginger")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1})
      .growthTemp(16f, 26f)
      .temp(1f, 41f)
      .rain(300f, 450f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("ginger")
      .build();

//    PlantTypes.BROADLEAF_CATTAIL = PlantType
//      .builder("broadleaf_cattail")
//      .category(PlantCategories.TALL_WATER)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(-16f, 22f)
//      .temp(-31f, 37f)
//      .rain(150f, 500f)
//      .sun(0, 15)
//      .waterDepth(1, 64)
//      .movementMod(0.6D)
//      .addOreDict("broadleaf_cattail")
//      .build();

    PlantTypes.BROMEGRASS = PlantType
      .builder("bromegrass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(4f, 20f)
      .temp(-11f, 35f)
      .rain(140f, 360f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

//    PlantTypes.BUR_MARIGOLD = PlantType
//      .builder("bur_marigold")
//      .category(PlantCategories.TALL_WATER)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(-8f, 18f)
//      .temp(-23f, 33f)
//      .rain(50f, 390f)
//      .sun(0, 15)
//      .waterDepth(1, 64)
//      .movementMod(0.4D)
//      .addOreDict("bur_marigold")
//      .build();

    PlantTypes.BUR_REED = PlantType
      .builder("bur_reed")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0})
      .growthTemp(-16f, 4f)
      .temp(-31f, 19f)
      .rain(250f, 400f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.6D)
      .addOreDict("bur_reed")
      .build();

//    PlantTypes.CANNA_LILY = PlantType
//      .builder("canna_lily")
//      .category(PlantCategories.STANDARD)
//      .stages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
//      .growthTemp(10f, 40f)
//      .temp(-5f, 50f)
//      .rain(270f, 500f)
//      .sun(0, 15)
//      .movementMod(0.8D)
//      .addOreDict("canna_lily")
//      .build();

//    PlantTypes.COCKLESHELL_ORCHID = PlantType
//      .builder("cockleshell_orchid")
//      .category(PlantCategories.STANDARD)
//      .stages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
//      .growthTemp(14f, 40f)
//      .temp(-1f, 50f)
//      .rain(290f, 410f)
//      .sun(0, 15)
//      .movementMod(0.8D)
//      .addOreDict("cockleshell_orchid")
//      .build();

    PlantTypes.DEAD_BUSH = PlantType
      .builder("dead_bush")
      .category(PlantCategories.DRY_TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-12f, 40f)
      .temp(-27f, 50f)
      .rain(70f, 120f)
      .sun(0, 15)
      .movementMod(0.9D)
      .addOreDict("dead_bush")
      .build();

    PlantTypes.DESERT_FLAME = PlantType
      .builder("desert_flame")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1})
      .growthTemp(0f, 20f)
      .temp(-15f, 35f)
      .rain(40f, 170f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("desert_flame")
      .build();

    PlantTypes.HELICONIA = PlantType
      .builder("heliconia")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 1, 2, 0, 0, 0, 0, 1, 2, 0, 0})
      .growthTemp(14f, 40f)
      .temp(-1f, 50f)
      .rain(320f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("heliconia")
      .build();

    PlantTypes.HIBISCUS = PlantType
      .builder("hibiscus")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{2, 2, 2, 0, 0, 0, 0, 0, 0, 1, 2, 2})
      .growthTemp(10f, 24f)
      .temp(-10f, 39f)
      .rain(260f, 450f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("hibiscus")
      .build();

//    PlantTypes.HUNGARIAN_LILAC = PlantType
//      .builder("hungarian_lilac")
//      .category(PlantCategories.TALL_PLANT)
//      .stages(new int[]{0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0})
//      .growthTemp(-10f, 6f)
//      .temp(-25f, 21f)
//      .rain(150f, 300f)
//      .sun(0, 15)
//      .maxHeight(2)
//      .movementMod(0.7D)
//      .addOreDict("hungarian_lilac")
//      .build();

    PlantTypes.KANGAROO_PAW = PlantType
      .builder("kangaroo_paw")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1})
      .growthTemp(14f, 40f)
      .temp(-1f, 50f)
      .rain(100f, 300f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("kangaroo_paw")
      .build();

//    PlantTypes.KING_FERN = PlantType
//      .builder("king_fern")
//      .category(PlantCategories.TALL_GRASS)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(18f, 40f)
//      .temp(-7f, 50f)
//      .rain(350f, 500f)
//      .sun(0, 15)
//      .maxHeight(2)
//      .movementMod(0.4D)
//      .build();

    PlantTypes.LIPSTICK_PALM = PlantType
      .builder("lipstick_palm")
      .category(PlantCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 40f)
      .temp(3f, 50f)
      .rain(280f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.4D)
      .addOreDict("lipstick_palm")
      .build();

//    PlantTypes.MARIGOLD = PlantType
//      .builder("marigold")
//      .category(PlantCategories.TALL_PLANT)
//      .stages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
//      .growthTemp(4f, 22f)
//      .temp(-11f, 37f)
//      .rain(130f, 300f)
//      .sun(0, 15)
//      .maxHeight(2)
//      .movementMod(0.8D)
//      .addOreDict("marigold")
//      .build();

    PlantTypes.MONSTERA_EPIPHYTE = PlantType
      .builder("monstera_epiphyte")
      .category(PlantCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(0f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.9D)
      .addOreDict("monstera")
      .build();

//    PlantTypes.MONSTERA_GROUND = PlantType
//      .builder("monstera_ground")
//      .category(PlantCategories.TALL_GRASS)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(20f, 40f)
//      .temp(0f, 50f)
//      .rain(200f, 500f)
//      .sun(0, 15)
//      .maxHeight(2)
//      .movementMod(0.9D)
//      .build();

    PlantTypes.PHRAGMITE = PlantType
      .builder("phragmite")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 2, 2, 3, 1, 1, 0})
      .growthTemp(-6f, 18f)
      .temp(-21f, 33f)
      .rain(50f, 250f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.6D)
      .addOreDict("phragmite")
      .build();

    PlantTypes.PICKERELWEED = PlantType
      .builder("pickerelweed")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 0, 0})
      .growthTemp(-14f, 16f)
      .temp(-29f, 31f)
      .rain(200f, 500f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.6D)
      .addOreDict("pickerelweed")
      .build();

    PlantTypes.RADDIA_GRASS = PlantType
      .builder("raddia_grass")
      .category(PlantCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 31f)
      .temp(3f, 48f)
      .rain(75f, 295f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

//    PlantTypes.SCARLET_STAR_BROMELIAD = PlantType
//      .builder("scarlet_star_bromeliad")
//      .category(PlantCategories.EPIPHYTE)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(20f, 40f)
//      .temp(5f, 50f)
//      .rain(290f, 480f)
//      .sun(0, 15)
//      .maxHeight(6)
//      .movementMod(0.9D)
//      .addOreDict("scarlet_star_bromeliad")
//      .build();

    PlantTypes.SILVER_SPURFLOWER = PlantType
      .builder("silver_spurflower")
      .category(PlantCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 2, 2, 0, 0, 0, 0})
      .growthTemp(14f, 24f)
      .temp(-1f, 39f)
      .rain(230f, 400f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("silver_spurflower")
      .build();

    PlantTypes.WATER_TARO = PlantType
      .builder("water_taro")
      .category(PlantCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(12f, 40f)
      .temp(-3f, 50f)
      .rain(260f, 500f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.6D)
      .addOreDict("water_taro")
      .build();

    PlantTypes.RATTAN = PlantType
      .builder("rattan")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 40f)
      .temp(10f, 50f)
      .rain(210f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.BEARDED_MOSS = PlantType
      .builder("bearded_moss")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-10f, 22f)
      .temp(-25f, 37f)
      .rain(180f, 500f)
      .sun(0, 15)
      .maxHeight(10)
      .movementMod(0.7D)
      .addOreDict("vine")
      .build();

    PlantTypes.BLUE_SKYFLOWER = PlantType
      .builder("blue_skyflower")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(15f, 50f)
      .rain(300f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.GLOW_VINE = PlantType
      .builder("glow_vine")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 50f)
      .temp(10f, 50f)
      .rain(210f, 500f)
      .sun(0, 15)
      .maxHeight(15)
      .movementMod(0.7D)
      .addOreDict("vine")
      .build();

    PlantTypes.HANGING_VINE = PlantType
      .builder("hanging_vine")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 50f)
      .temp(13f, 50f)
      .rain(150f, 470f)
      .sun(0, 15)
      .maxHeight(22)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.JADE_VINE = PlantType
      .builder("jade_vine")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.JAPANESE_IVY = PlantType
      .builder("japanese_ivy")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 35f)
      .temp(-6f, 36f)
      .rain(120f, 300f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.JUNGLE_VINE = PlantType
      .builder("jungle_vine")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 50f)
      .temp(15f, 50f)
      .rain(150f, 470f)
      .sun(0, 15)
      .maxHeight(22)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.LIANA = PlantType
      .builder("liana")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 50f)
      .temp(15f, 50f)
      .rain(150f, 470f)
      .sun(0, 15)
      .maxHeight(16)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.MADEIRA_VINE = PlantType
      .builder("madeira_vine")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(5f, 50f)
      .rain(100f, 350f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.MYSORE_TRUMPETVINE = PlantType
      .builder("mysore_trumpetvine")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.SILVERVEIN_CREEPER = PlantType
      .builder("silvervein_creeper")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(15f, 50f)
      .rain(300f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.SWEDISH_IVY = PlantType
      .builder("swedish_ivy")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(5f, 50f)
      .rain(50f, 300f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.VARIEGATED_PERSIAN_IVY = PlantType
      .builder("variegated_persian_ivy")
      .category(PlantCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(5f, 50f)
      .rain(50f, 300f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    PlantTypes.BADDERLOCKS = PlantType
      .builder("badderlocks")
      .category(PlantCategories.TALL_WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-31f, 17f)
      .temp(-33f, 25f)
      .rain(150f, 500f)
      .sun(0, 15)
      .maxHeight(6)
      .waterDepth(1, 256)
      .movementMod(0.8D)
      .addOreDict("seaweed")
      .build();

//    PlantTypes.BROWN_ALGAE = PlantType
//      .builder("brown_algae")
//      .category(PlantCategories.WATER)
//      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
//      .growthTemp(-37f, 11f)
//      .temp(-39f, 13f)
//      .rain(100f, 500f)
//      .sun(0, 15)
//      .waterDepth(1, 256)
//      .movementMod(0.6D)
//      .addOreDict("seaweed")
//      .build();

    PlantTypes.COONTAIL = PlantType
      .builder("coontail")
      .category(PlantCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(-11f, 31f)
      .temp(-13f, 33f)
      .rain(250f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.7D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.EEL_GRASS = PlantType
      .builder("eel_grass")
      .category(PlantCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(-7f, 48f)
      .temp(-9f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.GIANT_KELP = PlantType
      .builder("giant_kelp")
      .category(PlantCategories.TALL_WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-31f, 38f)
      .temp(-33f, 43f)
      .rain(0f, 500f)
      .sun(0, 15)
      .maxHeight(26)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.GUTWEED = PlantType
      .builder("gutweed")
      .category(PlantCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-19f, 31f)
      .temp(-21f, 33f)
      .rain(100f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.HORNWORT = PlantType
      .builder("hornwort")
      .category(PlantCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 31f)
      .temp(-13f, 33f)
      .rain(250f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.7D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.LAMINARIA = PlantType
      .builder("laminaria")
      .category(PlantCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-29f, 17f)
      .temp(-31f, 19f)
      .rain(250f, 400f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.6D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.LEAFY_KELP = PlantType
      .builder("leafy_kelp")
      .category(PlantCategories.TALL_WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-33f, 37f)
      .temp(-35f, 41f)
      .rain(0f, 500f)
      .sun(0, 15)
      .maxHeight(21)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.MANATEE_GRASS = PlantType
      .builder("manatee_grass")
      .category(PlantCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(-1f, 48f)
      .temp(-3f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.MILFOIL = PlantType
      .builder("milfoil")
      .category(PlantCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-27f, 35f)
      .temp(-29f, 37f)
      .rain(250f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.7D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.PONDWEED = PlantType
      .builder("pondweed")
      .category(PlantCategories.TALL_WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-31f, 31f)
      .temp(-33f, 33f)
      .rain(200f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .waterDepth(1, 256)
      .movementMod(0.7D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.RED_ALGAE = PlantType
      .builder("red_algae")
      .category(PlantCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(1f, 48f)
      .temp(-1f, 50f)
      .rain(150f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.RED_SEA_WHIP = PlantType
      .builder("red_sea_whip")
      .category(PlantCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(1f, 48f)
      .temp(-1f, 50f)
      .rain(150f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.SAGO = PlantType
      .builder("sago")
      .category(PlantCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-32f, 36f)
      .temp(-34f, 38f)
      .rain(0f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.SEA_ANEMONE = PlantType
      .builder("sea_anemone")
      .category(PlantCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(1f, 48f)
      .temp(-1f, 50f)
      .rain(150f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.SEAGRASS = PlantType
      .builder("seagrass")
      .category(PlantCategories.TALL_WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-20f, 50f)
      .temp(-30f, 50f)
      .rain(0f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.SEAWEED = PlantType
      .builder("seaweed")
      .category(PlantCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-19f, 40f)
      .temp(-21f, 50f)
      .rain(0f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.STAR_GRASS = PlantType
      .builder("star_grass")
      .category(PlantCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(50f, 260f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.TURTLE_GRASS = PlantType
      .builder("turtle_grass")
      .category(PlantCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(1f, 48f)
      .temp(-1f, 50f)
      .rain(240f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    PlantTypes.WINGED_KELP = PlantType
      .builder("winged_kelp")
      .category(PlantCategories.TALL_WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-28f, 38f)
      .temp(-30f, 42f)
      .rain(0f, 450f)
      .sun(0, 15)
      .maxHeight(21)
      .waterDepth(1, 256)
      .movementMod(0.8D)
      .addOreDict("seaweed")
      .build();

  }
}
