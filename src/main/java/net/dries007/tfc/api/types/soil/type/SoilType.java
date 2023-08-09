package net.dries007.tfc.api.types.soil.type;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class SoilType {

    private static final Set<SoilType> SOIL_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String soilTypeName;

    public SoilType(@Nonnull String soilTypeName) {
        this.soilTypeName = soilTypeName;

        if (soilTypeName.isEmpty()) {
            throw new RuntimeException(String.format("Rock name must contain any character: [%s]", soilTypeName));
        }

        if (!SOIL_TYPES.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", soilTypeName));
        }
    }

    @Nonnull
    @Override
    public String toString() {
        return soilTypeName;
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
