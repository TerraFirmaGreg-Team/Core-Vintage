package net.dries007.tfc.api.types2;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

@MethodsReturnNonnullByDefault
public enum StoneCategory implements IStringSerializable {
    IGNEOUS(TextFormatting.RED, 1.1F),
    METAMORPHIC(TextFormatting.AQUA, 1.5F),
    SEDIMENTARY(TextFormatting.GREEN, 1.9F);

    private final TextFormatting textFormatting;
    private final float hardnessMultiplier;

    StoneCategory(TextFormatting textFormatting, float hardnessMultiplier) {
        this.textFormatting = textFormatting;
        this.hardnessMultiplier = hardnessMultiplier;
    }

    public float getHardnessMultiplier() {
        return hardnessMultiplier;
    }

    @Override
    public String getName() {
        return name().toLowerCase();
    }

    public String getLocalizedName() {
        return textFormatting + new TextComponentTranslation(String.format("stonecategory.%s.name", this.getName())).getFormattedText();
    }

}
