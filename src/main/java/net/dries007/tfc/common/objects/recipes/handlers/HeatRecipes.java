package net.dries007.tfc.common.objects.recipes.handlers;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipeMetalMelting;
import net.dries007.tfc.api.recipes.heat.HeatRecipeSimple;
import net.dries007.tfc.api.recipes.heat.HeatRecipeVessel;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.food.variant.FoodVariants;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.common.objects.items.ItemsTFC;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.common.objects.items.ceramics.ItemMold;
import net.dries007.tfc.common.objects.items.ceramics.ItemUnfiredMold;
import net.dries007.tfc.common.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class HeatRecipes {

    public static void register() {
        var registry = TFCRegistries.HEAT;

        for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
            if (material.hasProperty(TFGPropertyKey.HEAT))
                registry.register(new HeatRecipeMetalMelting(material).setRegistryName(material.getName() + "_melting"));
        }

        // Pottery Items with metadata
        for (EnumDyeColor dye : EnumDyeColor.values()) {
            registry.register(
                    new HeatRecipeSimple(IIngredient.of(new ItemStack(ItemsTFC.UNFIRED_VESSEL_GLAZED, 1, dye.getMetadata())), new ItemStack(ItemsTFC.FIRED_VESSEL_GLAZED, 1, dye.getMetadata()), 1599f, 1).setRegistryName("unfired_vessel_glazed_" + dye.getName())
            );
        }

        // Molds
        for (var orePrefix : OrePrefix.values()) {
            var extendedOrePrefix = (IOrePrefixExtension) orePrefix;
            if (extendedOrePrefix.getHasMold()) {
                var unfiredMold = TFCStorage.UNFIRED_MOLDS.get(orePrefix);
                var firedMold = TFCStorage.FIRED_MOLDS.get(orePrefix);

                if (unfiredMold != null && firedMold != null) {
                    registry.register(new HeatRecipeSimple(IIngredient.of(unfiredMold), new ItemStack(firedMold), 1599f, 1).setRegistryName("fired_mold_" + orePrefix.name.toLowerCase()));
                }
            }
        }

        // Standard / Simple recipes
        registry.registerAll(
                // Pottery
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_FIRE_BRICK), new ItemStack(ItemsTFC.FIRED_FIRE_BRICK), 1599f, 1).setRegistryName("unfired_fire_brick"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_VESSEL), new ItemStack(ItemsTFC.FIRED_VESSEL), 1599f, 1).setRegistryName("unfired_vessel"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_JUG), new ItemStack(ItemsTFC.FIRED_JUG), 1599f, 1).setRegistryName("unfired_jug"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_POT), new ItemStack(ItemsTFC.FIRED_POT), 1599f, 1).setRegistryName("unfired_pot"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_BOWL), new ItemStack(ItemsTFC.FIRED_BOWL), 1599f, 1).setRegistryName("unfired_bowl"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_SPINDLE), new ItemStack(ItemsTFC.FIRED_SPINDLE), 1599f, 1).setRegistryName("unfired_spindle"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_LARGE_VESSEL), new ItemStack(TFCBlocks.FIRED_LARGE_VESSEL), 1599f, 1).setRegistryName("unfired_large_vessel"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_CRUCIBLE), new ItemStack(TFCBlocks.CRUCIBLE), 1599f, 1).setRegistryName("unfired_crucible"),

                // Fired Pottery - doesn't burn up
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.FIRED_FIRE_BRICK), new ItemStack(ItemsTFC.FIRED_FIRE_BRICK), 1599f, 1).setRegistryName("fired_fire_brick"),
                new HeatRecipeVessel(IIngredient.of(ItemsTFC.FIRED_VESSEL), 1599f, 1).setRegistryName("fired_vessel"),
                new HeatRecipeVessel(IIngredient.of(ItemsTFC.FIRED_VESSEL_GLAZED), 1599f, 1).setRegistryName("fired_vessel_glazed_all"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.FIRED_JUG), new ItemStack(ItemsTFC.FIRED_JUG), 1599f, 1).setRegistryName("fired_jug"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.FIRED_POT), new ItemStack(ItemsTFC.FIRED_POT), 1599f, 1).setRegistryName("fired_pot"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.FIRED_BOWL), new ItemStack(ItemsTFC.FIRED_BOWL), 1599f, 1).setRegistryName("fired_bowl"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.FIRED_SPINDLE), new ItemStack(ItemsTFC.FIRED_SPINDLE), 1599f, 1).setRegistryName("fired_spindle"),
                new HeatRecipeSimple(IIngredient.of(TFCBlocks.FIRED_LARGE_VESSEL), new ItemStack(TFCBlocks.FIRED_LARGE_VESSEL), 1599f, 1).setRegistryName("fired_large_vessel"),
                new HeatRecipeSimple(IIngredient.of(TFCBlocks.CRUCIBLE), new ItemStack(TFCBlocks.CRUCIBLE), 1599f, 1).setRegistryName("fired_crucible"),

                // Misc
                new HeatRecipeSimple(IIngredient.of("stickWood"), new ItemStack(Blocks.TORCH, 2), 40).setRegistryName("torch"),
                new HeatRecipeSimple(IIngredient.of(TFCItems.STICK_BUNCH), new ItemStack(Blocks.TORCH, 18), 60).setRegistryName("torch_stick_bunch"),
                new HeatRecipeSimple(IIngredient.of("sand"), new ItemStack(Blocks.GLASS), 600).setRegistryName("glass"),
                new HeatRecipeSimple(IIngredient.of(TFCItems.GLASS_SHARD), new ItemStack(Blocks.GLASS), 600).setRegistryName("glass_shard"),
                new HeatRecipeSimple(IIngredient.of("blockClay"), new ItemStack(Blocks.HARDENED_CLAY), 600).setRegistryName("terracotta"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_BRICK), new ItemStack(Items.BRICK), 1500).setRegistryName("unfired_brick"),
                new HeatRecipeSimple(IIngredient.of(ItemsTFC.UNFIRED_FLOWER_POT), new ItemStack(Items.FLOWER_POT), 1500).setRegistryName("unfired_flower_pot"),

                // Bread
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.BARLEY_DOUGH)), new ItemStack(ItemFoodTFC.get(FoodVariants.BARLEY_BREAD)), 200, 480).setRegistryName("barley_bread"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.CORNMEAL_DOUGH)), new ItemStack(ItemFoodTFC.get(FoodVariants.CORNBREAD)), 200, 480).setRegistryName("cornbread"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.OAT_DOUGH)), new ItemStack(ItemFoodTFC.get(FoodVariants.OAT_BREAD)), 200, 480).setRegistryName("oat_bread"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.RICE_DOUGH)), new ItemStack(ItemFoodTFC.get(FoodVariants.RICE_BREAD)), 200, 480).setRegistryName("rice_bread"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.RYE_DOUGH)), new ItemStack(ItemFoodTFC.get(FoodVariants.RYE_BREAD)), 200, 480).setRegistryName("rye_bread"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.WHEAT_DOUGH)), new ItemStack(ItemFoodTFC.get(FoodVariants.WHEAT_BREAD)), 200, 480).setRegistryName("wheat_bread"),

                // Meat
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.BEEF)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_BEEF)), 200, 480).setRegistryName("cooked_beef"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.PORK)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_PORK)), 200, 480).setRegistryName("cooked_pork"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.CHICKEN)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_CHICKEN)), 200, 480).setRegistryName("cooked_chicken"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.MUTTON)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_MUTTON)), 200, 480).setRegistryName("cooked_mutton"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.FISH)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_FISH)), 200, 480).setRegistryName("cooked_fish"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.BEAR)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_BEAR)), 200, 480).setRegistryName("cooked_bear"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.CALAMARI)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_CALAMARI)), 200, 480).setRegistryName("cooked_calamari"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.HORSE_MEAT)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_HORSE_MEAT)), 200, 480).setRegistryName("cooked_horse_meat"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.PHEASANT)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_PHEASANT)), 200, 480).setRegistryName("cooked_pheasant"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.VENISON)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_VENISON)), 200, 480).setRegistryName("cooked_venison"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.RABBIT)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_RABBIT)), 200, 480).setRegistryName("cooked_rabbit"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.WOLF)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_WOLF)), 200, 480).setRegistryName("cooked_wolf"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.CAMELIDAE)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_CAMELIDAE)), 200, 480).setRegistryName("cooked_camelidae"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.MONGOOSE)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_MONGOOSE)), 200, 480).setRegistryName("cooked_mongoose"),
                new HeatRecipeSimple(IIngredient.of(ItemFoodTFC.get(FoodVariants.GRAN_FELINE)), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_GRAN_FELINE)), 200, 480).setRegistryName("cooked_gran_feline"),

                // Egg
                new HeatRecipeSimple(IIngredient.of(Items.EGG), new ItemStack(ItemFoodTFC.get(FoodVariants.COOKED_EGG)), 200, 480).setRegistryName("cooked_egg"),

                // Bread
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.BARLEY_BREAD)), 480).setRegistryName("burned_barley_bread"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.CORNBREAD)), 480).setRegistryName("burned_cornbread"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.OAT_BREAD)), 480).setRegistryName("burned_oat_bread"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.RICE_BREAD)), 480).setRegistryName("burned_rice_bread"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.RYE_BREAD)), 480).setRegistryName("burned_rye_bread"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.WHEAT_BREAD)), 480).setRegistryName("burned_wheat_bread"),

                // Meat
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_BEEF)), 480).setRegistryName("burned_beef"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_PORK)), 480).setRegistryName("burned_pork"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_CHICKEN)), 480).setRegistryName("burned_chicken"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_MUTTON)), 480).setRegistryName("burned_mutton"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_FISH)), 480).setRegistryName("burned_fish"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_BEAR)), 480).setRegistryName("burned_bear"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_CALAMARI)), 480).setRegistryName("burned_calamari"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_HORSE_MEAT)), 480).setRegistryName("burned_horse_meat"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_PHEASANT)), 480).setRegistryName("burned_pheasant"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_RABBIT)), 480).setRegistryName("burned_rabbit"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_WOLF)), 480).setRegistryName("burned_wolf"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_VENISON)), 480).setRegistryName("burned_venison"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_CAMELIDAE)), 480).setRegistryName("burned_camelidae"),
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_MONGOOSE)), 480).setRegistryName("burned_mongoose"),

                // Egg
                HeatRecipe.destroy(IIngredient.of(ItemFoodTFC.get(FoodVariants.COOKED_EGG)), 480).setRegistryName("burned_egg"),

                // Glazed terracotta, because minecraft decided *this* one should not use metadata.
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.WHITE.getMetadata())), new ItemStack(Blocks.WHITE_GLAZED_TERRACOTTA), 1200).setRegistryName("white_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.ORANGE.getMetadata())), new ItemStack(Blocks.ORANGE_GLAZED_TERRACOTTA), 1200).setRegistryName("orange_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.MAGENTA.getMetadata())), new ItemStack(Blocks.MAGENTA_GLAZED_TERRACOTTA), 1200).setRegistryName("magenta_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.LIGHT_BLUE.getMetadata())), new ItemStack(Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA), 1200).setRegistryName("light_blue_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.YELLOW.getMetadata())), new ItemStack(Blocks.YELLOW_GLAZED_TERRACOTTA), 1200).setRegistryName("yellow_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.LIME.getMetadata())), new ItemStack(Blocks.LIME_GLAZED_TERRACOTTA), 1200).setRegistryName("lime_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.PINK.getMetadata())), new ItemStack(Blocks.PINK_GLAZED_TERRACOTTA), 1200).setRegistryName("pink_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.GRAY.getMetadata())), new ItemStack(Blocks.GRAY_GLAZED_TERRACOTTA), 1200).setRegistryName("gray_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.SILVER.getMetadata())), new ItemStack(Blocks.SILVER_GLAZED_TERRACOTTA), 1200).setRegistryName("silver_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.CYAN.getMetadata())), new ItemStack(Blocks.CYAN_GLAZED_TERRACOTTA), 1200).setRegistryName("cyan_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.PURPLE.getMetadata())), new ItemStack(Blocks.PURPLE_GLAZED_TERRACOTTA), 1200).setRegistryName("purple_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLUE.getMetadata())), new ItemStack(Blocks.BLUE_GLAZED_TERRACOTTA), 1200).setRegistryName("blue_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BROWN.getMetadata())), new ItemStack(Blocks.BROWN_GLAZED_TERRACOTTA), 1200).setRegistryName("brown_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.GREEN.getMetadata())), new ItemStack(Blocks.GREEN_GLAZED_TERRACOTTA), 1200).setRegistryName("green_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.RED.getMetadata())), new ItemStack(Blocks.RED_GLAZED_TERRACOTTA), 1200).setRegistryName("red_glazed_terracotta"),
                new HeatRecipeSimple(IIngredient.of(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 1, EnumDyeColor.BLACK.getMetadata())), new ItemStack(Blocks.BLACK_GLAZED_TERRACOTTA), 1200).setRegistryName("black_glazed_terracotta")
        );
    }
}
