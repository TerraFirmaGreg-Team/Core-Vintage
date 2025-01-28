package net.dries007.tfc.api.registries;

import net.minecraft.util.ResourceLocation;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFC;

/**
 * The names are separate from the instances TFCRegistries so they can be used without loading the class prematurely.
 */
public final class TFCRegistryNames {

  public static final ResourceLocation ROCK_TYPE = new ResourceLocation(TFC, "rock_type");
  public static final ResourceLocation ROCK = new ResourceLocation(TFC, "rock");
  public static final ResourceLocation ORE = new ResourceLocation(TFC, "ore");
  public static final ResourceLocation TREE = new ResourceLocation(TFC, "tree");
  public static final ResourceLocation METAL = new ResourceLocation(TFC, "metal");
  public static final ResourceLocation PLANT = new ResourceLocation(TFC, "plant");

  public static final ResourceLocation ALLOY_RECIPE = new ResourceLocation(TFC, "alloy_recipe");
  public static final ResourceLocation KNAPPING_RECIPE = new ResourceLocation(TFC, "knapping_recipe");
  public static final ResourceLocation ANVIL_RECIPE = new ResourceLocation(TFC, "anvil_recipe");
  public static final ResourceLocation WELDING_RECIPE = new ResourceLocation(TFC, "welding_recipe");
  public static final ResourceLocation HEAT_RECIPE = new ResourceLocation(TFC, "pit_kiln_recipe");
  public static final ResourceLocation BARREL_RECIPE = new ResourceLocation(TFC, "barrel_recipe");
  public static final ResourceLocation LOOM_RECIPE = new ResourceLocation(TFC, "loom_recipe");
  public static final ResourceLocation QUERN_RECIPE = new ResourceLocation(TFC, "quern_recipe");
  public static final ResourceLocation CHISEL_RECIPE = new ResourceLocation(TFC, "chisel_recipe");
  public static final ResourceLocation BLOOMERY_RECIPE = new ResourceLocation(TFC, "bloomery_recipe");
  public static final ResourceLocation BLAST_FURNACE_RECIPE = new ResourceLocation(TFC, "blast_furnace_recipe");
}
