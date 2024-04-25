package su.terrafirmagreg.modules.rock.api.types.type;

import su.terrafirmagreg.modules.rock.api.types.category.RockCategories;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategoryHandler;
import su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock;
import su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRock;

public class RockTypeHandler {

    public static void init() {
        RockCategoryHandler.init();

        RockTypes.GRANITE = new RockType.Builder("granite")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreGranite)
                .setMaterial(MaterialsRock.Granite)
                .build();

        RockTypes.DIORITE = new RockType.Builder("diorite")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreDiorite)
                .setMaterial(MaterialsRock.Diorite)
                .build();

        RockTypes.GABBRO = new RockType.Builder("gabbro")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreGabbro)
                .setMaterial(MaterialsRock.Gabbro)
                .build();

        RockTypes.BRECCIA = new RockType.Builder("breccia")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreBreccia)
                .setMaterial(MaterialsRock.Breccia)
                .build();

        RockTypes.FOIDOLITE = new RockType.Builder("foidolite")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreFoidolite)
                .setMaterial(MaterialsRock.Foidolite)
                .build();

        RockTypes.RED_GRANITE = new RockType.Builder("red_granite")
                .setRockCategory(RockCategories.IGNEOUS_INTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreRedgranite)
                .setMaterial(MaterialsRock.GraniteRed)
                .build();

        RockTypes.SHALE = new RockType.Builder("shale")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreShale)
                .setMaterial(MaterialsRock.Shale)
                .build();

        RockTypes.CLAYSTONE = new RockType.Builder("claystone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreClaystone)
                .setMaterial(MaterialsRock.Claystone)
                .build();

        RockTypes.LIMESTONE = new RockType.Builder("limestone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreLimestone)
                .setMaterial(MaterialsRock.Limestone)
                .build();

        RockTypes.CONGLOMERATE = new RockType.Builder("conglomerate")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreConglomerate)
                .setMaterial(MaterialsRock.Conglomerate)
                .build();

        RockTypes.DOLOMITE = new RockType.Builder("dolomite")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreDolomite)
                .setMaterial(MaterialsRock.Dolomite)
                .setFlux()
                .build();

        RockTypes.CHERT = new RockType.Builder("chert")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreChert)
                .setMaterial(MaterialsRock.Chert)
                .build();

        RockTypes.CHALK = new RockType.Builder("chalk")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreChalk)
                .setMaterial(MaterialsRock.Chalk)
                .setFlux()
                .build();

        RockTypes.MUDSTONE = new RockType.Builder("mudstone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreMudstone)
                .setMaterial(MaterialsRock.Mudstone)
                .setFlux()
                .build();

        RockTypes.SANDSTONE = new RockType.Builder("sandstone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreSandstone)
                .setMaterial(MaterialsRock.Sandstone)
                .setFlux()
                .build();

        RockTypes.SILTSTONE = new RockType.Builder("siltstone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreSiltstone)
                .setMaterial(MaterialsRock.Siltstone)
                .setFlux()
                .build();

        RockTypes.LATERITE = new RockType.Builder("laterite")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreLaterite)
                .setMaterial(MaterialsRock.Laterite)
                .setFlux()
                .build();

        RockTypes.ARKOSE = new RockType.Builder("arkose")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreArkose)
                .setMaterial(MaterialsRock.Arkose)
                .setFlux()
                .build();

        RockTypes.JASPILLITE = new RockType.Builder("jaspillite")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreJaspillite)
                .setMaterial(MaterialsRock.Jaspillite)
                .setFlux()
                .build();

        RockTypes.TRAVERTINE = new RockType.Builder("travertine")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreTravertine)
                .setMaterial(MaterialsRock.Travertine)
                .setFlux()
                .build();

        RockTypes.WACKESTONE = new RockType.Builder("wackestone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreWackestone)
                .setMaterial(MaterialsRock.Wackestone)
                .setFlux()
                .build();

        RockTypes.BLACKBAND_IRONSTONE = new RockType.Builder("blackband_ironstone")
                .setRockCategory(RockCategories.SEDIMENTARY)
                .setOrePrefix(OrePrefixRock.oreBlackbandIronstone)
                .setMaterial(MaterialsRock.BlackbandIronstone)
                .setFlux()
                .build();

        RockTypes.RHYOLITE = new RockType.Builder("rhyolite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreRhyolite)
                .setMaterial(MaterialsRock.Rhyolite)
                .build();

        RockTypes.BASALT = new RockType.Builder("basalt")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreBasalt)
                .setMaterial(MaterialsRock.Basalt)
                .build();

        RockTypes.ANDESITE = new RockType.Builder("andesite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreAndesite)
                .setMaterial(MaterialsRock.Andesite)
                .build();

        RockTypes.DACITE = new RockType.Builder("dacite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreDacite)
                .setMaterial(MaterialsRock.Dacite)
                .build();

        RockTypes.PERIDOTITE = new RockType.Builder("peridotite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(OrePrefixRock.orePeridotite)
                .setMaterial(MaterialsRock.Peridotite)
                .build();

        RockTypes.PORPHYRY = new RockType.Builder("porphyry")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(OrePrefixRock.orePorphyry)
                .setMaterial(MaterialsRock.Porphyry)
                .build();

        RockTypes.BLAIMORITE = new RockType.Builder("blaimorite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreBlaimorite)
                .setMaterial(MaterialsRock.Blaimorite)
                .build();

        RockTypes.BONINITE = new RockType.Builder("boninite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreBoninite)
                .setMaterial(MaterialsRock.Boninite)
                .build();

        RockTypes.CARBONATITE = new RockType.Builder("carbonatite")
                .setRockCategory(RockCategories.IGNEOUS_EXTRUSIVE)
                .setOrePrefix(OrePrefixRock.oreCarbonatite)
                .setMaterial(MaterialsRock.Carbonatite)
                .build();

        RockTypes.QUARTZITE = new RockType.Builder("quartzite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreQuartzite)
                .setMaterial(MaterialsRock.Quartzite)
                .setFlux()
                .build();

        RockTypes.SLATE = new RockType.Builder("slate")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreSlate)
                .setMaterial(MaterialsRock.Slate)
                .build();

        RockTypes.PHYLLITE = new RockType.Builder("phyllite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.orePhyllite)
                .setMaterial(MaterialsRock.Phyllite)
                .build();

        RockTypes.SCHIST = new RockType.Builder("schist")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreSchist)
                .setMaterial(MaterialsRock.Schist)
                .build();

        RockTypes.GNEISS = new RockType.Builder("gneiss")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreGneiss)
                .setMaterial(MaterialsRock.Gneiss)
                .build();

        RockTypes.MARBLE = new RockType.Builder("marble")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreMarble)
                .setMaterial(MaterialsRock.Marble)
                .setFlux()
                .build();

        RockTypes.BLUESCHIST = new RockType.Builder("blueschist")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreBlueschist)
                .setMaterial(MaterialsRock.Blueschist)
                .setFlux()
                .build();

        RockTypes.CATLINITE = new RockType.Builder("catlinite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreCatlinite)
                .setMaterial(MaterialsRock.Catlinite)
                .setFlux()
                .build();

        RockTypes.GREENSCHIST = new RockType.Builder("greenschist")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreGreenschist)
                .setMaterial(MaterialsRock.Greenschist)
                .setFlux()
                .build();

        RockTypes.NOVACULITE = new RockType.Builder("novaculite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreNovaculite)
                .setMaterial(MaterialsRock.Novaculite)
                .setFlux()
                .build();

        RockTypes.SOAPSTONE = new RockType.Builder("soapstone")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreSoapstone)
                .setMaterial(MaterialsRock.Soapstone)
                .setFlux()
                .build();

        RockTypes.KOMATIITE = new RockType.Builder("komatiite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreKomatiite)
                .setMaterial(MaterialsRock.Komatiite)
                .setFlux()
                .build();

        RockTypes.CATACLASITE = new RockType.Builder("cataclasite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreCataclasite)
                .setMaterial(MaterialsRock.Cataclasite)
                .setFlux()
                .build();

        RockTypes.MYLONITE = new RockType.Builder("mylonite")
                .setRockCategory(RockCategories.METAMORPHIC)
                .setOrePrefix(OrePrefixRock.oreMylonite)
                .setMaterial(MaterialsRock.Mylonite)
                .setFlux()
                .build();
    }
}
