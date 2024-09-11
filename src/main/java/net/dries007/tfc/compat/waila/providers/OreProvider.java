package net.dries007.tfc.compat.waila.providers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class OreProvider implements IWailaBlock {

  @NotNull
  @Override
  public ItemStack getIcon(@NotNull World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
    IBlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof BlockOreTFC b) {
      return ItemOreTFC.get(b.ore, 1);
    }
    return ItemStack.EMPTY;
  }

  @NotNull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(BlockOreTFC.class);
  }

  @Override
  public boolean appendBody() {
    return false;
  }

  @Override
  public boolean overrideIcon() {
    return true;
  }
}
