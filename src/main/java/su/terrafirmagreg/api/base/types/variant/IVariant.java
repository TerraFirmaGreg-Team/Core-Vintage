package su.terrafirmagreg.api.base.types.variant;

@FunctionalInterface
public interface IVariant<T extends Variant<T>> {

    T getVariant();
}
