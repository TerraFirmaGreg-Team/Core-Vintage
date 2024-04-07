package su.terrafirmagreg.modules.rock.api.types.type;

import su.terrafirmagreg.modules.rock.api.types.category.RockCategories;
import su.terrafirmagreg.modules.rock.plugin.gregtech.material.TFGMaterials;

import gregtech.api.unification.material.Materials;

import static gregtech.api.unification.ore.OrePrefix.*;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.TFGOrePrefix.*;

public class RockTypeHandler {

    public static void init() {

        RockTypes.GRANITE = new RockType.Builder("granite")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreGranite)
                .setMaterial(Materials.Granite)
                .build();

        RockTypes.DIORITE = new RockType.Builder("diorite")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreDiorite)
                .setMaterial(Materials.Diorite).build();

        RockTypes.GABBRO = new RockType.Builder("gabbro")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreGabbro)
                .setMaterial(TFGMaterials.Gabbro)
                .build();

        RockTypes.BRECCIA = new RockType.Builder("breccia")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreBreccia)
                .setMaterial(TFGMaterials.Breccia)
                .build();

        RockTypes.FOIDOLITE = new RockType.Builder("foidolite")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreFoidolite)
                .setMaterial(TFGMaterials.Foidolite)
                .build();

        RockTypes.RED_GRANITE = new RockType.Builder("red_granite")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreRedgranite)
                .setMaterial(Materials.GraniteRed)
                .build();

        RockTypes.SHALE = new RockType.Builder("shale")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreShale)
                .setMaterial(TFGMaterials.Shale)
                .build();

        RockTypes.CLAYSTONE = new RockType.Builder("claystone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreClaystone)
                .setMaterial(TFGMaterials.Claystone)
                .build();

        RockTypes.LIMESTONE = new RockType.Builder("limestone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreLimestone)
                .setMaterial(TFGMaterials.Limestone)
                .build();

        RockTypes.CONGLOMERATE = new RockType.Builder("conglomerate")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreConglomerate)
                .setMaterial(TFGMaterials.Conglomerate)
                .build();

        RockTypes.DOLOMITE = new RockType.Builder("dolomite")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreDolomite)
                .setMaterial(TFGMaterials.Dolomite)
                .setFlux()
                .build();

        RockTypes.CHERT = new RockType.Builder("chert")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreChert)
                .setMaterial(TFGMaterials.Chert)
                .build();

        RockTypes.CHALK = new RockType.Builder("chalk")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreChalk)
                .setMaterial(TFGMaterials.Chalk)
                .setFlux()
                .build();

        RockTypes.MUDSTONE = new RockType.Builder("mudstone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreMudstone)
                .setMaterial(TFGMaterials.Mudstone)
                .setFlux()
                .build();

        RockTypes.SANDSTONE = new RockType.Builder("sandstone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreSandstone)
                .setMaterial(TFGMaterials.Sandstone)
                .setFlux()
                .build();

        RockTypes.SILTSTONE = new RockType.Builder("siltstone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreSiltstone)
                .setMaterial(TFGMaterials.Siltstone)
                .setFlux()
                .build();

        RockTypes.LATERITE = new RockType.Builder("laterite")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreLaterite)
                .setMaterial(TFGMaterials.Laterite)
                .setFlux()
                .build();

        RockTypes.ARKOSE = new RockType.Builder("arkose")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreArkose)
                .setMaterial(TFGMaterials.Arkose)
                .setFlux()
                .build();

        RockTypes.JASPILLITE = new RockType.Builder("jaspillite")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreJaspillite)
                .setMaterial(TFGMaterials.Jaspillite)
                .setFlux()
                .build();

        RockTypes.TRAVERTINE = new RockType.Builder("travertine")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreTravertine)
                .setMaterial(TFGMaterials.Travertine)
                .setFlux()
                .build();

        RockTypes.WACKESTONE = new RockType.Builder("wackestone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreWackestone)
                .setMaterial(TFGMaterials.Wackestone)
                .setFlux()
                .build();

        RockTypes.BLACKBAND_IRONSTONE = new RockType.Builder("blackband_ironstone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(oreBlackbandIronstone)
                .setMaterial(TFGMaterials.BlackbandIronstone)
                .setFlux()
                .build();

        RockTypes.RHYOLITE = new RockType.Builder("rhyolite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreRhyolite)
                .setMaterial(TFGMaterials.Rhyolite)
                .build();

        RockTypes.BASALT = new RockType.Builder("basalt")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreBasalt)
                .setMaterial(Materials.Basalt)
                .build();

        RockTypes.ANDESITE = new RockType.Builder("andesite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreAndesite)
                .setMaterial(Materials.Andesite)
                .build();

        RockTypes.DACITE = new RockType.Builder("dacite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreDacite)
                .setMaterial(TFGMaterials.Dacite)
                .build();

        RockTypes.PERIDOTITE = new RockType.Builder("peridotite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(orePeridotite)
                .setMaterial(TFGMaterials.Peridotite)
                .build();

        RockTypes.PORPHYRY = new RockType.Builder("porphyry")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(orePorphyry)
                .setMaterial(TFGMaterials.Porphyry)
                .build();

        RockTypes.BLAIMORITE = new RockType.Builder("blaimorite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreBlaimorite)
                .setMaterial(TFGMaterials.Blaimorite)
                .build();

        RockTypes.BONINITE = new RockType.Builder("boninite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreBoninite)
                .setMaterial(TFGMaterials.Boninite)
                .build();

        RockTypes.CARBONATITE = new RockType.Builder("carbonatite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreCarbonatite)
                .setMaterial(TFGMaterials.Carbonatite)
                .build();

        RockTypes.QUARTZITE = new RockType.Builder("quartzite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreQuartzite)
                .setMaterial(Materials.Quartzite)
                .setFlux()
                .build();

        RockTypes.SLATE = new RockType.Builder("slate")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreSlate)
                .setMaterial(TFGMaterials.Slate)
                .build();

        RockTypes.PHYLLITE = new RockType.Builder("phyllite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(orePhyllite)
                .setMaterial(TFGMaterials.Phyllite)
                .build();

        RockTypes.SCHIST = new RockType.Builder("schist")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreSchist)
                .setMaterial(TFGMaterials.Schist)
                .build();

        RockTypes.GNEISS = new RockType.Builder("gneiss")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreGneiss)
                .setMaterial(TFGMaterials.Gneiss)
                .build();

        RockTypes.MARBLE = new RockType.Builder("marble")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreMarble)
                .setMaterial(Materials.Marble)
                .setFlux()
                .build();

        RockTypes.BLUESCHIST = new RockType.Builder("blueschist")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreBlueschist)
                .setMaterial(TFGMaterials.Blueschist)
                .setFlux()
                .build();

        RockTypes.CATLINITE = new RockType.Builder("catlinite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreCatlinite)
                .setMaterial(TFGMaterials.Catlinite)
                .setFlux()
                .build();

        RockTypes.GREENSCHIST = new RockType.Builder("greenschist")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreGreenschist)
                .setMaterial(TFGMaterials.Greenschist)
                .setFlux()
                .build();

        RockTypes.NOVACULITE = new RockType.Builder("novaculite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreNovaculite)
                .setMaterial(TFGMaterials.Novaculite)
                .setFlux()
                .build();

        RockTypes.SOAPSTONE = new RockType.Builder("soapstone")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreSoapstone)
                .setMaterial(Materials.Soapstone)
                .setFlux()
                .build();

        RockTypes.KOMATIITE = new RockType.Builder("komatiite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreKomatiite)
                .setMaterial(TFGMaterials.Komatiite)
                .setFlux()
                .build();

        RockTypes.CATACLASITE = new RockType.Builder("cataclasite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreCataclasite)
                .setMaterial(TFGMaterials.Komatiite)
                .setFlux()
                .build();

        RockTypes.MYLONITE = new RockType.Builder("mylonite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(oreMylonite)
                .setMaterial(TFGMaterials.Komatiite)
                .setFlux()
                .build();
    }
}
