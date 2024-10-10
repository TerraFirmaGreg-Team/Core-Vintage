package su.terrafirmagreg.core;

import su.terrafirmagreg.Tags;
import su.terrafirmagreg.core.modules.gregtech.material.TFGMaterialHandler;
import su.terrafirmagreg.core.modules.gregtech.oreprefix.TFGOrePrefixHandler;
import su.terrafirmagreg.core.modules.gregtech.recipes.TFGRecipeHandlerList;
import su.terrafirmagreg.core.modules.gregtech.stonetypes.StoneTypeHandler;

import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;

@Mod.EventBusSubscriber(modid = Tags.MOD_ID)
public final class CommonEventHandler {

  @SubscribeEvent
  public static void registerMaterials(MaterialEvent event) {
    TFGMaterialHandler.init();
    TFGOrePrefixHandler.init();
    StoneTypeHandler.init();
  }

  @SubscribeEvent
  public static void registerMaterialsPost(PostMaterialEvent event) {
    TFGMaterialHandler.postInit();
    for (Material material : GregTechAPI.materialManager.getRegisteredMaterials()) {
      if (material.hasProperty(PropertyKey.TOOL)) {
        ToolProperty toolProperty = material.getProperty(PropertyKey.TOOL);
        toolProperty.setToolDurability(toolProperty.getToolDurability() * 7);
      }
    }
  }

  @SubscribeEvent
  public static void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
    TFGRecipeHandlerList.register();
  }

  @SubscribeEvent
  public static void onNetherPortalActivating(BlockEvent.PortalSpawnEvent event) {
    event.setCanceled(true);
  }
}
