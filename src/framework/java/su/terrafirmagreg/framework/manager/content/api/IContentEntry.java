package su.terrafirmagreg.framework.manager.content.api;

import su.terrafirmagreg.api.library.tag.TagKey;
import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.content.api.IContentEntry.ContentSettings;

import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import lombok.Getter;

import java.util.List;

public interface IContentEntry<T extends ContentSettings<T>, V extends IForgeRegistryEntry<V>> extends IBaseEntry<T, V>, IForgeRegistryEntry<V> {

  default void apply() {}

  default void postRegister() {}

  @Getter
  abstract class ContentSettings<T> extends BaseSettings<T> {

    protected final List<TagKey> tagsKey = new ObjectArrayList<>();

    public T tag(TagKey tagKey) {
      this.tagsKey.add(tagKey);
      return this.self();
    }


  }
}
