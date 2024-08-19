package su.terrafirmagreg.modules.core.features.ambiental;

import su.terrafirmagreg.modules.core.features.ambiental.provider.ITemperatureBlockProvider;
import su.terrafirmagreg.modules.core.features.ambiental.provider.ITemperatureEnvironmentalProvider;
import su.terrafirmagreg.modules.core.features.ambiental.provider.ITemperatureEquipmentProvider;
import su.terrafirmagreg.modules.core.features.ambiental.provider.ITemperatureItemProvider;
import su.terrafirmagreg.modules.core.features.ambiental.provider.ITemperatureProvider;
import su.terrafirmagreg.modules.core.features.ambiental.provider.ITemperatureTileProvider;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AmbientalRegistry<Type extends ITemperatureProvider> implements Iterable<Type> {

    public static final AmbientalRegistry<ITemperatureItemProvider> ITEMS = new AmbientalRegistry<>();
    public static final AmbientalRegistry<ITemperatureBlockProvider> BLOCKS = new AmbientalRegistry<>();
    public static final AmbientalRegistry<ITemperatureTileProvider> TILE_ENTITIES = new AmbientalRegistry<>();
    public static final AmbientalRegistry<ITemperatureEnvironmentalProvider> ENVIRONMENT = new AmbientalRegistry<>();
    public static final AmbientalRegistry<ITemperatureEquipmentProvider> EQUIPMENT = new AmbientalRegistry<>();

    private final ArrayList<Type> list = new ArrayList<>();
    private final HashMap<String, Type> map = new HashMap<>();

    private AmbientalRegistry() {}

    public void register(Type type) {
        list.add(type);
    }

    public boolean has(Type type) {
        return map.containsValue(type) || list.contains(type);
    }

    @Override
    public @NotNull Iterator<Type> iterator() {
        return new Iterator<>() {

            private final Iterator<Type> listIterator = list.iterator();

            @Override
            public boolean hasNext() {
                return listIterator.hasNext();
            }

            @Override
            public Type next() {
                return listIterator.next();
            }

        };
    }

}
