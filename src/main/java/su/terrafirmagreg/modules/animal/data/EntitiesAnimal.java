package su.terrafirmagreg.modules.animal.data;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.client.render.*;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.*;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.*;
import su.terrafirmagreg.modules.animal.objects.entities.predator.*;

public final class EntitiesAnimal {

	public static void onRegister(RegistryManager registry) {

//		registry.registerEntity("sitblock", EntityEntryBuilder.create()
//				.entity(EntitySeatOn.class)
//				.tracker(160, 20, true)
//		);

		registry.registerEntity("sheep", EntityAnimalSheep.class, 0xFFFFFF, 0xFF6347);
		registry.registerEntity("cow", EntityAnimalCow.class, 0xA52A2A, 0xFFFFFF);
		registry.registerEntity("grizzlybear", EntityAnimalGrizzlyBear.class, 0xB22222, 0xDEB887);
		registry.registerEntity("chicken", EntityAnimalChicken.class, 0x557755, 0xFFF91F);
		registry.registerEntity("pheasant", EntityAnimalPheasant.class, 0x5577FF, 0xFFFA90);
		registry.registerEntity("deer", EntityAnimalDeer.class, 0x55FF55, 0x5FFAAF);
		registry.registerEntity("pig", EntityAnimalPig.class, 0xAA7722, 0xFFEBCD);
		registry.registerEntity("wolf", EntityAnimalWolf.class, 0xB0ACAC, 0x796555);
		registry.registerEntity("rabbit", EntityAnimalRabbit.class, 0x885040, 0x462612);
		registry.registerEntity("horse", EntityAnimalHorse.class, 0xA5886B, 0xABA400);
		registry.registerEntity("donkey", EntityAnimalDonkey.class, 0x493C32, 0x756659);
		registry.registerEntity("mule", EntityAnimalMule.class, 0x180200, 0x482D1A);
		registry.registerEntity("polarbear", EntityAnimalPolarBear.class, 0xF1FFF1, 0xA0A0A0);
		registry.registerEntity("parrot", EntityAnimalParrot.class, 0x885040, 0xB0ACAC);
		registry.registerEntity("llama", EntityAnimalLlama.class, 0xA52A2A, 0xAA7722);
		registry.registerEntity("ocelot", EntityAnimalOcelot.class, 0x3527FA, 0x7F23A0);
		registry.registerEntity("panther", EntityAnimalPanther.class, 0x000066, 0x000000);
		registry.registerEntity("duck", EntityAnimalDuck.class, 0xFFF91F, 0x462612);
		registry.registerEntity("alpaca", EntityAnimalAlpaca.class, 0x00CC66, 0x006633);
		registry.registerEntity("goat", EntityAnimalGoat.class, 0xA0A0A0, 0x404040);
		registry.registerEntity("sabertooth", EntityAnimalSaberTooth.class, 0xFF8000, 0xFFD700);
		registry.registerEntity("camel", EntityAnimalCamel.class, 0xA5886B, 0x006633);
		registry.registerEntity("lion", EntityAnimalLion.class, 0xDAA520, 0xA0522D);
		registry.registerEntity("hyena", EntityAnimalHyena.class, 0x666600, 0x331900);
		registry.registerEntity("direwolf", EntityAnimalDireWolf.class, 0x343434, 0x978f7e);
		registry.registerEntity("hare", EntityAnimalHare.class, 0x866724, 0xDADADA);
		registry.registerEntity("boar", EntityAnimalBoar.class, 0x463c09, 0xe39ad8);
		registry.registerEntity("zebu", EntityAnimalZebu.class, 0x2c2507, 0xbcb38e);
		registry.registerEntity("gazelle", EntityAnimalGazelle.class, 0xa9a76f, 0xc0ab55);
		registry.registerEntity("wildebeest", EntityAnimalWildebeest.class, 0x696142, 0x9c8115);
		registry.registerEntity("quail", EntityAnimalQuail.class, 0x237ddc, 0xe3e36d);
		registry.registerEntity("grouse", EntityAnimalGrouse.class, 0xf7a100, 0x71ffd0);
		registry.registerEntity("mongoose", EntityAnimalMongoose.class, 0xf9f50f, 0x90ec7f);
		registry.registerEntity("turkey", EntityAnimalTurkey.class, 0xad1d1d, 0xeaa659);
		registry.registerEntity("jackal", EntityAnimalJackal.class, 0xb8762b, 0xffffff);
		registry.registerEntity("muskox", EntityAnimalMuskOx.class, 0x620d55, 0xcdaf4f);
		registry.registerEntity("yak", EntityAnimalYak.class, 0x837669, 0x3e3d7c);
		registry.registerEntity("blackbear", EntityAnimalBlackBear.class, 0x000000, 0xa18f6c);
		registry.registerEntity("cougar", EntityAnimalCougar.class, 0x817a00, 0xdcd889);
		registry.registerEntity("coyote", EntityAnimalCoyote.class, 0xb7bc88, 0xdac213);
	}

	@SideOnly(Side.CLIENT)
	public static void onClientRegister(RegistryManager registry) {
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalSheep.class, RenderAnimalSheep::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalCow.class, RenderAnimalCow::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalGrizzlyBear.class, RenderAnimalGrizzlyBear::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalChicken.class, RenderAnimalChicken::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalPheasant.class, RenderAnimalPheasant::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalDeer.class, RenderAnimalDeer::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalPig.class, RenderAnimalPig::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalWolf.class, RenderAnimalWolf::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalRabbit.class, RenderAnimalRabbit::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalHorse.class, RenderAnimalHorse::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalDonkey.class, RenderAnimalAbstractHorse::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalMule.class, RenderAnimalAbstractHorse::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalPolarBear.class, RenderAnimalPolarBear::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalParrot.class, RenderAnimalParrot::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalLlama.class, RenderAnimalLlama::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalOcelot.class, RenderAnimalOcelot::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalPanther.class, RenderAnimalPanther::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalDuck.class, RenderAnimalDuck::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalAlpaca.class, RenderAnimalAlpaca::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalGoat.class, RenderAnimalGoat::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalSaberTooth.class, RenderAnimalSaberTooth::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalCamel.class, RenderAnimalCamel::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalLion.class, RenderAnimalLion::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalHyena.class, RenderAnimalHyena::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalDireWolf.class, RenderAnimalDireWolf::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalHare.class, RenderAnimalHare::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalBoar.class, RenderAnimalBoar::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalZebu.class, RenderAnimalZebu::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalGazelle.class, RenderAnimalGazelle::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalWildebeest.class, RenderAnimalWildebeest::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalQuail.class, RenderAnimalQuail::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalGrouse.class, RenderAnimalGrouse::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalMongoose.class, RenderAnimalMongoose::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalTurkey.class, RenderAnimalTurkey::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalJackal.class, RenderAnimalJackal::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalMuskOx.class, RenderAnimalMuskOx::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalYak.class, RenderAnimalYak::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalBlackBear.class, RenderAnimalBlackBear::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalCougar.class, RenderAnimalCougar::new);
		RenderingRegistry.registerEntityRenderingHandler(EntityAnimalCoyote.class, RenderAnimalCoyote::new);
	}
}
