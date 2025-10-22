package su.terrafirmagreg.framework.manager.content.api;

import su.terrafirmagreg.api.library.tag.TagKey;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.content.api.IContentEntry.ContentSettings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import lombok.Getter;

import java.util.List;

public interface IContentEntry<T extends ContentSettings<T>, V extends IForgeRegistryEntry<V>> extends IBaseEntry<T, V>, IForgeRegistryEntry<V> {

  default void preRegister() {}

  default void postRegister() {}

  default void setIdentifier(ResourceLocation identifier) {
    getSettings().identifier(identifier);
    asEntry().setRegistryName(identifier);
  }

  @Getter
  abstract class ContentSettings<T> extends BaseSettings<T> {

    protected final List<TagKey> tagsKey = new ObjectArrayList<>();
    protected Type<?> type;

    public T tag(TagKey tagKey) {
      this.tagsKey.add(tagKey);
      return this.self();
    }

    public T type(Type<?> type) {
      this.type = type;
      return this.self();
    }

  }
}
