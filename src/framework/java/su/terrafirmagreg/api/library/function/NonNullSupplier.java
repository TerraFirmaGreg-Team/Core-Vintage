package su.terrafirmagreg.api.library.function;

import su.terrafirmagreg.api.library.annotation.NonnullType;
import su.terrafirmagreg.api.library.annotation.NullableType;

import java.util.Objects;
import java.util.function.Supplier;

@FunctionalInterface
public interface NonNullSupplier<@NonnullType T> extends Supplier<T> {

  static <T> NonNullSupplier<T> of(Supplier<@NullableType T> sup) {
    return of(sup, () -> "Unexpected null value from supplier");
  }

  static <T> NonNullSupplier<T> of(Supplier<@NullableType T> sup, NonNullSupplier<String> errorMsg) {
    return () -> {
      T res = sup.get();
      Objects.requireNonNull(res, errorMsg);
      return res;
    };
  }

  static <T> NonNullSupplier<T> lazy(Supplier<@NonnullType T> sup) {
    return Lazy.of(sup)::get;
  }

  @Override
  T get();

  default NonNullSupplier<T> lazy() {
    return lazy(this);
  }
}
