package su.terrafirmagreg.modules.wood.object.block;


import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockContainer;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.render.TESRWoodLoom;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodLoom;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
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

import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

@Getter
@SuppressWarnings("deprecation")
public class BlockWoodLoom extends BaseBlockContainer implements IWoodEntry, IProviderBlockColor {

  protected static final AxisAlignedBB LOOM_EAST_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.0625D, 0.5625D, 1.0D, 0.9375D);
  protected static final AxisAlignedBB LOOM_WEST_AABB = new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.875D, 1.0D, 0.9375D);
  protected static final AxisAlignedBB LOOM_SOUTH_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.125D, 0.9375D, 1.0D, 0.5625D);
  protected static final AxisAlignedBB LOOM_NORTH_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.4375D, 0.9375D, 1.0D, 0.875D);

  protected final WoodType type;

  public BlockWoodLoom(WoodType type) {
    super(Settings.of(Material.WOOD, MapColor.AIR));

    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey("loom"))
      .customResource(type.getResource("loom"))
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .tile(TileWoodLoom.class, new TESRWoodLoom())
      .renderType(EnumBlockRenderType.MODEL)
      .nonOpaque()
      .nonFullCube()
      .hardness(0.5f)
      .resistance(3f)
      .fireInfo(5, 20)
      .capability(CapabilityProviderSize.of(Size.LARGE, Weight.VERY_HEAVY))
      .addOreDict("loom");

    setDefaultState(blockState.getBaseState()
      .withProperty(HORIZONTAL, EnumFacing.NORTH));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex();
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return switch (state.getValue(HORIZONTAL)) {
      case SOUTH -> LOOM_SOUTH_AABB;
      case WEST -> LOOM_WEST_AABB;
      case EAST -> LOOM_EAST_AABB;
      default -> LOOM_NORTH_AABB;
    };
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    return TileUtils.getTile(worldIn, pos, TileWoodLoom.class).map(tile -> tile.onRightClick(playerIn)).orElse(true);
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
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileWoodLoom();
  }

}
