package su.terrafirmagreg.api.library.types.variant;

@FunctionalInterface
public interface IVariant<V extends Variant<V, ?>> {

  V getVariant();
}
