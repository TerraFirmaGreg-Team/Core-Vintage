package su.terrafirmagreg.modules.metal.api.types.variant.Item;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.init.ItemsMetal;

import net.minecraft.item.Item;


import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.BiFunction;

/**
 * Класс, представляющий тип блока породы.
 */
public class MetalItemVariant implements Comparable<MetalItemVariant> {

    private static final Set<MetalItemVariant> METAL_ITEM_VARIANTS = new ObjectLinkedOpenHashSet<>();

    @NotNull
    private final String name;

    private MetalItemVariant(Builder builder) {
        this.name = builder.name;

        if (name.isEmpty())
            throw new RuntimeException(String.format("MetalItemVariant name must contain any character: [%s]", name));

        if (!METAL_ITEM_VARIANTS.add(this))
            throw new RuntimeException(String.format("MetalItemVariant: [%s] already exists!", name));

        for (var type : MetalType.getTypes()) {
            if (ItemsMetal.METAL_ITEMS.put(Pair.of(this, type), builder.factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
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

    public Item get(MetalType type) {
        var item = ItemsMetal.METAL_ITEMS.get(Pair.of(this, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item metal is null: %s, %s", this, type));
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

    @Override
    public int compareTo(@NotNull MetalItemVariant itemVariant) {
        return this.name.compareTo(itemVariant.toString());
    }

    public static class Builder {

        private final String name;
        private BiFunction<MetalItemVariant, MetalType, ? extends Item> factory;

        /**
         * Создает экземпляр Builder с указанным именем.
         *
         * @param name Имя породы.
         */
        public Builder(@NotNull String name) {
            this.name = name;
        }

        public Builder setFactory(BiFunction<MetalItemVariant, MetalType, ? extends Item> factory) {
            this.factory = factory;
            return this;
        }

        public MetalItemVariant build() {
            return new MetalItemVariant(this);
        }
    }
}
