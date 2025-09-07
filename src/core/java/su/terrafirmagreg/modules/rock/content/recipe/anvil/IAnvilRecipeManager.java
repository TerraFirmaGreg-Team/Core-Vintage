package su.terrafirmagreg.modules.rock.content.recipe.anvil;


import su.terrafirmagreg.framework.manager.content.base.recipe.api.IBaseRecipeManager;
import su.terrafirmagreg.modules.core.data.ingredient.IIngredient;
import su.terrafirmagreg.modules.core.feature.playerdata.spi.SmithingSkill;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.util.forge.ForgeRule;

import org.jetbrains.annotations.Nullable;

public interface IAnvilRecipeManager
  extends IBaseRecipeManager<IAnvilRecipe> {

  void addRecipe(ResourceLocation recipeName, IIngredient<ItemStack> inputItem,
                 ItemStack outputItem, Metal.Tier tier, ForgeRule... rules);

  void addRecipe(ResourceLocation recipeName, IIngredient<ItemStack> inputItem,
                 ItemStack outputItem, Metal.Tier tier, @Nullable SmithingSkill.Type skillBonusType,
                 ForgeRule... rules);
}
