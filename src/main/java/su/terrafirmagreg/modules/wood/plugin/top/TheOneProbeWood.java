package su.terrafirmagreg.modules.wood.plugin.top;

import su.terrafirmagreg.modules.wood.plugin.top.provider.ProviderWoodSapling;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public final class TheOneProbeWood {

  public static void init() {
    ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

    oneProbe.registerProvider(new ProviderWoodSapling());
  }
}
