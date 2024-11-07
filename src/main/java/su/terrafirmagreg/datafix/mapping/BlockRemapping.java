package su.terrafirmagreg.datafix.mapping;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfc.objects.blocks.BlocksTFC;

import java.util.HashMap;
import java.util.Map;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class BlockRemapping {

  private static final Map<String, Block> mappings = new HashMap<>() {{
    put("alloy_calculator", BlocksTFC.ALLOY_CALCULATOR);
    put("puddle", BlocksTFC.PUDDLE);
  }};

  @SubscribeEvent
  public static void onRemappingBlock(final RegistryEvent.MissingMappings<Block> event) {

    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

      if (mappings.containsKey(mappingPath)) {
        mapping.remap(mappings.get(mappingPath));
      }
    });
  }

  @SubscribeEvent
  public static void onRemappingItem(final RegistryEvent.MissingMappings<Item> event) {

    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

      if (mappings.containsKey(mappingPath)) {
        mapping.remap(Item.getItemFromBlock(mappings.get(mappingPath)));
      }
    });
  }

}
