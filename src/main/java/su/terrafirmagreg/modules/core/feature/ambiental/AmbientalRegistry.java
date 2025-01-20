package su.terrafirmagreg.modules.core.feature.ambiental;

import su.terrafirmagreg.modules.core.feature.ambiental.provider.IAmbientalProviderBase;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class AmbientalRegistry<Type extends IAmbientalProviderBase> implements Iterable<Type> {


  private final ArrayList<Type> list = new ArrayList<>();
  private final Map<String, Type> map = new Object2ObjectOpenHashMap<>();

  public AmbientalRegistry() {
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
