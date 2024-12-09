package su.terrafirmagreg.modules.device.object.recipe.dryingmat;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class DryingMatRecipeManager implements IDryingMatRecipeManager {

  private static final Set<IDryingMatRecipe> recipes = new HashSet<>();

  @Nullable
  public static IDryingMatRecipe findMatchingRecipe(ItemStack item) {
    return recipes.stream()
                  .filter(x -> x.isValidInput(item))
                  .findFirst()
                  .orElse(null);
  }

  @Override
  public void addRecipe(IIngredient<ItemStack> inputItem, ItemStack outputItem, int duration) {
    addRecipe(new DryingMatRecipe(inputItem, outputItem, duration));
  }

  @Override
  public void addRecipe(IDryingMatRecipe recipe) {
    recipes.add(recipe);
  }

  @Override
  public boolean removeRecipe(IDryingMatRecipe recipe) {
    return recipes.remove(recipe);
  }

  @Override
  public Collection<IDryingMatRecipe> recipes() {
    return Collections.unmodifiableSet(recipes);
  }
}
