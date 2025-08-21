package su.terrafirmagreg.modules.rock.object.recipe.anvil;


import su.terrafirmagreg.modules.core.data.ingredient.IIngredient;
import su.terrafirmagreg.modules.core.feature.playerdata.spi.SmithingSkill;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.util.forge.ForgeRule;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AnvilRecipeManager
  implements IAnvilRecipeManager {

  private static final Set<IAnvilRecipe> recipes = new HashSet<>();

  @Nullable
  public static IAnvilRecipe findMatchingRecipe(ItemStack item) {
    return recipes.stream()
      .filter(x -> x.isValidInput(item))
      .findFirst()
      .orElse(null);
  }

  @Nullable
  public static IAnvilRecipe findMatchingRecipe(ResourceLocation resourceLocation) {
    return recipes.stream()
      .filter(x -> x.getRecipeName().equals(resourceLocation))
      .findFirst()
      .orElse(null);
  }

  @NotNull
  public static List<IAnvilRecipe> getAllFor(ItemStack stack) {
    return recipes.stream()
      .filter(x -> x.isValidInput(stack))
      .collect(Collectors.toList());
  }

  @Override
  public void addRecipe(ResourceLocation recipeName, IIngredient<ItemStack> inputItem,
                        ItemStack outputItem, Metal.Tier tier, ForgeRule... rules) {
    addRecipe(recipeName, inputItem, outputItem, tier, null, rules);

  }

  @Override
  public void addRecipe(ResourceLocation recipeName, IIngredient<ItemStack> inputItem,
                        ItemStack outputItem, Metal.Tier tier, @Nullable SmithingSkill.Type skillBonusType,
                        ForgeRule... rules) {
    addRecipe(new AnvilRecipe(recipeName, inputItem, outputItem, tier, skillBonusType, rules));

  }

  @Override
  public void addRecipe(IAnvilRecipe recipe) {
    recipes.add(recipe);
  }

  @Override
  public boolean removeRecipe(IAnvilRecipe recipe) {
    return recipes.remove(recipe);
  }

  @Override
  public Collection<IAnvilRecipe> recipes() {
    return Collections.unmodifiableSet(recipes);
  }
}
