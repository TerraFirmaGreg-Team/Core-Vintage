package su.terrafirmagreg.framework.manager.content.base.block.spi;

import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * A modern slab block implementation that uses block states instead of separate blocks. Supports bottom, top, and double slab variants in a single block.
 */
public class ModernSlabBlock extends BaseBlock {

  public static final PropertyEnum<SlabType> TYPE = PropertyEnum.create("type", SlabType.class);

  public ModernSlabBlock(BlockSettings settings) {
    super(settings);
    this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, SlabType.BOTTOM));
    this.useNeighborBrightness = true;
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    if (meta == SlabType.DOUBLE.ordinal()) {
      return getDefaultState().withProperty(TYPE, SlabType.DOUBLE);
    }
    return getDefaultState().withProperty(TYPE,
      (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5D)) ?
      SlabType.BOTTOM : SlabType.TOP);
  }

  @Override
  public int damageDropped(IBlockState state) {
    return state.getValue(TYPE) == SlabType.DOUBLE ? 0 : getMetaFromState(state);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, TYPE);
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(TYPE, SlabType.byMetadata(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(TYPE).getMetadata();
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return state.getValue(TYPE) == SlabType.DOUBLE;
  }

  @Override
  public boolean isFullCube(IBlockState state) {
    return state.getValue(TYPE) == SlabType.DOUBLE;
  }

  @Override
  public boolean isTopSolid(IBlockState state) {
    return state.getValue(TYPE) != SlabType.BOTTOM;
  }

  @Override
  public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
    if (state.getValue(TYPE) == SlabType.DOUBLE) {
      return true;
    }
    if (face == EnumFacing.UP && state.getValue(TYPE) == SlabType.TOP) {
      return true;
    }
    return face == EnumFacing.DOWN && state.getValue(TYPE) == SlabType.BOTTOM;
  }

  public enum SlabType implements IStringSerializable {
    TOP("top"),
    BOTTOM("bottom"),
    DOUBLE("double");

    private final String name;

    SlabType(String name) {
      this.name = name;
    }

    @Override
    public String getName() {
      return name;
    }

    public int getMetadata() {
      return this.ordinal();
    }

    public static SlabType byMetadata(int meta) {
      return values()[meta % values().length];
    }

    @Override
    public String toString() {
      return name;
    }
  }

}
