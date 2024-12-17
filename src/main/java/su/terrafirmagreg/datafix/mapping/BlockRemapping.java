package su.terrafirmagreg.datafix.mapping;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class BlockRemapping extends AbstractRemapping {


  @SubscribeEvent
  public static void onRemappingBlock(final RegistryEvent.MissingMappings<Block> event) {

    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

      if (BLOCK_MAP.containsKey(mappingPath)) {
        mapping.remap(BLOCK_MAP.get(mappingPath));
      }

      if (MOD_ID_SET.contains(mappingNamespace)) {
        mapping.ignore();
      }
    });
  }

}
