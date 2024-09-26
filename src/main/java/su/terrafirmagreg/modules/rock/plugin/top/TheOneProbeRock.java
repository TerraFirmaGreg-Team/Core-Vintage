package su.terrafirmagreg.modules.rock.plugin.top;

import su.terrafirmagreg.modules.rock.plugin.top.provider.ProviderRockInfo;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

public final class TheOneProbeRock {

  public static void init() {
    ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
    oneProbe.registerProvider(new ProviderRockInfo());


  }
}
