package su.terrafirmagreg.api.library.types.category;

@FunctionalInterface
public interface ICategory<V extends Category<V>> {

  V getCategory();
}
