package net.dries007.tfc.objects.blocks.wood;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.BlockUtils;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.types.TreesTFCF;
import tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.DOWN;
import static su.terrafirmagreg.api.data.Properties.BoolProp.EAST;
import static su.terrafirmagreg.api.data.Properties.BoolProp.NORTH;
import static su.terrafirmagreg.api.data.Properties.BoolProp.SOUTH;
import static su.terrafirmagreg.api.data.Properties.BoolProp.UP;
import static su.terrafirmagreg.api.data.Properties.BoolProp.WEST;
import static su.terrafirmagreg.modules.rock.init.BlocksRock.SAND;

@MethodsReturnNonnullByDefault

public class BlockJoshuaTreeLog extends Block {

  private static final Map<Tree, BlockJoshuaTreeLog> MAP = new HashMap<>();
  private final Tree wood;

  public BlockJoshuaTreeLog(Tree wood) {
    super(Material.WOOD);
    this.wood = wood;
    if (MAP.put(wood, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }

    this.setDefaultState(this.blockState.getBaseState()
                                        .withProperty(NORTH, Boolean.FALSE)
                                        .withProperty(EAST, Boolean.FALSE)
                                        .withProperty(SOUTH, Boolean.FALSE)
                                        .withProperty(WEST, Boolean.FALSE)
                                        .withProperty(UP, Boolean.FALSE)
                                        .withProperty(DOWN, Boolean.FALSE));
    this.setHarvestLevel("axe", 0);
    this.setHardness(20.0F);
    this.setResistance(5.0F);
    this.setSoundType(SoundType.WOOD);
    OreDictionaryHelper.register(this, "log", "wood");
    //noinspection ConstantConditions
    OreDictionaryHelper.register(this, "log", "wood", wood.getRegistryName().getPath());
    if (wood.canMakeTannin()) {
      OreDictionaryHelper.register(this, "log", "wood", "tannin");
    }

    BlockUtils.setFireInfo(this, 5, 5);
    this.setTickRandomly(true);
  }

  public static BlockJoshuaTreeLog get(Tree wood) {
    return MAP.get(wood);
  }

  public Tree getWood() {
    return wood;
  }

  /**
   * Convert the BlockState into the correct metadata value
   */
  @Override
  public int getMetaFromState(IBlockState state) {
    return 0;
  }

  /**
   * Get the actual Block state of this Block at the given position. This applies properties not visible in the metadata, such as fence connections.
   */
  @Override
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    Block block = worldIn.getBlockState(pos.down()).getBlock();
    Block block1 = worldIn.getBlockState(pos.up()).getBlock();
    Block block2 = worldIn.getBlockState(pos.north()).getBlock();
    Block block3 = worldIn.getBlockState(pos.east()).getBlock();
    Block block4 = worldIn.getBlockState(pos.south()).getBlock();
    Block block5 = worldIn.getBlockState(pos.west()).getBlock();
    return state.withProperty(DOWN,
                              Variant.isVariant(worldIn.getBlockState(pos.down()), SAND) || BlockHelper.isSoilOrGravel(worldIn.getBlockState(pos.down()))
                              || BlockUtils.isBlock(block, this, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY, BlockJoshuaTreeFlower.get(wood)))
                .withProperty(UP, BlockUtils.isBlock(block1, this, BlockJoshuaTreeFlower.get(wood)))
                .withProperty(NORTH, BlockUtils.isBlock(block2, this, BlockJoshuaTreeFlower.get(wood)))
                .withProperty(EAST, BlockUtils.isBlock(block3, this, BlockJoshuaTreeFlower.get(wood)))
                .withProperty(SOUTH, BlockUtils.isBlock(block4, this, BlockJoshuaTreeFlower.get(wood)))
                .withProperty(WEST, BlockUtils.isBlock(block5, this, BlockJoshuaTreeFlower.get(wood)));
  }

