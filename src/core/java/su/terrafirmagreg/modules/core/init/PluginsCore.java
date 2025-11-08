package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.modules.core.plugin.gregtech.PluginCoreGregTech;
import su.terrafirmagreg.modules.core.plugin.groovy.PluginGroovy;

public class PluginsCore {

  public static void onRegister(IPluginRegistrar registrar) {

    registrar.addPlugin(new PluginGroovy());
    registrar.addPlugin(new PluginCoreGregTech());
  }
}
