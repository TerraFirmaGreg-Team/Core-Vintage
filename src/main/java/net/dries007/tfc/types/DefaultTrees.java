package net.dries007.tfc.types;

import su.terrafirmagreg.modules.wood.api.generator.ITreeGenerator;
import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeAcacia;
import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeBushes;
import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeComposite;
import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeKapok;
import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeNormal;
import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeRandom;
import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeSequoia;
import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeVariants;
import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeWillow;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Tree;

import static su.terrafirmagreg.api.data.Reference.MODID_TFC;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = MODID_TFC)
public final class DefaultTrees {

  /**
   * Default Tree ResourceLocations
   */
  public static final ResourceLocation ACACIA = new ResourceLocation(MODID_TFC, "acacia");
  public static final ResourceLocation ASH = new ResourceLocation(MODID_TFC, "ash");
  public static final ResourceLocation ASPEN = new ResourceLocation(MODID_TFC, "aspen");
  public static final ResourceLocation BIRCH = new ResourceLocation(MODID_TFC, "birch");
  public static final ResourceLocation BLACKWOOD = new ResourceLocation(MODID_TFC, "blackwood");
  public static final ResourceLocation CHESTNUT = new ResourceLocation(MODID_TFC, "chestnut");
  public static final ResourceLocation DOUGLAS_FIR = new ResourceLocation(MODID_TFC, "douglas_fir");
  public static final ResourceLocation HICKORY = new ResourceLocation(MODID_TFC, "hickory");
  public static final ResourceLocation MAPLE = new ResourceLocation(MODID_TFC, "maple");
  public static final ResourceLocation OAK = new ResourceLocation(MODID_TFC, "oak");
  public static final ResourceLocation PALM = new ResourceLocation(MODID_TFC, "palm");
  public static final ResourceLocation PINE = new ResourceLocation(MODID_TFC, "pine");
  public static final ResourceLocation ROSEWOOD = new ResourceLocation(MODID_TFC, "rosewood");
  public static final ResourceLocation SEQUOIA = new ResourceLocation(MODID_TFC, "sequoia");
  public static final ResourceLocation SPRUCE = new ResourceLocation(MODID_TFC, "spruce");
  public static final ResourceLocation SYCAMORE = new ResourceLocation(MODID_TFC, "sycamore");
  public static final ResourceLocation WHITE_CEDAR = new ResourceLocation(MODID_TFC, "white_cedar");
  public static final ResourceLocation WILLOW = new ResourceLocation(MODID_TFC, "willow");
  public static final ResourceLocation KAPOK = new ResourceLocation(MODID_TFC, "kapok");
  public static final ResourceLocation HEVEA = new ResourceLocation(MODID_TFC, "hevea");
  public static final ResourceLocation AFRICAN_PADAUK = new ResourceLocation(MODID_TFC, "african_padauk");
  public static final ResourceLocation ALDER = new ResourceLocation(MODID_TFC, "alder");
  public static final ResourceLocation ANGELIM = new ResourceLocation(MODID_TFC, "angelim");
  public static final ResourceLocation BALD_CYPRESS = new ResourceLocation(MODID_TFC, "bald_cypress");
  public static final ResourceLocation BAOBAB = new ResourceLocation(MODID_TFC, "baobab");
  public static final ResourceLocation BEECH = new ResourceLocation(MODID_TFC, "beech");
  public static final ResourceLocation BLACK_WALNUT = new ResourceLocation(MODID_TFC, "black_walnut");
  public static final ResourceLocation BOX = new ResourceLocation(MODID_TFC, "box");
  public static final ResourceLocation BRAZILWOOD = new ResourceLocation(MODID_TFC, "brazilwood");
  public static final ResourceLocation BUTTERNUT = new ResourceLocation(MODID_TFC, "butternut");
  public static final ResourceLocation COCOBOLO = new ResourceLocation(MODID_TFC, "cocobolo");
  public static final ResourceLocation CYPRESS = new ResourceLocation(MODID_TFC, "cypress");
  public static final ResourceLocation EBONY = new ResourceLocation(MODID_TFC, "ebony");
  public static final ResourceLocation EUCALYPTUS = new ResourceLocation(MODID_TFC, "eucalyptus");
  public static final ResourceLocation EUROPEAN_OAK = new ResourceLocation(MODID_TFC, "european_oak");
  public static final ResourceLocation FEVER = new ResourceLocation(MODID_TFC, "fever");
  public static final ResourceLocation FRUITWOOD = new ResourceLocation(MODID_TFC, "fruitwood");
  public static final ResourceLocation GINKGO = new ResourceLocation(MODID_TFC, "ginkgo");
  public static final ResourceLocation GREENHEART = new ResourceLocation(MODID_TFC, "greenheart");
  public static final ResourceLocation HAWTHORN = new ResourceLocation(MODID_TFC, "hawthorn");
  public static final ResourceLocation HAZEL = new ResourceLocation(MODID_TFC, "hazel");
  public static final ResourceLocation HEMLOCK = new ResourceLocation(MODID_TFC, "hemlock");
  public static final ResourceLocation HOLLY = new ResourceLocation(MODID_TFC, "holly");
  public static final ResourceLocation HORNBEAM = new ResourceLocation(MODID_TFC, "hornbeam");
  public static final ResourceLocation IPE = new ResourceLocation(MODID_TFC, "ipe");
  public static final ResourceLocation IROKO = new ResourceLocation(MODID_TFC, "iroko");
  public static final ResourceLocation IRONWOOD = new ResourceLocation(MODID_TFC, "ironwood");
  public static final ResourceLocation JACARANDA = new ResourceLocation(MODID_TFC, "jacaranda");
  public static final ResourceLocation JOSHUA_TREE = new ResourceLocation(MODID_TFC, "joshua_tree");
  public static final ResourceLocation JUNIPER = new ResourceLocation(MODID_TFC, "juniper");
  public static final ResourceLocation KAURI = new ResourceLocation(MODID_TFC, "kauri");
  public static final ResourceLocation LARCH = new ResourceLocation(MODID_TFC, "larch");
  public static final ResourceLocation LIMBA = new ResourceLocation(MODID_TFC, "limba");
  public static final ResourceLocation LOCUST = new ResourceLocation(MODID_TFC, "locust");
  public static final ResourceLocation LOGWOOD = new ResourceLocation(MODID_TFC, "logwood");
  public static final ResourceLocation MACLURA = new ResourceLocation(MODID_TFC, "maclura");
  public static final ResourceLocation MAHOE = new ResourceLocation(MODID_TFC, "mahoe");
  public static final ResourceLocation MAHOGANY = new ResourceLocation(MODID_TFC, "mahogany");
  public static final ResourceLocation MANGROVE = new ResourceLocation(MODID_TFC, "mangrove");
  public static final ResourceLocation MARBLEWOOD = new ResourceLocation(MODID_TFC, "marblewood");
  public static final ResourceLocation MESSMATE = new ResourceLocation(MODID_TFC, "messmate");
  public static final ResourceLocation MOUNTAIN_ASH = new ResourceLocation(MODID_TFC, "mountain_ash");
  public static final ResourceLocation NORDMANN_FIR = new ResourceLocation(MODID_TFC, "nordmann_fir");
  public static final ResourceLocation NORWAY_SPRUCE = new ResourceLocation(MODID_TFC, "norway_spruce");
  public static final ResourceLocation PINK_CHERRY = new ResourceLocation(MODID_TFC, "pink_cherry");
  public static final ResourceLocation PINK_IVORY = new ResourceLocation(MODID_TFC, "pink_ivory");
  public static final ResourceLocation POPLAR = new ResourceLocation(MODID_TFC, "poplar");
  public static final ResourceLocation PURPLEHEART = new ResourceLocation(MODID_TFC, "purpleheart");
  public static final ResourceLocation RED_CEDAR = new ResourceLocation(MODID_TFC, "red_cedar");
  public static final ResourceLocation RED_ELM = new ResourceLocation(MODID_TFC, "red_elm");
  public static final ResourceLocation REDWOOD = new ResourceLocation(MODID_TFC, "redwood");
  public static final ResourceLocation ROWAN = new ResourceLocation(MODID_TFC, "rowan");
  public static final ResourceLocation RUBBER_FIG = new ResourceLocation(MODID_TFC, "rubber_fig");
  public static final ResourceLocation SWEETGUM = new ResourceLocation(MODID_TFC, "sweetgum");
  public static final ResourceLocation SYZYGIUM = new ResourceLocation(MODID_TFC, "syzygium");
  public static final ResourceLocation TEAK = new ResourceLocation(MODID_TFC, "teak");
  public static final ResourceLocation WALNUT = new ResourceLocation(MODID_TFC, "walnut");
  public static final ResourceLocation WENGE = new ResourceLocation(MODID_TFC, "wenge");
  public static final ResourceLocation WHITE_CHERRY = new ResourceLocation(MODID_TFC, "white_cherry");
  public static final ResourceLocation WHITE_ELM = new ResourceLocation(MODID_TFC, "white_elm");
  public static final ResourceLocation WHITEBEAM = new ResourceLocation(MODID_TFC, "whitebeam");
  public static final ResourceLocation YELLOW_MERANTI = new ResourceLocation(MODID_TFC, "yellow_meranti");
  public static final ResourceLocation YEW = new ResourceLocation(MODID_TFC, "yew");
  public static final ResourceLocation ZEBRAWOOD = new ResourceLocation(MODID_TFC, "zebrawood");

