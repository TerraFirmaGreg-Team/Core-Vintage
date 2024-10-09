package su.terrafirmagreg.modules.rock.api.types.type;

import su.terrafirmagreg.modules.rock.api.types.category.RockCategoryHandler;

import static gregtech.api.unification.material.Materials.Andesite;
import static gregtech.api.unification.material.Materials.Basalt;
import static gregtech.api.unification.material.Materials.Diorite;
import static gregtech.api.unification.material.Materials.Granite;
import static gregtech.api.unification.material.Materials.GraniteRed;
import static gregtech.api.unification.material.Materials.Marble;
import static gregtech.api.unification.material.Materials.Quartzite;
import static gregtech.api.unification.material.Materials.Soapstone;
import static su.terrafirmagreg.modules.rock.api.types.category.RockCategories.IGNEOUS_EXTRUSIVE;
import static su.terrafirmagreg.modules.rock.api.types.category.RockCategories.IGNEOUS_INTRUSIVE;
import static su.terrafirmagreg.modules.rock.api.types.category.RockCategories.METAMORPHIC;
import static su.terrafirmagreg.modules.rock.api.types.category.RockCategories.SEDIMENTARY;
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
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRock.oreAndesite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRock.oreBasalt;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRock.oreDiorite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRock.oreGranite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRock.oreMarble;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRock.oreRedgranite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreArkose;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreBlackbandIronstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreBlaimorite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreBlueschist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreBoninite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreBreccia;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreCarbonatite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreCataclasite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreCatlinite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreChalk;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreChert;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreClaystone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreConglomerate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreDacite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreDolomite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreFoidolite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreGabbro;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreGneiss;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreGreenschist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreJaspillite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreKomatiite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreLaterite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreLimestone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreMudstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreMylonite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreNovaculite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.orePeridotite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.orePhyllite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.orePorphyry;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreQuartzite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreRhyolite;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreSandstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreSchist;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreShale;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreSiltstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreSlate;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreSoapstone;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreTravertine;
import static su.terrafirmagreg.modules.rock.plugin.gregtech.oreprefix.OrePrefixRockHandler.oreWackestone;

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
