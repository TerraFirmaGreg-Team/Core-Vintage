package su.terrafirmagreg.modules.wood.api.types.type;

import net.dries007.tfc.objects.items.ItemsTFCF;
import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.agriculture.Food;

import static net.dries007.tfc.types.DefaultTrees.GEN_ACACIA;
import static net.dries007.tfc.types.DefaultTrees.GEN_AFRICAN_PADAUK;
import static net.dries007.tfc.types.DefaultTrees.GEN_ALDER;
import static net.dries007.tfc.types.DefaultTrees.GEN_ANGELIM;
import static net.dries007.tfc.types.DefaultTrees.GEN_ASPEN;
import static net.dries007.tfc.types.DefaultTrees.GEN_BALD_CYPRESS;
import static net.dries007.tfc.types.DefaultTrees.GEN_BAOBAB;
import static net.dries007.tfc.types.DefaultTrees.GEN_BEECH;
import static net.dries007.tfc.types.DefaultTrees.GEN_BLACK_WALNUT;
import static net.dries007.tfc.types.DefaultTrees.GEN_BRAZILWOOD;
import static net.dries007.tfc.types.DefaultTrees.GEN_BUTTERNUT;
import static net.dries007.tfc.types.DefaultTrees.GEN_COCOBOLO;
import static net.dries007.tfc.types.DefaultTrees.GEN_CONIFER;
import static net.dries007.tfc.types.DefaultTrees.GEN_CYPRESS;
import static net.dries007.tfc.types.DefaultTrees.GEN_EBONY;
import static net.dries007.tfc.types.DefaultTrees.GEN_EUROPEAN_OAK;
import static net.dries007.tfc.types.DefaultTrees.GEN_FEVER;
import static net.dries007.tfc.types.DefaultTrees.GEN_FRUITWOOD;
import static net.dries007.tfc.types.DefaultTrees.GEN_GINKGO;
import static net.dries007.tfc.types.DefaultTrees.GEN_GREENHEART;
import static net.dries007.tfc.types.DefaultTrees.GEN_HAWTHORN;
import static net.dries007.tfc.types.DefaultTrees.GEN_HAZEL;
import static net.dries007.tfc.types.DefaultTrees.GEN_HEMLOCK;
import static net.dries007.tfc.types.DefaultTrees.GEN_HOLLY;
import static net.dries007.tfc.types.DefaultTrees.GEN_HORNBEAM;
import static net.dries007.tfc.types.DefaultTrees.GEN_IPE;
import static net.dries007.tfc.types.DefaultTrees.GEN_IROKO;
import static net.dries007.tfc.types.DefaultTrees.GEN_IRONWOOD;
import static net.dries007.tfc.types.DefaultTrees.GEN_JACARANDA;
import static net.dries007.tfc.types.DefaultTrees.GEN_JUNIPER;
import static net.dries007.tfc.types.DefaultTrees.GEN_KAPOK_COMPOSITE;
import static net.dries007.tfc.types.DefaultTrees.GEN_KAURI;
import static net.dries007.tfc.types.DefaultTrees.GEN_LARCH;
import static net.dries007.tfc.types.DefaultTrees.GEN_LIMBA;
import static net.dries007.tfc.types.DefaultTrees.GEN_LOCUST;
import static net.dries007.tfc.types.DefaultTrees.GEN_LOGWOOD;
import static net.dries007.tfc.types.DefaultTrees.GEN_MACLURA;
import static net.dries007.tfc.types.DefaultTrees.GEN_MAHOE;
import static net.dries007.tfc.types.DefaultTrees.GEN_MAHOGANY;
import static net.dries007.tfc.types.DefaultTrees.GEN_MANGROVE;
import static net.dries007.tfc.types.DefaultTrees.GEN_MARBLEWOOD;
import static net.dries007.tfc.types.DefaultTrees.GEN_MEDIUM;
import static net.dries007.tfc.types.DefaultTrees.GEN_MEDIUM_2;
import static net.dries007.tfc.types.DefaultTrees.GEN_MESSMATE;
import static net.dries007.tfc.types.DefaultTrees.GEN_MOUNTAIN_ASH;
import static net.dries007.tfc.types.DefaultTrees.GEN_NORDMANN_FIR;
import static net.dries007.tfc.types.DefaultTrees.GEN_NORMAL;
import static net.dries007.tfc.types.DefaultTrees.GEN_NORMAL_2;
import static net.dries007.tfc.types.DefaultTrees.GEN_NORWAY_SPRUCE;
import static net.dries007.tfc.types.DefaultTrees.GEN_PINK_CHERRY;
import static net.dries007.tfc.types.DefaultTrees.GEN_PINK_IVORY;
import static net.dries007.tfc.types.DefaultTrees.GEN_POPLAR;
import static net.dries007.tfc.types.DefaultTrees.GEN_PURPLEHEART;
import static net.dries007.tfc.types.DefaultTrees.GEN_REDWOOD;
import static net.dries007.tfc.types.DefaultTrees.GEN_RED_CEDAR;
import static net.dries007.tfc.types.DefaultTrees.GEN_RED_ELM;
import static net.dries007.tfc.types.DefaultTrees.GEN_ROWAN;
import static net.dries007.tfc.types.DefaultTrees.GEN_RUBBER_FIG;
import static net.dries007.tfc.types.DefaultTrees.GEN_SEQUOIA;
import static net.dries007.tfc.types.DefaultTrees.GEN_SWEETGUM;
import static net.dries007.tfc.types.DefaultTrees.GEN_SYZYGIUM;
import static net.dries007.tfc.types.DefaultTrees.GEN_TALL;
import static net.dries007.tfc.types.DefaultTrees.GEN_TALL_2;
import static net.dries007.tfc.types.DefaultTrees.GEN_TALL_SINGLE;
import static net.dries007.tfc.types.DefaultTrees.GEN_TALL_TFC;
import static net.dries007.tfc.types.DefaultTrees.GEN_TEAK;
import static net.dries007.tfc.types.DefaultTrees.GEN_TROPICAL;
import static net.dries007.tfc.types.DefaultTrees.GEN_WALNUT;
import static net.dries007.tfc.types.DefaultTrees.GEN_WENGE;
import static net.dries007.tfc.types.DefaultTrees.GEN_WHITEBEAM;
import static net.dries007.tfc.types.DefaultTrees.GEN_WHITE_CHERRY;
import static net.dries007.tfc.types.DefaultTrees.GEN_WHITE_ELM;
import static net.dries007.tfc.types.DefaultTrees.GEN_WILLOW;
import static net.dries007.tfc.types.DefaultTrees.GEN_YELLOW_MERANTI;
import static net.dries007.tfc.types.DefaultTrees.GEN_YEW;
import static net.dries007.tfc.types.DefaultTrees.GEN_ZEBRAWOOD;
import static su.terrafirmagreg.api.util.ColourUtils.rgb;

