package su.terrafirmagreg.modules.core.plugin.groovy;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.plugin.base.BasePlugin;
import su.terrafirmagreg.framework.module.spi.StateEvent;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PluginGroovy extends BasePlugin {

  public PluginGroovy() {
    super(PluginSettings.of()
      .modRequired(ModIDs.GROOVYSCRIPT)
    );
  }

  @SubscribeEvent
  public static void onInit(StateEvent.Initialization event) {

    GroovyScriptLoader.init();
  }


}
