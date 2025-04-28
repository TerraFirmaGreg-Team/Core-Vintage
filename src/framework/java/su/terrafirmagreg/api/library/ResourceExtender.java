package su.terrafirmagreg.api.library;

import net.minecraft.util.ResourceLocation;

/**
 * Simple helper methods to extend a resource location, either by prefixing or suffixing values
 */
public interface ResourceExtender<T extends ResourceLocation> {

  /**
   * Extender for standard resource locations
   */
  LocationExtender INSTANCE = new LocationExtender() {};

  /**
   * Wraps the resource location in the given prefix and suffix
   *
   * @param location Location to extend
   * @param prefix   Path prefix
   * @param suffix   Path suffix
   * @return Location with the given prefix and suffix
   */
  default T wrap(ResourceLocation location, String prefix, String suffix) {
    return location(location.getNamespace(), prefix + location.getPath() + suffix);
  }

  /**
   * Creates a resource location
   */
  T location(String namespace, String path);

  /**
   * Prefixes the resource location
   *
   * @param location Location to extend
   * @param prefix   Path prefix
   * @return Location with the given prefix
   */
  default T prefix(ResourceLocation location, String prefix) {
    return location(location.getNamespace(), prefix + location.getPath());
  }

  /**
   * Suffixes the resource location
   *
   * @param location Location to extend
   * @param suffix   Path suffix
   * @return Location with the given suffix
   */
  default T suffix(ResourceLocation location, String suffix) {
    return location(location.getNamespace(), location.getPath() + suffix);
  }

  interface LocationExtender extends ResourceExtender<ResourceLocation> {

    @Override
    default ResourceLocation location(String namespace, String path) {
      return new ResourceLocation(namespace, path);
    }
  }
}
