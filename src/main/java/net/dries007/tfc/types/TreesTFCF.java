package net.dries007.tfc.types;

import su.terrafirmagreg.modules.wood.object.generator.GeneratorTreeSequoia;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Tree;

import static net.dries007.tfc.types.DefaultTrees.GEN_MEDIUM;
import static net.dries007.tfc.types.DefaultTrees.GEN_NORMAL;
import static net.dries007.tfc.types.DefaultTrees.GEN_TALL;
import static su.terrafirmagreg.api.data.Reference.MODID_TFC;
import static su.terrafirmagreg.api.data.Reference.MODID_TFCF;

@SuppressWarnings({"unused", "WeakerAccess"})
@Mod.EventBusSubscriber(modid = MODID_TFCF)
public final class TreesTFCF {


  // Seasonal & Fruit-bearing Trees
  public static final Tree ASH_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "ash"), 60f, 140f, -6f, 12f, GEN_NORMAL).setBushes()
                                                                                                                               .setBurnInfo(696f, 1250)
                                                                                                                               .build();
  public static final Tree ASPEN_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "aspen"), 70f, 280f, -10f, 16f, DefaultTrees.GEN_ASPEN).setGrowthTime(8)
                                                                                                                                                .setDensity(0.25f, 1f)
                                                                                                                                                .setBurnInfo(611f, 1000)
                                                                                                                                                .build();
  public static final Tree BIRCH_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "birch"), 20f, 180f, -15f, 7f, GEN_TALL).setRadius(1)
                                                                                                                                 .setTannin()
                                                                                                                                 .setBurnInfo(652f, 1750)
                                                                                                                                 .build();
  public static final Tree CHESTNUT_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "chestnut"), 160f, 320f, 11f, 35f,
                                                            GEN_NORMAL).setTannin()
                                                                       .setBushes()
                                                                       .setBurnInfo(651f, 1500)
                                                                       .build();
  public static final Tree HICKORY_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "hickory"), 80f, 250f, 7f, 29f, GEN_TALL).setGrowthTime(
                                                                                                                                      10)
                                                                                                                                    .setBushes()
                                                                                                                                    .setTannin()
                                                                                                                                    .setBurnInfo(762f, 2000)
                                                                                                                                    .build();
  public static final Tree MAPLE_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "maple"), 140f, 360f, 3f, 20f, GEN_MEDIUM).setDominance(
                                                                                                                                     6.3f)
                                                                                                                                   .setRadius(1)
                                                                                                                                   .setTannin()
                                                                                                                                   .setBurnInfo(745f, 2000)
                                                                                                                                   .build();
  public static final Tree OAK_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "oak"), 180f, 430f, -8f, 12f, GEN_TALL).setHeight(16)
                                                                                                                              .setGrowthTime(10)
                                                                                                                              .setBushes()
                                                                                                                              .setTannin()
                                                                                                                              .setBurnInfo(728f, 2250)
                                                                                                                              .build();
  public static final Tree SYCAMORE_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "sycamore"), 120f, 290f, 17f, 33f,
                                                            GEN_MEDIUM).setGrowthTime(8)
                                                                       .setBushes()
                                                                       .setDensity(0.25f, 2f)
                                                                       .setBurnInfo(653f, 1750)
                                                                       .build();
  public static final Tree WHITE_CEDAR_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "white_cedar"), 10f, 240f, -8f, 17f,
                                                               DefaultTrees.GEN_TALL_SINGLE).setHeight(16)
                                                                                            .setBushes()
                                                                                            .setBurnInfo(625f, 1500)
                                                                                            .build();

  public static final Tree BAOBAB_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "baobab"), 10f, 150f, 21f, 40f, DefaultTrees.GEN_BAOBAB).setDecayDist(
                                                                                                                                                    6)
                                                                                                                                                  .setGrowthTime(20)
                                                                                                                                                  .setDensity(0.1f, 0.3f)
                                                                                                                                                  .setBurnInfo(478f, 1000)
                                                                                                                                                  .build();
  public static final Tree EUCALYPTUS_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "eucalyptus"), 120f, 300f, 18f, 39f,
                                                              DefaultTrees.GEN_EUCALYPTUS).setGrowthTime(8)
                                                                                          .setBushes()
                                                                                          .setDensity(0.35f, 2f)
                                                                                          .setBurnInfo(705f, 1000)
                                                                                          .build();
  public static final Tree HAWTHORN_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "hawthorn"), 180f, 400f, -8f, 14f,
                                                            DefaultTrees.GEN_HAWTHORN).setGrowthTime(8)
                                                                                      .setDensity(0.25f, 1f)
                                                                                      .setBurnInfo(683f, 1500)
                                                                                      .build();
  public static final Tree MACLURA_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "maclura"), 140f, 400f, -1f, 17f,
                                                           DefaultTrees.GEN_MACLURA).setGrowthTime(8)
                                                                                    .setBushes()
                                                                                    .setDensity(0.25f, 1f)
                                                                                    .setBurnInfo(773f, 1000)
                                                                                    .build();
  public static final Tree MAHOGANY_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "mahogany"), 270f, 500f, 23f, 42f,
                                                            DefaultTrees.GEN_MAHOGANY).setRadius(1)
                                                                                      .setDecayDist(6)
                                                                                      .setGrowthTime(18)
                                                                                      .setBushes()
                                                                                      .setDensity(0.5f, 2f)
                                                                                      .setBurnInfo(773f, 1000)
                                                                                      .build();
  public static final Tree PINK_IVORY_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "pink_ivory"), 210f, 500f, 18f, 31f,
                                                              DefaultTrees.GEN_PINK_IVORY).setDecayDist(6)
                                                                                          .setGrowthTime(18)
                                                                                          .setBushes()
                                                                                          .setDensity(0.2f, 2f)
                                                                                          .setBurnInfo(773f, 1000)
                                                                                          .build();
  public static final Tree RED_CEDAR_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "red_cedar"), 10f, 240f, -8f, 17f,
                                                             DefaultTrees.GEN_RED_CEDAR).setDecayDist(6)
                                                                                        .setGrowthTime(18)
                                                                                        .setBushes()
                                                                                        .setConifer()
                                                                                        .setBushes()
                                                                                        .setTannin()
                                                                                        .setDensity(0.4f, 2f)
                                                                                        .setBurnInfo(618f, 1750)
                                                                                        .build();
  public static final Tree ROWAN_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "rowan"), 180f, 400f, -15f, 8f, DefaultTrees.GEN_ROWAN).setGrowthTime(8)
                                                                                                                                                .setDensity(0.25f, 1f)
                                                                                                                                                .setBurnInfo(645f, 2000)
                                                                                                                                                .build();
  public static final Tree SYZYGIUM_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "syzygium"), 140f, 360f, 13f, 35f,
                                                            DefaultTrees.GEN_SYZYGIUM).setDecayDist(6)
                                                                                      .setGrowthTime(16)
                                                                                      .setBushes()
                                                                                      .setTannin()
                                                                                      .setDensity(0.2f, 1f)
                                                                                      .setBurnInfo(745f, 2000)
                                                                                      .build();
  public static final Tree YEW_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "yew"), 180f, 350f, -15f, 11f, DefaultTrees.GEN_YEW).setGrowthTime(10)
                                                                                                                                           .setBushes()
                                                                                                                                           .setBurnInfo(813f, 2150)
                                                                                                                                           .build();
  public static final Tree JACARANDA_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "jacaranda"), 180f, 300f, 10f, 34f,
                                                             DefaultTrees.GEN_JACARANDA).setGrowthTime(8)
                                                                                        .setDensity(0.25f, 2f)
                                                                                        .setBurnInfo(795f, 1250)
                                                                                        .build();
  public static final Tree JOSHUA_TREE_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "joshua_tree"), 20f, 150f, 15f, 40f,
                                                               DefaultTrees.GEN_JOSHUA_TREE).setDominance(0f)
                                                                                            .setDensity(0f, 0f)
                                                                                            .setGrowthTime(8)
                                                                                            .setConifer()
                                                                                            .setBurnInfo(696f, 1250)
                                                                                            .build();
  public static final Tree JUNIPER_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "juniper"), 80f, 350f, -8f, 20f,
                                                           DefaultTrees.GEN_JUNIPER).setGrowthTime(8)
                                                                                    .setConifer()
                                                                                    .setDensity(0.25f, 0.75f)
                                                                                    .setTannin()
                                                                                    .setBurnInfo(632f, 1750)
                                                                                    .build();
  public static final Tree IPE_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "ipe"), 150f, 350f, 15f, 32f, DefaultTrees.GEN_IPE).setDecayDist(6)
                                                                                                                                          .setGrowthTime(18)
                                                                                                                                          .setBushes()
                                                                                                                                          .setDensity(0.2f, 2f)
                                                                                                                                          .setBurnInfo(785f, 1200)
                                                                                                                                          .build();
  public static final Tree PINK_CHERRY_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "pink_cherry"), 180f, 300f, 0f, 20f,
                                                               DefaultTrees.GEN_PINK_CHERRY).setGrowthTime(8)
                                                                                            .setDensity(0.25f, 2f)
                                                                                            .setBurnInfo(795f, 1250)
                                                                                            .build();
  public static final Tree WHITE_CHERRY_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "white_cherry"), 180f, 300f, 0f, 20f,
                                                                DefaultTrees.GEN_WHITE_CHERRY).setGrowthTime(8)
                                                                                              .setDensity(0.25f, 2f)
                                                                                              .setBurnInfo(795f, 1250)
                                                                                              .build();
  public static final Tree SWEETGUM_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "sweetgum"), 140f, 360f, -2f, 18f,
                                                            DefaultTrees.GEN_SWEETGUM).setDecayDist(6)
                                                                                      .setGrowthTime(16)
                                                                                      .setBushes()
                                                                                      .setTannin()
                                                                                      .setDensity(0.2f, 1f)
                                                                                      .setBurnInfo(745f, 2000)
                                                                                      .build();
  public static final Tree LARCH_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "larch"), 60f, 400f, -12f, 15f, DefaultTrees.GEN_LARCH).setGrowthTime(8)
                                                                                                                                                .setConifer()
                                                                                                                                                .setDensity(0.25f, 1f)
                                                                                                                                                .setBurnInfo(632f, 1250)
                                                                                                                                                .build();
  public static final Tree ALDER_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "alder"), 60f, 400f, -4f, 13f, DefaultTrees.GEN_ALDER).setGrowthTime(8)
                                                                                                                                               .setBushes()
                                                                                                                                               .setDensity(0.25f, 2f)
                                                                                                                                               .setBurnInfo(601f, 1000)
                                                                                                                                               .build();
  public static final Tree BEECH_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "beech"), 220f, 300f, -15f, 9f, DefaultTrees.GEN_BEECH).setGrowthTime(8)
                                                                                                                                                .setBushes()
                                                                                                                                                .setTannin()
                                                                                                                                                .setDensity(0.25f, 1f)
                                                                                                                                                .setBurnInfo(703f, 1750)
                                                                                                                                                .build();
  public static final Tree BLACK_WALNUT_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "black_walnut"), 180f, 300f, -10f, 16f,
                                                                DefaultTrees.GEN_BLACK_WALNUT).setGrowthTime(9)
                                                                                              .setBushes()
                                                                                              .setBurnInfo(758f, 1800)
                                                                                              .build();
  public static final Tree BUTTERNUT_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "butternut"), 180f, 320f, -8f, 17f,
                                                             DefaultTrees.GEN_BUTTERNUT).setGrowthTime(9)
                                                                                        .setBushes()
                                                                                        .setBurnInfo(758f, 1800)
                                                                                        .build();
  public static final Tree EUROPEAN_OAK_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "european_oak"), 140f, 430f, -8f, 15f,
                                                                DefaultTrees.GEN_EUROPEAN_OAK).setGrowthTime(10)
                                                                                              .setBushes()
                                                                                              .setTannin()
                                                                                              .setBurnInfo(728f, 2250)
                                                                                              .build();
  public static final Tree GINKGO_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "ginkgo"), 240f, 550f, 6f, 20f, DefaultTrees.GEN_GINKGO).setGrowthTime(
                                                                                                                                                    8)
                                                                                                                                                  .setDensity(0.25f, 1f)
                                                                                                                                                  .setBurnInfo(710f, 1000)
                                                                                                                                                  .build();
  public static final Tree HAZEL_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "hazel"), 60f, 400f, -10f, 14f, DefaultTrees.GEN_HAZEL).setGrowthTime(8)
                                                                                                                                                .setBushes()
                                                                                                                                                .setDensity(0.25f, 1f)
                                                                                                                                                .setBurnInfo(683f, 1500)
                                                                                                                                                .build();
  public static final Tree HORNBEAM_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "hornbeam"), 140f, 430f, -10f, 12f,
                                                            DefaultTrees.GEN_HORNBEAM).setGrowthTime(10)
                                                                                      .setBushes()
                                                                                      .setTannin()
                                                                                      .setBurnInfo(728f, 2250)
                                                                                      .build();
  public static final Tree LOCUST_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "locust"), 120f, 290f, -6f, 15f,
                                                          DefaultTrees.GEN_LOCUST).setGrowthTime(8)
                                                                                  .setBushes()
                                                                                  .setBurnInfo(653f, 1750)
                                                                                  .build();
  public static final Tree POPLAR_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "poplar"), 140f, 400f, -7f, 14f,
                                                          DefaultTrees.GEN_POPLAR).setGrowthTime(8)
                                                                                  .setDensity(0.25f, 1f)
                                                                                  .setBurnInfo(609f, 1000)
                                                                                  .build();
  public static final Tree RED_ELM_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "red_elm"), 60f, 290f, 2f, 20f,
                                                           DefaultTrees.GEN_RED_ELM).setDecayDist(6)
                                                                                    .setGrowthTime(18)
                                                                                    .setBushes()
                                                                                    .setTannin()
                                                                                    .setDensity(0.4f, 2f)
                                                                                    .setBurnInfo(618f, 1750)
                                                                                    .build();
  public static final Tree WALNUT_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "walnut"), 180f, 300f, -10f, 16f,
                                                          DefaultTrees.GEN_WALNUT).setGrowthTime(9)
                                                                                  .setBushes()
                                                                                  .setBurnInfo(758f, 1800)
                                                                                  .build();
  public static final Tree WHITE_ELM_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "white_elm"), 60f, 290f, 2f, 20f,
                                                             DefaultTrees.GEN_WHITE_ELM).setGrowthTime(8)
                                                                                        .setBushes()
                                                                                        .setTannin()
                                                                                        .setBurnInfo(653f, 1750)
                                                                                        .build();
  public static final Tree WHITEBEAM_TREE = new Tree.Builder(new ResourceLocation(MODID_TFC, "whitebeam"), 140f, 430f, -10f, 12f,
                                                             DefaultTrees.GEN_WHITEBEAM).setGrowthTime(10)
                                                                                        .setBushes()
                                                                                        .setTannin()
                                                                                        .setBurnInfo(728f, 1750)
                                                                                        .build();

  public static final Tree CASSIA_CINNAMON_TREE = new Tree(new ResourceLocation(MODID_TFC, "cassia_cinnamon"), DefaultTrees.GEN_CASSIA_CINNAMON, 20, 35, 250,
                                                           400, 0.1f, 1, 2, 4, 15, 6, false, null, false, 15, 710f, 1000);
  public static final Tree CEYLON_CINNAMON_TREE = new Tree(new ResourceLocation(MODID_TFC, "ceylon_cinnamon"), DefaultTrees.GEN_CEYLON_CINNAMON, 20, 35, 250,
                                                           400, 0.1f, 1, 2, 4, 15, 6, false, null, false, 15, 710f, 1000);


  public static final Tree CINNAMON_TREE = new Tree(new ResourceLocation(MODID_TFC, "cinnamon"), new GeneratorTreeSequoia(), 28, 35, 280, 400,
                                                    0f, 1f, 0, 4, 15, 4, false, null, false, 15, 0, 0);
  public static final Tree ARROW_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "arrow_bamboo"), DefaultTrees.GEN_ARROW_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4,
                                                   15, 6, false, null, false, 10, 400f, 800);
  public static final Tree BLACK_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "black_bamboo"), DefaultTrees.GEN_BLACK_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4,
                                                   15, 6, false, null, false, 10, 400f, 800);
  public static final Tree BLUE_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "blue_bamboo"), DefaultTrees.GEN_BLUE_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15,
                                                  6, false, null, false, 10, 400f, 800);
  public static final Tree DRAGON_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "dragon_bamboo"), DefaultTrees.GEN_DRAGON_BAMBOO, 24, 35, 240, 420, 1, 2, 1,
                                                    4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree GOLDEN_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "golden_bamboo"), DefaultTrees.GEN_GOLDEN_BAMBOO, 24, 35, 240, 420, 1, 2, 1,
                                                    4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree NARROW_LEAF_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "narrow_leaf_bamboo"), DefaultTrees.GEN_NARROW_LEAF_BAMBOO, 24, 35, 240,
                                                         420, 1, 2, 1, 4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree RED_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "red_bamboo"), DefaultTrees.GEN_RED_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4, 15, 6,
                                                 false, null, false, 10, 400f, 800);
  public static final Tree TEMPLE_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "temple_bamboo"), DefaultTrees.GEN_TEMPLE_BAMBOO, 24, 35, 240, 420, 1, 2, 1,
                                                    4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree THORNY_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "thorny_bamboo"), DefaultTrees.GEN_THORNY_BAMBOO, 24, 35, 240, 420, 1, 2, 1,
                                                    4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree TIMBER_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "timber_bamboo"), DefaultTrees.GEN_TIMBER_BAMBOO, 24, 35, 240, 420, 1, 2, 1,
                                                    4, 15, 6, false, null, false, 10, 400f, 800);
  public static final Tree TINWA_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "tinwa_bamboo"), DefaultTrees.GEN_TINWA_BAMBOO, 24, 35, 240, 420, 1, 2, 1, 4,
                                                   15, 6, false, null, false, 10, 400f, 800);
  public static final Tree WEAVERS_BAMBOO = new Tree(new ResourceLocation(MODID_TFC, "weavers_bamboo"), DefaultTrees.GEN_WEAVERS_BAMBOO, 24, 35, 240, 420, 1, 2,
                                                     1, 4, 15, 6, false, null, false, 10, 400f, 800);

  @SubscribeEvent
  public static void onPreRegisterTrees(TFCRegistryEvent.RegisterPreBlock<Tree> event) {
    // Other Trees
    event.getRegistry()
         .registerAll(
           new Tree.Builder(new ResourceLocation(MODID_TFC, "african_padauk"), 275f, 500f, 22f, 50f, DefaultTrees.GEN_AFRICAN_PADAUK).setRadius(1)
                                                                                                                                     .setDecayDist(6)
                                                                                                                                     .setGrowthTime(18)
                                                                                                                                     .setBushes()
                                                                                                                                     .setDensity(0.5f, 2f)
                                                                                                                                     .setBurnInfo(745f, 1500)
                                                                                                                                     .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "angelim"), 320f, 500f, 22f, 50f, DefaultTrees.GEN_ANGELIM).setRadius(1)
                                                                                                                                  .setDecayDist(6)
                                                                                                                                  .setGrowthTime(18)
                                                                                                                                  .setBushes()
                                                                                                                                  .setDensity(0.5f, 2f)
                                                                                                                                  .setBurnInfo(773f, 1200)
                                                                                                                                  .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "box"), 180f, 400f, -8f, 15f, DefaultTrees.GEN_TALL_TFC).setGrowthTime(8)
                                                                                                                               .setBushes()
                                                                                                                               .setDensity(0.25f, 1f)
                                                                                                                               .setBurnInfo(683f, 1500)
                                                                                                                               .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "brazilwood"), 290f, 550f, 14f, 37f, DefaultTrees.GEN_BRAZILWOOD).setGrowthTime(8)
                                                                                                                                        .setBushes()
                                                                                                                                        .setDensity(0.25f, 1f)
                                                                                                                                        .setBurnInfo(710f, 1000)
                                                                                                                                        .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "cocobolo"), 255f, 500f, 20f, 50f, DefaultTrees.GEN_COCOBOLO).setRadius(1)
                                                                                                                                    .setGrowthTime(8)
                                                                                                                                    .setBushes()
                                                                                                                                    .setDensity(0.5f, 2f)
                                                                                                                                    .setBurnInfo(773f, 1000)
                                                                                                                                    .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "ebony"), 180f, 320f, 19f, 38f, DefaultTrees.GEN_EBONY).setGrowthTime(8)
                                                                                                                              .setBushes()
                                                                                                                              .setBurnInfo(795f, 1000)
                                                                                                                              .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "fever"), 70f, 220f, 19f, 50f, DefaultTrees.GEN_FEVER).setGrowthTime(10)
                                                                                                                             .setBushes()
                                                                                                                             .setDensity(0.25f, 1f)
                                                                                                                             .setBurnInfo(590f, 1000)
                                                                                                                             .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "fruitwood"), 180f, 550f, 11f, 30f, DefaultTrees.GEN_FRUITWOOD).setDominance(0)
                                                                                                                                      .setGrowthTime(9)
                                                                                                                                      .setBushes()
                                                                                                                                      .setDensity(0f, 0f)
                                                                                                                                      .setBurnInfo(720f, 1000)
                                                                                                                                      .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "greenheart"), 310f, 500f, 23f, 50f, DefaultTrees.GEN_GREENHEART).setRadius(1)
                                                                                                                                        .setDecayDist(6)
                                                                                                                                        .setGrowthTime(18)
                                                                                                                                        .setBushes()
                                                                                                                                        .setDensity(0.5f, 2f)
                                                                                                                                        .setBurnInfo(793f, 1700)
                                                                                                                                        .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "holly"), 140f, 400f, -4f, 16f, DefaultTrees.GEN_HOLLY).setGrowthTime(8)
                                                                                                                              .setBushes()
                                                                                                                              .setDensity(0.25f, 1f)
                                                                                                                              .setBurnInfo(609f, 1000)
                                                                                                                              .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "iroko"), 300f, 500f, 21f, 50f, DefaultTrees.GEN_IROKO).setRadius(1)
                                                                                                                              .setDecayDist(6)
                                                                                                                              .setGrowthTime(18)
                                                                                                                              .setBushes()
                                                                                                                              .setDensity(0.5f, 2f)
                                                                                                                              .setBurnInfo(785f, 1200)
                                                                                                                              .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "ironwood"), 30f, 210f, 11f, 36f, DefaultTrees.GEN_IRONWOOD).setDecayDist(6)
                                                                                                                                   .setGrowthTime(11)
                                                                                                                                   .setBushes()
                                                                                                                                   .setDensity(0.1f, 0.6f)
                                                                                                                                   .setBurnInfo(694f, 1170)
                                                                                                                                   .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "kauri"), 330f, 500f, 23f, 50f, DefaultTrees.GEN_KAURI).setRadius(1)
                                                                                                                              .setGrowthTime(10)
                                                                                                                              .setBushes()
                                                                                                                              .setDensity(0.5f, 2f)
                                                                                                                              .setBurnInfo(730f, 1250)
                                                                                                                              .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "limba"), 290f, 550f, 14f, 37f, DefaultTrees.GEN_LIMBA).setGrowthTime(9)
                                                                                                                              .setBushes()
                                                                                                                              .setDensity(0.25f, 1f)
                                                                                                                              .setBurnInfo(710f, 1000)
                                                                                                                              .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "logwood"), 180f, 430f, 12f, 35f, DefaultTrees.GEN_LOGWOOD).setGrowthTime(8)
                                                                                                                                  .setBushes()
                                                                                                                                  .setDensity(0.25f, 1f)
                                                                                                                                  .setBurnInfo(695f, 1000)
                                                                                                                                  .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "mahoe"), 180f, 350f, 13f, 32f, DefaultTrees.GEN_MAHOE).setHeight(16)
                                                                                                                              .setGrowthTime(8)
                                                                                                                              .setBushes()
                                                                                                                              .setBurnInfo(783f, 1100)
                                                                                                                              .build());
    //event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "mangrove"), 200f, 500f, 15f, 40f, GEN_MANGROVE).setDominance(0f).setDensity(0f, 0f).setRadius(1).setGrowthTime(8).setBushes().setBurnInfo(783f, 1100).build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "marblewood"), 180f, 500f, 16f, 35f, DefaultTrees.GEN_MARBLEWOOD).setDecayDist(6)
                                                                                                                                        .setGrowthTime(18)
                                                                                                                                        .setBushes()
                                                                                                                                        .setDensity(0.2f, 2f)
                                                                                                                                        .setBurnInfo(837f, 1200)
                                                                                                                                        .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "messmate"), 120f, 270f, 2f, 27f, DefaultTrees.GEN_MESSMATE).setGrowthTime(10)
                                                                                                                                   .setBushes()
                                                                                                                                   .setDensity(0.2f, 2f)
                                                                                                                                   .setBurnInfo(696f, 1250)
                                                                                                                                   .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "mountain_ash"), 80f, 270f, 9f, 33f, DefaultTrees.GEN_MOUNTAIN_ASH).setGrowthTime(10)
                                                                                                                                          .setBushes()
                                                                                                                                          .setDensity(0.4f, 2f)
                                                                                                                                          .setBurnInfo(696f, 1250)
                                                                                                                                          .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "purpleheart"), 310f, 500f, 22f, 50f, DefaultTrees.GEN_PURPLEHEART).setRadius(1)
                                                                                                                                          .setDecayDist(6)
                                                                                                                                          .setGrowthTime(18)
                                                                                                                                          .setBushes()
                                                                                                                                          .setDensity(0.5f, 2f)
                                                                                                                                          .setBurnInfo(793f, 1700)
                                                                                                                                          .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "rubber_fig"), 210f, 550f, 16f, 35f, DefaultTrees.GEN_RUBBER_FIG).setDecayDist(6)
                                                                                                                                        .setGrowthTime(16)
                                                                                                                                        .setBushes()
                                                                                                                                        .setDensity(0.2f, 1f)
                                                                                                                                        .setBurnInfo(785f, 1440)
                                                                                                                                        .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "teak"), 180f, 430f, 17f, 35f, DefaultTrees.GEN_TEAK).setGrowthTime(8)
                                                                                                                            .setBushes()
                                                                                                                            .setDensity(0.25f, 1f)
                                                                                                                            .setBurnInfo(695f, 1000)
                                                                                                                            .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "wenge"), 255f, 500f, 20f, 50f, DefaultTrees.GEN_WENGE).setRadius(1)
                                                                                                                              .setGrowthTime(8)
                                                                                                                              .setBushes()
                                                                                                                              .setDensity(0.5f, 2f)
                                                                                                                              .setBurnInfo(773f, 1250)
                                                                                                                              .build());
    event.getRegistry()
         .registerAll(
           new Tree.Builder(new ResourceLocation(MODID_TFC, "yellow_meranti"), 260f, 500f, 21f, 50f, DefaultTrees.GEN_YELLOW_MERANTI).setRadius(1)
                                                                                                                                     .setDecayDist(6)
                                                                                                                                     .setGrowthTime(18)
                                                                                                                                     .setBushes()
                                                                                                                                     .setDensity(0.5f, 2f)
                                                                                                                                     .setBurnInfo(837f, 1200)
                                                                                                                                     .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "zebrawood"), 280f, 500f, 23f, 50f, DefaultTrees.GEN_ZEBRAWOOD).setRadius(1)
                                                                                                                                      .setDecayDist(6)
                                                                                                                                      .setGrowthTime(18)
                                                                                                                                      .setBushes()
                                                                                                                                      .setDensity(0.5f, 2f)
                                                                                                                                      .setBurnInfo(822f, 1570)
                                                                                                                                      .build());

    // Coniferous Trees
    //event.getRegistry().registerAll(new Tree.Builder(new ResourceLocation(TerraFirmaCraft.MOD_ID, "bald_cypress"), 180f, 500f, 10f, 38f, GEN_BALD_CYPRESS).setDominance(0f).setDensity(0f, 0f).setGrowthTime(8).setBushes().setConifer().setBurnInfo(770f, 1300).build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "cypress"), 140f, 350f, 4f, 33f, DefaultTrees.GEN_CYPRESS).setGrowthTime(8)
                                                                                                                                 .setBushes()
                                                                                                                                 .setConifer()
                                                                                                                                 .setBurnInfo(783f, 1100)
                                                                                                                                 .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "hemlock"), 140f, 400f, -9f, 10f, DefaultTrees.GEN_HEMLOCK).setGrowthTime(8)
                                                                                                                                  .setConifer()
                                                                                                                                  .setDensity(0.25f, 1f)
                                                                                                                                  .setBurnInfo(609f, 1000)
                                                                                                                                  .build());
    event.getRegistry()
         .registerAll(
           new Tree.Builder(new ResourceLocation(MODID_TFC, "nordmann_fir"), 100f, 380f, -16f, 7f, DefaultTrees.GEN_NORDMANN_FIR).setGrowthTime(8)
                                                                                                                                 .setConifer()
                                                                                                                                 .setDensity(0.1f, 0.9f)
                                                                                                                                 .setBurnInfo(628f, 1500)
                                                                                                                                 .build());
    event.getRegistry()
         .registerAll(
           new Tree.Builder(new ResourceLocation(MODID_TFC, "norway_spruce"), 100f, 380f, -20f, 5f, DefaultTrees.GEN_NORWAY_SPRUCE).setGrowthTime(8)
                                                                                                                                   .setConifer()
                                                                                                                                   .setDensity(0.1f, 0.9f)
                                                                                                                                   .setBurnInfo(628f, 1500)
                                                                                                                                   .build());
    event.getRegistry()
         .registerAll(new Tree.Builder(new ResourceLocation(MODID_TFC, "redwood"), 160f, 400f, 0f, 17f, DefaultTrees.GEN_REDWOOD).setDecayDist(6)
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
    //event.getRegistry().registerAll(JOSHUA_TREE_TREE);

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





