package su.terrafirmagreg.api.spi.types.variant;

@FunctionalInterface
public interface IVariant<T> {

    T getVariant();
}
