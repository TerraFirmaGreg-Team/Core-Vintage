package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.client.render.TESRWoodToolRack;
import su.terrafirmagreg.modules.wood.objects.tiles.TileWoodToolRack;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import gregtech.api.items.toolitem.ToolClasses;

import org.jetbrains.annotations.Nullable;

import static net.minecraft.block.BlockHorizontal.FACING;
import static net.minecraft.util.EnumFacing.Axis;
import static net.minecraft.util.EnumFacing.HORIZONTALS;
import static net.minecraft.util.EnumFacing.NORTH;
import static net.minecraft.util.EnumFacing.byHorizontalIndex;

@SuppressWarnings("deprecation")
public class BlockWoodToolRack extends BlockWood implements IProviderTile {

  protected static final AxisAlignedBB RACK_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D,
      1.0D, 1.0D);
  protected static final AxisAlignedBB RACK_WEST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D,
      1.0D, 1.0D);
  protected static final AxisAlignedBB RACK_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D,
      1.0D, 0.125D);
  protected static final AxisAlignedBB RACK_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D,
      1.0D, 1.0D);

  public BlockWoodToolRack(WoodBlockVariant variant, WoodType type) {
    super(variant, type);

    getSettings()
        .hardness(0.5f)
        .resistance(3f)
        .size(Size.LARGE)
        .weight(Weight.VERY_HEAVY)
        .nonOpaque()
        .nonFullCube();

    setHarvestLevel(ToolClasses.AXE, 0);
    setDefaultState(getBlockState().getBaseState()
        .withProperty(FACING, NORTH));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(FACING, byHorizontalIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(FACING).getHorizontalIndex();
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return switch (state.getValue(FACING)) {
      default -> RACK_NORTH_AABB;
      case SOUTH -> RACK_SOUTH_AABB;
      case WEST -> RACK_WEST_AABB;
      case EAST -> RACK_EAST_AABB;
    };
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn,
      BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (!BlockUtils.canHangAt(worldIn, pos, state.getValue(FACING))) {
      dropBlockAsItem(worldIn, pos, state, 0);
      var tile = TileUtils.getTile(worldIn, pos, TileWoodToolRack.class);
      if (tile != null) {
        tile.onBreakBlock();
      }
      worldIn.setBlockToAir(pos);
    }
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    var tile = TileUtils.getTile(worldIn, pos, TileWoodToolRack.class);
    if (tile != null) {
      tile.onBreakBlock();
    }
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos)
        && BlockUtils.getASolidFacing(worldIn, pos, null, HORIZONTALS) != null;
  }

  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
      EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!world.isRemote) {
      var tile = TileUtils.getTile(world, pos, TileWoodToolRack.class);
      if (tile != null) {
        return tile.onRightClick(playerIn, hand, getSlotFromPos(state, hitX, hitY, hitZ));
      }
    }
    return true;
  }

  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing,
      float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    if (facing.getAxis() == Axis.Y) {
      facing = placer.getHorizontalFacing().getOpposite();
    }
    return this.getDefaultState()
        .withProperty(FACING, BlockUtils.getASolidFacing(worldIn, pos, facing, HORIZONTALS));
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, FACING);
  }

  @Override
  @SuppressWarnings("ConstantConditions")
  public ItemStack getPickBlock(IBlockState state, @Nullable RayTraceResult target, World world,
      BlockPos pos, EntityPlayer player) {
    if (target != null) {
      var vec = target.hitVec.subtract(pos.getX(), pos.getY(), pos.getZ());
      var tile = TileUtils.getTile(world, pos, TileWoodToolRack.class);
      if (tile != null) {
        ItemStack item = tile.getItems()
            .get(getSlotFromPos(state, (float) vec.x, (float) vec.y, (float) vec.z));
        if (!item.isEmpty()) {
          return item;
        }
      }
    }
    return super.getPickBlock(state, target, world, pos, player);
  }

  public int getSlotFromPos(IBlockState state, float x, float y, float z) {
    int slot = 0;
    if ((state.getValue(FACING).getAxis().equals(Axis.Z) ? x : z) > .5f) {
      slot += 1;
    }
    if (y < 0.5f) {
      slot += 2;
    }
    return slot;
  }

  @Override
  public Class<? extends TileEntity> getTileEntityClass() {
    return TileWoodToolRack.class;
  }

  @Override
  public TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRWoodToolRack();
  }

  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileWoodToolRack();
  }
}
