package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.registry.api.IRegistryManager;

import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public final class SoundsAnimal {

  public static Supplier<SoundEvent> ANIMAL_BEAR_SAY;
  public static Supplier<SoundEvent> ANIMAL_BEAR_CRY;
  public static Supplier<SoundEvent> ANIMAL_BEAR_HURT;
  public static Supplier<SoundEvent> ANIMAL_BEAR_DEATH;

  public static Supplier<SoundEvent> ANIMAL_DEER_SAY;
  public static Supplier<SoundEvent> ANIMAL_DEER_CRY;
  public static Supplier<SoundEvent> ANIMAL_DEER_HURT;
  public static Supplier<SoundEvent> ANIMAL_DEER_DEATH;

  public static Supplier<SoundEvent> ANIMAL_PHEASANT_SAY;
  public static Supplier<SoundEvent> ANIMAL_PHEASANT_HURT;
  public static Supplier<SoundEvent> ANIMAL_PHEASANT_DEATH;

  public static Supplier<SoundEvent> ANIMAL_ROOSTER_CRY;

  public static Supplier<SoundEvent> ANIMAL_ALPACA_SAY;
  public static Supplier<SoundEvent> ANIMAL_ALPACA_CRY;
  public static Supplier<SoundEvent> ANIMAL_ALPACA_HURT;
  public static Supplier<SoundEvent> ANIMAL_ALPACA_DEATH;
  public static Supplier<SoundEvent> ANIMAL_ALPACA_STEP;

  public static Supplier<SoundEvent> ANIMAL_DUCK_SAY;
  public static Supplier<SoundEvent> ANIMAL_DUCK_CRY;
  public static Supplier<SoundEvent> ANIMAL_DUCK_HURT;
  public static Supplier<SoundEvent> ANIMAL_DUCK_DEATH;

  public static Supplier<SoundEvent> ANIMAL_GOAT_SAY;
  public static Supplier<SoundEvent> ANIMAL_GOAT_CRY;
  public static Supplier<SoundEvent> ANIMAL_GOAT_HURT;
  public static Supplier<SoundEvent> ANIMAL_GOAT_DEATH;

  public static Supplier<SoundEvent> ANIMAL_CAMEL_SAY;
  public static Supplier<SoundEvent> ANIMAL_CAMEL_CRY;
  public static Supplier<SoundEvent> ANIMAL_CAMEL_HURT;
  public static Supplier<SoundEvent> ANIMAL_CAMEL_DEATH;

  public static Supplier<SoundEvent> ANIMAL_PANTHER_SAY;
  public static Supplier<SoundEvent> ANIMAL_PANTHER_CRY;
  public static Supplier<SoundEvent> ANIMAL_PANTHER_HURT;
  public static Supplier<SoundEvent> ANIMAL_PANTHER_DEATH;

  public static Supplier<SoundEvent> ANIMAL_SABERTOOTH_SAY;
  public static Supplier<SoundEvent> ANIMAL_SABERTOOTH_CRY;
  public static Supplier<SoundEvent> ANIMAL_SABERTOOTH_HURT;
  public static Supplier<SoundEvent> ANIMAL_SABERTOOTH_DEATH;

  public static Supplier<SoundEvent> ANIMAL_LION_SAY;
  public static Supplier<SoundEvent> ANIMAL_LION_CRY;
  public static Supplier<SoundEvent> ANIMAL_LION_HURT;
  public static Supplier<SoundEvent> ANIMAL_LION_DEATH;

  public static Supplier<SoundEvent> ANIMAL_HYENA_SAY;
  public static Supplier<SoundEvent> ANIMAL_HYENA_CRY;
  public static Supplier<SoundEvent> ANIMAL_HYENA_HURT;
  public static Supplier<SoundEvent> ANIMAL_HYENA_DEATH;

  public static Supplier<SoundEvent> ANIMAL_ZEBU_SAY;
  public static Supplier<SoundEvent> ANIMAL_ZEBU_HURT;
  public static Supplier<SoundEvent> ANIMAL_ZEBU_DEATH;

  public static Supplier<SoundEvent> ANIMAL_MUSKOX_SAY;
  public static Supplier<SoundEvent> ANIMAL_MUSKOX_HURT;
  public static Supplier<SoundEvent> ANIMAL_MUSKOX_DEATH;

  public static Supplier<SoundEvent> ANIMAL_TURKEY_SAY;
  public static Supplier<SoundEvent> ANIMAL_TURKEY_HURT;
  public static Supplier<SoundEvent> ANIMAL_TURKEY_DEATH;

  public static Supplier<SoundEvent> ANIMAL_BOAR_SAY;
  public static Supplier<SoundEvent> ANIMAL_BOAR_HURT;
  public static Supplier<SoundEvent> ANIMAL_BOAR_DEATH;

  public static Supplier<SoundEvent> ANIMAL_WILDEBEEST_SAY;
  public static Supplier<SoundEvent> ANIMAL_WILDEBEEST_HURT;
  public static Supplier<SoundEvent> ANIMAL_WILDEBEEST_DEATH;

  public static Supplier<SoundEvent> ANIMAL_GROUSE_SAY;
  public static Supplier<SoundEvent> ANIMAL_GROUSE_HURT;
  public static Supplier<SoundEvent> ANIMAL_GROUSE_DEATH;

  public static Supplier<SoundEvent> ANIMAL_QUAIL_SAY;
  public static Supplier<SoundEvent> ANIMAL_QUAIL_HURT;
  public static Supplier<SoundEvent> ANIMAL_QUAIL_DEATH;

  public static Supplier<SoundEvent> ANIMAL_COYOTE_SAY;
  public static Supplier<SoundEvent> ANIMAL_COYOTE_CRY;
  public static Supplier<SoundEvent> ANIMAL_COYOTE_HURT;
  public static Supplier<SoundEvent> ANIMAL_COYOTE_DEATH;

  public static Supplier<SoundEvent> ANIMAL_COUGAR_SAY;
  public static Supplier<SoundEvent> ANIMAL_COUGAR_CRY;
  public static Supplier<SoundEvent> ANIMAL_COUGAR_HURT;
  public static Supplier<SoundEvent> ANIMAL_COUGAR_DEATH;

  public static Supplier<SoundEvent> ANIMAL_GAZELLE_SAY;
  public static Supplier<SoundEvent> ANIMAL_GAZELLE_HURT;
  public static Supplier<SoundEvent> ANIMAL_GAZELLE_DEATH;

  public static Supplier<SoundEvent> ANIMAL_DIREWOLF_SAY;
  public static Supplier<SoundEvent> ANIMAL_DIREWOLF_CRY;
  public static Supplier<SoundEvent> ANIMAL_DIREWOLF_HURT;
  public static Supplier<SoundEvent> ANIMAL_DIREWOLF_DEATH;

  public static Supplier<SoundEvent> ANIMAL_YAK_SAY;
  public static Supplier<SoundEvent> ANIMAL_YAK_HURT;
  public static Supplier<SoundEvent> ANIMAL_YAK_DEATH;

  public static Supplier<SoundEvent> ANIMAL_JACKAL_SAY;
  public static Supplier<SoundEvent> ANIMAL_JACKAL_CRY;
  public static Supplier<SoundEvent> ANIMAL_JACKAL_HURT;
  public static Supplier<SoundEvent> ANIMAL_JACKAL_DEATH;

  public static Supplier<SoundEvent> ANIMAL_MONGOOSE_SAY;
  public static Supplier<SoundEvent> ANIMAL_MONGOOSE_HURT;
  public static Supplier<SoundEvent> ANIMAL_MONGOOSE_DEATH;

  public static Supplier<SoundEvent> ANIMAL_FELINE_STEP;

  public static void onRegister(IRegistryManager registry) {

    ANIMAL_BEAR_SAY = registry.sound("entity/bear/say");
    ANIMAL_BEAR_CRY = registry.sound("entity/bear/cry");
    ANIMAL_BEAR_HURT = registry.sound("entity/bear/hurt");
    ANIMAL_BEAR_DEATH = registry.sound("entity/bear/death");

    ANIMAL_DEER_SAY = registry.sound("entity/deer/say");
    ANIMAL_DEER_CRY = registry.sound("entity/deer/cry");
    ANIMAL_DEER_HURT = registry.sound("entity/deer/hurt");
    ANIMAL_DEER_DEATH = registry.sound("entity/deer/death");

    ANIMAL_PHEASANT_SAY = registry.sound("entity/pheasant/say");
    ANIMAL_PHEASANT_HURT = registry.sound("entity/pheasant/hurt");
    ANIMAL_PHEASANT_DEATH = registry.sound("entity/pheasant/death");

    ANIMAL_ROOSTER_CRY = registry.sound("entity/rooster/cry");

    ANIMAL_ALPACA_SAY = registry.sound("entity/alpaca/say");
    ANIMAL_ALPACA_CRY = registry.sound("entity/alpaca/cry");
    ANIMAL_ALPACA_HURT = registry.sound("entity/alpaca/hurt");
    ANIMAL_ALPACA_DEATH = registry.sound("entity/alpaca/death");
    ANIMAL_ALPACA_STEP = registry.sound("entity/alpaca/step");

    ANIMAL_DUCK_SAY = registry.sound("entity/duck/say");
    ANIMAL_DUCK_CRY = registry.sound("entity/duck/cry");
    ANIMAL_DUCK_HURT = registry.sound("entity/duck/hurt");
    ANIMAL_DUCK_DEATH = registry.sound("entity/duck/death");

    ANIMAL_GOAT_SAY = registry.sound("entity/goat/say");
    ANIMAL_GOAT_CRY = registry.sound("entity/goat/cry");
    ANIMAL_GOAT_HURT = registry.sound("entity/goat/hurt");
    ANIMAL_GOAT_DEATH = registry.sound("entity/goat/death");

    ANIMAL_CAMEL_SAY = registry.sound("entity/camel/say");
    ANIMAL_CAMEL_CRY = registry.sound("entity/camel/cry");
    ANIMAL_CAMEL_HURT = registry.sound("entity/camel/hurt");
    ANIMAL_CAMEL_DEATH = registry.sound("entity/camel/death");

    ANIMAL_PANTHER_SAY = registry.sound("entity/panther/say");
    ANIMAL_PANTHER_CRY = registry.sound("entity/panther/cry");
    ANIMAL_PANTHER_HURT = registry.sound("entity/panther/hurt");
    ANIMAL_PANTHER_DEATH = registry.sound("entity/panther/death");

    ANIMAL_SABERTOOTH_SAY = registry.sound("entity/sabertooth/say");
    ANIMAL_SABERTOOTH_CRY = registry.sound("entity/sabertooth/cry");
    ANIMAL_SABERTOOTH_HURT = registry.sound("entity/sabertooth/hurt");
    ANIMAL_SABERTOOTH_DEATH = registry.sound("entity/sabertooth/death");

    ANIMAL_LION_SAY = registry.sound("entity/lion/say");
    ANIMAL_LION_CRY = registry.sound("entity/lion/cry");
    ANIMAL_LION_HURT = registry.sound("entity/lion/hurt");
    ANIMAL_LION_DEATH = registry.sound("entity/lion/death");

    ANIMAL_HYENA_SAY = registry.sound("entity/hyena/say");
    ANIMAL_HYENA_CRY = registry.sound("entity/hyena/cry");
    ANIMAL_HYENA_HURT = registry.sound("entity/hyena/hurt");
    ANIMAL_HYENA_DEATH = registry.sound("entity/hyena/death");

    ANIMAL_ZEBU_SAY = registry.sound("entity/zebu/say");
    ANIMAL_ZEBU_HURT = registry.sound("entity/zebu/hurt");
    ANIMAL_ZEBU_DEATH = registry.sound("entity/zebu/death");

    ANIMAL_MUSKOX_SAY = registry.sound("entity/muskox/say");
    ANIMAL_MUSKOX_HURT = registry.sound("entity/muskox/hurt");
    ANIMAL_MUSKOX_DEATH = registry.sound("entity/muskox/death");

    ANIMAL_TURKEY_SAY = registry.sound("entity/turkey/say");
    ANIMAL_TURKEY_HURT = registry.sound("entity/turkey/hurt");
    ANIMAL_TURKEY_DEATH = registry.sound("entity/turkey/death");

    ANIMAL_BOAR_SAY = registry.sound("entity/boar/say");
    ANIMAL_BOAR_HURT = registry.sound("entity/boar/hurt");
    ANIMAL_BOAR_DEATH = registry.sound("entity/boar/death");

    ANIMAL_WILDEBEEST_SAY = registry.sound("entity/wildebeest/say");
    ANIMAL_WILDEBEEST_HURT = registry.sound("entity/wildebeest/hurt");
    ANIMAL_WILDEBEEST_DEATH = registry.sound("entity/wildebeest/death");

    ANIMAL_GROUSE_SAY = registry.sound("entity/grouse/say");
    ANIMAL_GROUSE_HURT = registry.sound("entity/grouse/hurt");
    ANIMAL_GROUSE_DEATH = registry.sound("entity/grouse/death");

    ANIMAL_QUAIL_SAY = registry.sound("entity/quail/say");
    ANIMAL_QUAIL_HURT = registry.sound("entity/quail/hurt");
    ANIMAL_QUAIL_DEATH = registry.sound("entity/quail/death");

    ANIMAL_COYOTE_SAY = registry.sound("entity/coyote/say");
    ANIMAL_COYOTE_CRY = registry.sound("entity/coyote/cry");
    ANIMAL_COYOTE_HURT = registry.sound("entity/coyote/hurt");
    ANIMAL_COYOTE_DEATH = registry.sound("entity/coyote/death");

    ANIMAL_COUGAR_SAY = registry.sound("entity/cougar/say");
    ANIMAL_COUGAR_CRY = registry.sound("entity/cougar/cry");
    ANIMAL_COUGAR_HURT = registry.sound("entity/cougar/hurt");
    ANIMAL_COUGAR_DEATH = registry.sound("entity/cougar/death");

    ANIMAL_GAZELLE_SAY = registry.sound("entity/gazelle/say");
    ANIMAL_GAZELLE_HURT = registry.sound("entity/gazelle/hurt");
    ANIMAL_GAZELLE_DEATH = registry.sound("entity/gazelle/death");

    ANIMAL_DIREWOLF_SAY = registry.sound("entity/direwolf/say");
    ANIMAL_DIREWOLF_CRY = registry.sound("entity/direwolf/cry");
    ANIMAL_DIREWOLF_HURT = registry.sound("entity/direwolf/hurt");
    ANIMAL_DIREWOLF_DEATH = registry.sound("entity/direwolf/death");

    ANIMAL_YAK_SAY = registry.sound("entity/yak/say");
    ANIMAL_YAK_HURT = registry.sound("entity/yak/hurt");
    ANIMAL_YAK_DEATH = registry.sound("entity/yak/death");

    ANIMAL_JACKAL_SAY = registry.sound("entity/jackal/say");
    ANIMAL_JACKAL_CRY = registry.sound("entity/jackal/cry");
    ANIMAL_JACKAL_HURT = registry.sound("entity/jackal/hurt");
    ANIMAL_JACKAL_DEATH = registry.sound("entity/jackal/death");

    ANIMAL_MONGOOSE_SAY = registry.sound("entity/mongoose/say");
    ANIMAL_MONGOOSE_HURT = registry.sound("entity/mongoose/hurt");
    ANIMAL_MONGOOSE_DEATH = registry.sound("entity/mongoose/death");

    ANIMAL_FELINE_STEP = registry.sound("entity/feline/step");

  }
}
