package su.terrafirmagreg.modules.core.capabilities.chunkdata;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.world.classic.DataLayerClassic;
import su.terrafirmagreg.modules.world.classic.objects.generator.vein.Vein;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;


import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.util.calendar.Calendar;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static su.terrafirmagreg.modules.world.classic.WorldTypeClassic.ROCKLAYER2;
import static su.terrafirmagreg.modules.world.classic.WorldTypeClassic.ROCKLAYER3;

@Getter
@SuppressWarnings("WeakerAccess")
public final class ProviderChunkData implements ICapabilityChunkData {

  public static final int FISH_POP_MAX = 60;

  public static final ProviderChunkData EMPTY = new ProviderChunkData();

  static {
    Arrays.fill(EMPTY.drainageLayer, DataLayerClassic.ERROR);
    Arrays.fill(EMPTY.stabilityLayer, DataLayerClassic.ERROR);
    Arrays.fill(EMPTY.seaLevelOffset, -1);
  }

  /**
   * Массивы для хранения данных о слоях горных пород.
   */
  private final int[] soilLayer1 = new int[256];

  private final int[] rockLayer1 = new int[256];
  private final int[] rockLayer2 = new int[256];
  private final int[] rockLayer3 = new int[256];

  /**
   * Массивы для хранения данных о слоях дренажа и стабильности.
   */
  private final DataLayerClassic[] drainageLayer = new DataLayerClassic[256];
  private final DataLayerClassic[] stabilityLayer = new DataLayerClassic[256];

  /**
   * Массив для хранения данных о смещении уровня моря.
   */
  private final int[] seaLevelOffset = new int[256];
  private final Set<Vein> generatedVeins = new HashSet<>();
  /**
   * Флаг инициализации данных.
   */
  @Setter
  private boolean initialized = false;
  /**
   * Популяция рыбы в чанке. Может быть установлена на основе биома, температуры или случайного числа.
   */
  @Setter
  private int fishPopulation = FISH_POP_MAX; // todo: Set this based on biome? temp? rng?
  /**
   * Данные об осадках, региональной температуре, средней температуре, плотности и разнообразии растительности.
   */
  @Setter
  private float rainfall;
  @Setter
  private float regionalTemp;
  @Setter
  private float averageTemp;
  @Setter
  private float floraDensity;
  @Setter
  private float floraDiversity;
  /**
   * Рабочая нагрузка чанка.
   */
  @Setter
  private int chunkWorkage;
  /**
   * Количество тиков защиты от появления враждебных мобов. Начинается с отрицательного значения и увеличивается при наличии игроков в области.
   */
  @Setter
  private long protectedTicks; // Used for hostile spawn protection. Starts negative, increases by players in the area
  /**
   * Время последнего обновления чанка.
   */
  @Setter
  private long lastUpdateTick;
  @Setter
  private long lastUpdateYear;

