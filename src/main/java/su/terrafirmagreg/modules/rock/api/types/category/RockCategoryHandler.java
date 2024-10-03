package su.terrafirmagreg.modules.rock.api.types.category;

import net.minecraft.util.text.TextFormatting;

import net.dries007.tfc.objects.ToolMaterials;

public class RockCategoryHandler {

  public static void init() {

    RockCategories.IGNEOUS_INTRUSIVE = RockCategory
      .builder("igneous_intrusive")
      .layer(true, true, true)
      .caveMod(-0.4f, 0f)
      .hardnessModifier(0.2f)
      .textFormatting(TextFormatting.RED)
      .toolMaterial(ToolMaterials.IGNEOUS_INTRUSIVE)
      .hasAnvil()
      .build();

    RockCategories.IGNEOUS_EXTRUSIVE = RockCategory
      .builder("igneous_extrusive")
      .layer(true, true, true)
      .caveMod(-0.5f, 0f)
      .hardnessModifier(0f)
      .textFormatting(TextFormatting.DARK_RED)
      .toolMaterial(ToolMaterials.IGNEOUS_EXTRUSIVE)
      .hasAnvil()
      .build();

    RockCategories.METAMORPHIC = RockCategory
      .builder("metamorphic")
      .layer(true, true, false)
      .caveMod(0.2f, 0f)
      .hardnessModifier(-0.2f)
      .textFormatting(TextFormatting.AQUA)
      .toolMaterial(ToolMaterials.SEDIMENTARY)
      .build();

    RockCategories.SEDIMENTARY = RockCategory
      .builder("sedimentary")
      .layer(true, false, false)
      .caveMod(0.3f, 5f)
      .hardnessModifier(-0.4f)
      .textFormatting(TextFormatting.GREEN)
      .toolMaterial(ToolMaterials.METAMORPHIC)
      .build();
  }
}

