package su.terrafirmagreg.modules.core.plugin.gregtech;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;
import su.terrafirmagreg.modules.core.plugin.gregtech.init.BlocksGregTech;
import su.terrafirmagreg.modules.core.plugin.gregtech.init.ItemsGregTech;
import su.terrafirmagreg.modules.core.plugin.gregtech.init.RecipesGregTech;
import su.terrafirmagreg.modules.core.plugin.gregtech.unification.material.materials.ElementMaterialsCore;
import su.terrafirmagreg.modules.core.plugin.gregtech.unification.ore.StoneTypesCore;

import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.GregTechAPI;
import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.api.unification.material.properties.ToolProperty;

public class PluginGregTech extends BasePlugin {

  public PluginGregTech() {
    super(Settings.of()
      .registryKey("gregtech")
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
        toolProperty.setToolDurability(toolProperty.getToolDurability() * 7);
      }
    });
  }

  @Override
  public void onPreInit(FMLPreInitializationEvent event) {
    ItemsGregTech.preInit();
    BlocksGregTech.preInit();
  }

  @Override
  public void onPostInit(FMLPostInitializationEvent event) {

    RecipesGregTech.postInit();
  }
}
