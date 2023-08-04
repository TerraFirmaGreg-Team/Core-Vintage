package net.dries007.tfc;

import zone.rong.mixinbooter.ILateMixinLoader;

import java.util.ArrayList;
import java.util.List;

public class LateMixinLoader implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        final var someList = new ArrayList<String>();

        someList.add("mixins.tfc.json");

        return someList;
    }
}
