package net.dries007.tfc.api.recipes.quern;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.Constants;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.ItemGem;

import javax.annotation.Nonnull;

public class QuernRecipeRandomGem extends QuernRecipe {

  private final Gem gem;

  public QuernRecipeRandomGem(IIngredient<ItemStack> input, Gem gem) {
    super(input, ItemGem.get(gem, Gem.Grade.NORMAL, 1));
    this.gem = gem;
  }

  @Nonnull
  @Override
  public ItemStack getOutputItem(ItemStack stack) {
    Gem.Grade grade = Gem.Grade.randomGrade(Constants.RNG);
    return ItemGem.get(gem, grade, 1);
  }
}
