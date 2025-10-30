package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.animal.content.item.ItemAnimalBladder;
import su.terrafirmagreg.modules.animal.content.item.ItemAnimalHalter;
import su.terrafirmagreg.modules.animal.content.item.ItemAnimalSilkCloth;
import su.terrafirmagreg.modules.animal.content.item.ItemAnimalWool;
import su.terrafirmagreg.modules.animal.content.item.ItemAnimalWoolCloth;
import su.terrafirmagreg.modules.animal.content.item.ItemAnimalWoolYarn;

public final class ItemsAnimal {

  public static ItemAnimalBladder BLADDER;
  public static ItemAnimalWool WOOL;
  public static ItemAnimalWoolYarn WOOL_YARN;
  public static ItemAnimalWoolCloth WOOL_CLOTH;
  public static ItemAnimalSilkCloth SILK_CLOTH;
  public static ItemAnimalHalter HALTER;

  public static void onRegister(IContentRegistrar registrar) {

    BLADDER = registrar.addItem("product/bladder", new ItemAnimalBladder());
    WOOL = registrar.addItem("product/wool", new ItemAnimalWool());
    WOOL_YARN = registrar.addItem("product/wool_yarn", new ItemAnimalWoolYarn());
    WOOL_CLOTH = registrar.addItem("product/wool_cloth", new ItemAnimalWoolCloth());
    SILK_CLOTH = registrar.addItem("product/silk_cloth", new ItemAnimalSilkCloth());
    HALTER = registrar.addItem("halter", new ItemAnimalHalter());

  }
}
