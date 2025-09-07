package su.terrafirmagreg.framework.manager.content.base.item.api;

import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.IVariant;
import su.terrafirmagreg.api.library.types.variant.Variant;

public interface IItemVariant<V extends Variant<V, T>, T extends Type<T>> extends IVariant<V>, IType<T> {
}
