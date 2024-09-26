package net.dries007.tfc.objects.blocks;

import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TileUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.objects.te.TEHangingPlanter;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Supplier;

import static su.terrafirmagreg.data.MathConstants.RNG;
import static su.terrafirmagreg.data.Properties.STAGE;

@MethodsReturnNonnullByDefault

public class BlockBonsai extends BlockNonCube {

  protected final Supplier<? extends Item> fruit;
  protected final Supplier<? extends Item> seed;
  protected final long period;
  protected final int tier;

  public BlockBonsai(Supplier<? extends Item> fruit, Supplier<? extends Item> seed, int period, int tier, Material material) {
    super(material);
    setHardness(2.0f);
    setResistance(3.0f);
    setLightOpacity(0);
    setTickRandomly(true);
    this.seed = seed;
    this.fruit = fruit;
    this.period = period;
    this.tier = tier;
    setDefaultState(blockState.getBaseState().withProperty(STAGE, 0));
  }

  @Override
  @SuppressWarnings("deprecation")
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(STAGE, meta);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(STAGE);
  }

  @Override
  @SuppressWarnings("deprecation")
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return BlockLargePlanter.HALF_BLOCK_SHAPE;
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    if (!world.isRemote) {
      var tile = TileUtils.getTile(world, pos, TEHangingPlanter.class);
      int stage = state.getValue(STAGE);
      if (tile != null && tile.isClimateValid(tier) && tile.getTicksSinceUpdate() >= (ICalendar.TICKS_IN_DAY * period) && stage < 2) {
        world.setBlockState(pos, state.withProperty(STAGE, stage + 1));
        tile.reduceCounter(ICalendar.TICKS_IN_DAY * period);
        tile.markForSync();
      }
    }
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX,
                                  float hitY, float hitZ) {
    if (!world.isRemote) {
      var tile = TileUtils.getTile(world, pos, TEHangingPlanter.class);
      if (tile == null) {
        return false;
      }
      ItemStack held = player.getHeldItem(hand);
      if (held.isEmpty() && state.getValue(STAGE) == 2) {
        BlockPos spawnPos = tier == 4 ? pos.up() : pos.down(); // who let me learn to code???
        StackUtils.spawnItemStack(world, spawnPos, new ItemStack(fruit.get(), tier == 4 ? 3 : 1));
        if (RNG.nextInt(7) == 0) {
          StackUtils.spawnItemStack(world, spawnPos, new ItemStack(seed.get()));
        }
        world.setBlockState(pos, state.withProperty(STAGE, 0));
        tile.resetCounter();
        return true;
      }
    }
    return false;
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    TETickCounter tile = TileUtils.getTile(worldIn, pos, TETickCounter.class);
    if (tile != null) {
      tile.resetCounter();
    }
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, STAGE);
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Override
  @Nullable
  public TileEntity createTileEntity(World world, IBlockState state) {
    return new TEHangingPlanter();
  }
}
