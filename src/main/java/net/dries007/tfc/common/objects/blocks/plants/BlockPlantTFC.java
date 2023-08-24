package net.dries007.tfc.common.objects.blocks.plants;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.plant.IPlantBlock;
import net.dries007.tfc.api.types.plant.type.PlantType;
import net.dries007.tfc.api.types.plant.variant.PlantBlockVariant;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@ParametersAreNonnullByDefault
public class BlockPlantTFC extends BlockBush implements IPlantBlock, IItemSize {
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
    /*
     * Time of day, used for rendering plants that bloom at different times
     * 0 = midnight-dawn
     * 1 = dawn-noon
     * 2 = noon-dusk
     * 3 = dusk-midnight
     */
    public static final PropertyInteger DAYPERIOD = PropertyInteger.create("dayperiod", 0, 3);
    private static final AxisAlignedBB PLANT_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
    /* Growth Stage of the plant, tied to the month of year */
    public final PropertyInteger growthStageProperty;
    protected final BlockStateContainer blockState;
    private final PlantType plant;
    private final PlantBlockVariant plantBlockVariant;
    private final ResourceLocation modelLocation;

    public BlockPlantTFC(PlantBlockVariant plantBlockVariant, PlantType plant) {
        super(plant.getMaterial());

        this.plant = plant;
        this.plantBlockVariant = plantBlockVariant;
        this.modelLocation = TerraFirmaCraft.identifier("plants/" + plant);
        var blockRegistryName = String.format("plants/%s/%s", plantBlockVariant, plant);

        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
        setCreativeTab(CreativeTabsTFC.FLORA);
        setTickRandomly(true);
        setSoundType(SoundType.PLANT);
        setHardness(0.0F);
        growthStageProperty = PropertyInteger.create("stage", 0, plant.getNumStages());
        blockState = this.createPlantBlockState();
        setDefaultState(this.blockState.getBaseState());

        plant.getOreDictName().ifPresent(name -> OreDictionaryHelper.register(this, name));
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }

    @Nonnull
    @Override
    public String getTranslationKey() {
        return "tile.tfc.plants." + plant.toString();
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
        if (!worldIn.isAreaLoaded(pos, 1)) return;
        Month currentMonth = CalendarTFC.CALENDAR_TIME.getMonthOfYear();
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
        if (!plant.getOreDictName().isPresent() &&
                !worldIn.isRemote && (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1) &&
                plant.getPlantVariant() != PlantBlockVariant.SHORT_GRASS && plant.getPlantVariant() != PlantBlockVariant.TALL_GRASS) {
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
        return switch (plant.getPlantVariant()) {
            case REED, REED_SEA, TALL_REED, TALL_REED_SEA, SHORT_GRASS, TALL_GRASS ->
                    (stack.getItem().getHarvestLevel(stack, "knife", player, state) != -1 || stack.getItem().getHarvestLevel(stack, "scythe", player, state) != -1);
            default -> true;
        };
    }

    public PlantType getPlant() {
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
        if (world.isRainingAt(pos)) return ConfigTFC.General.MISC.plantGrowthRate * 5d;
        else return ConfigTFC.General.MISC.plantGrowthRate;
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState soil = worldIn.getBlockState(pos.down());
        Block blockAt = worldIn.getBlockState(pos).getBlock();
        return blockAt.isReplaceable(worldIn, pos) && blockAt != this && soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), EnumFacing.UP, this);
    }

    @Override
    protected boolean canSustainBush(IBlockState state) {
        if (plant.isClayMarking()) return TFCBlocks.isClay(state) || isValidSoil(state);
        else return isValidSoil(state);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        if (!worldIn.isAreaLoaded(pos, 1)) return;

        if (plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidSunlight(Math.subtractExact(worldIn.getLightFor(EnumSkyBlock.SKY, pos), worldIn.getSkylightSubtracted()))) {
            int j = state.getValue(AGE);

            if (rand.nextDouble() < getGrowthRate(worldIn, pos) && ForgeHooks.onCropsGrowPre(worldIn, pos.up(), state, true)) {
                if (j < 3) {
                    worldIn.setBlockState(pos, state.withProperty(AGE, j + 1));
                }
                ForgeHooks.onCropsGrowPost(worldIn, pos, state, worldIn.getBlockState(pos));
            }
        } else if (!plant.isValidGrowthTemp(ClimateTFC.getActualTemp(worldIn, pos)) || !plant.isValidSunlight(worldIn.getLightFor(EnumSkyBlock.SKY, pos))) {
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
            return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this) && plant.isValidTemp(ClimateTFC.getActualTemp(worldIn, pos)) && plant.isValidRain(ChunkDataTFC.getRainfall(worldIn, pos));
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
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return switch (plant.getPlantVariant()) {
            case CACTUS, DESERT, DESERT_TALL_PLANT -> net.minecraftforge.common.EnumPlantType.Desert;
            case FLOATING, FLOATING_SEA -> net.minecraftforge.common.EnumPlantType.Water;
            case MUSHROOM -> net.minecraftforge.common.EnumPlantType.Cave;
            default -> net.minecraftforge.common.EnumPlantType.Plains;
        };
    }

    @Nonnull
    public PlantType.EnumPlantType getEnumType() {
        return plant.getEnumPlantType();
    }

    @Nonnull
    protected BlockStateContainer createPlantBlockState() {
        return new BlockStateContainer(this, growthStageProperty, DAYPERIOD, AGE);
    }

    int getDayPeriod() {
        return CalendarTFC.CALENDAR_TIME.getHourOfDay() / (ICalendar.HOURS_IN_DAY / 4);
    }

    private boolean isValidSoil(IBlockState state) {
        return switch (plant.getPlantVariant()) {
            case CACTUS, DESERT, DESERT_TALL_PLANT -> TFCBlocks.isSand(state);
            case DRY, DRY_TALL_PLANT -> TFCBlocks.isSand(state) || TFCBlocks.isDryGrass(state);
            case REED, REED_SEA, TALL_REED, TALL_REED_SEA -> TFCBlocks.isSand(state) || TFCBlocks.isSoil(state);
            case WATER, TALL_WATER, EMERGENT_TALL_WATER -> TFCBlocks.isSoilOrGravel(state);
            case WATER_SEA, TALL_WATER_SEA, EMERGENT_TALL_WATER_SEA ->
                    TFCBlocks.isSand(state) || TFCBlocks.isSoilOrGravel(state);
            default -> TFCBlocks.isSoil(state);
        };
    }

    @Override
    public PlantBlockVariant getBlockVariant() {
        return plantBlockVariant;
    }

    @Override
    public PlantType getType() {
        return plant;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(modelLocation, "stage=" + plant.getNumStages());
            }
        });

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(modelLocation.toString()));
    }
}
