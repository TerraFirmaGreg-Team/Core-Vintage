package net.dries007.tfc.compat.gregtech.oreprefix;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialIconType;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;
import net.dries007.tfc.compat.gregtech.material.TFGPropertyKey;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Conditions.*;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasToolProperty;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;

public class TFGOrePrefix {

    public static final OrePrefix toolHeadSword = new OrePrefix("toolHeadSword", M * 2, null, MaterialIconType.toolHeadSword, ENABLE_UNIFICATION, hasToolProperty);
    public static final OrePrefix toolHeadPickaxe = new OrePrefix("toolHeadPickaxe", M * 3, null, MaterialIconType.toolHeadPickaxe, ENABLE_UNIFICATION, hasToolProperty);
    public static final OrePrefix toolHeadShovel = new OrePrefix("toolHeadShovel", M, null, MaterialIconType.toolHeadShovel, ENABLE_UNIFICATION, hasToolProperty);
    public static final OrePrefix toolHeadAxe = new OrePrefix("toolHeadAxe", M * 3, null, MaterialIconType.toolHeadAxe, ENABLE_UNIFICATION, hasToolProperty);
    public static final OrePrefix toolHeadHoe = new OrePrefix("toolHeadHoe", M * 2, null, MaterialIconType.toolHeadHoe, ENABLE_UNIFICATION, hasToolProperty);
    public static final OrePrefix toolHeadSense = new OrePrefix("toolHeadSense", M * 3, null, TFGMaterialIconType.toolHeadSense, ENABLE_UNIFICATION, hasToolProperty);
    public static final OrePrefix toolHeadFile = new OrePrefix("toolHeadFile", M * 2, null, MaterialIconType.toolHeadFile, ENABLE_UNIFICATION, hasNoCraftingToolProperty);
    public static final OrePrefix toolHeadHammer = new OrePrefix("toolHeadHammer", M * 6, null, MaterialIconType.toolHeadHammer, ENABLE_UNIFICATION, hasNoCraftingToolProperty);
    public static final OrePrefix toolHeadSaw = new OrePrefix("toolHeadSaw", M * 2, null, MaterialIconType.toolHeadSaw, ENABLE_UNIFICATION, hasNoCraftingToolProperty);
    public static final OrePrefix toolHeadKnife = new OrePrefix("toolHeadKnife", M, null, TFGMaterialIconType.toolHeadKnife, ENABLE_UNIFICATION, hasToolProperty);
    public static final OrePrefix toolHeadPropick = new OrePrefix("toolHeadPropick", M * 3, null, TFGMaterialIconType.toolHeadPropick, ENABLE_UNIFICATION, hasToolProperty);
    public static final OrePrefix toolHeadChisel = new OrePrefix("toolHeadChisel", M * 2, null, TFGMaterialIconType.toolHeadChisel, ENABLE_UNIFICATION, hasToolProperty);

    public static final OrePrefix oreChunk = new OrePrefix("oreChunk", -1, null, TFGMaterialIconType.oreChunk, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix ingotDouble = new OrePrefix("ingotDouble", M * 2, null, MaterialIconType.ingotDouble, ENABLE_UNIFICATION,  m -> m.hasProperty(TFGPropertyKey.HEAT));
    public static final OrePrefix ingotTriple = new OrePrefix("ingotTriple", M * 3, null, MaterialIconType.ingotTriple, ENABLE_UNIFICATION,  m -> m.hasProperty(TFGPropertyKey.HEAT));
    public static final OrePrefix ingotHex = new OrePrefix("ingotHex", M * 6, null, TFGMaterialIconType.ingotHex, ENABLE_UNIFICATION,  m -> m.hasProperty(TFGPropertyKey.HEAT));

    public static final OrePrefix oreRockSalt = new OrePrefix("oreRockSalt", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreSoapstone = new OrePrefix("oreSoapstone", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreQuartzite = new OrePrefix("oreQuartzite", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreCatlinite = new OrePrefix("oreCatlinite", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreChalk = new OrePrefix("oreChalk", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreChert = new OrePrefix("oreChert", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreClaystone = new OrePrefix("oreClaystone", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreConglomerate = new OrePrefix("oreConglomerate", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreDacite = new OrePrefix("oreDacite", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreDolomite = new OrePrefix("oreDolomite", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreGabbro = new OrePrefix("oreGabbro", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreGneiss = new OrePrefix("oreGneiss", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreLimestone = new OrePrefix("oreLimestone", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix orePhyllite = new OrePrefix("orePhyllite", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreRhyolite = new OrePrefix("oreRhyolite", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreSchist = new OrePrefix("oreSchist", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreShale = new OrePrefix("oreShale", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);
    public static final OrePrefix oreSlate = new OrePrefix("oreSlate", -1, null, MaterialIconType.ore, ENABLE_UNIFICATION, hasOreProperty);

    static
    {
        oreChunk.addSecondaryMaterial(new MaterialStack(Materials.Stone, M));

        oreRockSalt.addSecondaryMaterial(new MaterialStack(Materials.RockSalt, M));
        oreSoapstone.addSecondaryMaterial(new MaterialStack(Materials.RockSalt, M));
        oreQuartzite.addSecondaryMaterial(new MaterialStack(Materials.Quartzite, M));
        oreCatlinite.addSecondaryMaterial(new MaterialStack(TFGMaterials.Catlinite, M));
        oreChalk.addSecondaryMaterial(new MaterialStack(TFGMaterials.Chalk, M));
        oreChert.addSecondaryMaterial(new MaterialStack(TFGMaterials.Chert, M));
        oreClaystone.addSecondaryMaterial(new MaterialStack(TFGMaterials.Claystone, M));
        oreConglomerate.addSecondaryMaterial(new MaterialStack(TFGMaterials.Conglomerate, M));
        oreDacite.addSecondaryMaterial(new MaterialStack(TFGMaterials.Dacite, M));
        oreDolomite.addSecondaryMaterial(new MaterialStack(TFGMaterials.Dolomite, M));
        oreGabbro.addSecondaryMaterial(new MaterialStack(TFGMaterials.Gabbro, M));
        oreGneiss.addSecondaryMaterial(new MaterialStack(TFGMaterials.Gneiss, M));
        oreLimestone.addSecondaryMaterial(new MaterialStack(TFGMaterials.Limestone, M));
        orePhyllite.addSecondaryMaterial(new MaterialStack(TFGMaterials.Phyllite, M));
        oreRhyolite.addSecondaryMaterial(new MaterialStack(TFGMaterials.Rhyolite, M));
        oreSchist.addSecondaryMaterial(new MaterialStack(TFGMaterials.Schist, M));
        oreShale.addSecondaryMaterial(new MaterialStack(TFGMaterials.Shale, M));
        oreSlate.addSecondaryMaterial(new MaterialStack(TFGMaterials.Slate, M));
    }
}
