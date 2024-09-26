package su.terrafirmagreg.modules.rock.api.types.category;

import net.minecraft.util.text.TextFormatting;

public class RockCategoryHandler {

  public static void init() {

    RockCategories.IGNEOUS_INTRUSIVE = RockCategory
      .builder("igneous_intrusive")
      .setLayer(true, true, true)
      .setCaveMod(-0.4f, 0f)
      .setHardnessModifier(0.2f)
      .setTextFormatting(TextFormatting.RED)
      .setAnvil()
      .build();

    RockCategories.IGNEOUS_EXTRUSIVE = RockCategory
      .builder("igneous_extrusive")
      .setLayer(true, true, true)
      .setCaveMod(-0.5f, 0f)
      .setHardnessModifier(0f)
      .setTextFormatting(TextFormatting.DARK_RED)
      .setAnvil()
      .build();

    RockCategories.METAMORPHIC = RockCategory
      .builder("metamorphic")
      .setLayer(true, true, false)
      .setCaveMod(0.2f, 0f)
      .setHardnessModifier(-0.2f)
      .setTextFormatting(TextFormatting.AQUA)
      .build();

    RockCategories.SEDIMENTARY = RockCategory
      .builder("sedimentary")
      .setLayer(true, false, false)
      .setCaveMod(0.3f, 5f)
      .setHardnessModifier(-0.4f)
      .setTextFormatting(TextFormatting.GREEN)
      .build();
  }
}

