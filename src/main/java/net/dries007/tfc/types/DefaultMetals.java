package net.dries007.tfc.types;

import su.terrafirmagreg.api.data.ArmorMaterials;
import su.terrafirmagreg.api.data.ToolMaterials;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.recipes.AlloyRecipe;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;

import static net.dries007.tfc.api.types.Metal.Tier.TIER_I;
import static net.dries007.tfc.api.types.Metal.Tier.TIER_II;
import static net.dries007.tfc.api.types.Metal.Tier.TIER_III;
import static net.dries007.tfc.api.types.Metal.Tier.TIER_IV;
import static net.dries007.tfc.api.types.Metal.Tier.TIER_V;
import static net.dries007.tfc.api.types.Metal.Tier.TIER_VI;
import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

@SuppressWarnings("WeakerAccess")
@Mod.EventBusSubscriber(modid = TFC)
public final class DefaultMetals {

  /*
   * Metals
   */
  public static final ResourceLocation BISMUTH = new ResourceLocation(TFC, "bismuth");
  public static final ResourceLocation BISMUTH_BRONZE = new ResourceLocation(TFC, "bismuth_bronze");
  public static final ResourceLocation BLACK_BRONZE = new ResourceLocation(TFC, "black_bronze");
  public static final ResourceLocation BRASS = new ResourceLocation(TFC, "brass");
  public static final ResourceLocation BRONZE = new ResourceLocation(TFC, "bronze");
  public static final ResourceLocation COPPER = new ResourceLocation(TFC, "copper");
  public static final ResourceLocation GOLD = new ResourceLocation(TFC, "gold");
  public static final ResourceLocation LEAD = new ResourceLocation(TFC, "lead");
  public static final ResourceLocation NICKEL = new ResourceLocation(TFC, "nickel");
  public static final ResourceLocation ROSE_GOLD = new ResourceLocation(TFC, "rose_gold");
  public static final ResourceLocation SILVER = new ResourceLocation(TFC, "silver");
  public static final ResourceLocation TIN = new ResourceLocation(TFC, "tin");
  public static final ResourceLocation ZINC = new ResourceLocation(TFC, "zinc");
  public static final ResourceLocation STERLING_SILVER = new ResourceLocation(TFC, "sterling_silver");
  public static final ResourceLocation WROUGHT_IRON = new ResourceLocation(TFC, "wrought_iron");
  public static final ResourceLocation PIG_IRON = new ResourceLocation(TFC, "pig_iron");
  public static final ResourceLocation STEEL = new ResourceLocation(TFC, "steel");
  public static final ResourceLocation PLATINUM = new ResourceLocation(TFC, "platinum");
  public static final ResourceLocation BLACK_STEEL = new ResourceLocation(TFC, "black_steel");
  public static final ResourceLocation BLUE_STEEL = new ResourceLocation(TFC, "blue_steel");
  public static final ResourceLocation RED_STEEL = new ResourceLocation(TFC, "red_steel");
  public static final ResourceLocation WEAK_STEEL = new ResourceLocation(TFC, "weak_steel");
  public static final ResourceLocation WEAK_BLUE_STEEL = new ResourceLocation(TFC, "weak_blue_steel");
  public static final ResourceLocation WEAK_RED_STEEL = new ResourceLocation(TFC, "weak_red_steel");
  public static final ResourceLocation HIGH_CARBON_STEEL = new ResourceLocation(TFC, "high_carbon_steel");
  public static final ResourceLocation HIGH_CARBON_BLUE_STEEL = new ResourceLocation(TFC, "high_carbon_blue_steel");
  public static final ResourceLocation HIGH_CARBON_RED_STEEL = new ResourceLocation(TFC, "high_carbon_red_steel");
  public static final ResourceLocation HIGH_CARBON_BLACK_STEEL = new ResourceLocation(TFC, "high_carbon_black_steel");
  public static final ResourceLocation UNKNOWN = new ResourceLocation(TFC, "unknown");

