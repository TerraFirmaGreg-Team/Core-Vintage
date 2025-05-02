package net.dries007.astikorcarts.init;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class ModKeybindings {

  public static List<KeyBinding> keybindings = new ArrayList<KeyBinding>();

  public static void registerKeyBindings() {
    keybindings.add(new KeyBinding("key.astikorcarts.desc", Keyboard.KEY_R, "key.categories.astikorcarts"));

    for (KeyBinding bind : keybindings) {
      ClientRegistry.registerKeyBinding(bind);
    }
  }
}
