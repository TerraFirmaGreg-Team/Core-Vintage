package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.data.enums.EnumGradeOre;
import su.terrafirmagreg.modules.device.object.recipe.quern.IQuernRecipeManager;
import su.terrafirmagreg.modules.plant.init.BlocksPlant;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypes;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import net.dries007.caffeineaddon.init.ModItems;
import net.dries007.eerussianguy.firmalife.init.FoodFL;
import net.dries007.eerussianguy.firmalife.init.Fruit;
import net.dries007.eerussianguy.firmalife.registry.ItemsFL;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import net.dries007.tfc.objects.items.metal.ItemSmallOre;
import net.dries007.tfc.util.agriculture.Food;
import net.dries007.tfcthings.init.TFCThingsItems;

import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.ALLIUM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.ANTHURIUM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.APACHE_DWARF;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.ARROWHEAD;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.ARTISTS_CONK;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.ARUNDO;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.ATHYRIUM_FERN;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BADDERLOCKS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BARREL_CACTUS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BEARDED_MOSS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BELL_TREE_DAHLIA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BIG_LEAF_PALM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BLACK_ORCHID;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BLUEGRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BLUESHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BLUE_GINGER;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BLUE_ORCHID;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BLUE_SKYFLOWER;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BROMEGRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BROMELIA_HEMISPHERICA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BROMELIA_LACINIOSA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BUNCH_GRASS_FLOATING;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BUNCH_GRASS_REED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BURNING_BUSH;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BUR_REED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.BUTTERFLY_MILKWEED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CALENDULA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CANNA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CATTAIL;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CAT_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CHAMOMILE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CINNAMON_FERN;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CLIMBING_CACTUS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.COMMON_MISTLETOE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.COONTAIL;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CORD_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CREEPING_MISTLETOE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CRIMSON_CATTLEYA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CROWNGRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.CUTHBERTS_DENDROBIUM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.DANDELION;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.DESERT_FLAME;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.DEVILS_TONGUE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.DRAKENSBERG_CYCAD;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.DWARF_SUGAR_PALM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.EEL_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.FISH_BONE_CACTUS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.FOXGLOVE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.FRAGRANT_FERN;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GIANT_CANE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GIANT_ELEPHANT_EAR;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GIANT_FEATHER_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GIANT_KELP;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GLOWSHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GLOW_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GOLDENROD;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GOOSEGRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GRAPE_HYACINTH;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GUTWEED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.GUZMANIA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HALFA_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HANGING_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HARLEQUIN_MISTLETOE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HELICONIA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HIBISCUS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HORNWORT;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HOUSTONIA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.HYDRANGEA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.IVY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.JADE_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.JAPANESE_IVY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.JAPANESE_PIERIS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.JUNGLE_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.KAIETEUR_FALLS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.KANGAROO_PAW;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.KING_FERN;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.KING_ORCHID;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LABRADOR_TEA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LAMINARIA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LANTERN_OF_THE_FOREST;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LARGE_FOOT_DENDROBIUM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LAVANDULA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LEAFY_KELP;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LEYMUS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LIANA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LILAC;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LILY_OF_THE_VALLEY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.LIPSTICK_PALM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MADAGASCAR_OCOTILLO;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MADEIRA_VINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MAGMA_SHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MALAGASY_TREE_ALOE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MANATEE_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MARIGOLD;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MARRAM_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MATTEUCCIA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MEADS_MILKWEED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MILFOIL;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MONSTERA_EPIPHYTE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MORNING_GLORY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MOSS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MOUNTAIN_CABBAGE_TREE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.MYSORE_TRUMPETVINE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.NASTURTIUM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.OXEYE_DAISY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PAPYRUS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PEONY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PEROVSKIA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PHRAGMITE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PICKERELWEED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.POISON_SHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PONDWEED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.POPPY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PORCINI;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PRAIRIE_JUNEGRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PRIMROSE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PULSATILLA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.PYGMY_DATE_PALM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.QUEEN_SAGO;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.RATTAN;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.RED_ALGAE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.RED_SEALING_WAX_PALM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.RED_SEA_WHIP;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.REED_MANNAGRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.REINDEER_LICHEN;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.ROSE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.ROUGH_HORSETAIL;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SACRED_DATURA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SAGO;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SAPPHIRE_TOWER;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SARGASSUM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SEAGRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SEAWEED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SEA_ANEMONE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SEA_OATS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SILVERVEIN_CREEPER;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SKY_PLANT;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SNAPDRAGON_PINK;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SNAPDRAGON_RED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SNAPDRAGON_WHITE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SNAPDRAGON_YELLOW;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SPANISH_MOSS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.STAR_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.STRELITZIA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SUGAR_CANE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SULPHUR_SHELF;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SULPHUR_SHROOM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SUMMER_ASPHODEL;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SUNFLOWER;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.SWEDISH_IVY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TACKWEED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TAKAKIA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TAMPA_BUTTERFLY_ORCHID;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TRILLIUM;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TROPICAL_MILKWEED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TULIP_ORANGE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TULIP_PINK;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TULIP_RED;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TULIP_WHITE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TURKEY_TAIL;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.TURTLE_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.UNDERGROWTH_SHRUB;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.UNDERGROWTH_SHRUB_SMALL;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.VARIEGATED_PERSIAN_IVY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.VOODOO_LILY;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.VRIESEA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.WATER_CANNA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.WHEATGRASS;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.WILDFIRE;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.WINGED_KELP;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.WOOLLY_BUSH;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.YUCCA;
import static su.terrafirmagreg.modules.plant.api.types.type.PlantTypes.ZIMBABWE_ALOE;
import static su.terrafirmagreg.modules.rock.api.types.type.RockTypes.QUARTZITE;

