package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.modules.device.objects.recipes.quern.IQuernRecipeManager;
import su.terrafirmagreg.modules.rock.api.types.type.RockTypes;
import su.terrafirmagreg.modules.rock.init.BlocksRock;

import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;


import com.eerussianguy.firmalife.init.FoodFL;
import com.eerussianguy.firmalife.init.Fruit;
import com.eerussianguy.firmalife.registry.ItemsFL;
import lyeoj.tfcthings.init.TFCThingsItems;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientItemFood;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;
import net.dries007.tfc.objects.items.metal.ItemSmallOre;
import net.dries007.tfc.objects.items.rock.ItemBrickTFC;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.types.DefaultRocks;
import net.dries007.tfc.util.agriculture.Food;
import pieman.caffeineaddon.init.ModItems;
import tfcflorae.objects.PowderTFCF;
import tfcflorae.objects.blocks.BlocksTFCF;
import tfcflorae.objects.items.ItemPowderTFCF;
import tfcflorae.objects.items.ItemsTFCF;
import tfcflorae.types.PlantsTFCF;

import static net.dries007.tfc.api.registries.TFCRegistries.PLANTS;
import static net.dries007.tfc.api.registries.TFCRegistries.ROCKS;

public final class RecipesDevice {

    public static void onRegister() {

        quernRecipes(RegistriesDevice.QUERN);

    }

