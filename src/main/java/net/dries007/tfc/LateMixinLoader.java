package net.dries007.tfc;

import java.util.ArrayList;
import java.util.List;
import zone.rong.mixinbooter.ILateMixinLoader;

public class LateMixinLoader implements ILateMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        final var someList = new ArrayList<String>();

        someList.add("mixins.tfc.json");

        return someList;
    }
}
