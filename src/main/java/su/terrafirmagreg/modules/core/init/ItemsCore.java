package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.object.item.ItemDebug;
import su.terrafirmagreg.modules.core.object.item.ItemGlassShard;
import su.terrafirmagreg.modules.core.object.item.ItemGlue;
import su.terrafirmagreg.modules.core.object.item.ItemIceShard;
import su.terrafirmagreg.modules.core.object.item.ItemJar;
import su.terrafirmagreg.modules.core.object.item.ItemMortar;
import su.terrafirmagreg.modules.core.object.item.ItemPackedIceShard;
import su.terrafirmagreg.modules.core.object.item.ItemSeaIceShard;
import su.terrafirmagreg.modules.core.object.item.ItemStraw;
import su.terrafirmagreg.modules.core.object.item.ItemWoodAsh;

public final class ItemsCore {

  public static ItemDebug WAND;
  public static ItemGlue GLUE;
  public static ItemGlassShard GLASS_SHARD;
  public static ItemIceShard ICE_SHARD;
  public static ItemSeaIceShard SEA_ICE_SHARD;
  public static ItemPackedIceShard PACKED_ICE_SHARD;
  public static ItemStraw STRAW;
  public static ItemWoodAsh WOOD_ASH;
  public static ItemJar JAR;
  public static ItemMortar MORTAR;

  public static void onRegister(RegistryManager registryManager) {

    //==== Other =================================================================================================//

    WAND = registryManager.item(new ItemDebug());
    GLUE = registryManager.item(new ItemGlue());
    GLASS_SHARD = registryManager.item(new ItemGlassShard());
    ICE_SHARD = registryManager.item(new ItemIceShard());
    SEA_ICE_SHARD = registryManager.item(new ItemSeaIceShard());
    PACKED_ICE_SHARD = registryManager.item(new ItemPackedIceShard());
    STRAW = registryManager.item(new ItemStraw());
    WOOD_ASH = registryManager.item(new ItemWoodAsh());
    JAR = registryManager.item(new ItemJar());
    MORTAR = registryManager.item(new ItemMortar());

  }
}
