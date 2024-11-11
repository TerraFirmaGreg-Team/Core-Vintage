package net.dries007.tfc.api.registries;

import net.minecraft.util.ResourceLocation;

import static su.terrafirmagreg.api.data.Reference.MODID_FL;
import static su.terrafirmagreg.api.data.Reference.MODID_TFC;

/**
 * The names are separate from the instances TFCRegistries so they can be used without loading the class prematurely.
 */
public final class TFCRegistryNames {

  public static final ResourceLocation ROCK_TYPE = new ResourceLocation(MODID_TFC, "rock_type");
  public static final ResourceLocation ORE = new ResourceLocation(MODID_TFC, "ore");
  public static final ResourceLocation TREE = new ResourceLocation(MODID_TFC, "tree");
  public static final ResourceLocation METAL = new ResourceLocation(MODID_TFC, "metal");
  public static final ResourceLocation PLANT = new ResourceLocation(MODID_TFC, "plant");

  public static final ResourceLocation ALLOY_RECIPE = new ResourceLocation(MODID_TFC, "alloy_recipe");
  public static final ResourceLocation KNAPPING_RECIPE = new ResourceLocation(MODID_TFC, "knapping_recipe");
  public static final ResourceLocation ANVIL_RECIPE = new ResourceLocation(MODID_TFC, "anvil_recipe");
  public static final ResourceLocation WELDING_RECIPE = new ResourceLocation(MODID_TFC, "welding_recipe");
  public static final ResourceLocation HEAT_RECIPE = new ResourceLocation(MODID_TFC, "pit_kiln_recipe");
  public static final ResourceLocation BARREL_RECIPE = new ResourceLocation(MODID_TFC, "barrel_recipe");
  public static final ResourceLocation LOOM_RECIPE = new ResourceLocation(MODID_TFC, "loom_recipe");
  public static final ResourceLocation QUERN_RECIPE = new ResourceLocation(MODID_TFC, "quern_recipe");
  public static final ResourceLocation CHISEL_RECIPE = new ResourceLocation(MODID_TFC, "chisel_recipe");
  public static final ResourceLocation BLOOMERY_RECIPE = new ResourceLocation(MODID_TFC, "bloomery_recipe");
  public static final ResourceLocation BLAST_FURNACE_RECIPE = new ResourceLocation(MODID_TFC, "blast_furnace_recipe");

  public static final ResourceLocation WIRE_DRAWING = new ResourceLocation(MODID_TFC, "wire_drawing_recipe");
  public static final ResourceLocation SMELTERY = new ResourceLocation(MODID_TFC, "smeltery_recipe");
  public static final ResourceLocation GLASSWORKING = new ResourceLocation(MODID_TFC, "glassworking_recipe");

  public static final ResourceLocation OVEN_RECIPE = new ResourceLocation(MODID_FL, "oven_recipe");
  public static final ResourceLocation DRYING_RECIPE = new ResourceLocation(MODID_FL, "drying_recipe");
  public static final ResourceLocation PLANTER_QUAD_REGISTRY = new ResourceLocation(MODID_FL, "planter_quad_registry");
  public static final ResourceLocation NUT_TREES_REGISTRY = new ResourceLocation(MODID_FL, "nut_trees");
  public static final ResourceLocation CRACKING_RECIPE = new ResourceLocation(MODID_FL, "cracking_recipe");
  public static final ResourceLocation STRAINING_RECIPE = new ResourceLocation(MODID_FL, "straining_recipe");
}
