package su.terrafirmagreg.modules.metal.api.types.variant.block;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;

import net.minecraft.block.Block;


import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification;

/**
 * Класс, представляющий вариант блока металла.
 */
public class MetalBlockVariant implements Comparable<MetalBlockVariant> {

    private static final Set<MetalBlockVariant> METAL_BLOCK_VARIANTS = new ObjectLinkedOpenHashSet<>();

    @NotNull
    private final String name;
    @Getter
    private final Specification specification;

    public MetalBlockVariant(Builder builder) {
        this.name = builder.name;
        this.specification = builder.specification;

        if (name.isEmpty())
            throw new RuntimeException(String.format("MetalBlockVariant name must contain any character: [%s]", name));

        if (!METAL_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("MetalBlockVariant: [%s] already exists!", name));
        }

        for (var type : MetalType.getTypes()) {
            if (BlocksMetal.METAL_BLOCKS.put(Pair.of(this, type), builder.factory.apply(this, type)) != null)
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

    public Block get(MetalType type) {
        var block = BlocksMetal.METAL_BLOCKS.get(Pair.of(this, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block metal is null: %s, %s", this, type));
    }

    /**
     * Возвращает строковое представление варианта металлического блока.
     *
     * @return Строковое представление варианта металлического блока.
     */
    @NotNull
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(@NotNull MetalBlockVariant blockVariant) {
        return this.name.compareTo(blockVariant.toString());
    }

    public static class Builder {

        private final String name;
        private BiFunction<MetalBlockVariant, MetalType, ? extends Block> factory;
        private Specification specification = null;

        public Builder(@NotNull String name) {
            this.name = name;
        }

        public Builder setFactory(BiFunction<MetalBlockVariant, MetalType, ? extends Block> factory) {
            this.factory = factory;
            return this;
        }

        public Builder setFallingSpecification(Specification specification) {
            this.specification = specification;
            return this;
        }

        public MetalBlockVariant build() {
            return new MetalBlockVariant(this);
        }
    }
}
