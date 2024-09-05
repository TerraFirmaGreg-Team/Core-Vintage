package su.terrafirmagreg.modules.metal.plugin.top;

import su.terrafirmagreg.modules.metal.plugin.top.provider.ProviderMetalLamp;


import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public final class PluginTheOneProbe {

  public static void init() {
    ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
    oneProbe.registerProvider(new ProviderMetalLamp());
  }
}
