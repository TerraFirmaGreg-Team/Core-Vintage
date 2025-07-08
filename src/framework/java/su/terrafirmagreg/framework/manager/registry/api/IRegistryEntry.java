package su.terrafirmagreg.framework.manager.registry.api;

import su.terrafirmagreg.api.library.tag.TagKey;
import su.terrafirmagreg.framework.manager.api.IBaseEntry;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry.RegistrySettings;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.base.Preconditions;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import lombok.Getter;

import javax.annotation.Nullable;
import java.util.List;

public interface IRegistryEntry<T extends RegistrySettings<T>, V extends IForgeRegistryEntry<V>> extends IBaseEntry<T, V> {

  default void preRegister() {}

  default void postRegister() {}

  @Nullable
  ResourceLocation getRegistryName();

  V setRegistryName(ResourceLocation name);

  Class<V> getRegistryType();

  @Getter
  abstract class RegistrySettings<T> extends BaseSettings<T> {

    protected final List<TagKey> tagsKey = new ObjectArrayList<>();
    protected String registryKey;
    protected boolean notRegister = false;

    public T registryKey(String registryKey) {
      this.registryKey = Preconditions.checkNotNull(registryKey, "registryKey");
      return this.self();
    }

    public T tag(TagKey tagKey) {
      this.tagsKey.add(tagKey);
      return this.self();
    }

    public T notRegister() {
      this.notRegister = true;
      return this.self();
    }

    public T notRegister(boolean notRegister) {
      this.notRegister = notRegister;
      return this.self();
    }
  }
}
