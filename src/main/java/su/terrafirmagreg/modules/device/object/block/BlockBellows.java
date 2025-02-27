package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlockContainer;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.device.client.render.TESRBellows;
import su.terrafirmagreg.modules.device.object.tile.TileBellows;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

@SuppressWarnings("deprecation")
public class BlockBellows extends BaseBlockContainer {

  public BlockBellows() {
    super(Settings.of(Material.CIRCUITS, MapColor.GRAY));

    getSettings()
      .registryKey("bellows")
      .sound(SoundType.WOOD)
      .harvestLevel(ToolClasses.AXE, 0)
      .nonFullCube()
      .nonOpaque()
      .hardness(2.0F)
      .resistance(2.0F);

    setDefaultState(getBlockState().getBaseState().withProperty(HORIZONTAL, EnumFacing.NORTH));
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex();
  }

  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    return TileUtils.getTile(world, pos, TileBellows.class).map(TileBellows::onRightClick).orElse(true);
  }

  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    if (facing.getAxis() == EnumFacing.Axis.Y) {
      if (placer.isSneaking()) {
        facing = placer.getHorizontalFacing().getOpposite();
      } else {
        facing = placer.getHorizontalFacing();
      }
    }
    return getDefaultState().withProperty(HORIZONTAL, facing);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL);
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return face == state.getValue(HORIZONTAL) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
  }

  @Override
  public Class<TileBellows> getTileClass() {
    return TileBellows.class;
  }

  @Override
  public TESRBellows getTileRenderer() {
    return new TESRBellows();
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileBellows();
  }
}
