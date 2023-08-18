package net.dries007.tfc.api.types.rock.type;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

/**
 * Основной класс для типов камней.
 */
public class RockType {

    private static final Set<RockType> ROCK_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final RockCategory rockCategory;
    @Nonnull
    private final OrePrefix orePrefix;
    @Nonnull
    private final Material material;
    private final boolean isFlux;

    /**
     * Создает экземпляр класса Rock с указанными параметрами.
     *
     * @param name         Название породы.
     * @param rockCategory Категория породы.
     * @param orePrefix    Орпрефикс для типа породы, для использования в стоунтайпах.
     * @param material     Материал для типа породы, для использования в стоунтайпах.
     * @param isFlux       Флаг, указывающий, является ли порода флюсом.
     */
    public RockType(@Nonnull String name, @Nonnull RockCategory rockCategory, @Nonnull OrePrefix orePrefix, @Nonnull Material material, boolean isFlux) {
        this.name = name;
        this.rockCategory = rockCategory;
        this.orePrefix = orePrefix;
        this.material = material;
        this.isFlux = isFlux;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("Rock name must contain any character: [%s]", name));
        }

        if (!ROCK_TYPES.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", name));
        }
    }

    /**
     * Создает экземпляр класса Rock с указанными параметрами и флагом флюса, установленным в false.
     *
     * @param name         Название породы.
     * @param rockCategory Категория породы.
     * @param orePrefix    Орпрефикс для типа породы, для использования в стоунтайпах.
     * @param material     Материал для типа породы, для использования в стоунтайпах.
     */
    public RockType(@Nonnull String name, @Nonnull RockCategory rockCategory, @Nonnull OrePrefix orePrefix, @Nonnull Material material) {
        this(name, rockCategory, orePrefix, material, false);
    }

    /**
     * Возвращает список всех типов пород.
     *
     * @return Список всех типов пород.
     */
    public static Set<RockType> getRockTypes() {
        return ROCK_TYPES;
    }

    @Nullable
    public static RockType getByName(@Nonnull String name) {
        return RockType.getRockTypes().stream().filter(s -> s.toString().equals(name)).findFirst().orElse(null);
    }

    /**
     * Возвращает экземпляр породы по индексу.
     *
     * @param i Индекс породы.
     * @return Экземпляр породы.
     */
    public static RockType valueOf(int i) {
        var values = new RockType[ROCK_TYPES.size()];
        values = ROCK_TYPES.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }

    /**
     * Возвращает индекс породы в списке.
     *
     * @param rockType Порода.
     * @return Индекс породы.
     */
    public static int indexOf(RockType rockType) {
        return new ArrayList<>(ROCK_TYPES).indexOf(rockType);
    }

    /**
     * Возвращает строковое представление породы.
     *
     * @return Строковое представление породы.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает категорию породы.
     *
     * @return Категория породы.
     */
    @Nonnull
    public RockCategory getCategory() {
        return rockCategory;
    }

    /**
     * Возвращает орпрефикс породы.
     *
     * @return ОрПрефикс породы.
     */
    @Nonnull
    public OrePrefix getOrePrefix() {
        return orePrefix;
    }

    /**
     * Возвращает материал породы.
     *
     * @return Материал породы.
     */
    @Nonnull
    public Material getMaterial() {
        return material;
    }

    /**
     * Проверяет, является ли порода флюсом.
     *
     * @return true, если порода является флюсом, в противном случае - false.
     */
    public boolean isFlux() {
        return isFlux;
    }

    /**
     * Возвращает ресурсное расположение текстуры породы.
     *
     * @return Ресурсное расположение текстуры породы.
     */
    @Nonnull
    public ResourceLocation getTexture() {
        return new ResourceLocation(MOD_ID, "textures/blocks/rock/raw/" + this + ".png");
    }
}
