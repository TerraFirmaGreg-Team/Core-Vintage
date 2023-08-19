package net.dries007.tfc.common.objects.recipes.handlers;


import net.dries007.tfc.api.recipes.barrel.*;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.food.type.FoodTypes;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.common.objects.inventory.ingredient.IngredientFluidItem;
import net.dries007.tfc.common.objects.inventory.ingredient.IngredientItemFood;
import net.dries007.tfc.common.objects.items.ItemAnimalHide;
import net.dries007.tfc.common.objects.items.ItemsTFC;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import static net.dries007.tfc.api.types.food.type.FoodTypes.*;
import static net.dries007.tfc.api.types.rock.variant.RockBlockVariants.*;

public class BarrelRecipes {

    public static void register() {
        var registry = TFCRegistries.BARREL;

        registry.registerAll(
                // Hide Processing (all three conversions)
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 300), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.SCRAPED, ItemAnimalHide.HideSize.SMALL)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.SMALL)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("small_prepared_hide"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 400), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.SCRAPED, ItemAnimalHide.HideSize.MEDIUM)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.MEDIUM)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("medium_prepared_hide"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.SCRAPED, ItemAnimalHide.HideSize.LARGE)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.LARGE)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("large_prepared_hide"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("limewater"), 300), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.RAW, ItemAnimalHide.HideSize.SMALL)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.SOAKED, ItemAnimalHide.HideSize.SMALL)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("small_soaked_hide"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("limewater"), 400), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.RAW, ItemAnimalHide.HideSize.MEDIUM)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.SOAKED, ItemAnimalHide.HideSize.MEDIUM)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("medium_soaked_hide"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("limewater"), 500), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.RAW, ItemAnimalHide.HideSize.LARGE)), null, new ItemStack(ItemAnimalHide.get(ItemAnimalHide.HideType.SOAKED, ItemAnimalHide.HideSize.LARGE)), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("large_soaked_hide"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("tannin"), 300), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.SMALL)), null, new ItemStack(Items.LEATHER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("leather_small_hide"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("tannin"), 400), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.MEDIUM)), null, new ItemStack(Items.LEATHER, 2), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("leather_medium_hide"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("tannin"), 500), IIngredient.of(ItemAnimalHide.get(ItemAnimalHide.HideType.PREPARED, ItemAnimalHide.HideSize.LARGE)), null, new ItemStack(Items.LEATHER, 3), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("leather_large_hide"),
                // Misc
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 1000), IIngredient.of("logWoodTannin"), new FluidStack(FluidRegistry.getFluid("tannin"), 10000), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("tannin"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 200), IIngredient.of(ItemsTFC.JUTE), null, new ItemStack(ItemsTFC.JUTE_FIBER), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("jute_fiber"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 600), new IngredientItemFood(IIngredient.of(TFCStorage.getFoodItem(SUGARCANE), 5)), null, new ItemStack(Items.SUGAR), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("sugar"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("limewater"), 500), IIngredient.of(new ItemStack(Items.DYE, 1, EnumDyeColor.WHITE.getDyeDamage())), null, new ItemStack(TFCItems.GLUE), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("glue"),
                // Alcohol - Classic created 1000mb with 4oz, which would be 8 items per full barrel at 5 oz/item. Instead we now require 20 items, so conversion is 2 oz/item here
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), new IngredientItemFood(IIngredient.of(TFCStorage.getFoodItem(BARLEY_FLOUR))), new FluidStack(FluidRegistry.getFluid("beer"), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("beer"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), new IngredientItemFood(IIngredient.of("apple")), new FluidStack(FluidRegistry.getFluid("cider"), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("cider"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), IIngredient.of(Items.SUGAR), new FluidStack(FluidRegistry.getFluid("rum"), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("rum"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), new IngredientItemFood(IIngredient.of(TFCStorage.getFoodItem(RICE_FLOUR))), new FluidStack(FluidRegistry.getFluid("sake"), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("sake"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), new IngredientItemFood(IIngredient.of(TFCStorage.getFoodItem(POTATO))), new FluidStack(FluidRegistry.getFluid("vodka"), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("vodka"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), new IngredientItemFood(IIngredient.of(TFCStorage.getFoodItem(WHEAT_FLOUR))), new FluidStack(FluidRegistry.getFluid("whiskey"), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("whiskey"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), new IngredientItemFood(IIngredient.of(TFCStorage.getFoodItem(CORNMEAL_FLOUR))), new FluidStack(FluidRegistry.getFluid("corn_whiskey"), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("corn_whiskey"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), new IngredientItemFood(IIngredient.of(TFCStorage.getFoodItem(RYE_FLOUR))), new FluidStack(FluidRegistry.getFluid("rye_whiskey"), 500), ItemStack.EMPTY, 72 * ICalendar.TICKS_IN_HOUR).setRegistryName("rye_whiskey"),
                // Vinegar - Classic created 1000mb with 10 oz, which would be 20 items per full barrel at 5 oz/item. Instead we now require 40 items, so conversion is 2.5 oz/item.
                new BarrelRecipe(IIngredient.of(250, FluidRegistry.getFluid("beer"),
                        FluidRegistry.getFluid("cider"), FluidRegistry.getFluid("rum"),
                        FluidRegistry.getFluid("sake"), FluidRegistry.getFluid("vodka"),
                        FluidRegistry.getFluid("whiskey"), FluidRegistry.getFluid("corn_whiskey"),
                        FluidRegistry.getFluid("rye_whiskey")),
                        new IngredientItemFood(IIngredient.of("categoryFruit")),
                        new FluidStack(FluidRegistry.getFluid("vinegar"), 250), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("vinegar"),
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

                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("limewater"), 100), IIngredient.of("sand"), null, new ItemStack(TFCItems.MORTAR, 16), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("mortar"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 125), IIngredient.of("dustSalt"), new FluidStack(FluidRegistry.getFluid("salt_water"), 125), ItemStack.EMPTY, 0).setRegistryName("fresh_to_salt_water"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("hot_water"), 125), IIngredient.of(new ItemStack(TFCItems.WOOD_ASH)), new FluidStack(FluidRegistry.getFluid("lye"), 125), ItemStack.EMPTY, 0).setRegistryName("lye"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("milk_vinegar"), 1), IIngredient.of(ItemStack.EMPTY), new FluidStack(FluidRegistry.getFluid("curdled_milk"), 1), ItemStack.EMPTY, 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("curdled_milk"),
                // based on eating 5 oz in classic, and 1 item in TNG, the full barrel recipe generated 160 oz of cheese, now 32 items. Therefore 625mb creates 2 cheese.
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("curdled_milk"), 625), IIngredient.of(ItemStack.EMPTY), null, new ItemStack(TFCStorage.getFoodItem(CHEESE), 2), 8 * ICalendar.TICKS_IN_HOUR).setRegistryName("cheese"),

                // Instant recipes: set the duration to 0
                new BarrelRecipeFluidMixing(IIngredient.of(FluidRegistry.getFluid("salt_water"), 9), new IngredientFluidItem(FluidRegistry.getFluid("vinegar"), 1), new FluidStack(FluidRegistry.getFluid("brine"), 10), 0).setRegistryName("brine"),
                // this ratio works for 9b + 1b = 10b (full barrel) of brine/milk_vinegar, but leaves odd ninths of fluid around for other mixtures.
                new BarrelRecipeFluidMixing(IIngredient.of(FluidRegistry.getFluid("milk"), 9), new IngredientFluidItem(FluidRegistry.getFluid("vinegar"), 1), new FluidStack(FluidRegistry.getFluid("milk_vinegar"), 10), 0).setRegistryName("milk_vinegar"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 500), IIngredient.of("dustFlux"), new FluidStack(FluidRegistry.getFluid("limewater"), 500), ItemStack.EMPTY, 0).setRegistryName("limewater"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("limewater"), 100), IIngredient.of("gemGypsum"), null, new ItemStack(TFCStorage.getAlabasterBlock("plain", RAW)), ICalendar.TICKS_IN_HOUR).setRegistryName("plain_alabaster"),

                //olive oil production
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("hot_water"), 125), IIngredient.of(ItemsTFC.OLIVE_PASTE), new FluidStack(FluidRegistry.getFluid("olive_oil_water"), 125), ItemStack.EMPTY, 2 * ICalendar.TICKS_IN_HOUR).setRegistryName("olive_water"),
                // Balance note: Classic gave 250mb for 160oz of olives ~= 32 items. We give 800 mb for that, so 3.2x more. Hopefully will help with lamp usage
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("olive_oil_water"), 250), IIngredient.of(ItemsTFC.JUTE_NET), new FluidStack(FluidRegistry.getFluid("olive_oil"), 50), new ItemStack(ItemsTFC.DIRTY_JUTE_NET), 0).setRegistryName("olive_oil"),
                // Balance: switch to fresh water. Hot water use that way is broken
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 125), IIngredient.of(ItemsTFC.DIRTY_JUTE_NET), null, new ItemStack(ItemsTFC.JUTE_NET), ICalendar.TICKS_IN_HOUR).setRegistryName("clean_net"),
                // Temperature recipes
                new BarrelRecipeTemperature(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 1), 50).setRegistryName("fresh_water_cooling"),
                new BarrelRecipeTemperature(IIngredient.of(FluidRegistry.getFluid("salt_water"), 1), 50).setRegistryName("salt_water_cooling")
        );

        for (FoodType foodOld : new FoodType[]{FoodTypes.SALAD_DAIRY, FoodTypes.SALAD_FRUIT, FoodTypes.SALAD_GRAIN, FoodTypes.SALAD_MEAT, FoodTypes.SALAD_VEGETABLE, FoodTypes.SOUP_DAIRY, FoodTypes.SOUP_FRUIT, FoodTypes.SOUP_GRAIN, FoodTypes.SOUP_MEAT, FoodTypes.SOUP_VEGETABLE}) {
            registry.register(new BarrelRecipeDynamicBowlFood(IIngredient.of(FluidRegistry.getFluid("fresh_water"), 200), IIngredient.of(TFCStorage.getFoodItem(foodOld)), 0).setRegistryName(foodOld.toString().toLowerCase() + "_cleaning"));
        }

        // The many many many recipes that is dye. This assumes that the standard meta values for colored objects are followed.