public final class RecipesDevice {

  public static void onRegister() {

    //quernRecipes(RegistriesDevice.QUERN);

  }

  private static void quernRecipes(IQuernRecipeManager manager) {
    //Grain
    manager.addRecipe(IIngredient.of("grainBarley"),
                      new ItemStack(ItemFoodTFC.get(Food.BARLEY_FLOUR), 1));
    manager.addRecipe(IIngredient.of("grainOat"),
                      new ItemStack(ItemFoodTFC.get(Food.OAT_FLOUR), 1));
    manager.addRecipe(IIngredient.of("grainRice"),
                      new ItemStack(ItemFoodTFC.get(Food.RICE_FLOUR), 1));
    manager.addRecipe(IIngredient.of("grainRye"),
                      new ItemStack(ItemFoodTFC.get(Food.RYE_FLOUR), 1));
    manager.addRecipe(IIngredient.of("grainWheat"),
                      new ItemStack(ItemFoodTFC.get(Food.WHEAT_FLOUR), 1));
    manager.addRecipe(IIngredient.of("grainMaize"),
                      new ItemStack(ItemFoodTFC.get(Food.CORNMEAL_FLOUR), 1));

    manager.addRecipe(IIngredient.of(IIngredient.of(ItemFoodTFC.get(Food.OLIVE))),
                      new ItemStack(ItemsTFC.OLIVE_PASTE, 1));

    //Flux
    manager.addRecipe(IIngredient.of("gemBorax"), new ItemStack(ItemPowder.get(Powder.FLUX), 6));
    manager.addRecipe(IIngredient.of("rockFlux"), new ItemStack(ItemPowder.get(Powder.FLUX), 2));

    //Redstone
    manager.addRecipe(IIngredient.of("gemCinnabar"), new ItemStack(Items.REDSTONE, 8));
    manager.addRecipe(IIngredient.of("gemCryolite"), new ItemStack(Items.REDSTONE, 8));

    //Hematite
    manager.addRecipe(IIngredient.of(ItemSmallOre.get(Ore.HEMATITE, 1)),
                      new ItemStack(ItemPowder.get(Powder.HEMATITE), 2));
    manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, EnumGradeOre.POOR, 1)),
                      new ItemStack(ItemPowder.get(Powder.HEMATITE), 3));
    manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, EnumGradeOre.NORMAL, 1)),
                      new ItemStack(ItemPowder.get(Powder.HEMATITE), 5));
    manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, EnumGradeOre.RICH, 1)),
                      new ItemStack(ItemPowder.get(Powder.HEMATITE), 7));

    //Limonite
    manager.addRecipe(IIngredient.of(ItemSmallOre.get(Ore.LIMONITE, 1)),
                      new ItemStack(ItemPowder.get(Powder.LIMONITE), 2));
    manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, EnumGradeOre.POOR, 1)),
                      new ItemStack(ItemPowder.get(Powder.LIMONITE), 3));
    manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, EnumGradeOre.NORMAL, 1)),
                      new ItemStack(ItemPowder.get(Powder.LIMONITE), 5));
    manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, EnumGradeOre.RICH, 1)),
                      new ItemStack(ItemPowder.get(Powder.LIMONITE), 7));

    //Malachite
    manager.addRecipe(IIngredient.of(ItemSmallOre.get(Ore.MALACHITE, 1)),
                      new ItemStack(ItemPowder.get(Powder.MALACHITE), 2));
    manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, EnumGradeOre.POOR, 1)),
                      new ItemStack(ItemPowder.get(Powder.MALACHITE), 3));
    manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, EnumGradeOre.NORMAL, 1)),
                      new ItemStack(ItemPowder.get(Powder.MALACHITE), 5));
    manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, EnumGradeOre.RICH, 1)),
                      new ItemStack(ItemPowder.get(Powder.MALACHITE), 7));

    //Bone meal
    manager.addRecipe(IIngredient.of("bone"),
                      new ItemStack(Items.DYE, 3, EnumDyeColor.WHITE.getDyeDamage()));
    manager.addRecipe(IIngredient.of("blockBone"),
                      new ItemStack(Items.DYE, 9, EnumDyeColor.WHITE.getDyeDamage()));

    //Dye from plants
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(HOUSTONIA)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(OXEYE_DAISY)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 1));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(PRIMROSE)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(SNAPDRAGON_WHITE)),
      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(TRILLIUM)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 1));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(SPANISH_MOSS)),
      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(TULIP_WHITE)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 1));

    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(BUTTERFLY_MILKWEED)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(CANNA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(NASTURTIUM)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(STRELITZIA)),
                      new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(TULIP_ORANGE)),
      new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(WATER_CANNA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));

    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(ATHYRIUM_FERN)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(MORNING_GLORY)),
      new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(PULSATILLA)),
                      new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage()));

    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(LABRADOR_TEA)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(SAPPHIRE_TOWER)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()));

    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(CALENDULA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(DANDELION)),
                      new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(MEADS_MILKWEED)),
      new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GOLDENROD)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(SNAPDRAGON_YELLOW)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));

    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(MOSS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.LIME.getDyeDamage()));

    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(FOXGLOVE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(SACRED_DATURA)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(TULIP_PINK)),
                      new ItemStack(Items.DYE, 1, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(SNAPDRAGON_PINK)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));

    //No gray :c
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(YUCCA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.SILVER.getDyeDamage()));

    //No Cyan :c
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(ALLIUM)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(BLACK_ORCHID)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(PEROVSKIA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));

    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BLUE_ORCHID)),
                      new ItemStack(ItemsTFC.DYE_BLUE, 2));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(GRAPE_HYACINTH)),
      new ItemStack(ItemsTFC.DYE_BLUE, 2));

    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(ROUGH_HORSETAIL)),
      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SARGASSUM)),
                      new ItemStack(ItemsTFC.DYE_BROWN, 2));

    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(BARREL_CACTUS)),
      new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(REINDEER_LICHEN)),
      new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));

    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GUZMANIA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(POPPY)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(PORCINI)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(ROSE)),
                      new ItemStack(Items.DYE, 4, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(SNAPDRAGON_RED)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(TROPICAL_MILKWEED)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(TULIP_RED)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(VRIESEA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));

    //Misc
    manager.addRecipe(IIngredient.of("gemSylvite"),
                      new ItemStack(ItemPowder.get(Powder.FERTILIZER), 4));
    manager.addRecipe(IIngredient.of("gemSulfur"), new ItemStack(ItemPowder.get(Powder.SULFUR), 4));
    manager.addRecipe(IIngredient.of("gemSaltpeter"),
                      new ItemStack(ItemPowder.get(Powder.SALTPETER), 4));
    manager.addRecipe(IIngredient.of("charcoal"),
                      new ItemStack(ItemPowder.get(Powder.CHARCOAL), 4));
    manager.addRecipe(IIngredient.of("rockRocksalt"),
                      new ItemStack(ItemPowder.get(Powder.SALT), 4));
    manager.addRecipe(IIngredient.of("stickBlaze"), new ItemStack(Items.BLAZE_POWDER, 2));
    manager.addRecipe(IIngredient.of("gemLapis"),
                      new ItemStack(ItemPowder.get(Powder.LAPIS_LAZULI), 4));
    manager.addRecipe(IIngredient.of("gemGraphite"),
                      new ItemStack(ItemPowder.get(Powder.GRAPHITE), 4));
    manager.addRecipe(IIngredient.of("gemKaolinite"),
                      new ItemStack(ItemPowder.get(Powder.KAOLINITE), 4));
    manager.addRecipe(IIngredient.of(BlocksRock.RAW.get(RockTypes.LIMESTONE)),
                      new ItemStack(ItemsTFC.GYPSUM));

    manager.addRecipe(IIngredient.of("logWoodLogwood"),
                      new ItemStack((ItemsTFCF.LOGWOOD_CHIPS), 3));
    manager.addRecipe(IIngredient.of("sugarcane"), new ItemStack((ItemsTFCF.MASHED_SUGAR_CANE)));
    manager.addRecipe(IIngredient.of("cropSugarBeet"),
                      new ItemStack((ItemsTFCF.MASHED_SUGAR_BEET)));
    manager.addRecipe(IIngredient.of("grainAmaranth"), new ItemStack((ItemsTFCF.AMARANTH_FLOUR)));
    manager.addRecipe(IIngredient.of("grainBuckwheat"), new ItemStack((ItemsTFCF.BUCKWHEAT_FLOUR)));
    manager.addRecipe(IIngredient.of("grainFonio"), new ItemStack((ItemsTFCF.FONIO_FLOUR)));
    manager.addRecipe(IIngredient.of("grainMillet"), new ItemStack((ItemsTFCF.MILLET_FLOUR)));
    manager.addRecipe(IIngredient.of("grainQuinoa"), new ItemStack((ItemsTFCF.QUINOA_FLOUR)));
    manager.addRecipe(IIngredient.of("grainSpelt"), new ItemStack((ItemsTFCF.SPELT_FLOUR)));
    manager.addRecipe(IIngredient.of(ItemsTFCF.CASSIA_CINNAMON_BARK),
                      new ItemStack(ItemsTFCF.GROUND_CASSIA_CINNAMON, 2));
    manager.addRecipe(IIngredient.of(ItemsTFCF.CEYLON_CINNAMON_BARK),
                      new ItemStack(ItemsTFCF.GROUND_CEYLON_CINNAMON, 2));
    //manager.addRecipe(IIngredient.of(ItemsTFCF.BLACK_PEPPER), new ItemStack(ItemsTFCF.GROUND_BLACK_PEPPER, 2));
    manager.addRecipe(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS),
                      new ItemStack(ItemsTFCF.COFFEE_POWDER, 2));
    manager.addRecipe(IIngredient.of("pearl"), new ItemStack(ItemPowder.get(Powder.PEARL)));
    manager.addRecipe(IIngredient.of("pearlBlack"),
                      new ItemStack(ItemPowder.get(Powder.BLACK_PEARL)));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(PAPYRUS)),
                      new ItemStack(ItemsTFCF.PAPYRUS_PULP, 3));

    // Dye from plants
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(CHAMOMILE)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(HYDRANGEA)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(LILY_OF_THE_VALLEY)),
      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(IIngredient.of("cropMadder"),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of("cropWoad"), new ItemStack(ItemsTFC.DYE_BLUE, 2));
    manager.addRecipe(IIngredient.of("cropIndigo"), new ItemStack(ItemsTFC.DYE_BLUE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SUNFLOWER)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(IIngredient.of("cropWeld"),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(IIngredient.of("cropRape"), new ItemStack(ItemsTFC.DYE_BLUE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(LILAC)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(PEONY)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(LAVANDULA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(CATTAIL)),
                      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(IIngredient.of("cropAgave"),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SUGAR_CANE)),
                      new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BADDERLOCKS)),
                      new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GUTWEED)),
                      new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SAGO)),
                      new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of("resin"),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(IIngredient.of("treeLeavesTeak"),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SUGAR_CANE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(TACKWEED)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(TAKAKIA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(VOODOO_LILY)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(DEVILS_TONGUE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(BROMELIA_HEMISPHERICA)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(BROMELIA_LACINIOSA)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(KAIETEUR_FALLS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(MATTEUCCIA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(CORD_GRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(REED_MANNAGRASS)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(PRAIRIE_JUNEGRASS)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(WOOLLY_BUSH)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(CINNAMON_FERN)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(JAPANESE_PIERIS)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BURNING_BUSH)),
                      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(UNDERGROWTH_SHRUB)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(UNDERGROWTH_SHRUB_SMALL)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SEA_OATS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(BUNCH_GRASS_FLOATING)),
      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(BUNCH_GRASS_REED)),
      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(CROWNGRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(CAT_GRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GOOSEGRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(WHEATGRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(HALFA_GRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(LEYMUS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(MARRAM_GRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    //manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PlantTypes.WILD_BARLEY)), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    //manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PlantTypes.WILD_RICE)), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    //manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PlantTypes.WILD_WHEAT)), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(RATTAN)),
                      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GLOW_VINE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BLUE_SKYFLOWER)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(JADE_VINE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(JAPANESE_IVY)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(MADEIRA_VINE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(MYSORE_TRUMPETVINE)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(SILVERVEIN_CREEPER)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SWEDISH_IVY)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(VARIEGATED_PERSIAN_IVY)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(APACHE_DWARF)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(ARTISTS_CONK)),
                      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(CLIMBING_CACTUS)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(CRIMSON_CATTLEYA)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(CREEPING_MISTLETOE)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(CUTHBERTS_DENDROBIUM)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(FISH_BONE_CACTUS)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(FRAGRANT_FERN)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(HARLEQUIN_MISTLETOE)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(KING_ORCHID)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(LANTERN_OF_THE_FOREST)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(LARGE_FOOT_DENDROBIUM)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(COMMON_MISTLETOE)),
      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SKY_PLANT)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SULPHUR_SHELF)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(TAMPA_BUTTERFLY_ORCHID)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(TURKEY_TAIL)),
                      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(WILDFIRE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(BELL_TREE_DAHLIA)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BIG_LEAF_PALM)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(DRAKENSBERG_CYCAD)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(DWARF_SUGAR_PALM)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GIANT_CANE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(GIANT_ELEPHANT_EAR)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(GIANT_FEATHER_GRASS)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(MADAGASCAR_OCOTILLO)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(MALAGASY_TREE_ALOE)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(MOUNTAIN_CABBAGE_TREE)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(PYGMY_DATE_PALM)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(QUEEN_SAGO)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(RED_SEALING_WAX_PALM)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(SUMMER_ASPHODEL)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(ZIMBABWE_ALOE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(ANTHURIUM)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(ARROWHEAD)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(ARUNDO)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BLUEGRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BLUE_GINGER)),
                      new ItemStack(ItemsTFC.DYE_BLUE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BROMEGRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BUR_REED)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(DESERT_FLAME)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(HELICONIA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(HIBISCUS)),
                      new ItemStack(ItemsTFC.DYE_WHITE, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(KANGAROO_PAW)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(KING_FERN)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(LIPSTICK_PALM)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(MARIGOLD)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
    manager.addRecipe(
      IIngredient.of(BlocksPlant.PLANT.get(MONSTERA_EPIPHYTE)),
      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    //manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PlantTypes.MONSTERA_GROUND)), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(PHRAGMITE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(PICKERELWEED)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BADDERLOCKS)),
                      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(COONTAIL)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(EEL_GRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GIANT_KELP)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GUTWEED)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(HORNWORT)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(LAMINARIA)),
                      new ItemStack(ItemsTFC.DYE_BROWN, 2));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(LEAFY_KELP)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(MANATEE_GRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(MILFOIL)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(PONDWEED)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SAGO)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SEAGRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SEAWEED)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(STAR_GRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(TURTLE_GRASS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(WINGED_KELP)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(RED_ALGAE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(RED_SEA_WHIP)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SEA_ANEMONE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BEARDED_MOSS)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GLOW_VINE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(HANGING_VINE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(JUNGLE_VINE)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(LIANA)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(IVY)),
                      new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(BLUESHROOM)),
                      new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(GLOWSHROOM)),
                      new ItemStack(Items.GLOWSTONE_DUST, 1));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(MAGMA_SHROOM)), new ItemStack(Items.MAGMA_CREAM, 1));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(POISON_SHROOM)),
                      new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage()));
    manager.addRecipe(IIngredient.of(BlocksPlant.PLANT.get(SULPHUR_SHROOM)),
                      new ItemStack(ItemPowder.get(Powder.SULFUR), 1));
    manager.addRecipe(IIngredient.of(BlocksTFCF.LIGHTSTONE),
                      new ItemStack(Items.GLOWSTONE_DUST, 2));

    manager.addRecipe(IIngredient.of(ItemsFL.getDriedFruit(Fruit.COFFEE_CHERRIES)),
                      new ItemStack(ModItems.GreenCoffeeBeans, 1));
    manager.addRecipe(IIngredient.of(ModItems.CoffeeBeans),
                      new ItemStack(ModItems.GroundCoffee, 1));
    manager.addRecipe(IIngredient.of(ItemsRock.BRICK.get(QUARTZITE)),
                      new ItemStack((TFCThingsItems.ITEM_WHETSTONE)));

    manager.addRecipe(IIngredient.of("gemHalite"), new ItemStack(ItemPowder.get(Powder.SALT), 2));
    manager.addRecipe(IIngredient.of(ItemsFL.CINNAMON), new ItemStack(ItemsFL.GROUND_CINNAMON, 2));
    manager.addRecipe(IIngredient.of(ItemsFL.getFood(FoodFL.ROASTED_CHESTNUTS)),
                      new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUT_FLOUR)));
    manager.addRecipe(IIngredient.of(ItemFoodTFC.get(Food.SOYBEAN)),
                      new ItemStack(ItemsFL.getFood(FoodFL.GROUND_SOYBEANS)));
    manager.addRecipe(IIngredient.of(ItemFoodTFC.get(Food.TOMATO)),
                      new ItemStack(ItemsFL.getFood(FoodFL.TOMATO_SAUCE)));

  }

}
