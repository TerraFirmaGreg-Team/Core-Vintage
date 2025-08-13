package su.terrafirmagreg.api.util;


import su.terrafirmagreg.api.library.tag.TagKey;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;

import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public final class RegistryUtils {


  public static <V extends IForgeRegistryEntry<V>> boolean isTag(V entry, TagKey... tagKey) {
    for (TagKey key : tagKey) {
      if (isTag(entry, key)) {
        return true;
      }
    }
    return false;
  }

  public static <V extends IForgeRegistryEntry<V>> boolean isTag(V entry, TagKey tagKey) {
    return getTags(entry).contains(tagKey);
  }

  public static <V extends IForgeRegistryEntry<V>> List<TagKey> getTags(V entry) {
    if (entry instanceof IRegistryEntry<?, ?> registryEntry) {
      return registryEntry.getSettings().getTagsKey();
    } else {
      return new ObjectArrayList<>();
    }
  }
}
