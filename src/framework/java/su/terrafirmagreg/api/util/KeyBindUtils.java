package su.terrafirmagreg.api.util;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class KeyBindUtils {

  public static KeyBinding addKeyBinding(KeyBinding key) {
    ClientRegistry.registerKeyBinding(key);
    return key;
  }

  public static KeyBinding addKeyBinding(String description, int keyCode, String category) {
    return KeyBindUtils.addKeyBinding(new KeyBinding(description, keyCode, category));
  }

}
