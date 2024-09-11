package BananaFructa.tfcpassingdays.fixes;

import su.terrafirmagreg.data.MathConstants;

import net.minecraft.block.BlockDaylightDetector;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;


import BananaFructa.tfcpassingdays.PassingDayWorldProviderServer;

public class BlockDaylightDetectorFixed extends BlockDaylightDetector {

  boolean inverted;

  public BlockDaylightDetectorFixed(boolean inverted) {
    super(inverted);
    this.inverted = inverted;
  }

  public void updatePower(World worldIn, BlockPos pos) {
    if (worldIn.provider.hasSkyLight()) {
      boolean modifiedProvider = worldIn.provider instanceof PassingDayWorldProviderServer;
      IBlockState iblockstate = worldIn.getBlockState(pos);
      int i = worldIn.getLightFor(EnumSkyBlock.SKY, pos) -
              (modifiedProvider ? ((PassingDayWorldProviderServer) worldIn.provider).getSubtractedSkylight(pos.getZ()) :
                      worldIn.getSkylightSubtracted());
      float f = (modifiedProvider ? ((PassingDayWorldProviderServer) worldIn.provider).getCelestialAngleRadians(1, pos.getZ()) :
              worldIn.getCelestialAngleRadians(1.0F));

      if (this.inverted) {
        i = 15 - i;
      }

      if (i > 0 && !this.inverted) {
        float f1 = f < MathConstants.PI ? 0.0F : ((float) Math.PI * 2F);
        f = f + (f1 - f) * 0.2F;
        i = Math.round((float) i * MathHelper.cos(f));
      }

      i = MathHelper.clamp(i, 0, 15);

      if (iblockstate.getValue(POWER) != i) {
        worldIn.setBlockState(pos, iblockstate.withProperty(POWER, i), 3);
      }
    }
  }
}
