package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.modules.core.plugin.gregtech.PluginGregTech;

public final class PluginsCore {

  public static void onRegister(IPluginRegistrar registrar) {

    registrar.addPlugin(new PluginGregTech());
  }
}