    private static void quernRecipes(IQuernRecipeManager manager) {
        //Grain
        manager.addRecipe(IIngredient.of("grainBarley"), new ItemStack(ItemFoodTFC.get(Food.BARLEY_FLOUR), 1));
        manager.addRecipe(IIngredient.of("grainOat"), new ItemStack(ItemFoodTFC.get(Food.OAT_FLOUR), 1));
        manager.addRecipe(IIngredient.of("grainRice"), new ItemStack(ItemFoodTFC.get(Food.RICE_FLOUR), 1));
        manager.addRecipe(IIngredient.of("grainRye"), new ItemStack(ItemFoodTFC.get(Food.RYE_FLOUR), 1));
        manager.addRecipe(IIngredient.of("grainWheat"), new ItemStack(ItemFoodTFC.get(Food.WHEAT_FLOUR), 1));
        manager.addRecipe(IIngredient.of("grainMaize"), new ItemStack(ItemFoodTFC.get(Food.CORNMEAL_FLOUR), 1));

        manager.addRecipe(IngredientItemFood.of(IIngredient.of(ItemFoodTFC.get(Food.OLIVE))), new ItemStack(ItemsTFC.OLIVE_PASTE, 1));

        //Flux
        manager.addRecipe(IIngredient.of("gemBorax"), new ItemStack(ItemPowder.get(Powder.FLUX), 6));
        manager.addRecipe(IIngredient.of("rockFlux"), new ItemStack(ItemPowder.get(Powder.FLUX), 2));

        //Redstone
        manager.addRecipe(IIngredient.of("gemCinnabar"), new ItemStack(Items.REDSTONE, 8));
        manager.addRecipe(IIngredient.of("gemCryolite"), new ItemStack(Items.REDSTONE, 8));

        //Hematite
        manager.addRecipe(IIngredient.of(ItemSmallOre.get(Ore.HEMATITE, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 2));
        manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 3));
        manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 5));
        manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 7));

        //Limonite
        manager.addRecipe(IIngredient.of(ItemSmallOre.get(Ore.LIMONITE, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 2));
        manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 3));
        manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 5));
        manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 7));

        //Malachite
        manager.addRecipe(IIngredient.of(ItemSmallOre.get(Ore.MALACHITE, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 2));
        manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 3));
        manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 5));
        manager.addRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 7));

        //Bone meal
        manager.addRecipe(IIngredient.of("bone"), new ItemStack(Items.DYE, 3, EnumDyeColor.WHITE.getDyeDamage()));
        manager.addRecipe(IIngredient.of("blockBone"), new ItemStack(Items.DYE, 9, EnumDyeColor.WHITE.getDyeDamage()));

        //Dye from plants
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.HOUSTONIA))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.OXEYE_DAISY))), new ItemStack(ItemsTFC.DYE_WHITE, 1));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.PRIMROSE))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.SNAPDRAGON_WHITE))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.TRILLIUM))), new ItemStack(ItemsTFC.DYE_WHITE, 1));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.SPANISH_MOSS))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.TULIP_WHITE))), new ItemStack(ItemsTFC.DYE_WHITE, 1));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.BUTTERFLY_MILKWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.CANNA))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.NASTURTIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.STRELITZIA))), new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.TULIP_ORANGE))), new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.WATER_CANNA))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.ATHYRIUM_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.MORNING_GLORY))), new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.PULSATILLA))), new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage()));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.LABRADOR_TEA))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.SAPPHIRE_TOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.CALENDULA))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.DANDELION))), new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.MEADS_MILKWEED))), new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.GOLDENROD))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.SNAPDRAGON_YELLOW))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.MOSS))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIME.getDyeDamage()));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.FOXGLOVE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.SACRED_DATURA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.TULIP_PINK))), new ItemStack(Items.DYE, 1, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.SNAPDRAGON_PINK))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));

        //No gray :c
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.YUCCA))), new ItemStack(Items.DYE, 2, EnumDyeColor.SILVER.getDyeDamage()));

        //No Cyan :c
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.ALLIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.BLACK_ORCHID))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.PEROVSKIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.BLUE_ORCHID))), new ItemStack(ItemsTFC.DYE_BLUE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.GRAPE_HYACINTH))), new ItemStack(ItemsTFC.DYE_BLUE, 2));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.ROUGH_HORSETAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.SARGASSUM))), new ItemStack(ItemsTFC.DYE_BROWN, 2));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.BARREL_CACTUS))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.REINDEER_LICHEN))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));

        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.GUZMANIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.POPPY))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.PORCINI))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.ROSE))), new ItemStack(Items.DYE, 4, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.SNAPDRAGON_RED))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.TROPICAL_MILKWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.TULIP_RED))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(DefaultPlants.VRIESEA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));

        //Misc
        manager.addRecipe(IIngredient.of("gemSylvite"), new ItemStack(ItemPowder.get(Powder.FERTILIZER), 4));
        manager.addRecipe(IIngredient.of("gemSulfur"), new ItemStack(ItemPowder.get(Powder.SULFUR), 4));
        manager.addRecipe(IIngredient.of("gemSaltpeter"), new ItemStack(ItemPowder.get(Powder.SALTPETER), 4));
        manager.addRecipe(IIngredient.of("charcoal"), new ItemStack(ItemPowder.get(Powder.CHARCOAL), 4));
        manager.addRecipe(IIngredient.of("rockRocksalt"), new ItemStack(ItemPowder.get(Powder.SALT), 4));
        manager.addRecipe(IIngredient.of("stickBlaze"), new ItemStack(Items.BLAZE_POWDER, 2));
        manager.addRecipe(IIngredient.of("gemLapis"), new ItemStack(ItemPowder.get(Powder.LAPIS_LAZULI), 4));
        manager.addRecipe(IIngredient.of("gemGraphite"), new ItemStack(ItemPowder.get(Powder.GRAPHITE), 4));
        manager.addRecipe(IIngredient.of("gemKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE), 4));
        manager.addRecipe(IIngredient.of(BlocksRock.RAW.get(RockTypes.LIMESTONE)), new ItemStack(ItemsTFC.GYPSUM));

        manager.addRecipe(IIngredient.of("logWoodLogwood"), new ItemStack((ItemsTFCF.LOGWOOD_CHIPS), 3));
        manager.addRecipe(IIngredient.of("sugarcane"), new ItemStack((ItemsTFCF.MASHED_SUGAR_CANE)));
        manager.addRecipe(IIngredient.of("cropSugarBeet"), new ItemStack((ItemsTFCF.MASHED_SUGAR_BEET)));
        manager.addRecipe(IIngredient.of("grainAmaranth"), new ItemStack((ItemsTFCF.AMARANTH_FLOUR)));
        manager.addRecipe(IIngredient.of("grainBuckwheat"), new ItemStack((ItemsTFCF.BUCKWHEAT_FLOUR)));
        manager.addRecipe(IIngredient.of("grainFonio"), new ItemStack((ItemsTFCF.FONIO_FLOUR)));
        manager.addRecipe(IIngredient.of("grainMillet"), new ItemStack((ItemsTFCF.MILLET_FLOUR)));
        manager.addRecipe(IIngredient.of("grainQuinoa"), new ItemStack((ItemsTFCF.QUINOA_FLOUR)));
        manager.addRecipe(IIngredient.of("grainSpelt"), new ItemStack((ItemsTFCF.SPELT_FLOUR)));
        manager.addRecipe(IIngredient.of(ItemsTFCF.CASSIA_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CASSIA_CINNAMON, 2));
        manager.addRecipe(IIngredient.of(ItemsTFCF.CEYLON_CINNAMON_BARK), new ItemStack(ItemsTFCF.GROUND_CEYLON_CINNAMON, 2));
        //manager.addRecipe(IIngredient.of(ItemsTFCF.BLACK_PEPPER), new ItemStack(ItemsTFCF.GROUND_BLACK_PEPPER, 2));
        manager.addRecipe(IIngredient.of(ItemsTFCF.ROASTED_COFFEE_BEANS), new ItemStack(ItemsTFCF.COFFEE_POWDER, 2));
        manager.addRecipe(IIngredient.of("pearl"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.PEARL)));
        manager.addRecipe(IIngredient.of("pearlBlack"), new ItemStack(ItemPowderTFCF.get(PowderTFCF.BLACK_PEARL)));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.PAPYRUS))), new ItemStack(ItemsTFCF.PAPYRUS_PULP, 3));

        // Dye from plants
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CHAMOMILE))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.HYDRANGEA))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LILY_OF_THE_VALLEY))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of("cropMadder"), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of("cropWoad"), new ItemStack(ItemsTFC.DYE_BLUE, 2));
        manager.addRecipe(IIngredient.of("cropIndigo"), new ItemStack(ItemsTFC.DYE_BLUE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SUNFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of("cropWeld"), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of("cropRape"), new ItemStack(ItemsTFC.DYE_BLUE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LILAC))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.PEONY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LAVANDULA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CATTAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of("cropAgave"), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SUGAR_CANE))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BADDERLOCKS))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.GUTWEED))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SAGO))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of("resin"), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of("treeLeavesTeak"), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SUGAR_CANE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.TACKWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.TAKAKIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.VOODOO_LILY))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.DEVILS_TONGUE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BROMELIA_HEMISPHERICA))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BROMELIA_LACINIOSA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.KAIETEUR_FALLS))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MATTEUCCIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CORD_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.REED_MANNAGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.PRAIRIE_JUNEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.WOOLLY_BUSH))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CINNAMON_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.JAPANESE_PIERIS))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BURNING_BUSH))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.UNDERGROWTH_SHRUB))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.UNDERGROWTH_SHRUB_SMALL))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SEA_OATS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BUNCH_GRASS_FLOATING))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BUNCH_GRASS_REED))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CROWNGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CAT_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.GOOSEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.WHEATGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.HALFA_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LEYMUS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MARRAM_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        //manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.WILD_BARLEY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        //manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.WILD_RICE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        //manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.WILD_WHEAT))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.RATTAN))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.GLOW_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BLUE_SKYFLOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.JADE_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.JAPANESE_IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MADEIRA_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MYSORE_TRUMPETVINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SILVERVEIN_CREEPER))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SWEDISH_IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.VARIEGATED_PERSIAN_IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.APACHE_DWARF))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.ARTISTS_CONK))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CLIMBING_CACTUS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CRIMSON_CATTLEYA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CREEPING_MISTLETOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.CUTHBERTS_DENDROBIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.FISH_BONE_CACTUS))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.FRAGRANT_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.HARLEQUIN_MISTLETOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.KING_ORCHID))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LANTERN_OF_THE_FOREST))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LARGE_FOOT_DENDROBIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.COMMON_MISTLETOE))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SKY_PLANT))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SULPHUR_SHELF))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.TAMPA_BUTTERFLY_ORCHID))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.TURKEY_TAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.WILDFIRE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BELL_TREE_DAHLIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BIG_LEAF_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.DRAKENSBERG_CYCAD))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.DWARF_SUGAR_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.GIANT_CANE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.GIANT_ELEPHANT_EAR))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.GIANT_FEATHER_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MADAGASCAR_OCOTILLO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MALAGASY_TREE_ALOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MOUNTAIN_CABBAGE_TREE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.PYGMY_DATE_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.QUEEN_SAGO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.RED_SEALING_WAX_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SUMMER_ASPHODEL))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.ZIMBABWE_ALOE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.ANTHURIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.ARROWHEAD))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.ARUNDO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BLUEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BLUE_GINGER))), new ItemStack(ItemsTFC.DYE_BLUE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BROMEGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BUR_REED))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.DESERT_FLAME))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.HELICONIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.HIBISCUS))), new ItemStack(ItemsTFC.DYE_WHITE, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.KANGAROO_PAW))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.KING_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LIPSTICK_PALM))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MARIGOLD))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MONSTERA_EPIPHYTE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        //manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MONSTERA_GROUND))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.PHRAGMITE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.PICKERELWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BADDERLOCKS))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.COONTAIL))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.EEL_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.GIANT_KELP))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.GUTWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.HORNWORT))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LAMINARIA))), new ItemStack(ItemsTFC.DYE_BROWN, 2));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LEAFY_KELP))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MANATEE_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.MILFOIL))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.PONDWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SAGO))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SEAGRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SEAWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.STAR_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.TURTLE_GRASS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.WINGED_KELP))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.RED_ALGAE))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.RED_SEA_WHIP))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.SEA_ANEMONE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.BEARDED_MOSS))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.GLOW_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.HANGING_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.JUNGLE_VINE))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.LIANA))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlockPlantTFC.get(PLANTS.getValue(PlantsTFCF.IVY))), new ItemStack(Items.DYE, 2, EnumDyeColor.GREEN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlocksTFCF.BLUESHROOM), new ItemStack(Items.DYE, 1, EnumDyeColor.CYAN.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlocksTFCF.GLOWSHROOM), new ItemStack(Items.GLOWSTONE_DUST, 1));
        manager.addRecipe(IIngredient.of(BlocksTFCF.MAGMA_SHROOM), new ItemStack(Items.MAGMA_CREAM, 1));
        manager.addRecipe(IIngredient.of(BlocksTFCF.POISON_SHROOM), new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage()));
        manager.addRecipe(IIngredient.of(BlocksTFCF.SULPHUR_SHROOM), new ItemStack(ItemPowder.get(Powder.SULFUR), 1));
        manager.addRecipe(IIngredient.of(BlocksTFCF.GLOWING_SEA_BANANA), new ItemStack(Items.GLOWSTONE_DUST, 2));
        manager.addRecipe(IIngredient.of(BlocksTFCF.LIGHTSTONE), new ItemStack(Items.GLOWSTONE_DUST, 2));

        manager.addRecipe(IIngredient.of(ItemsFL.getDriedFruit(Fruit.COFFEE_CHERRIES)), new ItemStack(ModItems.GreenCoffeeBeans, 1));
        manager.addRecipe(IIngredient.of(ModItems.CoffeeBeans), new ItemStack(ModItems.GroundCoffee, 1));
        manager.addRecipe(IIngredient.of(ItemBrickTFC.get(ROCKS.getValue(DefaultRocks.QUARTZITE))), new ItemStack((TFCThingsItems.ITEM_WHETSTONE)));

        manager.addRecipe(IIngredient.of("gemHalite"), new ItemStack(ItemPowder.get(Powder.SALT), 2));
        manager.addRecipe(IIngredient.of(ItemsFL.CINNAMON), new ItemStack(ItemsFL.GROUND_CINNAMON, 2));
        manager.addRecipe(IIngredient.of(ItemsFL.getFood(FoodFL.ROASTED_CHESTNUTS)), new ItemStack(ItemsFL.getFood(FoodFL.CHESTNUT_FLOUR)));
        manager.addRecipe(IIngredient.of(ItemFoodTFC.get(Food.SOYBEAN)), new ItemStack(ItemsFL.getFood(FoodFL.GROUND_SOYBEANS)));
        manager.addRecipe(IIngredient.of(ItemFoodTFC.get(Food.TOMATO)), new ItemStack(ItemsFL.getFood(FoodFL.TOMATO_SAUCE)));

    }

}
