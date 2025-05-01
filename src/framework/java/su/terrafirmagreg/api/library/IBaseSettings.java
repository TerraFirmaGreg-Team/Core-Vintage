package su.terrafirmagreg.api.library;

import su.terrafirmagreg.api.library.IBaseSettings.BaseSettings;

import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import lombok.Getter;

public interface IBaseSettings<T extends BaseSettings<T>, V extends IForgeRegistryEntry<V>> {

  T getSettings();

  @SuppressWarnings("unchecked")
  default V asEntry() {
    return (V) this;
  }

  default void register(IForgeRegistry<V> registry) {

  }


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
