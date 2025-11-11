package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.framework.manager.content.ContentManager;

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
  /**
   * Универсальный предикат для сравнения путей, который поддерживает: 1. Обычное сравнение (полное или частичное совпадение концов путей) 2. TFC-стиль путей (сравнение только типа и материала, игнорируя модификатор)
   * <p>
   * Примеры совпадений: - path: "wood/bookshelf/mahogany" и key: "bookshelf/mahogany" -> true - path: "tfc:wood/bookshelf/mahogany" и key: "wood/bookshelf/mahogany" -> true - path: "wood/bookshelf/mahogany" и key:
   * "tfg:wood/bookshelf/mahogany" -> true - path: "wood/bookshelf/mahogany" и key: "wood/bookshelf/mahogany" -> true
   */
  public static final BiPredicate<String, String> variantPredicate = (path, key) -> {
    // Удаляем namespace, если он есть
    path = path.contains(":") ? path.split(":")[1] : path;
    key = key.contains(":") ? key.split(":")[1] : key;

    String[] pathParts = path.split("/");
    String[] keyParts = key.split("/");

    // Если оба пути имеют TFC-стиль (минимум 3 части), сравниваем только тип и материал
    if (pathParts.length >= 3 && keyParts.length >= 3) {
      // Сравниваем предпоследнюю и последнюю части (тип и материал)
      return pathParts[pathParts.length - 2].equals(keyParts[keyParts.length - 2]) &&  // тип (bookshelf, button и т.д.)
             pathParts[pathParts.length - 1].equals(keyParts[keyParts.length - 1]);     // материал (mahogany, granite и т.д.)
    }

    // Для обычных путей используем сравнение с конца
    if (keyParts.length > pathParts.length) {
      return false;
    }

    // Сравниваем с конца, начиная с последнего элемента keyParts
    for (int i = 1; i <= keyParts.length; i++) {
      if (!pathParts[pathParts.length - i].equals(keyParts[keyParts.length - i])) {
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
      ContentManager.LOGGER.warn("Failed to remap {}: target object not found in registry", resourceLocation);
      mapping.warn();
      return;
    }

    if (mapping.registry.getRegistrySuperType() == registryObject.getRegistryType()) {
      mapping.remap(registryObject);
      ContentManager.LOGGER.info("Remapped {} to {}", mapping.key, resourceLocation);
      return;
    }

    mapping.warn();
    ContentManager.LOGGER.warn("Failed to remap {}: type mismatch ({} vs {})",
      mapping.key,
      mapping.registry.getRegistrySuperType(),
      resourceLocation);


  }


  public static <T extends IForgeRegistryEntry<T>> RemapBuilder<T> builder(RegistryEvent.MissingMappings<T> mappings) {
    return new RemapBuilder<>(mappings);
  }


  public static class RemapBuilder<T extends IForgeRegistryEntry<T>> {

    final Multimap<String, ResourceLocation> multimap = ArrayListMultimap.create();


    RegistryEvent.MissingMappings<T> mappings;
    Logger logger;
    BiPredicate<String, String> comparisonRule = endsWithPredicate;

    public RemapBuilder(RegistryEvent.MissingMappings<T> mappings) {
      this.mappings = mappings;
    }


    public RemapBuilder<T> comparisonRule(BiPredicate<String, String> comparisonRule) {
      this.comparisonRule = comparisonRule;
      return this;
    }

    public RemapBuilder<T> logger(Logger logger) {
      this.logger = logger;
      return this;
    }

    public RemapBuilder<T> put(String key, T values) {
      multimap.put(key, values.getRegistryName());
      return this;
    }

    public RemapBuilder<T> put(String key, Collection<? extends IForgeRegistryEntry<?>> values) {

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
