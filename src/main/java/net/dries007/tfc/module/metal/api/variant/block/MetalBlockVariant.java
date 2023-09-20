package net.dries007.tfc.module.metal.api.variant.block;

import gregtech.api.unification.material.Material;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Класс, представляющий вариант блока металла.
 */
public class MetalBlockVariant {
    private static final Set<MetalBlockVariant> METAL_BLOCK_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<MetalBlockVariant, Material, IMetalBlock> factory;

    /**
     * Создает экземпляр класса MetalBlockVariant с указанными параметрами.
     *
     * @param name    Имя варианта металлического блока.
     * @param factory Фабричная функция для создания металлического блока.
     */
    public MetalBlockVariant(@Nonnull String name, @Nonnull BiFunction<MetalBlockVariant, Material, IMetalBlock> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("MetalBlockVariant name must contain any character: [%s]", name));
        }

        if (!METAL_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("MetalBlockVariant: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех вариантов металлического блока.
     *
     * @return Набор всех вариантов металлического блока.
     */
    public static Set<MetalBlockVariant> getMetalBlockVariants() {
        return METAL_BLOCK_VARIANTS;
    }

    /**
     * Возвращает строковое представление варианта металлического блока.
     *
     * @return Строковое представление варианта металлического блока.
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }

    /**
     * Применяет вариант металлического блока к фабрике для создания металлического блока.
     *
     * @param material Тип материала.
     * @return Металлический блок.
     */
    @Nonnull
    public IMetalBlock create(Material material) {
        return this.factory.apply(this, material);
    }
}
