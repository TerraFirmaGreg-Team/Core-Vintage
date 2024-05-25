package su.terrafirmagreg.modules.device.plugin.top;

import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderFridge;
import su.terrafirmagreg.modules.device.plugin.top.provider.ProviderLatexExtractor;


import mcjty.theoneprobe.api.ITheOneProbe;

import java.util.function.Function;

@SuppressWarnings("unused")
public final class PluginTheOneProbe implements Function<ITheOneProbe, Void> {

    @Override
    public Void apply(ITheOneProbe probe) {
        probe.registerProvider(new ProviderFridge());
        probe.registerProvider(new ProviderLatexExtractor());

        return null;
    }
}
