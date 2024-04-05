package net.dries007.tfc.objects.blocks;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.items.ItemsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.NotNull;

import java.util.Random;


public class BlockFireClay extends Block implements IItemSize {
	public BlockFireClay() {
		super(Material.CLAY);
		setSoundType(SoundType.GROUND);
		setHardness(1.0F);
	}

	@Override
	public int quantityDropped(Random random) {
		return 4;
	}

	@NotNull
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return ItemsTFC.FIRE_CLAY;
	}


	@Override
	public @NotNull Size getSize(ItemStack stack) {
		return Size.VERY_SMALL;
	}


	@Override
	public @NotNull Weight getWeight(ItemStack stack) {
		return Weight.HEAVY;
	}
}
