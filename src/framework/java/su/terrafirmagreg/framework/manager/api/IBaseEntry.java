package su.terrafirmagreg.framework.manager.api;


import su.terrafirmagreg.framework.manager.api.IBaseEntry.BaseSettings;

import net.minecraft.util.ResourceLocation;

import com.google.common.base.Preconditions;
import com.google.common.reflect.TypeToken;

import lombok.Getter;

public interface IBaseEntry<T extends BaseSettings<T>, E> {

  @SuppressWarnings("unchecked")
  default E asEntry() {
    return (E) this;
  }

  @SuppressWarnings("unchecked")
  default Class<E> asClassEntry() {
    return (Class<E>) new TypeToken<E>(getClass()) {}.getRawType();
  }

  T getSettings();

  default ResourceLocation getIdentifier() {
    return getSettings().getIdentifier();
  }


  @Getter
  abstract class BaseSettings<T> {

    protected boolean enabled = true;
    protected ResourceLocation identifier;
    protected String registryKey;


    @SuppressWarnings("unchecked")
    protected T self() {return (T) this;}

    public T identifier(ResourceLocation identifier) {
      this.identifier = Preconditions.checkNotNull(identifier, "identifier");
      return this.self();
    }

    public T registryKey(String registryKey) {
      this.registryKey = Preconditions.checkNotNull(registryKey, "registryKey");
      return this.self();
    }

    public T enabled(boolean enabled) {
      this.enabled = enabled;
      return this.self();
    }

    public T disable() {
      this.enabled = false;
      return this.self();
    }
  }
}
