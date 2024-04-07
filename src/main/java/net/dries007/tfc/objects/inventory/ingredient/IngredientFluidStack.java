package net.dries007.tfc.objects.inventory.ingredient;

import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IngredientFluidStack implements IIngredient<FluidStack> {

    private final FluidStack inputFluid;

    IngredientFluidStack(@NotNull Fluid fluid, int amount) {
        this(new FluidStack(fluid, amount));
    }

    IngredientFluidStack(@NotNull FluidStack inputFluid) {
        this.inputFluid = inputFluid;
    }

    @Override
    public NonNullList<FluidStack> getValidIngredients() {
        return NonNullList.withSize(1, inputFluid.copy());
    }

    @Override
    public boolean test(FluidStack fluidStack) {
        return testIgnoreCount(fluidStack) && fluidStack.amount >= this.inputFluid.amount;
    }

    @Override
    public boolean testIgnoreCount(FluidStack fluidStack) {
        return fluidStack != null && fluidStack.getFluid() != null && fluidStack.getFluid() == this.inputFluid.getFluid();
    }

    @Override
    @Nullable
    public FluidStack consume(FluidStack input) {
        if (input.amount > inputFluid.amount) {
            return new FluidStack(input.getFluid(), input.amount - inputFluid.amount);
        }
        return null;
    }

    @Override
    public int getAmount() {
        if (inputFluid != null) {
            return inputFluid.amount;
        }
        return 0;
    }
}
