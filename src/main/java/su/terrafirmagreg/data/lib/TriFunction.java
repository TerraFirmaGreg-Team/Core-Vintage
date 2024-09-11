package su.terrafirmagreg.data.lib;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface TriFunction<T, U, F, R> {

  @NotNull
  default <V> TriFunction<T, U, F, V> andThen(Function<? super R, ? extends V> after) {
    Objects.requireNonNull(after);
    return (T t, U u, F f) -> after.apply(apply(t, u, f));
  }

  R apply(T t, U u, F f);
}
