package su.terrafirmagreg.api.library;

/**
 * Simply holds an object.
 */
public class Holder<T> {

  private T object;

  public Holder(T object) {
    this.object = object;
  }

  public Holder() {
  }

  /**
   * Sets the held object.
   *
   * @param object object to be held
   */
  public void set(T object) {
    this.object = object;
  }

  /**
   * Gets the held object.
   */
  public T get() {
    return this.object;
  }
}
