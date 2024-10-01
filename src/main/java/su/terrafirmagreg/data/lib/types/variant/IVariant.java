package su.terrafirmagreg.data.lib.types.variant;

@FunctionalInterface
public interface IVariant<V extends Variant<V, ?>> {

  V getVariant();
}
