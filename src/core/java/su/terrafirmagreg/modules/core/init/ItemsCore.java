package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.modules.core.object.item.ItemDebugWand;
import su.terrafirmagreg.modules.core.object.item.ItemGlassShard;
import su.terrafirmagreg.modules.core.object.item.ItemIceShard;
import su.terrafirmagreg.modules.core.object.item.ItemMortar;
import su.terrafirmagreg.modules.core.object.item.ItemStraw;
import su.terrafirmagreg.modules.core.object.item.ItemWoodAsh;

import net.minecraft.item.Item;

public final class ItemsCore {

  public static ItemDebugWand DEBUG_WAND;
  public static Item GLUE;
  public static ItemGlassShard GLASS_SHARD;
  public static ItemIceShard ICE_SHARD;
  public static ItemStraw STRAW;
  public static ItemWoodAsh WOOD_ASH;
  public static Item JAR;
  public static Item MORTAR;

  public static void onRegister(IRegistryRegistrar registry) {

    DEBUG_WAND = registry.addItem(new ItemDebugWand());
    GLASS_SHARD = registry.addItem(new ItemGlassShard());
    ICE_SHARD = registry.addItem(new ItemIceShard());
    STRAW = registry.addItem(new ItemStraw());
    WOOD_ASH = registry.addItem(new ItemWoodAsh());
    MORTAR = registry.addItem(new ItemMortar());


  }
}
