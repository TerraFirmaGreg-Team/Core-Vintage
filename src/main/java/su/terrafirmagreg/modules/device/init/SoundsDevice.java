package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;

import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public final class SoundsDevice {

  public static Supplier<SoundEvent> FLASK_BREAK;
  public static Supplier<SoundEvent> FIRE_STARTER;
  public static Supplier<SoundEvent> BELLOWS_BLOW_AIR;
  public static Supplier<SoundEvent> FRIDGE_OPEN;
  public static Supplier<SoundEvent> FRIDGE_CLOSE;
  public static Supplier<SoundEvent> INDUCTION_WORK;
  public static Supplier<SoundEvent> LATEX_EXTRACTOR_GROOVE_FIT;
  public static Supplier<SoundEvent> LATEX_EXTRACTOR_MOUNT_FIT;
  public static Supplier<SoundEvent> LATEX_EXTRACTOR_BOWL_FIT;
  public static Supplier<SoundEvent> LATEX_EXTRACTOR_BOWL_GRAB;
  public static Supplier<SoundEvent> LATEX_EXTRACTOR_TRUNK_SCRATH;

  public static void onRegister(IRegistryManager registry) {

    FLASK_BREAK = registry.sound("item.device.flaskbreak");
    FIRE_STARTER = registry.sound("item.device.firestarter");
    BELLOWS_BLOW_AIR = registry.sound("block.device.bellows.blow.air");
    FRIDGE_OPEN = registry.sound("block.device.fridge.open");
    FRIDGE_CLOSE = registry.sound("block.device.fridge.close");
    INDUCTION_WORK = registry.sound("block.device.induction_smelter.work");
    LATEX_EXTRACTOR_GROOVE_FIT = registry.sound("block.device.latex_extractor.fit.groove");
    LATEX_EXTRACTOR_MOUNT_FIT = registry.sound("block.device.latex_extractor.fit.mount");
    LATEX_EXTRACTOR_BOWL_FIT = registry.sound("block.device.latex_extractor.fit.bowl");
    LATEX_EXTRACTOR_BOWL_GRAB = registry.sound("block.device.latex_extractor.grab.bowl");
    LATEX_EXTRACTOR_TRUNK_SCRATH = registry.sound("block.device.latex_extractor.trunk.scratch");
  }
}
