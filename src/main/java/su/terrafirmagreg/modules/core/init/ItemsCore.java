package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.item.ItemDebug;

import net.minecraft.item.Item;

import java.util.function.Supplier;

public final class ItemsCore {

  public static Supplier<Item> WAND;

  public static void onRegister(IRegistryManager registry) {

    WAND = registry.item(new ItemDebug());
  }
}
