package su.terrafirmagreg.api.lib.types.type;

@FunctionalInterface
public interface IType<T extends Type<T>> {

    T getType();
}
