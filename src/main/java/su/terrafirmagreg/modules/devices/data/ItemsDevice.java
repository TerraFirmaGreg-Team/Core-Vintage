package su.terrafirmagreg.modules.devices.data;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.devices.objects.items.ItemFireStarter;


public class ItemsDevice {

	public static ItemFireStarter FIRESTARTER;

	public static void onRegister(RegistryManager registry) {

		registry.registerAuto(FIRESTARTER = new ItemFireStarter());

	}


	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {

	}
}
