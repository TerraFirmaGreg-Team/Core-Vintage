package su.terrafirmagreg.data.lib.types.variant;

@FunctionalInterface
public interface IVariant<T extends Variant<T>> {

  T getVariant();
}
