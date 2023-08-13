package net.dries007.tfc.common.objects.recipes.handlers;

import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.food.variant.FoodVariants;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.common.objects.inventory.ingredient.IngredientItemFood;
import net.dries007.tfc.common.objects.items.ItemsTFC;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class QuernRecipes {

    public static void register() {
        var registry = TFCRegistries.QUERN;

        registry.registerAll(
                //Grain
                new QuernRecipe(IIngredient.of("grainBarley"), new ItemStack(ItemFoodTFC.get(FoodVariants.BARLEY_FLOUR), 1)).setRegistryName("barley"),
                new QuernRecipe(IIngredient.of("grainOat"), new ItemStack(ItemFoodTFC.get(FoodVariants.OAT_FLOUR), 1)).setRegistryName("oat"),
                new QuernRecipe(IIngredient.of("grainRice"), new ItemStack(ItemFoodTFC.get(FoodVariants.RICE_FLOUR), 1)).setRegistryName("rice"),
                new QuernRecipe(IIngredient.of("grainRye"), new ItemStack(ItemFoodTFC.get(FoodVariants.RYE_FLOUR), 1)).setRegistryName("rye"),
                new QuernRecipe(IIngredient.of("grainWheat"), new ItemStack(ItemFoodTFC.get(FoodVariants.WHEAT_FLOUR), 1)).setRegistryName("wheat"),
                new QuernRecipe(IIngredient.of("grainMaize"), new ItemStack(ItemFoodTFC.get(FoodVariants.CORNMEAL_FLOUR), 1)).setRegistryName("maize"),

                new QuernRecipe(new IngredientItemFood(IIngredient.of(ItemFoodTFC.get(FoodVariants.OLIVE))), new ItemStack(ItemsTFC.OLIVE_PASTE, 1)).setRegistryName("olive"),

                //Flux
//                new QuernRecipe(IIngredient.of("gemBorax"), new ItemStack(ItemPowder.get(Powder.FLUX), 6)).setRegistryName("borax"),
//                new QuernRecipe(IIngredient.of("rockFlux"), new ItemStack(ItemPowder.get(Powder.FLUX), 2)).setRegistryName("flux"),

                //Redstone
                new QuernRecipe(IIngredient.of("gemCinnabar"), new ItemStack(Items.REDSTONE, 8)).setRegistryName("cinnabar"),
                new QuernRecipe(IIngredient.of("gemCryolite"), new ItemStack(Items.REDSTONE, 8)).setRegistryName("cryolite"),

                //Hematite
                //new QuernRecipe(IIngredient.of(ItemSmallOre.get(Ore.HEMATITE, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 2)).setRegistryName("hematite_powder_from_small"),
                //new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 3)).setRegistryName("hematite_powder_from_poor"),
                //new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 5)).setRegistryName("hematite_powder_from_normal"),
                //new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.HEMATITE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowder.get(Powder.HEMATITE), 7)).setRegistryName("hematite_powder_from_rich"),

                //Limonite
                //new QuernRecipe(IIngredient.of(ItemSmallOre.get(Ore.LIMONITE, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 2)).setRegistryName("limonite_powder_from_small"),
                //new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 3)).setRegistryName("limonite_powder_from_poor"),
                //new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 5)).setRegistryName("limonite_powder_from_normal"),
                //new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.LIMONITE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowder.get(Powder.LIMONITE), 7)).setRegistryName("limonite_powder_from_rich"),

                //Malachite
                //new QuernRecipe(IIngredient.of(ItemSmallOre.get(Ore.MALACHITE, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 2)).setRegistryName("malachite_powder_from_small"),
                //new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, Ore.Grade.POOR, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 3)).setRegistryName("malachite_powder_from_poor"),
                //new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, Ore.Grade.NORMAL, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 5)).setRegistryName("malachite_powder_from_normal"),
                //new QuernRecipe(IIngredient.of(ItemOreTFC.get(Ore.MALACHITE, Ore.Grade.RICH, 1)), new ItemStack(ItemPowder.get(Powder.MALACHITE), 7)).setRegistryName("malachite_powder_from_rich"),

                //Bone meal
                new QuernRecipe(IIngredient.of("bone"), new ItemStack(Items.DYE, 3, EnumDyeColor.WHITE.getDyeDamage())).setRegistryName("bone_meal_from_bone"),
                new QuernRecipe(IIngredient.of(Blocks.BONE_BLOCK), new ItemStack(Items.DYE, 9, EnumDyeColor.WHITE.getDyeDamage())).setRegistryName("bone_meal_from_bone_block")

                //Dye from plants
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.HOUSTONIA))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_houstonia"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.OXEYE_DAISY))), new ItemStack(ItemsTFC.DYE_WHITE, 1)).setRegistryName("crushed_oxeye_daisy"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PRIMROSE))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_primrose"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.SNAPDRAGON_WHITE))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_snapdragon_white"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.TRILLIUM))), new ItemStack(ItemsTFC.DYE_WHITE, 1)).setRegistryName("crushed_trillium"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.SPANISH_MOSS))), new ItemStack(ItemsTFC.DYE_WHITE, 2)).setRegistryName("crushed_spanish_moss"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.TULIP_WHITE))), new ItemStack(ItemsTFC.DYE_WHITE, 1)).setRegistryName("crushed_tulip_white"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.BUTTERFLY_MILKWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("crushed_butterfly_milkweed"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.CANNA))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("crushed_canna"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.NASTURTIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("crushed_nasturium"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.STRELITZIA))), new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("crushed_strelitzia"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.TULIP_ORANGE))), new ItemStack(Items.DYE, 1, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("crushed_tulip_orange"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.WATER_CANNA))), new ItemStack(Items.DYE, 2, EnumDyeColor.ORANGE.getDyeDamage())).setRegistryName("crushed_water_canna"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.ATHYRIUM_FERN))), new ItemStack(Items.DYE, 2, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("crushed_athyrium"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.MORNING_GLORY))), new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("crushed_morning_glory"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PULSATILLA))), new ItemStack(Items.DYE, 1, EnumDyeColor.MAGENTA.getDyeDamage())).setRegistryName("crushed_pulsatilla"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.LABRADOR_TEA))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage())).setRegistryName("crushed_labrador_tea"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.SAPPHIRE_TOWER))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage())).setRegistryName("crushed_sapphire_tower"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.CALENDULA))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_marigold"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.DANDELION))), new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_dandelion"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.MEADS_MILKWEED))), new ItemStack(Items.DYE, 1, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_meads_milkweed"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.GOLDENROD))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_goldenrod"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.SNAPDRAGON_YELLOW))), new ItemStack(Items.DYE, 2, EnumDyeColor.YELLOW.getDyeDamage())).setRegistryName("crushed_snapdragon_yellow"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.MOSS))), new ItemStack(Items.DYE, 2, EnumDyeColor.LIME.getDyeDamage())).setRegistryName("crushed_moss"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.FOXGLOVE))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_foxglove"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.SACRED_DATURA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_sacred_datura"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.TULIP_PINK))), new ItemStack(Items.DYE, 1, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_tulip_pink"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.SNAPDRAGON_PINK))), new ItemStack(Items.DYE, 2, EnumDyeColor.PINK.getDyeDamage())).setRegistryName("crushed_snapdragon_pink"),
//
//				//No gray :c
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.YUCCA))), new ItemStack(Items.DYE, 2, EnumDyeColor.SILVER.getDyeDamage())).setRegistryName("crushed_yucca"),
//
//				//No Cyan :c
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.ALLIUM))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_allium"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.BLACK_ORCHID))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_black_orchid"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PEROVSKIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.PURPLE.getDyeDamage())).setRegistryName("crushed_perovskia"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.BLUE_ORCHID))), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_blue_orchid"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.GRAPE_HYACINTH))), new ItemStack(ItemsTFC.DYE_BLUE, 2)).setRegistryName("crushed_grape_hyacinth"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.ROUGH_HORSETAIL))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("crushed_rough_horsetail"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.SARGASSUM))), new ItemStack(ItemsTFC.DYE_BROWN, 2)).setRegistryName("crushed_sargassum"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.BARREL_CACTUS))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_barrel_cactus"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.REINDEER_LICHEN))), new ItemStack(Items.DYE, 4, EnumDyeColor.GREEN.getDyeDamage())).setRegistryName("crushed_reindeer_lichen"),
//
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.GUZMANIA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_guzmania"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.POPPY))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_poppy"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.PORCINI))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_porcini"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.ROSE))), new ItemStack(Items.DYE, 4, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_rose"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.SNAPDRAGON_RED))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_snapdragon_red"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.TROPICAL_MILKWEED))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_tropical_milkweed"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.TULIP_RED))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_tulip_red"),
//				new QuernRecipe(IIngredient.of(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(DefaultPlants.VRIESEA))), new ItemStack(Items.DYE, 2, EnumDyeColor.RED.getDyeDamage())).setRegistryName("crushed_vriesea"),

                //Misc
