package su.terrafirmagreg.modules.wood.api.types.type;

public class WoodTypeHandler {

	public static void init() {
		WoodTypes.ACACIA = new WoodType.Builder("acacia")
				.setBurnInfo(650f, 1000)
				.setColor(9745287)
				.build();

		WoodTypes.ALDER = new WoodType.Builder("alder")
				.setBurnInfo(601f, 1000)
				.setColor(12092755)
				.build();

		WoodTypes.APPLE = new WoodType.Builder("apple")
				.setBurnInfo(720f, 1000)
				.setColor(6305064)
				.build();

		WoodTypes.ASH = new WoodType.Builder("ash")
				.setBurnInfo(696f, 1250)
				.setColor(16107368)
				.build();

		WoodTypes.BANANA = new WoodType.Builder("banana")
				.setBurnInfo(720f, 1750)
				.setColor(9466691)
				.build();

		WoodTypes.BAOBAB = new WoodType.Builder("baobab")
				.setBurnInfo(478f, 1000)
				.setColor(9608290)
				.build();

		WoodTypes.BALSA = new WoodType.Builder("balsa")
				.setBurnInfo(783f, 1100)
				.setColor(11117209)
				.build();

		WoodTypes.DARK_OAK = new WoodType.Builder("dark_oak")
				.setBurnInfo(611f, 1000)
				.setColor(4599061)
				.build();

		WoodTypes.BIRCH = new WoodType.Builder("birch")
				.setBurnInfo(652f, 1750)
				.setColor(14139781)
				.setTannin()
				.build();

		WoodTypes.JUNGLE = new WoodType.Builder("jungle")
				.setBurnInfo(720f, 1750)
				.setColor(11632732)
				.build();

		WoodTypes.CHESTNUT = new WoodType.Builder("chestnut")
				.setBurnInfo(651f, 1500)
				.setColor(12298845)
				.setTannin()
				.build();

		WoodTypes.HICKORY = new WoodType.Builder("hickory")
				.setBurnInfo(762f, 2000)
				.setColor(14333070)
				.setTannin()
				.build();

		WoodTypes.MAPLE = new WoodType.Builder("maple")
				.setBurnInfo(745f, 2000)
				.setColor(11431211)
				.setTannin()
				.build();

		WoodTypes.OAK = new WoodType.Builder("oak")
				.setBurnInfo(728f, 2250)
				.setColor(11833434)
				.setTannin()
				.build();

		WoodTypes.PALM = new WoodType.Builder("palm")
				.setBurnInfo(730f, 1250)
				.setColor(13271115)
				.build();

		WoodTypes.PINE = new WoodType.Builder("pine")
				.setBurnInfo(627f, 1250)
				.setColor(12885585)
				.build();

		WoodTypes.ROSEWOOD = new WoodType.Builder("rosewood")
				.setBurnInfo(640f, 1500)
				.setColor(7738624)
				.build();

		WoodTypes.SEQUOIA = new WoodType.Builder("sequoia")
				.setBurnInfo(612f, 1750)
				.setColor(10050135)
				.setTannin()
				.build();

		WoodTypes.SPRUCE = new WoodType.Builder("spruce")
				.setBurnInfo(608f, 1500)
				.setColor(8412726)
				.build();

		WoodTypes.PAPAYA = new WoodType.Builder("papaya")
				.setBurnInfo(653f, 1750)
				.setColor(14470005)
				.build();

		WoodTypes.CITRUS = new WoodType.Builder("citrus")
				.setColor(10266653)
				.setBurnInfo(625f, 1500)
				.build();

		WoodTypes.WILLOW = new WoodType.Builder("willow")
				.setBurnInfo(603f, 1000)
				.setColor(11710818)
				.build();

		WoodTypes.KAPOK = new WoodType.Builder("kapok")
				.setBurnInfo(645f, 1000)
				.setColor(8156212)
				.build();

		WoodTypes.HEVEA = new WoodType.Builder("hevea")
				.setBurnInfo(762f, 2000)
				.setColor(0x8B3929)
				.build();

		WoodTypes.GIGANTEUM = new WoodType.Builder("giganteum")
				.setBurnInfo(745f, 1500)
				.setColor(5186590)
				.build();

		WoodTypes.BEECH = new WoodType.Builder("beech")
				.setBurnInfo(703f, 1750)
				.setColor(14784849)
				.setTannin()
				.build();

		WoodTypes.FIR = new WoodType.Builder("fir")
				.setBurnInfo(758f, 1800)
				.setColor(12815444)
				.build();

		WoodTypes.BOX = new WoodType.Builder("box")
				.setBurnInfo(683f, 1500)
				.setColor(16511430)
				.build();

		WoodTypes.BRAZILWOOD = new WoodType.Builder("brazilwood")
				.setBurnInfo(710f, 1000)
				.setColor(7487063)
				.build();

		WoodTypes.BUTTERNUT = new WoodType.Builder("butternut")
				.setBurnInfo(758f, 1800)
				.setColor(15510138)
				.build();

		WoodTypes.COCOBOLO = new WoodType.Builder("cocobolo")
				.setBurnInfo(773f, 1000)
				.setColor(7541506)
				.build();

		WoodTypes.CYPRESS = new WoodType.Builder("cypress")
				.setBurnInfo(783f, 1100)
				.setColor(16169052)
				.build();

		WoodTypes.EBONY = new WoodType.Builder("ebony")
				.setBurnInfo(795f, 1000)
				.setColor(3946288)
				.build();

		WoodTypes.EUCALYPTUS = new WoodType.Builder("eucalyptus")
				.setBurnInfo(705f, 1000)
				.setColor(16165771)
				.build();

		WoodTypes.CEDAR = new WoodType.Builder("cedar")
				.setBurnInfo(728f, 2250)
				.setTannin()
				.setColor(14181940)
				.build();

		WoodTypes.FIG = new WoodType.Builder("fig")
				.setBurnInfo(590f, 1000)
				.setColor(13142058)
				.build();


		WoodTypes.ELM = new WoodType.Builder("elm")
				.setBurnInfo(710f, 1000)
				.setColor(15772004)
				.build();

		WoodTypes.GREENHEART = new WoodType.Builder("greenheart")
				.setBurnInfo(793f, 1700)
				.setColor(5144156)
				.build();

		WoodTypes.HAWTHORN = new WoodType.Builder("hawthorn")
				.setBurnInfo(683f, 1500)
				.setColor(13402978)
				.build();

		WoodTypes.HAZEL = new WoodType.Builder("hazel")
				.setBurnInfo(683f, 1500)
				.setColor(13480341)
				.build();

		WoodTypes.HEMLOCK = new WoodType.Builder("hemlock")
				.setBurnInfo(609f, 1000)
				.setColor(13088108)
				.build();

		WoodTypes.HOLLY = new WoodType.Builder("holly")
				.setBurnInfo(609f, 1000)
				.setColor(16512743)
				.build();

		WoodTypes.HORNBEAM = new WoodType.Builder("hornbeam")
				.setBurnInfo(728f, 2250)
				.setColor(12818528)
				.setTannin()
				.build();

		WoodTypes.IPE = new WoodType.Builder("ipe")
				.setBurnInfo(785f, 1200)
				.setColor(5057822)
				.build();

		WoodTypes.IROKO = new WoodType.Builder("iroko")
				.setBurnInfo(785f, 1200)
				.setColor(7681024)
				.build();

		WoodTypes.PEAR = new WoodType.Builder("pear")
				.setBurnInfo(694f, 1170)
				.setColor(12093805)
				.build();

		WoodTypes.PADAUK = new WoodType.Builder("padauk")
				.setBurnInfo(795f, 1250)
				.setColor(11756341)
				.build();

		WoodTypes.ELDER = new WoodType.Builder("elder")
				.setBurnInfo(632f, 1750)
				.setColor(12489337)
				.setTannin()
				.build();

		WoodTypes.SYZGIUM = new WoodType.Builder("syzgium")
				.setBurnInfo(730f, 1250)
				.setColor(15123393)
				.build();

		WoodTypes.LARCH = new WoodType.Builder("larch")
				.setBurnInfo(632f, 1250)
				.setColor(14131085)
				.build();

		WoodTypes.LIME = new WoodType.Builder("lime")
				.setBurnInfo(710f, 1000)
				.setColor(13544048)
				.build();

		WoodTypes.LOCUST = new WoodType.Builder("locust")
				.setBurnInfo(653f, 1750)
				.setColor(12816736)
				.build();

		WoodTypes.LOGWOOD = new WoodType.Builder("logwood")
				.setBurnInfo(695f, 1000)
				.setColor(10762028)
				.build();

		WoodTypes.MACLURA = new WoodType.Builder("maclura")
				.setBurnInfo(773f, 1000)
				.setColor(15970862)
				.build();

		WoodTypes.MAHOE = new WoodType.Builder("mahoe")
				.setBurnInfo(783f, 1100)
				.setColor(8362154)
				.build();

		WoodTypes.MAHOGANY = new WoodType.Builder("mahogany")
				.setBurnInfo(773f, 1000)
				.setColor(7749432)
				.build();

		WoodTypes.PINKIVORY = new WoodType.Builder("pinkivory")
				.setBurnInfo(773f, 1000)
				.setColor(15502496)
				.build();

		WoodTypes.POPLAR = new WoodType.Builder("poplar")
				.setBurnInfo(609f, 1000)
				.setColor(13619074)
				.build();

		WoodTypes.PURPLEHEART = new WoodType.Builder("purpleheart")
				.setBurnInfo(793f, 1700)
				.setColor(5970991)
				.build();

		WoodTypes.GINGKO = new WoodType.Builder("gingko")
				.setBurnInfo(618f, 1750)
				.setColor(16050106)
				.setTannin()
				.build();

		WoodTypes.ROWAN = new WoodType.Builder("rowan")
				.setBurnInfo(645f, 2000)
				.setColor(13610394)
				.build();

		WoodTypes.SWEETGUM = new WoodType.Builder("sweetgum")
				.setBurnInfo(745f, 2000)
				.setColor(13997656)
				.setTannin()
				.build();

		WoodTypes.TEAK = new WoodType.Builder("teak")
				.setBurnInfo(695f, 1000)
				.setColor(8223075)
				.build();

		WoodTypes.WALNUT = new WoodType.Builder("walnut")
				.setBurnInfo(758f, 1800)
				.setColor(6836802)
				.build();

		WoodTypes.WENGE = new WoodType.Builder("wenge")
				.setBurnInfo(773f, 1250)
				.setColor(6182474)
				.build();

		WoodTypes.WHITEBEAM = new WoodType.Builder("whitebeam")
				.setBurnInfo(728f, 1750)
				.setColor(13222585)
				.setTannin()
				.build();

		WoodTypes.YEW = new WoodType.Builder("yew")
				.setBurnInfo(813f, 2150)
				.setColor(14722426)
				.build();

		WoodTypes.ZEBRAWOOD = new WoodType.Builder("zebrawood")
				.setBurnInfo(822f, 1570)
				.setColor(10912334)
				.build();

		WoodTypes.CHERRY = new WoodType.Builder("cherry")
				.setBurnInfo(720f, 1750)
				.setColor(11895348)
				.build();

		WoodTypes.OLIVE = new WoodType.Builder("olive")
				.setBurnInfo(720f, 1750)
				.setColor(11578760)
				.build();

		WoodTypes.PLUM = new WoodType.Builder("plum")
				.setBurnInfo(720f, 1750)
				.setColor(11364479)
				.build();
	}
}
