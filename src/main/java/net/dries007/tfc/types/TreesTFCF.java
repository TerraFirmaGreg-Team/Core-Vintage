package net.dries007.tfc.types;

import su.terrafirmagreg.api.data.enums.Mods;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.api.util.ITreeGenerator;
import net.dries007.tfc.world.classic.worldgen.TreeGenRandom;
import net.dries007.tfc.world.classic.worldgen.trees.TreeGenNormal;
import net.dries007.tfc.world.classic.worldgen.trees.TreeGenVariants;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = Mods.ModIDs.TFCF)
public final class TreesTFCF {

  public static final ResourceLocation AFRICAN_PADAUK = new ResourceLocation(Mods.ModIDs.TFC, "african_padauk");
  public static final ResourceLocation ALDER = new ResourceLocation(Mods.ModIDs.TFC, "alder");
  public static final ResourceLocation ANGELIM = new ResourceLocation(Mods.ModIDs.TFC, "angelim");
  public static final ResourceLocation BALD_CYPRESS = new ResourceLocation(Mods.ModIDs.TFC, "bald_cypress");
  public static final ResourceLocation BAOBAB = new ResourceLocation(Mods.ModIDs.TFC, "baobab");
  public static final ResourceLocation BEECH = new ResourceLocation(Mods.ModIDs.TFC, "beech");
  public static final ResourceLocation BLACK_WALNUT = new ResourceLocation(Mods.ModIDs.TFC, "black_walnut");
  public static final ResourceLocation BOX = new ResourceLocation(Mods.ModIDs.TFC, "box");
  public static final ResourceLocation BRAZILWOOD = new ResourceLocation(Mods.ModIDs.TFC, "brazilwood");
  public static final ResourceLocation BUTTERNUT = new ResourceLocation(Mods.ModIDs.TFC, "butternut");
  public static final ResourceLocation COCOBOLO = new ResourceLocation(Mods.ModIDs.TFC, "cocobolo");
  public static final ResourceLocation CYPRESS = new ResourceLocation(Mods.ModIDs.TFC, "cypress");
  public static final ResourceLocation EBONY = new ResourceLocation(Mods.ModIDs.TFC, "ebony");
  public static final ResourceLocation EUCALYPTUS = new ResourceLocation(Mods.ModIDs.TFC, "eucalyptus");
  public static final ResourceLocation EUROPEAN_OAK = new ResourceLocation(Mods.ModIDs.TFC, "european_oak");
  public static final ResourceLocation FEVER = new ResourceLocation(Mods.ModIDs.TFC, "fever");
  public static final ResourceLocation FRUITWOOD = new ResourceLocation(Mods.ModIDs.TFC, "fruitwood");
  public static final ResourceLocation GINKGO = new ResourceLocation(Mods.ModIDs.TFC, "ginkgo");
  public static final ResourceLocation GREENHEART = new ResourceLocation(Mods.ModIDs.TFC, "greenheart");
  public static final ResourceLocation HAWTHORN = new ResourceLocation(Mods.ModIDs.TFC, "hawthorn");
  public static final ResourceLocation HAZEL = new ResourceLocation(Mods.ModIDs.TFC, "hazel");
  public static final ResourceLocation HEMLOCK = new ResourceLocation(Mods.ModIDs.TFC, "hemlock");
  public static final ResourceLocation HOLLY = new ResourceLocation(Mods.ModIDs.TFC, "holly");
  public static final ResourceLocation HORNBEAM = new ResourceLocation(Mods.ModIDs.TFC, "hornbeam");
  public static final ResourceLocation IPE = new ResourceLocation(Mods.ModIDs.TFC, "ipe");
  public static final ResourceLocation IROKO = new ResourceLocation(Mods.ModIDs.TFC, "iroko");
  public static final ResourceLocation IRONWOOD = new ResourceLocation(Mods.ModIDs.TFC, "ironwood");
  public static final ResourceLocation JACARANDA = new ResourceLocation(Mods.ModIDs.TFC, "jacaranda");
  public static final ResourceLocation JOSHUA_TREE = new ResourceLocation(Mods.ModIDs.TFC, "joshua_tree");
  public static final ResourceLocation JUNIPER = new ResourceLocation(Mods.ModIDs.TFC, "juniper");
  public static final ResourceLocation KAURI = new ResourceLocation(Mods.ModIDs.TFC, "kauri");
  public static final ResourceLocation LARCH = new ResourceLocation(Mods.ModIDs.TFC, "larch");
  public static final ResourceLocation LIMBA = new ResourceLocation(Mods.ModIDs.TFC, "limba");
  public static final ResourceLocation LOCUST = new ResourceLocation(Mods.ModIDs.TFC, "locust");
  public static final ResourceLocation LOGWOOD = new ResourceLocation(Mods.ModIDs.TFC, "logwood");
  public static final ResourceLocation MACLURA = new ResourceLocation(Mods.ModIDs.TFC, "maclura");
  public static final ResourceLocation MAHOE = new ResourceLocation(Mods.ModIDs.TFC, "mahoe");
  public static final ResourceLocation MAHOGANY = new ResourceLocation(Mods.ModIDs.TFC, "mahogany");
  public static final ResourceLocation MANGROVE = new ResourceLocation(Mods.ModIDs.TFC, "mangrove");
  public static final ResourceLocation MARBLEWOOD = new ResourceLocation(Mods.ModIDs.TFC, "marblewood");
  public static final ResourceLocation MESSMATE = new ResourceLocation(Mods.ModIDs.TFC, "messmate");
  public static final ResourceLocation MOUNTAIN_ASH = new ResourceLocation(Mods.ModIDs.TFC, "mountain_ash");
  public static final ResourceLocation NORDMANN_FIR = new ResourceLocation(Mods.ModIDs.TFC, "nordmann_fir");
  public static final ResourceLocation NORWAY_SPRUCE = new ResourceLocation(Mods.ModIDs.TFC, "norway_spruce");
  public static final ResourceLocation PINK_CHERRY = new ResourceLocation(Mods.ModIDs.TFC, "pink_cherry");
  public static final ResourceLocation PINK_IVORY = new ResourceLocation(Mods.ModIDs.TFC, "pink_ivory");
  public static final ResourceLocation POPLAR = new ResourceLocation(Mods.ModIDs.TFC, "poplar");
  public static final ResourceLocation PURPLEHEART = new ResourceLocation(Mods.ModIDs.TFC, "purpleheart");
  public static final ResourceLocation RED_CEDAR = new ResourceLocation(Mods.ModIDs.TFC, "red_cedar");
  public static final ResourceLocation RED_ELM = new ResourceLocation(Mods.ModIDs.TFC, "red_elm");
  public static final ResourceLocation REDWOOD = new ResourceLocation(Mods.ModIDs.TFC, "redwood");
  public static final ResourceLocation ROWAN = new ResourceLocation(Mods.ModIDs.TFC, "rowan");
  public static final ResourceLocation RUBBER_FIG = new ResourceLocation(Mods.ModIDs.TFC, "rubber_fig");
  public static final ResourceLocation SWEETGUM = new ResourceLocation(Mods.ModIDs.TFC, "sweetgum");
  public static final ResourceLocation SYZYGIUM = new ResourceLocation(Mods.ModIDs.TFC, "syzygium");
  public static final ResourceLocation TEAK = new ResourceLocation(Mods.ModIDs.TFC, "teak");
  public static final ResourceLocation WALNUT = new ResourceLocation(Mods.ModIDs.TFC, "walnut");
  public static final ResourceLocation WENGE = new ResourceLocation(Mods.ModIDs.TFC, "wenge");
  public static final ResourceLocation WHITE_CHERRY = new ResourceLocation(Mods.ModIDs.TFC, "white_cherry");
  public static final ResourceLocation WHITE_ELM = new ResourceLocation(Mods.ModIDs.TFC, "white_elm");
  public static final ResourceLocation WHITEBEAM = new ResourceLocation(Mods.ModIDs.TFC, "whitebeam");
  public static final ResourceLocation YELLOW_MERANTI = new ResourceLocation(Mods.ModIDs.TFC, "yellow_meranti");
  public static final ResourceLocation YEW = new ResourceLocation(Mods.ModIDs.TFC, "yew");
  public static final ResourceLocation ZEBRAWOOD = new ResourceLocation(Mods.ModIDs.TFC, "zebrawood");

