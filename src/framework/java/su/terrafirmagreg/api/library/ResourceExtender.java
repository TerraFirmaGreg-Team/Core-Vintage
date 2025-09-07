package su.terrafirmagreg.api.library;

import net.minecraft.util.ResourceLocation;

/**
 * Simple helper methods to extend a resource location, either by prefixing or suffixing values
 */
public class ResourceExtender extends ResourceLocation {


  public ResourceExtender(String namespaceIn, String pathIn) {
    super(namespaceIn, pathIn);

  }

  /**
   * Wraps the resource location in the given prefix and suffix
   *
   * @param location Location to extend
   * @param prefix   Path prefix
   * @param suffix   Path suffix
   * @return Location with the given prefix and suffix
   */
  public static ResourceLocation wrap(ResourceLocation location, String prefix, String suffix) {

    return location(location.getNamespace(), prefix + location.getPath() + suffix);
  }

  /**
   * Creates a resource location
   */
  public static ResourceLocation location(String namespace, String path) {

    return new ResourceExtender(namespace, path);
  }

  /**
   * Prefixes the resource location
   *
   * @param location Location to extend
   * @param prefix   Path prefix
   * @return Location with the given prefix
   */
  public static ResourceLocation prefix(ResourceLocation location, String prefix) {

    return location(location.getNamespace(), prefix + location.getPath());
  }

  /**
   * Suffixes the resource location
   *
   * @param location Location to extend
   * @param suffix   Path suffix
   * @return Location with the given suffix
   */
  public static ResourceLocation suffix(ResourceLocation location, String suffix) {

    return location(location.getNamespace(), location.getPath() + suffix);
  }

}
