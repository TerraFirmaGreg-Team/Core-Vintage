package net.dries007.tfc.objects.inventory.ingredient;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;


import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

public class IngredientOreDict implements IIngredient<ItemStack> {

  private final String oreName;
  private final int amount;

  IngredientOreDict(@NotNull String oreName) {
    this(oreName, 1);
  }

  IngredientOreDict(@NotNull String oreName, int amount) {
    this.oreName = oreName;
    this.amount = amount;
  }

  @Override
  public NonNullList<ItemStack> getValidIngredients() {
    NonNullList<ItemStack> output = NonNullList.create();
    for (ItemStack out : OreDictionary.getOres(oreName)) {
      ItemStack addToList = out.copy(); // Don't tamper with forge's ore dictionary list
      addToList.setCount(amount);
      output.add(addToList);
    }
    return output;
  }

  @Override
  public boolean testIgnoreCount(ItemStack stack) {
    return stack != null && !stack.isEmpty() && OreDictionaryHelper.doesStackMatchOre(stack, oreName);
  }

  @Override
  public boolean test(ItemStack stack) {
    return testIgnoreCount(stack) && stack.getCount() >= amount;
  }

  @Override
  @NotNull
  public ItemStack consume(ItemStack input) {
    input.shrink(amount);
    return input;
  }

  @Override
  public int getAmount() {
    return amount;
  }

  @NotNull
  public String getOreName() {
    return oreName;
  }
}
