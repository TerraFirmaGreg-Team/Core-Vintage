package net.dries007.tfc.module.crop.common.blocks;

import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.module.crop.api.type.CropType;
import net.dries007.tfc.module.crop.api.variant.block.CropBlockVariant;
import net.dries007.tfc.module.crop.api.variant.block.ICropBlock;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.module.crop.common.CropStorage;
import net.dries007.tfc.module.soil.common.blocks.BlockSoilFarmland;
import net.dries007.tfc.module.crop.common.tileentities.TECropBase;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.util.skills.SkillType;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

import static net.dries007.tfc.module.crop.api.category.CropCategories.PICKABLE;
import static net.dries007.tfc.module.crop.api.variant.block.CropBlockVariants.DEAD;


public class BlockCropGrowing extends BlockCrops implements IGrowingPlant, IPlantable, ICropBlock {

    // true, если культура появилась в дикой природе, это означает, что она игнорирует условия роста, например, пашня
    public static final PropertyBool WILD = PropertyBool.create("wild");
    private static final int MATURE_AGE = 7;
    // Свойства стадии роста
    public static final PropertyInteger AGE = PropertyInteger.create("age", 0, MATURE_AGE);
    // Модельные коробки
    private static final AxisAlignedBB[] CROPS_AABB = new AxisAlignedBB[]{
            new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.125D, 0.875D),
            new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.25D, 0.875D),
            new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.375D, 0.875D),
            new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.5D, 0.875D),
            new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.625D, 0.875D),
            new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.75D, 0.875D),
            new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.875D, 0.875D),
            new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D)
    };

    // Бинарные флаги для преобразования состояния и метаданных
    private static final int META_WILD = 8;
    private static final int META_GROWTH = 7;

    private final CropBlockVariant variant;
    private final CropType type;

    public BlockCropGrowing(CropBlockVariant variant, CropType type) {
        super();

        this.variant = variant;
        this.type = type;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.FLORA);
        setSoundType(SoundType.PLANT);
        setHardness(0.6f);

        setDefaultState(blockState.getBaseState()
                .withProperty(getAgeProperty(), 0)
                .withProperty(WILD, false));
    }

    public static int getMatureAge() {
        return MATURE_AGE;
    }

    @Override
    public Item getSeed() {
        return type.getDropSeed().getItem();
    }

    @Override
    public Item getCrop() {
        return type.getDropFood().getItem();
    }

    @Nonnull
    @Override
    public CropType getType() {
        return type;
    }

    @Nonnull
    @Override
    public CropBlockVariant getBlockVariant() {
        return variant;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return null;
    }

    /**
     * Возвращает состояние блока на основе его значения метаданных.
     *
     * @param meta Значение метаданных блока.
     * @return Состояние блока.
     */
    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(WILD, (meta & META_WILD) > 0)
                .withProperty(getAgeProperty(), meta & META_GROWTH);
    }

    /**
     * Возвращает значение метаданных блока на основе его состояния.
     *
     * @param state Состояние блока.
     * @return Значение метаданных блока.
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(getAgeProperty()) + (state.getValue(WILD) ? META_WILD : 0);
    }

    /**
     * Выполняет случайное обновление блока.
     *
     * @param worldIn Мир, в котором находится блок.
     * @param pos     Позиция блока.
     * @param state   Состояние блока.
     * @param random  Генератор случайных чисел.
     */
    @Override
    public void randomTick(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random random) {
        super.updateTick(worldIn, pos, state, random);
        checkGrowth(worldIn, pos, state, random);
    }

    /**
     * Вызывается при добавлении блока в мир.
     *
     * @param worldIn Мир, в котором находится блок.
     * @param pos     Позиция блока.
     * @param state   Состояние блока.
     */
    @Override
    public void onBlockAdded(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        TECropBase tile = Helpers.getTE(worldIn, pos, TECropBase.class);
        if (tile != null) {
            tile.resetCounter();
        }
    }

    /**
     * Создает контейнер состояния блока.
     *
     * @return Контейнер состояния блока.
     */
    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, AGE, WILD);
    }

    /**
     * Возвращает тип смещения блока.
     *
     * @return Тип смещения блока.
     */
    @Override
    @Nonnull
    public EnumOffsetType getOffsetType() {
        return EnumOffsetType.XZ;
    }

    /**
     * Проверяет, имеет ли блок тайл-энтити.
     *
     * @param state Состояние блока.
     * @return {@code true}, если блок имеет тайл-энтити, иначе {@code false}.
     */
    @Override
    public boolean hasTileEntity(@Nonnull IBlockState state) {
        return true;
    }

    /**
     * Создает тайл-энтити для блока.
     *
     * @param world Мир, в котором находится блок.
     * @param state Состояние блока.
     * @return Тайл-энтити блока.
     */
    @Nullable
    @Override
    public TileEntity createTileEntity(@Nonnull World world, @Nonnull IBlockState state) {
        return new TECropBase();
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (!isMature(state)) {
            return getSeed();
        } else {
            return getCrop();
        }
    }

    /**
     * Метод, который добавляет предметы в список drops.
     *
     * @param drops   Список предметов, в который будут добавлены предметы.
     * @param world   Объект IBlockAccess, представляющий мир.
     * @param pos     Позиция блока в мире.
     * @param state   Состояние блока.
     * @param fortune Уровень фортуны.
     */
    @Override
    public void getDrops(@Nonnull NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, IBlockState state, int fortune) {
        var player = harvesters.get();
        var seedStack = new ItemStack(this.getSeed());
        var foodStack = new ItemStack(this.getCrop());

        // Если игрок и навыки присутствуют, обновляем навыки и увеличиваем количество предметов в зависимости от навыка
        if (player != null) {
            var skill = CapabilityPlayerData.getSkill(player, SkillType.AGRICULTURE);

            if (skill != null) {
                if (isMature(state)) {
                    foodStack.setCount(1 + CropType.getSkillFoodBonus(skill, RANDOM));
                    seedStack.setCount(1 + CropType.getSkillSeedBonus(skill, RANDOM));
                    skill.add(0.04f);
                }
            }
        }

        // Добавляем предметы в список drops
        if (isMature(state)) {
            drops.add(foodStack);
        }
        if (!seedStack.isEmpty()) {
            drops.add(seedStack);
        }
    }

    /**
     * Метод, который возвращает предмет, который будет получен при использовании правой кнопки мыши на блоке.
     *
     * @param state  Состояние блока.
     * @param target Объект RayTraceResult, представляющий результат трассировки луча.
     * @param world  Объект World, представляющий мир.
     * @param pos    Позиция блока в мире.
     * @param player Игрок, который использовал правую кнопку мыши.
     * @return Предмет, который будет получен при использовании правой кнопки мыши на блоке.
     */
    @Override
    @Nonnull
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(this.getSeed());
    }

    /**
     * Метод, который проверяет рост культуры.
     *
     * @param worldIn Объект World, представляющий мир.
     * @param pos     Позиция блока в мире.
     * @param state   Состояние блока.
     * @param random  Объект Random для генерации случайных чисел.
     */
    public void checkGrowth(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (!worldIn.isRemote) {
            TECropBase te = Helpers.getTE(worldIn, pos, TECropBase.class);
            if (te != null) {
                // Если нельзя видеть небо или почва не увлажнена, сбрасываем счетчик роста
                IBlockState stateFarmland = worldIn.getBlockState(pos.down());
                if (!state.getValue(WILD)) {
                    if (!worldIn.canSeeSky(pos) || (stateFarmland.getBlock() instanceof BlockSoilFarmland &&
                            stateFarmland.getValue(BlockSoilFarmland.MOISTURE) < 3)) {
                        te.resetCounter();
                        return;
                    }
                }

                long growthTicks = (long) (type.getGrowthTicks() * ConfigTFC.General.FOOD.cropGrowthTimeModifier);
                int fullGrownStages = 0;
                while (te.getTicksSinceUpdate() > growthTicks) {
                    te.reduceCounter(growthTicks);

                    // Получаем показатели температуры и осадков для времени, в которое культура должна была вырасти
                    float temp = ClimateTFC.getActualTemp(worldIn, pos, -te.getTicksSinceUpdate());
                    float rainfall = ChunkDataTFC.getRainfall(worldIn, pos);

                    // Проверяем, может ли культура вырасти, и если да, то растим ее
                    if (type.isValidForGrowth(temp, rainfall)) {
                        grow(worldIn, pos, state);
                        state = worldIn.getBlockState(pos);

                        // Проверяем, является ли блок культурным растением,
                        // не является ли он диким и достиг ли он максимального возраста
                        if (state.getBlock() instanceof BlockCropGrowing &&
                                !state.getValue(WILD) &&
                                state.getValue(getAgeProperty()) == getMaxAge()) {
                            fullGrownStages++;

                            // Если количество полностью выросших стадий превышает 2, уничтожаем растение
                            if (fullGrownStages > 2) {
                                die(worldIn, pos, state);
                                return;
                            }
                        }
                    } else if (!type.isValidConditions(temp, rainfall)) {
                        // Если условия для роста культуры недопустимы, уничтожаем растение
                        die(worldIn, pos, state);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Проверяет, может ли культура вырасти, и если да, то растит ее.
     *
     * @param worldIn Объект World, представляющий мир.
     * @param pos     Позиция блока в мире.
     * @param state   Состояние блока.
     */
    @Override
    public void grow(World worldIn, BlockPos pos, IBlockState state) {
        if (!worldIn.isRemote) {
            if (state.getValue(getAgeProperty()) < MATURE_AGE) {
                worldIn.setBlockState(pos, state.withProperty(getAgeProperty(), state.getValue(getAgeProperty()) + 1), 2);
            }
        }
    }

    /**
     * Уничтожает культуру, превращая ее в мертвый блок культуры.
     *
     * @param worldIn Объект World, представляющий мир.
     * @param pos     Позиция блока в мире.
     * @param state   Состояние блока.
     */
    public void die(World worldIn, BlockPos pos, IBlockState state) {
        // Проверяем, включена ли опция смерти культурных растений в конфигурации
        if (ConfigTFC.General.FOOD.enableCropDeath) {
            // Устанавливаем состояние блока на месте умирающего растения
            worldIn.setBlockState(pos, CropStorage.getCropBlock(DEAD, type).getDefaultState()
                    .withProperty(BlockCropDead.MATURE, state.getValue(getAgeProperty()) == MATURE_AGE));
        }
    }

    /**
     * Возвращает ограничивающий параллелепипед для данного состояния блока.
     *
     * @param state  Состояние блока.
     * @param source Источник доступа к блокам.
     * @param pos    Позиция блока в мире.
     * @return Ограничивающий параллелепипед для данного состояния блока.
     */
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, @Nonnull IBlockAccess source, @Nonnull BlockPos pos) {
        return CROPS_AABB[state.getValue(getAgeProperty())];
    }

    /**
     * Возвращает тип растения для данной культуры.
     *
     * @param world Мир.
     * @param pos   Позиция блока в мире.
     * @return Тип растения.
     */
    @Nonnull
    @Override
    public EnumPlantType getPlantType(@Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        return EnumPlantType.Crop;
    }

    /**
     * Возвращает свойство, представляющее стадию роста культуры.
     *
     * @return Свойство стадии роста.
     */
    @Override
    public PropertyInteger getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return getMatureAge();
    }

    public boolean isMature(IBlockState state) {
        return state.getValue(getAgeProperty()) >= MATURE_AGE;
    }

    @Override
    public boolean isMaxAge(IBlockState state) {
        return getAge(state) >= getMaxAge();
    }

    @Override
    public int getAge(IBlockState state) {
        return getMetaFromState(state);
    }

    /**
     * Возвращает статус роста культуры на основе ее состояния, температуры и осадков.
     *
     * @param state Состояние блока.
     * @param world Мир.
     * @param pos   Позиция блока в мире.
     * @return Статус роста культуры.
     */
    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
        float temp = ClimateTFC.getActualTemp(world, pos);
        float rainfall = ChunkDataTFC.getRainfall(world, pos);
        if (state.getValue(getAgeProperty()) >= MATURE_AGE) {
            return GrowthStatus.FULLY_GROWN;
        } else if (!type.isValidConditions(temp, rainfall) || !world.canSeeSky(pos)) {
            return GrowthStatus.NOT_GROWING;
        } else if (type.isValidForGrowth(temp, rainfall)) {
            return GrowthStatus.GROWING;
        }
        return GrowthStatus.CAN_GROW;
    }

    @Override
    public boolean onBlockActivated(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer playerIn, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (getType().getCropCategory() == PICKABLE) {

            if (isMature(state)) {
                var seedDrop = this.getType().getDropSeed();
                var foodDrop = this.getType().getDropFood();
                var skill = CapabilityPlayerData.getSkill(playerIn, SkillType.AGRICULTURE);

                if (skill != null) {
                    foodDrop.setCount(1 + CropType.getSkillFoodBonus(skill, RANDOM));
                    // omit the +1 because the plant stays alive.
                    seedDrop.setCount(CropType.getSkillSeedBonus(skill, RANDOM));
                }

                if (!worldIn.isRemote) {
                    worldIn.setBlockState(pos, state.withProperty(getAgeProperty(), state.getValue(getAgeProperty()) - 3));
                    ItemHandlerHelper.giveItemToPlayer(playerIn, foodDrop);
                    if (!seedDrop.isEmpty())
                        ItemHandlerHelper.giveItemToPlayer(playerIn, seedDrop);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {

        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder().customPath(getResourceLocation()).ignore(WILD).build());

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(), "normal"));
    }
}
