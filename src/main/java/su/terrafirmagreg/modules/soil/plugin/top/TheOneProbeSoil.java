package su.terrafirmagreg.modules.soil.plugin.top;


import su.terrafirmagreg.modules.soil.plugin.top.provider.ProviderSoilInfo;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public final class TheOneProbeSoil {

  public static void init() {
    ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
    oneProbe.registerProvider(new ProviderSoilInfo());


  }
}