  /*
   * Ores
   */
  public static final ResourceLocation NATIVE_COPPER = new ResourceLocation(TFC, "native_copper");
  public static final ResourceLocation NATIVE_GOLD = new ResourceLocation(TFC, "native_gold");
  public static final ResourceLocation NATIVE_PLATINUM = new ResourceLocation(TFC, "native_platinum");
  public static final ResourceLocation HEMATITE = new ResourceLocation(TFC, "hematite");
  public static final ResourceLocation NATIVE_SILVER = new ResourceLocation(TFC, "native_silver");
  public static final ResourceLocation CASSITERITE = new ResourceLocation(TFC, "cassiterite");
  public static final ResourceLocation GALENA = new ResourceLocation(TFC, "galena");
  public static final ResourceLocation BISMUTHINITE = new ResourceLocation(TFC, "bismuthinite");
  public static final ResourceLocation GARNIERITE = new ResourceLocation(TFC, "garnierite");
  public static final ResourceLocation MALACHITE = new ResourceLocation(TFC, "malachite");
  public static final ResourceLocation MAGNETITE = new ResourceLocation(TFC, "magnetite");
  public static final ResourceLocation LIMONITE = new ResourceLocation(TFC, "limonite");
  public static final ResourceLocation SPHALERITE = new ResourceLocation(TFC, "sphalerite");
  public static final ResourceLocation TETRAHEDRITE = new ResourceLocation(TFC, "tetrahedrite");
  public static final ResourceLocation BITUMINOUS_COAL = new ResourceLocation(TFC, "bituminous_coal");
  public static final ResourceLocation LIGNITE = new ResourceLocation(TFC, "lignite");
  public static final ResourceLocation KAOLINITE = new ResourceLocation(TFC, "kaolinite");
  public static final ResourceLocation GYPSUM = new ResourceLocation(TFC, "gypsum");
  public static final ResourceLocation SATINSPAR = new ResourceLocation(TFC, "satinspar");
  public static final ResourceLocation SELENITE = new ResourceLocation(TFC, "selenite");
  public static final ResourceLocation GRAPHITE = new ResourceLocation(TFC, "graphite");
  public static final ResourceLocation KIMBERLITE = new ResourceLocation(TFC, "kimberlite");
  public static final ResourceLocation PETRIFIED_WOOD = new ResourceLocation(TFC, "petrified_wood");
  public static final ResourceLocation SULFUR = new ResourceLocation(TFC, "sulfur");
  public static final ResourceLocation JET = new ResourceLocation(TFC, "jet");
  public static final ResourceLocation MICROCLINE = new ResourceLocation(TFC, "microcline");
  public static final ResourceLocation PITCHBLENDE = new ResourceLocation(TFC, "pitchblende");
  public static final ResourceLocation CINNABAR = new ResourceLocation(TFC, "cinnabar");
  public static final ResourceLocation CRYOLITE = new ResourceLocation(TFC, "cryolite");
  public static final ResourceLocation SALTPETER = new ResourceLocation(TFC, "saltpeter");
  public static final ResourceLocation SERPENTINE = new ResourceLocation(TFC, "serpentine");
  public static final ResourceLocation SYLVITE = new ResourceLocation(TFC, "sylvite");
  public static final ResourceLocation BORAX = new ResourceLocation(TFC, "borax");
  public static final ResourceLocation OLIVINE = new ResourceLocation(TFC, "olivine");
  public static final ResourceLocation LAPIS_LAZULI = new ResourceLocation(TFC, "lapis_lazuli");

