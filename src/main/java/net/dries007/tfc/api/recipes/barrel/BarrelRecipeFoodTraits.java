package net.dries007.tfc.api.recipes.barrel;

import su.terrafirmagreg.api.util.CollectionUtils;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.modules.core.capabilities.food.spi.FoodTrait;

import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.objects.fluids.FluidsTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.inventory.ingredient.IngredientItemFoodTrait;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BarrelRecipeFoodTraits extends BarrelRecipe {

  private final FoodTrait trait;
  private final String tooltipName;

  private BarrelRecipeFoodTraits(@NotNull IIngredient<FluidStack> inputFluid, @NotNull IIngredient<ItemStack> inputStack, FoodTrait trait,
                                 int duration, String tooltipName) {
    super(inputFluid, inputStack, null, ItemStack.EMPTY, duration);
    this.trait = trait;
    this.tooltipName = tooltipName;
  }

  public static BarrelRecipe pickling(@NotNull IIngredient<ItemStack> inputStack) {
    return new BarrelRecipeFoodTraits(IIngredient.of(FluidsTFC.VINEGAR.get(), 125), new IngredientItemFoodTrait(inputStack, FoodTrait.BRINED),
                                      FoodTrait.PICKLED, 4 * ICalendar.TICKS_IN_HOUR, "barrel_recipe_pickling");
  }

  public static BarrelRecipe brining(@NotNull IIngredient<ItemStack> inputStack) {
    return new BarrelRecipeFoodTraits(IIngredient.of(FluidsTFC.BRINE.get(), 125), inputStack, FoodTrait.BRINED, 4 * ICalendar.TICKS_IN_HOUR,
                                      "barrel_recipe_brining");
  }

  @Override
  public boolean isValidInput(@Nullable FluidStack inputFluid, ItemStack inputStack) {
    IFood food = inputStack.getCapability(CapabilityFood.CAPABILITY, null);
    return super.isValidInput(inputFluid, inputStack) && food != null && !food.getTraits()
                                                                              .contains(trait); // Don't apply again and again.
  }

  @NotNull
  @Override
  public List<ItemStack> getOutputItem(FluidStack inputFluid, ItemStack inputStack) {
    int multiplier = getMultiplier(inputFluid, inputStack);
    ItemStack stack = inputStack.copy();
    stack.setCount(multiplier);

    ItemStack remainder = StackUtils.consumeItem(inputStack.copy(), multiplier);
    IFood food = stack.getCapability(CapabilityFood.CAPABILITY, null);
    if (food != null) {
      CapabilityFood.applyTrait(food, trait);
    }
    return CollectionUtils.listOf(stack, remainder);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public String getResultName() {
    return I18n.format("tfc.tooltip." + tooltipName);
  }
}
