package com.eerussianguy.firmalife.compat.waila;

import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.BlockHangingPlanter;
import net.dries007.tfc.objects.te.TEHangingPlanter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.data.Properties.IntProp.STAGE_3;

public class HangingPlanterProvider implements IWailaBlock {

  @NotNull
  @Override
  public List<String> getTooltip(World world, @NotNull BlockPos pos, @NotNull NBTTagCompound nbt) {
    List<String> currentTooltip = new ArrayList<>();
    IBlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof BlockHangingPlanter) {
      int maxStage = Collections.max(STAGE_3.getAllowedValues());
      int curStage = state.getValue(STAGE_3);
      if (maxStage == curStage) {
        currentTooltip.add("Mature");
      } else {
        float curStagePercent = (float) curStage * 100 / maxStage;
        String growth = String.format("%d%%", Math.round(curStagePercent));
        currentTooltip.add(growth);
      }
      TileUtils.getTile(world, pos, TEHangingPlanter.class).ifPresent(tile -> {
        currentTooltip.add(tile.isClimateValid() ? "Climate Valid" : "Climate Invalid");
      });
    }
    return currentTooltip;
  }

  @NotNull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(TEHangingPlanter.class);
  }
}
