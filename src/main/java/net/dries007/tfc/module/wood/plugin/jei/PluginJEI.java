package net.dries007.tfc.module.wood.plugin.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.dries007.tfc.compat.jei.wrappers.SimpleRecipeWrapper;
import net.dries007.tfc.module.core.init.RegistryCore;
import net.dries007.tfc.module.wood.StorageWood;
import net.dries007.tfc.module.wood.api.recipes.barrel.BarrelRecipeFoodPreservation;
import net.dries007.tfc.module.wood.api.recipes.barrel.BarrelRecipeFoodTraits;
import net.dries007.tfc.module.wood.api.types.variant.block.WoodBlockVariants;
import net.dries007.tfc.module.wood.client.gui.GuiWoodBarrel;
import net.dries007.tfc.module.wood.plugin.jei.categories.JEIRecipeCategoryBarrel;
import net.dries007.tfc.module.wood.plugin.jei.categories.JEIRecipeCategoryLoom;
import net.dries007.tfc.module.wood.plugin.jei.wrappers.JEIRecipeWrapperBarrel;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.stream.Collectors;

@ParametersAreNonnullByDefault
@JEIPlugin
public final class PluginJEI implements IModPlugin {


    private static IModRegistry REGISTRY;

    /**
     * Helper method to return a collection containing all possible itemstacks registered in JEI
     *
     * @return Collection of ItemStacks
     */
    public static Collection<ItemStack> getAllIngredients() {
        return REGISTRY.getIngredientRegistry().getAllIngredients(VanillaTypes.ITEM);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        var guiHelper = registry.getJeiHelpers().getGuiHelper();

        // Add new JEI recipe categories
        registry.addRecipeCategories(
                new JEIRecipeCategoryBarrel(guiHelper),
                new JEIRecipeCategoryLoom(guiHelper)
        );
    }

    @Override
    public void register(IModRegistry registry) {
        REGISTRY = registry;


        // Barrel Recipes
        for (var barrel : StorageWood.WOOD_BLOCKS.values().stream().filter(s -> s.getBlockVariant() == WoodBlockVariants.BARREL).toArray()) {
            registry.addRecipeCatalyst(new ItemStack((Block) barrel), JEIRecipeCategoryBarrel.UID);
        }

        var barrelRecipes = RegistryCore.BARREL.getValuesCollection()
                .stream()
                .filter(recipe -> recipe instanceof BarrelRecipeFoodTraits ||
                        recipe instanceof BarrelRecipeFoodPreservation ||
                        recipe.getOutputStack() != ItemStack.EMPTY ||
                        recipe.getOutputFluid() != null)
                .map(JEIRecipeWrapperBarrel::new)
                .collect(Collectors.toList());

        registry.addRecipes(barrelRecipes, JEIRecipeCategoryBarrel.UID);
        registry.addRecipeClickArea(GuiWoodBarrel.class, 92, 21, 9, 14, JEIRecipeCategoryBarrel.UID);

        // Loom Recipes
        for (var loom : StorageWood.WOOD_BLOCKS.values().stream().filter(s -> s.getBlockVariant() == WoodBlockVariants.LOOM).toArray()) {
            registry.addRecipeCatalyst(new ItemStack((Block) loom), JEIRecipeCategoryLoom.UID);
        }

        var loomRecipes = RegistryCore.LOOM.getValuesCollection()
                .stream()
                .map(SimpleRecipeWrapper::new)
                .collect(Collectors.toList());

        registry.addRecipes(loomRecipes, JEIRecipeCategoryLoom.UID);

    }
}
