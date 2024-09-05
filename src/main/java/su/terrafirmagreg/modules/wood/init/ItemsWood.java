package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodAnimalCart;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodBoat;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodLumber;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodMisc;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodPlowCart;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodSupplyCart;
import su.terrafirmagreg.modules.wood.objects.items.ItemWoodWheel;

import net.minecraft.item.Item;


import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public final class ItemsWood {

  public static final Map<Pair<WoodItemVariant, WoodType>, Item> WOOD_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

  public static WoodItemVariant BOAT;
  public static WoodItemVariant LUMBER;
  public static WoodItemVariant WHEEL;
  public static WoodItemVariant SUPPLY_CART;
  public static WoodItemVariant ANIMAL_CART;
  public static WoodItemVariant PLOW_CART;

  public static ItemWoodMisc STICK_BUNDLE;
  public static ItemWoodMisc STICK_BUNCH;

  public static void onRegister(RegistryManager registry) {
    BOAT = WoodItemVariant
        .builder("boat")
        .setFactory(ItemWoodBoat::new)
        .build();

    LUMBER = WoodItemVariant
        .builder("lumber")
        .setFactory(ItemWoodLumber::new)
        .build();

    WHEEL = WoodItemVariant
        .builder("wheel")
        .setFactory(ItemWoodWheel::new)
        .build();

    SUPPLY_CART = WoodItemVariant
        .builder("supply_cart")
        .setFactory(ItemWoodSupplyCart::new)
        .build();

    ANIMAL_CART = WoodItemVariant
        .builder("animal_cart")
        .setFactory(ItemWoodAnimalCart::new)
        .build();

    PLOW_CART = WoodItemVariant
        .builder("plow_cart")
        .setFactory(ItemWoodPlowCart::new)
        .build();

    STICK_BUNDLE = registry.item(
        new ItemWoodMisc("stick_bundle", Size.VERY_LARGE, Weight.MEDIUM, "log_wood",
            "stick_bundle"));
    STICK_BUNCH = registry.item(new ItemWoodMisc("stick_bunch", Size.NORMAL, Weight.LIGHT));

    registry.items(WOOD_ITEMS.values());
  }
}
