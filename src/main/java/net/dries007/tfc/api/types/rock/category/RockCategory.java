package net.dries007.tfc.api.types.rock.category;

import net.dries007.tfc.api.types.rock.type.RockType;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class RockCategory {
    private static final Set<RockCategory> rockCategories = new HashSet<>();

    private final String rockCategoryName;
    private final boolean layer1;
    private final boolean layer2;
    private final boolean layer3;
    private final float caveGenMod;
    private final float caveFreqMod;
    private final float hardnessModifier;

    @Nonnull
    private final TextFormatting textFormatting;
    private final boolean hasAnvil;


    RockCategory(@Nonnull String rockCategoryName, boolean layer1, boolean layer2, boolean layer3, float caveGenMod, float caveFreqMod, float hardnessModifier, @Nonnull TextFormatting textFormatting, boolean hasAnvil) {
        this.rockCategoryName = rockCategoryName;
        this.layer1 = layer1;
        this.layer2 = layer2;
        this.layer3 = layer3;
        this.caveGenMod = caveGenMod;
        this.caveFreqMod = caveFreqMod;
        this.hardnessModifier = hardnessModifier;
        this.textFormatting = textFormatting;
        this.hasAnvil = hasAnvil;

        if (rockCategoryName.isEmpty()) {
            throw new RuntimeException(String.format("RockCategory name must contain any character: [%s]", rockCategoryName));
        }

        if (!rockCategories.add(this)) {
            throw new RuntimeException(String.format("RockCategory: [%s] already exists!", rockCategoryName));
        }
    }
    @Override
    public String toString() {
        return rockCategoryName;
    }

    public float getCaveGenMod() {
        return caveGenMod;
    }

    public float getCaveFreqMod() {
        return caveFreqMod;
    }

    public float getHardnessModifier() {
        return hardnessModifier;
    }

    @Nonnull
    public TextFormatting getTextFormatting() {
        return textFormatting;
    }

    public boolean hasAnvil() {
        return hasAnvil;
    }

    public String getLocalizedName() {
        return textFormatting + new TextComponentTranslation(String.format("rockcategory.%s.name", this)).getFormattedText();
    }

    public static Set<RockCategory> getRockCategories() {
        return rockCategories;
    }

    public enum Layer implements Predicate<RockType> {
        BOTTOM(3, x -> x.getRockCategory().layer3),
        MIDDLE(2, x -> x.getRockCategory().layer2),
        TOP(1, x -> x.getRockCategory().layer1);

        public final int layer;
        private final Predicate<RockType> filter;

        Layer(int layer, Predicate<RockType> filter) {
            this.layer = layer;
            this.filter = filter;
        }

        @Override
        public boolean test(RockType rockType) {
            return filter.test(rockType);
        }
    }
}
