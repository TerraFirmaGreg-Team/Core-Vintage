package su.terrafirmagreg.api.library.types.type;

@FunctionalInterface
public interface IType<T extends Type<T>> {

  T getType();
}
