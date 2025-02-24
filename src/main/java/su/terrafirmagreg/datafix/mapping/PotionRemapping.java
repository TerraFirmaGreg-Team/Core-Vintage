package su.terrafirmagreg.datafix.mapping;

import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class PotionRemapping extends AbstractRemapping {


  @SubscribeEvent
  public static void onRemapping(final RegistryEvent.MissingMappings<PotionType> event) {

    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

      POTION_MAP.forEach((key, value) -> {
        if (mappingPath.endsWith(key)) {
          mapping.remap(value.get());
        }
      });

      if (MOD_ID_SET.contains(mappingNamespace)) {
        mapping.ignore();
      }
    });
  }

}
