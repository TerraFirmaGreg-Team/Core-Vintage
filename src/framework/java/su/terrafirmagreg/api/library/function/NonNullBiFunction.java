package su.terrafirmagreg.api.library.function;

import su.terrafirmagreg.api.library.annotation.NonnullType;

import java.util.function.BiFunction;

@FunctionalInterface
public interface NonNullBiFunction<@NonnullType T, @NonnullType U, @NonnullType R> extends BiFunction<T, U, R> {

  @Override
  R apply(T t, U u);
}
