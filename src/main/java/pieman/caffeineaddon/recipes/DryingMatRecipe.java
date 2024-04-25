package pieman.caffeineaddon.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.registries.IForgeRegistryEntry;


import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.compat.jei.IJEISimpleRecipe;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DryingMatRecipe extends IForgeRegistryEntry.Impl<DryingMatRecipe> implements IJEISimpleRecipe {

    private IIngredient<ItemStack> inputItem;
    private ItemStack outputItem;
    private int duration;

    public DryingMatRecipe(IIngredient<ItemStack> input, ItemStack output, int duration) {
        this.inputItem = input;
        this.outputItem = output;
        this.duration = duration;

        if (inputItem == null || outputItem == null) {
            throw new IllegalArgumentException("Input and output are not allowed to be empty");
        }
    }

    @Nullable
    public static DryingMatRecipe get(ItemStack item) {
        return Registries.DRYINGMAT.getValuesCollection().stream().filter(x -> x.isValidInput(item)).findFirst()
                .orElse(null);
    }

    public int getDuration() {
        return duration;
    }

    /**
     * Only for GUI purposes - not intended as a crafting mechanic
     *
     * @return the output item stack
     */
    @NotNull
    public ItemStack getOutputStack() {
        return outputItem;
    }

    @NotNull
    public IIngredient<ItemStack> getItemIngredient() {
        return inputItem;
    }

    @NotNull
    public ItemStack getOutputItem(ItemStack stack) {
        return CapabilityFood.updateFoodFromPrevious(stack, outputItem.copy());
    }

    @Override
    public NonNullList<IIngredient<ItemStack>> getIngredients() {
        return NonNullList.withSize(1, inputItem);
    }

    @Override
    public NonNullList<ItemStack> getOutputs() {
        return NonNullList.withSize(1, outputItem);
    }

    private boolean isValidInput(ItemStack inputItem) {
        return this.inputItem.testIgnoreCount(inputItem);
    }
}
