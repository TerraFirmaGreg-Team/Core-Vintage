package net.dries007.tfc.api.types.rock.category;

import net.minecraft.util.text.TextFormatting;

public class RockCategoryHandler {

    public static void init() {
        RockCategories.IgneousIntrusive = new RockCategory("igneous_intrusive", true, true, true, -0.4f, 0f, 0.2f, TextFormatting.RED, true);
        RockCategories.IgneousExtrusive = new RockCategory("igneous_extrusive", true, true, true, -0.5f, 0f, 0f, TextFormatting.DARK_RED, true);
        RockCategories.Metamorphic = new RockCategory("metamorphic", true, true, false, 0.2f, 0f, -0.2f, TextFormatting.AQUA, false);
        RockCategories.Sedimentary = new RockCategory("sedimentary", true, false, false, 0.3f, 5f, -0.4f, TextFormatting.GREEN, false);
    }
}
