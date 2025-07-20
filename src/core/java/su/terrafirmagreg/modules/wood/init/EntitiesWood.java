package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodAnimalCart.EntityTypeWoodAnimalCart;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodBoat.EntityTypeWoodBoat;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodPlowCart.EntityTypeWoodPlowCart;
import su.terrafirmagreg.modules.wood.object.entity.EntityWoodSupplyCart.EntityTypeWoodSupplyCart;

public class EntitiesWood {

  public static EntityTypeWoodBoat BOAT;
  public static EntityTypeWoodAnimalCart ANIMAL_CART;
  public static EntityTypeWoodSupplyCart SUPPLY_CART;
  public static EntityTypeWoodPlowCart PLOW_CART;

  public static void onRegister(IRegistryRegistrar registrar) {

    BOAT = registrar.addEntity(new EntityTypeWoodBoat());
    ANIMAL_CART = registrar.addEntity(new EntityTypeWoodAnimalCart());
    SUPPLY_CART = registrar.addEntity(new EntityTypeWoodSupplyCart());
    PLOW_CART = registrar.addEntity(new EntityTypeWoodPlowCart());

  }
}
