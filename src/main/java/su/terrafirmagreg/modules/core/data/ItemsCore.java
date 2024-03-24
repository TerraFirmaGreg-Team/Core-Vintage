package su.terrafirmagreg.modules.core.data;

import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.items.ItemCoreMisc;
import su.terrafirmagreg.modules.core.objects.items.ItemDebug;

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

		registry.registerAuto(WAND = new ItemDebug());
		registry.registerAuto(GLUE = new ItemCoreMisc("glue", Size.TINY, Weight.LIGHT, "slimeball", "glue"));
		registry.registerAuto(GLASS_SHARD = new ItemCoreMisc("glass_shard", Size.TINY, Weight.LIGHT));
		registry.registerAuto(ICE_SHARD = new ItemCoreMisc("ice_shard", Size.TINY, Weight.LIGHT));
		registry.registerAuto(SEA_ICE_SHARD = new ItemCoreMisc("sea_ice_shard", Size.TINY, Weight.LIGHT));
		registry.registerAuto(PACKED_ICE_SHARD = new ItemCoreMisc("packed_ice_shard", Size.TINY, Weight.LIGHT));
		registry.registerAuto(STRAW = new ItemCoreMisc("straw", Size.SMALL, Weight.VERY_LIGHT, "kindling", "straw"));
		registry.registerAuto(WOOD_ASH = new ItemCoreMisc("wood_ash", Size.VERY_SMALL, Weight.VERY_LIGHT));
		registry.registerAuto(JAR = new ItemCoreMisc("jar", Size.VERY_SMALL, Weight.VERY_LIGHT));
		registry.registerAuto(HALTER = new ItemCoreMisc("halter", Size.SMALL, Weight.LIGHT, "halter"));
		registry.registerAuto(MORTAR = new ItemCoreMisc("mortar", Size.TINY, Weight.VERY_LIGHT, "mortar"));


	}


	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {

	}
}
