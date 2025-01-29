package com.eerussianguy.firmalife.items;

import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;

import com.eerussianguy.firmalife.blocks.BlockFruitDoor;
import mcp.MethodsReturnNonnullByDefault;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;


@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemFruitDoor extends ItemDoor implements ICapabilitySize {

  public ItemFruitDoor(BlockFruitDoor block) {
    super(block);
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {return Size.VERY_LARGE;}

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {return Weight.HEAVY;}

  @Override
  public int getItemStackLimit(ItemStack stack) {return getStackSize(stack);}
}
