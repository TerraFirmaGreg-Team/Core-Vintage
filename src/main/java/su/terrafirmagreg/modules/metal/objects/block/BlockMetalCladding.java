package su.terrafirmagreg.modules.metal.objects.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.api.types.variant.block.IMetalBlock;
import su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariant;
import su.terrafirmagreg.modules.metal.objects.tile.TileMetalSheet;

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

import gregtech.api.unification.OreDictUnifier;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.ore.OrePrefix;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

import static su.terrafirmagreg.api.data.Properties.BoolProp.ALL_FACES;

@SuppressWarnings("deprecation")
@Getter
public class BlockMetalCladding extends BaseBlock implements IMetalBlock {

  private static final AxisAlignedBB[] SHEET_AABB = new AxisAlignedBB[]{
    new AxisAlignedBB(0d, 0.9375d, 0d, 1d, 1d, 1d),
    new AxisAlignedBB(0d, 0d, 0d, 1d, 0.0625d, 1d),
    new AxisAlignedBB(0d, 0d, 0.9375d, 1d, 1d, 1d),
    new AxisAlignedBB(0d, 0d, 0d, 1d, 1d, 0.0625d),
    new AxisAlignedBB(0.9375d, 0d, 0d, 1d, 1d, 1d),
    new AxisAlignedBB(0d, 0d, 0d, 0.0625d, 1d, 1d)
  };

  private final MetalBlockVariant variant;
  private final MetalType type;

  public BlockMetalCladding(MetalBlockVariant variant, MetalType type) {
    super(Settings.of(Material.IRON));

    this.variant = variant;
    this.type = type;

    getSettings()
      .registryKey(type.getRegistryKey(variant))
      .customResource(variant.getCustomResource())
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .nonOpaque()
      .nonFullCube()
      .noItemBlock()
      .hardness(40F)
      .resistance(25F);

    setDefaultState(blockState.getBaseState()
                              .withProperty(ALL_FACES[0], false)
                              .withProperty(ALL_FACES[1], false)
                              .withProperty(ALL_FACES[2], false)
                              .withProperty(ALL_FACES[3], false)
                              .withProperty(ALL_FACES[4], false)
                              .withProperty(ALL_FACES[5], false));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  @Override
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    var tile = TileUtils.getTile(worldIn, pos, TileMetalSheet.class);
    if (tile.isPresent()) {
      for (EnumFacing face : EnumFacing.values()) {
        state = state.withProperty(ALL_FACES[face.getIndex()], tile.get().getFace(face));
      }
    }
    return state;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    var tile = TileUtils.getTile(source, pos, TileMetalSheet.class);
    int sheets = 0;
    AxisAlignedBB boundingBox = FULL_BLOCK_AABB;
    if (tile.isPresent()) {
      for (EnumFacing face : EnumFacing.values()) {
        if (tile.get().getFace(face)) {
          if (sheets == 1) {
            return FULL_BLOCK_AABB;
          } else {
            boundingBox = SHEET_AABB[face.getIndex()];
            sheets++;
          }
        }
      }
    }
    // This should't ever return null, since it will return FULL_BLOCK_AABB before that
    return boundingBox;
  }

  @Override
  public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
    var tile = TileUtils.getTile(worldIn, pos, TileMetalSheet.class);
    tile.ifPresent(tileMetalSheet -> {
      for (EnumFacing face : EnumFacing.values()) {
        if (tileMetalSheet.getFace(face)) {
          addCollisionBoxToList(pos, entityBox, collidingBoxes, SHEET_AABB[face.getIndex()]);
        }
      }
    });
  }

  @SideOnly(Side.CLIENT)
  @Override
  public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
    return getBoundingBox(state, worldIn, pos);
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    var tile = TileUtils.getTile(worldIn, pos, TileMetalSheet.class);
    tile.ifPresent(tileMetalSheet -> {
      for (EnumFacing face : EnumFacing.values()) {
        if (tileMetalSheet.getFace(face) && !worldIn.isSideSolid(pos.offset(face.getOpposite()), face)) {
          StackUtils.spawnItemStack(worldIn, pos, new ItemStack(OreDictUnifier.get(OrePrefix.plate, Materials.Iron).getItem()));
          tileMetalSheet.setFace(face, false);
        }
      }
      if (tileMetalSheet.getFaceCount() == 0) {
        // Remove the block
        worldIn.setBlockToAir(pos);
      }
    });
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    var tile = TileUtils.getTile(worldIn, pos, TileMetalSheet.class);
    tile.ifPresent(TileMetalSheet::onBreakBlock);
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public @Nullable RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
    return TileUtils.getTile(worldIn, pos, TileMetalSheet.class).map(tile -> {
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
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, ALL_FACES);
  }

  @Override
  public @Nullable TileEntity createTileEntity(World world, IBlockState state) {
    return new TileMetalSheet();
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(OreDictUnifier.get(OrePrefix.plate, Materials.Iron).getItem());
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @Override
  public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return NULL_AABB;
  }
}
