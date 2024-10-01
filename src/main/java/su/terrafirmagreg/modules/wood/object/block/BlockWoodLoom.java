package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockContainer;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.client.render.TESRWoodLoom;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodLoom;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static su.terrafirmagreg.data.Properties.HORIZONTAL;

@Getter
@SuppressWarnings("deprecation")
public class BlockWoodLoom extends BaseBlockContainer implements IWoodBlock {

  protected static final AxisAlignedBB LOOM_EAST_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0625D,
                                                                          0.5625D, 1.0D, 0.9375D);
  protected static final AxisAlignedBB LOOM_WEST_AABB = new AxisAlignedBB(0.4375D, 0.0D, 0.0625D,
                                                                          0.875D, 1.0D, 0.9375D);
  protected static final AxisAlignedBB LOOM_SOUTH_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.125D,
                                                                           0.9375D, 1.0D, 0.5625D);
  protected static final AxisAlignedBB LOOM_NORTH_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.4375D,
                                                                           0.9375D, 1.0D, 0.875D);

  protected final WoodBlockVariant variant;
  protected final WoodType type;

  public BlockWoodLoom(WoodBlockVariant variant, WoodType type) {
    super(Settings.of(Material.WOOD, MapColor.AIR));

    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .customResource(variant.getCustomResource())
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .nonOpaque()
      .nonFullCube()
      .hardness(0.5f)
      .resistance(3f)
      .weight(Weight.VERY_HEAVY)
      .size(Size.LARGE)
      .oreDict(variant)
      .oreDict(variant, type);

    setDefaultState(blockState.getBaseState()
                              .withProperty(HORIZONTAL, EnumFacing.NORTH));

    BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.byHorizontalIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return switch (state.getValue(BlockHorizontal.FACING)) {
      case SOUTH -> LOOM_SOUTH_AABB;
      case WEST -> LOOM_WEST_AABB;
      case EAST -> LOOM_EAST_AABB;
      default -> LOOM_NORTH_AABB;
    };
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                  EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                  float hitX, float hitY, float hitZ) {
    var tile = TileUtils.getTile(worldIn, pos, TileWoodLoom.class);
    return tile.map(tileWoodLoom -> tileWoodLoom.onRightClick(playerIn)).orElse(true);
  }

  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    if (facing.getAxis() == EnumFacing.Axis.Y) {
      facing = placer.getHorizontalFacing().getOpposite();
    }
    return getDefaultState().withProperty(HORIZONTAL, facing);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL);
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    var tile = TileUtils.getTile(worldIn, pos, TileWoodLoom.class);
    tile.ifPresent(tileWoodLoom -> tileWoodLoom.onBreakBlock(worldIn, pos, state));
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileWoodLoom();
  }

  @Override
  public Class<TileWoodLoom> getTileClass() {
    return TileWoodLoom.class;
  }

  @Override
  public TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRWoodLoom();
  }
}
