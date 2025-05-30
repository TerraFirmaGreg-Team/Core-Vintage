package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.modules.animal.plugin.top.PluginTheOneProbe;

public final class PluginsAnimal {

  public static void onRegister(IPluginRegistrar registrar) {

    registrar.addPlugin(new PluginTheOneProbe());
  }
}
