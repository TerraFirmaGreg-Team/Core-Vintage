package su.terrafirmagreg.modules.rock.plugin.gregtech.material;

import static su.terrafirmagreg.modules.rock.ModuleRock.LOGGER;

public class MaterialRockHandler {

    public static void init() {

        LOGGER.info("Registered materials");
        // Igneous Intrusive
        MaterialsRock.Gabbro = new MaterialRock.Builder("gabbro")
                .dust()
                .color(0x7F8081)
                .build();

        MaterialsRock.Breccia = new MaterialRock.Builder("breccia")
                .dust()
                .color(0x7F8081)
                .build();

        MaterialsRock.Foidolite = new MaterialRock.Builder("foidolite")
                .dust()
                .color(0x7F8081)
                .build();

        // Sedimentary
        MaterialsRock.Shale = new MaterialRock.Builder("shale")
                .dust()
                .color(0x686567)
                .build();

        MaterialsRock.Claystone = new MaterialRock.Builder("claystone")
                .dust()
                .color(0xAF9377)
                .build();

        MaterialsRock.Limestone = new MaterialRock.Builder("limestone")
                .dust()
                .color(0xA09885)
                .build();

        MaterialsRock.Conglomerate = new MaterialRock.Builder("conglomerate")
                .dust()
                .color(0xA3977F)
                .build();

        MaterialsRock.Dolomite = new MaterialRock.Builder("dolomite")
                .dust()
                .color(0x515155)
                .build();

        MaterialsRock.Chert = new MaterialRock.Builder("chert")
                .dust()
                .color(0x7A6756)
                .build();

        MaterialsRock.Chalk = new MaterialRock.Builder("chalk")
                .dust()
                .color(0xA4A39F)
                .build();

        MaterialsRock.Mudstone = new MaterialRock.Builder("mudstone")
                .dust()
                .color(0xA4A39F)
                .build();

        MaterialsRock.Sandstone = new MaterialRock.Builder("sandstone")
                .dust()
                .color(0xA4A39F)
                .build();

        MaterialsRock.Siltstone = new MaterialRock.Builder("siltstone")
                .dust()
                .color(0xA4A39F)
                .build();

        MaterialsRock.Laterite = new MaterialRock.Builder("laterite")
                .dust()
                .color(0xA4A39F)
                .build();

        MaterialsRock.Arkose = new MaterialRock.Builder("arkose")
                .dust()
                .color(0xA4A39F)
                .build();

        MaterialsRock.Jaspillite = new MaterialRock.Builder("jaspillite")
                .dust()
                .color(0xA4A39F)
                .build();

        MaterialsRock.Travertine = new MaterialRock.Builder("travertine")
                .dust()
                .color(0xA4A39F)
                .build();

        MaterialsRock.Wackestone = new MaterialRock.Builder("wackestone")
                .dust()
                .color(0xA4A39F)
                .build();

        MaterialsRock.BlackbandIronstone = new MaterialRock.Builder("blackband_ironstone")
                .dust()
                .color(0xA4A39F)
                .build();

        // Igneous Extrusive
        MaterialsRock.Rhyolite = new MaterialRock.Builder("rhyolite")
                .dust()
                .color(0x726D69)
                .build();

        MaterialsRock.Dacite = new MaterialRock.Builder("dacite")
                .dust()
                .color(0x979797)
                .build();

        MaterialsRock.Porphyry = new MaterialRock.Builder("porphyry")
                .dust()
                .color(0x7F8081)
                .build();

        MaterialsRock.Peridotite = new MaterialRock.Builder("peridotite")
                .dust()
                .color(0x979797)
                .build();

        MaterialsRock.Blaimorite = new MaterialRock.Builder("blaimorite")
                .dust()
                .color(0x979797)
                .build();

        MaterialsRock.Boninite = new MaterialRock.Builder("boninite")
                .dust()
                .color(0x979797)
                .build();

        MaterialsRock.Carbonatite = new MaterialRock.Builder("carbonatite")
                .dust()
                .color(0x979797)
                .build();

        // Metamorphic
        MaterialsRock.Slate = new MaterialRock.Builder("slate")
                .dust()
                .color(0x989287)
                .build();

        MaterialsRock.Phyllite = new MaterialRock.Builder("phyllite")
                .dust()
                .color(0x706B69)
                .build();

        MaterialsRock.Schist = new MaterialRock.Builder("schist")
                .dust()
                .color(0x6E735C)
                .build();

        MaterialsRock.Gneiss = new MaterialRock.Builder("gneiss")
                .dust()
                .color(0x6A6D60)
                .build();

        MaterialsRock.Blueschist = new MaterialRock.Builder("blueschist")
                .dust()
                .color(0x6A6D60)
                .build();

        MaterialsRock.Catlinite = new MaterialRock.Builder("catlinite")
                .dust()
                .color(0x6A6D60)
                .build();

        MaterialsRock.Greenschist = new MaterialRock.Builder("greenschist")
                .dust()
                .color(0x6A6D60)
                .build();

        MaterialsRock.Novaculite = new MaterialRock.Builder("novaculite")
                .dust()
                .color(0x6A6D60)
                .build();

        MaterialsRock.Komatiite = new MaterialRock.Builder("komatiite")
                .dust()
                .color(0x6A6D60)
                .build();

        MaterialsRock.Cataclasite = new MaterialRock.Builder("cataclasite")
                .dust()
                .color(0x6A6D60)
                .build();

        MaterialsRock.Mylonite = new MaterialRock.Builder("mylonite")
                .dust()
                .color(0x6A6D60)
                .build();
    }

}
