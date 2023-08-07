package net.dries007.tfc.api.types.rock.type;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Основной класс для типов камней.
 */
public class Rock {

    private static final Set<Rock> ROCKS = new LinkedHashSet<>();

    @Nonnull
    private final String rockTypeName;
    @Nonnull
    private final RockCategory rockCategory;
    private final boolean isFlux;

    public Rock(@Nonnull String rockTypeName, @Nonnull RockCategory rockCategory, boolean isFlux) {
        this.rockTypeName = rockTypeName;
        this.rockCategory = rockCategory;
        this.isFlux = isFlux;

        if (rockTypeName.isEmpty()) {
            throw new RuntimeException(String.format("Rock name must contain any character: [%s]", rockTypeName));
        }

        if (!ROCKS.add(this)) {
            throw new RuntimeException(String.format("Rock: [%s] already exists!", rockTypeName));
        }
    }

    public Rock(@Nonnull String rockTypeName, @Nonnull RockCategory rockCategory) {
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

    public static Set<Rock> getRockTypes() {
        return ROCKS;
    }

    public static Rock valueOf(int i) {
        var values = new Rock[ROCKS.size()];
        values = ROCKS.toArray(values);

        return i >= 0 && i < values.length ? values[i] : values[i % values.length];
    }

    public static int indexOf(Rock rock) {
        return new ArrayList<>(ROCKS).indexOf(rock);
    }
}
