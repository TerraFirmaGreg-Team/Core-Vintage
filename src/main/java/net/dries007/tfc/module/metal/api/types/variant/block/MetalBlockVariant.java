package net.dries007.tfc.module.metal.api.types.variant.block;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.metal.api.types.type.MetalType;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.module.metal.StorageMetal.METAL_BLOCKS;

/**
 * Класс, представляющий вариант блока металла.
 */
public class MetalBlockVariant {
    private static final Set<MetalBlockVariant> METAL_BLOCK_VARIANTS = new ObjectLinkedOpenHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<MetalBlockVariant, MetalType, IMetalBlock> factory;

    /**
     * Создает экземпляр класса MetalBlockVariant с указанными параметрами.
     *
     * @param name    Имя варианта металлического блока.
     * @param factory Фабричная функция для создания металлического блока.
     */
    public MetalBlockVariant(@Nonnull String name, @Nonnull BiFunction<MetalBlockVariant, MetalType, IMetalBlock> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("MetalBlockVariant name must contain any character: [%s]", name));
        }

        if (!METAL_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("MetalBlockVariant: [%s] already exists!", name));
        }

        for (var type : MetalType.getMetalTypes()) {
            if (METAL_BLOCKS.put(new Pair<>(this, type), this.create(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
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
     * @param type Тип материала.
     * @return Металлический блок.
     */
    @Nonnull
    public IMetalBlock create(MetalType type) {
        return this.factory.apply(this, type);
    }
}
