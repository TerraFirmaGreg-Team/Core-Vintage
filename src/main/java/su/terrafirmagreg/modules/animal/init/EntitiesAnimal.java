package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalAbstractHorse;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalAlpaca;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalBlackBear;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalBoar;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalCamel;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalChicken;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalCougar;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalCow;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalCoyote;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalDeer;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalDireWolf;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalDuck;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalGazelle;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalGoat;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalGrizzlyBear;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalGrouse;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalHare;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalHorse;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalHyena;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalJackal;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalLion;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalLlama;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalMongoose;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalMuskOx;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalOcelot;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalPanther;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalParrot;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalPheasant;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalPig;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalPolarBear;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalQuail;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalRabbit;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalSaberTooth;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalSheep;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalTurkey;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalWildebeest;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalWolf;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalYak;
import su.terrafirmagreg.modules.animal.client.render.RenderAnimalZebu;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalBoar;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalDeer;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalGazelle;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalHare;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalMongoose;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalPheasant;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalRabbit;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalTurkey;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalWildebeest;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalAlpaca;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalCamel;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalChicken;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalCow;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalDonkey;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalDuck;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalGoat;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalGrouse;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalHorse;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalLlama;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalMule;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalMuskOx;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalOcelot;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalParrot;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalPig;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalQuail;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalSheep;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalWolf;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalYak;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalZebu;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalBlackBear;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalCougar;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalCoyote;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalDireWolf;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalGrizzlyBear;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalHyena;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalJackal;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalLion;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalPanther;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalPolarBear;
import su.terrafirmagreg.modules.animal.objects.entities.predator.EntityAnimalSaberTooth;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public final class EntitiesAnimal {

  public static EntityEntry SHEEP;
  public static EntityEntry COW;
  public static EntityEntry GRIZZLYBEAR;
  public static EntityEntry CHICKEN;
  public static EntityEntry PHEASANT;
  public static EntityEntry DEER;
  public static EntityEntry PIG;
  public static EntityEntry WOLF;
  public static EntityEntry RABBIT;
  public static EntityEntry HORSE;
  public static EntityEntry DONKEY;
  public static EntityEntry MULE;
  public static EntityEntry POLARBEAR;
  public static EntityEntry PARROT;
  public static EntityEntry LLAMA;
  public static EntityEntry OCELOT;
  public static EntityEntry PANTHER;
  public static EntityEntry DUCK;
  public static EntityEntry ALPACA;
  public static EntityEntry GOAT;
  public static EntityEntry SABERTOOTH;
  public static EntityEntry CAMEL;
  public static EntityEntry LION;
  public static EntityEntry HYENA;
  public static EntityEntry DIREWOLF;
  public static EntityEntry HARE;
  public static EntityEntry BOAR;
  public static EntityEntry ZEBU;
  public static EntityEntry GAZELLE;
  public static EntityEntry WILDEBEEST;
  public static EntityEntry QUAIL;
  public static EntityEntry GROUSE;
  public static EntityEntry MONGOOSE;
  public static EntityEntry TURKEY;
  public static EntityEntry JACKAL;
  public static EntityEntry MUSKOX;
  public static EntityEntry YAK;
  public static EntityEntry BLACKBEAR;
  public static EntityEntry COUGAR;
  public static EntityEntry COYOTE;

  public static void onRegister(RegistryManager registry) {

    SHEEP = registry.entity("sheep", EntityAnimalSheep.class, 0xFFFFFF, 0xFF6347);
    COW = registry.entity("cow", EntityAnimalCow.class, 0xA52A2A, 0xFFFFFF);
    GRIZZLYBEAR = registry.entity("grizzlybear", EntityAnimalGrizzlyBear.class, 0xB22222, 0xDEB887);
    CHICKEN = registry.entity("chicken", EntityAnimalChicken.class, 0x557755, 0xFFF91F);
    PHEASANT = registry.entity("pheasant", EntityAnimalPheasant.class, 0x5577FF, 0xFFFA90);
    DEER = registry.entity("deer", EntityAnimalDeer.class, 0x55FF55, 0x5FFAAF);
    PIG = registry.entity("pig", EntityAnimalPig.class, 0xAA7722, 0xFFEBCD);
    WOLF = registry.entity("wolf", EntityAnimalWolf.class, 0xB0ACAC, 0x796555);
    RABBIT = registry.entity("rabbit", EntityAnimalRabbit.class, 0x885040, 0x462612);
    HORSE = registry.entity("horse", EntityAnimalHorse.class, 0xA5886B, 0xABA400);
    DONKEY = registry.entity("donkey", EntityAnimalDonkey.class, 0x493C32, 0x756659);
    MULE = registry.entity("mule", EntityAnimalMule.class, 0x180200, 0x482D1A);
    POLARBEAR = registry.entity("polarbear", EntityAnimalPolarBear.class, 0xF1FFF1, 0xA0A0A0);
    PARROT = registry.entity("parrot", EntityAnimalParrot.class, 0x885040, 0xB0ACAC);
    LLAMA = registry.entity("llama", EntityAnimalLlama.class, 0xA52A2A, 0xAA7722);
    OCELOT = registry.entity("ocelot", EntityAnimalOcelot.class, 0x3527FA, 0x7F23A0);
    PANTHER = registry.entity("panther", EntityAnimalPanther.class, 0x000066, 0x000000);
    DUCK = registry.entity("duck", EntityAnimalDuck.class, 0xFFF91F, 0x462612);
    ALPACA = registry.entity("alpaca", EntityAnimalAlpaca.class, 0x00CC66, 0x006633);
    GOAT = registry.entity("goat", EntityAnimalGoat.class, 0xA0A0A0, 0x404040);
    SABERTOOTH = registry.entity("sabertooth", EntityAnimalSaberTooth.class, 0xFF8000, 0xFFD700);
    CAMEL = registry.entity("camel", EntityAnimalCamel.class, 0xA5886B, 0x006633);
    LION = registry.entity("lion", EntityAnimalLion.class, 0xDAA520, 0xA0522D);
    HYENA = registry.entity("hyena", EntityAnimalHyena.class, 0x666600, 0x331900);
    DIREWOLF = registry.entity("direwolf", EntityAnimalDireWolf.class, 0x343434, 0x978f7e);
    HARE = registry.entity("hare", EntityAnimalHare.class, 0x866724, 0xDADADA);
    BOAR = registry.entity("boar", EntityAnimalBoar.class, 0x463c09, 0xe39ad8);
    ZEBU = registry.entity("zebu", EntityAnimalZebu.class, 0x2c2507, 0xbcb38e);
    GAZELLE = registry.entity("gazelle", EntityAnimalGazelle.class, 0xa9a76f, 0xc0ab55);
    WILDEBEEST = registry.entity("wildebeest", EntityAnimalWildebeest.class, 0x696142, 0x9c8115);
    QUAIL = registry.entity("quail", EntityAnimalQuail.class, 0x237ddc, 0xe3e36d);
    GROUSE = registry.entity("grouse", EntityAnimalGrouse.class, 0xf7a100, 0x71ffd0);
    MONGOOSE = registry.entity("mongoose", EntityAnimalMongoose.class, 0xf9f50f, 0x90ec7f);
    TURKEY = registry.entity("turkey", EntityAnimalTurkey.class, 0xad1d1d, 0xeaa659);
    JACKAL = registry.entity("jackal", EntityAnimalJackal.class, 0xb8762b, 0xffffff);
    MUSKOX = registry.entity("muskox", EntityAnimalMuskOx.class, 0x620d55, 0xcdaf4f);
    YAK = registry.entity("yak", EntityAnimalYak.class, 0x837669, 0x3e3d7c);
    BLACKBEAR = registry.entity("blackbear", EntityAnimalBlackBear.class, 0x000000, 0xa18f6c);
    COUGAR = registry.entity("cougar", EntityAnimalCougar.class, 0x817a00, 0xdcd889);
    COYOTE = registry.entity("coyote", EntityAnimalCoyote.class, 0xb7bc88, 0xdac213);
  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(RegistryManager registry) {
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalSheep.class,
        RenderAnimalSheep::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalCow.class, RenderAnimalCow::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalGrizzlyBear.class,
        RenderAnimalGrizzlyBear::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalChicken.class,
        RenderAnimalChicken::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalPheasant.class,
        RenderAnimalPheasant::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalDeer.class, RenderAnimalDeer::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalPig.class, RenderAnimalPig::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalWolf.class, RenderAnimalWolf::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalRabbit.class,
        RenderAnimalRabbit::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalHorse.class,
        RenderAnimalHorse::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalDonkey.class,
        RenderAnimalAbstractHorse::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalMule.class,
        RenderAnimalAbstractHorse::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalPolarBear.class,
        RenderAnimalPolarBear::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalParrot.class,
        RenderAnimalParrot::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalLlama.class,
        RenderAnimalLlama::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalOcelot.class,
        RenderAnimalOcelot::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalPanther.class,
        RenderAnimalPanther::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalDuck.class, RenderAnimalDuck::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalAlpaca.class,
        RenderAnimalAlpaca::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalGoat.class, RenderAnimalGoat::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalSaberTooth.class,
        RenderAnimalSaberTooth::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalCamel.class,
        RenderAnimalCamel::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalLion.class, RenderAnimalLion::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalHyena.class,
        RenderAnimalHyena::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalDireWolf.class,
        RenderAnimalDireWolf::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalHare.class, RenderAnimalHare::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalBoar.class, RenderAnimalBoar::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalZebu.class, RenderAnimalZebu::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalGazelle.class,
        RenderAnimalGazelle::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalWildebeest.class,
        RenderAnimalWildebeest::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalQuail.class,
        RenderAnimalQuail::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalGrouse.class,
        RenderAnimalGrouse::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalMongoose.class,
        RenderAnimalMongoose::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalTurkey.class,
        RenderAnimalTurkey::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalJackal.class,
        RenderAnimalJackal::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalMuskOx.class,
        RenderAnimalMuskOx::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalYak.class, RenderAnimalYak::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalBlackBear.class,
        RenderAnimalBlackBear::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalCougar.class,
        RenderAnimalCougar::new);
    RenderingRegistry.registerEntityRenderingHandler(EntityAnimalCoyote.class,
        RenderAnimalCoyote::new);
  }
}
