package su.terrafirmagreg.modules.flora.api.types.type;

import su.terrafirmagreg.modules.flora.api.types.category.FloraCategories;
import su.terrafirmagreg.modules.flora.api.types.category.FloraCategoryHandler;

public final class FloraTypeHandler {

  public static void init() {
    FloraCategoryHandler.init();

    FloraTypes.BASIL = FloraType
      .builder("basil")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0})
      .growthTemp(13.0f, 25.0f)
      .temp(13.0f, 25.0f)
      .rain(200.0F, 500.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.BAY_LAUREL = FloraType
      .builder("bay_laurel")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0})
      .growthTemp(5.0F, 18.0F)
      .temp(5.0F, 18.0F)
      .rain(200.0F, 500.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.CARDAMOM = FloraType
      .builder("cardamom")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(16.0F, 30.0F)
      .temp(16.0F, 30.0F)
      .rain(100.0F, 400.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.CILANTRO = FloraType
      .builder("cilantro")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0})
      .growthTemp(14.0F, 31.0F)
      .temp(14.0F, 31.0F)
      .rain(300.0F, 500.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.CUMIN = FloraType
      .builder("cumin")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0})
      .growthTemp(10.0F, 30.0F)
      .temp(10.0F, 30.0F)
      .rain(150.0F, 350.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.OREGANO = FloraType
      .builder("oregano")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0})
      .growthTemp(14.0F, 28.0F)
      .temp(14.0F, 28.0F)
      .rain(50.0F, 300.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.PIMENTO = FloraType
      .builder("pimento")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(10.0F, 18.0F)
      .temp(10.0F, 18.0F)
      .rain(100.0F, 300.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.VANILLA = FloraType
      .builder("vanilla")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0})
      .growthTemp(13.0F, 29.0F)
      .temp(13.0F, 29.0F)
      .rain(200.0F, 450.0F)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.ALLIUM = FloraType
      .builder("allium")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{6, 6, 7, 0, 1, 1, 2, 2, 3, 4, 5, 6})
      .growthTemp(8f, 20f)
      .temp(-40f, 33f)
      .rain(150f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.ATHYRIUM_FERN = FloraType
      .builder("athyrium_fern")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .isClayMarking()
      .growthTemp(13f, 25f)
      .temp(-35f, 31f)
      .rain(200f, 500f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.BARREL_CACTUS = FloraType
      .builder("barrel_cactus")
      .category(FloraCategories.CACTUS)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 0})
      .growthTemp(18f, 40f)
      .temp(-6f, 50f)
      .rain(0f, 75f)
      .sun(12, 15)
      .maxHeight(3)
      .addOreDict("blockCactus")
      .build();

    FloraTypes.BLACK_ORCHID = FloraType
      .builder("black_orchid")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
      .growthTemp(20f, 35f)
      .temp(10f, 50f)
      .rain(300f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.BLOOD_LILY = FloraType
      .builder("blood_lily")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2})
      .growthTemp(18f, 30f)
      .temp(10f, 50f)
      .rain(200f, 500f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.BLUE_ORCHID = FloraType
      .builder("blue_orchid")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
      .growthTemp(20f, 35f)
      .temp(10f, 50f)
      .rain(300f, 500f)
      .sun(12, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.BUTTERFLY_MILKWEED = FloraType
      .builder("butterfly_milkweed")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(18f, 24f)
      .temp(-40f, 32f)
      .rain(75f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.CALENDULA = FloraType
      .builder("calendula")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(15f, 20f)
      .temp(-46f, 30f)
      .rain(130f, 300f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.CANNA = FloraType
      .builder("canna")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
      .isClayMarking()
      .growthTemp(18f, 30f)
      .temp(-12f, 36f)
      .rain(150f, 500f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.DANDELION = FloraType
      .builder("dandelion")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{9, 9, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8})
      .growthTemp(10f, 25f)
      .temp(-40f, 40f)
      .rain(75f, 400f)
      .sun(10, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.DUCKWEED = FloraType
      .builder("duckweed")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(11f, 20f)
      .temp(-34f, 38f)
      .rain(0f, 500f)
      .sun(4, 15)
      .waterDepth(2, 32)
      .movementMod(0.8D)
      .build();

    FloraTypes.FIELD_HORSETAIL = FloraType
      .builder("field_horsetail")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1})
      .growthTemp(5f, 20f)
      .temp(-40f, 33f)
      .rain(300f, 500f)
      .sun(9, 15)
      .movementMod(0.7D)
      .addOreDict("reed")
      .build();

    FloraTypes.FOUNTAIN_GRASS = FloraType
      .builder("fountain_grass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 35f)
      .temp(-12f, 40f)
      .rain(75f, 150f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.FOXGLOVE = FloraType
      .builder("foxglove")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 2, 3, 3, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 34f)
      .rain(150f, 300f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.GOLDENROD = FloraType
      .builder("goldenrod")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{4, 4, 4, 0, 0, 0, 1, 2, 2, 2, 2, 3})
      .isClayMarking()
      .growthTemp(15f, 23f)
      .temp(-29f, 32f)
      .rain(75f, 300f)
      .sun(9, 15)
      .movementMod(0.6D)
      .build();

    FloraTypes.GRAPE_HYACINTH = FloraType
      .builder("grape_hyacinth")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{3, 3, 3, 0, 1, 1, 2, 3, 3, 3, 3, 3})
      .growthTemp(4f, 18f)
      .temp(-34f, 32f)
      .rain(150f, 250f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.GUZMANIA = FloraType
      .builder("guzmania")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(15f, 50f)
      .rain(300f, 500f)
      .sun(4, 11)
      .movementMod(0.9D)
      .build();

    FloraTypes.HOUSTONIA = FloraType
      .builder("houstonia")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{2, 2, 2, 0, 1, 1, 1, 2, 2, 2, 2, 2})
      .growthTemp(15f, 30f)
      .temp(-46f, 36f)
      .rain(150f, 500f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.LABRADOR_TEA = FloraType
      .builder("labrador_tea")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 1, 2, 3, 4, 4, 5, 6, 0, 0, 0})
      .growthTemp(1f, 25f)
      .temp(-50f, 33f)
      .rain(300f, 500f)
      .sun(10, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.LADY_FERN = FloraType
      .builder("lady_fern")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(13f, 25f)
      .temp(-34f, 32f)
      .rain(200f, 500f)
      .sun(9, 11)
      .movementMod(0.6D)
      .build();

    FloraTypes.LICORICE_FERN = FloraType
      .builder("licorice_fern")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(5f, 18f)
      .temp(-29f, 25f)
      .rain(300f, 500f)
      .sun(4, 11)
      .movementMod(0.7D)
      .build();

    FloraTypes.LOTUS = FloraType
      .builder("lotus")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(0f, 500f)
      .sun(4, 15)
      .waterDepth(1, 1)
      .movementMod(0.9D)
      .build();

    FloraTypes.MEADS_MILKWEED = FloraType
      .builder("meads_milkweed")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(13f, 25f)
      .temp(-23f, 31f)
      .rain(130f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.MORNING_GLORY = FloraType
      .builder("morning_glory")
      .category(FloraCategories.CREEPING)
      .stages(new int[]{2, 2, 2, 0, 0, 1, 1, 1, 1, 1, 2, 2})
      .growthTemp(15f, 30f)
      .temp(-40f, 31f)
      .rain(150f, 500f)
      .sun(12, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.MOSS = FloraType
      .builder("moss")
      .category(FloraCategories.CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-2f, 15f)
      .temp(-7f, 36f)
      .rain(250f, 500f)
      .sun(0, 11)
      .movementMod(0.7D)
      .build();

    FloraTypes.NASTURTIUM = FloraType
      .builder("nasturtium")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{4, 4, 4, 0, 1, 2, 2, 2, 2, 2, 3, 3})
      .growthTemp(18f, 30f)
      .temp(-46f, 38f)
      .rain(150f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.ORCHARD_GRASS = FloraType
      .builder("orchard_grass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(13f, 20f)
      .temp(-29f, 30f)
      .rain(75f, 300f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.OSTRICH_FERN = FloraType
      .builder("ostrich_fern")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 4, 0})
      .growthTemp(10f, 18f)
      .temp(-40f, 33f)
      .rain(300f, 500f)
      .sun(4, 11)
      .maxHeight(2)
      .movementMod(0.6D)
      .build();

    FloraTypes.OXEYE_DAISY = FloraType
      .builder("oxeye_daisy")
      .category(FloraCategories.STANDARD)
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

    FloraTypes.PEROVSKIA = FloraType
      .builder("perovskia")
      .category(FloraCategories.DRY)
      .stages(new int[]{5, 5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4})
      .isClayMarking()
      .growthTemp(18f, 35f)
      .temp(-29f, 32f)
      .rain(0f, 200f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.PISTIA = FloraType
      .builder("pistia")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(0f, 500f)
      .sun(4, 15)
      .waterDepth(2, 32)
      .movementMod(0.8D)
      .build();

    FloraTypes.POPPY = FloraType
      .builder("poppy")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{4, 4, 4, 0, 1, 2, 2, 3, 3, 3, 3, 4})
      .growthTemp(17f, 30f)
      .temp(-40f, 36f)
      .rain(150f, 250f)
      .sun(12, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.PORCINI = FloraType
      .builder("porcini")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(13f, 20f)
      .temp(0f, 30f)
      .rain(300f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroomBrown")
      .build();

    FloraTypes.PRIMROSE = FloraType
      .builder("primrose")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(150f, 300f)
      .sun(9, 11)
      .movementMod(0.9D)
      .build();

    FloraTypes.PULSATILLA = FloraType
      .builder("pulsatilla")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 1, 2, 3, 3, 4, 5, 5, 5, 0, 0, 0})
      .growthTemp(1f, 25f)
      .temp(-50f, 30f)
      .rain(50f, 200f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.REINDEER_LICHEN = FloraType
      .builder("reindeer_lichen")
      .category(FloraCategories.CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(1f, 25f)
      .temp(-50f, 33f)
      .rain(50f, 500f)
      .sun(9, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.ROSE = FloraType
      .builder("rose")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .isClayMarking()
      .growthTemp(11f, 21f)
      .temp(-29f, 34f)
      .rain(150f, 300f)
      .sun(9, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .build();

    FloraTypes.ROUGH_HORSETAIL = FloraType
      .builder("rough_horsetail")
      .category(FloraCategories.REED)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(5f, 20f)
      .temp(-40f, 33f)
      .rain(200f, 500f)
      .sun(9, 15)
      .movementMod(0.7D)
      .addOreDict("reed")
      .build();

    FloraTypes.RYEGRASS = FloraType
      .builder("ryegrass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0})
      .growthTemp(10f, 18f)
      .temp(-46f, 32f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.SACRED_DATURA = FloraType
      .builder("sacred_datura")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{3, 3, 3, 0, 1, 2, 2, 2, 2, 2, 2, 2})
      .growthTemp(20f, 30f)
      .temp(5f, 33f)
      .rain(75f, 150f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.SAGEBRUSH = FloraType
      .builder("sagebrush")
      .category(FloraCategories.DRY)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0})
      .growthTemp(18f, 35f)
      .temp(-34f, 50f)
      .rain(0f, 100f)
      .sun(12, 15)
      .movementMod(0.5D)
      .build();

    FloraTypes.SAPPHIRE_TOWER = FloraType
      .builder("sapphire_tower")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{2, 3, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2})
      .growthTemp(21f, 31f)
      .temp(-6f, 38f)
      .rain(75f, 200f)
      .sun(9, 15)
      .maxHeight(2)
      .movementMod(0.6D)
      .build();

    FloraTypes.SARGASSUM = FloraType
      .builder("sargassum")
      .category(FloraCategories.FLOATING_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 30f)
      .temp(0f, 38f)
      .rain(0f, 500f)
      .sun(12, 15)
      .waterDepth(5, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.SCUTCH_GRASS = FloraType
      .builder("scutch_grass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 35f)
      .temp(-17f, 50f)
      .rain(150f, 500f)
      .sun(12, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.SNAPDRAGON_PINK = FloraType
      .builder("snapdragon_pink")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
      .growthTemp(15f, 25f)
      .temp(-28f, 36f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.SNAPDRAGON_RED = FloraType
      .builder("snapdragon_red")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
      .growthTemp(15f, 25f)
      .temp(-28f, 36f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.SNAPDRAGON_WHITE = FloraType
      .builder("snapdragon_white")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
      .growthTemp(15f, 25f)
      .temp(-28f, 36f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.SNAPDRAGON_YELLOW = FloraType
      .builder("snapdragon_yellow")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{6, 6, 6, 0, 1, 1, 2, 3, 4, 1, 1, 5})
      .growthTemp(15f, 25f)
      .temp(-28f, 36f)
      .rain(150f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.SPANISH_MOSS = FloraType
      .builder("spanish_moss")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .isSwampPlant()
      .growthTemp(20f, 40f)
      .temp(0f, 40f)
      .rain(300f, 500f)
      .sun(4, 15)
      .maxHeight(3)
      .movementMod(0.7D)
      .build();

    FloraTypes.STRELITZIA = FloraType
      .builder("strelitzia")
      .category(FloraCategories.STANDARD)
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

    FloraTypes.SWORD_FERN = FloraType
      .builder("sword_fern")
      .category(FloraCategories.STANDARD)
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

    FloraTypes.TIMOTHY_GRASS = FloraType
      .builder("timothy_grass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 25f)
      .temp(-46f, 30f)
      .rain(300f, 500f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.TOQUILLA_PALM = FloraType
      .builder("toquilla_palm")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(9, 15)
      .maxHeight(2)
      .movementMod(0.4D)
      .build();

    FloraTypes.TREE_FERN = FloraType
      .builder("tree_fern")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(300f, 500f)
      .sun(9, 15)
      .maxHeight(4)
      .movementMod(0D)
      .build();

    FloraTypes.TRILLIUM = FloraType
      .builder("trillium")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{5, 5, 5, 0, 1, 2, 3, 3, 4, 4, 4, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(150f, 300f)
      .sun(4, 11)
      .movementMod(0.8D)
      .build();

    FloraTypes.TROPICAL_MILKWEED = FloraType
      .builder("tropical_milkweed")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 3, 0})
      .growthTemp(20f, 35f)
      .temp(-6f, 36f)
      .rain(120f, 300f)
      .sun(12, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.TULIP_ORANGE = FloraType
      .builder("tulip_orange")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(100f, 200f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.TULIP_PINK = FloraType
      .builder("tulip_pink")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(100f, 200f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.TULIP_RED = FloraType
      .builder("tulip_red")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(100f, 200f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.TULIP_WHITE = FloraType
      .builder("tulip_white")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{4, 4, 5, 0, 1, 1, 2, 2, 2, 2, 3, 4})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(100f, 200f)
      .sun(9, 15)
      .movementMod(0.9D)
      .build();

    FloraTypes.VRIESEA = FloraType
      .builder("vriesea")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0})
      .growthTemp(0f, 40f)
      .temp(15f, 50f)
      .rain(300f, 500f)
      .sun(4, 11)
      .movementMod(0.8D)
      .build();

    FloraTypes.WATER_CANNA = FloraType
      .builder("water_canna")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 0})
      .isClayMarking()
      .growthTemp(18f, 30f)
      .temp(-12f, 36f)
      .rain(150f, 500f)
      .sun(9, 15)
      .waterDepth(1, 1)
      .movementMod(0.8D)
      .build();

    FloraTypes.WATER_LILY = FloraType
      .builder("water_lily")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{5, 5, 6, 0, 1, 2, 2, 2, 2, 3, 4, 5})
      .growthTemp(15f, 30f)
      .temp(-34f, 38f)
      .rain(0f, 500f)
      .sun(4, 15)
      .waterDepth(1, 1)
      .movementMod(0.8D)
      .build();

    FloraTypes.YUCCA = FloraType
      .builder("yucca")
      .category(FloraCategories.DESERT)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 3})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(9, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.BAMBOO = FloraType
      .builder("bamboo")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(270f, 500f)
      .sun(6, 15)
      .maxHeight(12)
      .movementMod(0D)
      .addOreDict("bamboo")
      .build();

    FloraTypes.BROMELIA_HEMISPHERICA = FloraType
      .builder("bromelia_hemispherica")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(16f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.BROMELIA_LACINIOSA = FloraType
      .builder("bromelia_laciniosa")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(16f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.BUNCH_GRASS_FLOATING = FloraType
      .builder("bunch_grass_floating")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(-10f, 35f)
      .temp(-15f, 45f)
      .rain(100f, 400f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.7D)
      .addOreDict("reed")
      .build();

    FloraTypes.BUNCH_GRASS_REED = FloraType
      .builder("bunch_grass_reed")
      .category(FloraCategories.TALL_REED)
      .stages(new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0})
      .growthTemp(-10f, 35f)
      .temp(-15f, 45f)
      .rain(100f, 400f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.7D)
      .addOreDict("reed")
      .build();

    FloraTypes.BURNING_BUSH = FloraType
      .builder("burning_bush")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0})
      .growthTemp(11f, 21f)
      .temp(-29f, 34f)
      .rain(125f, 300f)
      .sun(6, 15)
      .movementMod(0.5D)
      .build();

    FloraTypes.CATTAIL = FloraType
      .builder("cattail")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{4, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 4})
      .growthTemp(-16f, 28f)
      .temp(-36f, 40f)
      .rain(150f, 500f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.5D)
      .addOreDict("cattail")
      .build();

    FloraTypes.CAT_GRASS = FloraType
      .builder("cat_grass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(10f, 18f)
      .temp(-46f, 32f)
      .rain(150f, 300f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.CAVE_VINES = FloraType
      .builder("cave_vines")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-50f, 50f)
      .temp(-50f, 50f)
      .rain(0f, 500f)
      .sun(0, 5)
      .maxHeight(32)
      .movementMod(0.7D)
      .build();

    FloraTypes.CHAMOMILE = FloraType
      .builder("chamomile")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 0, 0})
      .growthTemp(5f, 25f)
      .temp(-40f, 40f)
      .rain(75f, 400f)
      .sun(7, 15)
      .movementMod(0.9D)
      .addOreDict("chamomile")
      .build();

    FloraTypes.CHAPARRAL_SHRUB = FloraType
      .builder("chaparral_shrub")
      .category(FloraCategories.DRY)
      .stages(new int[]{0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0})
      .growthTemp(10f, 40f)
      .temp(0f, 60f)
      .rain(100f, 250f)
      .sun(0, 15)
      .movementMod(0.1D)
      .addOreDict("shrub")
      .build();

    FloraTypes.CINNAMON_FERN = FloraType
      .builder("cinnamon_fern")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 25f)
      .temp(-40f, 30f)
      .rain(190f, 500f)
      .sun(5, 15)
      .movementMod(0.5D)
      .build();

    FloraTypes.CORD_GRASS = FloraType
      .builder("cord_grass")
      .category(FloraCategories.TALL_REED)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(17f, 40f)
      .temp(0f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.5D)
      .build();

    FloraTypes.CROWNGRASS = FloraType
      .builder("crowngrass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 30f)
      .temp(-46f, 38f)
      .rain(150f, 500f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.DEVILS_TONGUE = FloraType
      .builder("devils_tongue")
      .category(FloraCategories.TALL_PLANT)
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

    FloraTypes.GOOSEGRASS = FloraType
      .builder("goosegrass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(13f, 20f)
      .temp(-29f, 30f)
      .rain(75f, 300f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.HALFA_GRASS = FloraType
      .builder("halfa_grass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(3f, 25f)
      .temp(-15f, 32f)
      .rain(100f, 150f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.HYDRANGEA = FloraType
      .builder("hydrangea")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(11f, 31f)
      .temp(-29f, 34f)
      .rain(125f, 300f)
      .sun(8, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("hydrangea")
      .build();

    FloraTypes.ICICLE = FloraType
      .builder("icicle")
      .category(FloraCategories.HANGING)
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

    FloraTypes.JAPANESE_PIERIS = FloraType
      .builder("japanese_pieris")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 1, 0, 0})
      .growthTemp(15f, 25f)
      .temp(-29f, 30f)
      .rain(220f, 500f)
      .sun(6, 15)
      .maxHeight(1)
      .movementMod(0.5D)
      .build();

    FloraTypes.KAIETEUR_FALLS = FloraType
      .builder("kaieteur_falls")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(16f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.8D)
      .build();

    FloraTypes.LAVANDULA = FloraType
      .builder("lavandula")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(5f, 35f)
      .temp(-29f, 40f)
      .rain(0f, 300f)
      .sun(7, 15)
      .movementMod(0.8D)
      .addOreDict("lavender")
      .build();

    FloraTypes.LEAF_LITTER = FloraType
      .builder("leaf_litter")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-20f, 50f)
      .temp(-30f, 50f)
      .rain(70f, 500f)
      .sun(0, 14)
      .movementMod(0.9D)
      .build();

    FloraTypes.LEYMUS = FloraType
      .builder("leymus")
      .category(FloraCategories.DRY_TALL_PLANT)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 35f)
      .temp(-12f, 40f)
      .rain(80f, 180f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.LILAC = FloraType
      .builder("lilac")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(11f, 31f)
      .temp(-29f, 34f)
      .rain(125f, 300f)
      .sun(8, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("lilac")
      .build();

    FloraTypes.LILY_OF_THE_VALLEY = FloraType
      .builder("lily_of_the_valley")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{5, 0, 0, 1, 2, 2, 3, 3, 3, 3, 4, 5})
      .growthTemp(5f, 35f)
      .temp(-29f, 40f)
      .rain(0f, 300f)
      .sun(8, 15)
      .movementMod(0.8D)
      .addOreDict("lily_of_the_valley")
      .build();

    FloraTypes.UNDERGROWTH_LOW = FloraType
      .builder("undergrowth_low")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 40f)
      .temp(-1f, 50f)
      .rain(250f, 500f)
      .sun(0, 14)
      .movementMod(0.9D)
      .build();

    FloraTypes.MARRAM_GRASS = FloraType
      .builder("marram_grass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(15f, 50f)
      .temp(-10f, 50f)
      .rain(70f, 120f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.MATTEUCCIA = FloraType
      .builder("matteuccia")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(0f, 50f)
      .rain(210f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.5D)
      .build();

    FloraTypes.OCOTILLO = FloraType
      .builder("ocotillo")
      .category(FloraCategories.DRY_TALL_PLANT)
      .stages(new int[]{0, 0, 1, 1, 2, 2, 2, 1, 1, 0, 0, 0})
      .growthTemp(15f, 50f)
      .temp(-10f, 50f)
      .rain(70f, 90f)
      .sun(9, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("ocotillo")
      .build();

    FloraTypes.PAPYRUS = FloraType
      .builder("papyrus")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(17f, 40f)
      .temp(10f, 50f)
      .rain(150f, 400f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("papyrus")
      .build();

    FloraTypes.PEONY = FloraType
      .builder("peony")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(11f, 21f)
      .temp(-29f, 34f)
      .rain(125f, 300f)
      .sun(6, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("peony")
      .build();

    FloraTypes.PRAIRIE_JUNEGRASS = FloraType
      .builder("prairie_junegrass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0})
      .growthTemp(13f, 25f)
      .temp(-29f, 32f)
      .rain(75f, 200f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.REED_MANNAGRASS = FloraType
      .builder("reed_mannagrass")
      .category(FloraCategories.TALL_REED)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 40f)
      .temp(0f, 210f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.7D)
      .build();

    FloraTypes.RESIN = FloraType
      .builder("resin")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(10f, 45f)
      .temp(5f, 50f)
      .rain(50f, 500f)
      .sun(0, 14)
      .movementMod(0.0D)
      .addOreDict("resin")
      .build();

    FloraTypes.ROOTS = FloraType
      .builder("roots")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-50f, 50f)
      .temp(-50f, 50f)
      .rain(0f, 500f)
      .sun(0, 5)
      .maxHeight(2)
      .movementMod(0.9D)
      .build();

    FloraTypes.SEA_OATS = FloraType
      .builder("sea_oats")
      .category(FloraCategories.DESERT_TALL_PLANT)
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

    FloraTypes.SUGAR_CANE = FloraType
      .builder("sugar_cane")
      .category(FloraCategories.TALL_REED)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 40f)
      .temp(10f, 40f)
      .rain(300f, 500f)
      .sun(0, 15)
      .maxHeight(3)
      .movementMod(0.5D)
      .addOreDict("sugarcane")
      .build();

    FloraTypes.SUNFLOWER = FloraType
      .builder("sunflower")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{9, 0, 1, 2, 3, 4, 5, 5, 6, 7, 8, 9})
      .growthTemp(8f, 31f)
      .temp(-29f, 34f)
      .rain(100f, 300f)
      .sun(6, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("sunflower")
      .build();

    FloraTypes.TACKWEED = FloraType
      .builder("tackweed")
      .category(FloraCategories.CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-2f, 25f)
      .temp(-10f, 40f)
      .rain(250f, 500f)
      .sun(0, 14)
      .movementMod(0.7D)
      .addOreDict("moss")
      .build();

    FloraTypes.TAKAKIA = FloraType
      .builder("takakia")
      .category(FloraCategories.CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-2f, 25f)
      .temp(-10f, 40f)
      .rain(250f, 500f)
      .sun(0, 14)
      .movementMod(0.7D)
      .addOreDict("moss")
      .build();

    FloraTypes.UNDERGROWTH_SHRUB = FloraType
      .builder("undergrowth_shrub")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{4, 4, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4})
      .growthTemp(-10f, 40f)
      .temp(0f, 50f)
      .rain(70f, 500f)
      .sun(0, 15)
      .maxHeight(3)
      .movementMod(0.2D)
      .build();

    FloraTypes.UNDERGROWTH_SHRUB_SMALL = FloraType
      .builder("undergrowth_shrub_small")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{4, 4, 0, 1, 1, 1, 2, 2, 2, 3, 3, 4})
      .growthTemp(-10f, 40f)
      .temp(0f, 50f)
      .rain(70f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.3D)
      .build();

    FloraTypes.VOODOO_LILY = FloraType
      .builder("voodoo_lily")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(16f, 45f)
      .temp(10f, 50f)
      .rain(270f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.9D)
      .addOreDict("voodoo_lily")
      .build();

    FloraTypes.WHEATGRASS = FloraType
      .builder("wheatgrass")
      .category(FloraCategories.SHORT_GRASS)
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

    FloraTypes.WOOLLY_BUSH = FloraType
      .builder("woolly_bush")
      .category(FloraCategories.SHORT_GRASS)
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

    FloraTypes.APACHE_DWARF = FloraType
      .builder("apache_dwarf")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(5f, 50f)
      .rain(100f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.ARTISTS_CONK = FloraType
      .builder("artists_conk")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(-12f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("epiphyte_artists_conk")
      .build();

    FloraTypes.CLIMBING_CACTUS = FloraType
      .builder("climbing_cactus")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(11f, 31f)
      .temp(-29f, 34f)
      .rain(0f, 150f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.CRIMSON_CATTLEYA = FloraType
      .builder("crimson_cattleya")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(270f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.CREEPING_MISTLETOE = FloraType
      .builder("creeping_mistletoe")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(5f, 35f)
      .temp(-29f, 40f)
      .rain(0f, 300f)
      .sun(0, 15)
      .maxHeight(1)
      .movementMod(0.8D)
      .build();

    FloraTypes.CUTHBERTS_DENDROBIUM = FloraType
      .builder("cuthberts_dendrobium")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(270f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.FISH_BONE_CACTUS = FloraType
      .builder("fish_bone_cactus")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(130f, 300f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.FRAGRANT_FERN = FloraType
      .builder("fragrant_fern")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.HARLEQUIN_MISTLETOE = FloraType
      .builder("harlequin_mistletoe")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-12f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.KING_ORCHID = FloraType
      .builder("king_orchid")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.LANTERN_OF_THE_FOREST = FloraType
      .builder("lantern_of_the_forest")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.LARGE_FOOT_DENDROBIUM = FloraType
      .builder("large_foot_dendrobium")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.COMMON_MISTLETOE = FloraType
      .builder("common_mistletoe")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(5f, 35f)
      .temp(-29f, 40f)
      .rain(0f, 300f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.SKY_PLANT = FloraType
      .builder("sky_plant")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(130f, 300f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.SULPHUR_SHELF = FloraType
      .builder("sulphur_shelf")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(-12f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("epiphyte_sulphur_shelf")
      .build();

    FloraTypes.TAMPA_BUTTERFLY_ORCHID = FloraType
      .builder("tampa_butterfly_orchid")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(0f, 40f)
      .temp(-5f, 50f)
      .rain(190f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.TURKEY_TAIL = FloraType
      .builder("turkey_tail")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(-12f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("epiphyte_turkey_tail")
      .build();

    FloraTypes.WILDFIRE = FloraType
      .builder("wildfire")
      .category(FloraCategories.EPIPHYTE)
      .stages(new int[]{0, 1, 1, 2, 2, 3, 3, 2, 2, 1, 1, 0})
      .growthTemp(15f, 40f)
      .temp(10f, 50f)
      .rain(130f, 300f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.AMANITA = FloraType
      .builder("amanita")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_red")
      .build();

    FloraTypes.BLACK_POWDERPUFF = FloraType
      .builder("black_powderpuff")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_black_powderpuff")
      .build();

    FloraTypes.CHANTERELLE = FloraType
      .builder("chanterelle")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_chanterelle")
      .build();

    FloraTypes.DEATH_CAP = FloraType
      .builder("death_cap")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_death_cap")
      .build();

    FloraTypes.GIANT_CLUB = FloraType
      .builder("giant_club")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_giant_club")
      .build();

    FloraTypes.PARASOL_MUSHROOM = FloraType
      .builder("parasol_mushroom")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_parasol")
      .build();

    FloraTypes.STINKHORN = FloraType
      .builder("stinkhorn")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_stinkhorn")
      .build();

    FloraTypes.WEEPING_MILK_CAP = FloraType
      .builder("weeping_milk_cap")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_weeping_milk_cap")
      .build();

    FloraTypes.WOOD_BLEWIT = FloraType
      .builder("wood_blewit")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_wood_blewit")
      .build();

    FloraTypes.WOOLLY_GOMPHUS = FloraType
      .builder("woolly_gomphus")
      .category(FloraCategories.MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 12)
      .movementMod(0.8D)
      .addOreDict("mushroom_woolly_gomphus")
      .build();

    FloraTypes.GLOWSHROOM = FloraType
      .builder("glowshroom")
      .category(FloraCategories.CAVE_MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 5)
      .movementMod(0.8D)
      .build();

    FloraTypes.BLUESHROOM = FloraType
      .builder("blueshroom")
      .category(FloraCategories.CAVE_MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 5)
      .movementMod(0.8D)
      .build();

    FloraTypes.MAGMA_SHROOM = FloraType
      .builder("magma_shroom")
      .category(FloraCategories.CAVE_MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 5)
      .movementMod(0.8D)
      .build();

    FloraTypes.POISON_SHROOM = FloraType
      .builder("poison_shroom")
      .category(FloraCategories.CAVE_MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 5)
      .movementMod(0.8D)
      .build();

    FloraTypes.SULPHUR_SHROOM = FloraType
      .builder("sulphur_shroom")
      .category(FloraCategories.CAVE_MUSHROOM)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(250f, 500f)
      .sun(0, 5)
      .movementMod(0.8D)
      .build();

    FloraTypes.BELL_TREE_DAHLIA = FloraType
      .builder("bell_tree_dahlia")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-12f, 36f)
      .rain(75f, 200f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.BIG_LEAF_PALM = FloraType
      .builder("big_leaf_palm")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.DRAKENSBERG_CYCAD = FloraType
      .builder("drakensberg_cycad")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.DWARF_SUGAR_PALM = FloraType
      .builder("dwarf_sugar_palm")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(300f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.GIANT_CANE = FloraType
      .builder("giant_cane")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 30f)
      .temp(-12f, 36f)
      .rain(150f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .addOreDict("sugarcane")
      .build();

    FloraTypes.GIANT_ELEPHANT_EAR = FloraType
      .builder("giant_elephant_ear")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.GIANT_FEATHER_GRASS = FloraType
      .builder("giant_feather_grass")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(15f, 23f)
      .temp(-29f, 32f)
      .rain(75f, 300f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.MADAGASCAR_OCOTILLO = FloraType
      .builder("madagascar_ocotillo")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(0, 15)
      .maxHeight(3)
      .movementMod(0.2D)
      .build();

    FloraTypes.MALAGASY_TREE_ALOE = FloraType
      .builder("malagasy_tree_aloe")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.MOUNTAIN_CABBAGE_TREE = FloraType
      .builder("mountain_cabbage_tree")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.PYGMY_DATE_PALM = FloraType
      .builder("pygmy_date_palm")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.QUEEN_SAGO = FloraType
      .builder("queen_sago")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.RED_SEALING_WAX_PALM = FloraType
      .builder("red_sealing_wax_palm")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.SUMMER_ASPHODEL = FloraType
      .builder("summer_asphodel")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0})
      .growthTemp(15f, 25f)
      .temp(-34f, 33f)
      .rain(150f, 300f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.ZIMBABWE_ALOE = FloraType
      .builder("zimbabwe_aloe")
      .category(FloraCategories.TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 30f)
      .temp(-34f, 36f)
      .rain(0f, 75f)
      .sun(0, 15)
      .maxHeight(2)
      .movementMod(0.2D)
      .build();

    FloraTypes.ANTHURIUM = FloraType
      .builder("anthurium")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1})
      .growthTemp(12f, 40f)
      .temp(-3f, 50f)
      .rain(290f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("anthurium")
      .build();

    FloraTypes.ARROWHEAD = FloraType
      .builder("arrowhead")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-10f, 22f)
      .temp(-25f, 37f)
      .rain(180f, 500f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.6D)
      .addOreDict("arrowhead")
      .build();

    FloraTypes.ARUNDO = FloraType
      .builder("arundo")
      .category(FloraCategories.TALL_PLANT)
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

    FloraTypes.BLUEGRASS = FloraType
      .builder("bluegrass")
      .category(FloraCategories.SHORT_GRASS)
      .stages(new int[]{0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0})
      .growthTemp(-4f, 12f)
      .temp(-19f, 27f)
      .rain(110f, 280f)
      .sun(0, 15)
      .movementMod(0.8D)
      .build();

    FloraTypes.BLUE_GINGER = FloraType
      .builder("blue_ginger")
      .category(FloraCategories.STANDARD)
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

    FloraTypes.BROMEGRASS = FloraType
      .builder("bromegrass")
      .category(FloraCategories.SHORT_GRASS)
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

    FloraTypes.BUR_REED = FloraType
      .builder("bur_reed")
      .category(FloraCategories.FLOATING)
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

    FloraTypes.DEAD_BUSH = FloraType
      .builder("dead_bush")
      .category(FloraCategories.DRY_TALL_PLANT)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-12f, 40f)
      .temp(-27f, 50f)
      .rain(70f, 120f)
      .sun(0, 15)
      .movementMod(0.9D)
      .addOreDict("dead_bush")
      .build();

    FloraTypes.DESERT_FLAME = FloraType
      .builder("desert_flame")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1})
      .growthTemp(0f, 20f)
      .temp(-15f, 35f)
      .rain(40f, 170f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("desert_flame")
      .build();

    FloraTypes.HELICONIA = FloraType
      .builder("heliconia")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 1, 2, 0, 0, 0, 0, 1, 2, 0, 0})
      .growthTemp(14f, 40f)
      .temp(-1f, 50f)
      .rain(320f, 500f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("heliconia")
      .build();

    FloraTypes.HIBISCUS = FloraType
      .builder("hibiscus")
      .category(FloraCategories.TALL_PLANT)
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

    FloraTypes.KANGAROO_PAW = FloraType
      .builder("kangaroo_paw")
      .category(FloraCategories.STANDARD)
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

    FloraTypes.LIPSTICK_PALM = FloraType
      .builder("lipstick_palm")
      .category(FloraCategories.TALL_PLANT)
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

    FloraTypes.MONSTERA_EPIPHYTE = FloraType
      .builder("monstera_epiphyte")
      .category(FloraCategories.EPIPHYTE)
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

    FloraTypes.PHRAGMITE = FloraType
      .builder("phragmite")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 1, 1, 1, 2, 2, 3, 1, 1, 0})
      .growthTemp(-6f, 18f)
      .temp(-21f, 33f)
      .rain(50f, 250f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.6D)
      .addOreDict("phragmite")
      .build();

    FloraTypes.PICKERELWEED = FloraType
      .builder("pickerelweed")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 0, 0})
      .growthTemp(-14f, 16f)
      .temp(-29f, 31f)
      .rain(200f, 500f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.6D)
      .addOreDict("pickerelweed")
      .build();

    FloraTypes.RADDIA_GRASS = FloraType
      .builder("raddia_grass")
      .category(FloraCategories.SHORT_GRASS)
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

    FloraTypes.SILVER_SPURFLOWER = FloraType
      .builder("silver_spurflower")
      .category(FloraCategories.STANDARD)
      .stages(new int[]{0, 0, 0, 0, 1, 2, 2, 2, 0, 0, 0, 0})
      .growthTemp(14f, 24f)
      .temp(-1f, 39f)
      .rain(230f, 400f)
      .sun(0, 15)
      .movementMod(0.8D)
      .addOreDict("silver_spurflower")
      .build();

    FloraTypes.WATER_TARO = FloraType
      .builder("water_taro")
      .category(FloraCategories.FLOATING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(12f, 40f)
      .temp(-3f, 50f)
      .rain(260f, 500f)
      .sun(0, 15)
      .waterDepth(1, 1)
      .movementMod(0.6D)
      .addOreDict("water_taro")
      .build();

    FloraTypes.RATTAN = FloraType
      .builder("rattan")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 40f)
      .temp(10f, 50f)
      .rain(210f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.BEARDED_MOSS = FloraType
      .builder("bearded_moss")
      .category(FloraCategories.HANGING_TALL)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-10f, 22f)
      .temp(-25f, 37f)
      .rain(180f, 500f)
      .sun(0, 15)
      .maxHeight(10)
      .movementMod(0.7D)
      .addOreDict("vine")
      .build();

    FloraTypes.BEARDED_MOSS_CREEPING = FloraType
      .builder("bearded_moss_creeping")
      .category(FloraCategories.HANGING_CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-10f, 22f)
      .temp(-25f, 37f)
      .rain(180f, 500f)
      .sun(0, 15)
      .maxHeight(10)
      .movementMod(0.7D)
      .addOreDict("vine")
      .build();

    FloraTypes.BLUE_SKYFLOWER = FloraType
      .builder("blue_skyflower")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(15f, 50f)
      .rain(300f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.GLOW_VINE = FloraType
      .builder("glow_vine")
      .category(FloraCategories.HANGING_TALL)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 50f)
      .temp(10f, 50f)
      .rain(210f, 500f)
      .sun(0, 15)
      .maxHeight(15)
      .movementMod(0.7D)
      .addOreDict("vine")
      .build();

    FloraTypes.GLOW_VINE_CREEPING = FloraType
      .builder("glow_vine_creeping")
      .category(FloraCategories.HANGING_CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(18f, 50f)
      .temp(10f, 50f)
      .rain(210f, 500f)
      .sun(0, 15)
      .maxHeight(15)
      .movementMod(0.7D)
      .addOreDict("vine")
      .build();

    FloraTypes.HANGING_VINE = FloraType
      .builder("hanging_vine")
      .category(FloraCategories.HANGING_TALL)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 50f)
      .temp(13f, 50f)
      .rain(150f, 470f)
      .sun(0, 15)
      .maxHeight(22)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.HANGING_VINE_CREEPING = FloraType
      .builder("hanging_vine_creeping")
      .category(FloraCategories.HANGING_CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 50f)
      .temp(13f, 50f)
      .rain(150f, 470f)
      .sun(0, 15)
      .maxHeight(22)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.JADE_VINE = FloraType
      .builder("jade_vine")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.JAPANESE_IVY = FloraType
      .builder("japanese_ivy")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 35f)
      .temp(-6f, 36f)
      .rain(120f, 300f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.JUNGLE_VINE = FloraType
      .builder("jungle_vine")
      .category(FloraCategories.HANGING_TALL)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 50f)
      .temp(15f, 50f)
      .rain(150f, 470f)
      .sun(0, 15)
      .maxHeight(22)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.JUNGLE_VINE_CREEPING = FloraType
      .builder("jungle_vine_creeping")
      .category(FloraCategories.HANGING_CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 50f)
      .temp(15f, 50f)
      .rain(150f, 470f)
      .sun(0, 15)
      .maxHeight(22)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.LIANA = FloraType
      .builder("liana")
      .category(FloraCategories.HANGING_TALL)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 50f)
      .temp(15f, 50f)
      .rain(150f, 470f)
      .sun(0, 15)
      .maxHeight(16)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.LIANA_CREEPING = FloraType
      .builder("liana_creeping")
      .category(FloraCategories.HANGING_CREEPING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 50f)
      .temp(15f, 50f)
      .rain(150f, 470f)
      .sun(0, 15)
      .maxHeight(16)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.MADEIRA_VINE = FloraType
      .builder("madeira_vine")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(5f, 50f)
      .rain(100f, 350f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.MYSORE_TRUMPETVINE = FloraType
      .builder("mysore_trumpetvine")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(10f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.SILVERVEIN_CREEPER = FloraType
      .builder("silvervein_creeper")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(0f, 40f)
      .temp(15f, 50f)
      .rain(300f, 500f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.SWEDISH_IVY = FloraType
      .builder("swedish_ivy")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(5f, 50f)
      .rain(50f, 300f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.VARIEGATED_PERSIAN_IVY = FloraType
      .builder("variegated_persian_ivy")
      .category(FloraCategories.HANGING)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(20f, 40f)
      .temp(5f, 50f)
      .rain(50f, 300f)
      .sun(0, 15)
      .maxHeight(5)
      .movementMod(0.5D)
      .addOreDict("vine")
      .build();

    FloraTypes.BADDERLOCKS = FloraType
      .builder("badderlocks")
      .category(FloraCategories.TALL_WATER_SEA)
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

    FloraTypes.COONTAIL = FloraType
      .builder("coontail")
      .category(FloraCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(-11f, 31f)
      .temp(-13f, 33f)
      .rain(250f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.7D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.EEL_GRASS = FloraType
      .builder("eel_grass")
      .category(FloraCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(-7f, 48f)
      .temp(-9f, 50f)
      .rain(200f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.GIANT_KELP = FloraType
      .builder("giant_kelp")
      .category(FloraCategories.TALL_WATER_SEA)
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

    FloraTypes.GUTWEED = FloraType
      .builder("gutweed")
      .category(FloraCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-19f, 31f)
      .temp(-21f, 33f)
      .rain(100f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.HORNWORT = FloraType
      .builder("hornwort")
      .category(FloraCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-11f, 31f)
      .temp(-13f, 33f)
      .rain(250f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.7D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.LAMINARIA = FloraType
      .builder("laminaria")
      .category(FloraCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-29f, 17f)
      .temp(-31f, 19f)
      .rain(250f, 400f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.6D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.LEAFY_KELP = FloraType
      .builder("leafy_kelp")
      .category(FloraCategories.TALL_WATER_SEA)
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

    FloraTypes.MANATEE_GRASS = FloraType
      .builder("manatee_grass")
      .category(FloraCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(-1f, 48f)
      .temp(-3f, 50f)
      .rain(250f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.MILFOIL = FloraType
      .builder("milfoil")
      .category(FloraCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-27f, 35f)
      .temp(-29f, 37f)
      .rain(250f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.7D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.PONDWEED = FloraType
      .builder("pondweed")
      .category(FloraCategories.TALL_WATER)
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

    FloraTypes.RED_ALGAE = FloraType
      .builder("red_algae")
      .category(FloraCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(1f, 48f)
      .temp(-1f, 50f)
      .rain(150f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.RED_SEA_WHIP = FloraType
      .builder("red_sea_whip")
      .category(FloraCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(1f, 48f)
      .temp(-1f, 50f)
      .rain(150f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.SAGO = FloraType
      .builder("sago")
      .category(FloraCategories.WATER)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-32f, 36f)
      .temp(-34f, 38f)
      .rain(0f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.SEA_ANEMONE = FloraType
      .builder("sea_anemone")
      .category(FloraCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(1f, 48f)
      .temp(-1f, 50f)
      .rain(150f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.SEAGRASS = FloraType
      .builder("seagrass")
      .category(FloraCategories.TALL_WATER_SEA)
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

    FloraTypes.SEAWEED = FloraType
      .builder("seaweed")
      .category(FloraCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0})
      .growthTemp(-19f, 40f)
      .temp(-21f, 50f)
      .rain(0f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.STAR_GRASS = FloraType
      .builder("star_grass")
      .category(FloraCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(-11f, 48f)
      .temp(-13f, 50f)
      .rain(50f, 260f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.TURTLE_GRASS = FloraType
      .builder("turtle_grass")
      .category(FloraCategories.WATER_SEA)
      .stages(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}) //3, 0, 0, 0, 0, 1, 2, 2, 2, 2, 2, 2
      .growthTemp(1f, 48f)
      .temp(-1f, 50f)
      .rain(240f, 500f)
      .sun(0, 15)
      .waterDepth(1, 256)
      .movementMod(0.9D)
      .addOreDict("seaweed")
      .build();

    FloraTypes.WINGED_KELP = FloraType
      .builder("winged_kelp")
      .category(FloraCategories.TALL_WATER_SEA)
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
