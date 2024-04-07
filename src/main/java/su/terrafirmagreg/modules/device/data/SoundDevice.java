package su.terrafirmagreg.modules.device.data;

import su.terrafirmagreg.api.registry.RegistryManager;

import net.minecraft.util.SoundEvent;

public final class SoundDevice {

    public static SoundEvent FLASK_BREAK;
    public static SoundEvent FIRE_STARTER;
    public static SoundEvent BELLOWS_BLOW_AIR;

    public static void onRegister(RegistryManager registry) {

        FLASK_BREAK = registry.registerSound("item.device.flaskbreak");
        FIRE_STARTER = registry.registerSound("item.device.firestarter");
        BELLOWS_BLOW_AIR = registry.registerSound("block.device.bellows.blow.air");
    }
}
