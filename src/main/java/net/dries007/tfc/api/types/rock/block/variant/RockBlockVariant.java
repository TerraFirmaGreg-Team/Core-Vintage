package net.dries007.tfc.api.types.rock.block.variant;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class RockBlockVariant {

    private static final Set<RockBlockVariant> rockBlockVariants = new LinkedHashSet<>();

    @Nonnull
    private final String rockBlockVariantName;
    private final float baseHardness;

    public RockBlockVariant(@Nonnull String rockBlockVariantName, float baseHardness) {
        this.rockBlockVariantName = rockBlockVariantName;
        this.baseHardness = baseHardness;

        if (rockBlockVariantName.isEmpty()) {
            throw new RuntimeException(String.format("RockBlockVariant name must contain any character: [%s]", rockBlockVariantName));
        }

        if (!rockBlockVariants.add(this)) {
            throw new RuntimeException(String.format("RockBlockVariant: [%s] already exists!", rockBlockVariantName));
        }
    }

    public float getBaseHardness() {
        return baseHardness;
    }

    @Override
    public String toString() {
        return rockBlockVariantName;
    }

    public static Set<RockBlockVariant> getRockBlockVariants() {
        return rockBlockVariants;
    }

    public boolean canFall() {
        return false;
    }
}
