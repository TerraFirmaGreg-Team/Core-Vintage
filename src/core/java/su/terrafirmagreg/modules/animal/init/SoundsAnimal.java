package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;

import net.minecraft.util.SoundEvent;

public final class SoundsAnimal {

  public static SoundEvent ANIMAL_BEAR_SAY;
  public static SoundEvent ANIMAL_BEAR_CRY;
  public static SoundEvent ANIMAL_BEAR_HURT;
  public static SoundEvent ANIMAL_BEAR_DEATH;

  public static SoundEvent ANIMAL_DEER_SAY;
  public static SoundEvent ANIMAL_DEER_CRY;
  public static SoundEvent ANIMAL_DEER_HURT;
  public static SoundEvent ANIMAL_DEER_DEATH;

  public static SoundEvent ANIMAL_PHEASANT_SAY;
  public static SoundEvent ANIMAL_PHEASANT_HURT;
  public static SoundEvent ANIMAL_PHEASANT_DEATH;

  public static SoundEvent ANIMAL_ROOSTER_CRY;

  public static SoundEvent ANIMAL_ALPACA_SAY;
  public static SoundEvent ANIMAL_ALPACA_CRY;
  public static SoundEvent ANIMAL_ALPACA_HURT;
  public static SoundEvent ANIMAL_ALPACA_DEATH;
  public static SoundEvent ANIMAL_ALPACA_STEP;

  public static SoundEvent ANIMAL_DUCK_SAY;
  public static SoundEvent ANIMAL_DUCK_CRY;
  public static SoundEvent ANIMAL_DUCK_HURT;
  public static SoundEvent ANIMAL_DUCK_DEATH;

  public static SoundEvent ANIMAL_GOAT_SAY;
  public static SoundEvent ANIMAL_GOAT_CRY;
  public static SoundEvent ANIMAL_GOAT_HURT;
  public static SoundEvent ANIMAL_GOAT_DEATH;

  public static SoundEvent ANIMAL_CAMEL_SAY;
  public static SoundEvent ANIMAL_CAMEL_CRY;
  public static SoundEvent ANIMAL_CAMEL_HURT;
  public static SoundEvent ANIMAL_CAMEL_DEATH;

  public static SoundEvent ANIMAL_PANTHER_SAY;
  public static SoundEvent ANIMAL_PANTHER_CRY;
  public static SoundEvent ANIMAL_PANTHER_HURT;
  public static SoundEvent ANIMAL_PANTHER_DEATH;

  public static SoundEvent ANIMAL_SABERTOOTH_SAY;
  public static SoundEvent ANIMAL_SABERTOOTH_CRY;
  public static SoundEvent ANIMAL_SABERTOOTH_HURT;
  public static SoundEvent ANIMAL_SABERTOOTH_DEATH;

  public static SoundEvent ANIMAL_LION_SAY;
  public static SoundEvent ANIMAL_LION_CRY;
  public static SoundEvent ANIMAL_LION_HURT;
  public static SoundEvent ANIMAL_LION_DEATH;

  public static SoundEvent ANIMAL_HYENA_SAY;
  public static SoundEvent ANIMAL_HYENA_CRY;
  public static SoundEvent ANIMAL_HYENA_HURT;
  public static SoundEvent ANIMAL_HYENA_DEATH;

  public static SoundEvent ANIMAL_ZEBU_SAY;
  public static SoundEvent ANIMAL_ZEBU_HURT;
  public static SoundEvent ANIMAL_ZEBU_DEATH;

  public static SoundEvent ANIMAL_MUSKOX_SAY;
  public static SoundEvent ANIMAL_MUSKOX_HURT;
  public static SoundEvent ANIMAL_MUSKOX_DEATH;

  public static SoundEvent ANIMAL_TURKEY_SAY;
  public static SoundEvent ANIMAL_TURKEY_HURT;
  public static SoundEvent ANIMAL_TURKEY_DEATH;

  public static SoundEvent ANIMAL_BOAR_SAY;
  public static SoundEvent ANIMAL_BOAR_HURT;
  public static SoundEvent ANIMAL_BOAR_DEATH;

  public static SoundEvent ANIMAL_WILDEBEEST_SAY;
  public static SoundEvent ANIMAL_WILDEBEEST_HURT;
  public static SoundEvent ANIMAL_WILDEBEEST_DEATH;

  public static SoundEvent ANIMAL_GROUSE_SAY;
  public static SoundEvent ANIMAL_GROUSE_HURT;
  public static SoundEvent ANIMAL_GROUSE_DEATH;

  public static SoundEvent ANIMAL_QUAIL_SAY;
  public static SoundEvent ANIMAL_QUAIL_HURT;
  public static SoundEvent ANIMAL_QUAIL_DEATH;

  public static SoundEvent ANIMAL_COYOTE_SAY;
  public static SoundEvent ANIMAL_COYOTE_CRY;
  public static SoundEvent ANIMAL_COYOTE_HURT;
  public static SoundEvent ANIMAL_COYOTE_DEATH;

