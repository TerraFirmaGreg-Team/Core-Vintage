package su.terrafirmagreg.api.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.cleanroommc.modularui.api.drawable.IInterpolation;
import com.cleanroommc.modularui.utils.Interpolation;

import lombok.experimental.UtilityClass;

import java.util.function.ToIntFunction;

@UtilityClass
@SuppressWarnings("unused")
public final class ColourUtils {


  public static int addRGBComponents(int colour, int r, int g, int b) {
    r = getRed(colour) + r;
    g = getGreen(colour) + g;
    b = getBlue(colour) + b;

    r = Math.min(r, 255);
    g = Math.min(g, 255);
    b = Math.min(b, 255);

    return (r & 0x0ff) << 16 | (g & 0x0ff) << 8 | b & 0x0ff;
  }

  public static int getRed(int argb) {
    return argb >> 16 & 255;
  }

  public static int getGreen(int argb) {
    return argb >> 8 & 255;
  }

  public static int getBlue(int argb) {
    return argb & 255;
  }

  public static int multiplyRGBComponents(int colour, float factor) {
    int r = (int) (getRed(colour) * factor);
    int g = (int) (getGreen(colour) * factor);
    int b = (int) (getBlue(colour) * factor);

    r = Math.min(r, 255);
    g = Math.min(g, 255);
    b = Math.min(b, 255);

    return (r & 0x0ff) << 16 | (g & 0x0ff) << 8 | b & 0x0ff;
  }

  public static float getRedAsFloat(int colour) {
    return getRed(colour) / 255.0F;
  }

  public static float getGreenAsFloat(int colour) {
    return getGreen(colour) / 255.0F;
  }

  public static float getBlueAsFloat(int colour) {
    return getBlue(colour) / 255.0F;
  }

  public static int rgb(int red, int green, int blue) {
    return argb(red, green, blue, 255);
  }

  public static int argb(int red, int green, int blue, int alpha) {
    return (alpha & 255) << 24 | (red & 255) << 16 | (green & 255) << 8 | blue & 255;
  }

  public static int rgba(float red, float green, float blue, float alpha) {
    return rgba((int) (red * 255.0F), (int) (green * 255.0F), (int) (blue * 255.0F), (int) (alpha * 255.0F));
  }

  public static int rgba(int red, int green, int blue, int alpha) {
    return (red & 255) << 24 | (green & 255) << 16 | (blue & 255) << 8 | alpha & 255;
  }

  public static int rgb(float red, float green, float blue) {
    return argb(red, green, blue, 1.0F);
  }

  public static int argb(float red, float green, float blue, float alpha) {
    return argb((int) (red * 255.0F), (int) (green * 255.0F), (int) (blue * 255.0F), (int) (alpha * 255.0F));
  }

  public static int ofHSV(float hue, float saturation, float value) {
    return ofHSV(hue, saturation, value, 1.0F);
  }

  public static int ofHSV(float hue, float saturation, float value, float alpha) {
    hue %= 360.0F;
    if (hue < 0.0F) {
      hue += 360.0F;
    }

    saturation = MathHelper.clamp(saturation, 0.0F, 1.0F);
    value = MathHelper.clamp(value, 0.0F, 1.0F);
    alpha = MathHelper.clamp(alpha, 0.0F, 1.0F);
    float c = value * saturation;
    float x = c * (1.0F - Math.abs(hue / 60.0F % 2.0F - 1.0F));
    float m = value - c;
    return ofHxcm(hue, c, x, m, alpha);
  }

  private static int ofHxcm(float hue, float c, float x, float m, float alpha) {
    if (hue < 60.0F) {
      return argb(c + m, x + m, m, alpha);
    } else if (hue < 120.0F) {
      return argb(x + m, c + m, m, alpha);
    } else if (hue < 180.0F) {
      return argb(m, c + m, x + m, alpha);
    } else if (hue < 240.0F) {
      return argb(m, x + m, c + m, alpha);
    } else {
      return hue < 300.0F ? argb(x + m, m, c + m, alpha) : argb(c + m, m, x + m, alpha);
    }
  }