  /**
   * Simple ITreeGen instances.
   */
  public static final ITreeGenerator GEN_NORMAL = new GeneratorTreeNormal(1, 3);
  public static final ITreeGenerator GEN_MEDIUM = new GeneratorTreeNormal(2, 2);
  public static final ITreeGenerator GEN_TALL = new GeneratorTreeNormal(3, 3);
  public static final ITreeGenerator GEN_CONIFER = new GeneratorTreeVariants(false, 7);
  public static final ITreeGenerator GEN_TROPICAL = new GeneratorTreeVariants(true, 7);
  public static final ITreeGenerator GEN_WILLOW = new GeneratorTreeWillow();
  public static final ITreeGenerator GEN_ACACIA = new GeneratorTreeAcacia();
  public static final ITreeGenerator GEN_KAPOK = new GeneratorTreeKapok();
  public static final ITreeGenerator GEN_SEQUOIA = new GeneratorTreeSequoia();
  public static final ITreeGenerator GEN_KAPOK_COMPOSITE = new GeneratorTreeComposite().add(0.4f, GEN_TALL).add(0.6f, GEN_KAPOK);
  public static final ITreeGenerator GEN_BUSHES = new GeneratorTreeBushes();
  // Custom Tree Models
  public static final ITreeGenerator GEN_ASPEN = new GeneratorTreeVariants(true, 54);
  public static final ITreeGenerator GEN_AFRICAN_PADAUK = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_ALDER = new GeneratorTreeVariants(true, 30);
  public static final ITreeGenerator GEN_ANGELIM = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_BALD_CYPRESS = new GeneratorTreeVariants(true, 13);
  public static final ITreeGenerator GEN_BAOBAB = new GeneratorTreeVariants(true, 2);
  public static final ITreeGenerator GEN_BEECH = new GeneratorTreeVariants(true, 33);
  public static final ITreeGenerator GEN_BLACK_WALNUT = new GeneratorTreeVariants(true, 45);
  //public static final ITreeGen GEN_BOX = new GeneratorTreeVariants(true, 9);			// Tall Tree Model
  public static final ITreeGenerator GEN_BRAZILWOOD = new GeneratorTreeVariants(true, 14);
  public static final ITreeGenerator GEN_BUTTERNUT = new GeneratorTreeVariants(true, 45);
  public static final ITreeGenerator GEN_COCOBOLO = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_CYPRESS = new GeneratorTreeVariants(true, 5);
  public static final ITreeGenerator GEN_EBONY = new GeneratorTreeVariants(true, 20);
  public static final ITreeGenerator GEN_EUCALYPTUS = new GeneratorTreeVariants(true, 48);
  public static final ITreeGenerator GEN_EUROPEAN_OAK = new GeneratorTreeVariants(true, 45);
  public static final ITreeGenerator GEN_FEVER = new GeneratorTreeVariants(true, 13);
  public static final ITreeGenerator GEN_FRUITWOOD = new GeneratorTreeVariants(true, 3);
  //public static final ITreeGen GEN_GIGANTEUM = new GeneratorTreeVariants(true, 10);		// Redwood
  public static final ITreeGenerator GEN_GINKGO = new GeneratorTreeVariants(true, 20);
  public static final ITreeGenerator GEN_GREENHEART = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_HAWTHORN = new GeneratorTreeVariants(true, 15);
  public static final ITreeGenerator GEN_HAZEL = new GeneratorTreeVariants(true, 45);
  public static final ITreeGenerator GEN_HEMLOCK = new GeneratorTreeVariants(true, 16);
  public static final ITreeGenerator GEN_HOLLY = new GeneratorTreeVariants(true, 16);
  public static final ITreeGenerator GEN_HORNBEAM = new GeneratorTreeVariants(true, 35);
  public static final ITreeGenerator GEN_IPE = new GeneratorTreeVariants(true, 66);
  public static final ITreeGenerator GEN_IROKO = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_IRONWOOD = new GeneratorTreeVariants(true, 16);
  public static final ITreeGenerator GEN_JACARANDA = new GeneratorTreeVariants(true, 30);
  public static final ITreeGenerator GEN_JOSHUA_TREE = new GeneratorTreeVariants(true, 1);
  public static final ITreeGenerator GEN_JUNIPER = new GeneratorTreeVariants(true, 17);
  public static final ITreeGenerator GEN_KAURI = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_LARCH = new GeneratorTreeVariants(true, 10);
  public static final ITreeGenerator GEN_LIMBA = new GeneratorTreeVariants(true, 13);
  public static final ITreeGenerator GEN_LOCUST = new GeneratorTreeVariants(true, 15);
  public static final ITreeGenerator GEN_LOGWOOD = new GeneratorTreeVariants(true, 10);
  public static final ITreeGenerator GEN_MACLURA = new GeneratorTreeVariants(true, 15);
  public static final ITreeGenerator GEN_MAHOE = new GeneratorTreeVariants(true, 15);
  public static final ITreeGenerator GEN_MAHOGANY = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_MANGROVE = new GeneratorTreeVariants(true, 13);
  public static final ITreeGenerator GEN_MARBLEWOOD = new GeneratorTreeVariants(true, 24);
  public static final ITreeGenerator GEN_MESSMATE = new GeneratorTreeVariants(true, 18);
  public static final ITreeGenerator GEN_MOUNTAIN_ASH = new GeneratorTreeVariants(true, 20);
  public static final ITreeGenerator GEN_NORDMANN_FIR = new GeneratorTreeVariants(true, 26);
  public static final ITreeGenerator GEN_NORWAY_SPRUCE = new GeneratorTreeVariants(true, 16);
  public static final ITreeGenerator GEN_PINK_CHERRY = new GeneratorTreeVariants(true, 45);
  public static final ITreeGenerator GEN_PINK_IVORY = new GeneratorTreeVariants(true, 19);
  public static final ITreeGenerator GEN_POPLAR = new GeneratorTreeVariants(true, 51);
  public static final ITreeGenerator GEN_PURPLEHEART = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_RED_CEDAR = new GeneratorTreeVariants(true, 25);
  public static final ITreeGenerator GEN_RED_ELM = new GeneratorTreeVariants(true, 60);
  public static final ITreeGenerator GEN_REDWOOD = new GeneratorTreeVariants(true, 22);
  public static final ITreeGenerator GEN_ROWAN = new GeneratorTreeVariants(true, 18);
  public static final ITreeGenerator GEN_RUBBER_FIG = new GeneratorTreeVariants(true, 9);
  public static final ITreeGenerator GEN_SWEETGUM = new GeneratorTreeVariants(true, 60);
  public static final ITreeGenerator GEN_SYZYGIUM = new GeneratorTreeVariants(true, 14);
  public static final ITreeGenerator GEN_TEAK = new GeneratorTreeVariants(true, 13);
  public static final ITreeGenerator GEN_WALNUT = new GeneratorTreeVariants(true, 45);
  public static final ITreeGenerator GEN_WENGE = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_WHITE_CHERRY = new GeneratorTreeVariants(true, 45);
  public static final ITreeGenerator GEN_WHITE_ELM = new GeneratorTreeVariants(true, 60);
  public static final ITreeGenerator GEN_WHITEBEAM = new GeneratorTreeVariants(true, 42);
  public static final ITreeGenerator GEN_YELLOW_MERANTI = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_YEW = new GeneratorTreeVariants(true, 19);
  public static final ITreeGenerator GEN_ZEBRAWOOD = new GeneratorTreeVariants(true, 34);
  public static final ITreeGenerator GEN_TALL_TFC = new GeneratorTreeNormal(3, 3);
  public static final ITreeGenerator GEN_NORMAL_2 = new GeneratorTreeRandom(1, 3, 3);
  public static final ITreeGenerator GEN_MEDIUM_2 = new GeneratorTreeRandom(2, 2, 3);
  public static final ITreeGenerator GEN_TALL_2 = new GeneratorTreeRandom(3, 3, 3);
  public static final ITreeGenerator GEN_TALL_SINGLE = new GeneratorTreeRandom(3, 3, 1);
  public static final ITreeGenerator GEN_CASSIA_CINNAMON = new GeneratorTreeVariants(true, 6);
  public static final ITreeGenerator GEN_CEYLON_CINNAMON = new GeneratorTreeVariants(true, 6);
  public static final ITreeGenerator GEN_ARROW_BAMBOO = new GeneratorTreeVariants(true, 4);    // Pseudosasa japonica
  public static final ITreeGenerator GEN_BLACK_BAMBOO = new GeneratorTreeVariants(true, 4); // Phyllostachys nigra
  public static final ITreeGenerator GEN_BLUE_BAMBOO = new GeneratorTreeVariants(true, 4); // Himalayacalamus hookerianus
  public static final ITreeGenerator GEN_DRAGON_BAMBOO = new GeneratorTreeVariants(true, 4); // Dendrocalamus giganteus
  public static final ITreeGenerator GEN_GOLDEN_BAMBOO = new GeneratorTreeVariants(true, 4); // Alphonse Karr
  public static final ITreeGenerator GEN_NARROW_LEAF_BAMBOO = new GeneratorTreeVariants(true, 4); // Guadua angustifolia
  public static final ITreeGenerator GEN_RED_BAMBOO = new GeneratorTreeVariants(true, 4); // Fargesia nitida Jiuzhaigou
  public static final ITreeGenerator GEN_TEMPLE_BAMBOO = new GeneratorTreeVariants(true, 4); // Semiarundinaria fastuosa
  public static final ITreeGenerator GEN_THORNY_BAMBOO = new GeneratorTreeVariants(true, 4); // Chimonobambusa pachystachys
  public static final ITreeGenerator GEN_TIMBER_BAMBOO = new GeneratorTreeVariants(true, 4); // Phyllostachys vivax
  public static final ITreeGenerator GEN_TINWA_BAMBOO = new GeneratorTreeVariants(true, 4); // Cephalostachyum pergracile
  public static final ITreeGenerator GEN_WEAVERS_BAMBOO = new GeneratorTreeVariants(true, 4); // Bambusa textilis


