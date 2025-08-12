package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodAnimalCart;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodBoat;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodLumber;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodPlowCart;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodSupplyCart;
import su.terrafirmagreg.modules.wood.object.item.ItemWoodWheel;

import java.util.Map;

public class ItemsWood {

  public static Map<WoodType, ItemWoodBoat> BOAT;
  public static Map<WoodType, ItemWoodLumber> LUMBER;
  public static Map<WoodType, ItemWoodWheel> WHEEL;
  public static Map<WoodType, ItemWoodSupplyCart> SUPPLY_CART;
  public static Map<WoodType, ItemWoodAnimalCart> ANIMAL_CART;
  public static Map<WoodType, ItemWoodPlowCart> PLOW_CART;


  public static void onRegister(IRegistryRegistrar registrar) {
    BOAT = registrar.addItem(ItemWoodBoat::new, WoodType.getTypes());
    LUMBER = registrar.addItem(ItemWoodLumber::new, WoodType.getTypes());
    WHEEL = registrar.addItem(ItemWoodWheel::new, WoodType.getTypes());
    SUPPLY_CART = registrar.addItem(ItemWoodSupplyCart::new, WoodType.getTypes());
    ANIMAL_CART = registrar.addItem(ItemWoodAnimalCart::new, WoodType.getTypes());
    PLOW_CART = registrar.addItem(ItemWoodPlowCart::new, WoodType.getTypes());

  }
}
