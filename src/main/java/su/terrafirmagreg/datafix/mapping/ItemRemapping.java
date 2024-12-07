package su.terrafirmagreg.datafix.mapping;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.items.ItemGem;
import net.dries007.tfc.objects.items.ItemPowder;

import java.util.HashMap;
import java.util.Map;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class ItemRemapping {

  private static final Map<String, Item> mappings = new HashMap<>() {{
    put("gem/amber", ItemGem.get(Gem.AMBER));
    put("powder/pearl", ItemPowder.get(Powder.PEARL));
    put("powder/black_pearl", ItemPowder.get(Powder.BLACK_PEARL));
  }};

  @SubscribeEvent
  public static void onRemappingItem(final RegistryEvent.MissingMappings<Item> event) {

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
