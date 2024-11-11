package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockContainer;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.object.tile.TileFreezeDryer;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

@SuppressWarnings("deprecation")
public class BlockFreezeDryer extends BaseBlockContainer {

  public BlockFreezeDryer() {
    super(Settings.of(Material.WOOD));

    getSettings()
      .registryKey("device/freeze_dryer")
      .nonCube()
      .hardness(2F);
    setDefaultState(blockState.getBaseState()
                              .withProperty(HORIZONTAL, EnumFacing.NORTH));
  }

  public EnumFacing getFacing(IBlockState state) {
    return state.getValue(HORIZONTAL);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    EnumFacing enumfacing = EnumFacing.byHorizontalIndex(meta);
    if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
      enumfacing = EnumFacing.NORTH;
    }
    return this.getDefaultState().withProperty(HORIZONTAL, enumfacing);
  }

  /**
   * Convert the BlockState into the correct metadata value
   */
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getIndex();
  }

  @Override
  public IBlockState withRotation(IBlockState state, Rotation rot) {
    return state.withProperty(HORIZONTAL, rot.rotate(state.getValue(HORIZONTAL)));
  }

  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    this.setDefaultFacing(worldIn, pos, state);
  }

  private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state) {
    if (!worldIn.isRemote) {
      IBlockState iblockstate = worldIn.getBlockState(pos.north());
      IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
      IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
      IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
      EnumFacing enumfacing = state.getValue(HORIZONTAL);

      if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock()
          && !iblockstate1.isFullBlock()) {
        enumfacing = EnumFacing.SOUTH;
      } else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock()
                 && !iblockstate.isFullBlock()) {
        enumfacing = EnumFacing.NORTH;
      } else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock()
                 && !iblockstate3.isFullBlock()) {
        enumfacing = EnumFacing.EAST;
      } else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock()
                 && !iblockstate2.isFullBlock()) {
        enumfacing = EnumFacing.WEST;
      }

      worldIn.setBlockState(pos, state.withProperty(HORIZONTAL, enumfacing), 2);
    }
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                  EntityPlayer player, EnumHand hand, EnumFacing playerFacing, float hitX, float hitY,
                                  float hitZ) {
    if (!worldIn.isRemote) {
      GuiHandler.openGui(worldIn, pos, player);
    }
    return true;
  }

  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing,
                                          float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return this.getDefaultState()
               .withProperty(HORIZONTAL, placer.getHorizontalFacing().getOpposite());
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state,
                              EntityLivingBase placer, ItemStack stack) {
    if (stack.hasDisplayName()) {
      var tile = TileUtils.getTile(worldIn, pos, TileFreezeDryer.class);
      //tile.setCustomName(stack.getDisplayName());
    }

  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL);
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World world, int i) {
    return new TileFreezeDryer();
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TileFreezeDryer.class).ifPresent(tile -> tile.onBreakBlock(worldIn, pos, state));
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public Class<TileFreezeDryer> getTileClass() {
    return TileFreezeDryer.class;
  }

}
