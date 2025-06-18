package su.terrafirmagreg.modules.wood.object.itemblock;


import su.terrafirmagreg.framework.manager.registry.base.item.spi.BaseItemBlock;
import su.terrafirmagreg.modules.wood.object.block.BlockWoodSapling;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ItemBlockWoodSapling extends BaseItemBlock {

  private final BlockWoodSapling block;

  public ItemBlockWoodSapling(BlockWoodSapling block) {
    super(block);

    this.block = block;
  }

  @Override
  public boolean onEntityItemUpdate(EntityItem entityItem) {
    if (!entityItem.world.isRemote && entityItem.age >= entityItem.lifespan && !entityItem.getItem().isEmpty()) {
      final BlockPos pos = entityItem.getPosition();
      if (placeAndDecreaseCount(entityItem, pos)) {
        entityItem.setDead();
        return true;
      }
      for (EnumFacing face : EnumFacing.HORIZONTALS) {
        final BlockPos offsetPos = pos.offset(face);
        if (placeAndDecreaseCount(entityItem, offsetPos)) {
          entityItem.setDead();
          return true;
        }
      }
    }
    return false;
  }

  private boolean placeAndDecreaseCount(EntityItem entityItem, BlockPos pos) {
    if (entityItem.world.mayPlace(block, pos, false, EnumFacing.UP, null) && entityItem.world.setBlockState(pos, block.getDefaultState())) {
      entityItem.getItem().shrink(1);
    }
    return entityItem.getItem().isEmpty();
  }

}
