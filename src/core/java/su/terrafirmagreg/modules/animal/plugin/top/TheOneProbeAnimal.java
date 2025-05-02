package su.terrafirmagreg.modules.animal.plugin.top;

import su.terrafirmagreg.modules.animal.plugin.top.provider.AnimalProvider;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public final class TheOneProbeAnimal {

  public static void init() {
    ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

    oneProbe.registerEntityProvider(new AnimalProvider());
  }
}
