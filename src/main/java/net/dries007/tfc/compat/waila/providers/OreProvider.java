package net.dries007.tfc.compat.waila.providers;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;
import net.dries007.tfc.objects.items.metal.ItemOreTFC;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class OreProvider implements IWailaBlock {

  @Nonnull
  @Override
  public ItemStack getIcon(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull NBTTagCompound nbt) {
    IBlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof BlockOreTFC b) {
      return ItemOreTFC.get(b.ore, 1);
    }
    return ItemStack.EMPTY;
  }

  @Nonnull
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
