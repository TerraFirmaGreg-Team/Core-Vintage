package su.terrafirmagreg.modules.integration.gregtech.event;

import su.terrafirmagreg.modules.integration.gregtech.unification.material.MaterialCoreHandler;
import su.terrafirmagreg.modules.integration.gregtech.unification.ore.stonetype.StoneTypeHandler;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;

@SuppressWarnings("unused")
public final class MaterialEventHandler {

  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void registerMaterials(MaterialEvent event) {

    MaterialCoreHandler.init();
    StoneTypeHandler.init();
  }

  @SubscribeEvent
  public static void registerMaterialsPost(PostMaterialEvent event) {
    MaterialCoreHandler.postInit();

    GregTechAPI.materialManager.getRegisteredMaterials().forEach(material -> {
      if (material.hasProperty(PropertyKey.TOOL)) {
        ToolProperty toolProperty = material.getProperty(PropertyKey.TOOL);
        toolProperty.setToolDurability(toolProperty.getToolDurability() * 7);
      }
    });
  }
}
