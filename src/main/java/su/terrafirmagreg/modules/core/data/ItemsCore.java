package su.terrafirmagreg.modules.core.data;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.core.objects.items.ItemDebug;

public final class ItemsCore {

	public static ItemDebug WAND;


	public static void onRegister(RegistryManager manager) {

		//==== Other =================================================================================================//

		manager.registerAuto(WAND = new ItemDebug());


	}


	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {

	}
}
