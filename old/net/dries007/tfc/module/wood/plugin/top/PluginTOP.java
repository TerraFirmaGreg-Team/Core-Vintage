package net.dries007.tfc.module.wood.plugin.top;

import mcjty.theoneprobe.api.ITheOneProbe;
import net.dries007.tfc.module.wood.plugin.top.provider.BarrelProvider;
import net.dries007.tfc.module.wood.plugin.top.provider.TreeProvider;

import java.util.function.Function;

@SuppressWarnings("unused")
public class PluginTOP implements Function<ITheOneProbe, Void> {

    @Override
    public Void apply(ITheOneProbe top) {

        top.registerProvider(new BarrelProvider());
        top.registerProvider(new TreeProvider());
        //top.registerProvider(new FruitTreeProvider());
        return null;
    }


}
