package su.terrafirmagreg.modules.animal.data;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.client.render.*;
import su.terrafirmagreg.modules.animal.objects.entities.EntitySeatOn;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.*;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.*;
import su.terrafirmagreg.modules.animal.objects.entities.predator.*;

import java.awt.*;

public class EntitiesAnimal {

	public static void onRegister(RegistryManager registry) {

		registry.registerEntity("sitblock", EntityEntryBuilder.create()
				.entity(EntitySeatOn.class)
				.tracker(160, 20, true)
		);

		registry.registerEntity("sheep", EntityEntryBuilder.create()
				.entity(EntityAnimalSheep.class)
				.egg(Color.decode("0xFFFFFF").getRGB(), Color.decode("0xFF6347").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("cow", EntityEntryBuilder.create()
				.entity(EntityAnimalCow.class)
				.egg(Color.decode("0xA52A2A").getRGB(), Color.decode("0xFFFFFF").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("grizzlybear", EntityEntryBuilder.create()
				.entity(EntityAnimalGrizzlyBear.class)
				.egg(Color.decode("0xB22222").getRGB(), Color.decode("0xDEB887").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("chicken", EntityEntryBuilder.create()
				.entity(EntityAnimalChicken.class)
				.egg(Color.decode("0x557755").getRGB(), Color.decode("0xFFF91F").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("pheasant", EntityEntryBuilder.create()
				.entity(EntityAnimalPheasant.class)
				.egg(Color.decode("0x5577FF").getRGB(), Color.decode("0xFFFA90").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("deer", EntityEntryBuilder.create()
				.entity(EntityAnimalDeer.class)
				.egg(Color.decode("0x55FF55").getRGB(), Color.decode("0x5FFAAF").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("pig", EntityEntryBuilder.create()
				.entity(EntityAnimalPig.class)
				.egg(Color.decode("0xAA7722").getRGB(), Color.decode("0xFFEBCD").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("wolf", EntityEntryBuilder.create()
				.entity(EntityAnimalWolf.class)
				.egg(Color.decode("0xB0ACAC").getRGB(), Color.decode("0x796555").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("rabbit", EntityEntryBuilder.create()
				.entity(EntityAnimalRabbit.class)
				.egg(Color.decode("0x885040").getRGB(), Color.decode("0x462612").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("horse", EntityEntryBuilder.create()
				.entity(EntityAnimalHorse.class)
				.egg(Color.decode("0xA5886B").getRGB(), Color.decode("0xABA400").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("donkey", EntityEntryBuilder.create()
				.entity(EntityAnimalDonkey.class)
				.egg(Color.decode("0x493C32").getRGB(), Color.decode("0x756659").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("mule", EntityEntryBuilder.create()
				.entity(EntityAnimalMule.class)
				.egg(Color.decode("0x180200").getRGB(), Color.decode("0x482D1A").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("polarbear", EntityEntryBuilder.create()
				.entity(EntityAnimalPolarBear.class)
				.egg(Color.decode("0xF1FFF1").getRGB(), Color.decode("0xA0A0A0").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("parrot", EntityEntryBuilder.create()
				.entity(EntityAnimalParrot.class)
				.egg(Color.decode("0x885040").getRGB(), Color.decode("0xB0ACAC").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("llama", EntityEntryBuilder.create()
				.entity(EntityAnimalLlama.class)
				.egg(Color.decode("0xA52A2A").getRGB(), Color.decode("0xAA7722").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("ocelot", EntityEntryBuilder.create()
				.entity(EntityAnimalOcelot.class)
				.egg(Color.decode("0x3527FA").getRGB(), Color.decode("0x7F23A0").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("panther", EntityEntryBuilder.create()
				.entity(EntityAnimalPanther.class)
				.egg(Color.decode("0x000066").getRGB(), Color.decode("0x000000").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("duck", EntityEntryBuilder.create()
				.entity(EntityAnimalDuck.class)
				.egg(Color.decode("0xFFF91F").getRGB(), Color.decode("0x462612").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("alpaca", EntityEntryBuilder.create()
				.entity(EntityAnimalAlpaca.class)
				.egg(Color.decode("0x00CC66").getRGB(), Color.decode("0x006633").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("goat", EntityEntryBuilder.create()
				.entity(EntityAnimalGoat.class)
				.egg(Color.decode("0xA0A0A0").getRGB(), Color.decode("0x404040").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("sabertooth", EntityEntryBuilder.create()
				.entity(EntityAnimalSaberTooth.class)
				.egg(Color.decode("0xFF8000").getRGB(), Color.decode("0xFFD700").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("camel", EntityEntryBuilder.create()
				.entity(EntityAnimalCamel.class)
				.egg(Color.decode("0xA5886B").getRGB(), Color.decode("0x006633").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("lion", EntityEntryBuilder.create()
				.entity(EntityAnimalLion.class)
				.egg(Color.decode("0xDAA520").getRGB(), Color.decode("0xA0522D").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("hyena", EntityEntryBuilder.create()
				.entity(EntityAnimalHyena.class)
				.egg(Color.decode("0x666600").getRGB(), Color.decode("0x331900").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("direwolf", EntityEntryBuilder.create()
				.entity(EntityAnimalDireWolf.class)
				.egg(Color.decode("0x343434").getRGB(), Color.decode("0x978f7e").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("hare", EntityEntryBuilder.create()
				.entity(EntityAnimalHare.class)
				.egg(Color.decode("0x866724").getRGB(), Color.decode("0xDADADA").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("boar", EntityEntryBuilder.create()
				.entity(EntityAnimalBoar.class)
				.egg(Color.decode("0x463c09").getRGB(), Color.decode("0xe39ad8").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("zebu", EntityEntryBuilder.create()
				.entity(EntityAnimalZebu.class)
				.egg(Color.decode("0x2c2507").getRGB(), Color.decode("0xbcb38e").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("gazelle", EntityEntryBuilder.create()
				.entity(EntityAnimalGazelle.class)
				.egg(Color.decode("0xa9a76f").getRGB(), Color.decode("0xc0ab55").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("wildebeest", EntityEntryBuilder.create()
				.entity(EntityAnimalWildebeest.class)
				.egg(Color.decode("0x696142").getRGB(), Color.decode("0x9c8115").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("quail", EntityEntryBuilder.create()
				.entity(EntityAnimalQuail.class)
				.egg(Color.decode("0x237ddc").getRGB(), Color.decode("0xe3e36d").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("grouse", EntityEntryBuilder.create()
				.entity(EntityAnimalGrouse.class)
				.egg(Color.decode("0xf7a100").getRGB(), Color.decode("0x71ffd0").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("mongoose", EntityEntryBuilder.create()
				.entity(EntityAnimalMongoose.class)
				.egg(Color.decode("0xf9f50f").getRGB(), Color.decode("0x90ec7f").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("turkey", EntityEntryBuilder.create()
				.entity(EntityAnimalTurkey.class)
				.egg(Color.decode("0xad1d1d").getRGB(), Color.decode("0xeaa659").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("jackal", EntityEntryBuilder.create()
				.entity(EntityAnimalJackal.class)
				.egg(Color.decode("0xb8762b").getRGB(), Color.decode("0xffffff").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("muskox", EntityEntryBuilder.create()
				.entity(EntityAnimalMuskOx.class)
				.egg(Color.decode("0x620d55").getRGB(), Color.decode("0xcdaf4f").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("yak", EntityEntryBuilder.create()
				.entity(EntityAnimalYak.class)
				.egg(Color.decode("0x837669").getRGB(), Color.decode("0x3e3d7c").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("blackbear", EntityEntryBuilder.create()
				.entity(EntityAnimalBlackBear.class)
				.egg(Color.decode("0x000000").getRGB(), Color.decode("0xa18f6c").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("cougar", EntityEntryBuilder.create()
				.entity(EntityAnimalCougar.class)
				.egg(Color.decode("0x817a00").getRGB(), Color.decode("0xdcd889").getRGB())
				.tracker(80, 3, true)
		);

		registry.registerEntity("coyote", EntityEntryBuilder.create()
				.entity(EntityAnimalCoyote.class)
				.egg(Color.decode("0xb7bc88").getRGB(), Color.decode("0xdac213").getRGB())
				.tracker(80, 3, true)
		);
	}

	@SideOnly(Side.CLIENT)
	public static void onClientRegister() {
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
