package net.dries007.tfc.api.types.rock.type;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Supplier;

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
    private final OrePrefix associatedOrePrefix;
    @Nonnull
    private final Material associatedMaterial;
    private final boolean isFlux;

    /**
     * Создает экземпляр класса Rock с указанными параметрами.
     *
     * @param name Название породы.
     * @param rockCategory Категория породы.
     * @param associatedOrePrefix Орпрефикс для типа породы, для использования в стоунтайпах.
     * @param associatedMaterial Материал для типа породы, для использования в стоунтайпах.
     * @param isFlux Флаг, указывающий, является ли порода флюсом.
     */
    public RockType(@Nonnull String name, @Nonnull RockCategory rockCategory, @Nonnull OrePrefix associatedOrePrefix, @Nonnull Material associatedMaterial, boolean isFlux) {
        this.name = name;
        this.rockCategory = rockCategory;
        this.associatedOrePrefix = associatedOrePrefix;
        this.associatedMaterial = associatedMaterial;
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
     * @param name Название породы.
     * @param rockCategory Категория породы.
     * @param associatedOrePrefix Орпрефикс для типа породы, для использования в стоунтайпах.
     * @param associatedMaterial Материал для типа породы, для использования в стоунтайпах.
     */
    public RockType(@Nonnull String name, @Nonnull RockCategory rockCategory, @Nonnull OrePrefix associatedOrePrefix, @Nonnull Material associatedMaterial) {
        this(name, rockCategory, associatedOrePrefix, associatedMaterial, false);
    }

    /**
     * Возвращает список всех типов пород.
     *
     * @return Список всех типов пород.
     */
    public static Set<RockType> getRockTypes() {
        return ROCK_TYPES;
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
    public RockCategory getRockCategory() {
        return rockCategory;
    }

    /**
     * Возвращает орпрефикс породы.
     *
     * @return ОрПрефикс породы.
     */
    @Nonnull
    public OrePrefix getAssociatedOrePrefix() {
        return associatedOrePrefix;
    }

    /**
     * Возвращает материал породы.
     *
     * @return Материал породы.
     */
    @Nonnull
    public Material getAssociatedMaterial() {
        return associatedMaterial;
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
        return new ResourceLocation(TerraFirmaCraft.MOD_ID, "textures/blocks/rock/raw/" + this + ".png");
    }
}
