package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
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

  public static void onRegister(IRegistryRegistrar registrar) {

    BLADDER = registrar.addItem(new ItemAnimalBladder());
    WOOL = registrar.addItem(new ItemAnimalWool());
    WOOL_YARN = registrar.addItem(new ItemAnimalWoolYarn());
    WOOL_CLOTH = registrar.addItem(new ItemAnimalWoolCloth());
    SILK_CLOTH = registrar.addItem(new ItemAnimalSilkCloth());
    HALTER = registrar.addItem(new ItemAnimalHalter());

  }
}
