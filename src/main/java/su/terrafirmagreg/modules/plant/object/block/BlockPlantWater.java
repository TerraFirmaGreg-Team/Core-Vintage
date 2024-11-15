package su.terrafirmagreg.modules.plant.object.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.api.types.variant.block.PlantBlockVariant;

import static su.terrafirmagreg.modules.world.classic.ChunkGenClassic.SALT_WATER;

// todo: either pull some trickery to make this look like water or simply wait until 1.13 and implement ILiquidContainer

public class BlockPlantWater extends BlockPlant {

  public BlockPlantWater(PlantBlockVariant variant, PlantType type) {
    super(variant, type);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    if (type.getWaterType() == SALT_WATER) {
      return BlockHelper.isSaltWater(worldIn.getBlockState(pos)) && this.canSustainBush(soil);
    }
    return BlockHelper.isFreshWater(worldIn.getBlockState(pos)) && this.canSustainBush(soil);
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    this.onBlockHarvested(world, pos, state, player);
    return world.setBlockState(pos, type.getWaterType(), world.isRemote ? 11 : 3);
  }

  @Override
  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      this.dropBlockAsItem(worldIn, pos, state, 0);
      worldIn.setBlockState(pos, type.getWaterType());
    }
  }
}
