package su.terrafirmagreg.modules.rock.api.types.type;

import su.terrafirmagreg.modules.rock.api.types.category.RockCategoryHandler;


import static gregtech.api.unification.material.Materials.*;
import static su.terrafirmagreg.modules.rock.api.types.category.RockCategories.*;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialsRock.*;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRock.*;

public final class RockTypeHandler {

    public static void init() {
        RockCategoryHandler.init();

        RockTypes.GRANITE = new RockType
                .Builder("granite")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreGranite)
                .setMaterial(Granite)
                .build();

        RockTypes.DIORITE = new RockType
                .Builder("diorite")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreDiorite)
                .setMaterial(Diorite)
                .build();

        RockTypes.GABBRO = new RockType
                .Builder("gabbro")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreGabbro)
                .setMaterial(Gabbro)
                .build();

        RockTypes.BRECCIA = new RockType
                .Builder("breccia")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreBreccia)
                .setMaterial(Breccia)
                .build();

        RockTypes.FOIDOLITE = new RockType
                .Builder("foidolite")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreFoidolite)
                .setMaterial(Foidolite)
                .build();

        RockTypes.RED_GRANITE = new RockType
                .Builder("red_granite")
                .setRockCategory(IGNEOUS_INTRUSIVE)
                .setOrePrefix(oreRedgranite)
                .setMaterial(GraniteRed)
                .build();

        RockTypes.SHALE = new RockType
                .Builder("shale")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreShale)
                .setMaterial(Shale)
                .build();

        RockTypes.CLAYSTONE = new RockType
                .Builder("claystone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreClaystone)
                .setMaterial(Claystone)
                .build();

        RockTypes.LIMESTONE = new RockType
                .Builder("limestone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreLimestone)
                .setMaterial(Limestone)
                .build();

        RockTypes.CONGLOMERATE = new RockType
                .Builder("conglomerate")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreConglomerate)
                .setMaterial(Conglomerate)
                .build();

        RockTypes.DOLOMITE = new RockType
                .Builder("dolomite")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreDolomite)
                .setMaterial(Dolomite)
                .setFlux()
                .build();

        RockTypes.CHERT = new RockType
                .Builder("chert")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreChert)
                .setMaterial(Chert)
                .build();

        RockTypes.CHALK = new RockType
                .Builder("chalk")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreChalk)
                .setMaterial(Chalk)
                .setFlux()
                .build();

        RockTypes.MUDSTONE = new RockType
                .Builder("mudstone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreMudstone)
                .setMaterial(Mudstone)
                .setFlux()
                .build();

        RockTypes.SANDSTONE = new RockType
                .Builder("sandstone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreSandstone)
                .setMaterial(Sandstone)
                .setFlux()
                .build();

        RockTypes.SILTSTONE = new RockType
                .Builder("siltstone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreSiltstone)
                .setMaterial(Siltstone)
                .setFlux()
                .build();

        RockTypes.LATERITE = new RockType
                .Builder("laterite")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreLaterite)
                .setMaterial(Laterite)
                .setFlux()
                .build();

        RockTypes.ARKOSE = new RockType
                .Builder("arkose")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreArkose)
                .setMaterial(Arkose)
                .setFlux()
                .build();

        RockTypes.JASPILLITE = new RockType
                .Builder("jaspillite")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreJaspillite)
                .setMaterial(Jaspillite)
                .setFlux()
                .build();

        RockTypes.TRAVERTINE = new RockType
                .Builder("travertine")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreTravertine)
                .setMaterial(Travertine)
                .setFlux()
                .build();

        RockTypes.WACKESTONE = new RockType
                .Builder("wackestone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreWackestone)
                .setMaterial(Wackestone)
                .setFlux()
                .build();

        RockTypes.BLACKBAND_IRONSTONE = new RockType
                .Builder("blackband_ironstone")
                .setRockCategory(SEDIMENTARY)
                .setOrePrefix(oreBlackbandIronstone)
                .setMaterial(BlackbandIronstone)
                .setFlux()
                .build();

        RockTypes.RHYOLITE = new RockType
                .Builder("rhyolite")
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

        RockTypes.ANDESITE = new RockType
                .Builder("andesite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreAndesite)
                .setMaterial(Andesite)
                .build();

        RockTypes.DACITE = new RockType
                .Builder("dacite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreDacite)
                .setMaterial(Dacite)
                .build();

        RockTypes.PERIDOTITE = new RockType
                .Builder("peridotite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(orePeridotite)
                .setMaterial(Peridotite)
                .build();

        RockTypes.PORPHYRY = new RockType
                .Builder("porphyry")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(orePorphyry)
                .setMaterial(Porphyry)
                .build();

        RockTypes.BLAIMORITE = new RockType
                .Builder("blaimorite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreBlaimorite)
                .setMaterial(Blaimorite)
                .build();

        RockTypes.BONINITE = new RockType
                .Builder("boninite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreBoninite)
                .setMaterial(Boninite)
                .build();

        RockTypes.CARBONATITE = new RockType
                .Builder("carbonatite")
                .setRockCategory(IGNEOUS_EXTRUSIVE)
                .setOrePrefix(oreCarbonatite)
                .setMaterial(Carbonatite)
                .build();

        RockTypes.QUARTZITE = new RockType
                .Builder("quartzite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreQuartzite)
                .setMaterial(Quartzite)
                .setFlux()
                .build();

        RockTypes.SLATE = new RockType
                .Builder("slate")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreSlate)
                .setMaterial(Slate)
                .build();

        RockTypes.PHYLLITE = new RockType
                .Builder("phyllite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(orePhyllite)
                .setMaterial(Phyllite)
                .build();

        RockTypes.SCHIST = new RockType
                .Builder("schist")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreSchist)
                .setMaterial(Schist)
                .build();

        RockTypes.GNEISS = new RockType
                .Builder("gneiss")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreGneiss)
                .setMaterial(Gneiss)
                .build();

        RockTypes.MARBLE = new RockType
                .Builder("marble")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreMarble)
                .setMaterial(Marble)
                .setFlux()
                .build();

        RockTypes.BLUESCHIST = new RockType
                .Builder("blueschist")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreBlueschist)
                .setMaterial(Blueschist)
                .setFlux()
                .build();

        RockTypes.CATLINITE = new RockType
                .Builder("catlinite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreCatlinite)
                .setMaterial(Catlinite)
                .setFlux()
                .build();

        RockTypes.GREENSCHIST = new RockType
                .Builder("greenschist")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreGreenschist)
                .setMaterial(Greenschist)
                .setFlux()
                .build();

        RockTypes.NOVACULITE = new RockType
                .Builder("novaculite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreNovaculite)
                .setMaterial(Novaculite)
                .setFlux()
                .build();

        RockTypes.SOAPSTONE = new RockType
                .Builder("soapstone")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreSoapstone)
                .setMaterial(Soapstone)
                .setFlux()
                .build();

        RockTypes.KOMATIITE = new RockType
                .Builder("komatiite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreKomatiite)
                .setMaterial(Komatiite)
                .setFlux()
                .build();

        RockTypes.CATACLASITE = new RockType
                .Builder("cataclasite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreCataclasite)
                .setMaterial(Cataclasite)
                .setFlux()
                .build();

        RockTypes.MYLONITE = new RockType
                .Builder("mylonite")
                .setRockCategory(METAMORPHIC)
                .setOrePrefix(oreMylonite)
                .setMaterial(Mylonite)
                .setFlux()
                .build();
    }
}
