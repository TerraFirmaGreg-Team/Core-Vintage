package net.dries007.tfc.api.types.crop.category;

import net.minecraft.util.text.TextFormatting;

public class CropTypeHandler {

    public static void init() {
        CropTypes.SIMPLE = new CropType("simple", TextFormatting.GREEN);
        CropTypes.PICKABLE = new CropType("pickable", TextFormatting.YELLOW);
        CropTypes.SPREADING = new CropType("spreading", TextFormatting.BLUE);
    }
}
