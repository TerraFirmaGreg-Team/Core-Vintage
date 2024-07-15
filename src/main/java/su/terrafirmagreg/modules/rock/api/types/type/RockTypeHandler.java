package su.terrafirmagreg.modules.rock.api.types.type;

import su.terrafirmagreg.modules.rock.api.types.category.RockCategoryHandler;


import static gregtech.api.unification.material.Materials.*;
import static su.terrafirmagreg.modules.rock.api.types.category.RockCategories.*;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.*;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRock.*;

public final class RockTypeHandler {

    public static void init() {
        RockCategoryHandler.init();

        RockTypes.GRANITE = RockType
                .builder("granite")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreGranite)
                .setMaterial(Granite)
                .build();

        RockTypes.DIORITE = RockType
                .builder("diorite")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreDiorite)
                .setMaterial(Diorite)
                .build();

        RockTypes.GABBRO = RockType
                .builder("gabbro")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreGabbro)
                .setMaterial(Gabbro)
                .build();

        RockTypes.BRECCIA = RockType
                .builder("breccia")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreBreccia)
                .setMaterial(Breccia)
                .build();

        RockTypes.FOIDOLITE = RockType
                .builder("foidolite")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreFoidolite)
                .setMaterial(Foidolite)
                .build();

        RockTypes.RED_GRANITE = RockType
                .builder("red_granite")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreRedgranite)
                .setMaterial(GraniteRed)
                .build();

        RockTypes.SHALE = RockType
                .builder("shale")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreShale)
                .setMaterial(Shale)
                .build();

        RockTypes.CLAYSTONE = RockType
                .builder("claystone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreClaystone)
                .setMaterial(Claystone)
                .build();

        RockTypes.LIMESTONE = RockType
                .builder("limestone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreLimestone)
                .setMaterial(Limestone)
                .build();

        RockTypes.CONGLOMERATE = RockType
                .builder("conglomerate")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreConglomerate)
                .setMaterial(Conglomerate)
                .build();

        RockTypes.DOLOMITE = RockType
                .builder("dolomite")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreDolomite)
                .setMaterial(Dolomite)
                .setFlux()
                .build();

        RockTypes.CHERT = RockType
                .builder("chert")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreChert)
                .setMaterial(Chert)
                .build();

        RockTypes.CHALK = RockType
                .builder("chalk")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreChalk)
                .setMaterial(Chalk)
                .setFlux()
                .build();

        RockTypes.MUDSTONE = RockType
                .builder("mudstone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreMudstone)
                .setMaterial(Mudstone)
                .setFlux()
                .build();

        RockTypes.SANDSTONE = RockType
                .builder("sandstone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreSandstone)
                .setMaterial(Sandstone)
                .setFlux()
                .build();

        RockTypes.SILTSTONE = RockType
                .builder("siltstone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreSiltstone)
                .setMaterial(Siltstone)
                .setFlux()
                .build();

        RockTypes.LATERITE = RockType
                .builder("laterite")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreLaterite)
                .setMaterial(Laterite)
                .setFlux()
                .build();

        RockTypes.ARKOSE = RockType
                .builder("arkose")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreArkose)
                .setMaterial(Arkose)
                .setFlux()
                .build();

        RockTypes.JASPILLITE = RockType
                .builder("jaspillite")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreJaspillite)
                .setMaterial(Jaspillite)
                .setFlux()
                .build();

        RockTypes.TRAVERTINE = RockType
                .builder("travertine")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreTravertine)
                .setMaterial(Travertine)
                .setFlux()
                .build();

        RockTypes.WACKESTONE = RockType
                .builder("wackestone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreWackestone)
                .setMaterial(Wackestone)
                .setFlux()
                .build();

        RockTypes.BLACKBAND_IRONSTONE = RockType
                .builder("blackband_ironstone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreBlackbandIronstone)
                .setMaterial(BlackbandIronstone)
                .setFlux()
                .build();

        RockTypes.RHYOLITE = RockType
                .builder("rhyolite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreRhyolite)
                .setMaterial(Rhyolite)
                .build();

        RockTypes.BASALT = new RockType
                .Builder("basalt")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreBasalt)
                .setMaterial(Basalt)
                .build();

        RockTypes.ANDESITE = RockType
                .builder("andesite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreAndesite)
                .setMaterial(Andesite)
                .build();

        RockTypes.DACITE = RockType
                .builder("dacite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreDacite)
                .setMaterial(Dacite)
                .build();

        RockTypes.PERIDOTITE = RockType
                .builder("peridotite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(orePeridotite)
                .setMaterial(Peridotite)
                .build();

        RockTypes.PORPHYRY = RockType
                .builder("porphyry")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(orePorphyry)
                .setMaterial(Porphyry)
                .build();

        RockTypes.BLAIMORITE = RockType
                .builder("blaimorite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreBlaimorite)
                .setMaterial(Blaimorite)
                .build();

        RockTypes.BONINITE = RockType
                .builder("boninite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreBoninite)
                .setMaterial(Boninite)
                .build();

        RockTypes.CARBONATITE = RockType
                .builder("carbonatite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreCarbonatite)
                .setMaterial(Carbonatite)
                .build();

        RockTypes.QUARTZITE = RockType
                .builder("quartzite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreQuartzite)
                .setMaterial(Quartzite)
                .setFlux()
                .build();

        RockTypes.SLATE = RockType
                .builder("slate")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreSlate)
                .setMaterial(Slate)
                .build();

        RockTypes.PHYLLITE = RockType
                .builder("phyllite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(orePhyllite)
                .setMaterial(Phyllite)
                .build();

        RockTypes.SCHIST = RockType
                .builder("schist")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreSchist)
                .setMaterial(Schist)
                .build();

        RockTypes.GNEISS = RockType
                .builder("gneiss")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreGneiss)
                .setMaterial(Gneiss)
                .build();

        RockTypes.MARBLE = RockType
                .builder("marble")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreMarble)
                .setMaterial(Marble)
                .setFlux()
                .build();

        RockTypes.BLUESCHIST = RockType
                .builder("blueschist")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreBlueschist)
                .setMaterial(Blueschist)
                .setFlux()
                .build();

        RockTypes.CATLINITE = RockType
                .builder("catlinite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreCatlinite)
                .setMaterial(Catlinite)
                .setFlux()
                .build();

        RockTypes.GREENSCHIST = RockType
                .builder("greenschist")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreGreenschist)
                .setMaterial(Greenschist)
                .setFlux()
                .build();

        RockTypes.NOVACULITE = RockType
                .builder("novaculite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreNovaculite)
                .setMaterial(Novaculite)
                .setFlux()
                .build();

        RockTypes.SOAPSTONE = RockType
                .builder("soapstone")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreSoapstone)
                .setMaterial(Soapstone)
                .setFlux()
                .build();

        RockTypes.KOMATIITE = RockType
                .builder("komatiite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreKomatiite)
                .setMaterial(Komatiite)
                .setFlux()
                .build();

        RockTypes.CATACLASITE = RockType
                .builder("cataclasite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreCataclasite)
                .setMaterial(Cataclasite)
                .setFlux()
                .build();

        RockTypes.MYLONITE = RockType
                .builder("mylonite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreMylonite)
                .setMaterial(Mylonite)
                .setFlux()
                .build();
    }
}