  public static int ofHSL(float hue, float saturation, float lightness) {
    return ofHSL(hue, saturation, lightness, 1.0F);
  }

  public static int ofHSL(float hue, float saturation, float lightness, float alpha) {
    hue %= 360.0F;
    if (hue < 0.0F) {
      hue += 360.0F;
    }

    saturation = MathHelper.clamp(saturation, 0.0F, 1.0F);
    lightness = MathHelper.clamp(lightness, 0.0F, 1.0F);
    alpha = MathHelper.clamp(alpha, 0.0F, 1.0F);
    float c = (1.0F - Math.abs(2.0F * lightness - 1.0F)) * saturation;
    float x = c * (1.0F - Math.abs(hue / 60.0F % 2.0F - 1.0F));
    float m = lightness - c / 2.0F;
    return ofHxcm(hue, c, x, m, alpha);
  }

  public static int ofCMYK(float cyan, float magenta, float yellow, float black) {
    return ofCMYK(cyan, magenta, yellow, black, 1.0F);
  }

  public static int ofCMYK(float cyan, float magenta, float yellow, float black, float alpha) {
    float oneMinusBlack = 1.0F - black;
    return argb((1.0F - cyan) * oneMinusBlack, (1.0F - magenta) * oneMinusBlack, (1.0F - yellow) * oneMinusBlack, alpha);
  }

  public static int withRed(int argb, float red) {
    return withRed(argb, (int) (red * 255.0F));
  }

  public static int withRed(int argb, int red) {
    argb &= -16711681;
    return argb | red << 16;
  }

  public static int withGreen(int argb, float green) {
    return withGreen(argb, (int) (green * 255.0F));
  }

  public static int withGreen(int argb, int green) {
    argb &= -65281;
    return argb | green << 8;
  }

  public static int withBlue(int argb, float blue) {
    return withBlue(argb, (int) (blue * 255.0F));
  }

  public static int withBlue(int argb, int blue) {
    argb &= -256;
    return argb | blue;
  }

  public static int withAlpha(int argb, float alpha) {
    return withAlpha(argb, (int) (alpha * 255.0F));
  }

  public static int withAlpha(int argb, int alpha) {
    argb &= 16777215;
    return argb | alpha << 24;
  }

  public static int withHSVHue(int argb, float hue) {
    return ofHSV(hue, getHSVSaturation(argb), getValue(argb), getAlphaF(argb));
  }

  public static float getHSVSaturation(int argb) {
    float r = getRedF(argb);
    float g = getGreenF(argb);
    float b = getBlueF(argb);
    float min = Math.min(r, Math.min(g, b));
    float max = Math.max(r, Math.max(g, b));
    return max == 0.0F ? 0.0F : (max - min) / max;
  }

  public static float getValue(int argb) {
    float r = getRedF(argb);
    float g = getGreenF(argb);
    float b = getBlueF(argb);
    return Math.max(r, Math.max(g, b));
  }

  public static int withHSVSaturation(int argb, float saturation) {
    return ofHSV(getHue(argb), saturation, getValue(argb), getAlphaF(argb));
  }

  public static float getHue(int argb) {
    float r = getRedF(argb);
    float g = getGreenF(argb);
    float b = getBlueF(argb);
    if (r == g && r == b) {
      return 0.0F;
    } else {
      float min = Math.min(r, Math.min(g, b));
      float hue = 0.0F;
      if (r >= g && r >= b) {
        hue = (g - b) / (r - min) % 6.0F;
      } else if (g >= r && g >= b) {
        hue = (b - r) / (g - min) + 2.0F;
      } else if (b >= r && b >= g) {
        hue = (r - g) / (b - min) + 4.0F;
      }

      hue *= 60.0F;
      if (hue < 0.0F) {
        hue += 360.0F;
      }

      return hue;
    }
  }

