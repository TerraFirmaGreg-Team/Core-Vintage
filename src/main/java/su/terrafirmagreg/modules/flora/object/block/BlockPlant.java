package su.terrafirmagreg.modules.flora.object.block;

import su.terrafirmagreg.api.base.block.BaseBlockBush;
import su.terrafirmagreg.api.helper.BlockHelper;
import su.terrafirmagreg.api.library.MCDate.Month;
import su.terrafirmagreg.api.library.types.category.Category;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendar;
import su.terrafirmagreg.modules.core.feature.climate.Climate;
import su.terrafirmagreg.modules.flora.api.types.type.FloraType;
import su.terrafirmagreg.modules.flora.api.types.variant.block.FloraBlockVariant;
import su.terrafirmagreg.modules.flora.api.types.variant.block.IFloraBlock;

import net.minecraft.block.Block;
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

import git.jbredwards.fluidlogged_api.api.block.IFluidloggable;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.util.agriculture.CropTFCF;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.IntProp.AGE_4;
import static su.terrafirmagreg.api.data.Properties.IntProp.DAYPERIOD;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.CACTUS;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.CREEPING;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.DESERT;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.DESERT_TALL_PLANT;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.DRY;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.DRY_TALL_PLANT;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.EMERGENT_TALL_WATER;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.EMERGENT_TALL_WATER_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.HANGING;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.REED;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.REED_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.SHORT_GRASS;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_GRASS;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_REED;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_REED_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_WATER;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.TALL_WATER_SEA;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.WATER;
import static su.terrafirmagreg.modules.flora.api.types.category.FloraCategories.WATER_SEA;
import static su.terrafirmagreg.modules.flora.api.types.type.FloraTypes.BLUE_GINGER;
import static su.terrafirmagreg.modules.rock.init.BlocksRock.SAND;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DRY_GRASS;

@Getter
public class BlockPlant extends BaseBlockBush implements IFloraBlock, IFluidloggable {

