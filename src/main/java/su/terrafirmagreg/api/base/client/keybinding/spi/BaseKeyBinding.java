package su.terrafirmagreg.api.base.client.keybinding.spi;

import net.minecraft.client.settings.KeyBinding;

public abstract class BaseKeyBinding extends KeyBinding {

  public BaseKeyBinding(String description, int keyCode, String category) {
    super(description, keyCode, category);
  }
}
