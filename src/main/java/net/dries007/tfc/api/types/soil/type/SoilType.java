package net.dries007.tfc.api.types.soil.type;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class SoilType {

    private static final Set<SoilType> SOIL_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;

    public SoilType(@Nonnull String name) {
        this.name = name;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("Rock name must contain any character: [%s]", name));
        }

        if (!SOIL_TYPES.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", name));
        }
    }

    @Nonnull
    @Override
    public String toString() {
        return name;
    }

    public String name() {
        return name;
    }

    public static Set<SoilType> getSoilTypes() {
        return SOIL_TYPES;
    }

    @Nonnull
    public static SoilType valueOf(int i) {
        var values = new SoilType[SOIL_TYPES.size()];
        values = SOIL_TYPES.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }
}
