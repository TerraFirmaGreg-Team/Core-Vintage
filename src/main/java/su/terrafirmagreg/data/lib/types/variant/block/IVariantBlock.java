package su.terrafirmagreg.data.lib.types.variant.block;

import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.type.Type;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.data.lib.types.variant.Variant;

public interface IVariantBlock<V extends Variant<V, T>, T extends Type<T>> extends IVariant<V>, IType<T> {
  
}
