package su.terrafirmagreg.modules.soil.plugin.top;


import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

import su.terrafirmagreg.modules.soil.plugin.top.provider.ProviderSoilInfo;

public final class TheOneProbeSoil {

  public static void init() {
    ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
    oneProbe.registerProvider(new ProviderSoilInfo());


  }
}
