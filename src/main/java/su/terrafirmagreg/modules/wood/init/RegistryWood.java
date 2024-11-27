package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.recipes.LoomRecipe;
import su.terrafirmagreg.modules.wood.api.recipes.NutRecipe;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

public final class RegistryWood {

  public static IForgeRegistry<LoomRecipe> LOOM;
  public static IForgeRegistry<NutRecipe> NUT;

  public static void onRegister() {

    LOOM = new RegistryBuilder<LoomRecipe>()
      .setName(ModUtils.resource("loom_recipe"))
      .setType(LoomRecipe.class)
      .allowModification()
      .create();

    NUT = new RegistryBuilder<NutRecipe>()
      .setName(ModUtils.resource("nut_recipe"))
      .setType(NutRecipe.class)
      .allowModification()
      .create();
  }
}
