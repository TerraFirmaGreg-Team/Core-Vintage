package net.dries007.tfc.objects.blocks.plants.BlockPlant;

import su.terrafirmagreg.modules.core.feature.climate.Climate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;

import net.dries007.tfc.ConfigTFC;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.util.OreDictionaryHelper;

import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;

import su.terrafirmagreg.modules.core.feature.calendar.Month;

import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.dries007.tfc.objects.blocks.BlocksTFCF;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@ParametersAreNonnullByDefault
public class BlockPlantTFCF extends BlockBush implements ICapabilitySize {

  public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
  /*
   * Time of day, used for rendering plants that bloom at different times
   * 0 = midnight-dawn
   * 1 = dawn-noon
   * 2 = noon-dusk
   * 3 = dusk-midnight
   */
  public final static PropertyInteger DAYPERIOD = PropertyInteger.create("dayperiod", 0, 3);
  private static final AxisAlignedBB PLANT_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
  //private static final Map<Plant, BlockPlantTFCF> MAP = new HashMap<>();

    /*public static BlockPlantTFCF get(Plant plant)
    {
        return MAP.get(plant);
    }*/

  /* Growth Stage of the plant, tied to the month of year */
  public final PropertyInteger growthStageProperty;
  protected final Plant plant;
  protected final BlockStateContainer blockState;

  public BlockPlantTFCF(Plant plant) {
    super(plant.getMaterial());
    //if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");

    plant.getOreDictName().ifPresent(name -> OreDictionaryHelper.register(this, name));

    this.plant = plant;
    this.growthStageProperty = PropertyInteger.create("stage", 0, plant.getNumStages());
    this.setTickRandomly(true);
    setSoundType(SoundType.PLANT);
    setHardness(0.0F);
    Blocks.FIRE.setFireInfo(this, 5, 20);
    blockState = this.createPlantBlockState();
    this.setDefaultState(this.blockState.getBaseState());
  }

