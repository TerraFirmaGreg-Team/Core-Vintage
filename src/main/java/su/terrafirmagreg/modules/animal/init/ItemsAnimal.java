package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalBladder;
import su.terrafirmagreg.modules.animal.object.item.ItemAnimalHalter;

import java.util.function.Supplier;

public final class ItemsAnimal {

  public static Supplier<ItemAnimalBladder> BLADDER;
  public static Supplier<ItemAnimalHalter> HALTER;


  public static void onRegister(IRegistryManager registry) {

    BLADDER = registry.item(new ItemAnimalBladder());
    HALTER = registry.item(new ItemAnimalHalter());


  }
}
