package su.terrafirmagreg.modules.wood.api.types.type;

public class WoodTypeHandler {

    public static void init() {
        WoodTypes.ACACIA = WoodType
                .builder("acacia")
                .setBurnInfo(650f, 1000)
                .setColor(9745287)
                .build();

        WoodTypes.ALDER = WoodType
                .builder("alder")
                .setBurnInfo(601f, 1000)
                .setColor(12092755)
                .build();

        WoodTypes.APPLE = WoodType
                .builder("apple")
                .setBurnInfo(720f, 1000)
                .setColor(6305064)
                .build();

        WoodTypes.ASH = WoodType
                .builder("ash")
                .setBurnInfo(696f, 1250)
                .setColor(16107368)
                .build();

        WoodTypes.BANANA = WoodType
                .builder("banana")
                .setBurnInfo(720f, 1750)
                .setColor(9466691)
                .build();

        WoodTypes.BAOBAB = WoodType
                .builder("baobab")
                .setBurnInfo(478f, 1000)
                .setColor(9608290)
                .build();

        WoodTypes.BALSA = WoodType
                .builder("balsa")
                .setBurnInfo(783f, 1100)
                .setColor(11117209)
                .build();

        WoodTypes.DARK_OAK = WoodType
                .builder("dark_oak")
                .setBurnInfo(611f, 1000)
                .setColor(4599061)
                .build();

        WoodTypes.BIRCH = WoodType
                .builder("birch")
                .setBurnInfo(652f, 1750)
                .setColor(14139781)
                .setTannin()
                .build();

        WoodTypes.JUNGLE = WoodType
                .builder("jungle")
                .setBurnInfo(720f, 1750)
                .setColor(11632732)
                .build();

        WoodTypes.CHESTNUT = WoodType
                .builder("chestnut")
                .setBurnInfo(651f, 1500)
                .setColor(12298845)
                .setTannin()
                .build();

        WoodTypes.HICKORY = WoodType
                .builder("hickory")
                .setBurnInfo(762f, 2000)
                .setColor(14333070)
                .setTannin()
                .build();

        WoodTypes.MAPLE = WoodType
                .builder("maple")
                .setBurnInfo(745f, 2000)
                .setColor(11431211)
                .setTannin()
                .build();

        WoodTypes.OAK = WoodType
                .builder("oak")
                .setBurnInfo(728f, 2250)
                .setColor(11833434)
                .setTannin()
                .build();

        WoodTypes.PALM = WoodType
                .builder("palm")
                .setBurnInfo(730f, 1250)
                .setColor(13271115)
                .build();

        WoodTypes.PINE = WoodType
                .builder("pine")
                .setBurnInfo(627f, 1250)
                .setColor(12885585)
                .build();

        WoodTypes.ROSEWOOD = WoodType
                .builder("rosewood")
                .setBurnInfo(640f, 1500)
                .setColor(7738624)
                .build();

        WoodTypes.SEQUOIA = WoodType
                .builder("sequoia")
                .setBurnInfo(612f, 1750)
                .setColor(10050135)
                .setTannin()
                .build();

        WoodTypes.SPRUCE = WoodType
                .builder("spruce")
                .setBurnInfo(608f, 1500)
                .setColor(8412726)
                .build();

        WoodTypes.PAPAYA = WoodType
                .builder("papaya")
                .setBurnInfo(653f, 1750)
                .setColor(14470005)
                .build();

        WoodTypes.CITRUS = WoodType
                .builder("citrus")
                .setColor(10266653)
                .setBurnInfo(625f, 1500)
                .build();

        WoodTypes.WILLOW = WoodType
                .builder("willow")
                .setBurnInfo(603f, 1000)
                .setColor(11710818)
                .build();

        WoodTypes.KAPOK = WoodType
                .builder("kapok")
                .setBurnInfo(645f, 1000)
                .setColor(8156212)
                .build();

        WoodTypes.HEVEA = WoodType
                .builder("hevea")
                .setBurnInfo(762f, 2000)
                .setColor(0x8B3929)
                .build();

        WoodTypes.GIGANTEUM = WoodType
                .builder("giganteum")
                .setBurnInfo(745f, 1500)
                .setColor(5186590)
                .build();

        WoodTypes.BEECH = WoodType
                .builder("beech")
                .setBurnInfo(703f, 1750)
                .setColor(14784849)
                .setTannin()
                .build();

        WoodTypes.FIR = WoodType
                .builder("fir")
                .setBurnInfo(758f, 1800)
                .setColor(12815444)
                .build();

        WoodTypes.BOX = WoodType
                .builder("box")
                .setBurnInfo(683f, 1500)
                .setColor(16511430)
                .build();

        WoodTypes.BRAZILWOOD = WoodType
                .builder("brazilwood")
                .setBurnInfo(710f, 1000)
                .setColor(7487063)
                .build();

        WoodTypes.BUTTERNUT = WoodType
                .builder("butternut")
                .setBurnInfo(758f, 1800)
                .setColor(15510138)
                .build();

        WoodTypes.COCOBOLO = WoodType
                .builder("cocobolo")
                .setBurnInfo(773f, 1000)
                .setColor(7541506)
                .build();

        WoodTypes.CYPRESS = WoodType
                .builder("cypress")
                .setBurnInfo(783f, 1100)
                .setColor(16169052)
                .build();

        WoodTypes.EBONY = WoodType
                .builder("ebony")
                .setBurnInfo(795f, 1000)
                .setColor(3946288)
                .build();

        WoodTypes.EUCALYPTUS = WoodType
                .builder("eucalyptus")
                .setBurnInfo(705f, 1000)
                .setColor(16165771)
                .build();

        WoodTypes.CEDAR = WoodType
                .builder("cedar")
                .setBurnInfo(728f, 2250)
                .setTannin()
                .setColor(14181940)
                .build();

        WoodTypes.FIG = WoodType
                .builder("fig")
                .setBurnInfo(590f, 1000)
                .setColor(13142058)
                .build();

        WoodTypes.ELM = WoodType
                .builder("elm")
                .setBurnInfo(710f, 1000)
                .setColor(15772004)
                .build();

        WoodTypes.GREENHEART = WoodType
                .builder("greenheart")
                .setBurnInfo(793f, 1700)
                .setColor(5144156)
                .build();

        WoodTypes.HAWTHORN = WoodType
                .builder("hawthorn")
                .setBurnInfo(683f, 1500)
                .setColor(13402978)
                .build();

        WoodTypes.HAZEL = WoodType
                .builder("hazel")
                .setBurnInfo(683f, 1500)
                .setColor(13480341)
                .build();

        WoodTypes.HEMLOCK = WoodType
                .builder("hemlock")
                .setBurnInfo(609f, 1000)
                .setColor(13088108)
                .build();

        WoodTypes.HOLLY = WoodType
                .builder("holly")
                .setBurnInfo(609f, 1000)
                .setColor(16512743)
                .build();

        WoodTypes.HORNBEAM = WoodType
                .builder("hornbeam")
                .setBurnInfo(728f, 2250)
                .setColor(12818528)
                .setTannin()
                .build();

        WoodTypes.IPE = WoodType
                .builder("ipe")
                .setBurnInfo(785f, 1200)
                .setColor(5057822)
                .build();

        WoodTypes.IROKO = WoodType
                .builder("iroko")
                .setBurnInfo(785f, 1200)
                .setColor(7681024)
                .build();

        WoodTypes.PEAR = WoodType
                .builder("pear")
                .setBurnInfo(694f, 1170)
                .setColor(12093805)
                .build();

        WoodTypes.PADAUK = WoodType
                .builder("padauk")
                .setBurnInfo(795f, 1250)
                .setColor(11756341)
                .build();

        WoodTypes.ELDER = WoodType
                .builder("elder")
                .setBurnInfo(632f, 1750)
                .setColor(12489337)
                .setTannin()
                .build();

        WoodTypes.SYZGIUM = WoodType
                .builder("syzgium")
                .setBurnInfo(730f, 1250)
                .setColor(15123393)
                .build();

        WoodTypes.LARCH = WoodType
                .builder("larch")
                .setBurnInfo(632f, 1250)
                .setColor(14131085)
                .build();

        WoodTypes.LIME = WoodType
                .builder("lime")
                .setBurnInfo(710f, 1000)
                .setColor(13544048)
                .build();

        WoodTypes.LOCUST = WoodType
                .builder("locust")
                .setBurnInfo(653f, 1750)
                .setColor(12816736)
                .build();

        WoodTypes.LOGWOOD = WoodType
                .builder("logwood")
                .setBurnInfo(695f, 1000)
                .setColor(10762028)
                .build();

        WoodTypes.MACLURA = WoodType
                .builder("maclura")
                .setBurnInfo(773f, 1000)
                .setColor(15970862)
                .build();

        WoodTypes.MAHOE = WoodType
                .builder("mahoe")
                .setBurnInfo(783f, 1100)
                .setColor(8362154)
                .build();

        WoodTypes.MAHOGANY = WoodType
                .builder("mahogany")
                .setBurnInfo(773f, 1000)
                .setColor(7749432)
                .build();

        WoodTypes.PINKIVORY = WoodType
                .builder("pinkivory")
                .setBurnInfo(773f, 1000)
                .setColor(15502496)
                .build();

        WoodTypes.POPLAR = WoodType
                .builder("poplar")
                .setBurnInfo(609f, 1000)
                .setColor(13619074)
                .build();

        WoodTypes.PURPLEHEART = WoodType
                .builder("purpleheart")
                .setBurnInfo(793f, 1700)
                .setColor(5970991)
                .build();

        WoodTypes.GINGKO = WoodType
                .builder("gingko")
                .setBurnInfo(618f, 1750)
                .setColor(16050106)
                .setTannin()
                .build();

        WoodTypes.ROWAN = WoodType
                .builder("rowan")
                .setBurnInfo(645f, 2000)
                .setColor(13610394)
                .build();

        WoodTypes.SWEETGUM = WoodType
                .builder("sweetgum")
                .setBurnInfo(745f, 2000)
                .setColor(13997656)
                .setTannin()
                .build();

        WoodTypes.TEAK = WoodType
                .builder("teak")
                .setBurnInfo(695f, 1000)
                .setColor(8223075)
                .build();

        WoodTypes.WALNUT = WoodType
                .builder("walnut")
                .setBurnInfo(758f, 1800)
                .setColor(6836802)
                .build();

        WoodTypes.WENGE = WoodType
                .builder("wenge")
                .setBurnInfo(773f, 1250)
                .setColor(6182474)
                .build();

        WoodTypes.WHITEBEAM = WoodType
                .builder("whitebeam")
                .setBurnInfo(728f, 1750)
                .setColor(13222585)
                .setTannin()
                .build();

        WoodTypes.YEW = WoodType
                .builder("yew")
                .setBurnInfo(813f, 2150)
                .setColor(14722426)
                .build();

        WoodTypes.ZEBRAWOOD = WoodType
                .builder("zebrawood")
                .setBurnInfo(822f, 1570)
                .setColor(10912334)
                .build();

        WoodTypes.CHERRY = WoodType
                .builder("cherry")
                .setBurnInfo(720f, 1750)
                .setColor(11895348)
                .build();

        WoodTypes.OLIVE = WoodType
                .builder("olive")
                .setBurnInfo(720f, 1750)
                .setColor(11578760)
                .build();

        WoodTypes.PLUM = WoodType
                .builder("plum")
                .setBurnInfo(720f, 1750)
                .setColor(11364479)
                .build();
    }
}
