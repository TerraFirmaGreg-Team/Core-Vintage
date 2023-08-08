package net.dries007.tfc.api.types.wood.variant;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class WoodBlockVariant {

    private static final Set<WoodBlockVariant> WOOD_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;

    public WoodBlockVariant(@Nonnull String name) {
        this.name = name;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("WoodVariant name must contain any character: [%s]", name));
        }

        if (!WOOD_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("WoodVariant: [%s] already exists!", name));
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public static Set<WoodBlockVariant> getWoodVariants() {
        return WOOD_VARIANTS;
    }
}
