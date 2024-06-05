package su.terrafirmagreg.modules.device.objects.recipes.quern;

import net.minecraft.item.ItemStack;


import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;

import lombok.Getter;

@Getter
public class QuernRecipe implements IQuernRecipe {

    protected IIngredient<ItemStack> inputItem;
    protected ItemStack outputItem;

    public QuernRecipe(IIngredient<ItemStack> inputItem, ItemStack outputItem) {
        this.inputItem = inputItem;
        this.outputItem = outputItem;

        if (inputItem == null || outputItem == null) {
            throw new IllegalArgumentException("Input and output are not allowed to be empty");
        }
    }

    public ItemStack getOutputItem(ItemStack stack) {
        return CapabilityFood.updateFoodFromPrevious(stack, outputItem.copy());

    }

    public boolean isValidInput(ItemStack inputItem) {
        return this.inputItem.test(inputItem);
    }
}
