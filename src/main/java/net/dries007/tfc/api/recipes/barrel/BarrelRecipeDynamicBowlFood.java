package net.dries007.tfc.api.recipes.barrel;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.IFood;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.objects.items.food.ItemDynamicBowlFood;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class BarrelRecipeDynamicBowlFood extends BarrelRecipe {

    public BarrelRecipeDynamicBowlFood(IIngredient<FluidStack> inputFluid, IIngredient<ItemStack> inputStack, int duration) {
        super(inputFluid, inputStack, null, new ItemStack(Items.BOWL), duration);
    }

    @Override
    public boolean isValidInputInstant(ItemStack inputStack, @Nullable FluidStack inputFluid) {
        return true; // Since we don't care about ratios, excess item input will be leftover, and there's no excess fluid output.
    }

    @NotNull
    public List<ItemStack> getOutputItem(FluidStack inputFluid, ItemStack inputStack) {
        int multiplier = getMultiplier(inputFluid, inputStack);
        List<ItemStack> outputList = new ArrayList<>();
        IFood food = inputStack.getCapability(CapabilityFood.CAPABILITY, null);
        ItemStack outputStack = ItemStack.EMPTY;
        if (food instanceof ItemDynamicBowlFood.DynamicFoodHandler) {
            outputStack = ((ItemDynamicBowlFood.DynamicFoodHandler) food).getBowlStack();
        }
        if (!outputStack.isEmpty()) {
            // Ignore input and replace with output
            int expectedOutputCount = multiplier * outputStack.getCount();
            int outputCount = expectedOutputCount;
            do {
                int count = Math.min(outputCount, outputStack.getMaxStackSize());
                ItemStack output = outputStack.copy();
                output.setCount(count);
                outputCount -= count;
                outputList.add(output);
            } while (outputCount > 0);
            // Since this is 1-1 instant with an item input, convert excess
            if (inputStack.getCount() > expectedOutputCount) {
                ItemStack extraInput = inputStack.copy();
                extraInput.setCount(inputStack.getCount() - outputCount);
                outputList.add(extraInput);
            }
        } else {
            // Try and keep as much of the original input as possible
            int retainCount = inputStack.getCount() - (multiplier * this.inputStack.getAmount());
            if (retainCount > 0) {
                inputStack.setCount(retainCount);
                outputList.add(inputStack);
            } else {
                outputList.add(ItemStack.EMPTY);
            }
        }
        return outputList;
    }
}
