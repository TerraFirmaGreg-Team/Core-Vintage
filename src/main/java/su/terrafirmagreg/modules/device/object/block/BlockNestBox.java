package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockContainer;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.object.tile.TileNestBox;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class BlockNestBox extends BaseBlockContainer {

  private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D,
                                                                      0.25D, 0.875D);

  public BlockNestBox() {
    super(Settings.of(Material.GRASS));

    getSettings()
      .registryKey("device/nest_box")
      .nonCube()
      .hardness(0.5F);

    BlockUtils.setFireInfo(this, 60, 20);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return BOUNDING_BOX;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess world, BlockPos pos,
                                      EnumFacing side) {
    return true;
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn,
                              BlockPos fromPos) {
    if (!canStay(world, pos)) {
      world.destroyBlock(pos, true);
    }
  }

  @Override
  public boolean canPlaceBlockAt(World world, BlockPos pos) {
    return canStay(world, pos);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                  EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    var tile = TileUtils.getTile(world, pos, TileNestBox.class);
    if (tile != null && !world.isRemote) {
      GuiHandler.openGui(world, pos, player);
    }
    return true;
  }

  private boolean canStay(IBlockAccess world, BlockPos pos) {
    return world.getBlockState(pos.down())
                .getBlockFaceShape(world, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID;
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    var tile = TileUtils.getTile(worldIn, pos, TileNestBox.class);
    if (tile != null) {
      tile.onBreakBlock(worldIn, pos, state);
    }
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState,
                                                         IBlockAccess worldIn, BlockPos pos) {
    return BOUNDING_BOX;
  }

  @Override
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileNestBox();
  }

  @Override
  public Class<TileNestBox> getTileClass() {
    return TileNestBox.class;
  }
}
