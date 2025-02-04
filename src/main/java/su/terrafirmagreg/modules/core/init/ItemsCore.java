package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.item.ItemDebug;
import su.terrafirmagreg.modules.core.object.item.ItemSilkCloth;
import su.terrafirmagreg.modules.core.object.item.ItemWool;
import su.terrafirmagreg.modules.core.object.item.ItemWoolCloth;
import su.terrafirmagreg.modules.core.object.item.ItemWoolYarn;

import java.util.function.Supplier;

public final class ItemsCore {

  public static Supplier<ItemDebug> WAND;
  public static Supplier<ItemWool> WOOL;
  public static Supplier<ItemWoolYarn> WOOL_YARN;
  public static Supplier<ItemWoolCloth> WOOL_CLOTH;
  public static Supplier<ItemSilkCloth> SILK_CLOTH;

  public static void onRegister(IRegistryManager registry) {

    WAND = registry.item(new ItemDebug());
    WOOL = registry.item(new ItemWool());
    WOOL_YARN = registry.item(new ItemWoolYarn());
    WOOL_CLOTH = registry.item(new ItemWoolCloth());
    SILK_CLOTH = registry.item(new ItemSilkCloth());
  }
}
