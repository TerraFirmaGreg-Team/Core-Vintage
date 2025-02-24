package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.core.object.item.ItemDebugWand;
import su.terrafirmagreg.modules.core.object.item.ItemGlassShard;
import su.terrafirmagreg.modules.core.object.item.ItemIceShard;
import su.terrafirmagreg.modules.core.object.item.ItemStraw;
import su.terrafirmagreg.modules.core.object.item.ItemWoodAsh;

import net.minecraft.item.Item;

import java.util.function.Supplier;

public final class ItemsCore {

  public static Supplier<ItemDebugWand> DEBUG_WAND;
  public static Supplier<Item> GLUE;
  public static Supplier<ItemGlassShard> GLASS_SHARD;
  public static Supplier<ItemIceShard> ICE_SHARD;
  public static Supplier<ItemStraw> STRAW;
  public static Supplier<ItemWoodAsh> WOOD_ASH;
  public static Supplier<Item> JAR;
  public static Supplier<Item> MORTAR;

  public static void onRegister(IRegistryManager registry) {

    DEBUG_WAND = registry.item(new ItemDebugWand());
    WOOD_ASH = registry.item(new ItemWoodAsh());
    STRAW = registry.item(new ItemStraw());
    GLASS_SHARD = registry.item(new ItemGlassShard());
    ICE_SHARD = registry.item(new ItemIceShard());
  }
}
