package su.terrafirmagreg.framework.manager.feature.api;


// Это пока реализации чего то, что может быть изменено с помощью системы событий
public interface IFeature {

  default boolean hasSubscriptions() {
    return true;
  }

  default boolean isEnabled() {
    return true;
  }

}
