package net.dries007.tfc.module.metal.api.variant.Item;

import gregtech.api.unification.material.Material;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Класс, представляющий тип блока породы.
 */
public class MetalItemVariant {
    private static final Set<MetalItemVariant> METAL_ITEM_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;


    @Nonnull
    private final BiFunction<MetalItemVariant, Material, IMetalItem> factory;

    /**
     * Создает экземпляр класса RockType с указанными параметрами.
     *
     * @param name    Название типа блока породы.
     * @param factory Функция-фабрика для создания блока породы по умолчанию.
     */
    public MetalItemVariant(@Nonnull String name, @Nonnull BiFunction<MetalItemVariant, Material, IMetalItem> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("MetalItemVariant name must contain any character: [%s]", name));
        }

        if (!METAL_ITEM_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("MetalItemVariant: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех типов блоков породы.
     *
     * @return Набор всех типов блоков породы.
     */
    public static Set<MetalItemVariant> getMetalItemVariants() {
        return METAL_ITEM_VARIANTS;
    }

    /**
     * Возвращает строковое представление типа блока породы.
     *
     * @return Строковое представление типа блока породы.
     */
    @Override
    public String toString() {
        return name;
    }


    /**
     * Применяет функцию-фабрику к типу блока породы и возвращает созданный блок породы.
     *
     * @param type Тип породы.
     * @return Созданный блок породы.
     */
    @Nonnull
    public IMetalItem create(Material type) {
        return factory.apply(this, type);
    }
}
