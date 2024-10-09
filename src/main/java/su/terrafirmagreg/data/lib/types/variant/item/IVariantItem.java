package su.terrafirmagreg.data.lib.types.variant.item;

import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.type.Type;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.data.lib.types.variant.Variant;

public interface IVariantItem<V extends Variant<V, ?>, T extends Type<T>> extends IVariant<V>, IType<T> {
}
