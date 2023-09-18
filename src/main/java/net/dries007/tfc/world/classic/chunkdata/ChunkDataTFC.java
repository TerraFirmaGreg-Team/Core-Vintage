package net.dries007.tfc.world.classic.chunkdata;

import net.dries007.tfc.module.core.submodule.rock.api.type.RockType;
import net.dries007.tfc.module.core.submodule.soil.api.type.SoilType;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.util.NBTBuilder;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.dries007.tfc.world.classic.DataLayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagByteArray;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.dries007.tfc.world.classic.WorldTypeTFC.ROCKLAYER2;
import static net.dries007.tfc.world.classic.WorldTypeTFC.ROCKLAYER3;

/**
 * Класс ChunkDataTFC представляет данные для чанка в мире.
 */
@SuppressWarnings("WeakerAccess")
public final class ChunkDataTFC {
    /**
     * Максимальная популяция рыбы в чанке.
     */
    public static final int FISH_POP_MAX = 60;

    /**
     * Пустой экземпляр класса ChunkDataTFC.
     */
    private static final ChunkDataTFC EMPTY = new ChunkDataTFC();

    static {
        Arrays.fill(EMPTY.drainageLayer, DataLayer.ERROR);
        Arrays.fill(EMPTY.stabilityLayer, DataLayer.ERROR);
        Arrays.fill(EMPTY.seaLevelOffset, -1);
    }

    /**
     * Массивы для хранения данных о слоях горных пород.
     */
    private final int[] rockLayer1 = new int[256];
    private final int[] rockLayer2 = new int[256];
    private final int[] rockLayer3 = new int[256];

    /**
     * Массивы для хранения данных о слоях дренажа и стабильности.
     */
    private final DataLayer[] drainageLayer = new DataLayer[256];
    private final DataLayer[] stabilityLayer = new DataLayer[256];

    /**
     * Массив для хранения данных о смещении уровня моря.
     */
    private final int[] seaLevelOffset = new int[256];

    /**
     * Флаг инициализации данных.
     */
    private boolean initialized = false;

    /**
     * Популяция рыбы в чанке.
     * Может быть установлена на основе биома, температуры или случайного числа.
     */
    private int fishPopulation = FISH_POP_MAX;

    /**
     * Данные о осадках, региональной температуре, средней температуре, плотности и разнообразии растительности.
     */
    private float rainfall;
    private float regionalTemp;
    private float avgTemp;
    private float floraDensity;
    private float floraDiversity;

    /**
     * Рабочая нагрузка чанка.
     */
    private int chunkWorkage;

    /**
     * Количество тиков защиты от появления враждебных мобов.
     * Начинается с отрицательного значения и увеличивается при наличии игроков в области.
     */
    private long protectedTicks;

    /**
     * Время последнего обновления чанка.
     */
    private long lastUpdateTick;
    private long lastUpdateYear;

    /**
     * Возвращает экземпляр ChunkDataTFC для указанного мира и позиции.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Экземпляр ChunkDataTFC для указанного мира и позиции.
     */
    @Nonnull
    public static ChunkDataTFC get(World world, BlockPos pos) {
        return get(world.getChunk(pos));
    }

