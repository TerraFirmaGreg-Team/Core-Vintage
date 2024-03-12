package su.terrafirmagreg.modules.device.data;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.device.objects.items.ItemDeviceMisc;
import su.terrafirmagreg.modules.device.objects.items.ItemFireStarter;
import su.terrafirmagreg.modules.device.objects.items.ItemIronFlask;
import su.terrafirmagreg.modules.device.objects.items.ItemLeatherFlask;


public final class ItemsDevice {

	public static ItemFireStarter FIRESTARTER;
	public static ItemDeviceMisc LEATHER_SIDE;
	public static ItemLeatherFlask LEATHER_FLASK;
	public static ItemDeviceMisc BROKEN_LEATHER_FLASK;
	public static ItemDeviceMisc UNFINISHED_FLASK;
	public static ItemIronFlask IRON_FLASK;
	public static ItemDeviceMisc BROKEN_IRON_FLASK;


	public static void onRegister(RegistryManager registry) {

		registry.registerAuto(FIRESTARTER = new ItemFireStarter());

		registry.registerAuto(LEATHER_SIDE = new ItemDeviceMisc.Builder("flask/leather/side").build());
		registry.registerAuto(LEATHER_FLASK = new ItemLeatherFlask());
		registry.registerAuto(BROKEN_LEATHER_FLASK = new ItemDeviceMisc.Builder("flask/leather/broken").build());

		registry.registerAuto(UNFINISHED_FLASK = new ItemDeviceMisc.Builder("flask/iron/unfinished").build());
		registry.registerAuto(IRON_FLASK = new ItemIronFlask());
		registry.registerAuto(BROKEN_IRON_FLASK = new ItemDeviceMisc.Builder("flask/iron/broken").build());

	}


	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {

	}
}
