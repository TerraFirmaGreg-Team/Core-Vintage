package net.dries007.tfc.common.objects.blocks.agriculture.crop;

import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.types.crop.Crop;
import net.dries007.tfc.api.types.crop.ICrop;
import net.dries007.tfc.api.types.crop.ICropBlock;
import net.dries007.tfc.api.util.IGrowingPlant;
import net.dries007.tfc.common.objects.blocks.agriculture.crop_old.BlockCropDead;
import net.dries007.tfc.common.objects.blocks.soil.BlockSoilFarmland;
import net.dries007.tfc.common.objects.items.ItemSeedsTFC;
import net.dries007.tfc.common.objects.tileentities.TECropBase;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.util.skills.SimpleSkill;
import net.dries007.tfc.util.skills.SkillType;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@ParametersAreNonnullByDefault
public abstract class BlockCrop extends BlockBush implements IGrowingPlant, ICropBlock {

    // Свойства стадии роста
    public static final PropertyInteger STAGE_8 = PropertyInteger.create("stage", 0, 7);
    public static final PropertyInteger STAGE_7 = PropertyInteger.create("stage", 0, 6);
    public static final PropertyInteger STAGE_6 = PropertyInteger.create("stage", 0, 5);
    public static final PropertyInteger STAGE_5 = PropertyInteger.create("stage", 0, 4);

    // Статическое отображение для преобразования значения maxValue в свойство Stage
    public static final HashMap<Integer, PropertyInteger> STAGE_MAP = new HashMap<>();

    // true, если культура появилась в дикой природе, это означает, что она игнорирует условия роста, например, пашня
    public static final PropertyBool WILD = PropertyBool.create("wild");

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

    // Статическое поле для преобразования культуры в блок
    private static final Map<ICrop, BlockCrop> MAP = new HashMap<>();

    static {
        STAGE_MAP.put(5, STAGE_5);
        STAGE_MAP.put(6, STAGE_6);
        STAGE_MAP.put(7, STAGE_7);
        STAGE_MAP.put(8, STAGE_8);
    }

    private final ICrop crop;

    public BlockCrop(ICrop crop) {
        super(Material.PLANTS);

        this.crop = crop;
        if (MAP.put(crop, this) != null) {
            throw new IllegalStateException("Может быть только один экземпляр.");
        }

        setSoundType(SoundType.PLANT);
        setHardness(0.6f);
    }

    /**
     * Получить экземпляр BlockCrop, связанный с заданным ICrop.
     *
     * @param crop Экземпляр ICrop.
     * @return Экземпляр BlockCrop.
     */
    public static BlockCrop get(ICrop crop) {
        return MAP.get(crop);
    }

    /**
     * Получить набор всех зарегистрированных экземпляров ICrop.
     *
     * @return Набор экземпляров ICrop.
     */
    public static Set<ICrop> getCrops() {
        return MAP.keySet();
    }

    /**
     * Получить свойство стадии роста для заданного ICrop.
     *
     * @param crop Экземпляр ICrop.
     * @return Свойство стадии роста.
     */
    static PropertyInteger getStagePropertyForCrop(ICrop crop) {
        return STAGE_MAP.get(crop.getMaxStage() + 1);
    }

