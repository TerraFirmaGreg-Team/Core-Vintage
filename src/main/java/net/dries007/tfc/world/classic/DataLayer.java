package net.dries007.tfc.world.classic;

import net.dries007.tfc.api.types.rock.variant.block.IRockBlock;

/**
 * Класс DataLayer представляет слой данных и содержит информацию о различных аспектах геологических данных.
 */
@SuppressWarnings("WeakerAccess")
public final class DataLayer {
    public static final DataLayer ERROR = new DataLayer(-1, null, "ERROR", Integer.MIN_VALUE, Float.NaN);
    private static final DataLayer[] LAYERS = new DataLayer[256];

    // Слои для сейсмической активности
    public static final DataLayer SEISMIC_STABLE = newIntDataLayer(110, "Stable", 0);
    public static final DataLayer SEISMIC_UNSTABLE = newIntDataLayer(111, "Unstable", 1);

    // Слои для дренажа
    public static final DataLayer DRAINAGE_NONE = newIntDataLayer(120, "None", 0);
    public static final DataLayer DRAINAGE_VERY_POOR = newIntDataLayer(121, "Very Poor", 1);
    public static final DataLayer DRAINAGE_POOR = newIntDataLayer(122, "Poor", 2);
    public static final DataLayer DRAINAGE_NORMAL = newIntDataLayer(123, "Normal", 3);
    public static final DataLayer DRAINAGE_GOOD = newIntDataLayer(124, "Good", 4);
    public static final DataLayer DRAINAGE_VERY_GOOD = newIntDataLayer(125, "Very Good", 5);

    // Слои для pH
    public static final DataLayer PH_ACID_HIGH = newIntDataLayer(130, "High Acidity", 0);
    public static final DataLayer PH_ACID_LOW = newIntDataLayer(131, "Low acidity", 1);
    public static final DataLayer PH_NEUTRAL = newIntDataLayer(132, "Neutral", 2);
    public static final DataLayer PH_ALKALINE_LOW = newIntDataLayer(133, "Low Alkalinity", 3);
    public static final DataLayer PH_ALKALINE_HIGH = newIntDataLayer(134, "High Alkalinity", 4);

    public final int layerID;
    public final IRockBlock block;
    public final String name;
    public final int valueInt;
    public final float valueFloat;

    /**
     * Конструктор класса DataLayer.
     *
     * @param i          Идентификатор слоя.
     * @param block      Блок, связанный с данным слоем.
     * @param name       Название слоя.
     * @param valueInt   Целочисленное значение слоя.
     * @param valueFloat Вещественное значение слоя.
     */
    private DataLayer(int i, IRockBlock block, String name, int valueInt, float valueFloat) {
        this.layerID = i;
        this.block = block;
        this.name = name;
        this.valueInt = valueInt;
        this.valueFloat = valueFloat;
    }

    /**
     * Возвращает слой данных по его идентификатору.
     *
     * @param i Идентификатор слоя.
     * @return Слой данных.
     * @throws IllegalArgumentException Если слой с указанным идентификатором не используется.
     */
    public static DataLayer get(int i) {
        if (LAYERS[i] == null) throw new IllegalArgumentException("Layer " + i + " not used.");
        return LAYERS[i];
    }

    /**
     * Создает новый слой данных с целочисленным значением.
     *
     * @param i     Идентификатор слоя.
     * @param name  Название слоя.
     * @param value Целочисленное значение слоя.
     * @return Новый слой данных.
     * @throws IllegalArgumentException Если слой с указанным идентификатором уже используется.
     */
    private static DataLayer newIntDataLayer(int i, String name, int value) {
        if (LAYERS[i] != null) throw new IllegalArgumentException("Layer " + i + " already in use.");
        return LAYERS[i] = new DataLayer(i, null, name, value, Float.NaN);
    }
}
