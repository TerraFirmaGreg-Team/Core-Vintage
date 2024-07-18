package su.terrafirmagreg.api.spi.types.type;

@FunctionalInterface
public interface IType<T extends Type<T>> {

    T getType();
}
