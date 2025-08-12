package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.registry.RegistryManager;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.logging.log4j.Logger;

import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiPredicate;

@UtilityClass
public final class DataFixUtils {

  public static final Set<String> MOD_ID_SET = new ObjectOpenHashSet<>() {{
    add(ModIDs.TFCF);
    add(ModIDs.TFC);
    add(ModIDs.TFCTECH);
    add(ModIDs.TFCTHINGS);
    add(ModIDs.FL);
    add(ModIDs.TFCTOWERHEAT);
    add(ModIDs.PUDDLES);
    add(ModIDs.AGEDDRINKS);
    add(ModIDs.WATERFLASKS);
    add(ModIDs.CAFFEINEADDON);
    add(ModIDs.CELLARS);
  }};

  public static final BiPredicate<String, String> endsWithPredicate = String::endsWith;
  public static final BiPredicate<String, String> exactMatchPredicate = String::equals;
  public static final BiPredicate<String, String> containsPredicate = String::contains;
  public static final BiPredicate<String, String> caseInsensitivePredicate = (path, key) -> path.toLowerCase().contains(key.toLowerCase());
  public static final BiPredicate<String, String> variantPredicate = (path, key) -> {
    String[] pathParts = path.split("/");
    String[] keyParts = key.split("/");

    if (keyParts.length < 2) {
      return false;
    }

    if (pathParts.length < keyParts.length) {
      return false;
    }

    for (int i = 0; i < keyParts.length; i++) {
      int pathIndex = pathParts.length - keyParts.length + i;
      if (!pathParts[pathIndex].equals(keyParts[i])) {
        return false;
      }
    }

    return true;
  };

  public static <T extends IForgeRegistryEntry<T>> void processMapping(RegistryEvent.MissingMappings.Mapping<T> mapping, ResourceLocation resourceLocation) {

    if (resourceLocation == null) {
      mapping.warn();
      return;
    }

    // Получаем объект из registry
    T registryObject = mapping.registry.getValue(resourceLocation);
    if (registryObject == null) {
      RegistryManager.LOGGER.warn("Failed to remap {}: target object not found in registry", resourceLocation);
      mapping.warn();
      return;
    }

    if (mapping.registry.getRegistrySuperType() == registryObject.getRegistryType()) {
      mapping.remap(registryObject);
      RegistryManager.LOGGER.info("Remapped {} to {}", mapping.key, resourceLocation);
      return;
    }

    mapping.warn();
    RegistryManager.LOGGER.warn("Failed to remap {}: type mismatch ({} vs {})",
      mapping.key,
      mapping.registry.getRegistrySuperType(),
      resourceLocation);


  }


  public static <T extends IForgeRegistryEntry<T>> RemapBuilder builder(RegistryEvent.MissingMappings<T> mappings) {
    return new RemapBuilder(mappings);
  }


  public static class RemapBuilder {

    Multimap<String, ResourceLocation> multimap = ArrayListMultimap.create();


    RegistryEvent.MissingMappings<?> mappings;
    Logger logger;
    BiPredicate<String, String> comparisonRule = endsWithPredicate;

    public RemapBuilder(RegistryEvent.MissingMappings<?> mappings) {
      this.mappings = mappings;
    }


    public RemapBuilder comparisonRule(BiPredicate<String, String> comparisonRule) {
      this.comparisonRule = comparisonRule;
      return this;
    }

    public RemapBuilder logger(Logger logger) {
      this.logger = logger;
      return this;
    }

    public RemapBuilder put(String key, IForgeRegistryEntry<?> values) {
      multimap.put(key, values.getRegistryName());
      return this;
    }

    public RemapBuilder put(String key, Collection<? extends IForgeRegistryEntry<?>> values) {

      values.forEach(value -> multimap.put(key, value.getRegistryName()));
      return this;
    }

    // Обработка события
    public void build() {

//      mappings.getAllMappings()
//        .stream()
//        .filter(mapping -> Mods.contains(mapping.key.getNamespace()))
//        .forEach(mapping -> {
//          String mappingPath = mapping.key.getPath();
//
//          multimap.forEach((key, value) -> {
//            if (comparisonRule.test(mappingPath, key)) {
//
//              processMapping(mapping, value);
//            }
//
//          });
//        });
    }


  }


}
