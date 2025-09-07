package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;

import net.minecraft.util.SoundEvent;

public final class SoundsDevice {

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

  public static void onRegister(IContentRegistrar registrar) {

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
