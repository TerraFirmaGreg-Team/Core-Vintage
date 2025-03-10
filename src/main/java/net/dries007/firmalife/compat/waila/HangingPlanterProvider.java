package net.dries007.firmalife.compat.waila;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import net.dries007.firmalife.init.StatePropertiesFL;
import net.dries007.tfc.compat.waila.interfaces.IWailaBlock;
import net.dries007.tfc.objects.blocks.BlockHangingPlanter;
import net.dries007.tfc.objects.te.TEHangingPlanter;
import net.dries007.tfc.util.Helpers;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HangingPlanterProvider implements IWailaBlock {

  @Nonnull
  @Override
  public List<String> getTooltip(World world, @Nonnull BlockPos pos, @Nonnull NBTTagCompound nbt) {
    List<String> currentTooltip = new ArrayList<>();
    IBlockState state = world.getBlockState(pos);
    if (state.getBlock() instanceof BlockHangingPlanter) {
      int maxStage = Collections.max(StatePropertiesFL.STAGE.getAllowedValues());
      int curStage = state.getValue(StatePropertiesFL.STAGE);
      if (maxStage == curStage) {
        currentTooltip.add("Mature");
      } else {
        float curStagePercent = (float) curStage * 100 / maxStage;
        String growth = String.format("%d%%", Math.round(curStagePercent));
        currentTooltip.add(growth);
      }
      TEHangingPlanter te = Helpers.getTE(world, pos, TEHangingPlanter.class);
      if (te != null) {
        currentTooltip.add(te.isClimateValid() ? "Climate Valid" : "Climate Invalid");
      }
    }
    return currentTooltip;
  }

  @Nonnull
  @Override
  public List<Class<?>> getLookupClass() {
    return Collections.singletonList(TEHangingPlanter.class);
  }
}
