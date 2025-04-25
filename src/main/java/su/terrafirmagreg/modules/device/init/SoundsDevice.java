package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;

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

  public static void onRegister(IRegistryRegistrar registrar) {

    FLASK_BREAK = registrar.addSound("item/flaskbreak");
    FIRE_STARTER = registrar.addSound("item/firestarter");
    BELLOWS_BLOW_AIR = registrar.addSound("block/bellows/blow/air");
    FRIDGE_OPEN = registrar.addSound("block/fridge/open");
    FRIDGE_CLOSE = registrar.addSound("block/fridge/close");
    INDUCTION_WORK = registrar.addSound("block/induction_smelter/work");
    LATEX_EXTRACTOR_GROOVE_FIT = registrar.addSound("block/latex_extractor/fit/groove");
    LATEX_EXTRACTOR_MOUNT_FIT = registrar.addSound("block/latex_extractor/fit/mount");
    LATEX_EXTRACTOR_BOWL_FIT = registrar.addSound("block/latex_extractor/fit/bowl");
    LATEX_EXTRACTOR_BOWL_GRAB = registrar.addSound("block/latex_extractor/grab/bowl");
    LATEX_EXTRACTOR_TRUNK_SCRATH = registrar.addSound("block/latex_extractor/trunk/scratch");
  }
}
