package net.dries007.tfc.compat.gregtech.oreprefix;

import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialIconType;
import net.dries007.tfc.compat.gregtech.material.TFGMaterials;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasOreProperty;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;

public class TFGOrePrefix {

    public static final OrePrefix oreChunk = new OrePrefix("oreChunk", -1, null, TFGMaterialIconType.oreChunk, ENABLE_UNIFICATION, hasOreProperty);

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
