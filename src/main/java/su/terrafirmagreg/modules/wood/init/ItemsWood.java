package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.wood.api.types.variant.item.WoodItemVariant;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodAnimalCart;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodBoat;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodBucket;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodLumber;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodMisc;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodPlowCart;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodSupplyCart;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodWheel;

public final class ItemsWood {

  public static WoodItemVariant BOAT;
  public static WoodItemVariant LUMBER;
  public static WoodItemVariant WHEEL;
  public static WoodItemVariant SUPPLY_CART;
  public static WoodItemVariant ANIMAL_CART;
  public static WoodItemVariant PLOW_CART;

  public static ItemWoodMisc STICK_BUNDLE;
  public static ItemWoodMisc STICK_BUNCH;
  public static ItemWoodBucket BUCKET;

  public static void onRegister(RegistryManager registryManager) {
    BOAT = WoodItemVariant
      .builder("boat")
      .factory(ItemWoodBoat::new)
      .build();

    LUMBER = WoodItemVariant
      .builder("lumber")
      .factory(ItemWoodLumber::new)
      .build();

    WHEEL = WoodItemVariant
      .builder("wheel")
      .factory(ItemWoodWheel::new)
      .build();

    SUPPLY_CART = WoodItemVariant
      .builder("supply_cart")
      .factory(ItemWoodSupplyCart::new)
      .build();

    ANIMAL_CART = WoodItemVariant
      .builder("animal_cart")
      .factory(ItemWoodAnimalCart::new)
      .build();

    PLOW_CART = WoodItemVariant
      .builder("plow_cart")
      .factory(ItemWoodPlowCart::new)
      .build();

    STICK_BUNDLE = registryManager.item(new ItemWoodMisc("stick_bundle", Size.VERY_LARGE, Weight.MEDIUM, "log_wood", "stick_bundle"));
    STICK_BUNCH = registryManager.item(new ItemWoodMisc("stick_bunch", Size.NORMAL, Weight.LIGHT));
    BUCKET = registryManager.item(new ItemWoodBucket());
  }
}
