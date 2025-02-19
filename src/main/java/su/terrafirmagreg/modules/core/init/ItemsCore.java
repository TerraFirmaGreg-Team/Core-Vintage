package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.item.ItemDebug;
import su.terrafirmagreg.modules.core.object.item.ItemStraw;
import su.terrafirmagreg.modules.core.object.item.ItemWoodAsh;

import java.util.function.Supplier;

public final class ItemsCore {

  public static Supplier<ItemDebug> WAND;
  public static Supplier<ItemWoodAsh> WOOD_ASH;
  public static Supplier<ItemStraw> STRAW;

  public static void onRegister(IRegistryManager registry) {

    WAND = registry.item(new ItemDebug());
    WOOD_ASH = registry.item(new ItemWoodAsh());
    STRAW = registry.item(new ItemStraw());
  }
}
