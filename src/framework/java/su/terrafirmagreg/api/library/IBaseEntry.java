package su.terrafirmagreg.api.library;


import su.terrafirmagreg.api.library.IBaseEntry.BaseSettings;

import com.google.common.base.Preconditions;

import lombok.Getter;

public interface IBaseEntry<T extends BaseSettings<T>, V> {

  @SuppressWarnings("unchecked")
  default V asEntry() {
    return (V) this;
  }

  T getSettings();


  @Getter
  abstract class BaseSettings<T> {


    protected String registryKey;


    /**
     * Устанавливает ключ реестра для элемента. Во время регистрации, будет подставлено имя контейнера (modId) и имя модуля
     * <p>Формат modId:moduleName/registryKey</p>
     */
    @SuppressWarnings("unchecked")
    public T registryKey(String registryKey) {
      this.registryKey = Preconditions.checkNotNull(registryKey, "name");
      return (T) this;
    }

  }
}
