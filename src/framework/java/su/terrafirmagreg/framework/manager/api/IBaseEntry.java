package su.terrafirmagreg.framework.manager.api;


import su.terrafirmagreg.framework.manager.api.IBaseEntry.BaseSettings;

import lombok.Getter;

public interface IBaseEntry<T extends BaseSettings<T>, V> {

  @SuppressWarnings("unchecked")
  default V asEntry() {
    return (V) this;
  }

  T getSettings();


  @Getter
  abstract class BaseSettings<T> {


    @SuppressWarnings("unchecked")
    protected T self() {return (T) this;}


  }
}
