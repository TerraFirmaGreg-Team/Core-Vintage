package su.terrafirmagreg.modules.animal.init;

import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;

import net.minecraft.util.ResourceLocation;

public final class LootTablesAnimal {

  public static ResourceLocation ANIMALS_BLACK_BEAR;
  public static ResourceLocation ANIMALS_GRIZZLY_BEAR;
  public static ResourceLocation ANIMALS_POLAR_BEAR;
  public static ResourceLocation ANIMALS_CHICKEN;
  public static ResourceLocation ANIMALS_COW;
  public static ResourceLocation ANIMALS_DEER;
  public static ResourceLocation ANIMALS_PHEASANT;
  public static ResourceLocation ANIMALS_PIG;
  public static ResourceLocation ANIMALS_SHEEP;
  public static ResourceLocation ANIMALS_RABBIT;
  public static ResourceLocation ANIMALS_WOLF;
  public static ResourceLocation ANIMALS_HORSE;
  public static ResourceLocation ANIMALS_ALPACA;
  public static ResourceLocation ANIMALS_DUCK;
  public static ResourceLocation ANIMALS_GOAT;
  public static ResourceLocation ANIMALS_CAMEL;
  public static ResourceLocation ANIMALS_COUGAR;
  public static ResourceLocation ANIMALS_LLAMA;
  public static ResourceLocation ANIMALS_OCELOT;
  public static ResourceLocation ANIMALS_SQUID;
  public static ResourceLocation ANIMALS_PARROT;
  public static ResourceLocation ANIMALS_HYENA;
  public static ResourceLocation ANIMALS_MUSKOX;
  public static ResourceLocation ANIMALS_BOAR;
  public static ResourceLocation ANIMALS_COYOTE;
  public static ResourceLocation ANIMALS_DIREWOLF;
  public static ResourceLocation ANIMALS_DONKEY;
  public static ResourceLocation ANIMALS_GAZELLE;
  public static ResourceLocation ANIMALS_GROUSE;
  public static ResourceLocation ANIMALS_HARE;
  public static ResourceLocation ANIMALS_JACKAL;
  public static ResourceLocation ANIMALS_LION;
  public static ResourceLocation ANIMALS_MONGOOSE;
  public static ResourceLocation ANIMALS_MULE;
  public static ResourceLocation ANIMALS_PANTHER;
  public static ResourceLocation ANIMALS_QUAIL;
  public static ResourceLocation ANIMALS_SABERTOOTH;
  public static ResourceLocation ANIMALS_TURKEY;
  public static ResourceLocation ANIMALS_WILDEBEEST;
  public static ResourceLocation ANIMALS_YAK;
  public static ResourceLocation ANIMALS_ZEBU;

  public static void onRegister(IRegistryRegistrar registrar) {

    ANIMALS_BLACK_BEAR = registrar.addLoot("black_bear");
    ANIMALS_GRIZZLY_BEAR = registrar.addLoot("grizzly_bear");
    ANIMALS_POLAR_BEAR = registrar.addLoot("polar_bear");
    ANIMALS_CHICKEN = registrar.addLoot("chicken");
    ANIMALS_COW = registrar.addLoot("cow");
    ANIMALS_DEER = registrar.addLoot("deer");
    ANIMALS_PHEASANT = registrar.addLoot("pheasant");
    ANIMALS_PIG = registrar.addLoot("pig");
    ANIMALS_SHEEP = registrar.addLoot("sheep");
    ANIMALS_RABBIT = registrar.addLoot("rabbit");
    ANIMALS_WOLF = registrar.addLoot("wolf");
    ANIMALS_HORSE = registrar.addLoot("horse");
    ANIMALS_ALPACA = registrar.addLoot("alpaca");
    ANIMALS_DUCK = registrar.addLoot("duck");
    ANIMALS_GOAT = registrar.addLoot("goat");
    ANIMALS_CAMEL = registrar.addLoot("camel");
    ANIMALS_COUGAR = registrar.addLoot("cougar");
    ANIMALS_LLAMA = registrar.addLoot("llama");
    ANIMALS_OCELOT = registrar.addLoot("ocelot");
    ANIMALS_SQUID = registrar.addLoot("squid");
    ANIMALS_PARROT = registrar.addLoot("parrot");
    ANIMALS_HYENA = registrar.addLoot("hyena");
    ANIMALS_MUSKOX = registrar.addLoot("muskox");
    ANIMALS_BOAR = registrar.addLoot("boar");
    ANIMALS_COYOTE = registrar.addLoot("coyote");
    ANIMALS_DIREWOLF = registrar.addLoot("direwolf");
    ANIMALS_DONKEY = registrar.addLoot("donkey");
    ANIMALS_GAZELLE = registrar.addLoot("gazelle");
    ANIMALS_GROUSE = registrar.addLoot("grouse");
    ANIMALS_HARE = registrar.addLoot("hare");
    ANIMALS_JACKAL = registrar.addLoot("jackal");
    ANIMALS_LION = registrar.addLoot("lion");
    ANIMALS_MONGOOSE = registrar.addLoot("mongoose");
    ANIMALS_MULE = registrar.addLoot("mule");
    ANIMALS_PANTHER = registrar.addLoot("panther");
    ANIMALS_QUAIL = registrar.addLoot("quail");
    ANIMALS_SABERTOOTH = registrar.addLoot("sabertooth");
    ANIMALS_TURKEY = registrar.addLoot("turkey");
    ANIMALS_WILDEBEEST = registrar.addLoot("wildebeest");
    ANIMALS_YAK = registrar.addLoot("yak");
    ANIMALS_ZEBU = registrar.addLoot("zebu");
  }
}
