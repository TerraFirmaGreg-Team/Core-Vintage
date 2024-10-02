package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.client.GuiHandler;
import su.terrafirmagreg.modules.device.object.tile.TileInductionCrucible;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import static su.terrafirmagreg.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.data.Properties.DirectionProp.HORIZONTAL;

@SuppressWarnings("deprecation")
public class BlockInductionCrucible extends BaseBlock implements IProviderTile {

  private static final AxisAlignedBB CRUCIBLE_AABB = new AxisAlignedBB(0.0625, 0, 0.0625, 0.9375,
                                                                       0.9375, 0.9375);
  private static final AxisAlignedBB AABB_LEGS = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.9375D,
                                                                   0.125D, 0.9375D);
  private static final AxisAlignedBB AABB_WALL_NORTH = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D,
                                                                         0.9375D, 0.9375D, 0.1875D);
  private static final AxisAlignedBB AABB_WALL_SOUTH = new AxisAlignedBB(0.0625D, 0.0D, 0.8125D,
                                                                         0.9375D, 0.9375D, 0.9375D);
  private static final AxisAlignedBB AABB_WALL_EAST = new AxisAlignedBB(0.8125D, 0.0D, 0.0625D,
                                                                        0.9375D, 0.9375D, 0.9375D);
  private static final AxisAlignedBB AABB_WALL_WEST = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D,
                                                                        0.1875D, 0.9375D, 0.9375D);

  public BlockInductionCrucible() {
    super(Settings.of(Material.IRON));

    getSettings()
      .registryKey("device/induction_crucible")
      .sound(SoundType.METAL)
      .size(Size.LARGE)
      .weight(Weight.MEDIUM)
      .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
      .nonFullCube()
      .nonCanStack()
      .nonOpaque()
      .hardness(3.0f);

    setHarvestLevel(ToolClasses.PICKAXE, 0);
    setDefaultState(blockState.getBaseState()
                              .withProperty(LIT, false)
                              .withProperty(HORIZONTAL, EnumFacing.NORTH));

  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState()
               .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4))
               .withProperty(LIT, meta >= 4);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(LIT) ? 4 : 0);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return CRUCIBLE_AABB;
  }

  @Override
  public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos,
                                    AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes,
                                    @Nullable Entity entityIn,
                                    boolean isActualState) {
    addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_LEGS);
    addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_WEST);
    addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_NORTH);
    addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_EAST);
    addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_WALL_SOUTH);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
    return CRUCIBLE_AABB;
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
                                  EnumHand hand, EnumFacing facing, float hitX,
                                  float hitY, float hitZ) {
    if (!player.isSneaking()) {
      if (!world.isRemote) {
        GuiHandler.openGui(world, pos, player);
      }
      return true;
    }
    return false;
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL, LIT);
  }

  @Override
  public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos,
                             EnumFacing side) {
    return side == baseState.getValue(HORIZONTAL);
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX,
                                          float hitY, float hitZ, int meta,
                                          EntityLivingBase placer, EnumHand hand) {
    return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing().getOpposite());
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos,
                                          EnumFacing face) {
    if (face == EnumFacing.UP) {
      return BlockFaceShape.BOWL;
    }
    return isSideSolid(state, worldIn, pos, face) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
  }

  @Nullable
  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn,
                                               BlockPos pos) {
    return CRUCIBLE_AABB;
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileInductionCrucible();
  }

  @Override
  public Class<TileInductionCrucible> getTileClass() {
    return TileInductionCrucible.class;
  }
}