    /**
     * Возвращает состояние блока на основе его значения метаданных.
     *
     * @param meta Значение метаданных блока.
     * @return Состояние блока.
     */
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(WILD, (meta & META_WILD) > 0).withProperty(getStageProperty(), meta & META_GROWTH);
    }

    /**
     * Возвращает значение метаданных блока на основе его состояния.
     *
     * @param state Состояние блока.
     * @return Значение метаданных блока.
     */
    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(getStageProperty()) + (state.getValue(WILD) ? META_WILD : 0);
    }

    /**
     * Выполняет случайное обновление блока.
     *
     * @param worldIn Мир, в котором находится блок.
     * @param pos Позиция блока.
     * @param state Состояние блока.
     * @param random Генератор случайных чисел.
     */
    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        super.updateTick(worldIn, pos, state, random);
        checkGrowth(worldIn, pos, state, random);
    }

    /**
     * Вызывается при добавлении блока в мир.
     *
     * @param worldIn Мир, в котором находится блок.
     * @param pos Позиция блока.
     * @param state Состояние блока.
     */
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
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
        return new BlockStateContainer(this, getStageProperty(), WILD);
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
    public boolean hasTileEntity(IBlockState state) {
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
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TECropBase();
    }

    /**
     * Метод, который добавляет предметы в список drops.
     * @param drops Список предметов, в который будут добавлены предметы.
     * @param world Объект IBlockAccess, представляющий мир.
     * @param pos Позиция блока в мире.
     * @param state Состояние блока.
     * @param fortune Уровень фортуны.
     */
    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        EntityPlayer player = harvesters.get();
        ItemStack seedStack = new ItemStack(ItemSeedsTFC.get(crop));
        ItemStack foodStack = crop.getFoodDrop(state.getValue(getStageProperty()));

        // Если игрок и навыки присутствуют, обновляем навыки и увеличиваем количество предметов в зависимости от навыка
        if (player != null) {
            SimpleSkill skill = CapabilityPlayerData.getSkill(player, SkillType.AGRICULTURE);

            if (skill != null) {
                if (!foodStack.isEmpty()) {
                    foodStack.setCount(1 + Crop.getSkillFoodBonus(skill, RANDOM));
                    seedStack.setCount(1 + Crop.getSkillSeedBonus(skill, RANDOM));
                    skill.add(0.04f);
                }
            }
        }

        // Добавляем предметы в список drops
        if (!foodStack.isEmpty()) {
            drops.add(foodStack);
        }
        if (!seedStack.isEmpty()) {
            drops.add(seedStack);
        }
    }

    /**
     * Метод, который возвращает предмет, который будет получен при использовании правой кнопки мыши на блоке.
     * @param state Состояние блока.
     * @param target Объект RayTraceResult, представляющий результат трассировки луча.
     * @param world Объект World, представляющий мир.
     * @param pos Позиция блока в мире.
     * @param player Игрок, который использовал правую кнопку мыши.
     * @return Предмет, который будет получен при использовании правой кнопки мыши на блоке.
     */
    @Override
    @Nonnull
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(ItemSeedsTFC.get(crop));
    }

    /**
     * Метод, который проверяет рост культуры.
     * @param worldIn Объект World, представляющий мир.
     * @param pos Позиция блока в мире.
     * @param state Состояние блока.
     * @param random Объект Random для генерации случайных чисел.
     */
    public void checkGrowth(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (!worldIn.isRemote) {
            TECropBase te = Helpers.getTE(worldIn, pos, TECropBase.class);
            if (te != null) {
                // Если нельзя видеть небо или почва не увлажнена, сбрасываем счетчик роста
                IBlockState stateFarmland = worldIn.getBlockState(pos.down());
                if (!state.getValue(WILD)) {
                    if (!worldIn.canSeeSky(pos) || (stateFarmland.getBlock() instanceof BlockSoilFarmland && stateFarmland.getValue(BlockSoilFarmland.MOISTURE) < 3)) {
                        te.resetCounter();
                        return;
                    }
                }

                long growthTicks = (long) (crop.getGrowthTicks() * ConfigTFC.General.FOOD.cropGrowthTimeModifier);
                int fullGrownStages = 0;
                while (te.getTicksSinceUpdate() > growthTicks) {
                    te.reduceCounter(growthTicks);

                    // Находим показатели для времени, в которое культура должна была вырасти
                    float temp = ClimateTFC.getActualTemp(worldIn, pos, -te.getTicksSinceUpdate());
                    float rainfall = ChunkDataTFC.getRainfall(worldIn, pos);

                    // Проверяем, может ли культура вырасти, и если да, то растим ее
                    if (crop.isValidForGrowth(temp, rainfall)) {
                        grow(worldIn, pos, state, random);
                        state = worldIn.getBlockState(pos);
                        if (state.getBlock() instanceof BlockCrop && !state.getValue(WILD) && state.getValue(getStageProperty()) == crop.getMaxStage()) {
                            fullGrownStages++;
                            if (fullGrownStages > 2) {
                                die(worldIn, pos, state, random);
                                return;
                            }
                        }
                    } else if (!crop.isValidConditions(temp, rainfall)) {
                        die(worldIn, pos, state, random);
                        return;
                    }
                }
            }
        }
    }

    /**
     * Проверяет, может ли культура вырасти, и если да, то растит ее.
     * @param worldIn Объект World, представляющий мир.
     * @param pos Позиция блока в мире.
     * @param state Состояние блока.
     * @param random Объект Random для генерации случайных чисел.
     */
    public abstract void grow(World worldIn, BlockPos pos, IBlockState state, Random random);

    /**
     * Уничтожает культуру, превращая ее в мертвый блок культуры.
     * @param worldIn Объект World, представляющий мир.
     * @param pos Позиция блока в мире.
     * @param state Состояние блока.
     * @param random Объект Random для генерации случайных чисел.
     */
    public void die(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (ConfigTFC.General.FOOD.enableCropDeath) {
            worldIn.setBlockState(pos, BlockCropDead.get(crop).getDefaultState().withProperty(BlockCropDead.MATURE, state.getValue(getStageProperty()) == crop.getMaxStage()));
        }
    }

    /**
     * Возвращает ограничивающий параллелепипед для данного состояния блока.
     * @param state Состояние блока.
     * @param source Источник доступа к блокам.
     * @param pos Позиция блока в мире.
     * @return Ограничивающий параллелепипед для данного состояния блока.
     */
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return CROPS_AABB[state.getValue(getStageProperty())];
    }

    /**
     * Возвращает тип растения для данной культуры.
     * @param world Мир.
     * @param pos Позиция блока в мире.
     * @return Тип растения.
     */
    @Nonnull
    @Override
    public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Crop;
    }

    /**
     * Возвращает свойство, представляющее стадию роста культуры.
     * @return Свойство стадии роста.
     */
    public abstract PropertyInteger getStageProperty();

    /**
     * Возвращает статус роста культуры на основе ее состояния, температуры и осадков.
     * @param state Состояние блока.
     * @param world Мир.
     * @param pos Позиция блока в мире.
     * @return Статус роста культуры.
     */
    @Override
    public GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos) {
        float temp = ClimateTFC.getActualTemp(world, pos);
        float rainfall = ChunkDataTFC.getRainfall(world, pos);
        if (state.getValue(getStageProperty()) >= crop.getMaxStage()) {
            return GrowthStatus.FULLY_GROWN;
        } else if (!crop.isValidConditions(temp, rainfall) || !world.canSeeSky(pos)) {
            return GrowthStatus.NOT_GROWING;
        } else if (crop.isValidForGrowth(temp, rainfall)) {
            return GrowthStatus.GROWING;
        }
        return GrowthStatus.CAN_GROW;
    }
}
