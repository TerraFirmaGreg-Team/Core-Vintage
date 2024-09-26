package su.terrafirmagreg.modules.soil.api.spi;

import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.object.block.BlockSoilFarmland;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DIRT;

public interface IDirt {

  static void turnToDirt(World world, BlockPos pos) {
    Block block = world.getBlockState(pos).getBlock();
    if (block instanceof ISoilBlock soilBlockVariant) {
      var soil = soilBlockVariant.getType();

      world.setBlockState(pos, DIRT.get(soil).getDefaultState());
      AxisAlignedBB axisalignedbb = BlockSoilFarmland.FLIPPED_AABB.offset(pos);
      for (Entity entity : world.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
        double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - entity.getEntityBoundingBox().minY);
        entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
      }
    }
  }

}
