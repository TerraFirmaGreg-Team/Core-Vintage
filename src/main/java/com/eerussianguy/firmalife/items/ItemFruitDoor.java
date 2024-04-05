package com.eerussianguy.firmalife.items;

import com.eerussianguy.firmalife.blocks.BlockFruitDoor;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;


@MethodsReturnNonnullByDefault
public class ItemFruitDoor extends ItemDoor implements IItemSize {
	public ItemFruitDoor(BlockFruitDoor block) {
		super(block);
	}


	@Override
	public @NotNull Size getSize(ItemStack stack) {return Size.VERY_LARGE;}


	@Override
	public @NotNull Weight getWeight(ItemStack stack) {return Weight.HEAVY;}

	@Override
	public int getItemStackLimit(ItemStack stack) {return getStackSize(stack);}
}
