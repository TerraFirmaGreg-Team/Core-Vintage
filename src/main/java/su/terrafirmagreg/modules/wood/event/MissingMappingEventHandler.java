package su.terrafirmagreg.modules.wood.event;


import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariants;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodAnimalCart;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodBoat;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodPlow;
import su.terrafirmagreg.modules.wood.objects.entities.EntityWoodSupplyCart;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.modules.wood.api.types.type.WoodTypes.ACACIA;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class MissingMappingEventHandler {

	@SubscribeEvent
	public static void onMissingItemMapping(RegistryEvent.MissingMappings<Item> event) {
		for (RegistryEvent.MissingMappings.Mapping<Item> mapping : event.getAllMappings()) {
			String mappingKey = mapping.key.toString();

			if (mappingKey.startsWith("astikorcarts:")) {
				// Astikor Carts
				switch (mappingKey) {
					case "astikorcarts:plowcart":
						mapping.remap(WoodItemVariants.PLOW.get(ACACIA));
						break;
					case "astikorcarts:cargocart":
						mapping.remap(WoodItemVariants.SUPPLY_CART.get(ACACIA));
						break;
					case "astikorcarts:mobcart":
						mapping.remap(WoodItemVariants.ANIMAL_CART.get(ACACIA));
						break;
					case "astikorcarts:wheel":
						mapping.remap(WoodItemVariants.WHEEL.get(ACACIA));
						break;
				}
			} else if (mappingKey.startsWith("tfc:")) {
				for (var type : WoodType.getTypes()) {
					// TFC ItemsBlock
					for (var variant : WoodBlockVariant.getBlockVariants()) {
						var mappingKeyBlock = "tfc:wood/" + variant.toString() + "/" + type.toString();
						var mappingKeyBlockFruit = "tfc:wood/fruit_trees/" + variant + "/" + type;
						if (mappingKey.equals(mappingKeyBlock) || mappingKey.equals(mappingKeyBlockFruit)) {
							mapping.remap(Item.getItemFromBlock(variant.get(type)));
							break;
						}
					}

					// TFC Items
					for (var variant : WoodItemVariant.getWoodItemVariants()) {
						var mappingKeyBlock = "tfc:wood/" + variant.toString() + "/" + type.toString();
						var mappingKeyBlockFruit = "tfc:wood/fruit_trees/" + variant + "/" + type;
						if (mappingKey.equals(mappingKeyBlock) || mappingKey.equals(mappingKeyBlockFruit)) {
							mapping.remap(variant.get(type));
							break;
						}
					}
				}
			} else if (mappingKey.startsWith("tfcflorae:")) {
				for (var type : WoodType.getTypes()) {
					// TFCFlorae ItemsBlock
					for (var variant : WoodBlockVariant.getBlockVariants()) {
						var mappingKeyBlock = "tfcflorae:wood/" + variant.toString() + "/" + type.toString();
						var mappingKeyBlockFruit = "tfcflorae:wood/fruit_tree/" + variant + "/" + type;
						if (mappingKey.equals(mappingKeyBlock) || mappingKey.equals(mappingKeyBlockFruit)) {
							mapping.remap(Item.getItemFromBlock(variant.get(type)));
							break;
						}
					}

					// TFCFlorae Items
					for (var variant : WoodItemVariant.getWoodItemVariants()) {
						var mappingKeyBlock = "tfcflorae:wood/" + variant.toString() + "/" + type.toString();
						var mappingKeyBlockFruit = "tfcflorae:wood/fruit_tree/" + variant + "/" + type;
						if (mappingKey.equals(mappingKeyBlock) || mappingKey.equals(mappingKeyBlockFruit)) {
							mapping.remap(variant.get(type));
							break;
						}
					}
				}
			} else if (mappingKey.startsWith("firmalife:")) {
				for (var type : WoodType.getTypes()) {
					// FirmaLife ItemsBlock
					for (var variant : WoodBlockVariant.getBlockVariants()) {
						var mappingKeyBlock = "firmalife:wood/" + variant.toString() + "/" + type.toString();
						var mappingKeyBlockFruit = "firmalife:wood/fruit_tree/" + variant + "/" + type;
						if (mappingKey.equals(mappingKeyBlock) || mappingKey.equals(mappingKeyBlockFruit)) {
							mapping.remap(Item.getItemFromBlock(variant.get(type)));
							break;
						}
					}

					// FirmaLife Items
					for (var variant : WoodItemVariant.getWoodItemVariants()) {
						var mappingKeyBlock = "firmalife:wood/" + variant.toString() + "/" + type.toString();
						var mappingKeyBlockFruit = "firmalife:wood/fruit_tree/" + variant + "/" + type;
						if (mappingKey.equals(mappingKeyBlock) || mappingKey.equals(mappingKeyBlockFruit)) {
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
				for (var type : WoodType.getTypes()) {
					// TFC ItemsBlock
					for (var variant : WoodBlockVariant.getBlockVariants()) {
						var mappingKeyBlock = "tfc:wood/" + variant.toString() + "/" + type.toString();
						if (mappingKey.equals(mappingKeyBlock)) {
							mapping.remap(variant.get(type));
							break;
						}
					}
				}
			} else if (mappingKey.startsWith("tfcflorae:")) {
				for (var type : WoodType.getTypes()) {
					// TFCFlorae ItemsBlock
					for (var variant : WoodBlockVariant.getBlockVariants()) {
						var mappingKeyBlock = "tfcflorae:wood/" + variant.toString() + "/" + type.toString();
						if (mappingKey.equals(mappingKeyBlock)) {
							mapping.remap(variant.get(type));
							break;
						}
					}
				}
			} else if (mappingKey.startsWith("firmalife:")) {
				for (var type : WoodType.getTypes()) {
					// FirmaLife ItemsBlock
					for (var variant : WoodBlockVariant.getBlockVariants()) {
						var mappingKeyBlock = "firmalife:wood/" + variant.toString() + "/" + type.toString();
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
	public static void onMissingEntityMapping(RegistryEvent.MissingMappings<EntityEntry> event) {
		for (RegistryEvent.MissingMappings.Mapping<EntityEntry> mapping : event.getAllMappings()) {

			switch (mapping.key.toString()) {
				// Astikor Carts
				case "astikorcarts:plowcart":
					mapping.remap(EntityRegistry.getEntry(EntityWoodPlow.class));
					break;
				case "astikorcarts:mobcart":
					mapping.remap(EntityRegistry.getEntry(EntityWoodAnimalCart.class));
					break;
				case "astikorcarts:cargocart":
					mapping.remap(EntityRegistry.getEntry(EntityWoodSupplyCart.class));
					break;
				case "tfc:boat":
					mapping.remap(EntityRegistry.getEntry(EntityWoodBoat.class));
					break;
			}
		}
	}
}
