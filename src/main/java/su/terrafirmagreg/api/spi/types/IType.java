package su.terrafirmagreg.api.spi.types;

@FunctionalInterface
public interface IType<T> {

    T getType();
}
