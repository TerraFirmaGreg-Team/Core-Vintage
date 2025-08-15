package su.terrafirmagreg.framework.manager.registry.api;

import su.terrafirmagreg.api.library.tag.TagKey;
import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry.RegistrySettings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import lombok.Getter;

import java.util.List;

public interface IRegistryEntry<T extends RegistrySettings<T>, V extends IForgeRegistryEntry<V>> extends IBaseEntry<T, V>, IForgeRegistryEntry<V> {

  default void preRegister() {}

  default void postRegister() {}

  default void setIdentifier(ResourceLocation identifier) {
    getSettings().identifier(identifier);
    asEntry().setRegistryName(identifier);
  }

  @Getter
  abstract class RegistrySettings<T> extends BaseSettings<T> {

    protected final List<TagKey> tagsKey = new ObjectArrayList<>();

    public T tag(TagKey tagKey) {
      this.tagsKey.add(tagKey);
      return this.self();
    }

  }
}
