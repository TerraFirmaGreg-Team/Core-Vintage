package su.terrafirmagreg.modules.integration.gregtech.init;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.modules.integration.gregtech.object.item.MetaItemGregTech;
import su.terrafirmagreg.modules.integration.gregtech.object.item.tools.behaviors.ChiselBehavior;
import su.terrafirmagreg.modules.integration.gregtech.object.item.tools.behaviors.PropickBehavior;

import gregtech.api.GTValues;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ItemGTTool;
import gregtech.common.items.ToolItems;
import gregtech.core.sound.GTSoundEvents;

public class ItemsGregTech {

  public static IGTTool TONGS;
  public static IGTTool TUYERE;
  public static IGTTool CHISEL;
  public static IGTTool PROPICK;
  public static MetaItemGregTech META_ITEM;

  public static void init() {
    META_ITEM = new MetaItemGregTech();

    TONGS = ToolItems.register(ItemGTTool.Builder.of(GTValues.MODID, "tongs")
      .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F))
      .oreDict("toolTongs")
      .secondaryOreDicts("craftingToolTongs")
      .sound(GTSoundEvents.SOFT_MALLET_TOOL) // todo
      .toolClasses(ToolClasses.TONGS));

    TUYERE = ToolItems.register(ItemGTTool.Builder.of(GTValues.MODID, "tuyere")
      .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F))
      .oreDict("toolTuyere")
      .secondaryOreDicts("craftingToolTuyere")
      .sound(GTSoundEvents.SOFT_MALLET_TOOL) // todo
      .toolClasses(ToolClasses.TUYERE));

    CHISEL = ToolItems.register(ItemGTTool.Builder.of(GTValues.MODID, "chisel")
      .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F)
        .behaviors(ChiselBehavior.INSTANCE))
      .oreDict("toolChisel")
      .secondaryOreDicts("craftingToolChisel")
      .sound(GTSoundEvents.SOFT_MALLET_TOOL) // todo
      .toolClasses(ToolClasses.CHISEL));

    PROPICK = ToolItems.register(ItemGTTool.Builder.of(GTValues.MODID, "propick")
      .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F)
        .behaviors(PropickBehavior.INSTANCE))
      .oreDict("toolPropick")
      .secondaryOreDicts("craftingToolPropick")
      .sound(GTSoundEvents.SOFT_MALLET_TOOL) // todo
      .toolClasses(ToolClasses.PROPICK));
  }
}
