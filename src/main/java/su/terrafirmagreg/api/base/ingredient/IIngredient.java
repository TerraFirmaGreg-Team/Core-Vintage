package su.terrafirmagreg.api.base.ingredient;

import net.minecraft.util.NonNullList;


import java.util.function.Predicate;

/**
 * This is an ingredient wrapper for various types It includes static constructors for both item stack and fluid stack ingredients
 *
 * @param <T> the type of ingredient (i.e. ItemStack, FluidStack, etc.)
 * @author AlcatrazEscapee
 */
public interface IIngredient<T> extends Predicate<T> {

  IIngredient<?> EMPTY = input -> false;
  IIngredient<?> ANY = input -> true;

  @SuppressWarnings("unchecked")
  static <P> IIngredient<P> empty() {
    return (IIngredient<P>) EMPTY;
  }

  @SuppressWarnings("unchecked")
  static <P> IIngredient<P> any() {
    return (IIngredient<P>) ANY;
  }

  /**
   * This is used by JEI-CT hooks, return a valid list of inputs for this IIngredient
   *
   * @return NonNullList containing valid ingredients(fluidstack/itemstack) for this IIngredient
   */
  default NonNullList<T> getValidIngredients() {
    return NonNullList.create();
  }

  /**
   * This is used by recipes to test if the ingredient matches the input
   *
   * @param input the input supplied to the recipe
   * @return true if the ingredient matches the input
   */
  @Override
  boolean test(T input);

  /**
   * This is used by recipes to test if an input is valid, but necessarily high enough quantity i.e. used to check if an item is valid for a slot, not
   * if the recipe will complete.
   *
   * @param input the input supplied to the recipe
   * @return true if the ingredient matches the input, ignoring the amount of input
   */
  default boolean testIgnoreCount(T input) {
    return test(input);
  }

  /**
   * Consume one recipe's worth of input Depending on the input type, this may modify input and return it, or create a new output
   *
   * @param input the input supplied to the recipe
   * @return the result after modification.
   */
  default T consume(T input) {
    return input;
  }

  /**
   * Get the amount represented by this ingredient
   *
   * @return the amount
   */
  default int getAmount() {
    return 1;
  }
}
