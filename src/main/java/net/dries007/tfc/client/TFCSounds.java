/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.client;

import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.api.lib.Constants.MODID_TFC;

@Mod.EventBusSubscriber(modid = MODID_TFC)
public class TFCSounds {
	@GameRegistry.ObjectHolder(MODID_TFC + ":rock.slide.long")
	public static final SoundEvent ROCK_SLIDE_LONG = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":rock.slide.short")
	public static final SoundEvent ROCK_SLIDE_SHORT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":dirt.slide.short")
	public static final SoundEvent DIRT_SLIDE_SHORT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":bellows.blow.air")
	public static final SoundEvent BELLOWS_BLOW_AIR = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":quern.stonedrag")
	public static final SoundEvent QUERN_USE = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":item.ceramicbreak")
	public static final SoundEvent CERAMIC_BREAK = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":anvil.metalimpact")
	public static final SoundEvent ANVIL_IMPACT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":item.throw")
	public static final SoundEvent ITEM_THROW = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":item.jug.blow")
	public static final SoundEvent JUG_BLOW = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":item.jug.fill")
	public static final SoundEvent JUG_FILL = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":item.firestarter")
	public static final SoundEvent FIRE_STARTER = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.bear.say")
	public static final SoundEvent ANIMAL_BEAR_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.bear.cry")
	public static final SoundEvent ANIMAL_BEAR_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.bear.hurt")
	public static final SoundEvent ANIMAL_BEAR_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.bear.death")
	public static final SoundEvent ANIMAL_BEAR_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.deer.say")
	public static final SoundEvent ANIMAL_DEER_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.deer.cry")
	public static final SoundEvent ANIMAL_DEER_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.deer.hurt")
	public static final SoundEvent ANIMAL_DEER_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.deer.death")
	public static final SoundEvent ANIMAL_DEER_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.pheasant.say")
	public static final SoundEvent ANIMAL_PHEASANT_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.pheasant.hurt")
	public static final SoundEvent ANIMAL_PHEASANT_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.pheasant.death")
	public static final SoundEvent ANIMAL_PHEASANT_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.rooster.cry")
	public static final SoundEvent ANIMAL_ROOSTER_CRY = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.alpaca.say")
	public static final SoundEvent ANIMAL_ALPACA_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.alpaca.cry")
	public static final SoundEvent ANIMAL_ALPACA_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.alpaca.hurt")
	public static final SoundEvent ANIMAL_ALPACA_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.alpaca.death")
	public static final SoundEvent ANIMAL_ALPACA_DEATH = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.alpaca.step")
	public static final SoundEvent ANIMAL_ALPACA_STEP = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.duck.say")
	public static final SoundEvent ANIMAL_DUCK_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.duck.cry")
	public static final SoundEvent ANIMAL_DUCK_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.duck.hurt")
	public static final SoundEvent ANIMAL_DUCK_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.duck.death")
	public static final SoundEvent ANIMAL_DUCK_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.goat.say")
	public static final SoundEvent ANIMAL_GOAT_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.goat.cry")
	public static final SoundEvent ANIMAL_GOAT_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.goat.hurt")
	public static final SoundEvent ANIMAL_GOAT_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.goat.death")
	public static final SoundEvent ANIMAL_GOAT_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.camel.say")
	public static final SoundEvent ANIMAL_CAMEL_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.camel.cry")
	public static final SoundEvent ANIMAL_CAMEL_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.camel.hurt")
	public static final SoundEvent ANIMAL_CAMEL_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.camel.death")
	public static final SoundEvent ANIMAL_CAMEL_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.panther.say")
	public static final SoundEvent ANIMAL_PANTHER_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.panther.cry")
	public static final SoundEvent ANIMAL_PANTHER_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.panther.hurt")
	public static final SoundEvent ANIMAL_PANTHER_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.panther.death")
	public static final SoundEvent ANIMAL_PANTHER_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.sabertooth.say")
	public static final SoundEvent ANIMAL_SABERTOOTH_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.sabertooth.cry")
	public static final SoundEvent ANIMAL_SABERTOOTH_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.sabertooth.hurt")
	public static final SoundEvent ANIMAL_SABERTOOTH_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.sabertooth.death")
	public static final SoundEvent ANIMAL_SABERTOOTH_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.lion.death")
	public static final SoundEvent ANIMAL_LION_DEATH = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.lion.hurt")
	public static final SoundEvent ANIMAL_LION_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.lion.cry")
	public static final SoundEvent ANIMAL_LION_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.lion.say")
	public static final SoundEvent ANIMAL_LION_SAY = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.hyena.say")
	public static final SoundEvent ANIMAL_HYENA_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.hyena.cry")
	public static final SoundEvent ANIMAL_HYENA_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.hyena.hurt")
	public static final SoundEvent ANIMAL_HYENA_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.hyena.death")
	public static final SoundEvent ANIMAL_HYENA_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.zebu.say")
	public static final SoundEvent ANIMAL_ZEBU_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.zebu.hurt")
	public static final SoundEvent ANIMAL_ZEBU_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.zebu.death")
	public static final SoundEvent ANIMAL_ZEBU_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.muskox.say")
	public static final SoundEvent ANIMAL_MUSKOX_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.muskox.hurt")
	public static final SoundEvent ANIMAL_MUSKOX_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.muskox.death")
	public static final SoundEvent ANIMAL_MUSKOX_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.turkey.say")
	public static final SoundEvent ANIMAL_TURKEY_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.turkey.hurt")
	public static final SoundEvent ANIMAL_TURKEY_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.turkey.death")
	public static final SoundEvent ANIMAL_TURKEY_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.boar.say")
	public static final SoundEvent ANIMAL_BOAR_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.boar.hurt")
	public static final SoundEvent ANIMAL_BOAR_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.boar.death")
	public static final SoundEvent ANIMAL_BOAR_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.wildebeest.say")
	public static final SoundEvent ANIMAL_WILDEBEEST_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.wildebeest.hurt")
	public static final SoundEvent ANIMAL_WILDEBEEST_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.wildebeest.death")
	public static final SoundEvent ANIMAL_WILDEBEEST_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.grouse.say")
	public static final SoundEvent ANIMAL_GROUSE_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.grouse.hurt")
	public static final SoundEvent ANIMAL_GROUSE_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.grouse.death")
	public static final SoundEvent ANIMAL_GROUSE_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.quail.say")
	public static final SoundEvent ANIMAL_QUAIL_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.quail.hurt")
	public static final SoundEvent ANIMAL_QUAIL_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.quail.death")
	public static final SoundEvent ANIMAL_QUAIL_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.coyote.say")
	public static final SoundEvent ANIMAL_COYOTE_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.coyote.cry")
	public static final SoundEvent ANIMAL_COYOTE_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.coyote.hurt")
	public static final SoundEvent ANIMAL_COYOTE_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.coyote.death")
	public static final SoundEvent ANIMAL_COYOTE_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.cougar.say")
	public static final SoundEvent ANIMAL_COUGAR_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.cougar.cry")
	public static final SoundEvent ANIMAL_COUGAR_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.cougar.hurt")
	public static final SoundEvent ANIMAL_COUGAR_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.cougar.death")
	public static final SoundEvent ANIMAL_COUGAR_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.gazelle.say")
	public static final SoundEvent ANIMAL_GAZELLE_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.gazelle.hurt")
	public static final SoundEvent ANIMAL_GAZELLE_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.gazelle.death")
	public static final SoundEvent ANIMAL_GAZELLE_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.direwolf.say")
	public static final SoundEvent ANIMAL_DIREWOLF_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.direwolf.cry")
	public static final SoundEvent ANIMAL_DIREWOLF_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.direwolf.hurt")
	public static final SoundEvent ANIMAL_DIREWOLF_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.direwolf.death")
	public static final SoundEvent ANIMAL_DIREWOLF_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.yak.say")
	public static final SoundEvent ANIMAL_YAK_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.yak.hurt")
	public static final SoundEvent ANIMAL_YAK_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.yak.death")
	public static final SoundEvent ANIMAL_YAK_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.jackal.say")
	public static final SoundEvent ANIMAL_JACKAL_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.jackal.cry")
	public static final SoundEvent ANIMAL_JACKAL_CRY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.jackal.hurt")
	public static final SoundEvent ANIMAL_JACKAL_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.jackal.death")
	public static final SoundEvent ANIMAL_JACKAL_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.mongoose.say")
	public static final SoundEvent ANIMAL_MONGOOSE_SAY = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.mongoose.hurt")
	public static final SoundEvent ANIMAL_MONGOOSE_HURT = getNull();
	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.mongoose.death")
	public static final SoundEvent ANIMAL_MONGOOSE_DEATH = getNull();

	@GameRegistry.ObjectHolder(MODID_TFC + ":animal.feline.step")
	public static final SoundEvent ANIMAL_FELINE_STEP = getNull();

	// These are static initialized because we need a custom sound type which uses the sounds before initialization
	private static final SoundEvent CHARCOAL_PILE_BREAK = createSoundEvent("block.charcoal.break");
	private static final SoundEvent CHARCOAL_PILE_FALL = createSoundEvent("block.charcoal.fall");
	private static final SoundEvent CHARCOAL_PILE_HIT = createSoundEvent("block.charcoal.hit");
	private static final SoundEvent CHARCOAL_PILE_PLACE = createSoundEvent("block.charcoal.place");
	private static final SoundEvent CHARCOAL_PILE_STEP = createSoundEvent("block.charcoal.step");

	public static final SoundType CHARCOAL_PILE = new SoundType(1.0F, 1.0F, CHARCOAL_PILE_BREAK, CHARCOAL_PILE_STEP, CHARCOAL_PILE_PLACE, CHARCOAL_PILE_HIT, CHARCOAL_PILE_FALL);

	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		event.getRegistry().registerAll(
				// Custom block sounds
				CHARCOAL_PILE_BREAK,
				CHARCOAL_PILE_FALL,
				CHARCOAL_PILE_HIT,
				CHARCOAL_PILE_PLACE,
				CHARCOAL_PILE_STEP,
				// Misc
				createSoundEvent("rock.slide.long"),
				createSoundEvent("rock.slide.short"),
				createSoundEvent("dirt.slide.short"),
				createSoundEvent("bellows.blow.air"),
				createSoundEvent("quern.stonedrag"),
				createSoundEvent("item.ceramicbreak"),
				createSoundEvent("anvil.metalimpact"),
				createSoundEvent("item.throw"),
				createSoundEvent("item.jug.blow"),
				createSoundEvent("item.jug.fill"),
				createSoundEvent("item.firestarter"),
				// Animals
				createSoundEvent("animal.bear.cry"),
				createSoundEvent("animal.bear.say"),
				createSoundEvent("animal.bear.hurt"),
				createSoundEvent("animal.bear.death"),
				createSoundEvent("animal.deer.cry"),
				createSoundEvent("animal.deer.say"),
				createSoundEvent("animal.deer.hurt"),
				createSoundEvent("animal.deer.death"),
				createSoundEvent("animal.pheasant.say"),
				createSoundEvent("animal.pheasant.hurt"),
				createSoundEvent("animal.pheasant.death"),
				createSoundEvent("animal.rooster.cry"),
				createSoundEvent("animal.alpaca.cry"),
				createSoundEvent("animal.alpaca.say"),
				createSoundEvent("animal.alpaca.hurt"),
				createSoundEvent("animal.alpaca.death"),
				createSoundEvent("animal.duck.cry"),
				createSoundEvent("animal.duck.say"),
				createSoundEvent("animal.duck.hurt"),
				createSoundEvent("animal.duck.death"),
				createSoundEvent("animal.goat.cry"),
				createSoundEvent("animal.goat.say"),
				createSoundEvent("animal.goat.hurt"),
				createSoundEvent("animal.goat.death"),
				createSoundEvent("animal.camel.cry"),
				createSoundEvent("animal.camel.say"),
				createSoundEvent("animal.camel.hurt"),
				createSoundEvent("animal.camel.death"),
				createSoundEvent("animal.panther.cry"),
				createSoundEvent("animal.panther.say"),
				createSoundEvent("animal.panther.hurt"),
				createSoundEvent("animal.panther.death"),
				createSoundEvent("animal.sabertooth.cry"),
				createSoundEvent("animal.sabertooth.say"),
				createSoundEvent("animal.sabertooth.hurt"),
				createSoundEvent("animal.sabertooth.death"),
				createSoundEvent("animal.lion.cry"),
				createSoundEvent("animal.lion.say"),
				createSoundEvent("animal.lion.hurt"),
				createSoundEvent("animal.lion.death"),
				createSoundEvent("animal.hyena.cry"),
				createSoundEvent("animal.hyena.say"),
				createSoundEvent("animal.hyena.hurt"),
				createSoundEvent("animal.hyena.death"),
				createSoundEvent("animal.coyote.cry"),
				createSoundEvent("animal.coyote.say"),
				createSoundEvent("animal.coyote.hurt"),
				createSoundEvent("animal.coyote.death"),
				createSoundEvent("animal.gazelle.say"),
				createSoundEvent("animal.gazelle.hurt"),
				createSoundEvent("animal.gazelle.death"),
				createSoundEvent("animal.wildebeest.say"),
				createSoundEvent("animal.wildebeest.hurt"),
				createSoundEvent("animal.wildebeest.death"),
				createSoundEvent("animal.boar.say"),
				createSoundEvent("animal.boar.hurt"),
				createSoundEvent("animal.boar.death"),
				createSoundEvent("animal.direwolf.cry"),
				createSoundEvent("animal.direwolf.say"),
				createSoundEvent("animal.direwolf.hurt"),
				createSoundEvent("animal.direwolf.death"),
				createSoundEvent("animal.turkey.say"),
				createSoundEvent("animal.turkey.hurt"),
				createSoundEvent("animal.turkey.death"),
				createSoundEvent("animal.yak.say"),
				createSoundEvent("animal.yak.hurt"),
				createSoundEvent("animal.yak.death"),
				createSoundEvent("animal.muskox.say"),
				createSoundEvent("animal.muskox.hurt"),
				createSoundEvent("animal.muskox.death"),
				createSoundEvent("animal.zebu.say"),
				createSoundEvent("animal.zebu.hurt"),
				createSoundEvent("animal.zebu.death"),
				createSoundEvent("animal.mongoose.say"),
				createSoundEvent("animal.mongoose.hurt"),
				createSoundEvent("animal.mongoose.death"),
				createSoundEvent("animal.quail.say"),
				createSoundEvent("animal.quail.hurt"),
				createSoundEvent("animal.quail.death"),
				createSoundEvent("animal.grouse.say"),
				createSoundEvent("animal.grouse.hurt"),
				createSoundEvent("animal.grouse.death"),
				createSoundEvent("animal.cougar.cry"),
				createSoundEvent("animal.cougar.say"),
				createSoundEvent("animal.cougar.hurt"),
				createSoundEvent("animal.cougar.death"),
				createSoundEvent("animal.jackal.cry"),
				createSoundEvent("animal.jackal.say"),
				createSoundEvent("animal.jackal.hurt"),
				createSoundEvent("animal.jackal.death"),
				createSoundEvent("animal.feline.step")
		);
	}

	private static SoundEvent createSoundEvent(String name) {
		final ResourceLocation soundID = new ResourceLocation(MODID_TFC, name);
		return new SoundEvent(soundID).setRegistryName(soundID);
	}
}
