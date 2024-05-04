package su.terrafirmagreg.mixin;

import zone.rong.mixinbooter.IEarlyMixinLoader;

import java.util.ArrayList;
import java.util.List;

public class EarlyMixinLoader implements IEarlyMixinLoader {

    @Override
    public List<String> getMixinConfigs() {
        final var someList = new ArrayList<String>();

        someList.add("mixins.tfg.json");

        return someList;
    }
}