  /**
   * Возвращает тип горной породы слоя 1 для указанного мира и позиции.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Тип горной породы слоя 1 для указанного мира и позиции.
   */
  public static RockType getRock1(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos).getRockLayer1(pos.getX() & 15, pos.getZ() & 15);
  }

  /**
   * Возвращает тип горной породы слоя 2 для указанного мира и позиции.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Тип горной породы слоя 2 для указанного мира и позиции.
   */
  public static RockType getRock2(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos).getRockLayer2(pos.getX() & 15, pos.getZ() & 15);
  }

  /**
   * Возвращает тип горной породы слоя 3 для указанного мира и позиции.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Тип горной породы слоя 3 для указанного мира и позиции.
   */
  public static RockType getRock3(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos).getRockLayer3(pos.getX() & 15, pos.getZ() & 15);
  }

  /**
   * Возвращает значение осадков для указанного мира и позиции.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Значение осадков для указанного мира и позиции.
   */
  public static float getRainfall(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos).getRainfall();
  }

  /**
   * Возвращает плотность растительности для указанной позиции в мире.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Плотность растительности для указанной позиции в мире.
   */
  public static float getFloraDensity(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos).getFloraDensity();
  }

  /**
   * Возвращает разнообразие растительности для указанной позиции в мире.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Разнообразие растительности для указанной позиции в мире.
   */
  public static float getFloraDiversity(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos).getFloraDiversity();
  }

  /**
   * Проверяет, является ли указанная позиция стабильной в мире.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return true, если указанная позиция стабильна, иначе false.
   */
  public static boolean isStable(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos)
        .getStabilityLayer(pos.getX() & 15, pos.getZ() & 15).valueInt == 0;
  }

  /**
   * Возвращает уровень дренажа для указанной позиции в мире.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Уровень дренажа для указанной позиции в мире.
   */
  public static int getDrainage(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos)
        .getDrainageLayer(pos.getX() & 15, pos.getZ() & 15).valueInt;
  }

  /**
   * Возвращает смещение уровня моря для указанной позиции в мире.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Смещение уровня моря для указанной позиции в мире.
   */
  public static int getSeaLevelOffset(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos).getSeaLevelOffset(pos.getX() & 15, pos.getZ() & 15);
  }

  /**
   * Возвращает популяцию рыб для указанной позиции в мире.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Популяция рыб для указанной позиции в мире.
   */
  public static int getFishPopulation(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos).getFishPopulation();
  }

  /**
   * Возвращает высоту камня для указанной позиции в мире.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Высота камня для указанной позиции в мире.
   */
  public static RockType getRockHeight(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos)
        .getRockLayerHeight(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
  }

  /**
   * Возвращает высоту почвы для указанной позиции в мире.
   *
   * @param world Мир.
   * @param pos   Позиция.
   * @return Высота почвы для указанной позиции в мире.
   */
  public static SoilType getSoilHeight(World world, BlockPos pos) {
    return CapabilityChunkData.get(world, pos)
        .getSoilLayerHeight(pos.getX() & 15, pos.getY(), pos.getZ() & 15);
  }

  /**
   * Устанавливает данные генерации для мира.
   * <p>
   * ТОЛЬКО ВНУТРЕННЕЕ ИСПОЛЬЗОВАНИЕ. Нет необходимости помечать как "dirty", так как это будет вызываться только при генерации мира, перед первым
   * сохранением чанка.
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
  public void setGenerationData(int[] rockLayer1, int[] rockLayer2, int[] rockLayer3, int[] soilLayer1,
      DataLayerClassic[] stabilityLayer, DataLayerClassic[] drainageLayer, int[] seaLevelOffset,
      float rainfall, float regionalTemp, float avgTemp, float floraDensity, float floraDiversity) {

    this.initialized = true;

    System.arraycopy(soilLayer1, 0, this.soilLayer1, 0, 256);

    System.arraycopy(rockLayer1, 0, this.rockLayer1, 0, 256);
    System.arraycopy(rockLayer2, 0, this.rockLayer2, 0, 256);
    System.arraycopy(rockLayer3, 0, this.rockLayer3, 0, 256);

    System.arraycopy(stabilityLayer, 0, this.stabilityLayer, 0, 256);
    System.arraycopy(drainageLayer, 0, this.drainageLayer, 0, 256);
    System.arraycopy(seaLevelOffset, 0, this.seaLevelOffset, 0, 256);

    this.rainfall = rainfall;
    this.regionalTemp = regionalTemp;
    this.averageTemp = avgTemp;
    this.floraDensity = floraDensity;
    this.floraDiversity = floraDiversity;

    this.chunkWorkage = 0;

    this.lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
    this.lastUpdateYear = Calendar.CALENDAR_TIME.getTotalYears();
  }

  /**
   * Adds generated ores to this chunk list of ores Should be used by ore vein generators to save in this chunk which ores generated here
   *
   * @param vein the ore added by ore vein generator
   */
  public void markVeinGenerated(@NotNull Vein vein) {
    generatedVeins.add(vein);
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
   * Увеличивает защиту спавна на указанный множитель.
   *
   * @param multiplier Множитель защиты спавна.
   */
  public void addSpawnProtection(int multiplier) {
    if (protectedTicks < Calendar.PLAYER_TIME.getTicks()) {
      protectedTicks = Calendar.PLAYER_TIME.getTicks();
    }
    protectedTicks += multiplier * 600L;
  }

  /**
   * Возвращает оставшуюся защиту спавна.
   *
   * @return Оставшаяся защита спавна.
   */
  public long getSpawnProtection() {
    return protectedTicks - (24 * ICalendar.TICKS_IN_HOUR) - Calendar.PLAYER_TIME.getTicks();
  }

  /**
   * Проверяет, находится ли объект под защитой спавна.
   *
   * @return {@code true}, если объект находится под защитой спавна, иначе {@code false}.
   */
  public boolean isSpawnProtected() {
    return getSpawnProtection() > 0L;
  }

  @Override
  public boolean canWork(int amount) {
    return ConfigTFC.Devices.SLUICE.maxWorkChunk == 0
        || chunkWorkage <= ConfigTFC.Devices.SLUICE.maxWorkChunk + amount;
  }

  /**
   * Сбрасывает значение последнего тика обновления на текущее время игрока.
   */
  public void resetLastUpdateTick() {
    this.lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
  }

  /**
   * Сбрасывает значение последнего года обновления на текущий год в календаре.
   */
  public void resetLastUpdateYear() {
    this.lastUpdateYear = Calendar.CALENDAR_TIME.getTotalYears();
  }

  /**
   * Возвращает список допустимых типов деревьев, основываясь на климатических условиях.
   *
   * @return Список допустимых типов деревьев.
   */
  public List<Tree> getValidTrees() {
    return TFCRegistries.TREES.getValuesCollection().stream()
        .filter(t -> t.isValidLocation(averageTemp, rainfall, floraDensity))
        .sorted((s, t) -> (int) (t.getDominance() - s.getDominance()))
        .collect(Collectors.toList());
  }

  /**
   * Возвращает тип дерева для разреженной генерации, основываясь на климатических условиях.
   *
   * @return Тип дерева для разреженной генерации.
   */
  @Nullable
  public Tree getSparseGenTree() {
    return TFCRegistries.TREES.getValuesCollection().stream()
        .filter(t -> t.isValidLocation(0.5f * averageTemp + 10f, 0.5f * rainfall + 120f, 0.5f))
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

  public SoilType getSoilLayer1(int x, int z) {
    return SoilType.valueOf(soilLayer1[z << 4 | x]);
  }

  /**
   * Возвращает слой стабильности по указанным координатам.
   *
   * @param x Координата X.
   * @param z Координата Z.
   * @return Слой стабильности.
   */
  public DataLayerClassic getStabilityLayer(int x, int z) {
    return stabilityLayer[z << 4 | x];
  }

  /**
   * Возвращает слой дренажа по указанным координатам.
   *
   * @param x Координата X.
   * @param z Координата Z.
   * @return Слой дренажа.
   */
  public DataLayerClassic getDrainageLayer(int x, int z) {
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
    if (y <= ROCKLAYER3 + offset) {
      return getRockLayer3(x, z);
    }
    if (y <= ROCKLAYER2 + offset) {
      return getRockLayer2(x, z);
    }
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
    return getSoilLayer1(x, z);
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {

    return capability == CapabilityChunkData.CAPABILITY;
  }

  @Nullable
  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {

    return hasCapability(capability, facing) ? (T) this : null;
  }

  @Override
  public NBTTagCompound serializeNBT() {
    return (NBTTagCompound) CapabilityChunkData.CAPABILITY.writeNBT(this, null);
  }

  @Override
  public void deserializeNBT(NBTTagCompound nbt) {
    CapabilityChunkData.CAPABILITY.readNBT(this, null, nbt);
  }

}
