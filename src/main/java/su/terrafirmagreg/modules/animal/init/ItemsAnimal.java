package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalBladder;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalHalter;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalSilkCloth;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalWool;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalWoolCloth;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalWoolYarn;

public final class ItemsAnimal {

  public static ItemAnimalBladder BLADDER;
  public static ItemAnimalWool WOOL;
  public static ItemAnimalWoolYarn WOOL_YARN;
  public static ItemAnimalWoolCloth WOOL_CLOTH;
  public static ItemAnimalSilkCloth SILK_CLOTH;
  public static ItemAnimalHalter HALTER;

  public static void onRegister(RegistryManager registryManager) {

    BLADDER = registryManager.item(new ItemAnimalBladder());
    WOOL = registryManager.item(new ItemAnimalWool());
    WOOL_YARN = registryManager.item(new ItemAnimalWoolYarn());
    WOOL_CLOTH = registryManager.item(new ItemAnimalWoolCloth());
    SILK_CLOTH = registryManager.item(new ItemAnimalSilkCloth());
    HALTER = registryManager.item(new ItemAnimalHalter());

  }
}
