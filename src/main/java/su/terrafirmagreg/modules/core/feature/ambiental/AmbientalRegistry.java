package su.terrafirmagreg.modules.core.feature.ambiental;

import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalBaseProvider;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalBlockProvider;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalEnvironmentalProvider;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalEquipmentProvider;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalItemProvider;
import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalTileProvider;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class AmbientalRegistry<Type extends IAmbientalBaseProvider> implements Iterable<Type> {

  public static final AmbientalRegistry<IAmbientalItemProvider> ITEMS = new AmbientalRegistry<>();
  public static final AmbientalRegistry<IAmbientalBlockProvider> BLOCKS = new AmbientalRegistry<>();
  public static final AmbientalRegistry<IAmbientalTileProvider> TILE_ENTITIES = new AmbientalRegistry<>();
  public static final AmbientalRegistry<IAmbientalEnvironmentalProvider> ENVIRONMENT = new AmbientalRegistry<>();
  public static final AmbientalRegistry<IAmbientalEquipmentProvider> EQUIPMENT = new AmbientalRegistry<>();

  private final ArrayList<Type> list = new ArrayList<>();
  private final HashMap<String, Type> map = new HashMap<>();

  private AmbientalRegistry() {
  }

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