  @SubscribeEvent
  public static void onPreRegisterOre(TFCRegistryEvent.RegisterPreBlock<Ore> event) {
    event.getRegistry().registerAll(
      new Ore(NATIVE_COPPER, COPPER, 0.75D, 0.33D),
      new Ore(NATIVE_GOLD, GOLD, 0.25D, 0.15D),
      new Ore(NATIVE_PLATINUM, PLATINUM, 1D, 0.002D),
      new Ore(HEMATITE, WROUGHT_IRON, false),
      new Ore(NATIVE_SILVER, SILVER, 0.50D, 0.15D),
      new Ore(CASSITERITE, TIN),
      new Ore(GALENA, LEAD),
      new Ore(BISMUTHINITE, BISMUTH),
      new Ore(GARNIERITE, NICKEL),
      new Ore(MALACHITE, COPPER),
      new Ore(MAGNETITE, WROUGHT_IRON, false),
      new Ore(LIMONITE, WROUGHT_IRON, false),
      new Ore(SPHALERITE, ZINC),
      new Ore(TETRAHEDRITE, COPPER),
      new Ore(BITUMINOUS_COAL),
      new Ore(LIGNITE),
      new Ore(KAOLINITE),
      new Ore(GYPSUM),
      new Ore(SATINSPAR),
      new Ore(SELENITE),
      new Ore(GRAPHITE),
      new Ore(KIMBERLITE),
      new Ore(PETRIFIED_WOOD),
      new Ore(SULFUR),
      new Ore(JET),
      new Ore(MICROCLINE),
      new Ore(PITCHBLENDE),
      new Ore(CINNABAR),
      new Ore(CRYOLITE),
      new Ore(SALTPETER),
      new Ore(SERPENTINE),
      new Ore(SYLVITE),
      new Ore(BORAX),
      new Ore(OLIVINE),
      new Ore(LAPIS_LAZULI)
    );
  }

  @SubscribeEvent
  public static void onPreRegisterMetal(TFCRegistryEvent.RegisterPreBlock<Metal> event) {
    event.getRegistry().registerAll(
      new Metal(BISMUTH, TIER_I, true, 0.14f, 270, 0xFF486B72, null, null),
      new Metal(BISMUTH_BRONZE, TIER_II, true, 0.35f, 985, 0xFF418E4F, ToolMaterials.BISMUTH_BRONZE, ArmorMaterials.BISMUTH_BRONZE),
      new Metal(BLACK_BRONZE, TIER_II, true, 0.35f, 1070, 0xFF3B2636, ToolMaterials.BLACK_BRONZE, ArmorMaterials.BLACK_BRONZE),
      new Metal(BRASS, TIER_I, true, 0.35f, 930, 0xFF96892E, null, null),
      new Metal(BRONZE, TIER_II, true, 0.35f, 950, 0xFF7C5E33, ToolMaterials.BRONZE, ArmorMaterials.BRONZE),
      new Metal(COPPER, TIER_I, true, 0.35f, 1080, 0xFFB64027, ToolMaterials.COPPER, ArmorMaterials.COPPER),
      new Metal(GOLD, TIER_I, true, 0.6f, 1060, 0xFFDCBF1B, null, null),
      new Metal(LEAD, TIER_I, true, 0.22f, 328, 0xFF40494D, null, null),
      new Metal(NICKEL, TIER_I, true, 0.48f, 1453, 0xFF4E4E3C, null, null),
      new Metal(ROSE_GOLD, TIER_I, true, 0.35f, 960, 0xFFEB7137, null, null),
      new Metal(SILVER, TIER_I, true, 0.48f, 961, 0xFF949495, null, null),
      new Metal(TIN, TIER_I, true, 0.14f, 230, 0xFF90A4BB, null, null),
      new Metal(ZINC, TIER_I, true, 0.21f, 420, 0xFFBBB9C4, null, null),
      new Metal(STERLING_SILVER, TIER_I, true, 0.35f, 900, 0xFFAC927B, null, null),
      new Metal(WROUGHT_IRON, TIER_III, true, 0.35f, 1535, 0xFF989897, ToolMaterials.WROUGHT_IRON, ArmorMaterials.WROUGHT_IRON),
      new Metal(PIG_IRON, TIER_III, true, 0.35f, 1535, 0xFF6A595C, null, null),
      new Metal(STEEL, TIER_IV, true, 0.35f, 1540, 0xFF5F5F5F, ToolMaterials.STEEL, ArmorMaterials.STEEL),
      new Metal(PLATINUM, TIER_V, true, 0.35f, 1730, 0xFF9DADC0, null, null),
      new Metal(BLACK_STEEL, TIER_V, true, 0.35f, 1485, 0xFF111111, ToolMaterials.BLACK_STEEL, ArmorMaterials.BLACK_STEEL),
      new Metal(BLUE_STEEL, TIER_VI, true, 0.35f, 1540, 0xFF2D5596, ToolMaterials.BLUE_STEEL, ArmorMaterials.BLUE_STEEL),
      new Metal(RED_STEEL, TIER_VI, true, 0.35f, 1540, 0xFF700503, ToolMaterials.RED_STEEL, ArmorMaterials.RED_STEEL),
      new Metal(WEAK_STEEL, TIER_IV, false, 0.35f, 1540, 0xFF111111, null, null),
      new Metal(WEAK_BLUE_STEEL, TIER_V, false, 0.35f, 1540, 0xFF2D5596, null, null),
      new Metal(WEAK_RED_STEEL, TIER_V, false, 0.35f, 1540, 0xFF700503, null, null),
      new Metal(HIGH_CARBON_STEEL, TIER_III, false, 0.35f, 1540, 0xFF5F5F5F, null, null),
      new Metal(HIGH_CARBON_BLUE_STEEL, TIER_V, false, 0.35f, 1540, 0xFF2D5596, null, null),
      new Metal(HIGH_CARBON_RED_STEEL, TIER_V, false, 0.35f, 1540, 0xFF700503, null, null),
      new Metal(HIGH_CARBON_BLACK_STEEL, TIER_IV, false, 0.35f, 1540, 0xFF111111, null, null),
      new Metal(UNKNOWN, TIER_I, false, 0.5f, 1250, 0xFF2F2B27, null, null)
    );
  }

