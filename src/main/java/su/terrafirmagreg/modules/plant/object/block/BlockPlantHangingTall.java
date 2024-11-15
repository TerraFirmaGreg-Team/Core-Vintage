package su.terrafirmagreg.modules.plant.object.block;

import su.terrafirmagreg.api.data.enums.EnumPlantPart;
import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.api.types.variant.block.PlantBlockVariant;
import su.terrafirmagreg.modules.world.classic.WorldTypeClassic;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.IPlantable;

import net.dries007.tfc.objects.blocks.plants.property.ITallPlant;
import net.dries007.tfc.util.climate.ClimateTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.EnumProp.PLANT_PART;
import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.api.data.Properties.IntProp.DAYPERIOD;

public class BlockPlantHangingTall extends BlockPlant implements IGrowable, ITallPlant {

  public static final AxisAlignedBB AABB = new AxisAlignedBB(0.25F, 0, 0.25F, 0.75F, 1, 0.75F);

  public BlockPlantHangingTall(PlantBlockVariant variant, PlantType type) {
    super(variant, type);
  }

  @Override
  public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
    IBlockState iblockstate = worldIn.getBlockState(pos.down(2));
    Material material = iblockstate.getMaterial();

    int i;
    //noinspection StatementWithEmptyBody
    for (i = 1; worldIn.getBlockState(pos.up(i)).getBlock() == this; ++i) {

    }
    return i < type.getMaxHeight() && worldIn.isAirBlock(pos.down()) && ((!material.isSolid() || material == Material.LEAVES)) &&
           canBlockStay(worldIn, pos.down(), state);
  }

  @Override
  public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    return false;
  }

  @Override
  public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
    worldIn.setBlockState(pos.down(), this.getDefaultState());
    IBlockState iblockstate = state.withProperty(AGE_4, 0)
                                   .withProperty(STAGE, type.getStageForMonth())
                                   .withProperty(PLANT_PART, getPlantPart(worldIn, pos));
    worldIn.setBlockState(pos, iblockstate);
    iblockstate.neighborChanged(worldIn, pos.down(), this, pos);
  }

  public void shrink(World worldIn, BlockPos pos) {
    worldIn.setBlockToAir(pos);
    worldIn.getBlockState(pos).neighborChanged(worldIn, pos.up(), this, pos);
  }

  @Override
  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, STAGE, DAYPERIOD, AGE_4, PLANT_PART);
  }


  @Override
  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return super.getActualState(state, worldIn, pos).withProperty(PLANT_PART, getPlantPart(worldIn, pos));
  }

  @Override
  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
    if (!worldIn.isRemote) {
      if (stack.getItem() == Items.SHEARS ||
          stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 ||
          stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1) {

        spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
      }
    }
  }

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return Block.EnumOffsetType.XZ;
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState up = worldIn.getBlockState(pos.up());
    return (up.getBlock()
              .canSustainPlant(up, worldIn, pos.up(), net.minecraft.util.EnumFacing.DOWN, this) ||
            isValidBlock(worldIn, pos.up(), worldIn.getBlockState(pos.up())) || worldIn.getBlockState(pos.up())
                                                                                       .getBlock() == this)
           && type.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) &&
           type.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    //return this.canBlockStay(worldIn, pos, worldIn.getBlockState(pos));
    //return true;
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }

    if (type.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) &&
        type.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) &&
          ForgeHooks.onCropsGrowPre(worldIn, pos.down(), state, true)) {
        if (j == 3 && canGrow(worldIn, pos, state, worldIn.isRemote)) {
          grow(worldIn, rand, pos, state);
        } else if (j < 3) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j + 1)
                                          .withProperty(PLANT_PART, getPlantPart(worldIn, pos)));
        }
        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    } else if (!type.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) ||
               !type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
        if (j == 0 && canShrink(worldIn, pos)) {
          shrink(worldIn, pos);
        } else if (j > 0) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j - 1)
                                          .withProperty(PLANT_PART, getPlantPart(worldIn, pos)));
        }
        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    }

    checkAndDropBlock(worldIn, pos, state);
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState up = worldIn.getBlockState(pos.up());

    if (worldIn.getBlockState(pos.up(type.getMaxHeight())).getBlock() == this) {
      return false;
    }
    if (state.getBlock() == this) {
      return (up.getBlock()
                .canSustainPlant(up, worldIn, pos.up(), net.minecraft.util.EnumFacing.DOWN, this) ||
              isValidBlock(worldIn, pos.up(), worldIn.getBlockState(pos.up())) || worldIn.getBlockState(pos.up())
                                                                                         .getBlock() == this)
             && type.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) &&
             type.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    }
    return this.canSustainBush(up);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return AABB;
  }

  protected boolean isValidBlock(World world, BlockPos pos, IBlockState blockState) {
    IBlockState iblockstate = world.getBlockState(pos);
    Material material = iblockstate.getMaterial();

    return blockState.isSideSolid(world, pos, EnumFacing.DOWN) || material == Material.LEAVES || material == Material.GROUND ||
           material == Material.ROCK || material == Material.WOOD || BlockHelper.isGround(iblockstate) || blockState.getBlock() == this;
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      worldIn.destroyBlock(pos, false);
    }
  }

  @Override
  public void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
    if (!this.canBlockStay(worldIn, pos, state)) {
      worldIn.destroyBlock(pos, false);
    }
  }

  @Override
  public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
    if (pos.getY() < WorldTypeClassic.SEALEVEL) {
      if (rand.nextInt(40) == 0) {
        float dripRange = 0.4F;
        float px = rand.nextFloat() - 0.5F;
        float py = rand.nextFloat();
        float pz = rand.nextFloat() - 0.5F;
        float u = Math.max(Math.abs(px), Math.abs(pz));
        px = px / u * dripRange + 0.5F;
        pz = pz / u * dripRange + 0.5F;
        worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, pos.getX() + px, pos.getY() + py, pos.getZ() + pz, 0, -1, 0);
      }
    }
  }


  @Override
  public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
    return true;
  }

  @Override
  public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable) {
    IBlockState plant = plantable.getPlant(world, pos.offset(direction));

    if (plant.getBlock() == this) {
      return true;
    }
    return super.canSustainPlant(state, world, pos, direction, plantable);
  }

  @Override
  public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
    return this.getDefaultState().withProperty(PLANT_PART, EnumPlantPart.LOWER);
  }

  private boolean canShrink(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos.up()).getBlock() == this && worldIn.getBlockState(pos.down()).getBlock() != this;
  }
}
