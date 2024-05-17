package su.terrafirmagreg.modules.metal.api.types.type;

import gregtech.api.unification.material.Material;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;

import lombok.Getter;

import java.util.Set;

/**
 * Класс, представляющий тип почвы.
 */
public class MetalType implements Comparable<MetalType> {

    private static final Set<MetalType> METAL_TYPES = new ObjectLinkedOpenHashSet<>();

    private final String name;
    @Getter
    private final Material material;
    @Getter
    private final int tier;
    @Getter
    private final float specificHeat;
    @Getter
    private final float meltTemp;
    @Getter
    private final int color;

    public MetalType(Builder builder) {
        this.name = builder.name;
        this.material = builder.material;
        this.tier = builder.tier;
        this.specificHeat = builder.specificHeat;
        this.meltTemp = builder.meltTemp;
        this.color = builder.color;

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
    public static Set<MetalType> getTypes() {
        return METAL_TYPES;
    }

    /**
     * Возвращает строковое представление типа почвы.
     *
     * @return Строковое представление типа почвы.
     */
    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(MetalType type) {
        return this.name.compareTo(type.toString());
    }

    public static class Builder {

        private final String name;
        private Material material;
        private float meltTemp;
        private float specificHeat;
        private int color;
        private int tier;

        public Builder(String name) {
            this.name = name;

        }

        public Builder material(Material material) {
            this.material = material;
            this.color = material.getMaterialRGB();
            return this;
        }

        public Builder heat(float specificHeat, float meltTemp) {
            this.specificHeat = specificHeat;
            this.meltTemp = meltTemp;
            return this;
        }

        public Builder tier(int tier) {
            this.tier = tier;
            return this;
        }

        public MetalType build() {
            return new MetalType(this);
        }

    }
}
