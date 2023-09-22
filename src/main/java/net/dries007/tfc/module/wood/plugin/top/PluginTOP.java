package net.dries007.tfc.module.wood.plugin.top;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.module.wood.plugin.top.provider.BarrelProvider;

import java.util.function.Function;

@SuppressWarnings("unused")
public class PluginTOP {

    public static class Callback implements Function<ITheOneProbe, Void> {

        @Override
        public Void apply(ITheOneProbe top) {

            top.registerProvider(new BarrelProvider());
            return null;
        }
    }

}
