package su.terrafirmagreg.modules.wood.api.types.variant.block;

import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.data.lib.types.variant.Variant;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.minecraft.block.Block;


import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.features.falling.FallingBlockManager.Specification;

@Getter
public class WoodBlockVariant extends Variant<WoodBlockVariant> {

    @Getter
    private static final Set<WoodBlockVariant> variants = new ObjectOpenHashSet<>();

    private Specification specification;
    private BiFunction<WoodBlockVariant, WoodType, ? extends Block> factory;
    private int encouragement;
    private int flammability;

    private WoodBlockVariant(String name) {
        super(name);

        if (!variants.add(this)) throw new RuntimeException(String.format("WoodBlockVariant: [%s] already exists!", name));
    }

    public static WoodBlockVariant builder(String name) {
        return new WoodBlockVariant(name);
    }

    public Block get(WoodType type) {
        var block = BlocksWood.WOOD_BLOCKS.get(Pair.of(this, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block wood is null: %s, %s", this, type));
    }

    public List<Block> get() {
        final var list = new ArrayList<Block>();
        BlocksWood.WOOD_BLOCKS.forEach((key, value) -> {
            if (key.getLeft() == this) {
                list.add(value);
            }
        });
        return list;
    }

    public WoodBlockVariant setFactory(BiFunction<WoodBlockVariant, WoodType, ? extends Block> factory) {
        this.factory = factory;
        return this;
    }

    public WoodBlockVariant setFireInfo(int encouragement, int flammability) {
        this.encouragement = encouragement;
        this.flammability = flammability;
        return this;
    }

    public WoodBlockVariant setFallingSpecification(Specification specification) {
        this.specification = specification;
        return this;
    }

    public WoodBlockVariant build() {
        for (var type : WoodType.getTypes()) {
            if (BlocksWood.WOOD_BLOCKS.put(Pair.of(this, type), factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
        return this;
    }

}
