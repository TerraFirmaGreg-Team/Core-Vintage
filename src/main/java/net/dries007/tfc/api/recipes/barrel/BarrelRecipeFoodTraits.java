package net.dries007.tfc.api.recipes.barrel;

import su.terrafirmagreg.modules.core.capabilities.food.CapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.ICapabilityFood;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.init.FluidsCore;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientItemFoodTrait;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BarrelRecipeFoodTraits extends BarrelRecipe {

  private final FoodTrait trait;
  private final String tooltipName;

  private BarrelRecipeFoodTraits(@Nonnull IIngredient<FluidStack> inputFluid, @Nonnull IIngredient<ItemStack> inputStack, FoodTrait trait, int duration, String tooltipName) {
    super(inputFluid, inputStack, null, ItemStack.EMPTY, duration);
    this.trait = trait;
    this.tooltipName = tooltipName;
  }

  public static BarrelRecipe pickling(@Nonnull IIngredient<ItemStack> inputStack) {
    return new BarrelRecipeFoodTraits(IIngredient.of(FluidsCore.VINEGAR.get(), 125), new IngredientItemFoodTrait(inputStack, FoodTrait.BRINED), FoodTrait.PICKLED,
      4 * ICalendar.TICKS_IN_HOUR, "barrel_recipe_pickling");
  }

  public static BarrelRecipe brining(@Nonnull IIngredient<ItemStack> inputStack) {
    return new BarrelRecipeFoodTraits(IIngredient.of(FluidsCore.BRINE.get(), 125), inputStack, FoodTrait.BRINED,
      4 * ICalendar.TICKS_IN_HOUR, "barrel_recipe_brining");
  }

  @Override
  public boolean isValidInput(@Nullable FluidStack inputFluid, ItemStack inputStack) {
    ICapabilityFood food = inputStack.getCapability(CapabilityFood.CAPABILITY, null);
    return super.isValidInput(inputFluid, inputStack) && food != null && !food.getTraits().contains(trait); // Don't apply again and again.
  }

  @Nonnull
  @Override
  public List<ItemStack> getOutputItem(FluidStack inputFluid, ItemStack inputStack) {
    int multiplier = getMultiplier(inputFluid, inputStack);
    ItemStack stack = inputStack.copy();
    stack.setCount(multiplier);

    ItemStack remainder = Helpers.consumeItem(inputStack.copy(), multiplier);
    ICapabilityFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
    if (food != null) {
      CapabilityFood.applyTrait(food, trait);
    }
    return Helpers.listOf(stack, remainder);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public String getResultName() {
    return I18n.format("tfc.tooltip." + tooltipName);
  }
}
