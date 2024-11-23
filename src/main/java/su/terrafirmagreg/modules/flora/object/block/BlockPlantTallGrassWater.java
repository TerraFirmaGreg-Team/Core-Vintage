package su.terrafirmagreg.modules.flora.object.block;

import su.terrafirmagreg.api.data.enums.EnumPlantPart;
import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.flora.api.types.type.FloraType;
import su.terrafirmagreg.modules.flora.api.types.variant.block.FloraBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.objects.blocks.plants.property.ITallPlant;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.EnumProp.PLANT_PART;
import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.api.data.Properties.IntProp.DAYPERIOD;

public class BlockPlantTallGrassWater extends BlockPlantShortGrass implements IGrowable, ITallPlant {


  public BlockPlantTallGrassWater(FloraBlockVariant variant, FloraType type) {
    super(variant, type);

  }

  @Override
  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return super.getActualState(state, worldIn, pos).withProperty(PLANT_PART, getPlantPart(worldIn, pos));
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
  }

  @Override
  protected boolean canSustainBush(IBlockState state) {
    return (BlockHelper.isWater(state) || state.getMaterial() == Material.ICE && state == type.getWaterType()) ||
           (state.getMaterial() == Material.CORAL && !(state.getBlock() instanceof BlockPlantEmergentTallWater));
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState soil = worldIn.getBlockState(pos.down());

    if (worldIn.getBlockState(pos.down(type.getMaxHeight())).getBlock() == this) {
      return false;
    }
    if (soil.getBlock() instanceof BlockPlantWater) {
      return false;
    }
    if (state.getBlock() == this) {
      IBlockState stateDown = worldIn.getBlockState(pos.down());
      Material material = stateDown.getMaterial();
      return (soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this) ||
              ((material == Material.WATER && stateDown.getValue(BlockLiquid.LEVEL) == 0 && stateDown == type.getWaterType()) ||
               material == Material.ICE ||
               (material == Material.CORAL && !(state.getBlock() instanceof BlockPlantEmergentTallWater)))) &&
             type.isValidTemp(Climate.getActualTemp(worldIn, pos)) && type.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    } else {
      return this.canSustainBush(soil);
    }
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (getPlantPart(worldIn, pos) == EnumPlantPart.LOWER) {
      worldIn.setBlockState(pos, state.withProperty(AGE_4, worldIn.getBlockState(pos.up()).getValue(AGE_4)));
    }

    if (!this.canBlockStay(worldIn, pos, state)) {
      worldIn.destroyBlock(pos, true);
    }
  }

  @Override
  protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      if (getPlantPart(worldIn, pos) != EnumPlantPart.UPPER) {
        this.dropBlockAsItem(worldIn, pos, state, 0);
      }
      worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
    }
  }

  @Override
  public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    int i;
    //noinspection StatementWithEmptyBody
    for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; ++i)
      ;
    return i < type.getMaxHeight() && worldIn.isAirBlock(pos.up()) && canBlockStay(worldIn, pos.up(), state);
  }

  @Override
  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    return false;
  }

  @Override
  public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    worldIn.setBlockState(pos.up(), this.getDefaultState());
    IBlockState iblockstate = state.withProperty(AGE_4, 0)
                                   .withProperty(STAGE, type.getStageForMonth())
                                   .withProperty(PLANT_PART, getPlantPart(worldIn, pos));
    worldIn.setBlockState(pos, iblockstate);
    iblockstate.neighborChanged(worldIn, pos.up(), this, pos);
  }

  public void shrink(World worldIn, BlockPos pos) {
    worldIn.setBlockToAir(pos);
    worldIn.getBlockState(pos).neighborChanged(worldIn, pos.down(), this, pos);
  }

  @Override
  @NotNull
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, AGE_4, STAGE, DAYPERIOD, PLANT_PART);
  }

  @Override
  public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
    if (!worldIn.isRemote && player != null) {
      ItemStack stack = player.getHeldItemMainhand();
      if (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem()
                                                                                       .getHarvestLevel(stack, "scythe", player, state) != -1) {
        for (int i = 1; worldIn.getBlockState(pos.up(i)).getBlock() == this; ++i) {
          if (MathUtils.RNG.nextDouble() <= (worldIn.getBlockState(pos.up(i))
                                                    .getValue(AGE_4) + 1) / 4.0D) //+25% change for each age
          {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsCore.STRAW, 1));
          }
        }
      }
    }
  }

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return Block.EnumOffsetType.XZ;
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    this.onBlockHarvested(world, pos, state, player);
    return world.setBlockState(pos, net.minecraft.init.Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }

    if (type.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) &&
        type.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) &&
          net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
        if (j == 3 && canGrow(worldIn, pos, state, worldIn.isRemote)) {
          grow(worldIn, rand, pos, state);
        } else if (j < 3) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j + 1)
                                          .withProperty(PLANT_PART, getPlantPart(worldIn, pos)));
        }
        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    } else if (!type.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) ||
               !type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
        if (j == 0 && canShrink(worldIn, pos)) {
          shrink(worldIn, pos);
        } else if (j > 0) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j - 1)
                                          .withProperty(PLANT_PART, getPlantPart(worldIn, pos)));
        }
        net.minecraftforge.common.ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    }

    checkAndDropBlock(worldIn, pos, state);
  }

  @Override
  public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
                                 net.minecraftforge.common.IPlantable plantable) {
    IBlockState plant = plantable.getPlant(world, pos.offset(direction));

    if (plant.getBlock() == this) {
      return true;
    }
    return super.canSustainPlant(state, world, pos, direction, plantable);
  }

  @Override
  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return getTallBoundingBax(state.getValue(AGE_4), state, source, pos);
  }

  @Override
  public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
    return true;
  }

  @Override
  @NotNull
  public NonNullList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
    return NonNullList.withSize(1, new ItemStack(this, 1));
  }

  private boolean canShrink(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos.down()).getBlock() == this && worldIn.getBlockState(pos.up())
                                                                          .getBlock() != this;
  }


}
