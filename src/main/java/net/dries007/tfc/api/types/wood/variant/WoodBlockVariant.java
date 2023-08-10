package net.dries007.tfc.api.types.wood.variant;

import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class WoodBlockVariant {

    private static final Set<WoodBlockVariant> WOOD_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;

    @Nonnull
    private final BiFunction<WoodBlockVariant, WoodType, IWoodBlock> factory;

    public WoodBlockVariant(@Nonnull String name, @Nonnull BiFunction<WoodBlockVariant, WoodType, IWoodBlock> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("WoodVariant name must contain any character: [%s]", name));
        }

        if (!WOOD_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("WoodVariant: [%s] already exists!", name));
        }
    }

    public static Set<WoodBlockVariant> getWoodVariants() {
        return WOOD_VARIANTS;
    }

    @Nonnull
    @Override
    public String toString() {
        return name;
    }

    @Nonnull
    public IWoodBlock applyToFactory(@Nonnull WoodType woodType) {
        return factory.apply(this, woodType);
    }
}
