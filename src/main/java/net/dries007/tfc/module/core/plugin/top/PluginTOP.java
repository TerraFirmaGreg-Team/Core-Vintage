package net.dries007.tfc.module.core.plugin.top;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.module.core.plugin.top.provider.PlacedItemProvider;
import net.dries007.tfc.module.core.plugin.top.provider.TorchProvider;

import java.util.function.Function;

@SuppressWarnings("unused")
public class PluginTOP {

    public static class Callback implements Function<ITheOneProbe, Void> {

        @Override
        public Void apply(ITheOneProbe top) {

            top.registerProvider(new PlacedItemProvider());
            top.registerProvider(new TorchProvider());
            return null;
        }
    }

}