  public static SoundEvent ANIMAL_COUGAR_SAY;
  public static SoundEvent ANIMAL_COUGAR_CRY;
  public static SoundEvent ANIMAL_COUGAR_HURT;
  public static SoundEvent ANIMAL_COUGAR_DEATH;

  public static SoundEvent ANIMAL_GAZELLE_SAY;
  public static SoundEvent ANIMAL_GAZELLE_HURT;
  public static SoundEvent ANIMAL_GAZELLE_DEATH;

  public static SoundEvent ANIMAL_DIREWOLF_SAY;
  public static SoundEvent ANIMAL_DIREWOLF_CRY;
  public static SoundEvent ANIMAL_DIREWOLF_HURT;
  public static SoundEvent ANIMAL_DIREWOLF_DEATH;

  public static SoundEvent ANIMAL_YAK_SAY;
  public static SoundEvent ANIMAL_YAK_HURT;
  public static SoundEvent ANIMAL_YAK_DEATH;

  public static SoundEvent ANIMAL_JACKAL_SAY;
  public static SoundEvent ANIMAL_JACKAL_CRY;
  public static SoundEvent ANIMAL_JACKAL_HURT;
  public static SoundEvent ANIMAL_JACKAL_DEATH;

  public static SoundEvent ANIMAL_MONGOOSE_SAY;
  public static SoundEvent ANIMAL_MONGOOSE_HURT;
  public static SoundEvent ANIMAL_MONGOOSE_DEATH;

  public static SoundEvent ANIMAL_FELINE_STEP;