//                new QuernRecipe(IIngredient.of("gemSylvite"), new ItemStack(ItemPowder.get(Powder.FERTILIZER), 4)).setRegistryName("sylvite"),
//                new QuernRecipe(IIngredient.of("gemSulfur"), new ItemStack(ItemPowder.get(Powder.SULFUR), 4)).setRegistryName("sulfur"),
//                new QuernRecipe(IIngredient.of("gemSaltpeter"), new ItemStack(ItemPowder.get(Powder.SALTPETER), 4)).setRegistryName("saltpeter"),
//                new QuernRecipe(IIngredient.of("charcoal"), new ItemStack(ItemPowder.get(Powder.CHARCOAL), 4)).setRegistryName("charcoal"),
//                new QuernRecipe(IIngredient.of("rockRocksalt"), new ItemStack(ItemPowder.get(Powder.SALT), 4)).setRegistryName("rocksalt"),
//                new QuernRecipe(IIngredient.of(Items.BLAZE_ROD), new ItemStack(Items.BLAZE_POWDER, 2)).setRegistryName("blaze_powder"),
//                new QuernRecipe(IIngredient.of("gemLapis"), new ItemStack(ItemPowder.get(Powder.LAPIS_LAZULI), 4)).setRegistryName("lapis_lazuli"),
//                new QuernRecipe(IIngredient.of("gemGraphite"), new ItemStack(ItemPowder.get(Powder.GRAPHITE), 4)).setRegistryName("graphite_powder"),
//                new QuernRecipe(IIngredient.of("gemKaolinite"), new ItemStack(ItemPowder.get(Powder.KAOLINITE), 4)).setRegistryName("kaolinite_powder")
                //new QuernRecipe(IIngredient.of(BlockRockVariant.get(Rock.LIMESTONE, Rock.Type.RAW)), new ItemStack(ItemsTFC.GYPSUM)).setRegistryName("gypsum")
        );
    }
}
