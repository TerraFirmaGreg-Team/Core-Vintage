package su.terrafirmagreg.modules.integration.gregtech.unification.ore.oreprefix;

import su.terrafirmagreg.modules.integration.gregtech.unification.material.info.MaterialIconTypeCore;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.OrePrefix.Flags;
import gregtech.api.unification.stack.MaterialStack;
import gregtech.common.items.MetaItems;

import static gregtech.api.GTValues.M;
import static gregtech.api.unification.material.Materials.Quartzite;
import static gregtech.api.unification.material.Materials.RockSalt;
import static gregtech.api.unification.material.Materials.Soapstone;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasIngotProperty;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasNoCraftingToolProperty;
import static gregtech.api.unification.ore.OrePrefix.Conditions.hasToolProperty;
import static gregtech.api.unification.ore.OrePrefix.Flags.ENABLE_UNIFICATION;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Arkose;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.BlackbandIronstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Blaimorite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Blueschist;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Boninite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Breccia;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Carbonatite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Cataclasite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Catlinite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Chalk;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Chert;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Claystone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Conglomerate;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Dacite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Dolomite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Foidolite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Gabbro;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Gneiss;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Greenschist;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Jaspillite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Komatiite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Laterite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Limestone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Mudstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Mylonite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Novaculite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Peridotite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Phyllite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Porphyry;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Rhyolite;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Sandstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Schist;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Shale;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Siltstone;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Slate;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Travertine;
import static su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialsCore.Wackestone;

public final class OrePrefixHandler {


  public static final OrePrefix ingotDouble;
  // Igneous Intrusive
  public static final OrePrefix oreGabbro;
  public static final OrePrefix oreBreccia;
  public static final OrePrefix oreFoidolite;
  // Sedimentary
  public static final OrePrefix oreShale;
  public static final OrePrefix oreClaystone;
  public static final OrePrefix oreLimestone;
  public static final OrePrefix oreConglomerate;
  public static final OrePrefix oreDolomite;
  public static final OrePrefix oreChert;
  public static final OrePrefix oreChalk;
  public static final OrePrefix oreMudstone;
  public static final OrePrefix oreSandstone;
  public static final OrePrefix oreSiltstone;
  public static final OrePrefix oreLaterite;
  public static final OrePrefix oreArkose;
  public static final OrePrefix oreJaspillite;
  public static final OrePrefix oreTravertine;
  public static final OrePrefix oreWackestone;
  public static final OrePrefix oreBlackbandIronstone;
  // Igneous Extrusive
  public static final OrePrefix oreRhyolite;
  public static final OrePrefix oreDacite;
  public static final OrePrefix orePeridotite;
  public static final OrePrefix orePorphyry;
  public static final OrePrefix oreBlaimorite;
  public static final OrePrefix oreBoninite;
  public static final OrePrefix oreCarbonatite;
  // Metamorphic
  public static final OrePrefix oreQuartzite;
  public static final OrePrefix oreSlate;
  public static final OrePrefix orePhyllite;
  public static final OrePrefix oreSchist;
  public static final OrePrefix oreGneiss;
  public static final OrePrefix oreBlueschist;
  public static final OrePrefix oreCatlinite;
  public static final OrePrefix oreGreenschist;
  public static final OrePrefix oreNovaculite;
  public static final OrePrefix oreSoapstone;
  public static final OrePrefix oreKomatiite;
  public static final OrePrefix oreCataclasite;
  public static final OrePrefix oreMylonite;
  public static final OrePrefix oreRockSalt;
  // Other
  public static final OrePrefix oreChunk;
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

