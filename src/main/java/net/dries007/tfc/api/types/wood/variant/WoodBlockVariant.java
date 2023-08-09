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
    private final String woodName;

    @Nonnull
    private final BiFunction<WoodBlockVariant, WoodType, IWoodBlock> factory;

    public WoodBlockVariant(@Nonnull String woodName, @Nonnull BiFunction<WoodBlockVariant, WoodType, IWoodBlock> factory) {
        this.woodName = woodName;
        this.factory = factory;

        if (woodName.isEmpty()) {
            throw new RuntimeException(String.format("WoodVariant name must contain any character: [%s]", woodName));
        }

        if (!WOOD_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("WoodVariant: [%s] already exists!", woodName));
        }
    }

    @Nonnull
    @Override
    public String toString() {
        return woodName;
    }

    @Nonnull
    public IWoodBlock applyToFactory(@Nonnull WoodType woodType) {
        return factory.apply(this, woodType);
    }

    public static Set<WoodBlockVariant> getWoodVariants() {
        return WOOD_VARIANTS;
    }
}
