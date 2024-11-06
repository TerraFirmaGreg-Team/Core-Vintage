package su.terrafirmagreg.datafix.mapping;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;

import net.dries007.tfc.objects.blocks.BlocksTFC;

public class BlockMissingMappingHandler {

  public static void onRemappingBlock(final RegistryEvent.MissingMappings<Block> event) {
    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

      if (mappingKey.equals("tfcalloycalc:alloy_calculator")) {
        mapping.remap(BlocksTFC.ALLOY_CALCULATOR);
      }
    });
  }

  public static void onRemappingItem(final RegistryEvent.MissingMappings<Item> event) {

    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

      if (mappingKey.equals("tfcalloycalc:alloy_calculator")) {
        mapping.remap(Item.getItemFromBlock(BlocksTFC.ALLOY_CALCULATOR));
      }
    });
  }

}