  // Custom Tree Models
  public static final ITreeGenerator GEN_ASPEN = new TreeGenVariants(true, 54);

  public static final ITreeGenerator GEN_AFRICAN_PADAUK = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_ALDER = new TreeGenVariants(true, 30);
  public static final ITreeGenerator GEN_ANGELIM = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_BALD_CYPRESS = new TreeGenVariants(true, 13);
  public static final ITreeGenerator GEN_BAOBAB = new TreeGenVariants(true, 2);
  public static final ITreeGenerator GEN_BEECH = new TreeGenVariants(true, 33);
  public static final ITreeGenerator GEN_BLACK_WALNUT = new TreeGenVariants(true, 45);
  //public static final ITreeGenerator GEN_BOX = new TreeGenVariants(true, 9);			// Tall Tree Model
  public static final ITreeGenerator GEN_BRAZILWOOD = new TreeGenVariants(true, 14);
  public static final ITreeGenerator GEN_BUTTERNUT = new TreeGenVariants(true, 45);
  public static final ITreeGenerator GEN_COCOBOLO = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_CYPRESS = new TreeGenVariants(true, 5);
  public static final ITreeGenerator GEN_EBONY = new TreeGenVariants(true, 20);
  public static final ITreeGenerator GEN_EUCALYPTUS = new TreeGenVariants(true, 48);
  public static final ITreeGenerator GEN_EUROPEAN_OAK = new TreeGenVariants(true, 45);
  public static final ITreeGenerator GEN_FEVER = new TreeGenVariants(true, 13);
  public static final ITreeGenerator GEN_FRUITWOOD = new TreeGenVariants(true, 3);
  //public static final ITreeGenerator GEN_GIGANTEUM = new TreeGenVariants(true, 10);		// Redwood
  public static final ITreeGenerator GEN_GINKGO = new TreeGenVariants(true, 20);
  public static final ITreeGenerator GEN_GREENHEART = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_HAWTHORN = new TreeGenVariants(true, 15);
  public static final ITreeGenerator GEN_HAZEL = new TreeGenVariants(true, 45);
  public static final ITreeGenerator GEN_HEMLOCK = new TreeGenVariants(true, 16);
  public static final ITreeGenerator GEN_HOLLY = new TreeGenVariants(true, 16);
  public static final ITreeGenerator GEN_HORNBEAM = new TreeGenVariants(true, 35);
  public static final ITreeGenerator GEN_IPE = new TreeGenVariants(true, 66);
  public static final ITreeGenerator GEN_IROKO = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_IRONWOOD = new TreeGenVariants(true, 16);
  public static final ITreeGenerator GEN_JACARANDA = new TreeGenVariants(true, 30);
  public static final ITreeGenerator GEN_JOSHUA_TREE = new TreeGenVariants(true, 1);
  public static final ITreeGenerator GEN_JUNIPER = new TreeGenVariants(true, 17);
  public static final ITreeGenerator GEN_KAURI = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_LARCH = new TreeGenVariants(true, 10);
  public static final ITreeGenerator GEN_LIMBA = new TreeGenVariants(true, 13);
  public static final ITreeGenerator GEN_LOCUST = new TreeGenVariants(true, 15);
  public static final ITreeGenerator GEN_LOGWOOD = new TreeGenVariants(true, 10);
  public static final ITreeGenerator GEN_MACLURA = new TreeGenVariants(true, 15);
  public static final ITreeGenerator GEN_MAHOE = new TreeGenVariants(true, 15);
  public static final ITreeGenerator GEN_MAHOGANY = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_MANGROVE = new TreeGenVariants(true, 13);
  public static final ITreeGenerator GEN_MARBLEWOOD = new TreeGenVariants(true, 24);
  public static final ITreeGenerator GEN_MESSMATE = new TreeGenVariants(true, 18);
  public static final ITreeGenerator GEN_MOUNTAIN_ASH = new TreeGenVariants(true, 20);
  public static final ITreeGenerator GEN_NORDMANN_FIR = new TreeGenVariants(true, 26);
  public static final ITreeGenerator GEN_NORWAY_SPRUCE = new TreeGenVariants(true, 16);
  public static final ITreeGenerator GEN_PINK_CHERRY = new TreeGenVariants(true, 45);
  public static final ITreeGenerator GEN_PINK_IVORY = new TreeGenVariants(true, 19);
  public static final ITreeGenerator GEN_POPLAR = new TreeGenVariants(true, 51);
  public static final ITreeGenerator GEN_PURPLEHEART = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_RED_CEDAR = new TreeGenVariants(true, 25);
  public static final ITreeGenerator GEN_RED_ELM = new TreeGenVariants(true, 60);
  public static final ITreeGenerator GEN_REDWOOD = new TreeGenVariants(true, 22);
  public static final ITreeGenerator GEN_ROWAN = new TreeGenVariants(true, 18);
  public static final ITreeGenerator GEN_RUBBER_FIG = new TreeGenVariants(true, 9);
  public static final ITreeGenerator GEN_SWEETGUM = new TreeGenVariants(true, 60);
  public static final ITreeGenerator GEN_SYZYGIUM = new TreeGenVariants(true, 14);
  public static final ITreeGenerator GEN_TEAK = new TreeGenVariants(true, 13);
  public static final ITreeGenerator GEN_WALNUT = new TreeGenVariants(true, 45);
  public static final ITreeGenerator GEN_WENGE = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_WHITE_CHERRY = new TreeGenVariants(true, 45);
  public static final ITreeGenerator GEN_WHITE_ELM = new TreeGenVariants(true, 60);
  public static final ITreeGenerator GEN_WHITEBEAM = new TreeGenVariants(true, 42);
  public static final ITreeGenerator GEN_YELLOW_MERANTI = new TreeGenVariants(true, 34);
  public static final ITreeGenerator GEN_YEW = new TreeGenVariants(true, 19);
  public static final ITreeGenerator GEN_ZEBRAWOOD = new TreeGenVariants(true, 34);

