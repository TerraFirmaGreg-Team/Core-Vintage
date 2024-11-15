package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.core.object.item.ItemCoreMisc;
import su.terrafirmagreg.modules.core.object.item.ItemDebug;

public final class ItemsCore {

  public static ItemDebug WAND;
  public static ItemCoreMisc GLUE;
  public static ItemCoreMisc GLASS_SHARD;
  public static ItemCoreMisc ICE_SHARD;
  public static ItemCoreMisc SEA_ICE_SHARD;
  public static ItemCoreMisc PACKED_ICE_SHARD;
  public static ItemCoreMisc STRAW;
  public static ItemCoreMisc WOOD_ASH;
  public static ItemCoreMisc JAR;
  public static ItemCoreMisc HALTER;
  public static ItemCoreMisc MORTAR;

  public static void onRegister(RegistryManager registryManager) {

    //==== Other =================================================================================================//

    WAND = registryManager.item(new ItemDebug());
    GLUE = registryManager.item(new ItemCoreMisc("glue", Size.TINY, Weight.LIGHT, "slimeball", "glue"));
    GLASS_SHARD = registryManager.item(new ItemCoreMisc("glass_shard", Size.TINY, Weight.LIGHT));
    ICE_SHARD = registryManager.item(new ItemCoreMisc("ice_shard", Size.TINY, Weight.LIGHT));
    SEA_ICE_SHARD = registryManager.item(new ItemCoreMisc("sea_ice_shard", Size.TINY, Weight.LIGHT));
    PACKED_ICE_SHARD = registryManager.item(new ItemCoreMisc("packed_ice_shard", Size.TINY, Weight.LIGHT));
    STRAW = registryManager.item(
      new ItemCoreMisc("straw", Size.SMALL, Weight.VERY_LIGHT, "kindling", "straw"));
    WOOD_ASH = registryManager.item(
      new ItemCoreMisc("wood_ash", Size.VERY_SMALL, Weight.VERY_LIGHT, "dustAsh"));
    JAR = registryManager.item(new ItemCoreMisc("jar", Size.VERY_SMALL, Weight.VERY_LIGHT));
    HALTER = registryManager.item(new ItemCoreMisc("halter", Size.SMALL, Weight.LIGHT, "halter"));
    MORTAR = registryManager.item(new ItemCoreMisc("mortar", Size.TINY, Weight.VERY_LIGHT, "mortar"));

  }
}
