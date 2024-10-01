package net.dries007.tfc.objects.blocks.plants;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.data.lib.MCDate.Month;
import su.terrafirmagreg.modules.core.capabilities.chunkdata.ProviderChunkData;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

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
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.types.DefaultPlants;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.agriculture.CropTFCF;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.climate.Climate;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BlockPlant extends BlockBush implements ICapabilitySize {

  public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
  public static final PropertyInteger DAYPERIOD = PropertyInteger.create("dayperiod", 0, 3);
  private static final AxisAlignedBB PLANT_AABB = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
  private static final Map<Plant, BlockPlant> MAP = new HashMap<>();
  public final PropertyInteger growthStageProperty;
  protected final Plant plant;
  protected final BlockStateContainer blockState;

  public BlockPlant(Plant plant) {
    super(plant.getMaterial());
    if (MAP.put(plant, this) != null) {
      throw new IllegalStateException("There can only be one.");
    } else {
      plant.getOreDictName().ifPresent((name) -> {
        OreDictionaryHelper.register(this, name);
      });
      this.plant = plant;
      this.growthStageProperty = PropertyInteger.create("stage", 0, plant.getNumStages());
      this.setTickRandomly(true);
      this.setSoundType(SoundType.PLANT);
      this.setHardness(0.0F);
      BlockUtils.setFireInfo(this, 5, 20);
      this.blockState = this.createPlantBlockState();
      this.setDefaultState(this.blockState.getBaseState());
    }
  }

  public static BlockPlant get(Plant plant) {
    return MAP.get(plant);
  }

  @NotNull
  protected BlockStateContainer createPlantBlockState() {
    return new BlockStateContainer(this, this.growthStageProperty, DAYPERIOD, AGE);
  }

  @NotNull
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState().withProperty(AGE, meta);
  }

  public int getMetaFromState(IBlockState state) {
    return state.getValue(AGE);
  }

  @NotNull
  public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
    return state.withProperty(DAYPERIOD, this.getDayPeriod())
                .withProperty(this.growthStageProperty, this.plant.getStageForMonth());
  }

  public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (worldIn.isAreaLoaded(pos, 1)) {
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
      double modifier = 0.25 * (double) (4 - state.getValue(AGE));
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
        this.plant.getPlantType() != Plant.PlantType.SHORT_GRASS &&
        this.plant.getPlantType() != Plant.PlantType.TALL_GRASS) {

      if (this.plant == TFCRegistries.PLANTS.getValue(DefaultPlants.BLUE_GINGER)) {
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

  public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player) {
    ItemStack stack = player.getHeldItemMainhand();
    IBlockState state = world.getBlockState(pos);
    switch (this.plant.getPlantType()) {
      case REED:
      case REED_SEA:
      case TALL_REED:
      case TALL_REED_SEA:
      case SHORT_GRASS:
      case TALL_GRASS:
        return stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem()
                                                                                            .getHarvestLevel(stack, "scythe", player, state) != -1;
      default:
        return true;
    }
  }

  private boolean isValidSoil(IBlockState state) {
    return switch (this.plant.getPlantType()) {
      case REED, REED_SEA, TALL_REED, TALL_REED_SEA -> BlockUtils.isSand(state) || BlockUtils.isSoil(state) || state.getBlock() == Blocks.HARDENED_CLAY ||
                                                       state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      case SHORT_GRASS, TALL_GRASS, FLOATING, FLOATING_SEA, MUSHROOM, CACTUS, DESERT, DESERT_TALL_PLANT ->
        BlockUtils.isSand(state) || state.getBlock() == Blocks.HARDENED_CLAY || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      case DRY, DRY_TALL_PLANT -> BlockUtils.isSand(state) || BlockUtils.isDryGrass(state) ||
                                  state.getBlock() == Blocks.HARDENED_CLAY || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      case WATER, TALL_WATER, EMERGENT_TALL_WATER, WATER_SEA, TALL_WATER_SEA, EMERGENT_TALL_WATER_SEA ->
        BlockUtils.isSand(state) || BlockUtils.isSoilOrGravel(state) || BlockUtils.isSoil(state) || BlockUtils.isGround(state) ||
        state.getBlock() == Blocks.HARDENED_CLAY || state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
      default -> BlockUtils.isSoil(state) || state.getBlock() == Blocks.HARDENED_CLAY ||
                 state.getBlock() == Blocks.STAINED_HARDENED_CLAY;
    };
  }

  public double getGrowthRate(World world, BlockPos pos) {
    return world.isRainingAt(pos) ? ConfigTFC.General.MISC.plantGrowthRate * 5.0 : ConfigTFC.General.MISC.plantGrowthRate;
  }

  int getDayPeriod() {
    return Calendar.CALENDAR_TIME.getHourOfDay() / 6;
  }

  public Plant getPlant() {
    return this.plant;
  }

  public @NotNull Weight getWeight(ItemStack stack) {
    return Weight.VERY_LIGHT;
  }

  public @NotNull Size getSize(ItemStack stack) {
    return Size.TINY;
  }

  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState soil = worldIn.getBlockState(pos.down());
    Block blockAt = worldIn.getBlockState(pos).getBlock();
    return blockAt.isReplaceable(worldIn, pos) && blockAt != this && soil.getBlock()
                                                                         .canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
  }

  protected boolean canSustainBush(IBlockState state) {
    if (!this.plant.getIsClayMarking()) {
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
        j = state.getValue(AGE);
        if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
          if (j < 3) {
            worldIn.setBlockState(pos, state.withProperty(AGE, j + 1));
          }

          ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
        }
      } else if (!this.plant.isValidGrowthTemp(Climate.getActualTemp(worldIn, pos)) ||
                 !this.plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
        j = state.getValue(AGE);
        if (rand.nextDouble() < this.getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
          if (j > 0) {
            worldIn.setBlockState(pos, state.withProperty(AGE, j - 1));
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

  @NotNull
  public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
    return switch (this.plant.getPlantType()) {
      case CACTUS, DESERT, DESERT_TALL_PLANT -> EnumPlantType.Desert;
      case FLOATING, FLOATING_SEA -> EnumPlantType.Water;
      case MUSHROOM -> EnumPlantType.Cave;
      default -> EnumPlantType.Plains;
    };
  }

  @NotNull
  public Plant.EnumPlantTypeTFC getPlantTypeTFC() {
    return this.plant.getEnumPlantTypeTFC();
  }
}
