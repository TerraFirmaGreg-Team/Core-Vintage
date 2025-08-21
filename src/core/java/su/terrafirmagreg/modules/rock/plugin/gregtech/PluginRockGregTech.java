package su.terrafirmagreg.modules.rock.plugin.gregtech;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;
import su.terrafirmagreg.modules.rock.plugin.gregtech.material.MaterialRockHandler;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.unification.material.event.MaterialEvent;

public class PluginRockGregTech extends BasePlugin {

  public PluginRockGregTech() {
    super(PluginSettings.of()
      .modRequired(ModIDs.GREGTECH)

    );

  }

  @SubscribeEvent(priority = EventPriority.HIGH)
  public void registerMaterials(MaterialEvent event) {
    
    MaterialRockHandler.init();
  }
}
