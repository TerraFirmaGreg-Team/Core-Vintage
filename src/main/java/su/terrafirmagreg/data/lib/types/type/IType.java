package su.terrafirmagreg.data.lib.types.type;

@FunctionalInterface
public interface IType<T extends Type<T>> {

    T getType();
}
