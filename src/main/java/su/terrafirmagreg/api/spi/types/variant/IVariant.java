package su.terrafirmagreg.api.spi.types.variant;

@FunctionalInterface
public interface IVariant<T extends Variant<T>> {

    T getVariant();
}