  public static int withValue(int argb, float value) {
    return ofHSV(getHue(argb), getHSVSaturation(argb), value, getAlphaF(argb));
  }

  public static int withHSLHue(int argb, float hue) {
    return ofHSL(hue, getHSLSaturation(argb), getLightness(argb), getAlphaF(argb));
  }

  public static float getHSLSaturation(int argb) {
    float r = getRedF(argb);
    float g = getGreenF(argb);
    float b = getBlueF(argb);
    float min = Math.min(r, Math.min(g, b));
    float max = Math.max(r, Math.max(g, b));
    return (max - min) / (1.0F - Math.abs(max + min - 1.0F));
  }

  public static float getLightness(int argb) {
    float r = getRedF(argb);
    float g = getGreenF(argb);
    float b = getBlueF(argb);
    float min = Math.min(r, Math.min(g, b));
    float max = Math.max(r, Math.max(g, b));
    return (max + min) / 2.0F;
  }

  public static int withHSLSaturation(int argb, float saturation) {
    return ofHSL(getHue(argb), saturation, getLightness(argb), getAlphaF(argb));
  }

  public static int withLightness(int argb, float lightness) {
    return ofHSL(getHue(argb), getHSLSaturation(argb), lightness, getAlphaF(argb));
  }

  public static int withCyan(int argb, float cyan) {
    return ofCMYK(cyan, getMagenta(argb), getYellow(argb), getBlack(argb), getAlphaF(argb));
  }

  public static float getMagenta(int argb) {
    float r = getRedF(argb);
    float g = getGreenF(argb);
    float b = getBlueF(argb);
    float oneMinusBlack = Math.max(r, Math.max(g, b));
    return oneMinusBlack == 0.0F ? 0.0F : 1.0F - g / oneMinusBlack;
  }

  public static float getYellow(int argb) {
    float r = getRedF(argb);
    float g = getGreenF(argb);
    float b = getBlueF(argb);
    float oneMinusBlack = Math.max(r, Math.max(g, b));
    return oneMinusBlack == 0.0F ? 0.0F : 1.0F - b / oneMinusBlack;
  }

  public static float getBlack(int argb) {
    float r = getRedF(argb);
    float g = getGreenF(argb);
    float b = getBlueF(argb);
    return 1.0F - Math.max(r, Math.max(g, b));
  }

  public static int withMagenta(int argb, float magenta) {
    return ofCMYK(getCyan(argb), magenta, getYellow(argb), getBlack(argb), getAlphaF(argb));
  }

  public static float getCyan(int argb) {
    float r = getRedF(argb);
    float g = getGreenF(argb);
    float b = getBlueF(argb);
    float oneMinusBlack = Math.max(r, Math.max(g, b));
    return oneMinusBlack == 0.0F ? 0.0F : 1.0F - r / oneMinusBlack;
  }

  public static int withYellow(int argb, float yellow) {
    return ofCMYK(getCyan(argb), getMagenta(argb), yellow, getBlack(argb), getAlphaF(argb));
  }

  public static int withBlack(int argb, float black) {
    return ofCMYK(getCyan(argb), getMagenta(argb), getYellow(argb), black, getAlphaF(argb));
  }

  public static int[] getRGBValues(int argb) {
    return new int[]{getRed(argb), getGreen(argb), getBlue(argb)};
  }

  public static int[] getARGBValues(int argb) {
    return new int[]{getRed(argb), getGreen(argb), getBlue(argb), getAlpha(argb)};
  }

  public static int getAlpha(int argb) {
    return argb >> 24 & 255;
  }

  public static float[] getHSVValues(int argb) {
    return new float[]{getHue(argb), getHSVSaturation(argb), getValue(argb)};
  }

  public static float[] getHSLValues(int argb) {
    return new float[]{getHue(argb), getHSLSaturation(argb), getLightness(argb)};
  }

  public static float[] getCMYKValues(int argb) {
    return new float[]{getCyan(argb), getMagenta(argb), getYellow(argb), getBlack(argb)};
  }

