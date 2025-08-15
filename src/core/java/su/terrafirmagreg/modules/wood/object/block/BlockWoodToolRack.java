package su.terrafirmagreg.modules.wood.object.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderTile;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.IWoodEntry;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.render.TESRWoodToolRack;
import su.terrafirmagreg.modules.wood.object.tile.TileWoodToolRack;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import static net.minecraft.util.EnumFacing.Axis;
import static net.minecraft.util.EnumFacing.HORIZONTALS;
import static net.minecraft.util.EnumFacing.NORTH;
import static net.minecraft.util.EnumFacing.byHorizontalIndex;
import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

@SuppressWarnings("deprecation")
@Getter
public class BlockWoodToolRack extends BaseBlock implements IWoodEntry, IProviderTile {

  protected static final AxisAlignedBB RACK_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 1.0D, 1.0D);
  protected static final AxisAlignedBB RACK_WEST_AABB = new AxisAlignedBB(0.875D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
  protected static final AxisAlignedBB RACK_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.125D);
  protected static final AxisAlignedBB RACK_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.875D, 1.0D, 1.0D, 1.0D);

  protected final WoodType type;

  public BlockWoodToolRack(WoodType type) {
    super(BlockSettings.of()
      .material(Material.WOOD)
      .registryKey(type.getRegistryKey("tool_rack"))
      .customResource(type.getResource("tool_rack"))
      .harvestLevel(ToolClasses.AXE, 0)
      .sound(SoundType.WOOD)
      .addOreDict("tool_rack")
      .hardness(0.5f)
      .resistance(3f)
      .capability(CapabilityProviderSize.of(Size.LARGE, Weight.VERY_HEAVY))
      .renderType(EnumBlockRenderType.MODEL)
      .tile(TileWoodToolRack.class, new TESRWoodToolRack())
      .nonOpaque()
      .nonFullCube()
    );

    this.type = type;
    setDefaultState(getBlockState().getBaseState()
      .withProperty(HORIZONTAL, NORTH));
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(HORIZONTAL, byHorizontalIndex(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex();
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return switch (state.getValue(HORIZONTAL)) {
      case SOUTH -> RACK_SOUTH_AABB;
      case WEST -> RACK_WEST_AABB;
      case EAST -> RACK_EAST_AABB;
      default -> RACK_NORTH_AABB;
    };
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    if (!BlockUtils.canHangAt(worldIn, pos, state.getValue(HORIZONTAL))) {
      dropBlockAsItem(worldIn, pos, state, 0);
      TileUtils.getTile(worldIn, pos, TileWoodToolRack.class).ifPresent(TileWoodToolRack::onBreakBlock);
      worldIn.setBlockToAir(pos);
    }
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TileWoodToolRack.class).ifPresent(TileWoodToolRack::onBreakBlock);
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos) && BlockUtils.getASolidFacing(worldIn, pos, null, HORIZONTALS) != null;
  }

  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!world.isRemote) {
      return TileUtils.getTile(world, pos, TileWoodToolRack.class).map(tile -> tile.onRightClick(playerIn, hand, getSlotFromPos(state, hitX, hitY, hitZ))).orElse(true);
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
      .withProperty(HORIZONTAL, BlockUtils.getASolidFacing(worldIn, pos, facing, HORIZONTALS));
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL);
  }

  @Override
  @SuppressWarnings("ConstantConditions")
  public ItemStack getPickBlock(IBlockState state, @Nullable RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    if (target != null) {
      var vec = target.hitVec.subtract(pos.getX(), pos.getY(), pos.getZ());
      var tile = TileUtils.getTile(world, pos, TileWoodToolRack.class);
      if (tile.isPresent()) {
        ItemStack item = tile.get().getItems().get(getSlotFromPos(state, (float) vec.x, (float) vec.y, (float) vec.z));
        if (!item.isEmpty()) {
          return item;
        }
      }
    }
    return super.getPickBlock(state, target, world, pos, player);
  }

  public int getSlotFromPos(IBlockState state, float x, float y, float z) {
    int slot = 0;
    if ((state.getValue(HORIZONTAL).getAxis().equals(Axis.Z) ? x : z) > .5f) {
      slot += 1;
    }
    if (y < 0.5f) {
      slot += 2;
    }
    return slot;
  }


  @Nullable
  @Override
  public TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileWoodToolRack();
  }
}
