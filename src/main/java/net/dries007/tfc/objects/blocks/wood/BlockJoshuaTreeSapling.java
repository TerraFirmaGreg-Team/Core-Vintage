package net.dries007.tfc.objects.blocks.wood;

import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.te.TETickCounter;
import net.dries007.tfcflorae.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.STAGE_5;
import static su.terrafirmagreg.modules.rock.init.BlocksRock.SAND;

public class BlockJoshuaTreeSapling extends BlockBush implements IGrowable {

  protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.1, 0, 0.1, 0.9, 0.9, 0.9);
  private static final Map<Tree, BlockJoshuaTreeSapling> MAP = new HashMap<>();
  private final Tree wood;

  public BlockJoshuaTreeSapling(Tree wood) {
    if (MAP.put(wood, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }
    this.wood = wood;
    setDefaultState(blockState.getBaseState().withProperty(STAGE_5, 0));
    setSoundType(SoundType.PLANT);
    setHardness(0.0F);
    OreDictionaryHelper.register(this, "tree", "sapling");
    //noinspection ConstantConditions
    OreDictionaryHelper.register(this, "tree", "sapling", wood.getRegistryName().getPath());
    BlockUtils.setFireInfo(this, 5, 20);
  }

  public static BlockJoshuaTreeSapling get(Tree wood) {
    return MAP.get(wood);
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(STAGE_5, meta);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(STAGE_5);
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    TileUtils.getTile(worldIn, pos, TETickCounter.class).ifPresent(TETickCounter::resetCounter);
    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  @Override
  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, STAGE_5);
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
    wood.addInfo(stack, worldIn, tooltip, flagIn);
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

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    Block block = worldIn.getBlockState(pos.down()).getBlock();
    return (super.canPlaceBlockAt(worldIn, pos) ||
            Variant.isVariant(worldIn.getBlockState(pos.down()), SAND) ||
            BlockHelper.isSoilOrGravel(worldIn.getBlockState(pos.down())) ||
            BlockUtils.isBlock(block, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY));
  }

  @Override
  public void updateTick(World world, BlockPos pos, IBlockState state, Random random) {
    super.updateTick(world, pos, state, random);

    if (world.isRemote) {return;}
    TileUtils.getTile(world, pos, TETickCounter.class).ifPresent(tile -> {
      long days = tile.getTicksSinceUpdate() / ICalendar.TICKS_IN_DAY;
      if (days > wood.getMinGrowthTime()) {
        grow(world, random, pos, state);
      }
    });
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return SAPLING_AABB;
  }

  @Override
  @NotNull
  public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
    return EnumPlantType.Plains;
  }

  public Tree getWood() {
    return wood;
  }

  @Override
  public boolean canGrow(World world, BlockPos blockPos, IBlockState iBlockState, boolean b) {
    return true;
  }

  @Override
  public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, IBlockState iBlockState) {
    return false;
  }

  @Override
  public void grow(World world, Random rand, BlockPos pos, IBlockState blockState) {
        /*for (int air = 1; air < 15; air++)
        {
            if (!world.isAirBlock(pos.offset(EnumFacing.UP, air)))
                return;
        }
        int height = 1 + rand.nextInt(2);
        IBlockState flower = BlockJoshuaTreeFlower.get(wood).getDefaultState();
        for (int trunk = 0; trunk < height; trunk++)
        {
            BlockPos trunkPos = pos.offset(EnumFacing.UP, trunk);
            world.setBlockState(trunkPos, BlockJoshuaTreeLog.get(wood).getDefaultState());
            if (trunk < 0)
                continue;
            for (EnumFacing d : EnumFacing.HORIZONTALS)
            {
                world.setBlockState(trunkPos.offset(d, 1), flower);
                if (rand.nextFloat() < 1 - (float) trunk / height)
                {
                    world.setBlockState(trunkPos.offset(d, 2), flower);
                }
                else { continue; }
                if (trunk < 0.3 * height && rand.nextFloat() < (1 - (float) trunk / (height)) / 3)
                    world.setBlockState(trunkPos.offset(d, 3), flower);
            }
        }
        world.setBlockState(pos.offset(EnumFacing.UP, height), flower);*/

    float avgTemperature = Climate.getAvgTemp(world, pos);
    float rainfall = ProviderChunkData.getRainfall(world, pos);

    int j = rand.nextInt(5);
    for (int k = 0; k < j; ++k) {
      int l = rand.nextInt(16) + 8;
      int i1 = rand.nextInt(16) + 8;
      int j1 = world.getHeight(pos.add(l, 0, i1)).getY();
      if (j1 > 0) {
        int k1 = j1 - 1;
        Block block = world.getBlockState(pos.down()).getBlock();
        if (world.isAirBlock(pos.add(l, k1 + 1, i1)) && (
          Variant.isVariant(world.getBlockState(pos.add(l, k1, i1)), SAND) ||
          BlockHelper.isSoilOrGravel(world.getBlockState(pos.add(l, k1, i1))) ||
          BlockUtils.isBlock(block, Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY))) {
          BlockJoshuaTreeFlower.get(wood).generatePlant(world, pos.add(l, k1 + 1, i1), rand, 8);
        }
      }
    }
  }
}