    /**
     * Возвращает экземпляр ChunkDataTFC для указанного чанка.
     *
     * @param chunk Чанк.
     * @return Экземпляр ChunkDataTFC для указанного чанка.
     */
    @Nonnull
    public static ChunkDataTFC get(Chunk chunk) {
        ChunkDataTFC data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);
        return data == null ? EMPTY : data;
    }

    /**
     * Возвращает тип горной породы слоя 1 для указанного мира и позиции.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Тип горной породы слоя 1 для указанного мира и позиции.
     */
    public static RockType getRock1(World world, BlockPos pos) {
        return get(world, pos).getRockLayer1(pos.getX() & 15, pos.getZ() & 15);
    }

    /**
     * Возвращает тип горной породы слоя 2 для указанного мира и позиции.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Тип горной породы слоя 2 для указанного мира и позиции.
     */
    public static RockType getRock2(World world, BlockPos pos) {
        return get(world, pos).getRockLayer2(pos.getX() & 15, pos.getZ() & 15);
    }

    /**
     * Возвращает тип горной породы слоя 3 для указанного мира и позиции.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Тип горной породы слоя 3 для указанного мира и позиции.
     */
    public static RockType getRock3(World world, BlockPos pos) {
        return get(world, pos).getRockLayer3(pos.getX() & 15, pos.getZ() & 15);
    }

    /**
     * Возвращает значение осадков для указанного мира и позиции.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Значение осадков для указанного мира и позиции.
     */
    public static float getRainfall(World world, BlockPos pos) {
        return get(world, pos).getRainfall();
    }

    /**
     * Возвращает плотность растительности для указанной позиции в мире.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Плотность растительности для указанной позиции в мире.
     */
    public static float getFloraDensity(World world, BlockPos pos) {
        return get(world, pos).getFloraDensity();
    }

    /**
     * Возвращает разнообразие растительности для указанной позиции в мире.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Разнообразие растительности для указанной позиции в мире.
     */
    public static float getFloraDiversity(World world, BlockPos pos) {
        return get(world, pos).getFloraDiversity();
    }

    /**
     * Проверяет, является ли указанная позиция стабильной в мире.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return true, если указанная позиция стабильна, иначе false.
     */
    public static boolean isStable(World world, BlockPos pos) {
        return get(world, pos).getStabilityLayer(pos.getX() & 15, pos.getZ() & 15).valueInt == 0;
    }

    /**
     * Возвращает уровень дренажа для указанной позиции в мире.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Уровень дренажа для указанной позиции в мире.
     */
    public static int getDrainage(World world, BlockPos pos) {
        return get(world, pos).getDrainageLayer(pos.getX() & 15, pos.getZ() & 15).valueInt;
    }

    /**
     * Возвращает смещение уровня моря для указанной позиции в мире.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Смещение уровня моря для указанной позиции в мире.
     */
    public static int getSeaLevelOffset(World world, BlockPos pos) {
        return get(world, pos).getSeaLevelOffset(pos.getX() & 15, pos.getZ() & 15);
    }

    /**
     * Возвращает популяцию рыб для указанной позиции в мире.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Популяция рыб для указанной позиции в мире.
     */
    public static int getFishPopulation(World world, BlockPos pos) {
        return get(world, pos).getFishPopulation();
    }

    /**
     * Возвращает высоту камня для указанной позиции в мире.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Высота камня для указанной позиции в мире.
     */
    public static RockType getRockHeight(World world, BlockPos pos) {
        return get(world, pos).getRockLayerHeight(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
    }

    /**
     * Возвращает высоту почвы для указанной позиции в мире.
     *
     * @param world Мир.
     * @param pos   Позиция.
     * @return Высота почвы для указанной позиции в мире.
     */
    public static SoilType getSoilHeight(World world, BlockPos pos) {
        return get(world, pos).getSoilLayerHeight(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
    }

    /**
     * Устанавливает данные генерации для мира.
     * <p>
     * ТОЛЬКО ВНУТРЕННЕЕ ИСПОЛЬЗОВАНИЕ.
     * Нет необходимости помечать как "dirty", так как это будет вызываться только при генерации мира, перед первым сохранением чанка.
     *
     * @param rockLayer1     Массив данных первого слоя камня.
     * @param rockLayer2     Массив данных второго слоя камня.
     * @param rockLayer3     Массив данных третьего слоя камня.
     * @param stabilityLayer Массив слоев стабильности.
     * @param drainageLayer  Массив слоев дренажа.
     * @param seaLevelOffset Массив смещений уровня моря.
     * @param rainfall       Количество осадков.
     * @param regionalTemp   Региональная температура.
     * @param avgTemp        Средняя температура.
     * @param floraDensity   Плотность растительности.
     * @param floraDiversity Разнообразие растительности.
     */
    public void setGenerationData(int[] rockLayer1, int[] rockLayer2, int[] rockLayer3, DataLayer[] stabilityLayer, DataLayer[] drainageLayer, int[] seaLevelOffset, float rainfall, float regionalTemp, float avgTemp, float floraDensity, float floraDiversity) {
        this.initialized = true;
        System.arraycopy(rockLayer1, 0, this.rockLayer1, 0, 256);
        System.arraycopy(rockLayer2, 0, this.rockLayer2, 0, 256);
        System.arraycopy(rockLayer3, 0, this.rockLayer3, 0, 256);
        System.arraycopy(stabilityLayer, 0, this.stabilityLayer, 0, 256);
        System.arraycopy(drainageLayer, 0, this.drainageLayer, 0, 256);
        System.arraycopy(seaLevelOffset, 0, this.seaLevelOffset, 0, 256);

        this.rainfall = rainfall;
        this.regionalTemp = regionalTemp;
        this.avgTemp = avgTemp;
        this.floraDensity = floraDensity;
        this.floraDiversity = floraDiversity;

        this.chunkWorkage = 0;

        this.lastUpdateTick = CalendarTFC.PLAYER_TIME.getTicks();
        this.lastUpdateYear = CalendarTFC.CALENDAR_TIME.getTotalYears();
    }

    /**
     * Увеличивает количество работы на указанное значение.
     *
     * @param amount Количество работы для добавления.
     */
    public void addWork(int amount) {
        chunkWorkage += amount;
    }

    /**
     * Увеличивает количество работы на 1.
     */
    public void addWork() {
        addWork(1);
    }

    /**
     * Устанавливает количество работы.
     *
     * @param amount Количество работы.
     */
    public void setWork(int amount) {
        chunkWorkage = amount;
    }

    /**
     * Проверяет, инициализирован ли объект.
     *
     * @return true, если объект инициализирован, иначе false.
     */
    public boolean isInitialized() {
        return initialized;
    }

    /**
     * Возвращает тип горной породы для указанной позиции.
     *
     * @param pos Позиция блока.
     * @return Тип горной породы для указанной позиции.
     */
    public RockType getRock1(BlockPos pos) {
        return getRock1(pos.getX() & 15, pos.getY() & 15);
    }

    /**
     * Возвращает тип горной породы для указанных координат.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Тип горной породы для указанных координат.
     */
    public RockType getRock1(int x, int z) {
        return getRockLayer1(x, z);
    }

    /**
     * Возвращает тип горной породы для указанной позиции.
     *
     * @param pos Позиция блока.
     * @return Тип горной породы для указанной позиции.
     */
    public RockType getRock2(BlockPos pos) {
        return getRock2(pos.getX() & 15, pos.getY() & 15);
    }

    /**
     * Возвращает тип горной породы для указанных координат.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Тип горной породы для указанных координат.
     */
    public RockType getRock2(int x, int z) {
        return getRockLayer2(x, z);
    }

    /**
     * Возвращает тип горной породы для указанной позиции.
     *
     * @param pos Позиция блока.
     * @return Тип горной породы для указанной позиции.
     */
    public RockType getRock3(BlockPos pos) {
        return getRock3(pos.getX() & 15, pos.getY() & 15);
    }

    /**
     * Возвращает тип горной породы для указанных координат.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Тип горной породы для указанных координат.
     */
    public RockType getRock3(int x, int z) {
        return getRockLayer3(x, z);
    }

    /**
     * Проверяет, является ли указанная позиция стабильной.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return {@code true}, если указанная позиция стабильна, иначе {@code false}.
     */
    public boolean isStable(int x, int z) {
        return getStabilityLayer(x, z).valueInt == 0;
    }

    /**
     * Возвращает значение дренажа для указанных координат.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Значение дренажа для указанных координат.
     */
    public int getDrainage(int x, int z) {
        return getDrainageLayer(x, z).valueInt;
    }

    /**
     * Возвращает тип горной породы для указанной позиции.
     *
     * @param pos Позиция блока.
     * @return Тип горной породы для указанной позиции.
     */
    public RockType getRockHeight(BlockPos pos) {
        return getRockHeight(pos.getX(), pos.getY(), pos.getZ());
    }

    public SoilType getSoilHeight(BlockPos pos) {
        return getSoilHeight(pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Возвращает тип горной породы для указанных координат.
     *
     * @param x Координата X.
     * @param y Координата Y.
     * @param z Координата Z.
     * @return Тип горной породы для указанных координат.
     */
    public RockType getRockHeight(int x, int y, int z) {
        return getRockLayerHeight(x & 15, y, z & 15);
    }

    public SoilType getSoilHeight(int x, int y, int z) {
        return getSoilLayerHeight(x & 15, y, z & 15);
    }

    /**
     * Возвращает смещение уровня моря для указанной позиции.
     *
     * @param pos Позиция блока.
     * @return Смещение уровня моря для указанной позиции.
     */
    public int getSeaLevelOffset(BlockPos pos) {
        return getSeaLevelOffset(pos.getX() & 15, pos.getY() & 15);
    }

    /**
     * Возвращает смещение уровня моря для указанных координат.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Смещение уровня моря для указанных координат.
     */
    public int getSeaLevelOffset(int x, int z) {
        return seaLevelOffset[z << 4 | x];
    }

    /**
     * Возвращает популяцию рыбы.
     *
     * @return Популяция рыбы.
     */
    public int getFishPopulation() {
        return fishPopulation;
    }

    /**
     * Возвращает количество осадков.
     *
     * @return Количество осадков.
     */
    public float getRainfall() {
        return rainfall;
    }

    /**
     * Возвращает региональную температуру.
     *
     * @return Региональная температура.
     */
    public float getRegionalTemp() {
        return regionalTemp;
    }

    /**
     * Возвращает среднюю температуру.
     *
     * @return Средняя температура.
     */
    public float getAverageTemp() {
        return avgTemp;
    }

    /**
     * Возвращает плотность растительности.
     *
     * @return Плотность растительности.
     */
    public float getFloraDensity() {
        return floraDensity;
    }

    /**
     * Возвращает разнообразие растительности.
     *
     * @return Разнообразие растительности.
     */
    public float getFloraDiversity() {
        return floraDiversity;
    }

    /**
     * Увеличивает защиту спавна на указанный множитель.
     *
     * @param multiplier Множитель защиты спавна.
     */
    public void addSpawnProtection(int multiplier) {
        if (protectedTicks < CalendarTFC.PLAYER_TIME.getTicks()) {
            protectedTicks = CalendarTFC.PLAYER_TIME.getTicks();
        }
        protectedTicks += multiplier * 600L;
    }

    /**
     * Возвращает оставшуюся защиту спавна.
     *
     * @return Оставшаяся защита спавна.
     */
    public long getSpawnProtection() {
        return protectedTicks - (24 * ICalendar.TICKS_IN_HOUR) - CalendarTFC.PLAYER_TIME.getTicks();
    }

    /**
     * Проверяет, находится ли объект под защитой спавна.
     *
     * @return {@code true}, если объект находится под защитой спавна, иначе {@code false}.
     */
    public boolean isSpawnProtected() {
        return getSpawnProtection() > 0;
    }

    /**
     * Возвращает последний тик обновления.
     *
     * @return Последний тик обновления.
     */
    public long getLastUpdateTick() {
        return lastUpdateTick;
    }

    /**
     * Сбрасывает значение последнего тика обновления на текущее время игрока.
     */
    public void resetLastUpdateTick() {
        this.lastUpdateTick = CalendarTFC.PLAYER_TIME.getTicks();
    }

    /**
     * Возвращает значение последнего года обновления.
     *
     * @return Последний год обновления.
     */
    public long getLastUpdateYear() {
        return lastUpdateYear;
    }

    /**
     * Сбрасывает значение последнего года обновления на текущий год в календаре.
     */
    public void resetLastUpdateYear() {
        this.lastUpdateYear = CalendarTFC.CALENDAR_TIME.getTotalYears();
    }

    /**
     * Возвращает список допустимых типов деревьев, основываясь на климатических условиях.
     *
     * @return Список допустимых типов деревьев.
     */
    public List<TreeType> getValidTrees() {
        return TreeType.getTreeTypes().stream()
                .filter(t -> t.isValidLocation(avgTemp, rainfall, floraDensity))
                .sorted((s, t) -> (int) (t.getDominance() - s.getDominance()))
                .collect(Collectors.toList());
    }

    /**
     * Возвращает тип дерева для разреженной генерации, основываясь на климатических условиях.
     *
     * @return Тип дерева для разреженной генерации.
     */
    @Nullable
    public TreeType getSparseGenTree() {
        return TreeType.getTreeTypes().stream()
                .filter(t -> t.isValidLocation(0.5f * avgTemp + 10f, 0.5f * rainfall + 120f, 0.5f))
                .min((s, t) -> (int) (t.getDominance() - s.getDominance()))
                .orElse(null);
    }

    /**
     * Возвращает тип горной породы для слоя 1 по указанным координатам.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Тип горной породы для слоя 1.
     */
    public RockType getRockLayer1(int x, int z) {
        return RockType.valueOf(rockLayer1[z << 4 | x]);
    }

    /**
     * Возвращает тип горной породы для слоя 2 по указанным координатам.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Тип горной породы для слоя 2.
     */
    public RockType getRockLayer2(int x, int z) {
        return RockType.valueOf(rockLayer2[z << 4 | x]);
    }

    /**
     * Возвращает тип горной породы для слоя 3 по указанным координатам.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Тип горной породы для слоя 3.
     */
    public RockType getRockLayer3(int x, int z) {
        return RockType.valueOf(rockLayer3[z << 4 | x]);
    }

    /**
     * Возвращает слой стабильности по указанным координатам.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Слой стабильности.
     */
    public DataLayer getStabilityLayer(int x, int z) {
        return stabilityLayer[z << 4 | x];
    }

    /**
     * Возвращает слой дренажа по указанным координатам.
     *
     * @param x Координата X.
     * @param z Координата Z.
     * @return Слой дренажа.
     */
    public DataLayer getDrainageLayer(int x, int z) {
        return drainageLayer[z << 4 | x];
    }

    /**
     * Возвращает тип горной породы для указанных координат высоты.
     *
     * @param x Координата X.
     * @param y Координата Y.
     * @param z Координата Z.
     * @return Тип горной породы для указанных координат высоты.
     */
    public RockType getRockLayerHeight(int x, int y, int z) {
        int offset = getSeaLevelOffset(x, z);
        if (y <= ROCKLAYER3 + offset) return getRockLayer3(x, z);
        if (y <= ROCKLAYER2 + offset) return getRockLayer2(x, z);
        return getRockLayer1(x, z);
    }

    /**
     * Возвращает тип почвы для указанных координат высоты.
     *
     * @param x Координата X.
     * @param y Координата Y.
     * @param z Координата Z.
     * @return Тип почвы для указанных координат высоты.
     */
    public SoilType getSoilLayerHeight(int x, int y, int z) {
        return SoilType.valueOf(rockLayer1[z << 4 | x]);
    }

    /**
     * Хранилище данных для чанка.
     */
    public static final class ChunkDataStorage implements Capability.IStorage<ChunkDataTFC> {

        /**
         * Записывает слои данных в NBTTagByteArray.
         *
         * @param layers Массив слоев данных.
         * @return NBTTagByteArray, содержащий слои данных.
         */
        public static NBTTagByteArray write(DataLayer[] layers) {
            return new NBTTagByteArray(Arrays.stream(layers).map(x -> (byte) x.layerID).collect(Collectors.toList()));
        }

        /**
         * Считывает слои данных из массива байтов.
         *
         * @param layers Массив слоев данных.
         * @param bytes  Массив байтов.
         */
        public static void read(DataLayer[] layers, byte[] bytes) {
            for (int i = bytes.length - 1; i >= 0; i--) {
                layers[i] = DataLayer.get(bytes[i]);
            }
        }

        /**
         * Записывает данные объекта ChunkDataTFC в NBT формат.
         *
         * @param capability Возможность хранения.
         * @param instance   Экземпляр ChunkDataTFC.
         * @param side       Сторона.
         * @return NBTBase объект, содержащий данные ChunkDataTFC.
         */
        @Nullable
        @Override
        public NBTBase writeNBT(Capability<ChunkDataTFC> capability, ChunkDataTFC instance, EnumFacing side) {
            if (instance == null || !instance.isInitialized()) {
                return new NBTBuilder().setBoolean("valid", false).build();
            }
            NBTTagCompound root = new NBTTagCompound();
            root.setBoolean("valid", true);

            // Записываем слои данных о горных породах и уровне моря
            root.setTag("rockLayer1", new NBTTagIntArray(instance.rockLayer1));
            root.setTag("rockLayer2", new NBTTagIntArray(instance.rockLayer2));
            root.setTag("rockLayer3", new NBTTagIntArray(instance.rockLayer3));
            root.setTag("seaLevelOffset", new NBTTagIntArray(instance.seaLevelOffset));

            // Записываем слои данных о стабильности и дренаже
            root.setTag("stabilityLayer", write(instance.stabilityLayer));
            root.setTag("drainageLayer", write(instance.drainageLayer));

            // Записываем данные о популяции рыб
            root.setInteger("fishPopulation", instance.fishPopulation);

            // Записываем данные о климатических условиях
            root.setFloat("rainfall", instance.rainfall);
            root.setFloat("regionalTemp", instance.regionalTemp);
            root.setFloat("avgTemp", instance.avgTemp);
            root.setFloat("floraDensity", instance.floraDensity);
            root.setFloat("floraDiversity", instance.floraDiversity);

            // Записываем данные о работе чанка и времени обновления
            root.setInteger("chunkWorkage", instance.chunkWorkage);
            root.setLong("protectedTicks", instance.protectedTicks);
            root.setLong("lastUpdateTick", instance.lastUpdateTick);
            root.setLong("lastUpdateYear", instance.lastUpdateYear);

            return root;
        }

        /**
         * Считывает данные объекта ChunkDataTFC из NBT формата.
         *
         * @param capability Возможность хранения.
         * @param instance   Экземпляр ChunkDataTFC.
         * @param side       Сторона.
         * @param nbt        NBTBase объект, содержащий данные ChunkDataTFC.
         */
        @Override
        public void readNBT(Capability<ChunkDataTFC> capability, ChunkDataTFC instance, EnumFacing side, NBTBase nbt) {
            NBTTagCompound root = (NBTTagCompound) nbt;
            if (nbt != null && root.getBoolean("valid")) {
                // Считываем слои данных о горных породах и уровне моря
                System.arraycopy(root.getIntArray("rockLayer1"), 0, instance.rockLayer1, 0, 256);
                System.arraycopy(root.getIntArray("rockLayer2"), 0, instance.rockLayer2, 0, 256);
                System.arraycopy(root.getIntArray("rockLayer3"), 0, instance.rockLayer3, 0, 256);
                System.arraycopy(root.getIntArray("seaLevelOffset"), 0, instance.seaLevelOffset, 0, 256);

                // Считываем слои данных о стабильности и дренаже
                read(instance.stabilityLayer, root.getByteArray("stabilityLayer"));
                read(instance.drainageLayer, root.getByteArray("drainageLayer"));

                // Считываем данные о популяции рыб
                instance.fishPopulation = root.getInteger("fishPopulation");

                // Считываем данные о климатических условиях
                instance.rainfall = root.getFloat("rainfall");
                instance.regionalTemp = root.getFloat("regionalTemp");
                instance.avgTemp = root.getFloat("avgTemp");
                instance.floraDensity = root.getFloat("floraDensity");
                instance.floraDiversity = root.getFloat("floraDiversity");

                // Считываем данные о работе чанка и времени обновления
                instance.chunkWorkage = root.getInteger("chunkWorkage");
                instance.protectedTicks = root.getLong("protectedTicks");
                instance.lastUpdateTick = root.getLong("lastUpdateTick");
                instance.lastUpdateYear = root.getLong("lastUpdateYear");

                instance.initialized = true;
            }
        }
    }
}
