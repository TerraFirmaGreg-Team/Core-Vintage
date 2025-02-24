package su.terrafirmagreg.modules.core.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlock;
import su.terrafirmagreg.api.util.AABBUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockPuddle extends BaseBlock {

  public BlockPuddle() {
    super(Settings.of(Material.GROUND));

    getSettings()
      .registryKey("puddle")
      .renderLayer(BlockRenderLayer.TRANSLUCENT)
      .useNeighborBrightness()
      .unbreakable()
      .noCollision()
      .nonFullCube()
      .randomTicks()
      .replaceable();
  }

  @Override
  public boolean isTranslucent(IBlockState state) {
    return true;
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return AABBUtils.create(0, 0, 0, 1, 0.00625, 1);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    return blockAccess.getBlockState(pos.offset(side)).getBlock() != this;
  }

  @Override
  public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
    super.updateTick(world, pos, state, rand);
    if (!world.isRaining()) {
      if (rand.nextInt(3) == 0) {
        world.setBlockToAir(pos);
      }
    }
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!world.isSideSolid(pos.down(), EnumFacing.UP)) {
      world.setBlockToAir(pos);
    }
  }

  @Override
  public int quantityDropped(Random random) {
    return 0;
  }

  @Override
  public @Nullable RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
    return null;
  }
}
