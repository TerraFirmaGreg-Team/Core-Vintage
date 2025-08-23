package su.terrafirmagreg.modules.rock.plugin.gregtech;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;
import su.terrafirmagreg.framework.module.spi.StateEvent;
import su.terrafirmagreg.modules.rock.plugin.gregtech.recipes.OreRecipeGregTech;
import su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.MaterialRockHandler;
import su.terrafirmagreg.modules.rock.plugin.gregtech.unification.material.info.MaterialFlagsRock;
import su.terrafirmagreg.modules.rock.plugin.gregtech.unification.ore.StoneTypesRock;

import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.event.PostMaterialEvent;

public class PluginRockGregTech extends BasePlugin {

  public PluginRockGregTech() {
    super(PluginSettings.of()
      .modRequired(ModIDs.GREGTECH)

    );

  }

  @SubscribeEvent(priority = EventPriority.HIGH)
  public static void materialEventHigh(MaterialEvent event) {

    MaterialRockHandler.init();
    StoneTypesRock.init();
  }

  @SubscribeEvent
  public static void registerMaterialsPost(PostMaterialEvent event) {

    MaterialFlagsRock.init();
  }

  @SubscribeEvent
  public static void onPostInit(StateEvent.PostInitialization event) {

    OreRecipeGregTech.register();
  }
}
