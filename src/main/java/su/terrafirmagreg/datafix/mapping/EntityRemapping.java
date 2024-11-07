package su.terrafirmagreg.datafix.mapping;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.HashMap;
import java.util.Map;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class EntityRemapping {

  private static final Map<String, EntityEntry> mappings = new HashMap<>() {{

  }};

  @SubscribeEvent
  public static void onRemappingEntity(final RegistryEvent.MissingMappings<EntityEntry> event) {
    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

      if (mappings.containsKey(mappingPath)) {
        mapping.remap(mappings.get(mappingPath));
      }
    });
  }
}
