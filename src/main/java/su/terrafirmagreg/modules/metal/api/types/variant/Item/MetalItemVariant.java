package su.terrafirmagreg.modules.metal.api.types.variant.Item;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.lib.types.variant.Variant;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.init.ItemsMetal;

import net.minecraft.item.Item;


import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class MetalItemVariant extends Variant<MetalItemVariant> {

    @Getter
    private static final Set<MetalItemVariant> variants = new ObjectLinkedOpenHashSet<>();

    private BiFunction<MetalItemVariant, MetalType, ? extends Item> factory;

    private MetalItemVariant(String name) {
        super(name);

        if (!variants.add(this)) throw new RuntimeException(String.format("MetalItemVariant: [%s] already exists!", name));
    }

    public Item get(MetalType type) {
        var item = ItemsMetal.METAL_ITEMS.get(Pair.of(this, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item metal is null: %s, %s", this, type));
    }

    public static MetalItemVariant builder(String name) {

        return new MetalItemVariant(name);
    }

    public MetalItemVariant setFactory(BiFunction<MetalItemVariant, MetalType, ? extends Item> factory) {
        this.factory = factory;
        return this;
    }

    public MetalItemVariant build() {
        for (var type : MetalType.getTypes()) {
            if (ItemsMetal.METAL_ITEMS.put(Pair.of(this, type), factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
        return this;
    }
}
