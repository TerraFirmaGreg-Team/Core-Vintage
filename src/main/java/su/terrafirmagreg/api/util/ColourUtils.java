package su.terrafirmagreg.api.util;

import net.minecraft.client.renderer.GlStateManager;

@SuppressWarnings("unused")
public final class ColourUtils {

    private ColourUtils() {
        throw new IllegalAccessError("Utility class");
    }

    /**
     * Устанавливает цвет.
     *
     * @param color цвет в формате RGB
     */
    public static void setColor(int color) {
        float red = ((color >> 16) & 0xFF) / 255.0F;
        float green = ((color >> 8) & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        GlStateManager.color(red, green, blue, 0.4F);
    }

    public static void clearColor() {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static int addRGBComponents(int colour, int r, int g, int b) {
        r = getRed(colour) + r;
        g = getGreen(colour) + g;
        b = getBlue(colour) + b;

        r = r <= 255 ? r : 255;
        g = g <= 255 ? g : 255;
        b = b <= 255 ? b : 255;

        return (r & 0x0ff) << 16 | (g & 0x0ff) << 8 | b & 0x0ff;
    }

    public static int multiplyRGBComponents(int colour, float factor) {
        int r = (int) (getRed(colour) * factor);
        int g = (int) (getGreen(colour) * factor);
        int b = (int) (getBlue(colour) * factor);

        r = r <= 255 ? r : 255;
        g = g <= 255 ? g : 255;
        b = b <= 255 ? b : 255;

        return (r & 0x0ff) << 16 | (g & 0x0ff) << 8 | b & 0x0ff;
    }

    public static int getRed(int colour) {
        return (colour & 0xff0000) >> 16;
    }

    public static int getGreen(int colour) {
        return (colour & 0xff00) >> 8;
    }

    public static int getBlue(int colour) {
        return colour & 0xff;
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
}
