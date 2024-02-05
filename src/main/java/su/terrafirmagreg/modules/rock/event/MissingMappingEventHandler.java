package su.terrafirmagreg.modules.rock.event;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariant;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import static su.terrafirmagreg.Tags.MOD_ID;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class MissingMappingEventHandler {

    @SubscribeEvent
    public static void onMissingItemMapping(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
            String mappingKey = mapping.key.toString();

            if (mappingKey.startsWith("tfc:")) {
                for (var type : RockType.getTypes()) {
                    // TFC ItemsBlock
                    for (var variant : RockBlockVariant.getBlockVariants()) {
                        var mappingKeyBlock = "tfc:" + variant.toString() + "/" + type.toString();
                        var mappingKeyBlock2 = "tfc:stone/" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock) || mappingKey.equals(mappingKeyBlock2)) {
                            mapping.remap(Item.getItemFromBlock(BlocksRock.getBlock(variant, type)));
                            break;
                        }
                    }

                    // TFC Items
                    for (var variant : RockItemVariant.getItemVariants()) {
                        var mappingKeyBlock = "tfc:" + variant.toString() + "/" + type.toString();
                        var mappingKeyBlock2 = "tfc:stone/" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock) || mappingKey.equals(mappingKeyBlock2)) {
                            mapping.remap(ItemsRock.getItem(variant, type));
                            break;
                        }
                    }
                }
            } else if (mappingKey.startsWith("tfcflorae:")) {
                for (var type : RockType.getTypes()) {
                    // TFCFlorae ItemsBlock
                    for (var variant : RockBlockVariant.getBlockVariants()) {
                        var mappingKeyBlock = "tfcflorae:" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock)) {
                            mapping.remap(Item.getItemFromBlock(BlocksRock.getBlock(variant, type)));
                            break;
                        }
                    }

                    // TFCFlorae Items
                    for (var variant : RockItemVariant.getItemVariants()) {
                        var mappingKeyBlock = "tfcflorae:" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock)) {
                            mapping.remap(ItemsRock.getItem(variant, type));
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
                for (var type : RockType.getTypes()) {
                    // TFC ItemsBlock
                    for (var variant : RockBlockVariant.getBlockVariants()) {
                        var mappingKeyBlock = "tfc:" + variant.toString() + "/" + type.toString();
                        var mappingKeyBlock2 = "tfc:stone/" + variant + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock) || mappingKey.equals(mappingKeyBlock2)) {
                            mapping.remap(BlocksRock.getBlock(variant, type));
                            break;
                        }
                    }
                }
            } else if (mappingKey.startsWith("tfcflorae:")) {
                for (var type : RockType.getTypes()) {
                    // TFCFlorae ItemsBlock
                    for (var variant : RockBlockVariant.getBlockVariants()) {
                        var mappingKeyBlock = "tfcflorae:" + variant.toString() + "/" + type.toString();
                        if (mappingKey.equals(mappingKeyBlock)) {
                            mapping.remap(BlocksRock.getBlock(variant, type));
                            break;
                        }
                    }
                }
            }
        }
    }
}
