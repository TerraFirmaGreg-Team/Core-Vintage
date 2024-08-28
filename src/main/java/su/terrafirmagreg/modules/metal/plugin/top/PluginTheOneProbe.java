package su.terrafirmagreg.modules.metal.plugin.top;

import mcjty.theoneprobe.TheOneProbe;
import mcjty.theoneprobe.api.ITheOneProbe;


import su.terrafirmagreg.modules.metal.plugin.top.provider.ProviderMetalLamp;

public final class PluginTheOneProbe {

    public static void init() {
        ITheOneProbe oneProbe = TheOneProbe.theOneProbeImp;
        oneProbe.registerProvider(new ProviderMetalLamp());
    }
}
