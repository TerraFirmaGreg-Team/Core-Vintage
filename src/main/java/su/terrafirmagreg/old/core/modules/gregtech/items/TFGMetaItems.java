package su.terrafirmagreg.old.core.modules.gregtech.items;

import gregtech.api.items.metaitem.MetaItem;
import gregtech.api.items.metaitem.StandardMetaItem;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.stack.ItemMaterialInfo;
import gregtech.api.unification.stack.MaterialStack;

import static gregtech.api.GTValues.M;

public class TFGMetaItems extends StandardMetaItem {

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

  public TFGMetaItems() {
    super();
  }

  @Override
  public void registerSubItems() {
    // Here u can register items and behaviors and other for them
    SHAPE_EXTRUDER_SWORD = addItem(1, "shape.extruder.sword").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_EXTRUDER_PICKAXE = addItem(2, "shape.extruder.pickaxe").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_EXTRUDER_SHOVEL = addItem(3, "shape.extruder.shovel").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_EXTRUDER_AXE = addItem(4, "shape.extruder.axe").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_EXTRUDER_HOE = addItem(5, "shape.extruder.hoe").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_EXTRUDER_SENSE = addItem(6, "shape.extruder.sense").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_EXTRUDER_FILE = addItem(7, "shape.extruder.file").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_EXTRUDER_HAMMER = addItem(8, "shape.extruder.hammer").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_EXTRUDER_SAW = addItem(9, "shape.extruder.saw").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_MOLD_KNIFE = addItem(10, "shape.mold.knife").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_MOLD_PROPICK = addItem(11, "shape.mold.propick").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));
    SHAPE_EXTRUDER_CHISEL = addItem(12, "shape.extruder.chisel").setMaterialInfo(new ItemMaterialInfo(new MaterialStack(Materials.Steel, M * 4)));

    ROCKET_BODY_TIER_1 = addItem(13, "rocket.body.tier.1");
    ROCKET_BODY_TIER_2 = addItem(14, "rocket.body.tier.2");
    ROCKET_BODY_TIER_3 = addItem(15, "rocket.body.tier.3");
    ROCKET_BODY_TIER_4 = addItem(16, "rocket.body.tier.4");
    ROCKET_BODY_TIER_5 = addItem(17, "rocket.body.tier.5");
    ROCKET_BODY_TIER_6 = addItem(18, "rocket.body.tier.6");
    ROCKET_BODY_TIER_7 = addItem(19, "rocket.body.tier.7");
    ROCKET_BODY_TIER_8 = addItem(20, "rocket.body.tier.8");
    ROCKET_BODY_TIER_9 = addItem(21, "rocket.body.tier.9");

    ALLOY_INGOT_TIER_1 = addItem(24, "alloy.ingot.tier.1");
    ALLOY_INGOT_TIER_2 = addItem(25, "alloy.ingot.tier.2");
    ALLOY_INGOT_TIER_3 = addItem(26, "alloy.ingot.tier.3");
    ALLOY_INGOT_TIER_4 = addItem(27, "alloy.ingot.tier.4");
    ALLOY_INGOT_TIER_5 = addItem(28, "alloy.ingot.tier.5");
    ALLOY_INGOT_TIER_6 = addItem(29, "alloy.ingot.tier.6");
    ALLOY_INGOT_TIER_7 = addItem(30, "alloy.ingot.tier.7");
    ALLOY_INGOT_TIER_8 = addItem(31, "alloy.ingot.tier.8");
    ALLOY_INGOT_TIER_9 = addItem(32, "alloy.ingot.tier.9");

    ROCKET_CONTROL_COMPUTER_TIER_1 = addItem(35, "rocket.control.computer.tier.1");
    ROCKET_CONTROL_COMPUTER_TIER_2 = addItem(36, "rocket.control.computer.tier.2");
    ROCKET_CONTROL_COMPUTER_TIER_3 = addItem(37, "rocket.control.computer.tier.3");
    ROCKET_CONTROL_COMPUTER_TIER_4 = addItem(38, "rocket.control.computer.tier.4");
    ROCKET_CONTROL_COMPUTER_TIER_5 = addItem(39, "rocket.control.computer.tier.5");
    ROCKET_CONTROL_COMPUTER_TIER_6 = addItem(40, "rocket.control.computer.tier.6");
    ROCKET_CONTROL_COMPUTER_TIER_7 = addItem(41, "rocket.control.computer.tier.7");
    ROCKET_CONTROL_COMPUTER_TIER_8 = addItem(42, "rocket.control.computer.tier.8");
    ROCKET_CONTROL_COMPUTER_TIER_9 = addItem(43, "rocket.control.computer.tier.9");

    SCHEMATIC_BLANK = addItem(46, "schematic.blank");

    LANDER_TIER_1 = addItem(47, "lander.tier.1");
    LANDER_TIER_2 = addItem(48, "lander.tier.2");
    LANDER_TIER_3 = addItem(49, "lander.tier.3");

    FLUID_HOUSING = addItem(50, "fluid.housing");

    WOODEN_BUCKET_WITH_SALT = addItem(51, "wooden.bucket.with.salt").setMaxStackSize(1);

    ASTRO_MINER_COMPUTER = addItem(52, "astro.miner.computer");
    MOON_BUGGY_COMPUTER = addItem(53, "moon.buggy.computer");
    MARS_ROVER_COMPUTER = addItem(54, "mars.rover.computer");
    VENUS_ROVER_COMPUTER = addItem(55, "venus.rover.computer");
    CARGO_ROCKET_COMPUTER = addItem(56, "cargo.rocket.computer");
  }
}
