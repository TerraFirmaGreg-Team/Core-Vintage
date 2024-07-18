package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.spi.types.variant.Variant;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.init.ItemsWood;

import net.minecraft.item.Item;


import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class WoodItemVariant extends Variant<WoodItemVariant> {

    private static final Set<WoodItemVariant> variants = new ObjectOpenHashSet<>();

    private BiFunction<WoodItemVariant, WoodType, ? extends Item> factory;

    private WoodItemVariant(String name) {
        super(name);

        if (!variants.add(this)) throw new RuntimeException(String.format("CropItemVariant: [%s] already exists!", name));
    }

    public Item get(WoodType type) {
        var item = ItemsWood.WOOD_ITEMS.get(Pair.of(this, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item wood is null: %s, %s", this, type));
    }

    public static WoodItemVariant builder(String name) {
        return new WoodItemVariant(name);
    }

    public WoodItemVariant setFactory(BiFunction<WoodItemVariant, WoodType, ? extends Item> factory) {
        this.factory = factory;
        return this;
    }

    public WoodItemVariant build() {
        for (var type : WoodType.getTypes()) {
            if (ItemsWood.WOOD_ITEMS.put(Pair.of(this, type), factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }

        return this;
    }
}
