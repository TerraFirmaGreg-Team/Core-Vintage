package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.plugin.api.IPluginRegistrar;
import su.terrafirmagreg.modules.device.plugin.jei.PluginJustEnoughItems;
import su.terrafirmagreg.modules.device.plugin.top.PluginTheOneProbe;

public final class PluginsDevice {

  public static void onRegister(IPluginRegistrar registrar) {
    registrar.addPlugin(new PluginTheOneProbe());
    registrar.addPlugin(new PluginJustEnoughItems());
  }
}
