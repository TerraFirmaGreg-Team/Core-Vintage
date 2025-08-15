package su.terrafirmagreg.framework.manager.api;

import java.util.function.Consumer;

public interface IBaseService<E extends IBaseEntry<?, ?>> {

  void fireEvent(Consumer<E> consumer);
}
