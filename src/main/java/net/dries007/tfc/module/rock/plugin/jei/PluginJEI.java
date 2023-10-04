package net.dries007.tfc.module.rock.plugin.jei;

import gregtech.api.unification.material.Materials;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.dries007.tfc.client.gui.GuiKnapping;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolItems;
import net.dries007.tfc.module.core.api.recipes.knapping.KnappingType;
import net.dries007.tfc.module.core.init.RegistryCore;
import net.dries007.tfc.module.rock.StorageRock;
import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.dries007.tfc.module.rock.plugin.jei.categories.JEIRecipeCategoryChisel;
import net.dries007.tfc.module.rock.plugin.jei.categories.JEIRecipeCategoryKnapping;
import net.dries007.tfc.module.rock.plugin.jei.wrappers.JEIRecipeWrapperChisel;
import net.dries007.tfc.module.rock.plugin.jei.wrappers.JEIRecipeWrapperKnapping;
import net.minecraft.item.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.stream.Collectors;

import static net.dries007.tfc.module.rock.api.types.variant.item.RockItemVariants.LOOSE;

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
                new JEIRecipeCategoryChisel(guiHelper),
                new JEIRecipeCategoryKnapping(guiHelper)
                // new RockLayerCategory(gui),
        );
    }

    @Override
    public void register(IModRegistry registry) {
        REGISTRY = registry;


        // Chisel Recipes
        registry.addRecipeCatalyst(TFGToolItems.CHISEL.get(Materials.Iron), JEIRecipeCategoryChisel.UID);

        var chiselList = RegistryCore.CHISEL.getValuesCollection()
                .stream()
                .map(JEIRecipeWrapperChisel::new)
                .collect(Collectors.toList());

        registry.addRecipes(chiselList, JEIRecipeCategoryChisel.UID);

        // Stone Knapping Recipes
        for (var type : RockType.getRockTypes()) {
            registry.addRecipeCatalyst(new ItemStack(StorageRock.getRockItem(LOOSE, type)), JEIRecipeCategoryKnapping.UID);
        }

        var stoneknapRecipes = RegistryCore.KNAPPING.getValuesCollection().stream()
                .filter(recipe -> recipe.getType() == KnappingType.STONE)
                .map(recipe -> new JEIRecipeWrapperKnapping(recipe, registry.getJeiHelpers().getGuiHelper()))
                .collect(Collectors.toList());

        registry.addRecipes(stoneknapRecipes, JEIRecipeCategoryKnapping.UID);

        // Wraps all rock layers
        //List<RockLayerWrapper> rockLayerList = TFCRegistries.ROCKS.getValuesCollection()
        //    .stream()
        //    .map(RockLayerWrapper::new)
        //    .collect(Collectors.toList());

        //registry.addRecipes(rockLayerList, RockLayerCategory.UID);

        // Click areas
        registry.addRecipeClickArea(GuiKnapping.class, 97, 44, 22, 15, JEIRecipeCategoryKnapping.UID);

    }
}

