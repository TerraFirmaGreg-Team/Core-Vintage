package net.dries007.eerussianguy.firmalife.compat;

import su.terrafirmagreg.api.util.GameUtils;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.eerussianguy.firmalife.compat.dynamictrees.DTModule;
import net.dries007.eerussianguy.firmalife.recipe.NutRecipe;

import java.util.ArrayList;

import static su.terrafirmagreg.api.data.Reference.MODID_FL;

@Mod.EventBusSubscriber(modid = MODID_FL)
public class ModuleManager {

  private static final ArrayList<ModuleCore> modules = new ArrayList<>();

  public static ArrayList<ModuleCore> getModules() {
    return modules;
  }

  public static void initModules() {
    registerModule(new DTModule());
  }

  public static void registerModule(ModuleCore module) {
    if (GameUtils.isModLoaded(module.getDep())) {
      modules.add(module);
    }
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

}
