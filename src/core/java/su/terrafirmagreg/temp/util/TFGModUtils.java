package su.terrafirmagreg.temp.util;

import su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix.OrePrefixCore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.dries007.tfc.api.types.Metal;
import org.apache.commons.lang3.StringUtils;

public class TFGModUtils {

  public static final List<TFGOrePrefixExtended> TFG_OREPREFIX_REGISTRY = Arrays.asList(
    new TFGOrePrefixExtended(OrePrefixCore.toolHeadAxe, new String[]{" X   ", "XXXX ", "XXXXX", "XXXX ", " X   "}),
    new TFGOrePrefixExtended(OrePrefixCore.toolHeadShovel, new String[]{"XXX", "XXX", "XXX", "XXX", " X "}),
    new TFGOrePrefixExtended(OrePrefixCore.toolHeadHoe, new String[]{"XXXXX", "   XX"}),
    new TFGOrePrefixExtended(OrePrefixCore.toolHeadHammer, new String[]{"XXXXX", "XXXXX", "  X  "}),
    new TFGOrePrefixExtended(OrePrefixCore.toolHeadKnife, new String[]{"X ", "XX", "XX", "XX", "XX"})
  );
  public static Map<String, String> TYPE_TO_OREPREFIX = new Object2ObjectOpenHashMap<>() {{
    // TFC - GT
    // Здесь находятся только те идентификаторы, которые невозможно обычным путем конвертировать в GT варианты
    put("PICK_HEAD", "toolHeadPickaxe");
    put("SHOVEL_HEAD", "toolHeadShovel");
    put("AXE_HEAD", "toolHeadAxe");
    put("HOE_HEAD", "toolHeadHoe");
    put("HAMMER_HEAD", "toolHeadHammer");
    put("SAW_BLADE", "toolHeadSaw");
    put("SWORD_BLADE", "toolHeadSword");
    put("SCYTHE_BLADE", "toolHeadSense");
    put("KNIFE_BLADE", "toolHeadKnife");
    put("PROPICK_HEAD", "toolHeadPropick");
    put("CHISEL_HEAD", "toolHeadChisel");
  }};

  public static String constructOredictFromTFCToGT(Metal metal, Metal.ItemType type) {
    String fullString, typeString;

    // Проверка GT вариантов из хешмапы
    if (TYPE_TO_OREPREFIX.containsKey(type.toString())) {
      typeString = TYPE_TO_OREPREFIX.get(type.toString());
    } else {
      typeString = type.toString().toLowerCase();
    }

    // Проверка имеет ли металл, нижнее подчеркивание
    String metalString = metal.toString();
    if (metalString.contains("_")) {
      String[] partsOfMaterialName = metalString.split("_");

      metalString = StringUtils.capitalize(partsOfMaterialName[0]) + StringUtils.capitalize(partsOfMaterialName[1]);
    } else {
      metalString = StringUtils.capitalize(metalString);
    }

    fullString = typeString + metalString;

    return fullString;
  }
}
