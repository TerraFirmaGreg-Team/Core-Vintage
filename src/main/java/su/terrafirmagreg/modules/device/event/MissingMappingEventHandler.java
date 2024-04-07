package su.terrafirmagreg.modules.device.event;

import su.terrafirmagreg.modules.device.data.ItemsDevice;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public final class MissingMappingEventHandler {

    @SubscribeEvent
    public static void onMissingItemMapping(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
            String mappingKey = mapping.key.toString();
        }
    }

    @SubscribeEvent
    public static void onMissingBlockMapping(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()) {
            String mappingKey = mapping.key.toString();

            switch (mappingKey) {
                case "tfc:firestarter":
                    mapping.remap(Block.getBlockFromItem(ItemsDevice.FIRESTARTER));

                default:
                    break;
            }

        }
    }
}