  public static void onRegister(IContentRegistrar registrar) {

    ANIMAL_BEAR_SAY = registrar.addSound("entity/bear/say");
    ANIMAL_BEAR_CRY = registrar.addSound("entity/bear/cry");
    ANIMAL_BEAR_HURT = registrar.addSound("entity/bear/hurt");
    ANIMAL_BEAR_DEATH = registrar.addSound("entity/bear/death");

    ANIMAL_DEER_SAY = registrar.addSound("entity/deer/say");
    ANIMAL_DEER_CRY = registrar.addSound("entity/deer/cry");
    ANIMAL_DEER_HURT = registrar.addSound("entity/deer/hurt");
    ANIMAL_DEER_DEATH = registrar.addSound("entity/deer/death");

    ANIMAL_PHEASANT_SAY = registrar.addSound("entity/pheasant/say");
    ANIMAL_PHEASANT_HURT = registrar.addSound("entity/pheasant/hurt");
    ANIMAL_PHEASANT_DEATH = registrar.addSound("entity/pheasant/death");

    ANIMAL_ROOSTER_CRY = registrar.addSound("entity/rooster/cry");

    ANIMAL_ALPACA_SAY = registrar.addSound("entity/alpaca/say");
    ANIMAL_ALPACA_CRY = registrar.addSound("entity/alpaca/cry");
    ANIMAL_ALPACA_HURT = registrar.addSound("entity/alpaca/hurt");
    ANIMAL_ALPACA_DEATH = registrar.addSound("entity/alpaca/death");
    ANIMAL_ALPACA_STEP = registrar.addSound("entity/alpaca/step");

    ANIMAL_DUCK_SAY = registrar.addSound("entity/duck/say");
    ANIMAL_DUCK_CRY = registrar.addSound("entity/duck/cry");
    ANIMAL_DUCK_HURT = registrar.addSound("entity/duck/hurt");
    ANIMAL_DUCK_DEATH = registrar.addSound("entity/duck/death");

    ANIMAL_GOAT_SAY = registrar.addSound("entity/goat/say");
    ANIMAL_GOAT_CRY = registrar.addSound("entity/goat/cry");
    ANIMAL_GOAT_HURT = registrar.addSound("entity/goat/hurt");
    ANIMAL_GOAT_DEATH = registrar.addSound("entity/goat/death");

    ANIMAL_CAMEL_SAY = registrar.addSound("entity/camel/say");
    ANIMAL_CAMEL_CRY = registrar.addSound("entity/camel/cry");
    ANIMAL_CAMEL_HURT = registrar.addSound("entity/camel/hurt");
    ANIMAL_CAMEL_DEATH = registrar.addSound("entity/camel/death");

    ANIMAL_PANTHER_SAY = registrar.addSound("entity/panther/say");
    ANIMAL_PANTHER_CRY = registrar.addSound("entity/panther/cry");
    ANIMAL_PANTHER_HURT = registrar.addSound("entity/panther/hurt");
    ANIMAL_PANTHER_DEATH = registrar.addSound("entity/panther/death");

    ANIMAL_SABERTOOTH_SAY = registrar.addSound("entity/sabertooth/say");
    ANIMAL_SABERTOOTH_CRY = registrar.addSound("entity/sabertooth/cry");
    ANIMAL_SABERTOOTH_HURT = registrar.addSound("entity/sabertooth/hurt");
    ANIMAL_SABERTOOTH_DEATH = registrar.addSound("entity/sabertooth/death");

    ANIMAL_LION_SAY = registrar.addSound("entity/lion/say");
    ANIMAL_LION_CRY = registrar.addSound("entity/lion/cry");
    ANIMAL_LION_HURT = registrar.addSound("entity/lion/hurt");
    ANIMAL_LION_DEATH = registrar.addSound("entity/lion/death");

    ANIMAL_HYENA_SAY = registrar.addSound("entity/hyena/say");
    ANIMAL_HYENA_CRY = registrar.addSound("entity/hyena/cry");
    ANIMAL_HYENA_HURT = registrar.addSound("entity/hyena/hurt");
    ANIMAL_HYENA_DEATH = registrar.addSound("entity/hyena/death");

    ANIMAL_ZEBU_SAY = registrar.addSound("entity/zebu/say");
    ANIMAL_ZEBU_HURT = registrar.addSound("entity/zebu/hurt");
    ANIMAL_ZEBU_DEATH = registrar.addSound("entity/zebu/death");

    ANIMAL_MUSKOX_SAY = registrar.addSound("entity/muskox/say");
    ANIMAL_MUSKOX_HURT = registrar.addSound("entity/muskox/hurt");
    ANIMAL_MUSKOX_DEATH = registrar.addSound("entity/muskox/death");

    ANIMAL_TURKEY_SAY = registrar.addSound("entity/turkey/say");
    ANIMAL_TURKEY_HURT = registrar.addSound("entity/turkey/hurt");
    ANIMAL_TURKEY_DEATH = registrar.addSound("entity/turkey/death");

    ANIMAL_BOAR_SAY = registrar.addSound("entity/boar/say");
    ANIMAL_BOAR_HURT = registrar.addSound("entity/boar/hurt");
    ANIMAL_BOAR_DEATH = registrar.addSound("entity/boar/death");

    ANIMAL_WILDEBEEST_SAY = registrar.addSound("entity/wildebeest/say");
    ANIMAL_WILDEBEEST_HURT = registrar.addSound("entity/wildebeest/hurt");
    ANIMAL_WILDEBEEST_DEATH = registrar.addSound("entity/wildebeest/death");

    ANIMAL_GROUSE_SAY = registrar.addSound("entity/grouse/say");
    ANIMAL_GROUSE_HURT = registrar.addSound("entity/grouse/hurt");
    ANIMAL_GROUSE_DEATH = registrar.addSound("entity/grouse/death");

    ANIMAL_QUAIL_SAY = registrar.addSound("entity/quail/say");
    ANIMAL_QUAIL_HURT = registrar.addSound("entity/quail/hurt");
    ANIMAL_QUAIL_DEATH = registrar.addSound("entity/quail/death");

    ANIMAL_COYOTE_SAY = registrar.addSound("entity/coyote/say");
    ANIMAL_COYOTE_CRY = registrar.addSound("entity/coyote/cry");
    ANIMAL_COYOTE_HURT = registrar.addSound("entity/coyote/hurt");
    ANIMAL_COYOTE_DEATH = registrar.addSound("entity/coyote/death");

    ANIMAL_COUGAR_SAY = registrar.addSound("entity/cougar/say");
    ANIMAL_COUGAR_CRY = registrar.addSound("entity/cougar/cry");
    ANIMAL_COUGAR_HURT = registrar.addSound("entity/cougar/hurt");
    ANIMAL_COUGAR_DEATH = registrar.addSound("entity/cougar/death");

    ANIMAL_GAZELLE_SAY = registrar.addSound("entity/gazelle/say");
    ANIMAL_GAZELLE_HURT = registrar.addSound("entity/gazelle/hurt");
    ANIMAL_GAZELLE_DEATH = registrar.addSound("entity/gazelle/death");

    ANIMAL_DIREWOLF_SAY = registrar.addSound("entity/direwolf/say");
    ANIMAL_DIREWOLF_CRY = registrar.addSound("entity/direwolf/cry");
    ANIMAL_DIREWOLF_HURT = registrar.addSound("entity/direwolf/hurt");
    ANIMAL_DIREWOLF_DEATH = registrar.addSound("entity/direwolf/death");

    ANIMAL_YAK_SAY = registrar.addSound("entity/yak/say");
    ANIMAL_YAK_HURT = registrar.addSound("entity/yak/hurt");
    ANIMAL_YAK_DEATH = registrar.addSound("entity/yak/death");

    ANIMAL_JACKAL_SAY = registrar.addSound("entity/jackal/say");
    ANIMAL_JACKAL_CRY = registrar.addSound("entity/jackal/cry");
    ANIMAL_JACKAL_HURT = registrar.addSound("entity/jackal/hurt");
    ANIMAL_JACKAL_DEATH = registrar.addSound("entity/jackal/death");

    ANIMAL_MONGOOSE_SAY = registrar.addSound("entity/mongoose/say");
    ANIMAL_MONGOOSE_HURT = registrar.addSound("entity/mongoose/hurt");
    ANIMAL_MONGOOSE_DEATH = registrar.addSound("entity/mongoose/death");

    ANIMAL_FELINE_STEP = registrar.addSound("entity/feline/step");

  }
}
