package su.terrafirmagreg.modules.device.data;

import net.minecraft.util.SoundEvent;
import su.terrafirmagreg.api.registry.RegistryManager;

public class SoundDevice {

	public static SoundEvent FLASK_BREAK;

	public static void onRegister(RegistryManager registry) {

		FLASK_BREAK = registry.registerSound("item.device.flaskbreak");
	}
}
