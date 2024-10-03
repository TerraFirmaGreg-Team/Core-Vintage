package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

import static su.terrafirmagreg.modules.world.classic.ChunkGenClassic.SALT_WATER;

// todo: either pull some trickery to make this look like water or simply wait until 1.13 and implement ILiquidContainer

public class BlockPlantWater extends BlockPlant {

  private static final Map<PlantType, BlockPlantWater> MAP = new HashMap<>();

  public BlockPlantWater(PlantType plant) {
    super(plant);
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
  }

  public static BlockPlantWater get(PlantType plant) {
    return MAP.get(plant);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    if (plant.getWaterType() == SALT_WATER) {
      return BlockUtils.isSaltWater(worldIn.getBlockState(pos)) && this.canSustainBush(soil);
    }
    return BlockUtils.isFreshWater(worldIn.getBlockState(pos)) && this.canSustainBush(soil);
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    this.onBlockHarvested(world, pos, state, player);
    return world.setBlockState(pos, plant.getWaterType(), world.isRemote ? 11 : 3);
  }

  @Override
  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      this.dropBlockAsItem(worldIn, pos, state, 0);
      worldIn.setBlockState(pos, plant.getWaterType());
    }
  }
}
