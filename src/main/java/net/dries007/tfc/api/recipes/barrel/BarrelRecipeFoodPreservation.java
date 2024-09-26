package net.dries007.tfc.api.recipes.barrel;

import su.terrafirmagreg.api.util.CollectionUtils;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientItemFoodTrait;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BarrelRecipeFoodPreservation extends BarrelRecipe {

  private final FoodTrait trait;
  private final String tooltipName;

  public BarrelRecipeFoodPreservation(@NotNull IIngredient<FluidStack> inputFluid, @NotNull IIngredient<ItemStack> inputStack, FoodTrait trait,
                                      String tooltipName) {
    super(inputFluid, inputStack, null, ItemStack.EMPTY, -1);
    this.trait = trait;
    this.tooltipName = tooltipName;
  }

  public static BarrelRecipe vinegar(@NotNull IIngredient<ItemStack> inputStack) {
    return new BarrelRecipeFoodPreservation(IIngredient.of(FluidsTFC.VINEGAR.get(), 125),
                                            new IngredientItemFoodTrait(inputStack, FoodTrait.PICKLED), FoodTrait.VINEGAR, "barrel_recipe_vinegar");
  }

  @Override
  public boolean isValidInput(@Nullable FluidStack inputFluid, ItemStack inputStack) {
    // Only preserve food it there's enough fluid (the amount needed for 1 item * the number of items)
    return super.isValidInput(inputFluid, inputStack) &&
           (inputFluid == null || inputFluid.amount / this.inputFluid.getAmount() >= inputStack.getCount() / this.inputStack.getAmount());
  }

  @NotNull
  @Override
  public List<ItemStack> getOutputItem(FluidStack inputFluid, ItemStack inputStack) {
    return CollectionUtils.listOf(inputStack);
  }

  @Override
  public void onBarrelSealed(FluidStack inputFluid, ItemStack inputStack) {
    CapabilityFood.applyTrait(inputStack, trait);
  }

  @NotNull
  @Override
  public List<ItemStack> getOutputItemOnUnseal(FluidStack inputFluid, ItemStack inputStack) {
    CapabilityFood.removeTrait(inputStack, trait);
    return CollectionUtils.listOf(inputStack);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public String getResultName() {
    return I18n.format("tfc.tooltip." + tooltipName);
  }
}
