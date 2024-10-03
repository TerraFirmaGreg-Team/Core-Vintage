package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.data.lib.MCDate.Month;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.plant.api.types.category.PlantCategories;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

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
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.climate.Climate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.data.Properties.IntProp.DAYPERIOD;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.CACTUS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.CREEPING;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.DESERT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.DESERT_TALL_PLANT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.DRY;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.DRY_TALL_PLANT;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.EMERGENT_TALL_WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.EMERGENT_TALL_WATER_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.HANGING;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.REED;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.REED_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.SHORT_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_GRASS;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_REED;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_REED_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.TALL_WATER_SEA;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER;
import static su.terrafirmagreg.modules.plant.api.types.category.PlantCategories.WATER_SEA;

public class BlockPlantTFCF extends BlockBush implements ICapabilitySize {

  private static final AxisAlignedBB PLANT_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
  //private static final Map<Plant, BlockPlantTFCF> MAP = new HashMap<>();

    /*public static BlockPlantTFCF get(Plant plant)
    {
        return MAP.get(plant);
    }*/

  /* Growth Stage of the plant, tied to the month of year */
  public final PropertyInteger growthStageProperty;
  @Getter
  protected final PlantType plant;
  protected final BlockStateContainer blockState;

  public BlockPlantTFCF(PlantType plant) {
    super(plant.getMaterial());
    //if (MAP.put(plant, this) != null) throw new IllegalStateException("There can only be one.");

    plant.getOreDictName().ifPresent(name -> OreDictUtils.register(this, name));

    this.plant = plant;
    this.growthStageProperty = PropertyInteger.create("stage", 0, plant.getNumStages());
    this.setTickRandomly(true);
    setSoundType(SoundType.PLANT);
    setHardness(0.0F);
    BlockUtils.setFireInfo(this, 5, 20);
    blockState = this.createPlantBlockState();
    this.setDefaultState(this.blockState.getBaseState());
  }

  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, growthStageProperty, DAYPERIOD, AGE_4);
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(AGE_4, meta);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(AGE_4);
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return state.withProperty(DAYPERIOD, getDayPeriod())
                .withProperty(growthStageProperty, plant.getStageForMonth());
  }

  @Override
  public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  @Override
  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(growthStageProperty);
    int expectedStage = plant.getStageForMonth(currentMonth);
    int currentTime = state.getValue(DAYPERIOD);
    int expectedTime = getDayPeriod();

    if (currentTime != expectedTime) {
      worldIn.setBlockState(pos, state.withProperty(DAYPERIOD, expectedTime)
                                      .withProperty(growthStageProperty, currentStage));
    }
    if (currentStage != expectedStage && random.nextDouble() < 0.5) {
      worldIn.setBlockState(pos, state.withProperty(DAYPERIOD, expectedTime)
                                      .withProperty(growthStageProperty, expectedStage));
    }

    this.updateTick(worldIn, pos, state, random);
  }

  @Override
  public int tickRate(World worldIn) {
    return 10;
  }

  @Override
  public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    world.setBlockState(pos, state.withProperty(DAYPERIOD, getDayPeriod())
                                  .withProperty(growthStageProperty, plant.getStageForMonth()));
    checkAndDropBlock(world, pos, state);
  }

  @Override
  @NotNull
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
      double modifier = 0.25 * (4 - state.getValue(AGE_4));
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
    if (!plant.getOreDictName().isPresent() && !worldIn.isRemote && (stack.getItem()
                                                                          .getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem()
                                                                                                                                        .getHarvestLevel(stack, "scythe", player, state)
                                                                                                                                   != -1)
        && plant.getCategory() != PlantCategories.SHORT_GRASS &&
        plant.getCategory() != TALL_GRASS) {
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

  @NotNull
  @Override
  public BlockStateContainer getBlockState() {
    return this.blockState;
  }

  @Override
  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return Block.EnumOffsetType.XYZ;
  }

  @Override
  public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
    ItemStack stack = player.getHeldItemMainhand();
    IBlockState state = world.getBlockState(pos);
    if (plant.getCategory() == REED || plant.getCategory() == REED_SEA || plant.getCategory() == TALL_REED || plant.getCategory() == TALL_REED_SEA
        || plant.getCategory() == SHORT_GRASS || plant.getCategory() == TALL_GRASS) {
      return (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1);

    }
    return true;
  }

  private boolean isValidSoil(IBlockState state) {
    if (plant.getCategory() == CACTUS || plant.getCategory() == DESERT || plant.getCategory() == DESERT_TALL_PLANT) {
      return BlockUtils.isSand(state) || state.getBlock() == Blocks.HARDENED_CLAY || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
    } else if (plant.getCategory() == DRY || plant.getCategory() == DRY_TALL_PLANT) {
      return BlockUtils.isSand(state) || BlockUtils.isDryGrass(state) || state.getBlock() == Blocks.HARDENED_CLAY
             || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
    } else if (plant.getCategory() == REED || plant.getCategory() == REED_SEA || plant.getCategory() == TALL_REED || plant.getCategory() == TALL_REED_SEA) {
      return BlockUtils.isSand(state) || BlockUtils.isSoil(state) || state.getBlock() == Blocks.HARDENED_CLAY
             || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
    } else if (plant.getCategory() == WATER || plant.getCategory() == TALL_WATER || plant.getCategory() == EMERGENT_TALL_WATER
               || plant.getCategory() == WATER_SEA || plant.getCategory() == TALL_WATER_SEA || plant.getCategory() == EMERGENT_TALL_WATER_SEA
               || plant.getCategory() == CREEPING || plant.getCategory() == HANGING) {
      return BlockUtils.isSand(state) || BlockUtils.isSoilOrGravel(state) || BlockUtils.isSoil(state) || BlockUtils.isGround(state)
             || state.getBlock() == Blocks.HARDENED_CLAY || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
    }

    return BlockUtils.isSoil(state) || state.getBlock() == Blocks.HARDENED_CLAY || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
  }

  public double getGrowthRate(World world, BlockPos pos) {
    if (world.isRainingAt(pos)) {
      return ConfigTFC.General.MISC.plantGrowthRate * 5d;
    } else {
      return ConfigTFC.General.MISC.plantGrowthRate;
    }
  }

  int getDayPeriod() {
    return Calendar.CALENDAR_TIME.getHourOfDay() / (ICalendar.HOURS_IN_DAY / 4);
  }

  @Override
  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT; // Stacksize = 64
  }

  @Override
  public @NotNull Size getSize(ItemStack stack) {
    return Size.TINY; // Store anywhere
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    Block blockAt = worldIn.getBlockState(pos).getBlock();
    return blockAt.isReplaceable(worldIn, pos) && blockAt != this && soil.getBlock()
                                                                         .canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
  }

  @Override
  protected boolean canSustainBush(IBlockState state) {
    if (plant.isClayMarking()) {
      return BlockUtils.isClay(state) || isValidSoil(state);
    } else {
      return isValidSoil(state);
    }
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }

    if (plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) &&
        plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
        if (j < 3) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j + 1));
        }
        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    } else if (!plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) ||
               !plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
      int j = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
        if (j > 0) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, j - 1));
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
      return soil.getBlock()
                 .canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) &&
             plant.isValidTemp(Climate.getActualTemp(worldIn, pos)) && plant.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    }
    return this.canSustainBush(soil);
  }

  @SuppressWarnings("deprecation")
  @Override
  @NotNull
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
  @NotNull
  public EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
    return plant.getCategory().getEnumPlantType();
  }

  @NotNull
  public PlantType.EnumType getEnumPlantType() {
    return plant.getEnumPlantType();
  }
}
