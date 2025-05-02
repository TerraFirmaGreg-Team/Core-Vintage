package su.terrafirmagreg.api.library.types.variant.block;

import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.IVariant;
import su.terrafirmagreg.api.library.types.variant.Variant;

public interface IVariantBlock<V extends Variant<V, T>, T extends Type<T>> extends IVariant<V>, IType<T> {

}
