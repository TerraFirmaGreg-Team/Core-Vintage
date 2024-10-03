package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.api.base.block.BaseBlockBush;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.data.lib.MCDate.Month;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.plant.api.types.type.PlantTypes;

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

import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.util.agriculture.CropTFCF;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.climate.Climate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
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

public class BlockPlant extends BaseBlockBush {


  private static final AxisAlignedBB PLANT_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);

  private static final Map<PlantType, BlockPlant> MAP = new HashMap<>();

  public final PropertyInteger growthStageProperty;
  @Getter
  protected final PlantType plant;
  protected final BlockStateContainer blockState;

  public BlockPlant(PlantType plant) {
    super(Settings.of(plant.getMaterial()));

    this.plant = plant;

    getSettings()
      .weight(Weight.VERY_LIGHT)
      .sound(SoundType.PLANT)
      .size(Size.TINY)
      .hardness(0.0F)
      .oreDict(plant.getOreDictName())
      .randomTicks()
      .replaceable();

    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    }

    this.growthStageProperty = PropertyInteger.create("stage", 0, plant.getNumStages());

    this.blockState = this.createPlantBlockState();
    this.setDefaultState(this.blockState.getBaseState());

    BlockUtils.setFireInfo(this, 5, 20);
  }

  public static BlockPlant get(PlantType plant) {
    return MAP.get(plant);
  }

  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, this.growthStageProperty, DAYPERIOD, AGE_4);
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
    return state.withProperty(DAYPERIOD, this.getDayPeriod())
                .withProperty(this.growthStageProperty, this.plant.getStageForMonth());
  }

  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (!worldIn.isAreaLoaded(pos, 1)) {
      return;
    }
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(this.growthStageProperty);
    int expectedStage = this.plant.getStageForMonth(currentMonth);
    int currentTime = state.getValue(DAYPERIOD);
    int expectedTime = this.getDayPeriod();
    if (currentTime != expectedTime) {
      worldIn.setBlockState(pos, state.withProperty(DAYPERIOD, expectedTime)
                                      .withProperty(this.growthStageProperty, currentStage));
    }

    if (currentStage != expectedStage && random.nextDouble() < 0.5) {
      worldIn.setBlockState(pos, state.withProperty(DAYPERIOD, expectedTime)
                                      .withProperty(this.growthStageProperty, expectedStage));
    }

    this.updateTick(worldIn, pos, state, random);

  }

  public int tickRate(World worldIn) {
    return 10;
  }

  public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
    world.setBlockState(pos, state.withProperty(DAYPERIOD, this.getDayPeriod())
                                  .withProperty(this.growthStageProperty, this.plant.getStageForMonth()));
    this.checkAndDropBlock(world, pos, state);
  }

  @NotNull
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return !this.plant.getOreDictName().isPresent() ? Items.AIR : Item.getItemFromBlock(this);
  }

  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    if (!(entityIn instanceof EntityPlayer) || !((EntityPlayer) entityIn).isCreative()) {
      double modifier = 0.25 * (double) (4 - state.getValue(AGE_4));
      modifier += (1.0 - modifier) * this.plant.getMovementMod();
      if (modifier < ConfigTFC.General.MISC.minimumPlantMovementModifier) {
        modifier = ConfigTFC.General.MISC.minimumPlantMovementModifier;
      }

      entityIn.motionX *= modifier;
      entityIn.motionZ *= modifier;
    }

  }

  public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tile, ItemStack stack) {
    Month currentMonth = Calendar.CALENDAR_TIME.getMonthOfYear();
    int currentStage = state.getValue(this.growthStageProperty);
    int expectedStage = this.plant.getStageForMonth(currentMonth);
    if (!this.plant.getOreDictName().isPresent() &&
        !worldIn.isRemote &&
        (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 ||
         stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1) &&
        this.plant.getCategory() != SHORT_GRASS &&
        this.plant.getCategory() != TALL_GRASS) {

      if (this.plant == PlantTypes.BLUE_GINGER) {
        int chance;
        if (currentStage != 0 && expectedStage != 0) {
          chance = MathConstants.RNG.nextInt(2);
          if (chance == 0) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(CropTFCF.GINGER), MathConstants.RNG.nextInt(2)));
          }
        } else {
          chance = MathConstants.RNG.nextInt(2);
          if (chance == 0) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemsTFCF.GINGER, 1 + MathConstants.RNG.nextInt(2)));
            spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(CropTFCF.GINGER), MathConstants.RNG.nextInt(2)));
          } else if (chance == 1) {
            spawnAsEntity(worldIn, pos, new ItemStack(ItemSeedsTFC.get(CropTFCF.GINGER), 1 + MathConstants.RNG.nextInt(2)));
          }
        }
      } else {
        spawnAsEntity(worldIn, pos, new ItemStack(this, 1));
      }
    }

    super.harvestBlock(worldIn, player, pos, state, tile, stack);
  }

  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    if (!this.canBlockStay(worldIn, pos, state) && placer instanceof EntityPlayer && !((EntityPlayer) placer).isCreative() &&
        !this.plant.getOreDictName().isPresent()) {
      spawnAsEntity(worldIn, pos, new ItemStack(this));
    }

  }

  @NotNull
  public BlockStateContainer getBlockState() {
    return this.blockState;
  }

  @NotNull
  public Block.EnumOffsetType getOffsetType() {
    return EnumOffsetType.XYZ;
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
    return world.isRainingAt(pos) ? ConfigTFC.General.MISC.plantGrowthRate * 5.0 : ConfigTFC.General.MISC.plantGrowthRate;
  }

  int getDayPeriod() {
    return Calendar.CALENDAR_TIME.getHourOfDay() / 6;
  }

  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    Block blockAt = worldIn.getBlockState(pos).getBlock();
    return blockAt.isReplaceable(worldIn, pos) && blockAt != this && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
  }

  protected boolean canSustainBush(IBlockState state) {
    if (!this.plant.isClayMarking()) {
      return this.isValidSoil(state);
    } else {
      return BlockUtils.isClay(state) || this.isValidSoil(state);
    }
  }

  public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    if (worldIn.isAreaLoaded(pos, 1)) {
      int j;
      if (this.plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) &&
          this.plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
        j = state.getValue(AGE_4);
        if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
          if (j < 3) {
            worldIn.setBlockState(pos, state.withProperty(AGE_4, j + 1));
          }

          ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        }
      } else if (!this.plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) ||
                 !this.plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
        j = state.getValue(AGE_4);
        if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
          if (j > 0) {
            worldIn.setBlockState(pos, state.withProperty(AGE_4, j - 1));
          }

          ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        }
      }

      this.checkAndDropBlock(worldIn, pos, state);
    }
  }

  public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    if (state.getBlock() != this) {
      return this.canSustainBush(soil);
    } else {
      return soil.getBlock()
                 .canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this) &&
             this.plant.isValidTemp(Climate.getActualTemp(worldIn, pos)) &&
             this.plant.isValidRain(ProviderChunkData.getRainfall(worldIn, pos));
    }
  }

  @NotNull
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return PLANT_AABB.offset(state.getOffset(source, pos));
  }

  @Nullable
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return this.plant.getMovementMod() == 0.0 ? blockState.getBoundingBox(worldIn, pos) : NULL_AABB;
  }

  @Override
  @NotNull
  public EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
    return plant.getCategory().getEnumPlantType();
  }

  @NotNull
  public PlantType.EnumType getEnumPlantType() {
    return this.plant.getEnumPlantType();
  }
}
