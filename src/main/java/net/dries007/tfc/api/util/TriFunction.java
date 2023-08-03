package net.dries007.tfc.api.util;

import java.util.Objects;
import java.util.function.Function;
import javax.annotation.Nonnull;

@FunctionalInterface
public interface TriFunction<T, U, F, R> {
    R apply(T t, U u, F f);

    @Nonnull
    default <V> TriFunction<T, U, F, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (T t, U u, F f) -> after.apply(apply(t, u, f));
    }
}
