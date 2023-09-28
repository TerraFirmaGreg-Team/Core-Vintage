package net.dries007.tfc.module.devices.plugin.top;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.module.devices.plugin.top.provider.*;

import java.util.function.Function;

@SuppressWarnings("unused")
public class PluginTOP {

    public static class Callback implements Function<ITheOneProbe, Void> {

        @Override
        public Void apply(ITheOneProbe top) {

            top.registerProvider(new BlastFurnaceProvider());
            top.registerProvider(new BloomeryProvider());
            top.registerProvider(new CrucibleProvider());
            top.registerProvider(new LogPileProvider());
            top.registerProvider(new PitKilnProvider());
            top.registerProvider(new QuernProvider());
            return null;
        }
    }

}
