package su.terrafirmagreg.datafix.mapping;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings.Mapping;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.api.recipes.anvil.AnvilRecipe;
import net.dries007.tfc.api.recipes.knapping.KnappingRecipe;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class RecipeRemapping extends AbstractRemapping {


  @SubscribeEvent
  public static void onRemappingAnvilRecipe(final RegistryEvent.MissingMappings<AnvilRecipe> event) {

    event.getAllMappings().forEach(Mapping::ignore);
  }

  @SubscribeEvent
  public static void onRemappingKnappingRecipe(final RegistryEvent.MissingMappings<KnappingRecipe> event) {

    event.getAllMappings().forEach(Mapping::ignore);
  }
}