  @SubscribeEvent
  public static void onPreRegisterRockCategory(TFCRegistryEvent.RegisterPreBlock<Tree> event) {
    event.getRegistry().registerAll(
      new Tree.Builder(ACACIA, 30f, 210f, 19f, 31f, GEN_ACACIA).setHeight(12)
                                                               .setGrowthTime(11)
                                                               .setDensity(0.1f, 0.6f)
                                                               .setBurnInfo(650f, 1000)
                                                               .build(),
      new Tree.Builder(ASH, 60f, 140f, -6f, 12f, GEN_NORMAL).setBurnInfo(696f, 1250).build(),
      new Tree.Builder(ASPEN, 10f, 80f, -10f, 16f, GEN_MEDIUM).setRadius(1)
                                                              .setGrowthTime(8)
                                                              .setBurnInfo(611f, 1000)
                                                              .build(),
      new Tree.Builder(BIRCH, 20f, 180f, -15f, 7f, GEN_TALL).setRadius(1)
                                                            .setTannin()
                                                            .setBurnInfo(652f, 1750)
                                                            .build(),
      new Tree.Builder(BLACKWOOD, 0f, 120f, 4f, 33f, GEN_MEDIUM).setHeight(12)
                                                                .setGrowthTime(8)
                                                                .setBurnInfo(720f, 1750)
                                                                .build(),
      new Tree.Builder(CHESTNUT, 160f, 320f, 11f, 35f, GEN_NORMAL).setTannin()
                                                                  .setBurnInfo(651f, 1500)
                                                                  .build(),
      new Tree.Builder(DOUGLAS_FIR, 280f, 480f, -2f, 14f, GEN_TALL).setDominance(5.2f)
                                                                   .setHeight(16)
                                                                   .setBushes()
                                                                   .setTannin()
                                                                   .setDensity(0.25f, 2f)
                                                                   .setBurnInfo(707f, 1500)
                                                                   .build(),
      new Tree.Builder(HICKORY, 80f, 250f, 7f, 29f, GEN_TALL).setGrowthTime(10)
                                                             .setTannin()
                                                             .setBurnInfo(762f, 2000)
                                                             .build(),
      new Tree.Builder(KAPOK, 210f, 500f, 15f, 35f, GEN_KAPOK_COMPOSITE).setDominance(8.5f)
                                                                        .setRadius(3)
                                                                        .setHeight(24)
                                                                        .setDecayDist(6)
                                                                        .setGrowthTime(18)
                                                                        .setBushes()
                                                                        .setDensity(0.6f, 2f)
                                                                        .setBurnInfo(645f, 1000)
                                                                        .build(),
      new Tree.Builder(MAPLE, 140f, 360f, 3f, 20f, GEN_MEDIUM).setDominance(6.3f)
                                                              .setRadius(1)
                                                              .setTannin()
                                                              .setBurnInfo(745f, 2000)
                                                              .build(),
      new Tree.Builder(OAK, 180f, 430f, -8f, 12f, GEN_TALL).setHeight(16)
                                                           .setGrowthTime(10)
                                                           .setTannin()
                                                           .setBurnInfo(728f, 2250)
                                                           .build(),
      new Tree.Builder(PALM, 280f, 500f, 16f, 35f, GEN_TROPICAL).setDecayDist(6)
                                                                .setBurnInfo(730f, 1250)
                                                                .build(),
      new Tree.Builder(PINE, 60f, 250f, -15f, 7f, GEN_CONIFER).setRadius(1)
                                                              .setConifer()
                                                              .setDensity(0.1f, 0.8f)
                                                              .setBurnInfo(627f, 1250)
                                                              .build(),
      new Tree.Builder(ROSEWOOD, 10f, 190f, 8f, 18f, GEN_MEDIUM).setHeight(12)
                                                                .setGrowthTime(8)
                                                                .setBurnInfo(640f, 1500)
                                                                .build(),
      new Tree.Builder(SEQUOIA, 250f, 420f, -5f, 12f, GEN_SEQUOIA).setRadius(3)
                                                                  .setHeight(24)
                                                                  .setDecayDist(6)
                                                                  .setGrowthTime(18)
                                                                  .setConifer()
                                                                  .setBushes()
                                                                  .setTannin()
                                                                  .setDensity(0.4f, 0.9f)
                                                                  .setBurnInfo(612f, 1750)
                                                                  .build(),
      new Tree.Builder(SPRUCE, 120f, 380f, -11f, 6f, GEN_CONIFER).setRadius(1)
                                                                 .setConifer()
                                                                 .setDensity(0.1f, 0.8f)
                                                                 .setBurnInfo(608f, 1500)
                                                                 .build(),
      new Tree.Builder(SYCAMORE, 120f, 290f, 17f, 33f, GEN_MEDIUM).setGrowthTime(8)
                                                                  .setBushes()
                                                                  .setDensity(0.25f, 2f)
                                                                  .setBurnInfo(653f, 1750)
                                                                  .build(),
      new Tree.Builder(WHITE_CEDAR, 10f, 240f, -8f, 17f, GEN_TALL).setHeight(16)
                                                                  .setBurnInfo(625f, 1500)
                                                                  .build(),
      new Tree.Builder(WILLOW, 230f, 400f, 15f, 32f, GEN_WILLOW).setRadius(1)
                                                                .setGrowthTime(11)
                                                                .setBushes()
                                                                .setDensity(0.7f, 2f)
                                                                .setBurnInfo(603f, 1000)
                                                                .build(),
      new Tree.Builder(HEVEA, 140f, 350f, 7f, 27f, GEN_TALL).setDensity(0.1f, 0.6f)
                                                            .setRadius(2).setGrowthTime(10).setBurnInfo(762f, 2000).build()
    );
  }
}
