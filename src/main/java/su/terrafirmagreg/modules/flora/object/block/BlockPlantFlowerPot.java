package su.terrafirmagreg.modules.flora.object.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.modules.flora.api.types.type.FloraType;
import su.terrafirmagreg.modules.flora.api.types.variant.block.FloraBlockVariant;
import su.terrafirmagreg.modules.flora.api.types.variant.block.IFloraBlock;
import su.terrafirmagreg.modules.flora.init.BlocksFlora;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import lombok.Getter;

@SuppressWarnings("deprecation")
@Getter
public class BlockPlantFlowerPot extends BaseBlock implements IFloraBlock {

  protected static final AxisAlignedBB FLOWER_POT_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);

  protected final FloraType type;
  protected final FloraBlockVariant variant;

  public BlockPlantFlowerPot(FloraBlockVariant variant, FloraType type) {
    super(Settings.of(Material.CIRCUITS));

    this.type = type;
    this.variant = variant;

    getSettings()
      .registryKey(variant.getRegistryKey(type))
      .renderLayer(BlockRenderLayer.CUTOUT)
      .nonFullCube()
      .nonOpaque();


  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return FLOWER_POT_AABB;
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return face == EnumFacing.DOWN ? super.getBlockFaceShape(worldIn, state, pos, face) : BlockFaceShape.UNDEFINED;
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (!worldIn.isSideSolid(pos.down(), EnumFacing.UP)) {
      worldIn.setBlockToAir(pos);
    }
  }


  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState downState = worldIn.getBlockState(pos.down());
    return super.canPlaceBlockAt(worldIn, pos) &&
           (downState.isTopSolid() ||
            downState.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID);
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    drops.clear();
    drops.add(new ItemStack(BlocksFlora.PLANT.get(type)));
    drops.add(new ItemStack(Items.FLOWER_POT));
  }
}
