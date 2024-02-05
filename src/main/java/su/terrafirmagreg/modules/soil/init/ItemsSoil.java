package su.terrafirmagreg.modules.soil.init;

import net.minecraft.item.Item;
import su.terrafirmagreg.api.registry.Registry;
import su.terrafirmagreg.modules.soil.StorageSoil;

public final class ItemsSoil {

	public static void onRegister(Registry registry) {

		for (var item : StorageSoil.SOIL_ITEMS.values()) registry.registerAutoItem((Item) item);
	}


	public static void onClientRegister(Registry registry) {

	}
}