public class WoodTypeHandler {

  public static void init() {

    WoodTypes.ACACIA = WoodType
      .builder("acacia")
      .color(rgb(134, 51, 0))
      .burnInfo(650f, 1000)
      .rainInfo(30f, 210f)
      .tempInfo(19f, 31f)
      .height(12)
      .minGrowthTime(11)
      .density(0.1f, 0.6f)
      .generator(GEN_ACACIA)
      .paramMap(0.10f, 14f, 6, 6, 0.90f)
      .cellKit("acacia")
      .build();

    WoodTypes.ASH = WoodType
      .builder("ash")
      .color(rgb(183, 110, 93))
      .burnInfo(696f, 1250)
      .rainInfo(60f, 140f)
      .tempInfo(-6f, 12f)
      .bushes()
      .generator(GEN_NORMAL_2)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.25f, 12f, 4, 3, 1.00f)
      .cellKit("deciduous")
      .build();

    WoodTypes.ASPEN = WoodType
      .builder("aspen")
      .color(rgb(82, 82, 54))
      .burnInfo(611f, 1000)
      .rainInfo(10f, 80f)
      .tempInfo(-10f, 16f)
      .minGrowthTime(8)
      .density(0.25f, 1f)
      .generator(GEN_ASPEN)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 8, 2, 1.50f)
      .growthLogicKit("conifer")
      .cellKit("deciduous")
      .build();

    WoodTypes.BIRCH = WoodType
      .builder("birch")
      .color(rgb(119, 102, 69))
      .burnInfo(652f, 1750)
      .isCanMakeTannin()
      .rainInfo(20f, 180f)
      .tempInfo(-15f, 7f)
      .radius(1)
      .generator(GEN_TALL_2)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.25f, 12f, 5, 5, 1.15f)
      .cellKit("deciduous")
      .build();

    WoodTypes.BLACKWOOD = WoodType
      .builder("blackwood")
      .color(rgb(45, 42, 40))
      .burnInfo(720f, 1750)
      .rainInfo(0f, 120f)
      .tempInfo(4f, 33f)
      .height(12)
      .minGrowthTime(8)
      .generator(GEN_MEDIUM)
      .paramMap(0.20f, 13f, 3, 4, 0.90f)
      .growthLogicKit("darkoak")
      .cellKit("darkoak")
      .build();

    WoodTypes.CHESTNUT = WoodType
      .builder("chestnut")
      .color(rgb(118, 60, 28))
      .burnInfo(651f, 1500)
      .isCanMakeTannin()
      .rainInfo(160f, 320f)
      .tempInfo(11f, 35f)
      .bushes()
      .generator(GEN_NORMAL)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.20f, 10f, 3, 3, 1.00f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.DOUGLAS_FIR = WoodType
      .builder("douglas_fir")
      .color(rgb(205, 160, 105))
      .burnInfo(707f, 1500)
      .isCanMakeTannin()
      .rainInfo(280f, 480f)
      .tempInfo(-2f, 14f)
      .dominance(5.2f)
      .height(16)
      .density(0.25f, 2f)
      .generator(GEN_TALL)
      .paramMap(0.15f, 20f, 5, 3, 1.15f)
      .growthLogicKit("conifer")
      .cellKit("conifer")
      .bushes()
      .isConifer()
      .build();

    WoodTypes.HICKORY = WoodType
      .builder("hickory")
      .color(rgb(76, 46, 17))
      .burnInfo(762f, 2000)
      .isCanMakeTannin()
      .rainInfo(80f, 250f)
      .tempInfo(7f, 29f)
      .minGrowthTime(10)
      .bushes()
      .generator(GEN_TALL_2)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.20f, 14f, 5, 3, 0.80f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.KAPOK = WoodType
      .builder("kapok")
      .color(rgb(188, 151, 180))
      .burnInfo(645f, 1000)
      .rainInfo(210f, 500f)
      .tempInfo(15f, 35f)
      .dominance(8.5f)
      .radius(3)
      .height(24)
      .decayDist(6)
      .minGrowthTime(18)
      .density(0.6f, 2f)
      .generator(GEN_KAPOK_COMPOSITE)
      .paramMap(0.10f, 30f, 7, 4, 0.85f)
      .growthLogicKit("jungle")
      .cellKit("deciduous")
      .bushes()
      .isThick()
      .build();

    WoodTypes.MAPLE = WoodType
      .builder("maple")
      .color(rgb(205, 140, 73))
      .burnInfo(745f, 2000)
      .isCanMakeTannin()
      .rainInfo(140f, 360f)
      .tempInfo(3f, 20f)
      .dominance(6.3f)
      .radius(1)
      .generator(GEN_MEDIUM_2)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.15f, 15f, 6, 3, 0.95f)
      .cellKit("deciduous")
      .build();

    WoodTypes.OAK = WoodType
      .builder("oak")
      .color(rgb(180, 145, 91))
      .burnInfo(728f, 2250)
      .isCanMakeTannin()
      .rainInfo(180f, 430f)
      .tempInfo(-8f, 12f)
      .height(16)
      .minGrowthTime(10)
      .bushes()
      .generator(GEN_TALL_2)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.PALM = WoodType
      .builder("palm")
      .color(rgb(198, 125, 81))
      .burnInfo(730f, 1250)
      .rainInfo(280f, 500f)
      .tempInfo(16f, 35f)
      .decayDist(6)
      .generator(GEN_TROPICAL)
      .paramMap(0.05f, 16f, 5, 4, 1.10f)
      .growthLogicKit("jungle")
      .cellKit("palm")
      .build();

    WoodTypes.PINE = WoodType
      .builder("pine")
      .color(rgb(221, 206, 167))
      .burnInfo(627f, 1250)
      .rainInfo(60f, 250f)
      .tempInfo(-15f, 7f)
      .density(0.1f, 0.8f)
      .radius(1)
      .generator(GEN_CONIFER)
      .paramMap(0.20f, 18f, 6, 2, 1.20f)
      .growthLogicKit("conifer")
      .cellKit("conifer")
      .isConifer()
      .build();

    WoodTypes.ROSEWOOD = WoodType
      .builder("rosewood")
      .color(rgb(126, 23, 3))
      .burnInfo(640f, 1500)
      .rainInfo(10f, 190f)
      .tempInfo(8f, 18f)
      .height(12)
      .minGrowthTime(8)
      .generator(GEN_MEDIUM)
      .paramMap(0.35f, 15f, 7, 3, 1.00f)
      .cellKit("deciduous")
      .build();

    WoodTypes.SEQUOIA = WoodType
      .builder("sequoia")
      .color(rgb(227, 197, 137))
      .burnInfo(612f, 1750)
      .isCanMakeTannin()
      .rainInfo(250f, 420f)
      .tempInfo(-5f, 12f)
      .radius(3)
      .height(24)
      .decayDist(6)
      .minGrowthTime(18)
      .density(0.4f, 0.9f)
      .generator(GEN_SEQUOIA)
      .paramMap(0.20f, 36f, 9, 4, 0.70f)
      .growthLogicKit("conifer")
      .cellKit("conifer")
      .isConifer()
      .bushes()
      .isThick()
      .build();

    WoodTypes.SPRUCE = WoodType
      .builder("spruce")
      .color(rgb(187, 137, 120))
      .burnInfo(608f, 1500)
      .rainInfo(120f, 380f)
      .tempInfo(-11f, 6f)
      .density(0.1f, 0.8f)
      .radius(1)
      .generator(GEN_CONIFER)
      .paramMap(0.15f, 12f, 6, 3, 1.10f)
      .growthLogicKit("conifer")
      .cellKit("conifer")
      .isConifer()
      .build();

    WoodTypes.SYCAMORE = WoodType
      .builder("sycamore")
      .color(rgb(223, 172, 89))
      .burnInfo(653f, 1750)
      .rainInfo(120f, 290f)
      .tempInfo(17f, 33f)
      .minGrowthTime(8)
      .density(0.25f, 2f)
      .generator(GEN_MEDIUM_2)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.20f, 10f, 4, 3, 0.90f)
      .cellKit("deciduous")
      .bushes()
      .build();

    WoodTypes.WHITE_CEDAR = WoodType
      .builder("white_cedar")
      .color(rgb(222, 219, 215))
      .burnInfo(625f, 1500)
      .rainInfo(10f, 240f)
      .tempInfo(-8f, 17f)
      .height(16)
      .bushes()
      .generator(GEN_TALL_SINGLE)
      .fruit(() -> ItemsTFCF.JUNIPER, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.15f, 20f, 6, 2, 1.10f)
      .growthLogicKit("conifer")
      .cellKit("deciduous")
      .isConifer()
      .build();

    WoodTypes.WILLOW = WoodType
      .builder("willow")
      .color(rgb(40, 40, 8))
      .burnInfo(603f, 1000)
      .rainInfo(230f, 400f)
      .tempInfo(15f, 32f)
      .minGrowthTime(11)
      .density(0.7f, 2f)
      .radius(1)
      .generator(GEN_WILLOW)
      .paramMap(0.55f, 15f, 2, 2, 1.40f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .bushes()
      .build();

    // TFC TECH
    WoodTypes.HEVEA = WoodType
      .builder("hevea")
      .color(rgb(179, 113, 64))
      .burnInfo(762f, 2000)
      .rainInfo(140f, 350f)
      .tempInfo(7f, 27f)
      .density(0.1f, 0.6f)
      .radius(2)
      .minGrowthTime(10)
      .generator(GEN_TALL)
      .paramMap(0.20f, 13f, 3, 7, 1.25f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    // TFCFlorae
    WoodTypes.AFRICAN_PADAUK = WoodType
      .builder("african_padauk")
      .color(rgb(121, 37, 27))
      .burnInfo(745f, 1500)
      .rainInfo(275f, 500f)
      .tempInfo(22f, 50f)
      .density(0.5f, 2f)
      .minGrowthTime(18)
      .decayDist(6)
      .radius(1)
      .bushes()
      .generator(GEN_AFRICAN_PADAUK)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.ALDER = WoodType
      .builder("alder")
      .color(rgb(166, 118, 71))
      .burnInfo(601f, 1000)
      .rainInfo(60f, 400f)
      .tempInfo(-4f, 13f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 2f)
      .generator(GEN_ALDER)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.ANGELIM = WoodType
      .builder("angelim")
      .color(rgb(158, 109, 59))
      .burnInfo(773f, 1200)
      .rainInfo(320f, 500f)
      .tempInfo(22f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .density(0.5f, 2f)
      .bushes()
      .generator(GEN_ANGELIM)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.EUCALYPTUS = WoodType
      .builder("eucalyptus")
      .color(rgb(243, 159, 112))
      .burnInfo(773f, 1200)
      .rainInfo(320f, 500f)
      .tempInfo(22f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .density(0.5f, 2f)
      .bushes()
      .generator(GEN_ANGELIM)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.EUROPEAN_OAK = WoodType
      .builder("european_oak")
      .color(rgb(196, 161, 133))
      .burnInfo(728f, 2250)
      .isCanMakeTannin()
      .rainInfo(140f, 430f)
      .tempInfo(-8f, 15f)
      .minGrowthTime(10)
      .bushes()
      .generator(GEN_EUROPEAN_OAK)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.BALD_CYPRESS = WoodType
      .builder("bald_cypress") //TODO
      .color(rgb(198, 152, 97))
      .burnInfo(770f, 1300)
      .rainInfo(180f, 500f)
      .tempInfo(10f, 38f)
      .dominance(0f)
      .density(0f, 0f)
      .minGrowthTime(8)
      .bushes()
      .isConifer()
      .generator(GEN_BALD_CYPRESS)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.BAOBAB = WoodType
      .builder("baobab")
      .color(rgb(197, 173, 123))
      .burnInfo(478f, 1000)
      .rainInfo(10f, 150f)
      .tempInfo(23f, 40f)
      .decayDist(6)
      .minGrowthTime(20)
      .density(0.1f, 0.3f)
      .generator(GEN_BAOBAB)
      .fruit(() -> ItemsTFCF.BAOBAB_FRUIT, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.BEECH = WoodType
      .builder("beech")
      .color(rgb(217, 172, 130))
      .burnInfo(703f, 1750)
      .isCanMakeTannin()
      .rainInfo(220f, 300f)
      .tempInfo(-15f, 9f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 1f)
      .generator(GEN_BEECH)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.BLACK_WALNUT = WoodType
      .builder("black_walnut")
      .color(rgb(102, 74, 41))
      .burnInfo(758f, 1800)
      .rainInfo(180f, 300f)
      .tempInfo(-10f, 16f)
      .minGrowthTime(9)
      .bushes()
      .generator(GEN_BLACK_WALNUT)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.BOX = WoodType
      .builder("box")
      .color(rgb(102, 74, 41))
      .burnInfo(683f, 1500)
      .rainInfo(180f, 400f)
      .tempInfo(-8f, 15f)
      .minGrowthTime(9)
      .density(0.25f, 1f)
      .bushes()
      .generator(GEN_TALL_TFC)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.BRAZILWOOD = WoodType
      .builder("brazilwood")
      .color(rgb(107, 38, 74))
      .burnInfo(710f, 1000)
      .rainInfo(290f, 550f)
      .tempInfo(14f, 37f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 1f)
      .generator(GEN_BRAZILWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.BUTTERNUT = WoodType
      .builder("butternut")
      .color(rgb(209, 161, 116))
      .burnInfo(758f, 1800)
      .rainInfo(180f, 320f)
      .tempInfo(-8f, 17f)
      .minGrowthTime(9)
      .bushes()
      .generator(GEN_BUTTERNUT)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.COCOBOLO = WoodType
      .builder("cocobolo")
      .color(rgb(76, 27, 22))
      .burnInfo(773f, 1000)
      .rainInfo(255f, 500f)
      .tempInfo(20f, 50f)
      .radius(1)
      .minGrowthTime(8)
      .bushes()
      .density(0.5f, 2f)
      .generator(GEN_COCOBOLO)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.CYPRESS = WoodType
      .builder("cypress")
      .color(rgb(179, 184, 141))
      .burnInfo(783f, 1100)
      .rainInfo(140f, 350f)
      .tempInfo(4f, 33f)
      .minGrowthTime(8)
      .bushes()
      .isConifer()
      .generator(GEN_CYPRESS)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.EBONY = WoodType
      .builder("ebony")
      .color(rgb(36, 30, 41))
      .burnInfo(795f, 1000)
      .rainInfo(180f, 320f)
      .tempInfo(19f, 38f)
      .minGrowthTime(8)
      .bushes()
      .generator(GEN_EBONY)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.FEVER = WoodType
      .builder("fever")
      .color(rgb(225, 194, 137))
      .burnInfo(590f, 1000)
      .rainInfo(70f, 220f)
      .tempInfo(19f, 50f)
      .minGrowthTime(10)
      .bushes()
      .density(0.25f, 1f)
      .generator(GEN_FEVER)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.GINKGO = WoodType
      .builder("ginkgo")
      .color(rgb(222, 215, 138))
      .burnInfo(710f, 1000)
      .rainInfo(240f, 550f)
      .tempInfo(6f, 20f)
      .minGrowthTime(8)
      .density(0.25f, 1f)
      .generator(GEN_GINKGO)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.GREENHEART = WoodType
      .builder("greenheart")
      .color(rgb(70, 105, 79))
      .burnInfo(793f, 1700)
      .rainInfo(310f, 500f)
      .tempInfo(23f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.5f, 2f)
      .generator(GEN_GREENHEART)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.HAWTHORN = WoodType
      .builder("hawthorn")
      .color(rgb(212, 128, 73))
      .burnInfo(683f, 1500)
      .rainInfo(180f, 400f)
      .tempInfo(-8f, 14f)
      .minGrowthTime(8)
      .density(0.25f, 1f)
      .generator(GEN_HAWTHORN)
      .fruit(() -> ItemsTFCF.HAWTHORN, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.HAZEL = WoodType
      .builder("hazel")
      .color(rgb(226, 179, 142))
      .burnInfo(683f, 1500)
      .rainInfo(60f, 400f)
      .tempInfo(-10f, 14f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 1f)
      .generator(GEN_HAZEL)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.HEMLOCK = WoodType
      .builder("hemlock")
      .color(rgb(172, 164, 115))
      .burnInfo(609f, 1000)
      .rainInfo(140f, 400f)
      .tempInfo(-9f, 10f)
      .minGrowthTime(8)
      .isConifer()
      .density(0.25f, 1f)
      .generator(GEN_HEMLOCK)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.HOLLY = WoodType
      .builder("holly")
      .color(rgb(250, 242, 223))
      .burnInfo(609f, 1000)
      .rainInfo(140f, 400f)
      .tempInfo(-4f, 16f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 1f)
      .generator(GEN_HOLLY)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.HORNBEAM = WoodType
      .builder("hornbeam")
      .color(rgb(187, 143, 64))
      .burnInfo(728f, 2250)
      .isCanMakeTannin()
      .rainInfo(140f, 430f)
      .tempInfo(-10f, 12f)
      .minGrowthTime(10)
      .bushes()
      .generator(GEN_HORNBEAM)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.IROKO = WoodType
      .builder("iroko")
      .color(rgb(125, 39, 0))
      .burnInfo(785f, 1200)
      .rainInfo(300f, 500f)
      .tempInfo(21f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.5f, 2f)
      .generator(GEN_IROKO)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.IRONWOOD = WoodType
      .builder("ironwood")
      .color(rgb(153, 127, 97))
      .burnInfo(694f, 1170)
      .rainInfo(30f, 210f)
      .tempInfo(11f, 36f)
      .decayDist(6)
      .minGrowthTime(11)
      .bushes()
      .density(0.1f, 0.6f)
      .generator(GEN_IRONWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.JOSHUA = WoodType
      .builder("joshua") //TODO
      .color(rgb(142, 114, 108))
      .burnInfo(694f, 1170)
      .rainInfo(30f, 210f)
      .tempInfo(11f, 36f)
      .decayDist(6)
      .minGrowthTime(11)
      .bushes()
      .density(0.1f, 0.6f)
      .generator(GEN_IRONWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.JUNIPER = WoodType
      .builder("juniper")
      .color(rgb(167, 154, 130))
      .burnInfo(632f, 1750)
      .isCanMakeTannin()
      .rainInfo(80f, 350f)
      .tempInfo(-8f, 20f)
      .minGrowthTime(8)
      .isConifer()
      .density(0.25f, 0.75f)
      .generator(GEN_JUNIPER)
      .fruit(() -> ItemsTFCF.JUNIPER, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.KAURI = WoodType
      .builder("kauri")
      .color(rgb(162, 102, 54))
      .burnInfo(730f, 1250)
      .rainInfo(330f, 500f)
      .tempInfo(23f, 50f)
      .radius(1)
      .minGrowthTime(10)
      .bushes()
      .density(0.5f, 2f)
      .generator(GEN_KAURI)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.LARCH = WoodType
      .builder("larch") //TODO
      .color(rgb(175, 140, 128))
      .burnInfo(632f, 1250)
      .rainInfo(60f, 400f)
      .tempInfo(-12f, 15f)
      .minGrowthTime(8)
      .isConifer()
      .density(0.25f, 1f)
      .generator(GEN_LARCH)
      .fruit(() -> ItemsTFCF.PINECONE, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.LIMBA = WoodType
      .builder("limba")
      .color(rgb(245, 198, 95))
      .burnInfo(710f, 1000)
      .rainInfo(290f, 550f)
      .tempInfo(14f, 37f)
      .minGrowthTime(9)
      .bushes()
      .density(0.25f, 1f)
      .generator(GEN_LIMBA)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.LOCUST = WoodType
      .builder("locust")
      .color(rgb(189, 137, 94))
      .burnInfo(653f, 1750)
      .rainInfo(120f, 290f)
      .tempInfo(-6f, 15f)
      .minGrowthTime(8)
      .bushes()
      .generator(GEN_LOCUST)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.LOGWOOD = WoodType
      .builder("logwood")
      .color(rgb(146, 43, 33))
      .burnInfo(695f, 1000)
      .rainInfo(180f, 430f)
      .tempInfo(12f, 35f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 1f)
      .generator(GEN_LOGWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.MACLURA = WoodType
      .builder("maclura")
      .color(rgb(221, 155, 21))
      .burnInfo(773f, 1000)
      .rainInfo(140f, 400f)
      .tempInfo(-1f, 17f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 1f)
      .generator(GEN_MACLURA)
      .fruit(() -> ItemsTFCF.OSAGE_ORANGE, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.MAHOE = WoodType
      .builder("mahoe")
      .color(rgb(115, 124, 129))
      .burnInfo(783f, 1100)
      .rainInfo(180f, 350f)
      .tempInfo(13f, 32f)
      .height(16)
      .minGrowthTime(8)
      .bushes()
      .generator(GEN_MAHOE)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.MAHOGANY = WoodType
      .builder("mahogany")
      .color(rgb(135, 105, 95))
      .burnInfo(773f, 1000)
      .rainInfo(220f, 500f)
      .tempInfo(23f, 42f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.5f, 2f)
      .generator(GEN_MAHOGANY)
      .fruit(() -> ItemsTFCF.SKY_FRUIT, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.MARBLEWOOD = WoodType
      .builder("marblewood")
      .color(rgb(164, 104, 55))
      .burnInfo(837f, 1200)
      .rainInfo(180f, 500f)
      .tempInfo(16f, 35f)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.2f, 2f)
      .generator(GEN_MARBLEWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.MESSMATE = WoodType
      .builder("messmate")
      .color(rgb(179, 166, 136))
      .burnInfo(696f, 1250)
      .rainInfo(120f, 270f)
      .tempInfo(2f, 27f)
      .minGrowthTime(10)
      .bushes()
      .density(0.2f, 2f)
      .generator(GEN_MESSMATE)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.MOUNTAIN_ASH = WoodType
      .builder("mountain_ash")
      .color(rgb(216, 175, 119))
      .burnInfo(696f, 1250)
      .rainInfo(80f, 270f)
      .tempInfo(9f, 33f)
      .minGrowthTime(10)
      .bushes()
      .density(0.4f, 2f)
      .generator(GEN_MOUNTAIN_ASH)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.NORDMANN_FIR = WoodType
      .builder("nordmann_fir")
      .color(rgb(97, 75, 46))
      .burnInfo(628f, 1500)
      .rainInfo(100f, 380f)
      .tempInfo(-16f, 7f)
      .minGrowthTime(8)
      .isConifer()
      .density(0.1f, 0.9f)
      .generator(GEN_NORDMANN_FIR)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.NORWAY_SPRUCE = WoodType
      .builder("norway_spruce")
      .color(rgb(235, 204, 175))
      .burnInfo(628f, 1500)
      .rainInfo(100f, 380f)
      .tempInfo(-20f, 5f)
      .minGrowthTime(8)
      .isConifer()
      .density(0.1f, 0.9f)
      .generator(GEN_NORWAY_SPRUCE)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.PINK_CHERRY = WoodType
      .builder("pink_cherry")
      .color(rgb(225, 167, 160))
      .burnInfo(795f, 1250)
      .rainInfo(180f, 300f)
      .tempInfo(0f, 20f)
      .minGrowthTime(8)
      .density(0.25f, 2f)
      .generator(GEN_PINK_CHERRY)
      .fruit(() -> ItemFoodTFC.get(Food.CHERRY), 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.IPE = WoodType
      .builder("ipe")
      .color(rgb(63, 39, 27))
      .burnInfo(785f, 1200)
      .rainInfo(150f, 350f)
      .tempInfo(15f, 32f)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.2f, 2f)
      .generator(GEN_IPE)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.PINK_IVORY = WoodType
      .builder("pink_ivory")
      .color(rgb(253, 103, 132))
      .burnInfo(773f, 1000)
      .rainInfo(210f, 500f)
      .tempInfo(18f, 31f)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.2f, 2f)
      .generator(GEN_PINK_IVORY)
      .fruit(() -> ItemsTFCF.PINK_IVORY_DRUPE, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.POPLAR = WoodType
      .builder("poplar")
      .color(rgb(221, 209, 172))
      .burnInfo(609f, 1000)
      .rainInfo(140f, 400f)
      .tempInfo(-7f, 14f)
      .minGrowthTime(8)
      .density(0.25f, 1f)
      .generator(GEN_POPLAR)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.PURPLEHEART = WoodType
      .builder("purpleheart")
      .color(rgb(89, 5, 53))
      .burnInfo(793f, 1700)
      .rainInfo(310f, 500f)
      .tempInfo(22f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.5f, 2f)
      .generator(GEN_PURPLEHEART)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.JACARANDA = WoodType
      .builder("jacaranda")
      .color(rgb(142, 110, 91))
      .burnInfo(795f, 1250)
      .rainInfo(180f, 300f)
      .tempInfo(10f, 34f)
      .minGrowthTime(8)
      .density(0.25f, 2f)
      .generator(GEN_JACARANDA)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.REDWOOD = WoodType
      .builder("redwood")
      .color(rgb(141, 73, 41))
      .burnInfo(618f, 1750)
      .isCanMakeTannin()
      .rainInfo(160f, 400f)
      .tempInfo(0f, 17f)
      .decayDist(6)
      .minGrowthTime(18)
      .isConifer()
      .bushes()
      .density(0.4f, 2f)
      .generator(GEN_REDWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.RED_CEDAR = WoodType
      .builder("red_cedar")
      .color(rgb(162, 115, 101))
      .burnInfo(618f, 1750)
      .isCanMakeTannin()
      .rainInfo(10f, 240f)
      .tempInfo(-8f, 17f)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .isConifer()
      .bushes()
      .density(0.4f, 2f)
      .generator(GEN_RED_CEDAR)
      .fruit(() -> ItemsTFCF.JUNIPER, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.RED_ELM = WoodType
      .builder("red_elm")
      .color(rgb(224, 159, 105))
      .burnInfo(618f, 1750)
      .isCanMakeTannin()
      .rainInfo(60f, 290f)
      .tempInfo(2f, 20f)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.4f, 2f)
      .generator(GEN_RED_ELM)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.ROWAN = WoodType
      .builder("rowan")
      .color(rgb(173, 147, 145))
      .burnInfo(645f, 2000)
      .rainInfo(180f, 400f)
      .tempInfo(-15f, 8f)
      .minGrowthTime(8)
      .density(0.25f, 1f)
      .generator(GEN_ROWAN)
      .fruit(() -> ItemsTFCF.ROWAN_BERRY, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.RUBBER_FIG = WoodType
      .builder("rubber_fig")
      .color(rgb(200, 119, 19))
      .burnInfo(785f, 1440)
      .rainInfo(210f, 550f)
      .tempInfo(16f, 35f)
      .decayDist(6)
      .minGrowthTime(16)
      .bushes()
      .density(0.2f, 1f)
      .generator(GEN_RUBBER_FIG)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.SWEETGUM = WoodType
      .builder("sweetgum")
      .color(rgb(195, 130, 67))
      .burnInfo(745f, 2000)
      .isCanMakeTannin()
      .rainInfo(140f, 360f)
      .tempInfo(-2f, 18f)
      .decayDist(6)
      .minGrowthTime(16)
      .bushes()
      .density(0.2f, 1f)
      .generator(GEN_SWEETGUM)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.SYZYGIUM = WoodType
      .builder("syzygium")
      .color(rgb(229, 182, 188))
      .burnInfo(745f, 2000)
      .isCanMakeTannin()
      .rainInfo(140f, 360f)
      .tempInfo(13f, 35f)
      .decayDist(6)
      .minGrowthTime(16)
      .bushes()
      .density(0.2f, 1f)
      .generator(GEN_SYZYGIUM)
      .fruit(() -> ItemsTFCF.RIBERRY, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.TEAK = WoodType
      .builder("teak")
      .color(rgb(177, 128, 66))
      .burnInfo(695f, 1000)
      .rainInfo(180f, 430f)
      .tempInfo(17f, 35f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 1f)
      .generator(GEN_TEAK)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.WALNUT = WoodType
      .builder("walnut")
      .color(rgb(60, 44, 34))
      .burnInfo(758f, 1800)
      .rainInfo(180f, 300f)
      .tempInfo(-10f, 16f)
      .minGrowthTime(9)
      .bushes()
      .generator(GEN_WALNUT)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.WENGE = WoodType
      .builder("wenge")
      .color(rgb(88, 83, 77))
      .burnInfo(773f, 1250)
      .rainInfo(255f, 500f)
      .tempInfo(20f, 50f)
      .radius(1)
      .minGrowthTime(8)
      .bushes()
      .density(0.5f, 2f)
      .generator(GEN_WENGE)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.WHITEBEAM = WoodType
      .builder("whitebeam")
      .color(rgb(186, 177, 168))
      .burnInfo(728f, 1750)
      .isCanMakeTannin()
      .rainInfo(140f, 430f)
      .tempInfo(-10f, 12f)
      .minGrowthTime(10)
      .bushes()
      .generator(GEN_WHITEBEAM)
      .fruit(() -> ItemsTFCF.ROWAN_BERRY, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.WHITE_CHERRY = WoodType
      .builder("white_cherry")
      .color(rgb(208, 175, 163))
      .burnInfo(795f, 1250)
      .rainInfo(180f, 300f)
      .tempInfo(0f, 20f)
      .minGrowthTime(8)
      .density(0.25f, 2f)
      .generator(GEN_WHITE_CHERRY)
      .fruit(() -> ItemFoodTFC.get(Food.CHERRY), 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.WHITE_ELM = WoodType
      .builder("white_elm")
      .color(rgb(174, 170, 136))
      .burnInfo(653f, 1750)
      .isCanMakeTannin()
      .rainInfo(60f, 290f)
      .tempInfo(2f, 20f)
      .minGrowthTime(8)
      .bushes()
      .generator(GEN_WHITE_ELM)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.MANGROVE = WoodType
      .builder("mangrove") //TODO
      .color(rgb(156, 150, 150))
      .burnInfo(653f, 1750)
      .isCanMakeTannin()
      .rainInfo(60f, 290f)
      .tempInfo(2f, 20f)
      .minGrowthTime(8)
      .bushes()
      .generator(GEN_MANGROVE)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.YELLOW_MERANTI = WoodType
      .builder("yellow_meranti")
      .color(rgb(182, 149, 114))
      .burnInfo(837f, 1200)
      .rainInfo(260f, 500f)
      .tempInfo(21f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.5f, 2f)
      .generator(GEN_YELLOW_MERANTI)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.YEW = WoodType
      .builder("yew")
      .color(rgb(238, 161, 118))
      .burnInfo(813f, 2150)
      .rainInfo(180f, 350f)
      .tempInfo(-15f, 11f)
      .minGrowthTime(10)
      .bushes()
      .generator(GEN_YEW)
      .fruit(() -> ItemsTFCF.YEW_BERRY, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.ZEBRAWOOD = WoodType
      .builder("zebrawood")
      .color(rgb(149, 118, 77))
      .burnInfo(822f, 1570)
      .rainInfo(280f, 500f)
      .tempInfo(23f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.5f, 2f)
      .generator(GEN_ZEBRAWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

    WoodTypes.FRUITWOOD = WoodType
      .builder("fruitwood")
      .color(rgb(178, 181, 162))
      .burnInfo(720f, 1000)
      .rainInfo(180f, 550f)
      .tempInfo(11f, 30f)
      .dominance(0)
      .minGrowthTime(9)
      .bushes()
      .density(0f, 0f)
      .generator(GEN_FRUITWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .build();

//    WoodTypes.DATE_PALM = WoodType
//      .builder("date_palm") //TODO
//      .color(rgb(178, 181, 162))
//      .burnInfo(720f, 1000)
//      .rainInfo(180f, 550f)
//      .tempInfo(11f, 30f)
//      .dominance(0)
//      .minGrowthTime(9)
//      .bushes()
//      .density(0f, 0f)
//      .generator(GEN_FRUITWOOD)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.COCONUT_PALM = WoodType
//      .builder("coconut_palm") //TODO
//      .color(rgb(176, 170, 154))
//      .burnInfo(720f, 1000)
//      .rainInfo(180f, 550f)
//      .tempInfo(11f, 30f)
//      .dominance(0)
//      .minGrowthTime(9)
//      .bushes()
//      .density(0f, 0f)
//      .generator(GEN_FRUITWOOD)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();

    // Фруктовые
//    WoodTypes.BANANA = new WoodType
//            ("banana")
//            .setColor(0xBB9C7D)
//            .setBurnInfo(720f, 1750)
//            .setRainInfo()
//            .setTempInfo()
//            .build();
//
//    WoodTypes.CHERRY = new WoodType
//            ("cherry")
//            .setColor(0xDC5E2C)
//            .setBurnInfo(720f, 1750)
//            .setRainInfo()
//            .setTempInfo()
//            .build();
//
//    WoodTypes.GREEN_APPLE = new WoodType
//            ("green_apple")
//            .setColor(0xCFC498)
//            .setBurnInfo(720f, 1750)
//            .setRainInfo()
//            .setTempInfo()
//            .build();
//
//    WoodTypes.LEMON = new WoodType
//            ("lemon")
//            .setColor(0xE8DD8C)
//            .setBurnInfo(720f, 1750)
//            .setRainInfo()
//            .setTempInfo()
//            .build();
//
//    WoodTypes.OLIVE = new WoodType
//            ("olive")
//            .setColor(0x97492B)
//            .setBurnInfo(720f, 1750)
//            .setRainInfo()
//            .setTempInfo()
//            .build();
//
//    WoodTypes.ORANGE = new WoodType
//            ("orange")
//            .setColor(0xC18C52)
//            .setBurnInfo(720f, 1750)
//            .setRainInfo()
//            .setTempInfo()
//            .build();
//
//    WoodTypes.PEACH = new WoodType
//            ("peach")
//            .setColor(0x9A5B37)
//            .setBurnInfo(720f, 1750)
//            .setRainInfo()
//            .setTempInfo()
//            .build();
//
//    WoodTypes.PLUM = new WoodType
//            ("plum")
//            .setColor(0x9F573B)
//            .setBurnInfo(720f, 1750)
//            .setRainInfo()
//            .setTempInfo()
//            .build();
//
//    WoodTypes.RED_APPLE = new WoodType
//            ("red_apple")
//            .setColor(0x6E150E)
//            .setBurnInfo(720f, 1750)
//            .setRainInfo()
//            .setTempInfo()
//            .build();
//
//    WoodTypes.COCOA = new WoodType
//            ("cocoa")
//            .setColor(0xC29D79)
//            .setBurnInfo(822f, 1570)
//            .setRainInfo()
//            .setTempInfo()
//            .build();

//    // ?
//    WoodTypes.CINNAMON = WoodType
//      .builder("cinnamon")
//      .color(0x8c6333)
//      .burnInfo(822f, 1570)
//      .rainInfo(28, 35)
//      .tempInfo(280, 400)
//      .density(0f, 1f)
//      .dominance(0)
//      .radius(4)
//      .height(15)
//      .decayDist(4)
//      .minGrowthTime(15)
//      .generator(GEN_SEQUOIA)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();

//    WoodTypes.CASSIA_CINNAMON = WoodType
//      .builder("cassia_cinnamon")
//      .color(0x92744c)
//      .burnInfo(822f, 1570)
//      .rainInfo(20, 35)
//      .tempInfo(250, 400)
//      .density(0.1f, 1f)
//      .dominance(2)
//      .radius(4)
//      .height(15)
//      .decayDist(6)
//      .minGrowthTime(15)
//      .generator(GEN_CASSIA_CINNAMON)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.CEYLON_CINNAMON = WoodType
//      .builder("ceylon_cinnamon")
//      .color(0x92744c)
//      .burnInfo(822f, 1570)
//      .rainInfo(20, 35)
//      .tempInfo(250, 400)
//      .density(0.1f, 1f)
//      .dominance(2)
//      .radius(4)
//      .height(15)
//      .decayDist(6)
//      .minGrowthTime(15)
//      .generator(GEN_CEYLON_CINNAMON)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//    WoodTypes.ARGYLE_EUCALYPTUS = WoodType
//      .builder("argyle_eucalyptus") //TODO
//      .color(rgb(243, 159, 112))
//      .burnInfo(773f, 1200)
//      .rainInfo(320f, 500f)
//      .tempInfo(22f, 50f)
//      .radius(1)
//      .decayDist(6)
//      .minGrowthTime(18)
//      .density(0.5f, 2f)
//      .bushes()
//      .generator(GEN_ANGELIM)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//    WoodTypes.WHITE_JACARANDA = WoodType
//      .builder("white_jacaranda") //TODO
//      .color(rgb(144, 114, 94))
//      .burnInfo(653f, 1750)
//      .isCanMakeTannin()
//      .rainInfo(60f, 290f)
//      .tempInfo(2f, 20f)
//      .minGrowthTime(8)
//      .bushes()
//      .generator(GEN_WHITE_ELM)
//      .fruit(null, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.WHITE_MANGROVE = WoodType
//      .builder("white_mangrove") //TODO
//      .color(rgb(156, 150, 150))
//      .burnInfo(653f, 1750)
//      .isCanMakeTannin()
//      .rainInfo(60f, 290f)
//      .tempInfo(2f, 20f)
//      .minGrowthTime(8)
//      .bushes()
//      .generator(GEN_WHITE_ELM)
//      .fruit(null, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.YELLOW_JACARANDA = WoodType
//      .builder("yellow_jacaranda") //TODO
//      .color(rgb(144, 113, 93))
//      .burnInfo(837f, 1200)
//      .rainInfo(260f, 500f)
//      .tempInfo(21f, 50f)
//      .radius(1)
//      .decayDist(6)
//      .minGrowthTime(18)
//      .bushes()
//      .density(0.5f, 2f)
//      .generator(GEN_YELLOW_MERANTI)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//    WoodTypes.WHITE_CHERRY_BLOSSOM = WoodType
//      .builder("white_cherry_blossom")
//      .color(rgb(208, 175, 163))
//      .burnInfo(795f, 1250)
//      .rainInfo(180f, 300f)
//      .tempInfo(0f, 20f)
//      .minGrowthTime(8)
//      .density(0.25f, 2f)
//      .generator(GEN_WHITE_CHERRY)
//      .fruit(() -> ItemFoodTFC.get(Food.CHERRY), 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//    WoodTypes.SLOE = WoodType
//      .builder("sloe") //TODO
//      .color(rgb(205, 174, 135))
//      .burnInfo(745f, 2000)
//      .isCanMakeTannin()
//      .rainInfo(140f, 360f)
//      .tempInfo(-2f, 18f)
//      .decayDist(6)
//      .minGrowthTime(16)
//      .bushes()
//      .density(0.2f, 1f)
//      .generator(GEN_SWEETGUM)
//      .fruit(null, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.SNOW_GUM_EUCALYPTUS = WoodType
//      .builder("snow_gum_eucalyptus") //TODO
//      .color(rgb(239, 157, 109))
//      .burnInfo(745f, 2000)
//      .isCanMakeTannin()
//      .rainInfo(140f, 360f)
//      .tempInfo(-2f, 18f)
//      .decayDist(6)
//      .minGrowthTime(16)
//      .bushes()
//      .density(0.2f, 1f)
//      .generator(GEN_SWEETGUM)
//      .fruit(null, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.SORB = WoodType
//      .builder("sorb") //TODO
//      .color(rgb(82, 44, 41))
//      .burnInfo(745f, 2000)
//      .isCanMakeTannin()
//      .rainInfo(140f, 360f)
//      .tempInfo(-2f, 18f)
//      .decayDist(6)
//      .minGrowthTime(16)
//      .bushes()
//      .density(0.2f, 1f)
//      .generator(GEN_SWEETGUM)
//      .fruit(null, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.RED_MANGROVE = WoodType
//      .builder("red_mangrove")
//      .color(rgb(99, 34, 34))
//      .burnInfo(783f, 1100)
//      .rainInfo(200f, 500f)
//      .tempInfo(15f, 40f)
//      .dominance(0f)
//      .density(0f, 0f)
//      .radius(1)
//      .minGrowthTime(8)
//      .bushes()
//      .generator(GEN_MANGROVE)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//    WoodTypes.RED_CYPRESS = WoodType
//      .builder("red_cypress") //TODO
//      .color(rgb(109, 107, 74))
//      .burnInfo(618f, 1750)
//      .isCanMakeTannin()
//      .rainInfo(10f, 240f)
//      .tempInfo(-8f, 17f)
//      .decayDist(6)
//      .minGrowthTime(18)
//      .bushes()
//      .isConifer()
//      .bushes()
//      .density(0.4f, 2f)
//      .generator(GEN_RED_CEDAR)
//      .fruit(() -> ItemsTFCF.JUNIPER, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.PURPLE_JACARANDA = WoodType
//      .builder("purple_jacaranda") //TODO
//      .color(rgb(142, 110, 91))
//      .burnInfo(795f, 1250)
//      .rainInfo(180f, 300f)
//      .tempInfo(10f, 34f)
//      .minGrowthTime(8)
//      .density(0.25f, 2f)
//      .generator(GEN_JACARANDA)
//      .fruit(null, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.QUINCE = WoodType
//      .builder("quince") //TODO
//      .color(rgb(183, 137, 62))
//      .burnInfo(795f, 1250)
//      .rainInfo(180f, 300f)
//      .tempInfo(10f, 34f)
//      .minGrowthTime(8)
//      .density(0.25f, 2f)
//      .generator(GEN_JACARANDA)
//      .fruit(null, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.RAINBOW_EUCALYPTUS = WoodType
//      .builder("rainbow_eucalyptus")
//      .color(rgb(239, 157, 109))
//      .burnInfo(705f, 1000)
//      .rainInfo(120f, 300f)
//      .tempInfo(18f, 39f)
//      .minGrowthTime(8)
//      .bushes()
//      .density(0.35f, 2f)
//      .generator(GEN_EUCALYPTUS)
//      .fruit(null, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.PEAR = WoodType
//      .builder("pear") //TODO
//      .color(rgb(156, 71, 34))
//      .burnInfo(628f, 1500)
//      .rainInfo(100f, 380f)
//      .tempInfo(-20f, 5f)
//      .minGrowthTime(8)
//      .isConifer()
//      .density(0.1f, 0.9f)
//      .generator(GEN_NORWAY_SPRUCE)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.PERSIMMON = WoodType
//      .builder("persimmon") //TODO
//      .color(rgb(81, 45, 36))
//      .burnInfo(628f, 1500)
//      .rainInfo(100f, 380f)
//      .tempInfo(-20f, 5f)
//      .minGrowthTime(8)
//      .isConifer()
//      .density(0.1f, 0.9f)
//      .generator(GEN_NORWAY_SPRUCE)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.PINK_CHERRY_BLOSSOM = WoodType
//      .builder("pink_cherry_blossom")
//      .color(rgb(225, 167, 160))
//      .burnInfo(795f, 1250)
//      .rainInfo(180f, 300f)
//      .tempInfo(0f, 20f)
//      .minGrowthTime(8)
//      .density(0.25f, 2f)
//      .generator(GEN_PINK_CHERRY)
//      .fruit(() -> ItemFoodTFC.get(Food.CHERRY), 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
//    WoodTypes.MULBERRY = WoodType
//      .builder("mulberry") //TODO
//      .color(rgb(154, 113, 81))
//      .burnInfo(696f, 1250)
//      .rainInfo(80f, 270f)
//      .tempInfo(9f, 33f)
//      .minGrowthTime(10)
//      .bushes()
//      .density(0.4f, 2f)
//      .generator(GEN_MOUNTAIN_ASH)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//    WoodTypes.MEDLAR = WoodType
//      .builder("medlar") //TODO
//      .color(rgb(132, 82, 68))
//      .burnInfo(837f, 1200)
//      .rainInfo(180f, 500f)
//      .tempInfo(16f, 35f)
//      .decayDist(6)
//      .minGrowthTime(18)
//      .bushes()
//      .density(0.2f, 2f)
//      .generator(GEN_MARBLEWOOD)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//    WoodTypes.LAUREL = WoodType
//      .builder("laurel")
//      .color(rgb(202, 165, 110))
//      .burnInfo(632f, 1250)
//      .rainInfo(60f, 400f)
//      .tempInfo(-12f, 15f)
//      .minGrowthTime(8)
//      .isConifer()
//      .density(0.25f, 1f)
//      .generator(GEN_LARCH)
//      .fruit(() -> ItemsTFCF.PINECONE, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();

//    WoodTypes.BAMBOO = WoodType
//      .builder("bamboo")
//      .color(rgb(83, 97, 28))
//      .burnInfo(770f, 1300)
//      .rainInfo(180f, 500f)
//      .tempInfo(10f, 38f)
//      .dominance(0f)
//      .density(0f, 0f)
//      .minGrowthTime(8)
//      .bushes()
//      .isConifer()
//      .generator(GEN_BALD_CYPRESS)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();

//    WoodTypes.BLACK_WILLOW = WoodType
//      .builder("black_willow") //TODO
//      .color(rgb(35, 42, 11))
//      .burnInfo(758f, 1800)
//      .rainInfo(180f, 300f)
//      .tempInfo(-10f, 16f)
//      .minGrowthTime(9)
//      .bushes()
//      .generator(GEN_BLACK_WALNUT)
//      .fruit(null, 0.33f)
//      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();

//    WoodTypes.BUXUS = WoodType
//      .builder("buxus")
//      .color(rgb(212, 175, 130))
//      .burnInfo(683f, 1500)
//      .rainInfo(180f, 400f)
//      .tempInfo(-8f, 15f)
//      .minGrowthTime(8)
//      .density(0.25f, 1f)
//      .generator(GEN_TALL_TFC)
//      .bushes()
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
    //    WoodTypes.GHAF = WoodType
//      .builder("ghaf")
//      .color(rgb(95, 93, 98))
//      .burnInfo(590f, 1000)
//      .rainInfo(70f, 220f)
//      .tempInfo(19f, 50f)
//      .minGrowthTime(10)
//      .bushes()
//      .density(0.25f, 1f)
//      .generator(GEN_FEVER)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
    //    WoodTypes.JABUTICABEIRA = WoodType
//      .builder("jabuticabeira") //TODO
//      .color(rgb(147, 86, 76))
//      .burnInfo(694f, 1170)
//      .rainInfo(30f, 210f)
//      .tempInfo(11f, 36f)
//      .decayDist(6)
//      .minGrowthTime(11)
//      .bushes()
//      .density(0.1f, 0.6f)
//      .generator(GEN_IRONWOOD)
//      .paramMap(0.30f, 16f, 3, 3, 0.85f)
//      .growthLogicKit("darkoak")
//      .cellKit("deciduous")
//      .build();
//
  }
}
