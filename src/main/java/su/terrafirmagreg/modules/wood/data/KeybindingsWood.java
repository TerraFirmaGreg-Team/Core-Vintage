package su.terrafirmagreg.modules.wood.data;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import su.terrafirmagreg.api.registry.RegistryManager;

public final class KeybindingsWood {

	public static KeyBinding ACTION_CART;


	public static void onClientRegister(RegistryManager registry) {
		ACTION_CART = registry.registerKeyBinding("action_cart", Keyboard.KEY_R);


	}


}
