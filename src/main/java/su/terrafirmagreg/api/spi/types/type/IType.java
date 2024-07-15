package su.terrafirmagreg.api.spi.types.type;

@FunctionalInterface
public interface IType<T> {

    T getType();
}
