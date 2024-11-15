package su.terrafirmagreg.modules.core.capabilities.heat.spi;

import su.terrafirmagreg.api.data.Unicode;
import su.terrafirmagreg.api.data.enums.OreTooltipMode;
import su.terrafirmagreg.api.util.TranslatorUtil;
import su.terrafirmagreg.modules.core.ConfigCore;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

public enum Heat {
  WARMING(1f, 80f, TextFormatting.GRAY, TextFormatting.DARK_GRAY),
  HOT(80f, 210f, TextFormatting.GRAY, TextFormatting.DARK_GRAY),
  VERY_HOT(210f, 480f, TextFormatting.GRAY, TextFormatting.DARK_GRAY),
  FAINT_RED(480f, 580f, TextFormatting.DARK_RED),
  DARK_RED(580f, 730f, TextFormatting.DARK_RED),
  BRIGHT_RED(730f, 930f, TextFormatting.RED),
  ORANGE(930f, 1100f, TextFormatting.GOLD),
  YELLOW(1100f, 1300f, TextFormatting.YELLOW),
  YELLOW_WHITE(1300f, 1400f, TextFormatting.YELLOW),
  WHITE(1400f, 1500f, TextFormatting.WHITE),
  BRILLIANT_WHITE(1500f, 1601f, TextFormatting.WHITE);

  private static final Heat[] VALUES = values();
  private final TextFormatting format, alternate;
  @Getter
  private final float min;
  @Getter
  private final float max;

  Heat(float min, float max, TextFormatting format) {
    this(min, max, format, format);
  }

  Heat(float min, float max, TextFormatting format, TextFormatting alternate) {
    this.min = min;
    this.max = max;
    this.format = format;
    this.alternate = alternate;
  }

  public static float maxVisibleTemperature() {
    return BRILLIANT_WHITE.getMax();
  }

  @Nullable
  public static String getTooltip(float temperature) {
    Heat heat = Heat.getHeat(temperature);
    String tooltip = getTooltipColorless(temperature);
    if (tooltip != null && heat != null) {
      tooltip = heat.format + tooltip;
      if (ConfigCore.MISC.HEAT.oreTooltipMode == OreTooltipMode.ADVANCED) {
        tooltip = tooltip + " : " + I18n.format("tfc.tooltip.melttemp", Math.round(temperature));
      }

    }
    return tooltip;
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

  @Nullable
  public static String getTooltipColorless(float temperature) {
    Heat heat = Heat.getHeat(temperature);
    if (heat != null) {
      StringBuilder b = new StringBuilder();
      b.append(I18n.format(TranslatorUtil.getEnumName(heat)));
      if (heat != Heat.BRILLIANT_WHITE) {
        for (int i = 1; i <= 4; i++) {
          if (temperature <= heat.getMin() + ((float) i * 0.2f) * (heat.getMax() - heat.getMin())) {
            continue;
          }
          b.append(Unicode.STAR);
        }
      }
      return b.toString();
    }
    return null;
  }

  @Nullable
  public static String getTooltipAlternate(float temperature) {
    Heat heat = Heat.getHeat(temperature);
    String tooltip = getTooltipColorless(temperature);
    if (tooltip != null && heat != null) {
      tooltip = heat.alternate + tooltip;
      if (ConfigCore.MISC.HEAT.oreTooltipMode == OreTooltipMode.ADVANCED) {
        tooltip = tooltip + " : " + I18n.format("tfc.tooltip.melttemp", Math.round(temperature));
      }
    }
    return tooltip;
  }

}
