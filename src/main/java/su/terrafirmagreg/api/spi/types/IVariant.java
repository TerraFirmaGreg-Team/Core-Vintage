package su.terrafirmagreg.api.spi.types;

@FunctionalInterface
public interface IVariant<T> {

    T getVariant();
}
