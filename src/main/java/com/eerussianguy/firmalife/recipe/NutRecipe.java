package com.eerussianguy.firmalife.recipe;

import com.eerussianguy.firmalife.init.RegistriesFL;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.Nullable;

public class NutRecipe extends IForgeRegistryEntry.Impl<NutRecipe> {
	protected Block inputLog;
	protected Block inputLeaves;
	protected ItemStack outputItem;

	public NutRecipe(Block inputLog, Block inputLeaves, ItemStack outputItem) {
		this.inputLog = inputLog;
		this.inputLeaves = inputLeaves;
		this.outputItem = outputItem;

		if (inputLog == null || inputLeaves == null || outputItem == null) {
			throw new IllegalArgumentException("Sorry, something was null in your nut tree registry.");
		}
	}

	@Nullable
	public static NutRecipe get(Block block) {
		return RegistriesFL.NUT_TREES.getValuesCollection()
		                             .stream()
		                             .filter(x -> x.isValidInput(block))
		                             .findFirst()
		                             .orElse(null);
	}

	@NotNull
	public Block getLeaves() {
		return inputLeaves;
	}

	@NotNull
	public ItemStack getNut() {
		return outputItem;
	}

	private boolean isValidInput(Block input) {
		return this.inputLog == input;
	}
}
