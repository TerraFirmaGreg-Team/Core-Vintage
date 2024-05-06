package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.registry.RegistryManager;

import net.minecraft.util.SoundEvent;

public final class SoundDevice {

    public static SoundEvent FLASK_BREAK;
    public static SoundEvent FIRE_STARTER;
    public static SoundEvent BELLOWS_BLOW_AIR;
    public static SoundEvent FRIDGE_OPEN;
    public static SoundEvent FRIDGE_CLOSE;
    public static SoundEvent INDUCTION_WORK;
    public static SoundEvent LATEX_EXTRACTOR_GROOVE_FIT;
    public static SoundEvent LATEX_EXTRACTOR_MOUNT_FIT;
    public static SoundEvent LATEX_EXTRACTOR_BOWL_FIT;
    public static SoundEvent LATEX_EXTRACTOR_BOWL_GRAB;
    public static SoundEvent LATEX_EXTRACTOR_TRUNK_SCRATH;

    public static void onRegister(RegistryManager registry) {

        FLASK_BREAK = registry.registerSound("item.device.flaskbreak");
        FIRE_STARTER = registry.registerSound("item.device.firestarter");
        BELLOWS_BLOW_AIR = registry.registerSound("block.device.bellows.blow.air");
        FRIDGE_OPEN = registry.registerSound("block.device.fridge.open");
        FRIDGE_CLOSE = registry.registerSound("block.device.fridge.close");
        INDUCTION_WORK = registry.registerSound("block.device.induction_smelter.work");
        LATEX_EXTRACTOR_GROOVE_FIT = registry.registerSound("block.device.latex_extractor.fit.groove");
        LATEX_EXTRACTOR_MOUNT_FIT = registry.registerSound("block.device.latex_extractor.fit.mount");
        LATEX_EXTRACTOR_BOWL_FIT = registry.registerSound("block.device.latex_extractor.fit.bowl");
        LATEX_EXTRACTOR_BOWL_GRAB = registry.registerSound("block.device.latex_extractor.grab.bowl");
        LATEX_EXTRACTOR_TRUNK_SCRATH = registry.registerSound("block.device.latex_extractor.trunk.scratch");
    }
}