  private static final AxisAlignedBB PLANT_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);


  protected final PropertyInteger STAGE;


  protected final FloraType type;
  protected final FloraBlockVariant variant;

  public BlockPlant(FloraBlockVariant variant, FloraType type) {
    super(Settings.of(type.getMaterial()));

    this.type = type;
    this.variant = variant;

    getSettings()
      .registryKey(variant.getRegistryKey(type))
      .weight(Weight.VERY_LIGHT)
      .sound(SoundType.PLANT)
      .size(Size.TINY)
      .hardness(0.0F)
      .oreDict(type.getOreDictName())
      .randomTicks()
      .replaceable();

    this.STAGE = PropertyInteger.create("stage", 0, type.getNumStages());

    this.blockState = this.createPlantBlockState();
    this.setDefaultState(this.blockState.getBaseState());

    BlockUtils.setFireInfo(this, 5, 20);
  }

  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, STAGE, DAYPERIOD, AGE_4);
  }

  @NotNull
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(AGE_4, meta);
  }

  public int getMetaFromState(IBlockState state) {
    return state.getValue(AGE_4);
  }

  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return state.withProperty(DAYPERIOD, getDayPeriod()).withProperty(STAGE, type.getStageForMonth());
  }

  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(this.STAGE);
    int expectedStage = type.getStageForMonth(currentMonth);
    int currentTime = state.getValue(DAYPERIOD);
    int expectedTime = this.getDayPeriod();

    if (currentTime != expectedTime) {
      worldIn.setBlockState(pos, state.withProperty(DAYPERIOD, expectedTime)
                                      .withProperty(this.STAGE, currentStage));
    }

    if (currentStage != expectedStage && random.nextDouble() < 0.5) {
      worldIn.setBlockState(pos, state.withProperty(DAYPERIOD, expectedTime)
                                      .withProperty(this.STAGE, expectedStage));
    }

    this.updateTick(worldIn, pos, state, random);

  }

  public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    world.setBlockState(pos, state.withProperty(DAYPERIOD, this.getDayPeriod())
                                  .withProperty(this.STAGE, type.getStageForMonth()));
    this.checkAndDropBlock(world, pos, state);
  }

  @NotNull
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return !type.getOreDictName().isPresent() ? Items.AIR : Item.getItemFromBlock(this);
  }

  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    if (!(entityIn instanceof EntityPlayer entityPlayer) || !entityPlayer.isCreative()) {
      double modifier = 0.25 * (double) (4 - state.getValue(AGE_4));
      modifier += (1.0 - modifier) * type.getMovementMod();
      if (modifier < ConfigTFC.General.MISC.minimumPlantMovementModifier) {
        modifier = ConfigTFC.General.MISC.minimumPlantMovementModifier;
      }

      entityIn.motionX *= modifier;
      entityIn.motionZ *= modifier;
    }

  }

  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tile, ItemStack stack) {
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(this.STAGE);
    int expectedStage = type.getStageForMonth(currentMonth);

    if (!type.getOreDictName().isPresent() && !worldIn.isRemote && (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1
                                                                    || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1)
        && !Category.isCategory(type.getCategory(), SHORT_GRASS, TALL_GRASS)) {

      if (Type.isType(type, BLUE_GINGER)) {
        int chance;
        if (currentStage != 0 && expectedStage != 0) {
          chance = MathUtils.RNG.nextInt(2);
          if (chance == 0) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(CropTFCF.GINGER), MathUtils.RNG.nextInt(2)));
          }
        } else {
          chance = MathUtils.RNG.nextInt(2);
          if (chance == 0) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsTFCF.GINGER, 1 + MathUtils.RNG.nextInt(2)));
            spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(CropTFCF.GINGER), MathUtils.RNG.nextInt(2)));
          } else {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(CropTFCF.GINGER), 1 + MathUtils.RNG.nextInt(2)));
          }
        }
      } else {
        spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
      }
    }

    super.harvestBlock(worldIn, player, pos, state, tile, stack);
  }

  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    if (this.canBlockStay(worldIn, pos, state)) {return;}
    if (placer instanceof EntityPlayer entityPlayer && entityPlayer.isCreative()) {return;}
    if (type.getOreDictName().isPresent()) {return;}

    spawnAsEntity(worldIn, pos, new ItemStack(this));


  }

  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return EnumOffsetType.XYZ;
  }

  @Override
  public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
    ItemStack stack = player.getHeldItemMainhand();
    IBlockState state = world.getBlockState(pos);
    if (Category.isCategory(type.getCategory(), REED, REED_SEA, TALL_REED, TALL_REED_SEA, SHORT_GRASS, TALL_GRASS)) {
      return (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1);

    }
    return true;
  }

  private boolean isValidSoil(IBlockState state) {
    if (Category.isCategory(type.getCategory(), CACTUS, DESERT, DESERT_TALL_PLANT)) {
      return Variant.isVariant(state, SAND) || BlockUtils.isBlock(state.getBlock(), Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY);

    } else if (Category.isCategory(type.getCategory(), DRY, DRY_TALL_PLANT)) {
      return Variant.isVariant(state, SAND, DRY_GRASS) || BlockUtils.isBlock(state.getBlock(), Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY);

    } else if (Category.isCategory(type.getCategory(), REED, REED_SEA, TALL_REED, TALL_REED_SEA)) {
      return Variant.isVariant(state, SAND) || BlockHelper.isSoil(state)
             || BlockUtils.isBlock(state.getBlock(), Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY);

    } else if (Category.isCategory(type.getCategory(), WATER, TALL_WATER, EMERGENT_TALL_WATER, WATER_SEA, TALL_WATER_SEA, EMERGENT_TALL_WATER_SEA, CREEPING, HANGING)) {
      return Variant.isVariant(state, SAND) || BlockHelper.isSoilOrGravel(state) || BlockHelper.isSoil(state) || BlockHelper.isGround(state)
             || BlockUtils.isBlock(state.getBlock(), Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY);
    }

    return BlockHelper.isSoil(state) || BlockUtils.isBlock(state.getBlock(), Blocks.HARDENED_CLAY, Blocks.STAINED_HARDENED_CLAY);
  }

  public double getGrowthRate(World world, BlockPos pos) {
    return world.isRainingAt(pos) ? ConfigTFC.General.MISC.plantGrowthRate * 5D : ConfigTFC.General.MISC.plantGrowthRate;
  }

  int getDayPeriod() {
    return Calendar.CALENDAR_TIME.getHourOfDay() / (ICalendar.HOURS_IN_DAY / 4);
  }

  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos).getBlock() != this && super.canPlaceBlockAt(worldIn, pos);
  }

  @Override
  protected boolean canSustainBush(IBlockState state) {
    return type.isClayMarking() ? BlockHelper.isClay(state) || isValidSoil(state) : isValidSoil(state);
  }

  @Override
  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (!worldIn.isAreaLoaded(pos, 1)) {return;}
    int age;
    if (type.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) &&
        type.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {

      age = state.getValue(AGE_4);

      if (rand.nextDouble() < getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
        if (age < 3) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, age + 1));
        }

        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    } else if (!type.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) || !type.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
      age = state.getValue(AGE_4);
      if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
        if (age > 0) {
          worldIn.setBlockState(pos, state.withProperty(AGE_4, age - 1));
        }

        ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
      }
    }

    checkAndDropBlock(worldIn, pos, state);

  }

  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    if (state.getBlock() == this) {
      return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this) &&
             type.isValidTemp(Climate.getActualTemp(worldIn, pos)) &&
             type.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    }

    return this.canSustainBush(soil);
  }

  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return PLANT_AABB.offset(state.getOffset(source, pos));
  }

  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return type.getMovementMod() == 0.0 ? blockState.getBoundingBox(worldIn, pos) : NULL_AABB;
  }

  @Override
  @NotNull
  public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
    return type.getCategory().getEnumPlantType();
  }

  @NotNull
  public FloraType.EnumType getEnumPlantType() {
    return type.getEnumPlantType();
  }
}
