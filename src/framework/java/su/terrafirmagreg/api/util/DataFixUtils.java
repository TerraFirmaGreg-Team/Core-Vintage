package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.apache.logging.log4j.Logger;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

@UtilityClass
public final class DataFixUtils {

  public static final Set<String> MOD_ID_SET = new ObjectOpenHashSet<>() {{
    add(ModIDs.TFCF);
    add(ModIDs.TFC);
    add(ModIDs.TFCTECH);
    add(ModIDs.FL);
    add(ModIDs.TFCTOWERHEAT);
    add(ModIDs.PUDDLES);
    add(ModIDs.AGEDDRINKS);
    add(ModIDs.WATERFLASKS);
    add(ModIDs.CAFFEINEADDON);
    add(ModIDs.CELLARS);
  }};

  public static <T extends IForgeRegistryEntry<T>> void remap(RegistryEvent.MissingMappings<T> event, Logger logger, Map<String, Supplier<T>> map) {

    event.getAllMappings().stream()
      .filter(e -> MOD_ID_SET.contains(e.key.getNamespace()))
      .forEach(mapping -> {
        String mappingKey = mapping.key.toString();
        String mappingPath = mapping.key.getPath();
        map.forEach((key, value) -> {
          if (mappingPath.endsWith(key)) {
            var object = value.get();
            if (object == null) {
              logger.error("Failed to map {}", key);
              return;
            }
            logger.info("Mapped {} to {}", mappingKey, object.getRegistryName());
            mapping.remap(object);
            map.remove(key);
          }
        });
      });
  }


}
