package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;
import su.terrafirmagreg.modules.wood.content.item.ItemWoodAnimalCart;
import su.terrafirmagreg.modules.wood.content.item.ItemWoodBoat;
import su.terrafirmagreg.modules.wood.content.item.ItemWoodLumber;
import su.terrafirmagreg.modules.wood.content.item.ItemWoodPlowCart;
import su.terrafirmagreg.modules.wood.content.item.ItemWoodSupplyCart;
import su.terrafirmagreg.modules.wood.content.item.ItemWoodWheel;

import java.util.Map;

public class ItemsWood {

  public static Map<WoodType, ItemWoodBoat> BOAT;
  public static Map<WoodType, ItemWoodLumber> LUMBER;
  public static Map<WoodType, ItemWoodWheel> WHEEL;
  public static Map<WoodType, ItemWoodSupplyCart> SUPPLY_CART;
  public static Map<WoodType, ItemWoodAnimalCart> ANIMAL_CART;
  public static Map<WoodType, ItemWoodPlowCart> PLOW_CART;


  public static void onRegister(IContentRegistrar registrar) {
    BOAT = registrar.addItem(ItemWoodBoat::new, WoodType.getTypes());
    LUMBER = registrar.addItem(ItemWoodLumber::new, WoodType.getTypes());
    WHEEL = registrar.addItem(ItemWoodWheel::new, WoodType.getTypes());
    SUPPLY_CART = registrar.addItem(ItemWoodSupplyCart::new, WoodType.getTypes());
    ANIMAL_CART = registrar.addItem(ItemWoodAnimalCart::new, WoodType.getTypes());
    PLOW_CART = registrar.addItem(ItemWoodPlowCart::new, WoodType.getTypes());

  }
}
