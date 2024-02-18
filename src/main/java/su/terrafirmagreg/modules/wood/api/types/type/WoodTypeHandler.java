package su.terrafirmagreg.modules.wood.api.types.type;

public class WoodTypeHandler {

	public static void init() {
		WoodTypes.ACACIA = new WoodType.Builder("acacia")
				.setColor(0x8B3929)
				.setBurnInfo(650f, 1000)
				.build();

		WoodTypes.ASH = new WoodType.Builder("ash")
				.setColor(0xAE604E)
				.setBurnInfo(696f, 1250)
				.build();

		WoodTypes.ASPEN = new WoodType.Builder("aspen")
				.setColor(0x373727)
				.setBurnInfo(611f, 1000)
				.build();

		WoodTypes.BIRCH = new WoodType.Builder("birch")
				.setColor(0x897658)
				.setBurnInfo(652f, 1750)
				.setTannin()
				.build();

		WoodTypes.BLACKWOOD = new WoodType.Builder("blackwood")
				.setColor(0x1A1A1A)
				.setBurnInfo(720f, 1750)
				.build();

		WoodTypes.CHESTNUT = new WoodType.Builder("chestnut")
				.setColor(0x642C1E)
				.setBurnInfo(651f, 1500)
				.setTannin()
				.build();

		WoodTypes.DOUGLAS_FIR = new WoodType.Builder("douglas_fir")
				.setColor(0xD7BC8D)
				.setBurnInfo(707f, 1500)
				.setTannin()
				.build();

		WoodTypes.HICKORY = new WoodType.Builder("hickory")
				.setColor(0x4E3418)
				.setBurnInfo(762f, 2000)
				.setTannin()
				.build();

		WoodTypes.MAPLE = new WoodType.Builder("maple")
				.setColor(0xC3782F)
				.setBurnInfo(745f, 2000)
				.setTannin()
				.build();

		WoodTypes.OAK = new WoodType.Builder("oak")
				.setColor(0xC29D62)
				.setBurnInfo(728f, 2250)
				.setTannin()
				.build();

		WoodTypes.PALM = new WoodType.Builder("palm")
				.setColor(0xB56F38)
				.setBurnInfo(730f, 1250)
				.build();

		WoodTypes.PINE = new WoodType.Builder("pine")
				.setColor(0xD1BD9A)
				.setBurnInfo(627f, 1250)
				.build();

		WoodTypes.ROSEWOOD = new WoodType.Builder("rosewood")
				.setColor(0x912222)
				.setBurnInfo(640f, 1500)
				.build();

		WoodTypes.SEQUOIA = new WoodType.Builder("sequoia")
				.setColor(0x965B3B)
				.setBurnInfo(612f, 1750)
				.setTannin()
				.build();

		WoodTypes.SPRUCE = new WoodType.Builder("spruce")
				.setColor(0xBF806F)
				.setBurnInfo(608f, 1500)
				.build();

		WoodTypes.SYCAMORE = new WoodType.Builder("sycamore")
				.setColor(0xDCA448)
				.setBurnInfo(653f, 1750)
				.build();

		WoodTypes.WHITE_CEDAR = new WoodType.Builder("white_cedar")
				.setColor(0xD4D4D4)
				.setBurnInfo(625f, 1500)
				.build();

		WoodTypes.WILLOW = new WoodType.Builder("willow")
				.setColor(0x3A430B)
				.setBurnInfo(603f, 1000)
				.build();

		WoodTypes.KAPOK = new WoodType.Builder("kapok")
				.setColor(0xAD879F)
				.setBurnInfo(645f, 1000)
				.build();

		// TFC TECH
		WoodTypes.HEVEA = new WoodType.Builder("hevea")
				.setColor(0x8B3929)
				.setBurnInfo(762f, 2000)
				.build();

		// TFCFlorae
		WoodTypes.AFRICAN_PADAUK = new WoodType.Builder("african_padauk")
				.setColor(0x78251c)
				.setBurnInfo(745f, 1500)
				.build();

		WoodTypes.ALDER = new WoodType.Builder("alder")
				.setColor(0x906836)
				.setBurnInfo(601f, 1000)
				.build();

		WoodTypes.ANGELIM = new WoodType.Builder("angelim")
				.setColor(0x8a5927)
				.setBurnInfo(773f, 1200)
				.build();

		WoodTypes.BALD_CYPRESS = new WoodType.Builder("bald_cypress")
				.setColor(0xdcae70)
				.setBurnInfo(770f, 1300)
				.build();

		WoodTypes.BAOBAB = new WoodType.Builder("baobab")
				.setColor(0xbca374)
				.setBurnInfo(478f, 1000)
				.build();

		WoodTypes.BEECH = new WoodType.Builder("beech")
				.setColor(0xcf9d73)
				.setBurnInfo(703f, 1750)
				.setTannin()
				.build();

		WoodTypes.BLACK_WALNUT = new WoodType.Builder("black_walnut")
				.setColor(0x5d4020)
				.setBurnInfo(758f, 1800)
				.build();

		WoodTypes.BOX = new WoodType.Builder("box")
				.setColor(0xd4cdac)
				.setBurnInfo(683f, 1500)
				.build();

		WoodTypes.BRAZILWOOD = new WoodType.Builder("brazilwood")
				.setColor(0x5d2039)
				.setBurnInfo(710f, 1000)
				.build();

		WoodTypes.BUTTERNUT = new WoodType.Builder("butternut")
				.setColor(0xbc9a6d)
				.setBurnInfo(758f, 1800)
				.build();

		WoodTypes.COCOBOLO = new WoodType.Builder("cocobolo")
				.setColor(0x351d17)
				.setBurnInfo(773f, 1000)
				.build();

		WoodTypes.CYPRESS = new WoodType.Builder("cypress")
				.setColor(0xb3b78d)
				.setBurnInfo(783f, 1100)
				.build();

		WoodTypes.EBONY = new WoodType.Builder("ebony")
				.setColor(0x2d2a26)
				.setBurnInfo(795f, 1000)
				.build();

		WoodTypes.EUCALYPTUS = new WoodType.Builder("eucalyptus")
				.setColor(0xe59d6a)
				.setBurnInfo(705f, 1000)
				.build();

		WoodTypes.EUROPEAN_OAK = new WoodType.Builder("european_oak")
				.setColor(0xaf9775)
				.setBurnInfo(728f, 2250)
				.setTannin()
				.build();

		WoodTypes.FEVER = new WoodType.Builder("fever")
				.setColor(0xc3a677)
				.setBurnInfo(590f, 1000)
				.build();

		WoodTypes.FRUITWOOD = new WoodType.Builder("fruitwood")
				.setColor(0xa37c55)
				.setBurnInfo(720f, 1000)
				.build();

		WoodTypes.GINKGO = new WoodType.Builder("ginkgo")
				.setColor(0xc2b976)
				.setBurnInfo(710f, 1000)
				.build();

		WoodTypes.GREENHEART = new WoodType.Builder("greenheart")
				.setColor(0x3e5f46)
				.setBurnInfo(793f, 1700)
				.build();

		WoodTypes.HAWTHORN = new WoodType.Builder("hawthorn")
				.setColor(0xc25d34)
				.setBurnInfo(683f, 1500)
				.build();

		WoodTypes.HAZEL = new WoodType.Builder("hazel")
				.setColor(0xd2a784)
				.setBurnInfo(683f, 1500)
				.build();

		WoodTypes.HEMLOCK = new WoodType.Builder("hemlock")
				.setColor(0x9b9867)
				.setBurnInfo(609f, 1000)
				.build();

		WoodTypes.HOLLY = new WoodType.Builder("holly")
				.setColor(0xd9d4c3)
				.setBurnInfo(609f, 1000)
				.build();

		WoodTypes.HORNBEAM = new WoodType.Builder("hornbeam")
				.setColor(0xa48434)
				.setBurnInfo(728f, 2250)
				.setTannin()
				.build();

		WoodTypes.IPE = new WoodType.Builder("ipe")
				.setColor(0x372217)
				.setBurnInfo(785f, 1200)
				.build();

		WoodTypes.IROKO = new WoodType.Builder("iroko")
				.setColor(0x6c2900)
				.setBurnInfo(785f, 1200)
				.build();

		WoodTypes.IRONWOOD = new WoodType.Builder("ironwood")
				.setColor(0xa6896a)
				.setBurnInfo(694f, 1170)
				.build();

		WoodTypes.JACARANDA = new WoodType.Builder("jacaranda")
				.setColor(0x9d7c69)
				.setBurnInfo(795f, 1250)
				.build();

		WoodTypes.JUNIPER = new WoodType.Builder("juniper")
				.setColor(0x978e75)
				.setBurnInfo(632f, 1750)
				.setTannin()
				.build();

		WoodTypes.KAURI = new WoodType.Builder("kauri")
				.setColor(0x995f30)
				.setBurnInfo(730f, 1250)
				.build();

		WoodTypes.LARCH = new WoodType.Builder("larch")
				.setColor(0xa57566)
				.setBurnInfo(632f, 1250)
				.build();

		WoodTypes.LIMBA = new WoodType.Builder("limba")
				.setColor(0xe0bc5b)
				.setBurnInfo(710f, 1000)
				.build();

		WoodTypes.LOCUST = new WoodType.Builder("locust")
				.setColor(0xb0855a)
				.setBurnInfo(653f, 1750)
				.build();

		WoodTypes.LOGWOOD = new WoodType.Builder("logwood")
				.setColor(0x80311c)
				.setBurnInfo(695f, 1000)
				.build();

		WoodTypes.MACLURA = new WoodType.Builder("maclura")
				.setColor(0xc1990e)
				.setBurnInfo(773f, 1000)
				.build();

		WoodTypes.MAHOE = new WoodType.Builder("mahoe")
				.setColor(0x666e72)
				.setBurnInfo(783f, 1100)
				.build();

		WoodTypes.MAHOGANY = new WoodType.Builder("mahogany")
				.setColor(0x7b6057)
				.setBurnInfo(773f, 1000)
				.build();

		WoodTypes.MANGROVE = new WoodType.Builder("mangrove")
				.setColor(0x602020)
				.setBurnInfo(783f, 1100)
				.build();

		WoodTypes.MARBLEWOOD = new WoodType.Builder("marblewood")
				.setColor(0x9a6030)
				.setBurnInfo(837f, 1200)
				.build();

		WoodTypes.MESSMATE = new WoodType.Builder("messmate")
				.setColor(0xa59a7f)
				.setBurnInfo(696f, 1250)
				.build();

		WoodTypes.MOUNTAIN_ASH = new WoodType.Builder("mountain_ash")
				.setColor(0xc3a06c)
				.setBurnInfo(696f, 1250)
				.build();

		WoodTypes.NORDMANN_FIR = new WoodType.Builder("nordmann_fir")
				.setColor(0x644c2e)
				.setBurnInfo(628f, 1500)
				.build();

		WoodTypes.NORWAY_SPRUCE = new WoodType.Builder("norway_spruce")
				.setColor(0xd6baa0)
				.setBurnInfo(628f, 1500)
				.build();

		WoodTypes.PINK_CHERRY = new WoodType.Builder("pink_cherry")
				.setColor(0xd19176)
				.setBurnInfo(795f, 1250)
				.build();

		WoodTypes.PINK_IVORY = new WoodType.Builder("pink_ivory")
				.setColor(0xee6276)
				.setBurnInfo(773f, 1000)
				.build();

		WoodTypes.POPLAR = new WoodType.Builder("poplar")
				.setColor(0xd4c99b)
				.setBurnInfo(609f, 1000)
				.build();

		WoodTypes.PURPLEHEART = new WoodType.Builder("purpleheart")
				.setColor(0x3e001d)
				.setBurnInfo(793f, 1700)
				.build();

		WoodTypes.RED_CEDAR = new WoodType.Builder("red_cedar")
				.setColor(0x855f4b)
				.setBurnInfo(618f, 1750)
				.setTannin()
				.build();

		WoodTypes.RED_ELM = new WoodType.Builder("red_elm")
				.setColor(0xc8955c)
				.setBurnInfo(618f, 1750)
				.setTannin()
				.build();

		WoodTypes.REDWOOD = new WoodType.Builder("redwood")
				.setColor(0x8e4c2b)
				.setBurnInfo(618f, 1750)
				.setTannin()
				.build();

		WoodTypes.ROWAN = new WoodType.Builder("rowan")
				.setColor(0x9d8682)
				.setBurnInfo(645f, 2000)
				.build();

		WoodTypes.RUBBER_FIG = new WoodType.Builder("rubber_fig")
				.setColor(0xbc6607)
				.setBurnInfo(785f, 1440)
				.build();

		WoodTypes.SWEETGUM = new WoodType.Builder("sweetgum")
				.setColor(0xac7a3c)
				.setBurnInfo(745f, 2000)
				.setTannin()
				.build();

		WoodTypes.SYZYGIUM = new WoodType.Builder("syzygium")
				.setColor(0xbf9b9b)
				.setBurnInfo(745f, 2000)
				.setTannin()
				.build();

		WoodTypes.TEAK = new WoodType.Builder("teak")
				.setColor(0xa2753d)
				.setBurnInfo(695f, 1000)
				.build();

		WoodTypes.WALNUT = new WoodType.Builder("walnut")
				.setColor(0x322218)
				.setBurnInfo(758f, 1800)
				.build();

		WoodTypes.WENGE = new WoodType.Builder("wenge")
				.setColor(0x4c4842)
				.setBurnInfo(773f, 1250)
				.build();

		WoodTypes.WHITE_CHERRY = new WoodType.Builder("white_cherry")
				.setColor(0xc6a190)
				.setBurnInfo(795f, 1250)
				.build();

		WoodTypes.WHITE_ELM = new WoodType.Builder("white_elm")
				.setColor(0xb3af8d)
				.setBurnInfo(653f, 1750)
				.setTannin()
				.build();

		WoodTypes.WHITEBEAM = new WoodType.Builder("whitebeam")
				.setColor(0xafa49d)
				.setBurnInfo(728f, 1750)
				.setTannin()
				.build();

		WoodTypes.YELLOW_MERANTI = new WoodType.Builder("yellow_meranti")
				.setColor(0xad8861)
				.setBurnInfo(837f, 1200)
				.build();

		WoodTypes.YEW = new WoodType.Builder("yew")
				.setColor(0xdb9b70)
				.setBurnInfo(813f, 2150)
				.build();

		WoodTypes.ZEBRAWOOD = new WoodType.Builder("zebrawood")
				.setColor(0x92744c)
				.setBurnInfo(822f, 1570)
				.build();

		// Фруктовые
		WoodTypes.BANANA = new WoodType.Builder("banana")
				.setColor(0xBB9C7D)
				.setBurnInfo(720f, 1750)
				.build();


		WoodTypes.CHERRY = new WoodType.Builder("cherry")
				.setColor(0xDC5E2C)
				.setBurnInfo(720f, 1750)
				.build();


		WoodTypes.GREEN_APPLE = new WoodType.Builder("green_apple")
				.setColor(0xCFC498)
				.setBurnInfo(720f, 1750)
				.build();


		WoodTypes.LEMON = new WoodType.Builder("lemon")
				.setColor(0xE8DD8C)
				.setBurnInfo(720f, 1750)
				.build();


		WoodTypes.OLIVE = new WoodType.Builder("olive")
				.setColor(0x97492B)
				.setBurnInfo(720f, 1750)
				.build();


		WoodTypes.ORANGE = new WoodType.Builder("orange")
				.setColor(0xC18C52)
				.setBurnInfo(720f, 1750)
				.build();


		WoodTypes.PEACH = new WoodType.Builder("peach")
				.setColor(0x9A5B37)
				.setBurnInfo(720f, 1750)
				.build();


		WoodTypes.PLUM = new WoodType.Builder("plum")
				.setColor(0x9F573B)
				.setBurnInfo(720f, 1750)
				.build();


		WoodTypes.RED_APPLE = new WoodType.Builder("red_apple")
				.setColor(0x6E150E)
				.setBurnInfo(720f, 1750)
				.build();

		WoodTypes.COCOA = new WoodType.Builder("cocoa")
				.setColor(0xC29D79)
				.setBurnInfo(822f, 1570)
				.build();

		// ?
		WoodTypes.CINNAMON = new WoodType.Builder("cinnamon")
				.setColor(0x8c6333)
				.setBurnInfo(822f, 1570)
				.build();

//        WoodTypes.CASSIA_CINNAMON = new WoodType.Builder("cassia_cinnamon")
//                .setColor(0x92744c)
//                .setBurnInfo(822f, 1570)
//                .build();
//
//        WoodTypes.CEYLON_CINNAMON = new WoodType.Builder("ceylon_cinnamon")
//                .setColor(0x92744c)
//                .setBurnInfo(822f, 1570)
//                .build();

	}
}
