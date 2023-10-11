package net.dries007.tfc.module.rock.api.types.category;

import net.minecraft.util.text.TextFormatting;

import static net.dries007.tfc.module.rock.api.types.category.RockCategories.*;

public class RockCategoryHandler {

    public static void init() {
        RockCategories.IGNEOUS_INTRUSIVE = new RockCategory("igneous_intrusive", true, true, true, -0.4f, 0f, 0.2f, TextFormatting.RED, true);
        RockCategories.IGNEOUS_EXTRUSIVE = new RockCategory("igneous_extrusive", true, true, true, -0.5f, 0f, 0f, TextFormatting.DARK_RED, true);
        RockCategories.METAMORPHIC = new RockCategory("metamorphic", true, true, false, 0.2f, 0f, -0.2f, TextFormatting.AQUA, false);
        RockCategories.SEDIMENTARY = new RockCategory("sedimentary", true, false, false, 0.3f, 5f, -0.4f, TextFormatting.GREEN, false);
    }
}
