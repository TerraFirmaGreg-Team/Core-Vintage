package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.device.object.item.MetaItemDevice;
import su.terrafirmagreg.modules.device.object.item.tools.behaviors.ChiselBehavior;
import su.terrafirmagreg.modules.device.object.item.tools.behaviors.PropickBehavior;

import gregtech.api.GTValues;
import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ItemGTTool;
import gregtech.common.items.ToolItems;
import gregtech.core.sound.GTSoundEvents;

public final class ItemsDevice {

  public static IGTTool TONGS;
  public static IGTTool TUYERE;
  public static IGTTool CHISEL;
  public static IGTTool PROPICK;
  public static MetaItemDevice META_ITEM;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_SWORD;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_PICKAXE;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_SHOVEL;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_AXE;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_HOE;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_SENSE;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_FILE;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_HAMMER;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_SAW;
  public static MetaItem<?>.MetaValueItem SHAPE_MOLD_KNIFE;
  public static MetaItem<?>.MetaValueItem SHAPE_MOLD_PROPICK;
  public static MetaItem<?>.MetaValueItem SHAPE_EXTRUDER_CHISEL;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_1;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_2;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_3;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_4;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_5;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_6;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_7;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_8;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_9;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_10;
  public static MetaItem<?>.MetaValueItem ROCKET_BODY_TIER_11;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_1;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_2;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_3;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_4;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_5;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_6;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_7;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_8;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_9;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_10;
  public static MetaItem<?>.MetaValueItem ALLOY_INGOT_TIER_11;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_1;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_2;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_3;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_4;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_5;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_6;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_7;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_8;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_9;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_10;
  public static MetaItem<?>.MetaValueItem ROCKET_CONTROL_COMPUTER_TIER_11;
  public static MetaItem<?>.MetaValueItem SCHEMATIC_BLANK;
  public static MetaItem<?>.MetaValueItem LANDER_TIER_1;
  public static MetaItem<?>.MetaValueItem LANDER_TIER_2;
  public static MetaItem<?>.MetaValueItem LANDER_TIER_3;
  public static MetaItem<?>.MetaValueItem FLUID_HOUSING;
  public static MetaItem<?>.MetaValueItem WOODEN_BUCKET_WITH_SALT;
  public static MetaItem<?>.MetaValueItem ASTRO_MINER_COMPUTER;
  public static MetaItem<?>.MetaValueItem MOON_BUGGY_COMPUTER;
  public static MetaItem<?>.MetaValueItem MARS_ROVER_COMPUTER;
  public static MetaItem<?>.MetaValueItem VENUS_ROVER_COMPUTER;
  public static MetaItem<?>.MetaValueItem CARGO_ROCKET_COMPUTER;

  public static void onRegister(IRegistryManager registry) {
    META_ITEM = new MetaItemDevice();

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
