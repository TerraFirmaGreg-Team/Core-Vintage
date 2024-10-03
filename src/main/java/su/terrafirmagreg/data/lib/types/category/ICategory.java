package su.terrafirmagreg.data.lib.types.category;

@FunctionalInterface
public interface ICategory<V extends Category<V>> {

  V getCategory();
}