  /**
   * Returns the quantity of items to drop on block destruction.
   */
    /*@Override
    public int quantityDropped(Random random)
    {
        return random.nextInt(2);
    }*/
  @Override
  public boolean isFullCube(IBlockState state) {
    return false;
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return false;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    state = state.getActualState(source, pos);
    float f = 0.1875F;
    float f1 = state.getValue(WEST) ? 0.0F : 0.1875F;
    float f2 = state.getValue(DOWN) ? 0.0F : 0.1875F;
    float f3 = state.getValue(NORTH) ? 0.0F : 0.1875F;
    float f4 = state.getValue(EAST) ? 1.0F : 0.8125F;
    float f5 = state.getValue(UP) ? 1.0F : 0.8125F;
    float f6 = state.getValue(SOUTH) ? 1.0F : 0.8125F;
    return new AxisAlignedBB(f1, f2, f3, f4, f5, f6);
  }

  @SideOnly(Side.CLIENT)
  @Override
  public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
    Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
    return block != this && block != BlockJoshuaTreeFlower.get(wood) &&
           (side != EnumFacing.DOWN || !Variant.isVariant(blockAccess.getBlockState(pos.offset(side)), SAND) ||
            !BlockHelper.isSoilOrGravel(blockAccess.getBlockState(pos.offset(side))) ||
            BlockUtils.isBlock(block, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY));
  }

  @Override
  public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
    return BlockFaceShape.UNDEFINED;
  }

  @SuppressWarnings("deprecation")
  @Override
  public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
    if (!isActualState) {
      state = state.getActualState(worldIn, pos);
    }

    float f = 0.1875F;
    float f1 = 0.8125F;
    addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D));

    if (state.getValue(WEST)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D));
    }

    if (state.getValue(EAST)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.8125D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D));
    }

    if (state.getValue(UP)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.8125D, 0.1875D, 0.8125D, 1.0D, 0.8125D));
    }

    if (state.getValue(DOWN)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D, 0.8125D));
    }

    if (state.getValue(NORTH)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D));
    }

    if (state.getValue(SOUTH)) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D));
    }
  }

  @Override
  public boolean isOpaqueCube(IBlockState state) {
    return false;
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!this.canSurviveAt(worldIn, pos)) {
      worldIn.destroyBlock(pos, true);
    }
  }

  /**
   * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor change. Cases may include when redstone
   * power is updated, cactus blocks popping off due to a neighboring solid block, etc.
   */
  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!this.canSurviveAt(worldIn, pos)) {
      worldIn.scheduleUpdate(pos, this, 1);
    }
  }

  /**
   * Get the Item that this Block should drop when harvested.
   */
  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        /*TFCFlorae.getLog().warn("This wood is " + wood);
        TFCFlorae.getLog().warn("Wood drop is " + BlockLogTFC.get(wood));*/
    if (BlockLogTFC.get(wood) != null) {
      return BlockLogTFC.get(wood).getItemDropped(state, rand, fortune);
    } else {
      return BlockLogTFC.get(TFCRegistries.TREES.getValue(TreesTFCF.JOSHUA_TREE))
                        .getItemDropped(state, rand, fortune);
    }
  }

  @SideOnly(Side.CLIENT)
  @Override
  public BlockRenderLayer getRenderLayer() {
    return BlockRenderLayer.CUTOUT;
  }

  /**
   * Checks if this block can be placed exactly at the given position.
   */
  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos) && this.canSurviveAt(worldIn, pos);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, NORTH, EAST, SOUTH, WEST, UP, DOWN);
  }

  public boolean canSurviveAt(World worldIn, BlockPos pos) {
    boolean flag = worldIn.isAirBlock(pos.up());
    boolean flag1 = worldIn.isAirBlock(pos.down());

    for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
      BlockPos blockpos = pos.offset(enumfacing);
      Block block = worldIn.getBlockState(blockpos).getBlock();

      if (block == this) {
        if (!flag && !flag1) {
          return false;
        }

        Block block1 = worldIn.getBlockState(blockpos.down()).getBlock();

        if (Variant.isVariant(worldIn.getBlockState(blockpos.down()), SAND) ||
                BlockHelper.isSoilOrGravel(worldIn.getBlockState(blockpos.down())) ||
            BlockUtils.isBlock(block1, this, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY)) {
          return true;
        }
      }
    }

    Block block2 = worldIn.getBlockState(pos.down()).getBlock();
    return Variant.isVariant(worldIn.getBlockState(pos.down()), SAND) ||
            BlockHelper.isSoilOrGravel(worldIn.getBlockState(pos.down())) ||
           BlockUtils.isBlock(block2, this, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY);
  }
}
