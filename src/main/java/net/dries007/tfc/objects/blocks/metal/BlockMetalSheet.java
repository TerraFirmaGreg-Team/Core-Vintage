package net.dries007.tfc.objects.blocks.metal;

import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemMetalSheet;
import net.dries007.tfc.objects.te.TEMetalSheet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static su.terrafirmagreg.api.data.Properties.BoolProp.ALL_FACES;

public class BlockMetalSheet extends Block {

  private static final Map<Metal, BlockMetalSheet> MAP = new HashMap<>();
  private static final AxisAlignedBB[] SHEET_AABB = new AxisAlignedBB[]{
    new AxisAlignedBB(0d, 0.9375d, 0d, 1d, 1d, 1d),
    new AxisAlignedBB(0d, 0d, 0d, 1d, 0.0625d, 1d),
    new AxisAlignedBB(0d, 0d, 0.9375d, 1d, 1d, 1d),
    new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 0.0625d),
    new AxisAlignedBB(0.9375d, 0d, 0d, 1d, 1d, 1d),
    new AxisAlignedBB(0d, 0d, 0d, 0.0625d, 1d, 1d)
  };
  private final Metal metal;

  public BlockMetalSheet(Metal metal) {
    super(Material.IRON);

    this.metal = metal;
    if (MAP.put(metal, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }

    setHardness(40F);
    setResistance(25F);
    setHarvestLevel("pickaxe", 0);

    setDefaultState(this.blockState.getBaseState()
                                   .withProperty(ALL_FACES[0], false)
                                   .withProperty(ALL_FACES[1], false)
                                   .withProperty(ALL_FACES[2], false)
                                   .withProperty(ALL_FACES[3], false)
                                   .withProperty(ALL_FACES[4], false)
                                   .withProperty(ALL_FACES[5], false));
  }

  public static BlockMetalSheet get(Metal metal) {
    return MAP.get(metal);
  }

  public static ItemStack get(Metal metal, int amount) {
    return new ItemStack(MAP.get(metal), amount);
  }

  @NotNull
  public Metal getMetal() {
    return metal;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isTopSolid(IBlockState state) {
    return false;
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    TileUtils.getTile(worldIn, pos, TEMetalSheet.class).ifPresent(tile -> {
      for (EnumFacing face : EnumFacing.values()) {
        state.withProperty(ALL_FACES[face.getIndex()], tile.getFace(face));
      }
    });
    return state;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isBlockNormalCube(IBlockState state) {
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isNormalCube(IBlockState state) {
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @Override
  @NotNull
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {

    return TileUtils.getTile(source, pos, TEMetalSheet.class).map(tile -> {
      int sheets = 0;
      AxisAlignedBB boundingBox = FULL_BLOCK_AABB;
      for (EnumFacing face : EnumFacing.values()) {
        if (tile.getFace(face)) {
          if (sheets == 1) {
            return boundingBox;
          } else {
            boundingBox = SHEET_AABB[face.getIndex()];
            sheets++;
          }
        }
      }
      return boundingBox;
    }).orElse(FULL_BLOCK_AABB);

  }

  @Override
  @NotNull
  @SuppressWarnings("deprecation")
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
    TileUtils.getTile(worldIn, pos, TEMetalSheet.class).ifPresent(tile -> {
      for (EnumFacing face : EnumFacing.values()) {
        if (tile.getFace(face)) {
          addCollisionBoxToList(pos, entityBox, collidingBoxes, SHEET_AABB[face.getIndex()]);
        }
      }
    });
  }

  @Nullable
  @Override
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }

  @SideOnly(Side.CLIENT)
  @Override
  @NotNull
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
    return getBoundingBox(state, worldIn, pos);
  }

  @SuppressWarnings("deprecation")
  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    TileUtils.getTile(worldIn, pos, TEMetalSheet.class).ifPresent(tile -> {
      for (EnumFacing face : EnumFacing.values()) {
        if (tile.getFace(face) && !worldIn.isSideSolid(pos.offset(face.getOpposite()), face)) {
          StackUtils.spawnItemStack(worldIn, pos, new ItemStack(ItemMetalSheet.get(metal, Metal.ItemType.SHEET)));
          tile.setFace(face, false);
        }
      }
      if (tile.getFaceCount() == 0) {
        // Remove the block
        worldIn.setBlockToAir(pos);
      }
    });
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TEMetalSheet.class).ifPresent(tile -> tile.onBreakBlock(this.metal));
    super.breakBlock(worldIn, pos, state);
  }

  @Nullable
  @Override
  @SuppressWarnings("deprecation")
  public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
    return TileUtils.getTile(worldIn, pos, TEMetalSheet.class).map(tile -> {
      for (EnumFacing face : EnumFacing.values()) {
        if (tile.getFace(face)) {
          RayTraceResult result = rayTrace(pos, start, end, SHEET_AABB[face.getIndex()]);
          if (result != null) {
            return result;
          }
        }
      }
      return null;
    }).orElse(null);
  }

  @Override
  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, ALL_FACES);
  }

  @Override
  public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
    return false;
  }

  @Override
  @SuppressWarnings("deprecation")
  public boolean isSideSolid(IBlockState baseState, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return false;
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TEMetalSheet();
  }

  @NotNull
  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(ItemMetalSheet.get(this.metal, Metal.ItemType.SHEET));
  }
}
