package su.terrafirmagreg.modules.core.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.items.ItemCoreMisc;
import su.terrafirmagreg.modules.core.objects.items.ItemDebug;


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;

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

    public static void onRegister(RegistryManager registry) {

        //==== Other =================================================================================================//

        WAND = registry.registerItem(new ItemDebug());
        GLUE = registry.registerItem(new ItemCoreMisc("glue", Size.TINY, Weight.LIGHT, "slimeball", "glue"));
        GLASS_SHARD = registry.registerItem(new ItemCoreMisc("glass_shard", Size.TINY, Weight.LIGHT));
        ICE_SHARD = registry.registerItem(new ItemCoreMisc("ice_shard", Size.TINY, Weight.LIGHT));
        SEA_ICE_SHARD = registry.registerItem(new ItemCoreMisc("sea_ice_shard", Size.TINY, Weight.LIGHT));
        PACKED_ICE_SHARD = registry.registerItem(new ItemCoreMisc("packed_ice_shard", Size.TINY, Weight.LIGHT));
        STRAW = registry.registerItem(new ItemCoreMisc("straw", Size.SMALL, Weight.VERY_LIGHT, "kindling", "straw"));
        WOOD_ASH = registry.registerItem(new ItemCoreMisc("wood_ash", Size.VERY_SMALL, Weight.VERY_LIGHT, "dustAsh"));
        JAR = registry.registerItem(new ItemCoreMisc("jar", Size.VERY_SMALL, Weight.VERY_LIGHT));
        HALTER = registry.registerItem(new ItemCoreMisc("halter", Size.SMALL, Weight.LIGHT, "halter"));
        MORTAR = registry.registerItem(new ItemCoreMisc("mortar", Size.TINY, Weight.VERY_LIGHT, "mortar"));

    }
}
