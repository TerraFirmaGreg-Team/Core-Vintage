package su.terrafirmagreg.modules.rock.init;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.modules.rock.plugin.gregtech.PluginRockGregTech;

public class PluginRock {

  public static void onRegister(IPluginRegistrar registrar) {

    registrar.addPlugin(new PluginRockGregTech());
  }
}
