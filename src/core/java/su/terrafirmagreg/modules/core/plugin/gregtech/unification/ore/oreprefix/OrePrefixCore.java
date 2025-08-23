package su.terrafirmagreg.modules.core.plugin.gregtech.unification.ore.oreprefix;

import su.terrafirmagreg.modules.core.plugin.gregtech.unification.material.info.MaterialIconTypeCore;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.items.MetaItems;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasIngotProperty;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasNoCraftingToolProperty;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasToolProperty;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;

public class OrePrefixCore extends OrePrefix {

  // Other
  public static final OrePrefix oreChunk;
  public static final OrePrefix ingotDouble;

  public static final OrePrefix toolHeadSword;
  public static final OrePrefix toolHeadPickaxe;
  public static final OrePrefix toolHeadShovel;
  public static final OrePrefix toolHeadAxe;
  public static final OrePrefix toolHeadHoe;
  public static final OrePrefix toolHeadSense;
  public static final OrePrefix toolHeadFile;
  public static final OrePrefix toolHeadHammer;
  public static final OrePrefix toolHeadSaw;
  public static final OrePrefix toolHeadKnife;
  public static final OrePrefix toolHeadPropick;
  public static final OrePrefix toolHeadChisel;
  public static final OrePrefix toolHeadMace;
  public static final OrePrefix toolHeadJavelin;

  static {
    oreChunk = new OrePrefixCore("oreChunk", M / 4, MaterialIconTypeCore.oreChunk, new MaterialStack(Materials.Stone, M));
    ingotDouble = new OrePrefixCore("ingotDouble", M * 2, null, MaterialIconType.ingotDouble, Flags.ENABLE_UNIFICATION, hasIngotProperty);

    toolHeadChisel = new OrePrefixCore("toolHeadChisel", M * 2, MaterialIconTypeCore.toolHeadChisel, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadPropick = new OrePrefixCore("toolHeadPropick", M * 3, MaterialIconTypeCore.toolHeadPropick, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadKnife = new OrePrefixCore("toolHeadKnife", M, MaterialIconTypeCore.toolHeadKnife, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadSaw = new OrePrefixCore("toolHeadSaw", M * 2, MaterialIconTypeCore.toolHeadSaw, ENABLE_UNIFICATION, hasNoCraftingToolProperty);
    toolHeadHammer = new OrePrefixCore("toolHeadHammer", M * 6, MaterialIconTypeCore.toolHeadHammer, ENABLE_UNIFICATION, hasNoCraftingToolProperty);
    toolHeadFile = new OrePrefixCore("toolHeadFile", M * 2, MaterialIconTypeCore.toolHeadFile, ENABLE_UNIFICATION, hasNoCraftingToolProperty);
    toolHeadSense = new OrePrefixCore("toolHeadSense", M, MaterialIconTypeCore.toolHeadSense, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadHoe = new OrePrefixCore("toolHeadHoe", M, MaterialIconTypeCore.toolHeadHoe, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadAxe = new OrePrefixCore("toolHeadAxe", M, MaterialIconTypeCore.toolHeadAxe, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadShovel = new OrePrefixCore("toolHeadShovel", M, MaterialIconTypeCore.toolHeadShovel, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadSword = new OrePrefixCore("toolHeadSword", M * 2, MaterialIconTypeCore.toolHeadSword, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadPickaxe = new OrePrefixCore("toolHeadPickaxe", M, MaterialIconTypeCore.toolHeadPickaxe, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadMace = new OrePrefixCore("toolHeadMace", M * 2, MaterialIconTypeCore.toolHeadMace, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadJavelin = new OrePrefixCore("toolHeadJavelin", M, MaterialIconTypeCore.toolHeadJavelin, ENABLE_UNIFICATION, hasToolProperty);
  }

  static {
    MetaItems.addOrePrefix(oreChunk);
    MetaItems.addOrePrefix(ingotDouble);

    MetaItems.addOrePrefix(toolHeadSword);
    MetaItems.addOrePrefix(toolHeadPickaxe);
    MetaItems.addOrePrefix(toolHeadShovel);
    MetaItems.addOrePrefix(toolHeadAxe);
    MetaItems.addOrePrefix(toolHeadHoe);
    MetaItems.addOrePrefix(toolHeadSense);
    MetaItems.addOrePrefix(toolHeadFile);
    MetaItems.addOrePrefix(toolHeadHammer);
    MetaItems.addOrePrefix(toolHeadSaw);
    MetaItems.addOrePrefix(toolHeadKnife);
    MetaItems.addOrePrefix(toolHeadPropick);
    MetaItems.addOrePrefix(toolHeadChisel);
    MetaItems.addOrePrefix(toolHeadMace);
    MetaItems.addOrePrefix(toolHeadJavelin);
  }

  public OrePrefixCore(String name, long materialAmount, @Nullable Material material, @Nullable MaterialIconType materialIconType, long flags, @Nullable Predicate<Material> condition) {
    super(name, materialAmount, material, materialIconType, flags, condition);
  }

  public OrePrefixCore(String name, long materialAmount, @Nullable Material material, @Nullable MaterialIconType materialIconType, long flags, @Nullable Predicate<Material> condition, @Nullable Function<Material, List<String>> tooltipFunc) {
    super(name, materialAmount, material, materialIconType, flags, condition, tooltipFunc);
  }

  public OrePrefixCore(String name, MaterialStack secondaryMaterial) {
    super(name, -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    this.addSecondaryMaterial(secondaryMaterial);
  }

  public OrePrefixCore(String name, @Nullable MaterialIconType materialIconType, MaterialStack secondaryMaterial) {
    super(name, -1, null, materialIconType, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    this.addSecondaryMaterial(secondaryMaterial);
  }

  public OrePrefixCore(String name, long materialAmount, @Nullable MaterialIconType materialIconType, MaterialStack secondaryMaterial) {
    super(name, materialAmount, null, materialIconType, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    this.addSecondaryMaterial(secondaryMaterial);
  }

  public OrePrefixCore(String name, long materialAmount, @Nullable MaterialIconType materialIconType, long flags, @Nullable Predicate<Material> condition) {
    super(name, materialAmount, null, materialIconType, flags, condition);

  }


}
