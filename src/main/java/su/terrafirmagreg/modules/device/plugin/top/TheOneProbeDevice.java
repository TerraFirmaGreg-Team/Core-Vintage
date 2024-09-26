package su.terrafirmagreg.modules.device.plugin.top;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;

import su.terrafirmagreg.modules.device.plugin.top.provider.*;

public final class TheOneProbeDevice {

  public static void init() {
    ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;

    oneProbe.registerProvider(new ProviderFridge());
    oneProbe.registerProvider(new ProviderLatexExtractor());
    oneProbe.registerProvider(new ProviderPitKiln());
    oneProbe.registerProvider(new ProviderCrucible());
    oneProbe.registerProvider(new ProviderQuernManual());
    oneProbe.registerProvider(new ProviderQuernHorse());
    oneProbe.registerProvider(new ProviderBlastFurnace());
    oneProbe.registerProvider(new ProviderBloom());
    oneProbe.registerProvider(new ProviderBloomery());
    oneProbe.registerProvider(new ProviderLogPile());

  }
}
