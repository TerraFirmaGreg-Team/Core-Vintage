package su.terrafirmagreg.datafix.mapping;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

public class ItemMissingMappingHandler {

  public static void onRemappingItem(final RegistryEvent.MissingMappings<Item> event) {

    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();


    });
  }
}
