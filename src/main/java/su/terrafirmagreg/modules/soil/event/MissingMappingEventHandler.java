package su.terrafirmagreg.modules.soil.event;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariant;

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

            if (mappingKey.startsWith("tfc:")) {
                for (var type : SoilType.getTypes()) {
                    // TFC ItemsBlock
                    for (var variant : SoilBlockVariant.getBlockVariants()) {
                        var mappingKeyBlock = "tfc:" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock)) {
                            mapping.remap(Item.getItemFromBlock(variant.get(type)));
                            break;
                        }
                    }

                    // TFC Items
                    for (var variant : SoilItemVariant.getItemVariants()) {
                        var mappingKeyBlock = "tfc:" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock)) {
                            mapping.remap(variant.get(type));
                            break;
                        }
                    }
                }
            } else if (mappingKey.startsWith("tfcflorae:")) {
                for (var type : SoilType.getTypes()) {
                    // TFCFlorae ItemsBlock
                    for (var variant : SoilBlockVariant.getBlockVariants()) {
                        var mappingKeyBlock = "tfcflorae:" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock)) {
                            mapping.remap(Item.getItemFromBlock(variant.get(type)));
                            break;
                        }
                    }

                    // TFCFlorae Items
                    for (var variant : SoilItemVariant.getItemVariants()) {
                        var mappingKeyBlock = "tfcflorae:" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock)) {
                            mapping.remap(variant.get(type));
                            break;
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMissingBlockMapping(RegistryEvent.MissingMappings<Block> event) {
        for (RegistryEvent.MissingMappings.Mapping<Block> mapping : event.getAllMappings()) {
            String mappingKey = mapping.key.toString();

            if (mappingKey.startsWith("tfc:")) {
                for (var type : SoilType.getTypes()) {
                    // TFC ItemsBlock
                    for (var variant : SoilBlockVariant.getBlockVariants()) {
                        var mappingKeyBlock = "tfc:" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock)) {
                            mapping.remap(variant.get(type));
                            break;
                        }
                    }
                }
            } else if (mappingKey.startsWith("tfcflorae:")) {
                for (var type : SoilType.getTypes()) {
                    // TFCFlorae ItemsBlock
                    for (var variant : SoilBlockVariant.getBlockVariants()) {
                        var mappingKeyBlock = "tfcflorae:" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock)) {
                            mapping.remap(variant.get(type));
                            break;
                        }
                    }
                }
            }
        }
    }
}
