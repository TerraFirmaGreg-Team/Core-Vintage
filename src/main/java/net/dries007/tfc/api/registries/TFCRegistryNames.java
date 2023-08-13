package net.dries007.tfc.api.registries;

import net.minecraft.util.ResourceLocation;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

/**
 * The names are separate from the instances TFCRegistries so they can be used without loading the class prematurely.
 */
public final class TFCRegistryNames {
    public static final ResourceLocation ALLOY_RECIPE = new ResourceLocation(MOD_ID, "alloy_recipe");
    public static final ResourceLocation KNAPPING_RECIPE = new ResourceLocation(MOD_ID, "knapping_recipe");
    public static final ResourceLocation ANVIL_RECIPE = new ResourceLocation(MOD_ID, "anvil_recipe");
    public static final ResourceLocation WELDING_RECIPE = new ResourceLocation(MOD_ID, "welding_recipe");
    public static final ResourceLocation HEAT_RECIPE = new ResourceLocation(MOD_ID, "pit_kiln_recipe");
    public static final ResourceLocation BARREL_RECIPE = new ResourceLocation(MOD_ID, "barrel_recipe");
    public static final ResourceLocation LOOM_RECIPE = new ResourceLocation(MOD_ID, "loom_recipe");
    public static final ResourceLocation QUERN_RECIPE = new ResourceLocation(MOD_ID, "quern_recipe");
    public static final ResourceLocation CHISEL_RECIPE = new ResourceLocation(MOD_ID, "chisel_recipe");
    public static final ResourceLocation BLOOMERY_RECIPE = new ResourceLocation(MOD_ID, "bloomery_recipe");
    public static final ResourceLocation BLAST_FURNACE_RECIPE = new ResourceLocation(MOD_ID, "blast_furnace_recipe");
}
