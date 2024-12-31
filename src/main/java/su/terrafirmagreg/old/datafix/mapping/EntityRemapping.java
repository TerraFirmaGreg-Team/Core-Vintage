package su.terrafirmagreg.old.datafix.mapping;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class EntityRemapping extends AbstractRemapping {


  @SubscribeEvent
  public static void onRemappingEntity(final RegistryEvent.MissingMappings<EntityEntry> event) {
    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

      if (ENTITY_ENTRY_MAP.containsKey(mappingPath)) {
        mapping.remap(ENTITY_ENTRY_MAP.get(mappingPath));
      }

      if (MOD_ID_SET.contains(mappingNamespace)) {
        mapping.ignore();
      }
    });
  }
}
