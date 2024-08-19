package su.terrafirmagreg.api.registry.spi;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public interface IKeyBindingRegistry
        extends IBaseRegistry {

    default KeyBinding keyBinding(String description, int keyCode) {

        var prefix = "key." + this.getModID() + ".";
        final KeyBinding key = new KeyBinding(prefix + description, keyCode, prefix + "categories");
        ClientRegistry.registerKeyBinding(key);

        this.getRegistry().getKeyBinding().add(key);
        return key;
    }

}
