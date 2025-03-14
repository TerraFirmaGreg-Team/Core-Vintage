package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlock;
import su.terrafirmagreg.framework.registry.api.provider.IProviderTile;
import su.terrafirmagreg.modules.device.object.tile.TileInfectedAir;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

public class BlockInfectedAir extends BaseBlock implements IProviderTile {

  public BlockInfectedAir() {
    super(Settings.of(Material.AIR));

    getSettings()
      .registryKey("infected_air")
      .hardness(2F)
      .noCollision()
      .replaceable()
      .nonFullCube();
  }

  public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
    return false;
  }

  public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
  }

  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }

  @Override
  public Class<TileInfectedAir> getTileClass() {
    return TileInfectedAir.class;
  }

  @Nullable
  @Override
  public TileInfectedAir createNewTileEntity(World worldIn, int meta) {
    return new TileInfectedAir();
  }
}