//        for (EnumDyeColor dyeColor : EnumDyeColor.values()) {
//            Fluid fluid = FluidsTFC.getFluidFromDye(dyeColor);
//            String dyeName = dyeColor == EnumDyeColor.SILVER ? "light_gray" : dyeColor.getName();
//            int dyeMeta = dyeColor.getMetadata();
//            registry.registerAll(
//                    // Dye fluid
//                    new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("hot_water"), 1000), IIngredient.of(OreDictionaryHelper.upperCaseToCamelCase("dye", dyeName)), new FluidStack(FluidsTFC.getFluidFromDye(dyeColor), 1000), ItemStack.EMPTY, ICalendar.TICKS_IN_HOUR).setRegistryName(dyeName),
//                    // Vanilla dye-able items
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of("woolWhite"), null, new ItemStack(Blocks.WOOL, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("wool_" + dyeName),
//                    new BarrelRecipe(IIngredient.of(fluid, 25), IIngredient.of(new ItemStack(Blocks.CARPET, 1, 0)), null, new ItemStack(Blocks.CARPET, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("carpet_" + dyeName),
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(new ItemStack(Items.BED, 1, 0)), null, new ItemStack(Items.BED, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("bed_" + dyeName),
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(Blocks.HARDENED_CLAY), null, new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("terracotta_" + dyeName),
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(Blocks.GLASS), null, new ItemStack(Blocks.STAINED_GLASS, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("glass_" + dyeName),
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(Blocks.GLASS_PANE), null, new ItemStack(Blocks.STAINED_GLASS_PANE, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("glass_pane_" + dyeName),
//                    // Glazed Vessels
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(ItemsTFC.UNFIRED_VESSEL), null, new ItemStack(ItemsTFC.UNFIRED_VESSEL_GLAZED, 1, 15 - dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("glazed_vessel_" + dyeName),
//                    // Concrete (vanilla + aggregate)
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(new ItemStack(Blocks.CONCRETE_POWDER, 1, 0)), null, new ItemStack(Blocks.CONCRETE_POWDER, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("concrete_" + dyeName),
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(TFCBlocks.AGGREGATE), null, new ItemStack(Blocks.CONCRETE_POWDER, 1, dyeMeta), ICalendar.TICKS_IN_HOUR).setRegistryName("aggregate_" + dyeName),
//                    // Alabaster
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(TFCStorage.getAlabasterBlock("plain", BRICK)), null, new ItemStack(TFCStorage.getAlabasterBlock(dyeColor.getName(), BRICK)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_bricks_" + dyeColor.getName()),
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(TFCStorage.getAlabasterBlock("plain", RAW)), null, new ItemStack(TFCStorage.getAlabasterBlock(dyeColor.getName(), RAW)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_raw_" + dyeColor.getName()),
//                    new BarrelRecipe(IIngredient.of(fluid, 125), IIngredient.of(TFCStorage.getAlabasterBlock("plain", SMOOTH)), null, new ItemStack(TFCStorage.getAlabasterBlock(dyeColor.getName(), SMOOTH)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_polished_" + dyeColor.getName())
//            );
//        }
        // Un-dyeing Recipes
        registry.registerAll(
                // Vanilla dye-able items
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 125), IIngredient.of("wool"), null, new ItemStack(Blocks.WOOL, 1, 0), ICalendar.TICKS_IN_HOUR).setRegistryName("wool_undo"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 25), IIngredient.of("carpet"), null, new ItemStack(Blocks.CARPET, 1, 0), ICalendar.TICKS_IN_HOUR).setRegistryName("carpet_undo"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 125), IIngredient.of("bed"), null, new ItemStack(Items.BED, 1, 0), ICalendar.TICKS_IN_HOUR).setRegistryName("bed_undo"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 125), IIngredient.of("terracotta"), null, new ItemStack(Blocks.HARDENED_CLAY), ICalendar.TICKS_IN_HOUR).setRegistryName("terracotta_undo"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 125), IIngredient.of("blockGlass"), null, new ItemStack(Blocks.GLASS), ICalendar.TICKS_IN_HOUR).setRegistryName("glass_undo"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 125), IIngredient.of("paneGlass"), null, new ItemStack(Blocks.GLASS_PANE), ICalendar.TICKS_IN_HOUR).setRegistryName("glass_pane_undo"),
                // Concrete
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 125), IIngredient.of("powderConcrete"), null, new ItemStack(TFCBlocks.AGGREGATE), ICalendar.TICKS_IN_HOUR).setRegistryName("concrete_undo"),
                // Alabaster
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 125), IIngredient.of("alabasterBricks"), null, new ItemStack(TFCStorage.getAlabasterBlock("plain", BRICK)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_bricks_undo"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 125), IIngredient.of("alabasterRaw"), null, new ItemStack(TFCStorage.getAlabasterBlock("plain", RAW)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_raw_undo"),
                new BarrelRecipe(IIngredient.of(FluidRegistry.getFluid("lye"), 125), IIngredient.of("alabasterPolished"), null, new ItemStack(TFCStorage.getAlabasterBlock("plain", SMOOTH)), ICalendar.TICKS_IN_HOUR).setRegistryName("alabaster_polished_undo")
        );
        // Dye combinations.
