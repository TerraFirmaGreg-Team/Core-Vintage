package su.terrafirmagreg.api.library;

import lombok.Getter;

import java.util.function.Supplier;


public class DeferredObject<T> {

  @Getter
  private final Identifier identifier;
  private final Supplier<T> supplier;

  private T entry;

  public DeferredObject(Identifier identifier, T entry) {
    this.identifier = identifier;
    this.supplier = () -> entry;
  }

  public DeferredObject(Identifier identifier, Supplier<T> supplier) {
    this.identifier = identifier;
    this.supplier = supplier;
  }

  public static <T> DeferredObject<T> of(Identifier id, T entry) {
    return new DeferredObject<>(id, entry);
  }

  public static <T> DeferredObject<T> of(Identifier id, Supplier<T> entry) {
    return new DeferredObject<>(id, entry);
  }

  public T get() {
    if (this.entry == null) {
      this.entry = this.supplier.get();
    }
    return this.entry;
  }

}
