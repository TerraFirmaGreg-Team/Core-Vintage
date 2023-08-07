package net.dries007.tfc.objects.recipes.category;


import net.dries007.tfc.api.recipes.barrel.*;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.food.Food;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariants;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientFluidItem;
import net.dries007.tfc.objects.inventory.ingredient.IngredientItemFood;
import net.dries007.tfc.objects.items.ItemAnimalHide;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.test.blocks.TFCBlocks;
import net.dries007.tfc.test.items.TFCItems;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import static net.dries007.tfc.objects.fluids.FluidsTFC.*;

public class BarrelRecipes {

    public static void registerBarrelRecipes() {
        var registry = TFCRegistries.BARREL;

        registry.registerAll(
                // Hide Processing (all three conversions)
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 300), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.SCRAPED, ItemAnimalHide.HideSize.SMALL)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.SMALL)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("small_prepared_hide"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 400), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.SCRAPED, ItemAnimalHide.HideSize.MEDIUM)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.MEDIUM)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("medium_prepared_hide"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.SCRAPED, ItemAnimalHide.HideSize.LARGE)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.LARGE)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("large_prepared_hide"),
                new BarrelRecipe(IIngredient.of(LIMEWATER.get(), 300), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.RAW, ItemAnimalHide.HideSize.SMALL)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.SOAKED, ItemAnimalHide.HideSize.SMALL)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("small_soaked_hide"),
                new BarrelRecipe(IIngredient.of(LIMEWATER.get(), 400), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.RAW, ItemAnimalHide.HideSize.MEDIUM)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.SOAKED, ItemAnimalHide.HideSize.MEDIUM)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("medium_soaked_hide"),
                new BarrelRecipe(IIngredient.of(LIMEWATER.get(), 500), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.RAW, ItemAnimalHide.HideSize.LARGE)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.SOAKED, ItemAnimalHide.HideSize.LARGE)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("large_soaked_hide"),
                new BarrelRecipe(IIngredient.of(TANNIN.get(), 300), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.SMALL)), null, new ItemStack(Items.LEATHER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("leather_small_hide"),
                new BarrelRecipe(IIngredient.of(TANNIN.get(), 400), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.MEDIUM)), null, new ItemStack(Items.LEATHER, 2), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("leather_medium_hide"),
                new BarrelRecipe(IIngredient.of(TANNIN.get(), 500), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.LARGE)), null, new ItemStack(Items.LEATHER, 3), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("leather_large_hide"),
                // Misc
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 1000), IIngredient.of("logWoodTannin"), new FluidStack(TANNIN.get(), 10000), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("tannin"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemsTFC.JUTE), null, new ItemStack(ItemsTFC.JUTE_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("jute_fiber"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 600), new IngredientItemFood(IIngredient.of(ItemFoodTFC.get(Food.SUGARCANE), 5)), null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar"),
                new BarrelRecipe(IIngredient.of(LIMEWATER.get(), 500), IIngredient.of(new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage())), null, new ItemStack(TFCItems.GLUE), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("glue"),
                // Alcohol - Classic created 1000mb with 4oz, which would be 8 items per full barrel at 5 oz/item. Instead we now require 20 items, so conversion is 2 oz/item here
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), new IngredientItemFood(IIngredient.of(ItemFoodTFC.get(Food.BARLEY_FLOUR))), new FluidStack(FluidsTFC.BEER.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), new IngredientItemFood(IIngredient.of("apple")), new FluidStack(FluidsTFC.CIDER.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cider"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidsTFC.RUM.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("rum"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), new IngredientItemFood(IIngredient.of(ItemFoodTFC.get(Food.RICE_FLOUR))), new FluidStack(FluidsTFC.SAKE.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("sake"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), new IngredientItemFood(IIngredient.of(ItemFoodTFC.get(Food.POTATO))), new FluidStack(FluidsTFC.VODKA.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("vodka"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), new IngredientItemFood(IIngredient.of(ItemFoodTFC.get(Food.WHEAT_FLOUR))), new FluidStack(FluidsTFC.WHISKEY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("whiskey"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), new IngredientItemFood(IIngredient.of(ItemFoodTFC.get(Food.CORNMEAL_FLOUR))), new FluidStack(FluidsTFC.CORN_WHISKEY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("corn_whiskey"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), new IngredientItemFood(IIngredient.of(ItemFoodTFC.get(Food.RYE_FLOUR))), new FluidStack(FluidsTFC.RYE_WHISKEY.get(), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("rye_whiskey"),
                // Vinegar - Classic created 1000mb with 10 oz, which would be 20 items per full barrel at 5 oz/item. Instead we now require 40 items, so conversion is 2.5 oz/item.
                new BarrelRecipe(IIngredient.of(250, FluidsTFC.BEER.get(), FluidsTFC.CIDER.get(), FluidsTFC.RUM.get(), FluidsTFC.SAKE.get(), FluidsTFC.VODKA.get(), FluidsTFC.WHISKEY.get(), FluidsTFC.CORN_WHISKEY.get(), FluidsTFC.RYE_WHISKEY.get()), new IngredientItemFood(IIngredient.of("categoryFruit")), new FluidStack(FluidsTFC.VINEGAR.get(), 250), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("vinegar"),
                // Food preservation
                BarrelRecipeFoodTraits.pickling(new IngredientItemFood(IIngredient.of("categoryFruit"))).setRegistryName("pickling_fruit"),
                BarrelRecipeFoodTraits.pickling(new IngredientItemFood(IIngredient.of("categoryVegetable"))).setRegistryName("pickling_vegetable"),
                BarrelRecipeFoodTraits.pickling(new IngredientItemFood(IIngredient.of("categoryMeat"))).setRegistryName("pickling_meat"),
                BarrelRecipeFoodTraits.brining(new IngredientItemFood(IIngredient.of("categoryFruit"))).setRegistryName("brining_fruit"),
                BarrelRecipeFoodTraits.brining(new IngredientItemFood(IIngredient.of("categoryVegetable"))).setRegistryName("brining_vegetable"),
                BarrelRecipeFoodTraits.brining(new IngredientItemFood(IIngredient.of("categoryMeat"))).setRegistryName("brining_meat"),
                BarrelRecipeFoodPreservation.vinegar(new IngredientItemFood(IIngredient.of("categoryFruit"))).setRegistryName("vinegar_fruit"),
                BarrelRecipeFoodPreservation.vinegar(new IngredientItemFood(IIngredient.of("categoryVegetable"))).setRegistryName("vinegar_vegetable"),
                BarrelRecipeFoodPreservation.vinegar(new IngredientItemFood(IIngredient.of("categoryMeat"))).setRegistryName("vinegar_meat"),

                new BarrelRecipe(IIngredient.of(LIMEWATER.get(), 100), IIngredient.of("sand"), null, new ItemStack(TFCItems.MORTAR, 16), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("mortar"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 125), IIngredient.of("dustSalt"), new FluidStack(SALT_WATER.get(), 125), ItemStack.EMPTY, 0).setRegistryName("fresh_to_salt_water"),
                new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(new ItemStack(TFCItems.WOOD_ASH)), new FluidStack(LYE.get(), 125), ItemStack.EMPTY, 0).setRegistryName("lye"),
                new BarrelRecipe(IIngredient.of(MILK_VINEGAR.get(), 1), IIngredient.of(ItemStack.EMPTY), new FluidStack(CURDLED_MILK.get(), 1), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("curdled_milk"),
                // based on eating 5 oz in classic, and 1 item in TNG, the full barrel recipe generated 160 oz of cheese, now 32 items. Therefore 625mb creates 2 cheese.
                new BarrelRecipe(IIngredient.of(CURDLED_MILK.get(), 625), IIngredient.of(ItemStack.EMPTY), null, new ItemStack(ItemFoodTFC.get(Food.CHEESE), 2), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cheese"),

                // Instant recipes: set the duration to 0
                new BarrelRecipeFluidMixing(IIngredient.of(SALT_WATER.get(), 9), new IngredientFluidItem(VINEGAR.get(), 1), new FluidStack(BRINE.get(), 10), 0).setRegistryName("brine"),
                // this ratio works for 9b + 1b = 10b (full barrel) of brine/milk_vinegar, but leaves odd ninths of fluid around for other mixtures.
                new BarrelRecipeFluidMixing(IIngredient.of(MILK.get(), 9), new IngredientFluidItem(VINEGAR.get(), 1), new FluidStack(MILK_VINEGAR.get(), 10), 0).setRegistryName("milk_vinegar"),
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 500), IIngredient.of("dustFlux"), new FluidStack(LIMEWATER.get(), 500), ItemStack.EMPTY, 0).setRegistryName("limewater"),
                new BarrelRecipe(IIngredient.of(LIMEWATER.get(), 100), IIngredient.of("gemGypsum"), null, new ItemStack(TFCStorage.getAlabasterBlock("plain", RockBlockVariants.RAW)), ICalendar.TICKS_IN_HOUR).setRegistryName("plain_alabaster"),

                //olive oil production
                new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 125), IIngredient.of(ItemsTFC.OLIVE_PASTE), new FluidStack(OLIVE_OIL_WATER.get(), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("olive_water"),
                // Balance note: Classic gave 250mb for 160oz of olives ~= 32 items. We give 800 mb for that, so 3.2x more. Hopefully will help with lamp usage
                new BarrelRecipe(IIngredient.of(OLIVE_OIL_WATER.get(), 250), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(OLIVE_OIL.get(), 50), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("olive_oil"),
                // Balance: switch to fresh water. Hot water use that way is broken
                new BarrelRecipe(IIngredient.of(FRESH_WATER.get(), 125), IIngredient.of(ItemsTFC.DIRTY_JUTE_NET), null, new ItemStack(ItemsTFC.JUTE_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net"),
                // Temperature recipes
                new BarrelRecipeTemperature(IIngredient.of(FRESH_WATER.get(), 1), 50).setRegistryName("fresh_water_cooling"),
                new BarrelRecipeTemperature(IIngredient.of(SALT_WATER.get(), 1), 50).setRegistryName("salt_water_cooling")
        );

        for (Food food : new Food[]{Food.SALAD_DAIRY, Food.SALAD_FRUIT, Food.SALAD_GRAIN, Food.SALAD_MEAT, Food.SALAD_VEGETABLE, Food.SOUP_DAIRY, Food.SOUP_FRUIT, Food.SOUP_GRAIN, Food.SOUP_MEAT, Food.SOUP_VEGETABLE}) {
            registry.register(new BarrelRecipeDynamicBowlFood(IIngredient.of(FRESH_WATER.get(), 200), IIngredient.of(ItemFoodTFC.get(food)), 0).setRegistryName(food.name().toLowerCase() + "_cleaning"));
        }

        //The many many many recipes that is dye. This assumes that the standard meta values for colored objects are followed.
        for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
            Fluid fluid = FluidsTFC.getFluidFromDye(dyeColor).get();
            String dyeName = dyeColor == EnumDyeColor.SILVER ? "light_gray" : dyeColor.getName();
            int dyeMeta = dyeColor.getMetadata();
            registry.registerAll(
                    // Dye fluid
                    new BarrelRecipe(IIngredient.of(HOT_WATER.get(), 1000), IIngredient.of(OreDictionaryHelper.toString("dye_" + dyeName)), new FluidStack(FluidsTFC.getFluidFromDye(dyeColor).get(), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName(dyeName),
                    // Vanilla dye-able items
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of("woolWhite"), null, new ItemStack(Blocks.WOOL, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("wool_" + dyeName),
                    new BarrelRecipe(IIngredient.of(fluid, 25), IIngredient.of(new ItemStack(Blocks.CARPET, 1, 0)), null, new ItemStack(Blocks.CARPET, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("carpet_" + dyeName),
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(new ItemStack(Items.BED, 1, 0)), null, new ItemStack(Items.BED, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("bed_" + dyeName),
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(Blocks.HARDENED_CLAY), null, new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("terracotta_" + dyeName),
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(Blocks.GLASS), null, new ItemStack(Blocks.STAINED_GLASS, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("glass_" + dyeName),
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(Blocks.GLASS_PANE), null, new ItemStack(Blocks.STAINED_GLASS_PANE, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("glass_pane_" + dyeName),
                    // Glazed Vessels
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(ItemsTFC.UNFIRED_VESSEL), null, new ItemStack(ItemsTFC.UNFIRED_VESSEL_GLAZED, 1, 15 - dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("glazed_vessel_" + dyeName),
                    // Concrete (vanilla + aggregate)
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(new ItemStack(Blocks.CONCRETE_POWDER, 1, 0)), null, new ItemStack(Blocks.CONCRETE_POWDER, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("concrete_" + dyeName),
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(TFCBlocks.AGGREGATE), null, new ItemStack(Blocks.CONCRETE_POWDER, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("aggregate_" + dyeName),
                    // Alabaster
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(TFCStorage.getAlabasterBlock("plain", RockBlockVariants.BRICK)), null, new ItemStack(TFCStorage.getAlabasterBlock(dyeColor.getName(), RockBlockVariants.BRICK)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_bricks_" + dyeColor.getName()),
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(TFCStorage.getAlabasterBlock("plain", RockBlockVariants.RAW)), null, new ItemStack(TFCStorage.getAlabasterBlock(dyeColor.getName(), RockBlockVariants.RAW)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_raw_" + dyeColor.getName()),
                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(TFCStorage.getAlabasterBlock("plain", RockBlockVariants.SMOOTH)), null, new ItemStack(TFCStorage.getAlabasterBlock(dyeColor.getName(), RockBlockVariants.SMOOTH)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_polished_" + dyeColor.getName())
            );
        }
        // Un-dyeing Recipes
        registry.registerAll(
                // Vanilla dye-able items
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 125), IIngredient.of("wool"), null, new ItemStack(Blocks.WOOL, 1, 0), ICalendar.TICKS_IN_HOUR).setRegistryName("wool_undo"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 25), IIngredient.of("carpet"), null, new ItemStack(Blocks.CARPET, 1, 0), ICalendar.TICKS_IN_HOUR).setRegistryName("carpet_undo"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 125), IIngredient.of("bed"), null, new ItemStack(Items.BED, 1, 0), ICalendar.TICKS_IN_HOUR).setRegistryName("bed_undo"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 125), IIngredient.of("terracotta"), null, new ItemStack(Blocks.HARDENED_CLAY), ICalendar.TICKS_IN_HOUR).setRegistryName("terracotta_undo"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 125), IIngredient.of("blockGlass"), null, new ItemStack(Blocks.GLASS), ICalendar.TICKS_IN_HOUR).setRegistryName("glass_undo"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 125), IIngredient.of("paneGlass"), null, new ItemStack(Blocks.GLASS_PANE), ICalendar.TICKS_IN_HOUR).setRegistryName("glass_pane_undo"),
                // Concrete
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 125), IIngredient.of("powderConcrete"), null, new ItemStack(TFCBlocks.AGGREGATE), ICalendar.TICKS_IN_HOUR).setRegistryName("concrete_undo"),
                // Alabaster
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 125), IIngredient.of("alabasterBricks"), null, new ItemStack(TFCStorage.getAlabasterBlock("plain", RockBlockVariants.BRICK)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_bricks_undo"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 125), IIngredient.of("alabasterRaw"), null, new ItemStack(TFCStorage.getAlabasterBlock("plain", RockBlockVariants.RAW)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_raw_undo"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.LYE.get(), 125), IIngredient.of("alabasterPolished"), null, new ItemStack(TFCStorage.getAlabasterBlock("plain", RockBlockVariants.SMOOTH)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_polished_undo")
        );
        // Dye combinations.
        registry.registerAll(
                //Orange
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.ORANGE).get(), 2), 0).setRegistryName("orange_dye_red_yellow_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.ORANGE).get(), 2), 0).setRegistryName("orange_dye_yellow_red_liquid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1000), IIngredient.of("dyeYellow"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.ORANGE).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("orange_dye_red_yellow_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW).get(), 1000), IIngredient.of("dyeRed"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.ORANGE).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("orange_dye_yellow_red_solid"),
                //Light Blue
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIGHT_BLUE).get(), 2), 0).setRegistryName("light_blue_dye_blue_white_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIGHT_BLUE).get(), 2), 0).setRegistryName("light_blue_dye_white_blue_liquid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIGHT_BLUE).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("light_blue_dye_blue_white_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1000), IIngredient.of("dyeBlue"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIGHT_BLUE).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("light_blue_dye_white_blue_solid"),
                //Magenta
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.MAGENTA).get(), 2), 0).setRegistryName("magenta_dye_purple_pink_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.MAGENTA).get(), 2), 0).setRegistryName("magenta_dye_pink_purple_liquid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE).get(), 1000), IIngredient.of("dyePink"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.MAGENTA).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("magenta_dye_purple_pink_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK).get(), 1000), IIngredient.of("dyePurple"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.MAGENTA).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("magenta_dye_pink_purple_solid"),
                //Pink
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK).get(), 2), 0).setRegistryName("pink_dye_red_white_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK).get(), 2), 0).setRegistryName("pink_dye_white_red_liquid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("pink_dye_red_white_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1000), IIngredient.of("dyeRed"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("pink_dye_white_red_solid"),
                //Light Gray
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER).get(), 2), 0).setRegistryName("light_gray_dye_white_gray_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 2), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER).get(), 3), 0).setRegistryName("light_gray_dye_white_black_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER).get(), 2), 0).setRegistryName("light_gray_dye_gray_white_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 2), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER).get(), 3), 0).setRegistryName("light_gray_dye_black_white_liquid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1000), IIngredient.of("dyeGray"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("light_gray_dye_white_gray_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 2000), IIngredient.of("dyeBlack"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("light_gray_dye_white_black_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY).get(), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("light_gray_dye_gray_white_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 500), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("light_gray_dye_black_white_solid"),
                //Lime
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIME).get(), 2), 0).setRegistryName("lime_dye_green_white_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIME).get(), 2), 0).setRegistryName("lime_dye_white_green_liquid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIME).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("lime_dye_green_white_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1000), IIngredient.of("dyeGreen"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIME).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("lime_dye_white_green_solid"),
                //Cyan
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.CYAN).get(), 2), 0).setRegistryName("cyan_dye_green_blue_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.CYAN).get(), 2), 0).setRegistryName("cyan_dye_blue_green_liquid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN).get(), 1000), IIngredient.of("dyeBlue"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.CYAN).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("cyan_dye_green_blue_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), IIngredient.of("dyeGreen"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.CYAN).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("cyan_dye_blue_green_solid"),
                //Purple
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE).get(), 2), 0).setRegistryName("purple_dye_red_blue_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE).get(), 2), 0).setRegistryName("purple_dye_blue_red_liquid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED).get(), 1000), IIngredient.of("dyeBlue"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("purple_dye_red_blue_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE).get(), 1000), IIngredient.of("dyeRed"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("purple_dye_blue_red_solid"),
                //Gray
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY).get(), 2), 0).setRegistryName("gray_dye_black_white_liquid"),
                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY).get(), 2), 0).setRegistryName("gray_dye_white_black_liquid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK).get(), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("gray_dye_black_white_solid"),
                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE).get(), 1000), IIngredient.of("dyeBlack"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY).get(), 1000), ItemStack.EMPTY, 0).setRegistryName("gray_dye_white_black_solid")
        );
    }
}
