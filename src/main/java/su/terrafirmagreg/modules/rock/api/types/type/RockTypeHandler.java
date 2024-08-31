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
                .rockCategory(IGNEOUS_INTRUSIVE)
                .orePrefix(oreGranite)
                .material(Granite)
                .build();

        RockTypes.DIORITE = RockType
                .builder("diorite")
                .rockCategory(IGNEOUS_INTRUSIVE)
                .orePrefix(oreDiorite)
                .material(Diorite)
                .build();

        RockTypes.GABBRO = RockType
                .builder("gabbro")
                .rockCategory(IGNEOUS_INTRUSIVE)
                .orePrefix(oreGabbro)
                .material(Gabbro)
                .build();

        RockTypes.BRECCIA = RockType
                .builder("breccia")
                .rockCategory(IGNEOUS_INTRUSIVE)
                .orePrefix(oreBreccia)
                .material(Breccia)
                .build();

        RockTypes.FOIDOLITE = RockType
                .builder("foidolite")
                .rockCategory(IGNEOUS_INTRUSIVE)
                .orePrefix(oreFoidolite)
                .material(Foidolite)
                .build();

        RockTypes.RED_GRANITE = RockType
                .builder("red_granite")
                .rockCategory(IGNEOUS_INTRUSIVE)
                .orePrefix(oreRedgranite)
                .material(GraniteRed)
                .build();

        RockTypes.SHALE = RockType
                .builder("shale")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreShale)
                .material(Shale)
                .build();

        RockTypes.CLAYSTONE = RockType
                .builder("claystone")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreClaystone)
                .material(Claystone)
                .build();

        RockTypes.LIMESTONE = RockType
                .builder("limestone")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreLimestone)
                .material(Limestone)
                .build();

        RockTypes.CONGLOMERATE = RockType
                .builder("conglomerate")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreConglomerate)
                .material(Conglomerate)
                .build();

        RockTypes.DOLOMITE = RockType
                .builder("dolomite")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreDolomite)
                .material(Dolomite)
                .isFlux()
                .build();

        RockTypes.CHERT = RockType
                .builder("chert")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreChert)
                .material(Chert)
                .build();

        RockTypes.CHALK = RockType
                .builder("chalk")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreChalk)
                .material(Chalk)
                .isFlux()
                .build();

        RockTypes.MUDSTONE = RockType
                .builder("mudstone")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreMudstone)
                .material(Mudstone)
                .isFlux()
                .build();

        RockTypes.SANDSTONE = RockType
                .builder("sandstone")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreSandstone)
                .material(Sandstone)
                .isFlux()
                .build();

        RockTypes.SILTSTONE = RockType
                .builder("siltstone")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreSiltstone)
                .material(Siltstone)
                .isFlux()
                .build();

        RockTypes.LATERITE = RockType
                .builder("laterite")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreLaterite)
                .material(Laterite)
                .isFlux()
                .build();

        RockTypes.ARKOSE = RockType
                .builder("arkose")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreArkose)
                .material(Arkose)
                .isFlux()
                .build();

        RockTypes.JASPILLITE = RockType
                .builder("jaspillite")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreJaspillite)
                .material(Jaspillite)
                .isFlux()
                .build();

        RockTypes.TRAVERTINE = RockType
                .builder("travertine")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreTravertine)
                .material(Travertine)
                .isFlux()
                .build();

        RockTypes.WACKESTONE = RockType
                .builder("wackestone")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreWackestone)
                .material(Wackestone)
                .isFlux()
                .build();

        RockTypes.BLACKBAND_IRONSTONE = RockType
                .builder("blackband_ironstone")
                .rockCategory(SEDIMENTARY)
                .orePrefix(oreBlackbandIronstone)
                .material(BlackbandIronstone)
                .isFlux()
                .build();

        RockTypes.RHYOLITE = RockType
                .builder("rhyolite")
                .rockCategory(IGNEOUS_EXTRUSIVE)
                .orePrefix(oreRhyolite)
                .material(Rhyolite)
                .build();

        RockTypes.BASALT = new RockType
                .Builder("basalt")
                .rockCategory(IGNEOUS_EXTRUSIVE)
                .orePrefix(oreBasalt)
                .material(Basalt)
                .build();

        RockTypes.ANDESITE = RockType
                .builder("andesite")
                .rockCategory(IGNEOUS_EXTRUSIVE)
                .orePrefix(oreAndesite)
                .material(Andesite)
                .build();

        RockTypes.DACITE = RockType
                .builder("dacite")
                .rockCategory(IGNEOUS_EXTRUSIVE)
                .orePrefix(oreDacite)
                .material(Dacite)
                .build();

        RockTypes.PERIDOTITE = RockType
                .builder("peridotite")
                .rockCategory(IGNEOUS_EXTRUSIVE)
                .orePrefix(orePeridotite)
                .material(Peridotite)
                .build();

        RockTypes.PORPHYRY = RockType
                .builder("porphyry")
                .rockCategory(IGNEOUS_EXTRUSIVE)
                .orePrefix(orePorphyry)
                .material(Porphyry)
                .build();

        RockTypes.BLAIMORITE = RockType
                .builder("blaimorite")
                .rockCategory(IGNEOUS_EXTRUSIVE)
                .orePrefix(oreBlaimorite)
                .material(Blaimorite)
                .build();

        RockTypes.BONINITE = RockType
                .builder("boninite")
                .rockCategory(IGNEOUS_EXTRUSIVE)
                .orePrefix(oreBoninite)
                .material(Boninite)
                .build();

        RockTypes.CARBONATITE = RockType
                .builder("carbonatite")
                .rockCategory(IGNEOUS_EXTRUSIVE)
                .orePrefix(oreCarbonatite)
                .material(Carbonatite)
                .build();

        RockTypes.QUARTZITE = RockType
                .builder("quartzite")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreQuartzite)
                .material(Quartzite)
                .isFlux()
                .build();

        RockTypes.SLATE = RockType
                .builder("slate")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreSlate)
                .material(Slate)
                .build();

        RockTypes.PHYLLITE = RockType
                .builder("phyllite")
                .rockCategory(METAMORPHIC)
                .orePrefix(orePhyllite)
                .material(Phyllite)
                .build();

        RockTypes.SCHIST = RockType
                .builder("schist")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreSchist)
                .material(Schist)
                .build();

        RockTypes.GNEISS = RockType
                .builder("gneiss")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreGneiss)
                .material(Gneiss)
                .build();

        RockTypes.MARBLE = RockType
                .builder("marble")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreMarble)
                .material(Marble)
                .isFlux()
                .build();

        RockTypes.BLUESCHIST = RockType
                .builder("blueschist")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreBlueschist)
                .material(Blueschist)
                .isFlux()
                .build();

        RockTypes.CATLINITE = RockType
                .builder("catlinite")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreCatlinite)
                .material(Catlinite)
                .isFlux()
                .build();

        RockTypes.GREENSCHIST = RockType
                .builder("greenschist")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreGreenschist)
                .material(Greenschist)
                .isFlux()
                .build();

        RockTypes.NOVACULITE = RockType
                .builder("novaculite")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreNovaculite)
                .material(Novaculite)
                .isFlux()
                .build();

        RockTypes.SOAPSTONE = RockType
                .builder("soapstone")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreSoapstone)
                .material(Soapstone)
                .isFlux()
                .build();

        RockTypes.KOMATIITE = RockType
                .builder("komatiite")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreKomatiite)
                .material(Komatiite)
                .isFlux()
                .build();

        RockTypes.CATACLASITE = RockType
                .builder("cataclasite")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreCataclasite)
                .material(Cataclasite)
                .isFlux()
                .build();

        RockTypes.MYLONITE = RockType
                .builder("mylonite")
                .rockCategory(METAMORPHIC)
                .orePrefix(oreMylonite)
                .material(Mylonite)
                .isFlux()
                .build();
    }
}
