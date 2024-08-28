package su.terrafirmagreg.modules.metal.objects.recipe.anvil;

import su.terrafirmagreg.api.base.recipe.ICraftingProvider;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;


import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.forge.ForgeRule;
import net.dries007.tfc.util.skills.SmithingSkill;

import org.jetbrains.annotations.Nullable;

public interface IAnvilRecipeManager
        extends ICraftingProvider<IAnvilRecipe> {

    void addRecipe(ResourceLocation recipeName, IIngredient<ItemStack> inputItem, ItemStack outputItem, Metal.Tier tier, ForgeRule... rules);

    void addRecipe(ResourceLocation recipeName, IIngredient<ItemStack> inputItem, ItemStack outputItem, Metal.Tier tier, @Nullable SmithingSkill.Type skillBonusType,
                   ForgeRule... rules);
}
