package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.modules.device.objects.tiles.TileInfectedAir;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BlockInfectedAir extends BaseBlock implements IProviderTile {

  public BlockInfectedAir() {
    super(Settings.of(Material.AIR));

    getSettings()
        .registryKey("device/infected_air")
        .hardness(2F)
        .nonOpaque()
        .nonFullCube();
  }

  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn,
      BlockPos pos) {
    return NULL_AABB;
  }

  public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
    return false;
  }

  public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state,
      float chance, int fortune) {
  }

  public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos,
      EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @Override
  public Class<? extends TileEntity> getTileEntityClass() {
    return TileInfectedAir.class;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileInfectedAir();
  }
}
