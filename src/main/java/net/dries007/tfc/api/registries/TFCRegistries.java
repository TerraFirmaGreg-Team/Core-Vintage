package net.dries007.tfc.api.registries;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.tfc.TerraFirmaCraft;
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
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Plant;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.api.types.RockCategory;
import net.dries007.tfc.api.types.Tree;
import net.dries007.tfc.objects.recipes.CrackingRecipe;
import net.dries007.tfc.objects.recipes.DryingRecipe;
import net.dries007.tfc.objects.recipes.NutRecipe;
import net.dries007.tfc.objects.recipes.OvenRecipe;
import net.dries007.tfc.objects.recipes.PlanterRecipe;
import net.dries007.tfc.objects.recipes.StrainingRecipe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Get Registry instances for standard TFC objects here.
 */
public class TFCRegistries {

  /**
   * To developers: If you are considering creating one of these pre-block registries, take a minute to ask "is this the best idea" i.e create an interface +
   * enum, so addons register their own instances of TFC block classes using a custom implementation of the enum - AlcatrazEscapee
   */
  public static final IForgeRegistry<Rock> ROCKS = GameRegistry.findRegistry(Rock.class);
  public static final IForgeRegistry<RockCategory> ROCK_CATEGORIES = GameRegistry.findRegistry(RockCategory.class);
  public static final IForgeRegistry<Ore> ORES = GameRegistry.findRegistry(Ore.class);
  public static final IForgeRegistry<Tree> TREES = GameRegistry.findRegistry(Tree.class);
  public static final IForgeRegistry<Metal> METALS = GameRegistry.findRegistry(Metal.class);

  public static final IForgeRegistry<AlloyRecipe> ALLOYS = GameRegistry.findRegistry(AlloyRecipe.class);
  public static final IForgeRegistry<KnappingRecipe> KNAPPING = GameRegistry.findRegistry(KnappingRecipe.class);
  public static final IForgeRegistry<AnvilRecipe> ANVIL = GameRegistry.findRegistry(AnvilRecipe.class);
  public static final IForgeRegistry<WeldingRecipe> WELDING = GameRegistry.findRegistry(WeldingRecipe.class);
  public static final IForgeRegistry<HeatRecipe> HEAT = GameRegistry.findRegistry(HeatRecipe.class);
  public static final IForgeRegistry<BarrelRecipe> BARREL = GameRegistry.findRegistry(BarrelRecipe.class);
  public static final IForgeRegistry<LoomRecipe> LOOM = GameRegistry.findRegistry(LoomRecipe.class);
  public static final IForgeRegistry<QuernRecipe> QUERN = GameRegistry.findRegistry(QuernRecipe.class);
  public static final IForgeRegistry<ChiselRecipe> CHISEL = GameRegistry.findRegistry(ChiselRecipe.class);
  public static final IForgeRegistry<BloomeryRecipe> BLOOMERY = GameRegistry.findRegistry(BloomeryRecipe.class);
  public static final IForgeRegistry<BlastFurnaceRecipe> BLAST_FURNACE = GameRegistry.findRegistry(BlastFurnaceRecipe.class);
  public static final IForgeRegistry<OvenRecipe> OVEN = GameRegistry.findRegistry(OvenRecipe.class);
  public static final IForgeRegistry<DryingRecipe> DRYING = GameRegistry.findRegistry(DryingRecipe.class);
  public static final IForgeRegistry<PlanterRecipe> PLANTER_QUAD = GameRegistry.findRegistry(PlanterRecipe.class);
  public static final IForgeRegistry<NutRecipe> NUT_TREES = GameRegistry.findRegistry(NutRecipe.class);
  public static final IForgeRegistry<CrackingRecipe> CRACKING = GameRegistry.findRegistry(CrackingRecipe.class);
  public static final IForgeRegistry<StrainingRecipe> STRAINING = GameRegistry.findRegistry(StrainingRecipe.class);

  public static final IForgeRegistry<Plant> PLANTS = GameRegistry.findRegistry(Plant.class);


  static {
    // Make sure all public static final fields have values, should stop people from prematurely loading this class.
    try {
      int publicStaticFinal = Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL;

      for (Field field : TFCRegistries.class.getFields()) {
        if (!field.getType().isAssignableFrom(IForgeRegistry.class)) {
          TerraFirmaCraft.getLog().warn("[Please inform developers] Weird field? (Not a registry) {}", field);
          continue;
        }
        if ((field.getModifiers() & publicStaticFinal) != publicStaticFinal) {
          TerraFirmaCraft.getLog().warn("[Please inform developers] Weird field? (not Public Static Final) {}", field);
          continue;
        }
        if (field.get(null) == null) {
          throw new RuntimeException("Oh nooo! Someone tried to use the registries before they exist. Now everything is broken!");
        }
      }
    } catch (Exception e) {
      TerraFirmaCraft.getLog().fatal("Fatal error! This is likely a programming mistake.", e);
      throw new RuntimeException(e);
    }
  }
}