  public static int rgbaToArgb(int rgba) {
    return argb(getAlpha(rgba), getRed(rgba), getGreen(rgba), getBlue(rgba));
  }

  public static int argbToRgba(int argb) {
    return rgba(getRed(argb), getGreen(argb), getBlue(argb), getAlpha(argb));
  }

  public static int invert(int argb) {
    return argb(255 - getRed(argb), 255 - getGreen(argb), 255 - getBlue(argb), getAlpha(argb));
  }

  public static int multiply(int argb, float factor, boolean multiplyAlpha) {
    return argb(getRedF(argb) * factor, getGreenF(argb) * factor, getBlueF(argb) * factor, multiplyAlpha ? getAlphaF(argb) * factor : getAlphaF(argb));
  }

  public static float getRedF(int argb) {
    return (float) getRed(argb) / 255.0F;
  }

  public static float getGreenF(int argb) {
    return (float) getGreen(argb) / 255.0F;
  }

  public static float getBlueF(int argb) {
    return (float) getBlue(argb) / 255.0F;
  }

  public static float getAlphaF(int argb) {
    return (float) getAlpha(argb) / 255.0F;
  }

  public static int average(int... colors) {
    int r = 0;
    int g = 0;
    int b = 0;
    int a = 0;
    int[] var5 = colors;
    int var6 = colors.length;

    for (int var7 = 0; var7 < var6; ++var7) {
      int color = var5[var7];
      r += getRed(color);
      g += getGreen(color);
      b += getBlue(color);
      a += getAlpha(color);
    }

    return argb(r / colors.length, g / colors.length, b / colors.length, a / colors.length);
  }

  @SafeVarargs
  public static <T> int average(ToIntFunction<T> colorFunction, T... colorHolders) {
    int r = 0;
    int g = 0;
    int b = 0;
    int a = 0;
    Object[] var6 = colorHolders;
    int var7 = colorHolders.length;

    for (int var8 = 0; var8 < var7; ++var8) {
      T colorHolder = (T) var6[var8];
      int color = colorFunction.applyAsInt(colorHolder);
      r += getRed(color);
      g += getGreen(color);
      b += getBlue(color);
      a += getAlpha(color);
    }

    return argb(r / colorHolders.length, g / colorHolders.length, b / colorHolders.length, a / colorHolders.length);
  }

  public static int interpolate(int color1, int color2, float value) {
    return interpolate(Interpolation.LINEAR, color1, color2, value);
  }

  public static int interpolate(IInterpolation curve, int color1, int color2, float value) {
    value = MathHelper.clamp(value, 0.0F, 1.0F);
    int r = (int) curve.interpolate((float) getRed(color1), (float) getRed(color2), value);
    int g = (int) curve.interpolate((float) getGreen(color1), (float) getGreen(color2), value);
    int b = (int) curve.interpolate((float) getBlue(color1), (float) getBlue(color2), value);
    int a = (int) curve.interpolate((float) getAlpha(color1), (float) getAlpha(color2), value);
    return argb(r, g, b, a);
  }

  @SideOnly(Side.CLIENT)
  public static void setGlColor(int color) {
    if (color == 0) {
      GlStateManager.color(0.0F, 0.0F, 0.0F, 0.0F);
    } else {
      float a = getAlphaF(color);
      if (a == 0.0F) {
        a = 1.0F;
      }

      GlStateManager.color(getRedF(color), getGreenF(color), getBlueF(color), a);
    }
  }

  @SideOnly(Side.CLIENT)
  public static void resetGlColor() {
    GlStateManager.colorMask(true, true, true, true);
    setGlColorOpaque(-1);
  }

  @SideOnly(Side.CLIENT)
  public static void setGlColorOpaque(int color) {
    if (color == 0) {
      GlStateManager.color(0.0F, 0.0F, 0.0F, 0.0F);
    } else {
      GlStateManager.color(getRedF(color), getGreenF(color), getBlueF(color), 1.0F);
    }
  }
}