  static {

    oreRockSalt = new OrePrefixCore("oreRockSalt", new MaterialStack(RockSalt, GTValues.M));
    oreMylonite = new OrePrefixCore("oreMylonite", new MaterialStack(Mylonite, GTValues.M));
    oreCataclasite = new OrePrefixCore("oreCataclasite", new MaterialStack(Cataclasite, GTValues.M));
    oreKomatiite = new OrePrefixCore("oreKomatiite", new MaterialStack(Komatiite, GTValues.M));
    oreSoapstone = new OrePrefixCore("oreSoapstone", new MaterialStack(Soapstone, GTValues.M));
    oreNovaculite = new OrePrefixCore("oreNovaculite", new MaterialStack(Novaculite, GTValues.M));
    oreGreenschist = new OrePrefixCore("oreGreenschist", new MaterialStack(Greenschist, GTValues.M));
    oreCatlinite = new OrePrefixCore("oreCatlinite", new MaterialStack(Catlinite, GTValues.M));
    oreBlueschist = new OrePrefixCore("oreBlueschist", new MaterialStack(Blueschist, GTValues.M));
    oreGneiss = new OrePrefixCore("oreGneiss", new MaterialStack(Gneiss, GTValues.M));
    oreSchist = new OrePrefixCore("oreSchist", new MaterialStack(Schist, GTValues.M));
    orePhyllite = new OrePrefixCore("orePhyllite", new MaterialStack(Phyllite, GTValues.M));
    oreSlate = new OrePrefixCore("oreSlate", new MaterialStack(Slate, GTValues.M));
    oreQuartzite = new OrePrefixCore("oreQuartzite", new MaterialStack(Quartzite, GTValues.M));
    oreCarbonatite = new OrePrefixCore("oreCarbonatite", new MaterialStack(Carbonatite, GTValues.M));
    oreBoninite = new OrePrefixCore("oreBoninite", new MaterialStack(Boninite, GTValues.M));
    oreBlaimorite = new OrePrefixCore("oreBlaimorite", new MaterialStack(Blaimorite, GTValues.M));
    orePorphyry = new OrePrefixCore("orePorphyry", new MaterialStack(Porphyry, GTValues.M));
    orePeridotite = new OrePrefixCore("orePeridotite", new MaterialStack(Peridotite, GTValues.M));
    oreDacite = new OrePrefixCore("oreDacite", new MaterialStack(Dacite, GTValues.M));
    oreRhyolite = new OrePrefixCore("oreRhyolite", new MaterialStack(Rhyolite, GTValues.M));
    oreBlackbandIronstone = new OrePrefixCore("oreBlackbandIronstone", new MaterialStack(BlackbandIronstone, GTValues.M));
    oreWackestone = new OrePrefixCore("oreWackestone", new MaterialStack(Wackestone, GTValues.M));
    oreTravertine = new OrePrefixCore("oreTravertine", new MaterialStack(Travertine, GTValues.M));
    oreJaspillite = new OrePrefixCore("oreJaspillite", new MaterialStack(Jaspillite, GTValues.M));
    oreArkose = new OrePrefixCore("oreArkose", new MaterialStack(Arkose, GTValues.M));
    oreLaterite = new OrePrefixCore("oreLaterite", new MaterialStack(Laterite, GTValues.M));
    oreSiltstone = new OrePrefixCore("oreSiltstone", new MaterialStack(Siltstone, GTValues.M));
    oreSandstone = new OrePrefixCore("oreSandstone", new MaterialStack(Sandstone, GTValues.M));
    oreMudstone = new OrePrefixCore("oreMudstone", new MaterialStack(Mudstone, GTValues.M));
    oreChalk = new OrePrefixCore("oreChalk", new MaterialStack(Chalk, GTValues.M));
    oreChert = new OrePrefixCore("oreChert", new MaterialStack(Chert, GTValues.M));
    oreDolomite = new OrePrefixCore("oreDolomite", new MaterialStack(Dolomite, GTValues.M));
    oreConglomerate = new OrePrefixCore("oreConglomerate", new MaterialStack(Conglomerate, GTValues.M));
    oreLimestone = new OrePrefixCore("oreLimestone", new MaterialStack(Limestone, GTValues.M));
    oreClaystone = new OrePrefixCore("oreClaystone", new MaterialStack(Claystone, GTValues.M));
    oreShale = new OrePrefixCore("oreShale", new MaterialStack(Shale, GTValues.M));
    oreFoidolite = new OrePrefixCore("oreFoidolite", new MaterialStack(Foidolite, GTValues.M));
    oreBreccia = new OrePrefixCore("oreBreccia", new MaterialStack(Breccia, GTValues.M));
    oreGabbro = new OrePrefixCore("oreGabbro", new MaterialStack(Gabbro, GTValues.M));

    oreChunk = new OrePrefixCore("oreChunk", MaterialIconTypeCore.oreChunk, new MaterialStack(Materials.Stone, M));

    ingotDouble = new OrePrefixCore("ingotDouble", M * 2, null, MaterialIconType.ingotDouble, Flags.ENABLE_UNIFICATION, hasIngotProperty);

    toolHeadChisel = new OrePrefixCore("toolHeadChisel", M * 2, MaterialIconTypeCore.toolHeadChisel, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadPropick = new OrePrefixCore("toolHeadPropick", M * 3, MaterialIconTypeCore.toolHeadPropick, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadKnife = new OrePrefixCore("toolHeadKnife", M, MaterialIconTypeCore.toolHeadKnife, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadSaw = new OrePrefixCore("toolHeadSaw", M * 2, MaterialIconType.toolHeadSaw, ENABLE_UNIFICATION, hasNoCraftingToolProperty);
    toolHeadHammer = new OrePrefixCore("toolHeadHammer", M * 6, MaterialIconType.toolHeadHammer, ENABLE_UNIFICATION, hasNoCraftingToolProperty);
    toolHeadFile = new OrePrefixCore("toolHeadFile", M * 2, MaterialIconType.toolHeadFile, ENABLE_UNIFICATION, hasNoCraftingToolProperty);
    toolHeadSense = new OrePrefixCore("toolHeadSense", M * 3, MaterialIconTypeCore.toolHeadSense, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadHoe = new OrePrefixCore("toolHeadHoe", M * 2, MaterialIconType.toolHeadHoe, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadAxe = new OrePrefixCore("toolHeadAxe", M * 3, MaterialIconType.toolHeadAxe, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadShovel = new OrePrefixCore("toolHeadShovel", M, MaterialIconType.toolHeadShovel, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadSword = new OrePrefixCore("toolHeadSword", M * 2, MaterialIconType.toolHeadSword, ENABLE_UNIFICATION, hasToolProperty);
    toolHeadPickaxe = new OrePrefixCore("toolHeadPickaxe", M * 3, MaterialIconType.toolHeadPickaxe, ENABLE_UNIFICATION, hasToolProperty);
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
  }
}
