package net.dries007.tfc.compat.gregtech.oreprefix;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialFlags;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialIconType;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Conditions.*;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;

public class TFGOrePrefix {

    public static final OrePrefix toolHeadSword = new OrePrefix("toolHeadSword", GTValues.M * 2, null, MaterialIconType.toolHeadSword, Flags.ENABLE_UNIFICATION, Conditions.hasToolProperty);
    public static final OrePrefix toolHeadPickaxe = new OrePrefix("toolHeadPickaxe", GTValues.M * 3, null, MaterialIconType.toolHeadPickaxe, Flags.ENABLE_UNIFICATION, Conditions.hasToolProperty);
    public static final OrePrefix toolHeadShovel = new OrePrefix("toolHeadShovel", GTValues.M, null, MaterialIconType.toolHeadShovel, Flags.ENABLE_UNIFICATION, Conditions.hasToolProperty);
    public static final OrePrefix toolHeadAxe = new OrePrefix("toolHeadAxe", GTValues.M * 3, null, MaterialIconType.toolHeadAxe, Flags.ENABLE_UNIFICATION, Conditions.hasToolProperty);
    public static final OrePrefix toolHeadHoe = new OrePrefix("toolHeadHoe", GTValues.M * 2, null, MaterialIconType.toolHeadHoe, Flags.ENABLE_UNIFICATION, Conditions.hasToolProperty);
    public static final OrePrefix toolHeadSense = new OrePrefix("toolHeadSense", GTValues.M * 3, null, TFGMaterialIconType.toolHeadSense, Flags.ENABLE_UNIFICATION, Conditions.hasToolProperty);
    public static final OrePrefix toolHeadFile = new OrePrefix("toolHeadFile", GTValues.M * 2, null, MaterialIconType.toolHeadFile, Flags.ENABLE_UNIFICATION, Conditions.hasNoCraftingToolProperty);
    public static final OrePrefix toolHeadHammer = new OrePrefix("toolHeadHammer", GTValues.M * 6, null, MaterialIconType.toolHeadHammer, Flags.ENABLE_UNIFICATION, Conditions.hasNoCraftingToolProperty);
    public static final OrePrefix toolHeadSaw = new OrePrefix("toolHeadSaw", GTValues.M * 2, null, MaterialIconType.toolHeadSaw, Flags.ENABLE_UNIFICATION, Conditions.hasNoCraftingToolProperty);
    public static final OrePrefix toolHeadKnife = new OrePrefix("toolHeadKnife", GTValues.M, null, TFGMaterialIconType.toolHeadKnife, Flags.ENABLE_UNIFICATION, Conditions.hasToolProperty);
    public static final OrePrefix toolHeadPropick = new OrePrefix("toolHeadPropick", GTValues.M * 3, null, TFGMaterialIconType.toolHeadPropick, Flags.ENABLE_UNIFICATION, Conditions.hasToolProperty);
    public static final OrePrefix toolHeadChisel = new OrePrefix("toolHeadChisel", GTValues.M * 2, null, TFGMaterialIconType.toolHeadChisel, Flags.ENABLE_UNIFICATION, Conditions.hasToolProperty);

    public static final OrePrefix oreChunk = new OrePrefix("oreChunk", -1, null, TFGMaterialIconType.oreChunk, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix ingotDouble = new OrePrefix("ingotDouble", GTValues.M * 2, null, MaterialIconType.ingotDouble, Flags.ENABLE_UNIFICATION, m -> !m.hasFlag(TFGMaterialFlags.UNUSABLE) && m.hasProperty(TFGPropertyKey.HEAT));
    public static final OrePrefix ingotTriple = new OrePrefix("ingotTriple", GTValues.M * 3, null, MaterialIconType.ingotTriple, Flags.ENABLE_UNIFICATION, m -> !m.hasFlag(TFGMaterialFlags.UNUSABLE) && m.hasProperty(TFGPropertyKey.HEAT));
    public static final OrePrefix ingotHex = new OrePrefix("ingotHex", GTValues.M * 6, null, TFGMaterialIconType.ingotHex, Flags.ENABLE_UNIFICATION, m -> !m.hasFlag(TFGMaterialFlags.UNUSABLE) && m.hasProperty(TFGPropertyKey.HEAT));

    public static final OrePrefix anvil = new OrePrefix("anvil", GTValues.M * 6, null, TFGMaterialIconType.anvil, Flags.ENABLE_UNIFICATION, m -> !m.hasFlag(TFGMaterialFlags.UNUSABLE) && m.hasProperty(TFGPropertyKey.HEAT));

    public static final OrePrefix oreQuartzite = new OrePrefix("oreQuartzite", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreChalk = new OrePrefix("oreChalk", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreChert = new OrePrefix("oreChert", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreClaystone = new OrePrefix("oreClaystone", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreConglomerate = new OrePrefix("oreConglomerate", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreDacite = new OrePrefix("oreDacite", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreDolomite = new OrePrefix("oreDolomite", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreGabbro = new OrePrefix("oreGabbro", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreGneiss = new OrePrefix("oreGneiss", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreLimestone = new OrePrefix("oreLimestone", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix orePhyllite = new OrePrefix("orePhyllite", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreRhyolite = new OrePrefix("oreRhyolite", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreSchist = new OrePrefix("oreSchist", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreShale = new OrePrefix("oreShale", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
    public static final OrePrefix oreSlate = new OrePrefix("oreSlate", -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);

    static {
        oreChunk.addSecondaryMaterial(new MaterialStack(Materials.Stone, GTValues.M));

        oreQuartzite.addSecondaryMaterial(new MaterialStack(Materials.Quartzite, GTValues.M));
        oreChalk.addSecondaryMaterial(new MaterialStack(TFGMaterials.Chalk, GTValues.M));
        oreChert.addSecondaryMaterial(new MaterialStack(TFGMaterials.Chert, GTValues.M));
        oreClaystone.addSecondaryMaterial(new MaterialStack(TFGMaterials.Claystone, GTValues.M));
        oreConglomerate.addSecondaryMaterial(new MaterialStack(TFGMaterials.Conglomerate, GTValues.M));
        oreDacite.addSecondaryMaterial(new MaterialStack(TFGMaterials.Dacite, GTValues.M));
        oreDolomite.addSecondaryMaterial(new MaterialStack(TFGMaterials.Dolomite, GTValues.M));
        oreGabbro.addSecondaryMaterial(new MaterialStack(TFGMaterials.Gabbro, GTValues.M));
        oreGneiss.addSecondaryMaterial(new MaterialStack(TFGMaterials.Gneiss, GTValues.M));
        oreLimestone.addSecondaryMaterial(new MaterialStack(TFGMaterials.Limestone, GTValues.M));
        orePhyllite.addSecondaryMaterial(new MaterialStack(TFGMaterials.Phyllite, GTValues.M));
        oreRhyolite.addSecondaryMaterial(new MaterialStack(TFGMaterials.Rhyolite, GTValues.M));
        oreSchist.addSecondaryMaterial(new MaterialStack(TFGMaterials.Schist, GTValues.M));
        oreShale.addSecondaryMaterial(new MaterialStack(TFGMaterials.Shale, GTValues.M));
        oreSlate.addSecondaryMaterial(new MaterialStack(TFGMaterials.Slate, GTValues.M));
    }
}
