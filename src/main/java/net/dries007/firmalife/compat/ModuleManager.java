package net.dries007.firmalife.compat;

import su.terrafirmagreg.api.util.ModUtils;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import net.dries007.firmalife.compat.dynamictrees.DTModule;
import net.dries007.tfc.objects.recipes.NutRecipe;

import lombok.Getter;

import java.util.ArrayList;

import static net.dries007.firmalife.FirmaLife.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class ModuleManager {

  @Getter
  private static final ArrayList<ModuleCore> modules = new ArrayList<>();

  public static void registerModule(ModuleCore module) {
    if (isLoaded(module.getDep())) {
      modules.add(module);
    }
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
    return ModUtils.isModLoaded(modName);
  }
}
