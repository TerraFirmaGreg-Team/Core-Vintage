package su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.oreprefix;

import su.terrafirmagreg.framework.manager.plugin.spi.gregtech.unification.ore.BuilderOrePrefix;

import gregtech.api.GTValues;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.ore.OrePrefix.Conditions;
import gregtech.api.unification.ore.OrePrefix.Flags;
import gregtech.api.unification.stack.MaterialStack;

import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Andesite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Arkose;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Basalt;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.BlackbandIronstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Blaimorite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Blueschist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Boninite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Breccia;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Carbonatite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Cataclasite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Catlinite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Chalk;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Chert;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Claystone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Conglomerate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Dacite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Diorite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Dolomite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Foidolite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Gabbro;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Gneiss;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Granite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.GraniteRed;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Greenschist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Jaspillite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Komatiite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Laterite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Limestone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Marble;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Mudstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Mylonite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Novaculite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Peridotite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Phyllite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Porphyry;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Quartzite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Rhyolite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.RockSalt;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Sandstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Schist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Shale;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Siltstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Slate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Soapstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Travertine;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialsRock.Wackestone;

public class OrePrefixRock {

  // Igneous Intrusive
  public static final OrePrefix oreGranite = new BuilderOrePrefix("oreGranite")
    .addSecondaryMaterial(new MaterialStack(Granite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreDiorite = new BuilderOrePrefix("oreDiorite")
    .addSecondaryMaterial(new MaterialStack(Diorite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreGabbro = new BuilderOrePrefix("oreGabbro")
    .addSecondaryMaterial(new MaterialStack(Gabbro, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreBreccia = new BuilderOrePrefix("oreBreccia")
    .addSecondaryMaterial(new MaterialStack(Breccia, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreFoidolite = new BuilderOrePrefix("oreFoidolite")
    .addSecondaryMaterial(new MaterialStack(Foidolite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreRedgranite = new BuilderOrePrefix("oreRedgranite")
    .addSecondaryMaterial(new MaterialStack(GraniteRed, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  // Sedimentary
  public static final OrePrefix oreShale = new BuilderOrePrefix("oreShale")
    .addSecondaryMaterial(new MaterialStack(Shale, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreClaystone = new BuilderOrePrefix("oreClaystone")
    .addSecondaryMaterial(new MaterialStack(Claystone, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreLimestone = new BuilderOrePrefix("oreLimestone")
    .addSecondaryMaterial(new MaterialStack(Limestone, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreConglomerate = new BuilderOrePrefix("oreConglomerate")
    .addSecondaryMaterial(new MaterialStack(Conglomerate, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreDolomite = new BuilderOrePrefix("oreDolomite")
    .addSecondaryMaterial(new MaterialStack(Dolomite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreChert = new BuilderOrePrefix("oreChert")
    .addSecondaryMaterial(new MaterialStack(Chert, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreChalk = new BuilderOrePrefix("oreChalk")
    .addSecondaryMaterial(new MaterialStack(Chalk, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreMudstone = new BuilderOrePrefix("oreMudstone")
    .addSecondaryMaterial(new MaterialStack(Mudstone, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreSandstone = new BuilderOrePrefix("oreSandstone")
    .addSecondaryMaterial(new MaterialStack(Sandstone, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreSiltstone = new BuilderOrePrefix("oreSiltstone")
    .addSecondaryMaterial(new MaterialStack(Siltstone, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreLaterite = new BuilderOrePrefix("oreLaterite")
    .addSecondaryMaterial(new MaterialStack(Laterite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreArkose = new BuilderOrePrefix("oreArkose")
    .addSecondaryMaterial(new MaterialStack(Arkose, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreJaspillite = new BuilderOrePrefix("oreJaspillite")
    .addSecondaryMaterial(new MaterialStack(Jaspillite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreTravertine = new BuilderOrePrefix("oreTravertine")
    .addSecondaryMaterial(new MaterialStack(Travertine, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreWackestone = new BuilderOrePrefix("oreWackestone")
    .addSecondaryMaterial(new MaterialStack(Wackestone, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreBlackbandIronstone = new BuilderOrePrefix("oreBlackbandIronstone")
    .addSecondaryMaterial(new MaterialStack(BlackbandIronstone, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();


  // Igneous Extrusive
  public static final OrePrefix oreRhyolite = new BuilderOrePrefix("oreRhyolite")
    .addSecondaryMaterial(new MaterialStack(Rhyolite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreBasalt = new BuilderOrePrefix("oreBasalt")
    .addSecondaryMaterial(new MaterialStack(Basalt, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreAndesite = new BuilderOrePrefix("oreAndesite")
    .addSecondaryMaterial(new MaterialStack(Andesite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreDacite = new BuilderOrePrefix("oreDacite")
    .addSecondaryMaterial(new MaterialStack(Dacite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix orePeridotite = new BuilderOrePrefix("orePeridotite")
    .addSecondaryMaterial(new MaterialStack(Peridotite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix orePorphyry = new BuilderOrePrefix("orePorphyry")
    .addSecondaryMaterial(new MaterialStack(Porphyry, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreBlaimorite = new BuilderOrePrefix("oreBlaimorite")
    .addSecondaryMaterial(new MaterialStack(Blaimorite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreBoninite = new BuilderOrePrefix("oreBoninite")
    .addSecondaryMaterial(new MaterialStack(Boninite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreCarbonatite = new BuilderOrePrefix("oreCarbonatite")
    .addSecondaryMaterial(new MaterialStack(Carbonatite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  // Metamorphic
  public static final OrePrefix oreQuartzite = new BuilderOrePrefix("oreQuartzite")
    .addSecondaryMaterial(new MaterialStack(Quartzite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreSlate = new BuilderOrePrefix("oreSlate")
    .addSecondaryMaterial(new MaterialStack(Slate, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix orePhyllite = new BuilderOrePrefix("orePhyllite")
    .addSecondaryMaterial(new MaterialStack(Phyllite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreSchist = new BuilderOrePrefix("oreSchist")
    .addSecondaryMaterial(new MaterialStack(Schist, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreGneiss = new BuilderOrePrefix("oreGneiss")
    .addSecondaryMaterial(new MaterialStack(Gneiss, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreMarble = new BuilderOrePrefix("oreMarble")
    .addSecondaryMaterial(new MaterialStack(Marble, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreBlueschist = new BuilderOrePrefix("oreBlueschist")
    .addSecondaryMaterial(new MaterialStack(Blueschist, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreCatlinite = new BuilderOrePrefix("oreCatlinite")
    .addSecondaryMaterial(new MaterialStack(Catlinite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreGreenschist = new BuilderOrePrefix("oreGreenschist")
    .addSecondaryMaterial(new MaterialStack(Greenschist, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreNovaculite = new BuilderOrePrefix("oreNovaculite")
    .addSecondaryMaterial(new MaterialStack(Novaculite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreSoapstone = new BuilderOrePrefix("oreSoapstone")
    .addSecondaryMaterial(new MaterialStack(Soapstone, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreKomatiite = new BuilderOrePrefix("oreKomatiite")
    .addSecondaryMaterial(new MaterialStack(Komatiite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreCataclasite = new BuilderOrePrefix("oreCataclasite")
    .addSecondaryMaterial(new MaterialStack(Cataclasite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();

  public static final OrePrefix oreMylonite = new BuilderOrePrefix("oreMylonite")
    .addSecondaryMaterial(new MaterialStack(Mylonite, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();


  public static final OrePrefix oreRockSalt = new BuilderOrePrefix("oreRockSalt")
    .addSecondaryMaterial(new MaterialStack(RockSalt, GTValues.M))
    .setMaterialIconType(MaterialIconType.ore)
    .setFlags(Flags.ENABLE_UNIFICATION)
    .setCondition(Conditions.hasOreProperty)
    .build();
}
