package su.terrafirmagreg.modules.core.plugin.gregtech;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.core.plugin.gregtech.init.BlocksGregTech;
import su.terrafirmagreg.modules.core.plugin.gregtech.init.ItemsGregTech;
import su.terrafirmagreg.modules.core.plugin.gregtech.init.RecipesGregTech;
import su.terrafirmagreg.modules.core.plugin.gregtech.unification.material.materials.ElementMaterialsCore;
import su.terrafirmagreg.modules.core.plugin.gregtech.unification.ore.StoneTypesCore;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;

public class PluginCoreGregTech extends BasePlugin {

  public PluginCoreGregTech() {
    super(PluginSettings.of()
      .modRequired(ModIDs.GREGTECH)
    );
  }

  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void registerMaterials(MaterialEvent event) {

    ElementMaterialsCore.init();
    StoneTypesCore.init();
  }

  @SubscribeEvent
  public static void registerMaterialsPost(PostMaterialEvent event) {
    ElementMaterialsCore.postInit();

    GregTechAPI.materialManager.getRegisteredMaterials().forEach(material -> {
      if (material.hasProperty(PropertyKey.TOOL)) {
        ToolProperty toolProperty = material.getProperty(PropertyKey.TOOL);
        toolProperty.setDurabilityMultiplier(7);
      }
    });
  }

  @SubscribeEvent
  public static void onPreInit(StateEvent.PreInitialization event) {

    ItemsGregTech.preInit();
    BlocksGregTech.preInit();
  }

  @SubscribeEvent
  public static void onPostInit(StateEvent.PostInitialization event) {

    RecipesGregTech.postInit();
  }
}
