package su.terrafirmagreg.api.util;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class KeyBindUtils {

  public static void register(KeyBinding key) {
    ClientRegistry.registerKeyBinding(key);
  }

}
