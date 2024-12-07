/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.types;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import net.dries007.tfc.api.recipes.AlloyRecipe;
import net.dries007.tfc.api.recipes.BlastFurnaceRecipe;
import net.dries007.tfc.api.recipes.BloomeryRecipe;
import net.dries007.tfc.api.recipes.ChiselRecipe;
import net.dries007.tfc.api.recipes.LoomRecipe;
import net.dries007.tfc.api.recipes.WeldingRecipe;
import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.barrel.BarrelRecipe;
import net.dries007.tfc.api.recipes.heat.HeatRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;
import net.dries007.tfc.api.recipes.quern.QuernRecipe;
import net.dries007.tfc.api.registries.TFCRegistryEvent;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.types.Tree;

import java.util.LinkedHashMap;
import java.util.Map;

import static net.dries007.tfc.TerraFirmaCraft.MODID_TFC;
import static net.dries007.tfc.api.registries.TFCRegistryNames.ALLOY_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.ANVIL_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.BARREL_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.BLAST_FURNACE_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.BLOOMERY_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.CHISEL_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.HEAT_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.KNAPPING_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.LOOM_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.METAL;
import static net.dries007.tfc.api.registries.TFCRegistryNames.ORE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.PLANT;
import static net.dries007.tfc.api.registries.TFCRegistryNames.QUERN_RECIPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.ROCK;
import static net.dries007.tfc.api.registries.TFCRegistryNames.ROCK_TYPE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.TREE;
import static net.dries007.tfc.api.registries.TFCRegistryNames.WELDING_RECIPE;

@Mod.EventBusSubscriber(modid = MODID_TFC)
public final class Registries {

  private static final Map<ResourceLocation, IForgeRegistry<?>> preBlockRegistries = new LinkedHashMap<>(); // Needs to respect insertion order

  @SubscribeEvent
  public static void onNewRegistryEvent(RegistryEvent.NewRegistry event) {
    // Pre Block registries (dirty hack)

    newRegistry(ROCK_TYPE, RockCategory.class, true); // Required before: ROCK
    newRegistry(ROCK, Rock.class, true);
    newRegistry(METAL, Metal.class, true);// Required before: ORE, ALLOY_RECIPE, WELDING_RECIPE
    newRegistry(ORE, Ore.class, true);
    newRegistry(TREE, Tree.class, true);
    newRegistry(PLANT, Plant.class, true);

    // Normal registries
    newRegistry(ALLOY_RECIPE, AlloyRecipe.class, false);
    newRegistry(KNAPPING_RECIPE, KnappingRecipe.class, false);
    newRegistry(ANVIL_RECIPE, AnvilRecipe.class, false);
    newRegistry(WELDING_RECIPE, WeldingRecipe.class, false);
    newRegistry(HEAT_RECIPE, HeatRecipe.class, false);
    newRegistry(BARREL_RECIPE, BarrelRecipe.class, false);
    newRegistry(LOOM_RECIPE, LoomRecipe.class, false);
    newRegistry(QUERN_RECIPE, QuernRecipe.class, false);
    newRegistry(CHISEL_RECIPE, ChiselRecipe.class, false);
    newRegistry(BLOOMERY_RECIPE, BloomeryRecipe.class, false);
    newRegistry(BLAST_FURNACE_RECIPE, BlastFurnaceRecipe.class, false);
  }

  /**
   * Danger: dirty hack.
   */
  @SubscribeEvent(priority = EventPriority.HIGHEST)
  public static void onRegisterBlock(RegistryEvent.Register<Block> event) {
    preBlockRegistries.forEach((e, r) -> MinecraftForge.EVENT_BUS.post(new TFCRegistryEvent.RegisterPreBlock<>(e, r)));
  }

  private static <T extends IForgeRegistryEntry<T>> void newRegistry(ResourceLocation name, Class<T> tClass, boolean isPreBlockRegistry) {
    IForgeRegistry<T> reg = new RegistryBuilder<T>().setName(name).allowModification().setType(tClass).create();
    if (isPreBlockRegistry) {
      preBlockRegistries.put(name, reg);
    }
  }
}