  @SubscribeEvent
  public static void onRegisterAlloyRecipe(RegistryEvent.Register<AlloyRecipe> event) {
    event.getRegistry().registerAll(
      new AlloyRecipe.Builder(BISMUTH_BRONZE).add(ZINC, 0.2, 0.3).add(COPPER, 0.5, 0.65).add(BISMUTH, 0.1, 0.2).build(),
      new AlloyRecipe.Builder(BLACK_BRONZE).add(COPPER, 0.5, 0.7).add(SILVER, 0.1, 0.25).add(GOLD, 0.1, 0.25).build(),
      new AlloyRecipe.Builder(BRONZE).add(COPPER, 0.88, 0.92).add(TIN, 0.08, 0.12).build(),
      new AlloyRecipe.Builder(BRASS).add(COPPER, 0.88, 0.92).add(ZINC, 0.08, 0.12).build(),
      new AlloyRecipe.Builder(ROSE_GOLD).add(COPPER, 0.15, 0.3).add(GOLD, 0.7, 0.85).build(),
      new AlloyRecipe.Builder(STERLING_SILVER).add(COPPER, 0.2, 0.4).add(SILVER, 0.6, 0.8).build(),
      new AlloyRecipe.Builder(WEAK_STEEL).add(STEEL, 0.5, 0.7).add(NICKEL, 0.15, 0.25).add(BLACK_BRONZE, 0.15, 0.25).build(),
      new AlloyRecipe.Builder(WEAK_BLUE_STEEL).add(BLACK_STEEL, 0.5, 0.55).add(STEEL, 0.2, 0.25).add(BISMUTH_BRONZE, 0.1, 0.15).add(STERLING_SILVER, 0.1, 0.15)
        .build(),
      new AlloyRecipe.Builder(WEAK_RED_STEEL).add(BLACK_STEEL, 0.5, 0.55).add(STEEL, 0.2, 0.25).add(BRASS, 0.1, 0.15).add(ROSE_GOLD, 0.1, 0.15).build()
    );
  }
}
