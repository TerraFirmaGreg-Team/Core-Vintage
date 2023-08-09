package net.dries007.tfc.objects.recipes.category;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariants;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import net.dries007.tfc.objects.items.ceramics.ItemMold;
import net.dries007.tfc.objects.recipes.UnmoldRecipe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class WorkbenchRecipes {

    private static final IForgeRegistry<IRecipe> workbenchRegistry = ForgeRegistries.RECIPES;

    public static void onRegisterWorkbenchRecipes() {
        registerUnmoldRecipes();
        registerProcessingRecipes();
        registerWoodRecipes();
    }

    private static void registerUnmoldRecipes() {
        var registry = ForgeRegistries.RECIPES;

        for (var material : GregTechAPI.materialManager.getRegistry("gregtech")) {
            for (var orePrefix : OrePrefix.values()) {
                var extendedOrePrefix = (IOrePrefixExtension) orePrefix;
                if (material.hasProperty(TFGPropertyKey.HEAT) && extendedOrePrefix.getHasMold()) {
                    if (material.hasFlag(TFGMaterialFlags.TOOL_MATERIAL_CAN_BE_UNMOLDED) || orePrefix == OrePrefix.ingot) {
                        registry.register(
                                new UnmoldRecipe(new ItemStack(ItemMold.get(orePrefix)), material, 1).setRegistryName(MOD_ID, "unmold_" + orePrefix.name + "_" + material.getName())
                        );
                    }

                }
            }
        }
    }

    private static void registerProcessingRecipes() {
        TFGOrePrefix.oreChunk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

        TFGOrePrefix.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreChalk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreChert.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreClaystone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreConglomerate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreDacite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreDolomite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreRhyolite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSchist.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    }

    private static void registerWoodRecipes() {
        for (var woodType : WoodType.getWoodTypes()) {
            // Barrel
            register(
                    "barrel_" + woodType,
                    new ItemStack(TFCStorage.getWoodBlock(WoodBlockVariants.BARREL, woodType)),
                    "L L", "L L", "LLL",
                    'L', new ItemStack(TFCStorage.getLumberItem(woodType))
            );
        }
    }

    private static void register(String recipeName, @Nonnull ItemStack output, Object... recipePattern) {
        var recipeLocation = new ResourceLocation(MOD_ID, recipeName);
        workbenchRegistry.register(new ShapedOreRecipe(recipeLocation, output, recipePattern).setRegistryName(recipeLocation));
    }
}
