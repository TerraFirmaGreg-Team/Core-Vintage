package su.terrafirmagreg.api.library;

import java.util.function.Supplier;

public final class Lazy {

  private Object value;

  private Lazy(Object value) {
    this.value = value;
  }

  public static Lazy of(Object value) {
    return new Lazy(value);
  }

  @SuppressWarnings("unchecked")
  public <T> T get() {
    if (value instanceof Supplier<?> supplier) {
      value = supplier.get();
    }
    return (T) value;
  }
}
