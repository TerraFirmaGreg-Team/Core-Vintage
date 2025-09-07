package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.modules.core.content.item.ItemDebugWand;
import su.terrafirmagreg.modules.core.content.item.ItemGlassShard;
import su.terrafirmagreg.modules.core.content.item.ItemGlue;
import su.terrafirmagreg.modules.core.content.item.ItemIceShard;
import su.terrafirmagreg.modules.core.content.item.ItemMortar;
import su.terrafirmagreg.modules.core.content.item.ItemStickBunch;
import su.terrafirmagreg.modules.core.content.item.ItemStickBundle;
import su.terrafirmagreg.modules.core.content.item.ItemStraw;
import su.terrafirmagreg.modules.core.content.item.ItemWoodAsh;

import net.minecraft.item.Item;

public final class ItemsCore {

  public static ItemDebugWand DEBUG_WAND;
  public static ItemGlue GLUE;
  public static ItemGlassShard GLASS_SHARD;
  public static ItemIceShard ICE_SHARD;
  public static ItemStraw STRAW;
  public static ItemWoodAsh WOOD_ASH;
  public static Item JAR;
  public static ItemMortar MORTAR;
  public static ItemStickBundle STICK_BUNDLE;
  public static ItemStickBunch STICK_BUNCH;


  public static void onRegister(IContentRegistrar registrar) {

    DEBUG_WAND = registrar.addItem(new ItemDebugWand());
    GLUE = registrar.addItem(new ItemGlue());
    GLASS_SHARD = registrar.addItem(new ItemGlassShard());
    ICE_SHARD = registrar.addItem(new ItemIceShard());
    STRAW = registrar.addItem(new ItemStraw());
    WOOD_ASH = registrar.addItem(new ItemWoodAsh());
    MORTAR = registrar.addItem(new ItemMortar());
    STICK_BUNDLE = registrar.addItem(new ItemStickBundle());
    STICK_BUNCH = registrar.addItem(new ItemStickBunch());


  }
}
