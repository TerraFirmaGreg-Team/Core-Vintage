package su.terrafirmagreg.api.library.function;

@FunctionalInterface
public interface TriConsumer<T, U, S> {

  void accept(T t, U u, S s);
}
