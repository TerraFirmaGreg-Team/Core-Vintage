package su.terrafirmagreg.old.datafix.mapping;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class ItemRemapping extends AbstractRemapping {


  @SubscribeEvent
  public static void onRemappingItem(final RegistryEvent.MissingMappings<Item> event) {

    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

      if (ITEM_MAP.containsKey(mappingPath)) {
        mapping.remap(ITEM_MAP.get(mappingPath));
      }

      if (BLOCK_MAP.containsKey(mappingPath)) {
        mapping.remap(Item.getItemFromBlock(BLOCK_MAP.get(mappingPath)));
      }

      if (MOD_ID_SET.contains(mappingNamespace)) {
        mapping.ignore();
      }
    });
  }
}