//        registry.registerAll(
//                //Orange
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.ORANGE), 2), 0).setRegistryName("orange_dye_red_yellow_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.RED), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.ORANGE), 2), 0).setRegistryName("orange_dye_yellow_red_liquid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED), 1000), IIngredient.of("dyeYellow"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.ORANGE), 1000), ItemStack.EMPTY, 0).setRegistryName("orange_dye_red_yellow_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.YELLOW), 1000), IIngredient.of("dyeRed"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.ORANGE), 1000), ItemStack.EMPTY, 0).setRegistryName("orange_dye_yellow_red_solid"),
//                //Light Blue
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIGHT_BLUE), 2), 0).setRegistryName("light_blue_dye_blue_white_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIGHT_BLUE), 2), 0).setRegistryName("light_blue_dye_white_blue_liquid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIGHT_BLUE), 1000), ItemStack.EMPTY, 0).setRegistryName("light_blue_dye_blue_white_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1000), IIngredient.of("dyeBlue"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIGHT_BLUE), 1000), ItemStack.EMPTY, 0).setRegistryName("light_blue_dye_white_blue_solid"),
//                //Magenta
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.MAGENTA), 2), 0).setRegistryName("magenta_dye_purple_pink_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.MAGENTA), 2), 0).setRegistryName("magenta_dye_pink_purple_liquid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE), 1000), IIngredient.of("dyePink"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.MAGENTA), 1000), ItemStack.EMPTY, 0).setRegistryName("magenta_dye_purple_pink_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK), 1000), IIngredient.of("dyePurple"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.MAGENTA), 1000), ItemStack.EMPTY, 0).setRegistryName("magenta_dye_pink_purple_solid"),
//                //Pink
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK), 2), 0).setRegistryName("pink_dye_red_white_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.RED), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK), 2), 0).setRegistryName("pink_dye_white_red_liquid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK), 1000), ItemStack.EMPTY, 0).setRegistryName("pink_dye_red_white_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1000), IIngredient.of("dyeRed"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PINK), 1000), ItemStack.EMPTY, 0).setRegistryName("pink_dye_white_red_solid"),
//                //Light Gray
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER), 2), 0).setRegistryName("light_gray_dye_white_gray_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 2), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER), 3), 0).setRegistryName("light_gray_dye_white_black_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER), 2), 0).setRegistryName("light_gray_dye_gray_white_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 2), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER), 3), 0).setRegistryName("light_gray_dye_black_white_liquid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1000), IIngredient.of("dyeGray"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER), 1000), ItemStack.EMPTY, 0).setRegistryName("light_gray_dye_white_gray_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 2000), IIngredient.of("dyeBlack"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER), 1000), ItemStack.EMPTY, 0).setRegistryName("light_gray_dye_white_black_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER), 1000), ItemStack.EMPTY, 0).setRegistryName("light_gray_dye_gray_white_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK), 500), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.SILVER), 1000), ItemStack.EMPTY, 0).setRegistryName("light_gray_dye_black_white_solid"),
//                //Lime
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIME), 2), 0).setRegistryName("lime_dye_green_white_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIME), 2), 0).setRegistryName("lime_dye_white_green_liquid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIME), 1000), ItemStack.EMPTY, 0).setRegistryName("lime_dye_green_white_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1000), IIngredient.of("dyeGreen"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.LIME), 1000), ItemStack.EMPTY, 0).setRegistryName("lime_dye_white_green_solid"),
//                //Cyan
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.CYAN), 2), 0).setRegistryName("cyan_dye_green_blue_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.CYAN), 2), 0).setRegistryName("cyan_dye_blue_green_liquid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.GREEN), 1000), IIngredient.of("dyeBlue"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.CYAN), 1000), ItemStack.EMPTY, 0).setRegistryName("cyan_dye_green_blue_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE), 1000), IIngredient.of("dyeGreen"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.CYAN), 1000), ItemStack.EMPTY, 0).setRegistryName("cyan_dye_blue_green_solid"),
//                //Purple
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE), 2), 0).setRegistryName("purple_dye_red_blue_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.RED), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE), 2), 0).setRegistryName("purple_dye_blue_red_liquid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.RED), 1000), IIngredient.of("dyeBlue"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE), 1000), ItemStack.EMPTY, 0).setRegistryName("purple_dye_red_blue_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLUE), 1000), IIngredient.of("dyeRed"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.PURPLE), 1000), ItemStack.EMPTY, 0).setRegistryName("purple_dye_blue_red_solid"),
//                //Gray
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY), 2), 0).setRegistryName("gray_dye_black_white_liquid"),
//                new BarrelRecipeFluidMixing(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1), new IngredientFluidItem(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK), 1), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY), 2), 0).setRegistryName("gray_dye_white_black_liquid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.BLACK), 1000), IIngredient.of("dyeWhite"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY), 1000), ItemStack.EMPTY, 0).setRegistryName("gray_dye_black_white_solid"),
//                new BarrelRecipe(IIngredient.of(FluidsTFC.getFluidFromDye(EnumDyeColor.WHITE), 1000), IIngredient.of("dyeBlack"), new FluidStack(FluidsTFC.getFluidFromDye(EnumDyeColor.GRAY), 1000), ItemStack.EMPTY, 0).setRegistryName("gray_dye_white_black_solid")
//        );
    }
}
