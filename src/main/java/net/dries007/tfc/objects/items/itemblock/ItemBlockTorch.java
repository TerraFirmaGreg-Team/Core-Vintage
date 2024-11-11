package net.dries007.tfc.objects.items.itemblock;

import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.object.tile.TileLogPile;
import su.terrafirmagreg.modules.device.object.tile.TilePitKiln;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;

public class ItemBlockTorch extends ItemBlockTFC {

  public ItemBlockTorch(Block block) {
    super(block);
  }

  @Override
  public boolean onEntityItemUpdate(EntityItem entityItem) {
    BlockPos pos = entityItem.getPosition().down();
    World world = entityItem.getEntityWorld();
    IBlockState state = entityItem.getEntityWorld().getBlockState(pos);

    if (state.getBlock() == BlocksDevice.LOG_PILE || state.getBlock() == BlocksDevice.PIT_KILN) {
      int count = entityItem.getEntityData().getInteger("torchCount");
      if (count > 160) {
        if (state.getBlock() == BlocksDevice.LOG_PILE) {
          world.setBlockState(pos, state.withProperty(LIT, true));
          TileUtils.getTile(world, pos, TileLogPile.class).ifPresent(TileLogPile::light);
          if (Blocks.FIRE.canPlaceBlockAt(world, pos.up())) {
            world.setBlockState(pos.up(), Blocks.FIRE.getDefaultState());
          }
        } else if (state.getBlock() == BlocksDevice.PIT_KILN) {
          TileUtils.getTile(world, pos, TilePitKiln.class).ifPresent(TilePitKiln::tryLight);
        }
        entityItem.setDead();
      } else {
        if (Math.random() <= 0.1) {
          world.spawnParticle(EnumParticleTypes.LAVA, entityItem.posX, entityItem.posY, entityItem.posZ,
                              -0.5F + Math.random(), -0.5F + Math.random(), -0.5F + Math.random());
        }
        entityItem.getEntityData().setInteger("torchCount", count + 1);
      }
    }
    return super.onEntityItemUpdate(entityItem);
  }
}
