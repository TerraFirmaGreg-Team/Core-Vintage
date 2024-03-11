package su.terrafirmagreg.modules.animal.data;

import net.minecraft.util.SoundEvent;
import su.terrafirmagreg.api.registry.RegistryManager;

public class SoundAnimal {

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

	public static void onRegister(RegistryManager registry) {

		ANIMAL_BEAR_SAY = registry.registerSound("animal.bear.say");
		ANIMAL_BEAR_CRY = registry.registerSound("animal.bear.cry");
		ANIMAL_BEAR_HURT = registry.registerSound("animal.bear.hurt");
		ANIMAL_BEAR_DEATH = registry.registerSound("animal.bear.death");

		ANIMAL_DEER_SAY = registry.registerSound("animal.deer.say");
		ANIMAL_DEER_CRY = registry.registerSound("animal.deer.cry");
		ANIMAL_DEER_HURT = registry.registerSound("animal.deer.hurt");
		ANIMAL_DEER_DEATH = registry.registerSound("animal.deer.death");

		ANIMAL_PHEASANT_SAY = registry.registerSound("animal.pheasant.say");
		ANIMAL_PHEASANT_HURT = registry.registerSound("animal.pheasant.hurt");
		ANIMAL_PHEASANT_DEATH = registry.registerSound("animal.pheasant.death");

		ANIMAL_ROOSTER_CRY = registry.registerSound("animal.rooster.cry");

		ANIMAL_ALPACA_SAY = registry.registerSound("animal.alpaca.say");
		ANIMAL_ALPACA_CRY = registry.registerSound("animal.alpaca.cry");
		ANIMAL_ALPACA_HURT = registry.registerSound("animal.alpaca.hurt");
		ANIMAL_ALPACA_DEATH = registry.registerSound("animal.alpaca.death");
		ANIMAL_ALPACA_STEP = registry.registerSound("animal.alpaca.step");

		ANIMAL_DUCK_SAY = registry.registerSound("animal.duck.say");
		ANIMAL_DUCK_CRY = registry.registerSound("animal.duck.cry");
		ANIMAL_DUCK_HURT = registry.registerSound("animal.duck.hurt");
		ANIMAL_DUCK_DEATH = registry.registerSound("animal.duck.death");

		ANIMAL_GOAT_SAY = registry.registerSound("animal.goat.say");
		ANIMAL_GOAT_CRY = registry.registerSound("animal.goat.cry");
		ANIMAL_GOAT_HURT = registry.registerSound("animal.goat.hurt");
		ANIMAL_GOAT_DEATH = registry.registerSound("animal.goat.death");

		ANIMAL_CAMEL_SAY = registry.registerSound("animal.camel.say");
		ANIMAL_CAMEL_CRY = registry.registerSound("animal.camel.cry");
		ANIMAL_CAMEL_HURT = registry.registerSound("animal.camel.hurt");
		ANIMAL_CAMEL_DEATH = registry.registerSound("animal.camel.death");

		ANIMAL_PANTHER_SAY = registry.registerSound("animal.panther.say");
		ANIMAL_PANTHER_CRY = registry.registerSound("animal.panther.cry");
		ANIMAL_PANTHER_HURT = registry.registerSound("animal.panther.hurt");
		ANIMAL_PANTHER_DEATH = registry.registerSound("animal.panther.death");

		ANIMAL_SABERTOOTH_SAY = registry.registerSound("animal.sabertooth.say");
		ANIMAL_SABERTOOTH_CRY = registry.registerSound("animal.sabertooth.cry");
		ANIMAL_SABERTOOTH_HURT = registry.registerSound("animal.sabertooth.hurt");
		ANIMAL_SABERTOOTH_DEATH = registry.registerSound("animal.sabertooth.death");

		ANIMAL_LION_SAY = registry.registerSound("animal.lion.say");
		ANIMAL_LION_CRY = registry.registerSound("animal.lion.cry");
		ANIMAL_LION_HURT = registry.registerSound("animal.lion.hurt");
		ANIMAL_LION_DEATH = registry.registerSound("animal.lion.death");

		ANIMAL_HYENA_SAY = registry.registerSound("animal.hyena.say");
		ANIMAL_HYENA_CRY = registry.registerSound("animal.hyena.cry");
		ANIMAL_HYENA_HURT = registry.registerSound("animal.hyena.hurt");
		ANIMAL_HYENA_DEATH = registry.registerSound("animal.hyena.death");

		ANIMAL_ZEBU_SAY = registry.registerSound("animal.zebu.say");
		ANIMAL_ZEBU_HURT = registry.registerSound("animal.zebu.hurt");
		ANIMAL_ZEBU_DEATH = registry.registerSound("animal.zebu.death");

		ANIMAL_MUSKOX_SAY = registry.registerSound("animal.muskox.say");
		ANIMAL_MUSKOX_HURT = registry.registerSound("animal.muskox.hurt");
		ANIMAL_MUSKOX_DEATH = registry.registerSound("animal.muskox.death");

		ANIMAL_TURKEY_SAY = registry.registerSound("animal.turkey.say");
		ANIMAL_TURKEY_HURT = registry.registerSound("animal.turkey.hurt");
		ANIMAL_TURKEY_DEATH = registry.registerSound("animal.turkey.death");

		ANIMAL_BOAR_SAY = registry.registerSound("animal.boar.say");
		ANIMAL_BOAR_HURT = registry.registerSound("animal.boar.hurt");
		ANIMAL_BOAR_DEATH = registry.registerSound("animal.boar.death");

		ANIMAL_WILDEBEEST_SAY = registry.registerSound("animal.wildebeest.say");
		ANIMAL_WILDEBEEST_HURT = registry.registerSound("animal.wildebeest.hurt");
		ANIMAL_WILDEBEEST_DEATH = registry.registerSound("animal.wildebeest.death");

		ANIMAL_GROUSE_SAY = registry.registerSound("animal.grouse.say");
		ANIMAL_GROUSE_HURT = registry.registerSound("animal.grouse.hurt");
		ANIMAL_GROUSE_DEATH = registry.registerSound("animal.grouse.death");

		ANIMAL_QUAIL_SAY = registry.registerSound("animal.quail.say");
		ANIMAL_QUAIL_HURT = registry.registerSound("animal.quail.hurt");
		ANIMAL_QUAIL_DEATH = registry.registerSound("animal.quail.death");

		ANIMAL_COYOTE_SAY = registry.registerSound("animal.coyote.say");
		ANIMAL_COYOTE_CRY = registry.registerSound("animal.coyote.cry");
		ANIMAL_COYOTE_HURT = registry.registerSound("animal.coyote.hurt");
		ANIMAL_COYOTE_DEATH = registry.registerSound("animal.coyote.death");

		ANIMAL_COUGAR_SAY = registry.registerSound("animal.cougar.say");
		ANIMAL_COUGAR_CRY = registry.registerSound("animal.cougar.cry");
		ANIMAL_COUGAR_HURT = registry.registerSound("animal.cougar.hurt");
		ANIMAL_COUGAR_DEATH = registry.registerSound("animal.cougar.death");

		ANIMAL_GAZELLE_SAY = registry.registerSound("animal.gazelle.say");
		ANIMAL_GAZELLE_HURT = registry.registerSound("animal.gazelle.hurt");
		ANIMAL_GAZELLE_DEATH = registry.registerSound("animal.gazelle.death");

		ANIMAL_DIREWOLF_SAY = registry.registerSound("animal.direwolf.say");
		ANIMAL_DIREWOLF_CRY = registry.registerSound("animal.direwolf.cry");
		ANIMAL_DIREWOLF_HURT = registry.registerSound("animal.direwolf.hurt");
		ANIMAL_DIREWOLF_DEATH = registry.registerSound("animal.direwolf.death");

		ANIMAL_YAK_SAY = registry.registerSound("animal.yak.say");
		ANIMAL_YAK_HURT = registry.registerSound("animal.yak.hurt");
		ANIMAL_YAK_DEATH = registry.registerSound("animal.yak.death");

		ANIMAL_JACKAL_SAY = registry.registerSound("animal.jackal.say");
		ANIMAL_JACKAL_CRY = registry.registerSound("animal.jackal.cry");
		ANIMAL_JACKAL_HURT = registry.registerSound("animal.jackal.hurt");
		ANIMAL_JACKAL_DEATH = registry.registerSound("animal.jackal.death");

		ANIMAL_MONGOOSE_SAY = registry.registerSound("animal.mongoose.say");
		ANIMAL_MONGOOSE_HURT = registry.registerSound("animal.mongoose.hurt");
		ANIMAL_MONGOOSE_DEATH = registry.registerSound("animal.mongoose.death");

		ANIMAL_FELINE_STEP = registry.registerSound("animal.feline.step");


	}
}