  public static final ITreeGenerator GEN_TALL_TFC = new TreeGenNormal(3, 3);
  public static final ITreeGenerator GEN_NORMAL = new TreeGenRandom(1, 3, 3);
  public static final ITreeGenerator GEN_MEDIUM = new TreeGenRandom(2, 2, 3);
  public static final ITreeGenerator GEN_TALL = new TreeGenRandom(3, 3, 3);
  public static final ITreeGenerator GEN_TALL_SINGLE = new TreeGenRandom(3, 3, 1);

  // Seasonal & Fruit-bearing Trees
  public static final Tree ASH_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "ash"), 60f, 140f, -6f, 12f, GEN_NORMAL).setBushes()
    .setBurnInfo(696f, 1250)
    .build();
  public static final Tree ASPEN_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "aspen"), 70f, 280f, -10f, 16f, GEN_ASPEN).setGrowthTime(8)
    .setDensity(0.25f, 1f)
    .setBurnInfo(611f, 1000)
    .build();
  public static final Tree BIRCH_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "birch"), 20f, 180f, -15f, 7f, GEN_TALL).setRadius(1)
    .setTannin()
    .setBurnInfo(652f, 1750)
    .build();
  public static final Tree CHESTNUT_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "chestnut"), 160f, 320f, 11f, 35f, GEN_NORMAL).setTannin()
    .setBushes()
    .setBurnInfo(651f, 1500)
    .build();
  public static final Tree HICKORY_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "hickory"), 80f, 250f, 7f, 29f, GEN_TALL).setGrowthTime(10)
    .setBushes()
    .setTannin()
    .setBurnInfo(762f, 2000)
    .build();
  public static final Tree MAPLE_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "maple"), 140f, 360f, 3f, 20f, GEN_MEDIUM).setDominance(6.3f)
    .setRadius(1)
    .setTannin()
    .setBurnInfo(745f, 2000)
    .build();
  public static final Tree OAK_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "oak"), 180f, 430f, -8f, 12f, GEN_TALL).setHeight(16)
    .setGrowthTime(10)
    .setBushes()
    .setTannin()
    .setBurnInfo(728f, 2250)
    .build();
  public static final Tree SYCAMORE_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "sycamore"), 120f, 290f, 17f, 33f, GEN_MEDIUM).setGrowthTime(8)
    .setBushes()
    .setDensity(0.25f, 2f)
    .setBurnInfo(653f, 1750)
    .build();
  public static final Tree WHITE_CEDAR_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "white_cedar"), 10f, 240f, -8f, 17f, GEN_TALL_SINGLE).setHeight(16)
    .setBushes()
    .setBurnInfo(625f, 1500)
    .build();

  public static final Tree BAOBAB_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "baobab"), 10f, 150f, 21f, 40f, GEN_BAOBAB).setDecayDist(6)
    .setGrowthTime(20)
    .setDensity(0.1f, 0.3f)
    .setBurnInfo(478f, 1000)
    .build();
  public static final Tree EUCALYPTUS_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "eucalyptus"), 120f, 300f, 18f, 39f, GEN_EUCALYPTUS).setGrowthTime(8)
    .setBushes()
    .setDensity(0.35f, 2f)
    .setBurnInfo(705f, 1000)
    .build();
  public static final Tree HAWTHORN_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "hawthorn"), 180f, 400f, -8f, 14f, GEN_HAWTHORN).setGrowthTime(8)
    .setDensity(0.25f, 1f)
    .setBurnInfo(683f, 1500)
    .build();
  public static final Tree MACLURA_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "maclura"), 140f, 400f, -1f, 17f, GEN_MACLURA).setGrowthTime(8)
    .setBushes()
    .setDensity(0.25f, 1f)
    .setBurnInfo(773f, 1000)
    .build();
  public static final Tree MAHOGANY_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "mahogany"), 270f, 500f, 23f, 42f, GEN_MAHOGANY).setRadius(1)
    .setDecayDist(6)
    .setGrowthTime(18)
    .setBushes()
    .setDensity(0.5f, 2f)
    .setBurnInfo(773f, 1000)
    .build();
  public static final Tree PINK_IVORY_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "pink_ivory"), 210f, 500f, 18f, 31f, GEN_PINK_IVORY).setDecayDist(6)
    .setGrowthTime(18)
    .setBushes()
    .setDensity(0.2f, 2f)
    .setBurnInfo(773f, 1000)
    .build();
  public static final Tree RED_CEDAR_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "red_cedar"), 10f, 240f, -8f, 17f, GEN_RED_CEDAR).setDecayDist(6)
    .setGrowthTime(18)
    .setBushes()
    .setConifer()
    .setBushes()
    .setTannin()
    .setDensity(0.4f, 2f)
    .setBurnInfo(618f, 1750)
    .build();
  public static final Tree ROWAN_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "rowan"), 180f, 400f, -15f, 8f, GEN_ROWAN).setGrowthTime(8)
    .setDensity(0.25f, 1f)
    .setBurnInfo(645f, 2000)
    .build();
  public static final Tree SYZYGIUM_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "syzygium"), 140f, 360f, 13f, 35f, GEN_SYZYGIUM).setDecayDist(6)
    .setGrowthTime(16)
    .setBushes()
    .setTannin()
    .setDensity(0.2f, 1f)
    .setBurnInfo(745f, 2000)
    .build();
  public static final Tree YEW_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "yew"), 180f, 350f, -15f, 11f, GEN_YEW).setGrowthTime(10)
    .setBushes()
    .setBurnInfo(813f, 2150)
    .build();
  public static final Tree JACARANDA_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "jacaranda"), 180f, 300f, 10f, 34f, GEN_JACARANDA).setGrowthTime(8)
    .setDensity(0.25f, 2f)
    .setBurnInfo(795f, 1250)
    .build();
  public static final Tree JOSHUA_TREE_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "joshua_tree"), 20f, 150f, 15f, 40f, GEN_JOSHUA_TREE).setDominance(0f)
    .setDensity(0f, 0f)
    .setGrowthTime(8)
    .setConifer()
    .setBurnInfo(696f, 1250)
    .build();
  public static final Tree JUNIPER_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "juniper"), 80f, 350f, -8f, 20f, GEN_JUNIPER).setGrowthTime(8)
    .setConifer()
    .setDensity(0.25f, 0.75f)
    .setTannin()
    .setBurnInfo(632f, 1750)
    .build();
  public static final Tree IPE_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "ipe"), 150f, 350f, 15f, 32f, GEN_IPE).setDecayDist(6)
    .setGrowthTime(18)
    .setBushes()
    .setDensity(0.2f, 2f)
    .setBurnInfo(785f, 1200)
    .build();
  public static final Tree PINK_CHERRY_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "pink_cherry"), 180f, 300f, 0f, 20f, GEN_PINK_CHERRY).setGrowthTime(8)
    .setDensity(0.25f, 2f)
    .setBurnInfo(795f, 1250)
    .build();
  public static final Tree WHITE_CHERRY_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "white_cherry"), 180f, 300f, 0f, 20f, GEN_WHITE_CHERRY).setGrowthTime(8)
    .setDensity(0.25f, 2f)
    .setBurnInfo(795f, 1250)
    .build();
  public static final Tree SWEETGUM_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "sweetgum"), 140f, 360f, -2f, 18f, GEN_SWEETGUM).setDecayDist(6)
    .setGrowthTime(16)
    .setBushes()
    .setTannin()
    .setDensity(0.2f, 1f)
    .setBurnInfo(745f, 2000)
    .build();
  public static final Tree LARCH_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "larch"), 60f, 400f, -12f, 15f, GEN_LARCH).setGrowthTime(8)
    .setConifer()
    .setDensity(0.25f, 1f)
    .setBurnInfo(632f, 1250)
    .build();
  public static final Tree ALDER_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "alder"), 60f, 400f, -4f, 13f, GEN_ALDER).setGrowthTime(8)
    .setBushes()
    .setDensity(0.25f, 2f)
    .setBurnInfo(601f, 1000)
    .build();
  public static final Tree BEECH_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "beech"), 220f, 300f, -15f, 9f, GEN_BEECH).setGrowthTime(8)
    .setBushes()
    .setTannin()
    .setDensity(0.25f, 1f)
    .setBurnInfo(703f, 1750)
    .build();
  public static final Tree BLACK_WALNUT_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "black_walnut"), 180f, 300f, -10f, 16f, GEN_BLACK_WALNUT).setGrowthTime(9)
    .setBushes()
    .setBurnInfo(758f, 1800)
    .build();
  public static final Tree BUTTERNUT_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "butternut"), 180f, 320f, -8f, 17f, GEN_BUTTERNUT).setGrowthTime(9)
    .setBushes()
    .setBurnInfo(758f, 1800)
    .build();
  public static final Tree EUROPEAN_OAK_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "european_oak"), 140f, 430f, -8f, 15f, GEN_EUROPEAN_OAK).setGrowthTime(10)
    .setBushes()
    .setTannin()
    .setBurnInfo(728f, 2250)
    .build();
  public static final Tree GINKGO_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "ginkgo"), 240f, 550f, 6f, 20f, GEN_GINKGO).setGrowthTime(8)
    .setDensity(0.25f, 1f)
    .setBurnInfo(710f, 1000)
    .build();
  public static final Tree HAZEL_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "hazel"), 60f, 400f, -10f, 14f, GEN_HAZEL).setGrowthTime(8)
    .setBushes()
    .setDensity(0.25f, 1f)
    .setBurnInfo(683f, 1500)
    .build();
  public static final Tree HORNBEAM_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "hornbeam"), 140f, 430f, -10f, 12f, GEN_HORNBEAM).setGrowthTime(10)
    .setBushes()
    .setTannin()
    .setBurnInfo(728f, 2250)
    .build();
  public static final Tree LOCUST_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "locust"), 120f, 290f, -6f, 15f, GEN_LOCUST).setGrowthTime(8)
    .setBushes()
    .setBurnInfo(653f, 1750)
    .build();
  public static final Tree POPLAR_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "poplar"), 140f, 400f, -7f, 14f, GEN_POPLAR).setGrowthTime(8)
    .setDensity(0.25f, 1f)
    .setBurnInfo(609f, 1000)
    .build();
  public static final Tree RED_ELM_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "red_elm"), 60f, 290f, 2f, 20f, GEN_RED_ELM).setDecayDist(6)
    .setGrowthTime(18)
    .setBushes()
    .setTannin()
    .setDensity(0.4f, 2f)
    .setBurnInfo(618f, 1750)
    .build();
  public static final Tree WALNUT_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "walnut"), 180f, 300f, -10f, 16f, GEN_WALNUT).setGrowthTime(9)
    .setBushes()
    .setBurnInfo(758f, 1800)
    .build();
  public static final Tree WHITE_ELM_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "white_elm"), 60f, 290f, 2f, 20f, GEN_WHITE_ELM).setGrowthTime(8)
    .setBushes()
    .setTannin()
    .setBurnInfo(653f, 1750)
    .build();
  public static final Tree WHITEBEAM_TREE = new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "whitebeam"), 140f, 430f, -10f, 12f, GEN_WHITEBEAM).setGrowthTime(10)
    .setBushes()
    .setTannin()
    .setBurnInfo(728f, 1750)
    .build();
  public static final ITreeGenerator GEN_CASSIA_CINNAMON = new TreeGenVariants(true, 6);
  public static final ITreeGenerator GEN_CEYLON_CINNAMON = new TreeGenVariants(true, 6);
  public static final Tree CASSIA_CINNAMON_TREE = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "cassia_cinnamon"), GEN_CASSIA_CINNAMON, 20, 35, 250, 400, 0.1f, 1, 2, 4, 15, 6, false, null, false, 15, 710f, 1000);
  public static final Tree CEYLON_CINNAMON_TREE = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "ceylon_cinnamon"), GEN_CEYLON_CINNAMON, 20, 35, 250, 400, 0.1f, 1, 2, 4, 15, 6, false, null, false, 15, 710f, 1000);
  public static final ITreeGenerator GEN_ARROW_BAMBOO = new TreeGenVariants(true, 4);  // Pseudosasa japonica
  public static final ITreeGenerator GEN_BLACK_BAMBOO = new TreeGenVariants(true, 4); // Phyllostachys nigra
  public static final ITreeGenerator GEN_BLUE_BAMBOO = new TreeGenVariants(true, 4); // Himalayacalamus hookerianus
  public static final ITreeGenerator GEN_DRAGON_BAMBOO = new TreeGenVariants(true, 4); // Dendrocalamus giganteus
  public static final ITreeGenerator GEN_GOLDEN_BAMBOO = new TreeGenVariants(true, 4); // Alphonse Karr
  public static final ITreeGenerator GEN_NARROW_LEAF_BAMBOO = new TreeGenVariants(true, 4); // Guadua angustifolia
  public static final ITreeGenerator GEN_RED_BAMBOO = new TreeGenVariants(true, 4); // Fargesia nitida Jiuzhaigou
  public static final ITreeGenerator GEN_TEMPLE_BAMBOO = new TreeGenVariants(true, 4); // Semiarundinaria fastuosa
  public static final ITreeGenerator GEN_THORNY_BAMBOO = new TreeGenVariants(true, 4); // Chimonobambusa pachystachys
  public static final ITreeGenerator GEN_TIMBER_BAMBOO = new TreeGenVariants(true, 4); // Phyllostachys vivax
  public static final ITreeGenerator GEN_TINWA_BAMBOO = new TreeGenVariants(true, 4); // Cephalostachyum pergracile
  public static final ITreeGenerator GEN_WEAVERS_BAMBOO = new TreeGenVariants(true, 4); // Bambusa textilis
  public static final Tree ARROW_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "arrow_bamboo"), GEN_ARROW_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree BLACK_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "black_bamboo"), GEN_BLACK_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree BLUE_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "blue_bamboo"), GEN_BLUE_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree DRAGON_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "dragon_bamboo"), GEN_DRAGON_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree GOLDEN_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "golden_bamboo"), GEN_GOLDEN_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree NARROW_LEAF_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "narrow_leaf_bamboo"), GEN_NARROW_LEAF_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree RED_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "red_bamboo"), GEN_RED_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree TEMPLE_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "temple_bamboo"), GEN_TEMPLE_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree THORNY_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "thorny_bamboo"), GEN_THORNY_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree TIMBER_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "timber_bamboo"), GEN_TIMBER_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree TINWA_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "tinwa_bamboo"), GEN_TINWA_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree WEAVERS_BAMBOO = new Tree(new ResourceLocation(Mods.ModIDs.TFC, "weavers_bamboo"), GEN_WEAVERS_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);

  @SubscribeEvent
  public static void onPreRegisterTrees(TFCRegistryEvent.RegisterPreBlock<Tree> event) {
    // Other Trees
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "african_padauk"), 275f, 500f, 22f, 50f, GEN_AFRICAN_PADAUK).setRadius(1)
        .setDecayDist(6)
        .setGrowthTime(18)
        .setBushes()
        .setDensity(0.5f, 2f)
        .setBurnInfo(745f, 1500)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "angelim"), 320f, 500f, 22f, 50f, GEN_ANGELIM).setRadius(1).setDecayDist(6)
        .setGrowthTime(18).setBushes()
        .setDensity(0.5f, 2f)
        .setBurnInfo(773f, 1200)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "box"), 180f, 400f, -8f, 15f, GEN_TALL_TFC).setGrowthTime(8).setBushes()
        .setDensity(0.25f, 1f)
        .setBurnInfo(683f, 1500)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "brazilwood"), 290f, 550f, 14f, 37f, GEN_BRAZILWOOD).setGrowthTime(8)
        .setBushes()
        .setDensity(0.25f, 1f)
        .setBurnInfo(710f, 1000)
        .build());
    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "cocobolo"), 255f, 500f, 20f, 50f, GEN_COCOBOLO).setRadius(1)
      .setGrowthTime(8)
      .setBushes()
      .setDensity(0.5f, 2f)
      .setBurnInfo(773f, 1000)
      .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "ebony"), 180f, 320f, 19f, 38f, GEN_EBONY).setGrowthTime(8).setBushes()
        .setBurnInfo(795f, 1000).build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "fever"), 70f, 220f, 19f, 50f, GEN_FEVER).setGrowthTime(10).setBushes()
        .setDensity(0.25f, 1f)
        .setBurnInfo(590f, 1000).build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "fruitwood"), 180f, 550f, 11f, 30f, GEN_FRUITWOOD).setDominance(0)
        .setGrowthTime(9)
        .setBushes()
        .setDensity(0f, 0f)
        .setBurnInfo(720f, 1000)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "greenheart"), 310f, 500f, 23f, 50f, GEN_GREENHEART).setRadius(1)
        .setDecayDist(6)
        .setGrowthTime(18)
        .setBushes()
        .setDensity(0.5f, 2f)
        .setBurnInfo(793f, 1700)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "holly"), 140f, 400f, -4f, 16f, GEN_HOLLY).setGrowthTime(8).setBushes()
        .setDensity(0.25f, 1f)
        .setBurnInfo(609f, 1000).build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "iroko"), 300f, 500f, 21f, 50f, GEN_IROKO).setRadius(1).setDecayDist(6)
        .setGrowthTime(18).setBushes()
        .setDensity(0.5f, 2f)
        .setBurnInfo(785f, 1200).build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "ironwood"), 30f, 210f, 11f, 36f, GEN_IRONWOOD).setDecayDist(6)
        .setGrowthTime(11)
        .setBushes()
        .setDensity(0.1f, 0.6f)
        .setBurnInfo(694f, 1170)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "kauri"), 330f, 500f, 23f, 50f, GEN_KAURI).setRadius(1).setGrowthTime(10)
        .setBushes().setDensity(0.5f, 2f)
        .setBurnInfo(730f, 1250).build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "limba"), 290f, 550f, 14f, 37f, GEN_LIMBA).setGrowthTime(9).setBushes()
        .setDensity(0.25f, 1f)
        .setBurnInfo(710f, 1000).build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "logwood"), 180f, 430f, 12f, 35f, GEN_LOGWOOD).setGrowthTime(8).setBushes()
        .setDensity(0.25f, 1f)
        .setBurnInfo(695f, 1000)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "mahoe"), 180f, 350f, 13f, 32f, GEN_MAHOE).setHeight(16).setGrowthTime(8)
        .setBushes()
        .setBurnInfo(783f, 1100).build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "mangrove"), 200f, 500f, 15f, 40f, GEN_MANGROVE).setDominance(0f)
        .setDensity(0f, 0f)
        .setRadius(1)
        .setGrowthTime(8)
        .setBushes()
        .setBurnInfo(783f, 1100)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "marblewood"), 180f, 500f, 16f, 35f, GEN_MARBLEWOOD).setDecayDist(6)
        .setGrowthTime(18)
        .setBushes()
        .setDensity(0.2f, 2f)
        .setBurnInfo(837f, 1200)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "messmate"), 120f, 270f, 2f, 27f, GEN_MESSMATE).setGrowthTime(10)
        .setBushes()
        .setDensity(0.2f, 2f)
        .setBurnInfo(696f, 1250)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "mountain_ash"), 80f, 270f, 9f, 33f, GEN_MOUNTAIN_ASH).setGrowthTime(10)
        .setBushes()
        .setDensity(0.4f, 2f)
        .setBurnInfo(696f, 1250)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "purpleheart"), 310f, 500f, 22f, 50f, GEN_PURPLEHEART).setRadius(1)
        .setDecayDist(6)
        .setGrowthTime(18)
        .setBushes()
        .setDensity(0.5f, 2f)
        .setBurnInfo(793f, 1700)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "rubber_fig"), 210f, 550f, 16f, 35f, GEN_RUBBER_FIG).setDecayDist(6)
        .setGrowthTime(16)
        .setBushes()
        .setDensity(0.2f, 1f)
        .setBurnInfo(785f, 1440)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "teak"), 180f, 430f, 17f, 35f, GEN_TEAK).setGrowthTime(8).setBushes()
        .setDensity(0.25f, 1f)
        .setBurnInfo(695f, 1000).build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "wenge"), 255f, 500f, 20f, 50f, GEN_WENGE).setRadius(1).setGrowthTime(8)
        .setBushes().setDensity(0.5f, 2f)
        .setBurnInfo(773f, 1250).build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "yellow_meranti"), 260f, 500f, 21f, 50f, GEN_YELLOW_MERANTI).setRadius(1)
        .setDecayDist(6)
        .setGrowthTime(18)
        .setBushes()
        .setDensity(0.5f, 2f)
        .setBurnInfo(837f, 1200)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "zebrawood"), 280f, 500f, 23f, 50f, GEN_ZEBRAWOOD).setRadius(1)
        .setDecayDist(6)
        .setGrowthTime(18)
        .setBushes()
        .setDensity(0.5f, 2f)
        .setBurnInfo(822f, 1570)
        .build());

    // Coniferous Trees
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "bald_cypress"), 180f, 500f, 10f, 38f, GEN_BALD_CYPRESS).setDominance(0f)
        .setDensity(0f, 0f)
        .setGrowthTime(8)
        .setBushes()
        .setConifer()
        .setBurnInfo(770f, 1300)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "cypress"), 140f, 350f, 4f, 33f, GEN_CYPRESS).setGrowthTime(8).setBushes()
        .setConifer()
        .setBurnInfo(783f, 1100)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "hemlock"), 140f, 400f, -9f, 10f, GEN_HEMLOCK).setGrowthTime(8).setConifer()
        .setDensity(0.25f, 1f)
        .setBurnInfo(609f, 1000)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "nordmann_fir"), 100f, 380f, -16f, 7f, GEN_NORDMANN_FIR).setGrowthTime(8)
        .setConifer()
        .setDensity(0.1f, 0.9f)
        .setBurnInfo(628f, 1500)
        .build());
    event.getRegistry()
      .registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "norway_spruce"), 100f, 380f, -20f, 5f, GEN_NORWAY_SPRUCE).setGrowthTime(8)
        .setConifer()
        .setDensity(0.1f, 0.9f)
        .setBurnInfo(628f, 1500)
        .build());
    event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(Mods.ModIDs.TFC, "redwood"), 160f, 400f, 0f, 17f, GEN_REDWOOD).setDecayDist(6)
      .setGrowthTime(18)
      .setConifer()
      .setBushes()
      .setTannin()
      .setDensity(0.4f, 2f)
      .setBurnInfo(618f, 1750)
      .build());

    // Seasonal & Fruit-bearing Trees
    event.getRegistry().registerAll(ASH_TREE);
    event.getRegistry().registerAll(ASPEN_TREE);
    event.getRegistry().registerAll(BIRCH_TREE);
    event.getRegistry().registerAll(CHESTNUT_TREE);
    event.getRegistry().registerAll(HICKORY_TREE);
    event.getRegistry().registerAll(MAPLE_TREE);
    event.getRegistry().registerAll(OAK_TREE);
    event.getRegistry().registerAll(SYCAMORE_TREE);
    event.getRegistry().registerAll(WHITE_CEDAR_TREE);
    event.getRegistry().registerAll(JOSHUA_TREE_TREE);

    event.getRegistry().registerAll(BAOBAB_TREE);
    event.getRegistry().registerAll(EUCALYPTUS_TREE);
    event.getRegistry().registerAll(HAWTHORN_TREE);
    event.getRegistry().registerAll(MACLURA_TREE);
    event.getRegistry().registerAll(MAHOGANY_TREE);
    event.getRegistry().registerAll(PINK_IVORY_TREE);
    event.getRegistry().registerAll(RED_CEDAR_TREE);
    event.getRegistry().registerAll(ROWAN_TREE);
    event.getRegistry().registerAll(SYZYGIUM_TREE);
    event.getRegistry().registerAll(YEW_TREE);
    event.getRegistry().registerAll(JACARANDA_TREE);
    event.getRegistry().registerAll(JUNIPER_TREE);
    event.getRegistry().registerAll(IPE_TREE);
    event.getRegistry().registerAll(PINK_CHERRY_TREE);
    event.getRegistry().registerAll(WHITE_CHERRY_TREE);
    event.getRegistry().registerAll(SWEETGUM_TREE);
    event.getRegistry().registerAll(LARCH_TREE);
    event.getRegistry().registerAll(ALDER_TREE);
    event.getRegistry().registerAll(BEECH_TREE);
    event.getRegistry().registerAll(BLACK_WALNUT_TREE);
    event.getRegistry().registerAll(BUTTERNUT_TREE);
    //event.getRegistry().registerAll(CYPRESS_TREE); // Whoops, it's a conifer, not deciduous!
    event.getRegistry().registerAll(EUROPEAN_OAK_TREE);
    event.getRegistry().registerAll(GINKGO_TREE);
    event.getRegistry().registerAll(HAZEL_TREE);
    event.getRegistry().registerAll(HORNBEAM_TREE);
    event.getRegistry().registerAll(LOCUST_TREE);
    event.getRegistry().registerAll(POPLAR_TREE);
    event.getRegistry().registerAll(RED_ELM_TREE);
    event.getRegistry().registerAll(WALNUT_TREE);
    event.getRegistry().registerAll(WHITE_ELM_TREE);
    event.getRegistry().registerAll(WHITEBEAM_TREE);
  }
}