  @SuppressWarnings("deprecation")
  @Override
  @Nonnull
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(AGE, meta);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(AGE);
  }

  @SuppressWarnings("deprecation")
  @Override
  @Nonnull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return state.withProperty(DAYPERIOD, getDayPeriod()).withProperty(growthStageProperty, plant.getStageForMonth());
  }

  @Override
  public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  @Override
  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (!worldIn.isAreaLoaded(pos, 1)) {return;}
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(growthStageProperty);
    int expectedStage = plant.getStageForMonth(currentMonth);
    int currentTime = state.getValue(DAYPERIOD);
    int expectedTime = getDayPeriod();

    if (currentTime != expectedTime) {
      worldIn.setBlockState(pos, state.withProperty(DAYPERIOD, expectedTime).withProperty(growthStageProperty, currentStage));
    }
    if (currentStage != expectedStage && random.nextDouble() < 0.5) {
      worldIn.setBlockState(pos, state.withProperty(DAYPERIOD, expectedTime).withProperty(growthStageProperty, expectedStage));
    }

    this.updateTick(worldIn, pos, state, random);
  }

  @Override
  public int tickRate(World worldIn) {
    return 10;
  }

  @Override
  public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    world.setBlockState(pos, state.withProperty(DAYPERIOD, getDayPeriod()).withProperty(growthStageProperty, plant.getStageForMonth()));
    checkAndDropBlock(world, pos, state);
  }

  @Override
  @Nonnull
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    if (!plant.getOreDictName().isPresent()) {
      return Items.AIR;
    }
    return Item.getItemFromBlock(this);
  }

  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    // Entity X/Z motion is reduced by plants. Affine combination of age modifier and actual modifier
    if (!(entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).isCreative())) {
      double modifier = 0.25 * (4 - state.getValue(AGE));
      modifier = (1 - modifier) * plant.getMovementMod() + modifier;
      if (modifier < ConfigTFC.General.MISC.minimumPlantMovementModifier) {
        modifier = ConfigTFC.General.MISC.minimumPlantMovementModifier;
      }
      entityIn.motionX *= modifier;
      entityIn.motionZ *= modifier;
    }
  }

  @Override
  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
    if (!plant.getOreDictName().isPresent() && !worldIn.isRemote && (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1
                                                                     || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1)
        && plant.getPlantType() != Plant.PlantType.SHORT_GRASS && plant.getPlantType() != Plant.PlantType.TALL_GRASS) {
      spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
    }
    super.harvestBlock(worldIn, player, pos, state, te, stack);
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    if (!canBlockStay(worldIn, pos, state) && placer instanceof EntityPlayer) {
      if (!((EntityPlayer) placer).isCreative() && !plant.getOreDictName().isPresent()) {
        spawnAsEntity(worldIn, pos, new ItemStack(this));
      }
    }
  }

  @Nonnull
  @Override
  public BlockStateContainer getBlockState() {
    return this.blockState;
  }

  @Override
  @Nonnull
  public Block.EnumOffsetType getOffsetType() {
    return Block.EnumOffsetType.XYZ;
  }

  @Override
  public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
    ItemStack stack = player.getHeldItemMainhand();
    IBlockState state = world.getBlockState(pos);
    switch (plant.getPlantType()) {
      case REED:
      case REED_SEA:
      case TALL_REED:
      case TALL_REED_SEA:
            /*case SHORT_GRASS:
            case TALL_GRASS:*/
        return (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1);
      default:
        return true;
    }
  }

  public Plant getPlant() {
    return plant;
  }

  @Nonnull
  @Override
  public Size getSize(ItemStack stack) {
    return Size.TINY; // Store anywhere
  }

  @Nonnull
  @Override
  public Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT; // Stacksize = 64
  }

  public double getGrowthRate(World world, BlockPos pos) {
    if (world.isRainingAt(pos)) {return ConfigTFC.General.MISC.plantGrowthRate * 5d;} else {return ConfigTFC.General.MISC.plantGrowthRate;}
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    Block blockAt = worldIn.getBlockState(pos).getBlock();
    return blockAt.isReplaceable(worldIn, pos) && blockAt != this && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
  }

  @Override
  protected boolean canSustainBush(IBlockState state) {
    if (plant.getIsClayMarking()) {return BlocksTFC.isClay(state) || isValidSoil(state);} else {return isValidSoil(state);}
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {return;}

    if (plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos))
        && plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
      int j = state.getValue(AGE);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
        if (j < 3) {
          worldIn.setBlockState(pos, state.withProperty(AGE, j + 1));
        }
        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    } else if (!plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) || !plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
      int j = state.getValue(AGE);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
        if (j > 0) {
          worldIn.setBlockState(pos, state.withProperty(AGE, j - 1));
        }
        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    }

    checkAndDropBlock(worldIn, pos, state);
  }

  @Override
  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    if (state.getBlock() == this) {
      return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this)
             && plant.isValidTemp(Climate.getActualTemp(worldIn, pos)) && plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos));
    }
    return this.canSustainBush(soil);
  }

  @SuppressWarnings("deprecation")
  @Override
  @Nonnull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return PLANT_AABB.offset(state.getOffset(source, pos));
  }

  @SuppressWarnings("deprecation")
  @Nullable
  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    if (plant.getMovementMod() == 0.0D) {
      return blockState.getBoundingBox(worldIn, pos);
    } else {
      return NULL_AABB;
    }
  }

  @Override
  @Nonnull
  public EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
    switch (plant.getPlantType()) {
      case CACTUS:
      case DESERT:
      case DESERT_TALL_PLANT:
        return EnumPlantType.Desert;
      case FLOATING:
      case FLOATING_SEA:
        return EnumPlantType.Water;
      case MUSHROOM:
        return EnumPlantType.Cave;
      default:
        return EnumPlantType.Plains;
    }
  }

  @Nonnull
  public Plant.EnumPlantTypeTFC getPlantTypeTFC() {
    return plant.getEnumPlantTypeTFC();
  }

  @Nonnull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, growthStageProperty, DAYPERIOD, AGE);
  }

  int getDayPeriod() {
    return Calendar.CALENDAR_TIME.getHourOfDay() / (ICalendar.HOURS_IN_DAY / 4);
  }

  private boolean isValidSoil(IBlockState state) {
    switch (plant.getPlantType()) {
      case CACTUS:
      case DESERT:
      case DESERT_TALL_PLANT:
        return BlocksTFC.isSand(state) || state.getBlock() == Blocks.HARDENED_CLAY || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      case DRY:
      case DRY_TALL_PLANT:
        return BlocksTFC.isSand(state) || BlocksTFC.isDryGrass(state) || BlocksTFCF.isDryGrass(state) || state.getBlock() == Blocks.HARDENED_CLAY
               || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      case REED:
      case REED_SEA:
      case TALL_REED:
      case TALL_REED_SEA:
        return BlocksTFC.isSand(state) || BlocksTFC.isSoil(state) || BlocksTFCF.isSoil(state) || state.getBlock() == Blocks.HARDENED_CLAY
               || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      case WATER:
      case TALL_WATER:
      case EMERGENT_TALL_WATER:
        return BlocksTFC.isSand(state) || BlocksTFC.isSoilOrGravel(state) || BlocksTFC.isSoil(state) || BlocksTFC.isGround(state)
               || BlocksTFCF.isSoilOrGravel(state) || BlocksTFCF.isSoil(state) || BlocksTFCF.isGround(state) || state.getBlock() == Blocks.HARDENED_CLAY
               || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      case WATER_SEA:
      case TALL_WATER_SEA:
      case EMERGENT_TALL_WATER_SEA:
        return BlocksTFC.isSand(state) || BlocksTFC.isSoilOrGravel(state) || BlocksTFC.isSoil(state) || BlocksTFC.isGround(state)
               || BlocksTFCF.isSoilOrGravel(state) || BlocksTFCF.isSoil(state) || BlocksTFCF.isGround(state) || state.getBlock() == Blocks.HARDENED_CLAY
               || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      case CREEPING:
      case HANGING:
        return BlocksTFC.isSand(state) || BlocksTFC.isSoilOrGravel(state) || BlocksTFC.isSoil(state) || BlocksTFC.isGround(state)
               || BlocksTFCF.isSoilOrGravel(state) || BlocksTFCF.isSoil(state) || BlocksTFCF.isGround(state) || state.getBlock() == Blocks.HARDENED_CLAY
               || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      default:
        return BlocksTFC.isSoil(state) || BlocksTFCF.isSoil(state) || state.getBlock() == Blocks.HARDENED_CLAY
               || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
    }
  }
}
