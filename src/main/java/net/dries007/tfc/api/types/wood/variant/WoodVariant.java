package net.dries007.tfc.api.types.wood.variant;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class WoodVariant {

    private static final Set<WoodVariant> WOOD_VARIANT = new LinkedHashSet<>();

    @Nonnull
    private final String name;

    public WoodVariant(@Nonnull String name) {
        this.name = name;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("WoodVariant name must contain any character: [%s]", name));
        }

        if (!WOOD_VARIANT.add(this)) {
            throw new RuntimeException(String.format("WoodVariant: [%s] already exists!", name));
        }
    }


    public static Set<WoodVariant> getAllWoodVariant() {
        return WOOD_VARIANT;
    }
}
