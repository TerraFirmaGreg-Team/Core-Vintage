package com.eerussianguy.firmalife.compat.waila;

import su.terrafirmagreg.data.Properties;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;


import com.eerussianguy.firmalife.init.AgingFL;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.BlockCheesewheel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CheesewheelProvider implements IWailaBlock {

  @NotNull
  @Override
  public List<String> getTooltip(World world, @NotNull BlockPos pos, @NotNull NBTTagCompound NBT) {
    List<String> currentTooltip = new ArrayList<>();
    IBlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof BlockCheesewheel) {
      AgingFL age = state.getValue(Properties.AGE);
      currentTooltip.add(age.getFormat() + new TextComponentTranslation(age.getTranslationKey()).getFormattedText());
    }

    return currentTooltip;
  }

  @NotNull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(BlockCheesewheel.class);
  }
}
