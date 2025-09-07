package su.terrafirmagreg.modules.soil.feature.soiltype.types;

import su.terrafirmagreg.modules.soil.content.block.BlockSoilFarmland;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IDirtBlock {

  static void turnToDirt(World world, BlockPos pos) {
    Block block = world.getBlockState(pos).getBlock();
    if (block instanceof ISoilBlock soil) {
      world.setBlockState(pos, soil.getDirt());
      AxisAlignedBB axisalignedbb = BlockSoilFarmland.FLIPPED_AABB.offset(pos);
      for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
        double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
        entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
      }


    }
  }

  IBlockState getGrass();


}
