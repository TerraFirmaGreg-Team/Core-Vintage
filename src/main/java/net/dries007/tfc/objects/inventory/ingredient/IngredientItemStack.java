package net.dries007.tfc.objects.inventory.ingredient;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import org.jetbrains.annotations.NotNull;

public class IngredientItemStack implements IIngredient<ItemStack> {
	private final ItemStack inputStack;

	IngredientItemStack(@NotNull ItemStack inputStack) {
		this.inputStack = inputStack;
	}

	@Override
	public NonNullList<ItemStack> getValidIngredients() {
		return NonNullList.withSize(1, inputStack.copy());
	}

	@Override
	public boolean test(ItemStack stack) {
		return testIgnoreCount(stack) && stack.getCount() >= inputStack.getCount();
	}

	@Override
	public boolean testIgnoreCount(ItemStack stack) {
		if (stack != null && (!stack.isEmpty() || inputStack.isEmpty())) {
			if (inputStack.getItem() == stack.getItem()) {
				return inputStack.getMetadata() == OreDictionary.WILDCARD_VALUE || inputStack.getMetadata() == stack.getMetadata();
			}
		}
		return false;
	}

	@Override
	@NotNull
	public ItemStack consume(ItemStack input) {
		input.shrink(inputStack.getCount());
		return input;
	}

	@Override
	public int getAmount() {
		return inputStack.getCount();
	}
}
