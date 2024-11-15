package su.terrafirmagreg.modules.wood.init;

import su.terrafirmagreg.api.registry.RegistryManager;

import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

public final class KeybindingsWood {

  public static KeyBinding ACTION_CART;

  public static void onClientRegister(RegistryManager registryManager) {
    ACTION_CART = registryManager.keyBinding("action_cart", Keyboard.KEY_R);

  }

}
