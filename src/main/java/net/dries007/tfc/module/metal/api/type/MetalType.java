package net.dries007.tfc.module.metal.api.type;

import gregtech.api.unification.material.Material;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Класс, представляющий тип почвы.
 */
public class MetalType {

    private static final Set<MetalType> METAL_TYPES = new ObjectLinkedOpenHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final Material material;

    /**
     * Создает экземпляр класса MetalType с указанным именем.
     *
     * @param name Имя типа почвы.
     */
    public MetalType(@Nonnull String name, @Nonnull Material material) {
        this.name = name;
        this.material = material;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("MetalType name must contain any character: [%s]", name));
        }

        if (!METAL_TYPES.add(this)) {
            throw new RuntimeException(String.format("MetalType: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех типов почвы.
     *
     * @return Набор всех типов почвы.
     */
    public static Set<MetalType> getMetalTypes() {
        return METAL_TYPES;
    }

    /**
     * Возвращает строковое представление типа почвы.
     *
     * @return Строковое представление типа почвы.
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }


    @Nonnull
    public Material getMaterial() {
        return material;
    }
}
