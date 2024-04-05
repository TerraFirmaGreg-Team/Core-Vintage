package se.gory_moon.horsepower.recipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import org.jetbrains.annotations.NotNull;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.blocks.BlockHPChoppingBase;

import java.util.List;

import static se.gory_moon.horsepower.blocks.BlockHPChoppingBase.createItemStack;
import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

public class ShapelessChoppingRecipe extends ShapelessOreRecipe {
	public final List<ItemStack> outputBlocks;

	public ShapelessChoppingRecipe(ResourceLocation location, List<ItemStack> variantItems, ItemStack result, Object... recipe) {
		super(location, result, recipe);
		this.outputBlocks = variantItems;
		for (ItemStack stack : outputBlocks) {
			if (Block.getBlockFromItem(stack.getItem()) instanceof BlockHPChoppingBase) {
				for (int i = 0; i < input.size(); i++) {
					Ingredient ingredient = input.get(i);
					if (ingredient.apply(stack)) {
						NonNullList<ItemStack> stacks = NonNullList.create();
						Block.getBlockFromItem(stack.getItem()).getSubBlocks(null, stacks);
						input.set(i, Ingredient.fromStacks(stacks.toArray(new ItemStack[stacks.size()])));
					}
				}
			}
		}
	}

	@NotNull
	@Override
	public ItemStack getRecipeOutput() {
		if (!outputBlocks.isEmpty() && !output.isEmpty()) {
			ItemStack stack = outputBlocks.get(0).copy();
			BlockHPChoppingBase block = (BlockHPChoppingBase) Block.getBlockFromItem(output.getItem());
			int meta = stack.getMetadata();
			if (meta == OreDictionary.WILDCARD_VALUE)
				stack.setItemDamage(0);
			return createItemStack(block, getSimpleRecipeOutput().getCount(), stack);

		}
		return super.getRecipeOutput();
	}

	@NotNull
	@Override
	public ItemStack getCraftingResult(@NotNull InventoryCrafting craftMatrix) {
        /*boolean isTypeChopping = false;
        for (ItemStack outputBlock : outputBlocks)
        {
            if (((ItemBlock) outputBlock.getItem()).getBlock() instanceof BlockHPChoppingBase)
                isTypeChopping = true;
        }*/
		for (int i = 0; i < craftMatrix.getSizeInventory(); i++) {
			for (ItemStack ore : outputBlocks) {
				ItemStack stack = craftMatrix.getStackInSlot(i);
				if ((OreDictionary.itemMatches(ore, stack, false) || (/*isTypeChopping && */(Block.getBlockFromItem(stack.getItem())) instanceof BlockHPChoppingBase)) && Block.getBlockFromItem(stack.getItem()) != Blocks.AIR) {
					BlockHPChoppingBase block = (BlockHPChoppingBase) Block.getBlockFromItem(getSimpleRecipeOutput().getItem());
					if (!Configs.general.useDynamicCrafting) {
						if (MODID_TFC.equals(ore.getItem().getRegistryName().getNamespace()))
							return createItemStack(block, getSimpleRecipeOutput().getCount(), stack);
						else
							return createItemStack(block, getSimpleRecipeOutput().getCount(), new ItemStack(Blocks.LOG, 1, 0));
					}
					return createItemStack(block, getSimpleRecipeOutput().getCount(), stack);
				}
			}
		}
		return super.getCraftingResult(craftMatrix);
	}

	public ItemStack getSimpleRecipeOutput() {
		return output;
	}
}
