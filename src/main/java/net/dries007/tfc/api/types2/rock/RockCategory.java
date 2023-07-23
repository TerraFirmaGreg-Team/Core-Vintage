package net.dries007.tfc.api.types2.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.Rock;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

import java.util.function.Predicate;

@MethodsReturnNonnullByDefault
public enum RockCategory implements IStringSerializable {
    IGNEOUS_INTRUSIVE(TextFormatting.RED, true, true, true, -0.4f, 0f, 0.2f, true),
    IGNEOUS_EXTRUSIVE(TextFormatting.DARK_RED, true, true, true, -0.5f, 0f, 0f, true),
    METAMORPHIC(TextFormatting.AQUA, true, true, false, 0.2f, 0f, -0.2f,  false),
    SEDIMENTARY(TextFormatting.GREEN, true, false, false, 0.3f, 5f, -0.4f, false);

    private final TextFormatting textFormatting;

    private final boolean layer1;
    private final boolean layer2;
    private final boolean layer3;
    private final float caveGenMod;
    private final float caveFreqMod;
    private final float hardnessModifier;
    private final boolean hasAnvil;

    /**
     * A rock category.
     *
     * @param textFormatting        The resource location of the rock.
     * @param caveGenMod            A modifier for cave generation. Default 0, range -0.5 <> 0.5
     * @param caveFreqMod           Another modifier for cave generation. Default 0, sedimentary uses +5
     * @param hardnessModifier      How hard this type is (how slower is to break blocks)
     * @param hasAnvil              if this rock should be able to create a stone anvil
     */

    RockCategory(TextFormatting textFormatting, boolean layer1, boolean layer2, boolean layer3, float caveGenMod, float caveFreqMod, float hardnessModifier, boolean hasAnvil) {
        this.textFormatting = textFormatting;
        this.layer1 = layer1;
        this.layer2 = layer2;
        this.layer3 = layer3;
        this.caveGenMod = caveGenMod;
        this.caveFreqMod = caveFreqMod;
        this.hardnessModifier = hardnessModifier;
        this.hasAnvil = hasAnvil;
    }

    public float getCaveGenMod()
    {
        return caveGenMod;
    }

    public float getCaveFreqMod()
    {
        return caveFreqMod;
    }

    public float getHardnessModifier() {
        return hardnessModifier;
    }

    public boolean hasAnvil()
    {
        return hasAnvil;
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }

    public String getLocalizedName() {
        return textFormatting + new TextComponentTranslation(String.format("stonecategory.%s.name", this.getName())).getFormattedText();
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
