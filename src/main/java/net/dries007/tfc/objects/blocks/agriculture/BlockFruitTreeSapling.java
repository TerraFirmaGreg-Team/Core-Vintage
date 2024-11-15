package net.dries007.tfc.objects.blocks.agriculture;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types.IFruitTree;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.OreDictionaryHelper;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.feature.climate.Climate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.HARVESTABLE;

@MethodsReturnNonnullByDefault

public class BlockFruitTreeSapling extends BlockBush implements IGrowable, IGrowingPlant {

  private static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.9, 0.9);

  private static final Map<IFruitTree, BlockFruitTreeSapling> MAP = new HashMap<>();
  private final IFruitTree tree;

  public BlockFruitTreeSapling(IFruitTree tree) {
    if (MAP.put(tree, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
    this.tree = tree;
    setSoundType(SoundType.PLANT);
    setHardness(0.0F);
    setTickRandomly(true);
    OreDictionaryHelper.register(this, "tree", "sapling");
    OreDictionaryHelper.register(this, "tree", "sapling", tree.getName());
    BlockUtils.setFireInfo(this, 5, 20);
  }

  public static BlockFruitTreeSapling get(IFruitTree tree) {
    return MAP.get(tree);
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    super.updateTick(world, pos, state, random);

    if (world.isRemote) {return;}
    TileUtils.getTile(world, pos, TETickCounter.class).ifPresent(tile -> {
      float temp = Climate.getActualTemp(world, pos);
      float rainfall = ProviderChunkData.getRainfall(world, pos);
      long hours = tile.getTicksSinceUpdate() / ICalendar.TICKS_IN_HOUR;
      if (hours > (tree.getGrowthTime() * ConfigTFC.General.FOOD.fruitTreeGrowthTimeModifier) && tree.isValidForGrowth(temp, rainfall)) {
        tile.resetCounter();
        grow(world, random, pos, state);
      }
    });

  }

  @Override
  public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TETickCounter.class).ifPresent(TETickCounter::resetCounter);
  }

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return Block.EnumOffsetType.XZ;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    tree.addInfo(stack, worldIn, tooltip, flagIn);
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TETickCounter();
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return SAPLING_AABB;
  }

  @Override
  public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean b) {
    return true;
  }

  @Override
  public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
    return true; //Only on sapling tho, so trunk still has to grow
  }

  @Override
  public void grow(World world, Random random, BlockPos blockPos, IBlockState blockState) {
    if (!world.isRemote) {
      world.setBlockState(blockPos, BlockFruitTreeTrunk.get(this.tree).getDefaultState());
      if (world.getBlockState(blockPos.up()).getMaterial().isReplaceable()) {
        world.setBlockState(blockPos.up(), BlockFruitTreeLeaves.get(tree)
                                                               .getDefaultState()
                                                               .withProperty(HARVESTABLE, false));
      }
    }
  }

  @NotNull
  public IFruitTree getTree() {
    return tree;
  }

  @Override
  public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
    float temp = Climate.getActualTemp(world, pos);
    float rainfall = ProviderChunkData.getRainfall(world, pos);
    boolean canGrow = tree.isValidForGrowth(temp, rainfall);
    return canGrow ? GrowthStatus.GROWING : GrowthStatus.NOT_GROWING;
  }
}
