package net.dries007.tfc.module.animal.plugin.top;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.module.animal.plugin.top.provider.AnimalProvider;

import java.util.function.Function;

@SuppressWarnings("unused")
public class PluginTOP {

    public static class Callback implements Function<ITheOneProbe, Void> {

        @Override
        public Void apply(ITheOneProbe top) {

            top.registerEntityProvider(new AnimalProvider());
            return null;
        }
    }

}
