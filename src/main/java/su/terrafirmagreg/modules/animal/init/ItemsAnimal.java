package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalBladder;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalHalter;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalSilkCloth;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalWool;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalWoolCloth;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalWoolYarn;

import java.util.function.Supplier;

public final class ItemsAnimal {

  public static Supplier<ItemAnimalBladder> BLADDER;
  public static Supplier<ItemAnimalWool> WOOL;
  public static Supplier<ItemAnimalWoolYarn> WOOL_YARN;
  public static Supplier<ItemAnimalWoolCloth> WOOL_CLOTH;
  public static Supplier<ItemAnimalSilkCloth> SILK_CLOTH;
  public static Supplier<ItemAnimalHalter> HALTER;

  public static void onRegister(IRegistryManager registry) {

    BLADDER = registry.item(new ItemAnimalBladder());
    WOOL = registry.item(new ItemAnimalWool());
    WOOL_YARN = registry.item(new ItemAnimalWoolYarn());
    WOOL_CLOTH = registry.item(new ItemAnimalWoolCloth());
    SILK_CLOTH = registry.item(new ItemAnimalSilkCloth());
    HALTER = registry.item(new ItemAnimalHalter());

  }
}
