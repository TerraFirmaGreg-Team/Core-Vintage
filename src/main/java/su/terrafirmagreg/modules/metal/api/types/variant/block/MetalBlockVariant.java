package su.terrafirmagreg.modules.metal.api.types.variant.block;

import su.terrafirmagreg.data.lib.types.variant.Variant;
import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.init.BlocksMetal;

import net.minecraft.block.Block;


import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.features.falling.FallingBlockManager.Specification;

@Getter
public class MetalBlockVariant extends Variant<MetalBlockVariant> {

    @Getter
    private static final Set<MetalBlockVariant> variants = new ObjectLinkedOpenHashSet<>();

    private Specification specification;
    private BiFunction<MetalBlockVariant, MetalType, ? extends Block> factory;

    public MetalBlockVariant(String name) {
        super(name);

        if (!variants.add(this)) throw new RuntimeException(String.format("MetalBlockVariant: [%s] already exists!", name));
    }

    public static MetalBlockVariant builder(String name) {
        return new MetalBlockVariant(name);
    }

    public Block get(MetalType type) {
        var block = BlocksMetal.METAL_BLOCKS.get(Pair.of(this, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block metal is null: %s, %s", this, type));
    }

    public MetalBlockVariant setFactory(BiFunction<MetalBlockVariant, MetalType, ? extends Block> factory) {
        this.factory = factory;
        return this;
    }

    public MetalBlockVariant setFallingSpecification(Specification specification) {
        this.specification = specification;
        return this;
    }

    public MetalBlockVariant build() {
        MetalType.getTypes().forEach(type -> {
            if (BlocksMetal.METAL_BLOCKS.put(Pair.of(this, type), factory.apply(this, type)) != null) {
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
            }
        });
        return this;
    }
}
