package su.terrafirmagreg.modules.soil.api.types.variant.item;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.api.base.types.variant.Variant;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.ItemsSoil;

import net.minecraft.item.Item;


import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class SoilItemVariant extends Variant<SoilItemVariant> {

    @Getter
    private static final Set<SoilItemVariant> variants = new ObjectOpenHashSet<>();

    private BiFunction<SoilItemVariant, SoilType, ? extends Item> factory;

    private SoilItemVariant(String name) {
        super(name);

        if (!variants.add(this)) throw new RuntimeException(String.format("SoilItemVariant: [%s] already exists!", name));
    }

    public Item get(SoilType type) {
        var item = ItemsSoil.SOIL_ITEMS.get(Pair.of(this, type));
        if (item != null) return item;
        throw new RuntimeException(String.format("Item soil is null: %s, %s", this, type));
    }

    public static SoilItemVariant builder(String name) {
        return new SoilItemVariant(name);
    }

    public SoilItemVariant setFactory(BiFunction<SoilItemVariant, SoilType, ? extends Item> factory) {
        this.factory = factory;
        return this;
    }

    public SoilItemVariant build() {
        for (var type : SoilType.getTypes()) {
            if (ItemsSoil.SOIL_ITEMS.put(Pair.of(this, type), factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }

        return this;
    }
}
