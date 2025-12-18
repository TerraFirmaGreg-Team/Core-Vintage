package su.terrafirmagreg.api.library.collection;

import org.jetbrains.annotations.Nullable;

public interface IdMap<T> extends Iterable<T> {

  int DEFAULT = -1;

  int getId(T value);

  @Nullable
  T byId(int id);

  default T byIdOrThrow(int id) {
    T object = this.byId(id);
    if (object == null) {
      throw new IllegalArgumentException("No value with id " + id);
    } else {
      return object;
    }
  }

  int size();
}

