package net.dries007.tfc.module.rock.plugin.top;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.module.rock.plugin.top.provider.RockBlockProvider;

import java.util.function.Function;

@SuppressWarnings("unused")
public class PluginTOP {

    public static class Callback implements Function<ITheOneProbe, Void> {

        @Override
        public Void apply(ITheOneProbe top) {

            top.registerProvider(new RockBlockProvider());
            return null;
        }
    }

}
