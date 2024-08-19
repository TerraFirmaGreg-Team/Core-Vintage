package su.terrafirmagreg.api.base.types.type;

@FunctionalInterface
public interface IType<T extends Type<T>> {

    T getType();
}
