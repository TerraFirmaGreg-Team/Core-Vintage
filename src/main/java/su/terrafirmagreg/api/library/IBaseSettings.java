package su.terrafirmagreg.api.library;

import su.terrafirmagreg.api.library.IBaseSettings.BaseSettings;

import lombok.Getter;

public interface IBaseSettings<T extends BaseSettings<T>> {

  T getSettings();

  default void defaultSetter() {}

  @Getter
  abstract class BaseSettings<T> {


    protected String registryKey;

    /**
     * Устанавливает ключ реестра для элемента. Во время регистрации, будет подставлено имя контейнера (modId) и имя модуля
     * <p>Формат test:moduleName/registryKey</p>
     */
    @SuppressWarnings("unchecked")
    public T registryKey(String registryKey) {
      this.registryKey = registryKey;
      return (T) this;
    }

  }
}
