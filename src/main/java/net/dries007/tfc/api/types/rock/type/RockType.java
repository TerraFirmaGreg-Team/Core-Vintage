package net.dries007.tfc.api.types.rock.type;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Основной класс для типов камней.
 * */
public class RockType {

    private static final Set<RockType> rockTypes = new HashSet<>();

    @Nonnull
    private final String rockTypeName;
    @Nonnull
    private final RockCategory rockCategory;
    private final boolean isFlux;

    public RockType(@Nonnull String rockTypeName, @Nonnull RockCategory rockCategory, boolean isFlux) {
        this.rockTypeName = rockTypeName;
        this.rockCategory = rockCategory;
        this.isFlux = isFlux;

        if (rockTypeName.isEmpty()) {
            throw new RuntimeException(String.format("RockType name must contain any character: [%s]", rockTypeName));
        }

        if (!rockTypes.add(this)) {
            throw new RuntimeException(String.format("RockType: [%s] already exists!", rockTypeName));
        }
    }

    public RockType(@Nonnull String rockTypeName, @Nonnull RockCategory rockCategory) {
        this(rockTypeName, rockCategory, false);
    }

    @Override
    public String toString() {
        return rockTypeName;
    }

    @Nonnull
    public RockCategory getRockCategory() {
        return rockCategory;
    }

    public boolean isFlux() {
        return isFlux;
    }

    @Nonnull
    public ResourceLocation getTexture() {
        return new ResourceLocation(TerraFirmaCraft.MOD_ID, "textures/blocks/rock/raw/" + this + ".png");
    }

    public static Set<RockType> getRockTypes() {
        return rockTypes;
    }

    public static RockType valueOf(int i) {
        var values = new RockType[rockTypes.size()];
        values = rockTypes.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }
    public static int indexOf(RockType rockType) {
        return new ArrayList<>(rockTypes).indexOf(rockType);
    }
}
