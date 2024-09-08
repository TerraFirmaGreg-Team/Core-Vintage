package su.terrafirmagreg.modules.device.objects.recipes.quern;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class QuernRecipeManager implements IQuernRecipeManager {

  private static final Set<IQuernRecipe> recipes = new HashSet<>();

  @Nullable
  public static IQuernRecipe findMatchingRecipe(ItemStack item) {
    return recipes.stream()
            .filter(x -> x.isValidInput(item))
            .findFirst()
            .orElse(null);
  }

  @Override
  public void addRecipe(IIngredient<ItemStack> inputItem, ItemStack outputItem) {
    addRecipe(new QuernRecipe(inputItem, outputItem));
  }

  @Override
  public void addRecipe(IQuernRecipe recipe) {
    recipes.add(recipe);
  }

  @Override
  public boolean removeRecipe(IQuernRecipe recipe) {
    return recipes.remove(recipe);
  }

  @Override
  public Collection<IQuernRecipe> recipes() {
    return Collections.unmodifiableSet(recipes);
  }
}
