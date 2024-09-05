package su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix;

import gregtech.api.GTValues;
import gregtech.api.unification.material.info.MaterialIconType;
import gregtech.api.unification.ore.OrePrefix;
import gregtech.api.unification.stack.MaterialStack;

import static gregtech.api.unification.material.Materials.Quartzite;
import static gregtech.api.unification.material.Materials.Soapstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Arkose;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.BlackbandIronstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Blaimorite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Blueschist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Boninite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Breccia;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Carbonatite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Cataclasite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Catlinite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Chalk;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Chert;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Claystone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Conglomerate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Dacite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Dolomite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Foidolite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Gabbro;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Gneiss;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Greenschist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Jaspillite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Komatiite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Laterite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Limestone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Mudstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Mylonite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Novaculite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Peridotite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Phyllite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Porphyry;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Rhyolite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Sandstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Schist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Shale;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Siltstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Slate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Travertine;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.Wackestone;

public class OrePrefixRock extends OrePrefix {

  // @formatter:off

    // Igneous Intrusive
    public static final OrePrefix oreGabbro = new OrePrefixRock("oreGabbro", new MaterialStack(Gabbro, GTValues.M));
    public static final OrePrefix oreBreccia = new OrePrefixRock("oreBreccia", new MaterialStack(Breccia, GTValues.M));
    public static final OrePrefix oreFoidolite = new OrePrefixRock("oreFoidolite", new MaterialStack(Foidolite, GTValues.M));

    // Sedimentary
    public static final OrePrefix oreShale = new OrePrefixRock("oreShale", new MaterialStack(Shale, GTValues.M));
    public static final OrePrefix oreClaystone = new OrePrefixRock("oreClaystone", new MaterialStack(Claystone, GTValues.M));
    public static final OrePrefix oreLimestone = new OrePrefixRock("oreLimestone", new MaterialStack(Limestone, GTValues.M));
    public static final OrePrefix oreConglomerate = new OrePrefixRock("oreConglomerate", new MaterialStack(Conglomerate, GTValues.M));
    public static final OrePrefix oreDolomite = new OrePrefixRock("oreDolomite", new MaterialStack(Dolomite, GTValues.M));
    public static final OrePrefix oreChert = new OrePrefixRock("oreChert", new MaterialStack(Chert, GTValues.M));
    public static final OrePrefix oreChalk = new OrePrefixRock("oreChalk", new MaterialStack(Chalk, GTValues.M));
    public static final OrePrefix oreMudstone = new OrePrefixRock("oreMudstone", new MaterialStack(Mudstone, GTValues.M));
    public static final OrePrefix oreSandstone = new OrePrefixRock("oreSandstone", new MaterialStack(Sandstone, GTValues.M));
    public static final OrePrefix oreSiltstone = new OrePrefixRock("oreSiltstone", new MaterialStack(Siltstone, GTValues.M));
    public static final OrePrefix oreLaterite = new OrePrefixRock("oreLaterite", new MaterialStack(Laterite, GTValues.M));
    public static final OrePrefix oreArkose = new OrePrefixRock("oreArkose", new MaterialStack(Arkose, GTValues.M));
    public static final OrePrefix oreJaspillite = new OrePrefixRock("oreJaspillite", new MaterialStack(Jaspillite, GTValues.M));
    public static final OrePrefix oreTravertine = new OrePrefixRock("oreTravertine", new MaterialStack(Travertine, GTValues.M));
    public static final OrePrefix oreWackestone = new OrePrefixRock("oreWackestone", new MaterialStack(Wackestone, GTValues.M));
    public static final OrePrefix oreBlackbandIronstone = new OrePrefixRock("oreBlackbandIronstone", new MaterialStack(BlackbandIronstone, GTValues.M));

    // Igneous Extrusive
    public static final OrePrefix oreRhyolite = new OrePrefixRock("oreRhyolite", new MaterialStack(Rhyolite, GTValues.M));
    public static final OrePrefix oreDacite = new OrePrefixRock("oreDacite", new MaterialStack(Dacite, GTValues.M));
    public static final OrePrefix orePeridotite = new OrePrefixRock("orePeridotite", new MaterialStack(Peridotite, GTValues.M));
    public static final OrePrefix orePorphyry = new OrePrefixRock("orePorphyry", new MaterialStack(Porphyry, GTValues.M));
    public static final OrePrefix oreBlaimorite = new OrePrefixRock("oreBlaimorite", new MaterialStack(Blaimorite, GTValues.M));
    public static final OrePrefix oreBoninite = new OrePrefixRock("oreBoninite", new MaterialStack(Boninite, GTValues.M));
    public static final OrePrefix oreCarbonatite = new OrePrefixRock("oreCarbonatite", new MaterialStack(Carbonatite, GTValues.M));

    // Metamorphic
    public static final OrePrefix oreQuartzite = new OrePrefixRock("oreQuartzite", new MaterialStack(Quartzite, GTValues.M));
    public static final OrePrefix oreSlate = new OrePrefixRock("oreSlate", new MaterialStack(Slate, GTValues.M));
    public static final OrePrefix orePhyllite = new OrePrefixRock("orePhyllite", new MaterialStack(Phyllite, GTValues.M));
    public static final OrePrefix oreSchist = new OrePrefixRock("oreSchist", new MaterialStack(Schist, GTValues.M));
    public static final OrePrefix oreGneiss = new OrePrefixRock("oreGneiss", new MaterialStack(Gneiss, GTValues.M));
    public static final OrePrefix oreBlueschist = new OrePrefixRock("oreBlueschist", new MaterialStack(Blueschist, GTValues.M));
    public static final OrePrefix oreCatlinite = new OrePrefixRock("oreCatlinite", new MaterialStack(Catlinite, GTValues.M));
    public static final OrePrefix oreGreenschist = new OrePrefixRock("oreGreenschist", new MaterialStack(Greenschist, GTValues.M));
    public static final OrePrefix oreNovaculite = new OrePrefixRock("oreNovaculite", new MaterialStack(Novaculite, GTValues.M));
    public static final OrePrefix oreSoapstone = new OrePrefixRock("oreSoapstone", new MaterialStack(Soapstone, GTValues.M));
    public static final OrePrefix oreKomatiite = new OrePrefixRock("oreKomatiite", new MaterialStack(Komatiite, GTValues.M));
    public static final OrePrefix oreCataclasite = new OrePrefixRock("oreCataclasite", new MaterialStack(Cataclasite, GTValues.M));
    public static final OrePrefix oreMylonite = new OrePrefixRock("oreMylonite", new MaterialStack(Mylonite, GTValues.M));

    public OrePrefixRock(String name, MaterialStack secondaryMaterial) {
        super(name, -1, null, MaterialIconType.ore, Flags.ENABLE_UNIFICATION, Conditions.hasOreProperty);
        this.addSecondaryMaterial(secondaryMaterial);
    }
}
