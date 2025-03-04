package net.dries007.firmalife.compat;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.firmalife.compat.dynamictrees.DTModule;
import net.dries007.tfc.objects.recipes.NutRecipe;

import java.util.ArrayList;

import static net.dries007.firmalife.FirmaLife.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ModuleManager {

  private static final ArrayList<ModuleCore> modules = new ArrayList<>();

  public static void registerModule(ModuleCore module) {
    if (isLoaded(module.getDep())) {
      modules.add(module);
    }
  }

  public static ArrayList<ModuleCore> getModules() {
    return modules;
  }

  public static void initModules() {
    registerModule(new DTModule());
  }

  @SubscribeEvent(priority = EventPriority.LOWEST)
  public static void onRegisterNutRecipeEvent(RegistryEvent.Register<NutRecipe> event) {
    IForgeRegistry<NutRecipe> r = event.getRegistry();

    for (ModuleCore module : modules) {
      if (module.getRegistry() != null) {
        module.getRegistry().registerNutRecipes(r);
      }
    }
  }

  private static boolean isLoaded(String modName) {
    return Loader.isModLoaded(modName);
  }
}
