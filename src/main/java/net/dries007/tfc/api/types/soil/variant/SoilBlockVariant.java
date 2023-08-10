package net.dries007.tfc.api.types.soil.variant;

import net.dries007.tfc.api.types.soil.ISoilBlock;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.util.FallingBlockManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class SoilBlockVariant {

    private static final Set<SoilBlockVariant> SOIL_BLOCK_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<SoilBlockVariant, SoilType, ISoilBlock> factory;
    @Nullable
    private final FallingBlockManager.Specification fallingSpecification;

    public SoilBlockVariant(@Nonnull String name, @Nonnull BiFunction<SoilBlockVariant, SoilType, ISoilBlock> factory, @Nullable FallingBlockManager.Specification fallingSpecification) {
        this.name = name;
        this.factory = factory;
        this.fallingSpecification = fallingSpecification;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("Rock name must contain any character: [%s]", name));
        }

        if (!SOIL_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", name));
        }
    }

    @Nonnull
    @Override
    public String toString() {
        return name;
    }

    @Nullable
    public FallingBlockManager.Specification getFallingSpecification() {
        return fallingSpecification;
    }

    public boolean canFall() {
        return fallingSpecification != null;
    }

    public SoilBlockVariant getNonGrassVersion() {
        if (this == SoilBlockVariants.GRASS || this == SoilBlockVariants.DRY_GRASS)
            return SoilBlockVariants.DIRT;

        if (this == SoilBlockVariants.CLAY_GRASS)
            return SoilBlockVariants.CLAY;

        throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    public SoilBlockVariant getGrassVersion(SoilBlockVariant spreader) {
        if (this == SoilBlockVariants.DIRT) {
            return SoilBlockVariants.CLAY_GRASS;
        }

        if (this == SoilBlockVariants.CLAY) {
            return spreader == SoilBlockVariants.DRY_GRASS ? SoilBlockVariants.DRY_GRASS : SoilBlockVariants.GRASS;
        }

        throw new IllegalArgumentException(String.format("You cannot get grass from [%s] types.", spreader.toString()));
    }

    public boolean isGrass() {
        return this == SoilBlockVariants.GRASS || this == SoilBlockVariants.DRY_GRASS || this == SoilBlockVariants.CLAY_GRASS;
    }

    public static Set<SoilBlockVariant> getSoilBlockVariants() {
        return SOIL_BLOCK_VARIANTS;
    }

    @Nonnull
    public ISoilBlock applyToFactory(SoilType soilType) {
        return factory.apply(this, soilType);
    }
}
