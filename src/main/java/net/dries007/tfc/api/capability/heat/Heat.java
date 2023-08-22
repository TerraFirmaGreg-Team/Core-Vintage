package net.dries007.tfc.api.capability.heat;

import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.config.OreTooltipMode;
import net.dries007.tfc.util.Helpers;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nullable;

public enum Heat {
    WARMING(1f, 80f, TextFormatting.GRAY, TextFormatting.DARK_GRAY, 5592405),
    HOT(80f, 210f, TextFormatting.GRAY, TextFormatting.DARK_GRAY, 5592405),
    VERY_HOT(210f, 480f, TextFormatting.GRAY, TextFormatting.DARK_GRAY, 5592405),
    FAINT_RED(480f, 580f, TextFormatting.DARK_RED, 11141120),
    DARK_RED(580f, 730f, TextFormatting.DARK_RED, 11141120),
    BRIGHT_RED(730f, 930f, TextFormatting.RED, 16733525),
    ORANGE(930f, 1100f, TextFormatting.GOLD, 16755200),
    YELLOW(1100f, 1300f, TextFormatting.YELLOW, 16777045),
    YELLOW_WHITE(1300f, 1400f, TextFormatting.YELLOW, 16777045),
    WHITE(1400f, 1500f, TextFormatting.WHITE, 16777215),
    BRILLIANT_WHITE(1500f, 1601f, TextFormatting.WHITE, 16777215);

    private static final Heat[] VALUES = values();
    final TextFormatting format, alternate;
    private final float min;
    private final float max;
    private final int intColor;

    Heat(float min, float max, TextFormatting format, TextFormatting alternate, int intColor) {
        this.min = min;
        this.max = max;
        this.format = format;
        this.alternate = alternate;
        this.intColor = intColor;
    }

    Heat(float min, float max, TextFormatting format, int intColor) {
        this(min, max, format, format, intColor);
    }

    public float getMin() {
        return min;
    }

    public float getMax() {
        return max;
    }

    public int getIntColor() {
        return intColor;
    }

    public static float maxVisibleTemperature() {
        return BRILLIANT_WHITE.getMax();
    }

    @Nullable
    public static Heat getHeat(float temperature) {
        for (Heat heat : VALUES) {
            if (heat.min <= temperature && temperature < heat.max) {
                return heat;
            }
        }
        if (temperature > BRILLIANT_WHITE.max) {
            // Default to "hotter than brilliant white" for max
            return BRILLIANT_WHITE;
        }
        return null;
    }

    public static int getColor(float temperature) {
        var heat = getHeat(temperature);

        if (heat == null) return 0;

        return heat.getIntColor();
    }

    @Nullable
    public static String getTooltipColorless(float temperature) {
        Heat heat = Heat.getHeat(temperature);
        if (heat != null) {
            StringBuilder b = new StringBuilder();
            b.append(I18n.format(Helpers.getEnumName(heat)));
            if (heat != Heat.BRILLIANT_WHITE) {
                for (int i = 1; i <= 4; i++) {
                    if (temperature <= heat.getMin() + ((float) i * 0.2f) * (heat.getMax() - heat.getMin()))
                        continue;
                    b.append("\u2605");
                }
            }
            return b.toString();
        }
        return null;
    }

    @Nullable
    public static String getTooltip(float temperature) {
        Heat heat = Heat.getHeat(temperature);
        String tooltip = getTooltipColorless(temperature);
        if (tooltip != null && heat != null) {
            tooltip = heat.format + tooltip;
            if (ConfigTFC.Client.TOOLTIP.oreTooltipMode == OreTooltipMode.ADVANCED) {
                tooltip = tooltip + " : " + I18n.format("tfc.tooltip.melttemp", Math.round(temperature));
            }

        }
        return tooltip;
    }

    @Nullable
    public static String getTooltipAlternate(float temperature) {
        Heat heat = Heat.getHeat(temperature);
        String tooltip = getTooltipColorless(temperature);
        if (tooltip != null && heat != null) {
            tooltip = heat.alternate + tooltip;
            if (ConfigTFC.Client.TOOLTIP.oreTooltipMode == OreTooltipMode.ADVANCED) {
                tooltip = tooltip + " : " + I18n.format("tfc.tooltip.melttemp", Math.round(temperature));
            }
        }
        return tooltip;
    }


}
