package su.terrafirmagreg.api.base;

import su.terrafirmagreg.api.base.IBaseSettings.BaseSettings;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

public interface IBaseSettings<T extends BaseSettings<T>, V> {

  @SuppressWarnings("unchecked")
  default V asEntry() {
    return (V) this;
  }

  T getSettings();


  default void postRegister() {}

  @Getter
  abstract class BaseSettings<T> {


    protected String registryKey;


    /**
     * Устанавливает ключ реестра для элемента. Во время регистрации, будет подставлено имя контейнера (modId) и имя модуля
     * <p>Формат modId:moduleName/registryKey</p>
     */
    @SuppressWarnings("unchecked")
    public T registryKey(String registryKey) {
      this.registryKey = checkNotNull(registryKey, "name");
      return (T) this;
    }

  }
}
