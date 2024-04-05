package net.dries007.tfc.objects.blocks;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;


public class BlockFireBrick extends Block implements IItemSize {
	public BlockFireBrick() {
		super(Material.ROCK);
		setSoundType(SoundType.STONE);
		setHardness(1.0F);
	}


	@Override
	public @NotNull Size getSize(ItemStack stack) {
		return Size.SMALL; // Stored everywhere
	}


	@Override
	public @NotNull Weight getWeight(ItemStack stack) {
		return Weight.LIGHT; // Stacksize = 32
	}
}
