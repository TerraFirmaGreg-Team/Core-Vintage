package su.terrafirmagreg.modules.animal;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import static su.terrafirmagreg.api.data.Constants.MOD_ID;
import static su.terrafirmagreg.api.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "Animal")
public class ConfigAnimal {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    public static BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Items settings")
    public static ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Entities")
    @Config.Comment("Entities settings")
    public static EntitiesCategory ENTITIES = new EntitiesCategory();

    @Config.Name("Misc")
    @Config.Comment("Miscellaneous")
    public static MiscCategory MISC = new MiscCategory();

    static {
        ConfigAnytime.register(ConfigAnimal.class);
    }

    public static class BlocksCategory {}

    public static class ItemsCategory {}

    public static class EntitiesCategory {

        @Config.Comment("Alpaca")
        public Alpaca ALPACA = new Alpaca();

        @Config.Comment("Sheep")
        public Sheep SHEEP = new Sheep();

        @Config.Comment("Cow")
        public Cow COW = new Cow();

        @Config.Comment("Goat")
        public Goat GOAT = new Goat();

        @Config.Comment("Chicken")
        public Chicken CHICKEN = new Chicken();

        @Config.Comment("Duck")
        public Duck DUCK = new Duck();

        @Config.Comment("Pig")
        public Pig PIG = new Pig();

        @Config.Comment("Camel")
        public Camel CAMEL = new Camel();

        @Config.Comment("Llama")
        public Llama LLAMA = new Llama();

        @Config.Comment("Horse")
        public Horse HORSE = new Horse();

        @Config.Comment("Donkey")
        public Donkey DONKEY = new Donkey();

        @Config.Comment("Mule")
        public Mule MULE = new Mule();

        @Config.Comment("Ocelot")
        public Ocelot OCELOT = new Ocelot();

        @Config.Comment("Wolf")
        public Wolf WOLF = new Wolf();

        @Config.Comment("GrizzlyBear")
        public GrizzlyBear GRIZZLY_BEAR = new GrizzlyBear();

        @Config.Comment("Polar Bear")
        public PolarBear POLAR_BEAR = new PolarBear();

        @Config.Comment("Lion")
        public Lion LION = new Lion();

        @Config.Comment("Panther")
        public Panther PANTHER = new Panther();

        @Config.Comment("Saber Tooth")
        public SaberTooth SABER_TOOTH = new SaberTooth();

        @Config.Comment("Hyena")
        public Hyena HYENA = new Hyena();

        @Config.Comment("Deer")
        public Deer DEER = new Deer();

        @Config.Comment("Parrot")
        public Parrot PARROT = new Parrot();

        @Config.Comment("Pheasant")
        public Pheasant PHEASANT = new Pheasant();

        @Config.Comment("Rabbit")
        public Rabbit RABBIT = new Rabbit();

        @Config.Comment("DireWolf")
        public DireWolf DIREWOLF = new DireWolf();

        @Config.Comment("Hare")
        public Hare HARE = new Hare();

        @Config.Comment("Boar")
        public Boar BOAR = new Boar();

        @Config.Comment("Zebu")
        public Zebu ZEBU = new Zebu();

        @Config.Comment("Gazelle")
        public Gazelle GAZELLE = new Gazelle();

        @Config.Comment("Wildebeest")
        public Wildebeest WILDEBEEST = new Wildebeest();

        @Config.Comment("Quail")
        public Quail QUAIL = new Quail();

        @Config.Comment("Grouse")
        public Grouse GROUSE = new Grouse();

        @Config.Comment("Mongoose")
        public Mongoose MONGOOSE = new Mongoose();

        @Config.Comment("Turkey")
        public Turkey TURKEY = new Turkey();

        @Config.Comment("Jackal")
        public Jackal JACKAL = new Jackal();

        @Config.Comment("MuskOx")
        public MuskOx MUSKOX = new MuskOx();

        @Config.Comment("Yak")
        public Yak YAK = new Yak();

        @Config.Comment("Black Bear")
        public BlackBear BLACK_BEAR = new BlackBear();

        @Config.Comment("Cougar")
        public Cougar COUGAR = new Cougar();

        @Config.Comment("Coyote")
        public Coyote COYOTE = new Coyote();

        public static final class Alpaca {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 98;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 392;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 36;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 1;

            @Config.Comment("How many ticks are needed for this animal grow back wool?")
            @Config.RangeInt(min = 1_000)
            public int woolTicks = 120_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Sheep {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 64;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 256;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 28;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 2;

            @Config.Comment("How many ticks are needed for this animal grow back wool?")
            @Config.RangeInt(min = 1_000)
            public int woolTicks = 168_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 240;
        }

        public static final class MuskOx {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 192;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 768;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 59;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 1;

            @Config.Comment("How many ticks are needed for this animal grow back wool?")
            @Config.RangeInt(min = 1_000)
            public int woolTicks = 96_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Cow {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 192;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 768;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 58;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 1;

            @Config.Comment("How many ticks it is needed for this animal give milk?")
            @Config.RangeInt(min = 1_000)
            public int milkTicks = 24_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 240;
        }

        public static final class Yak {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 180;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 720;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 62;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 1;

            @Config.Comment("How many ticks it is needed for this animal give milk?")
            @Config.RangeInt(min = 1_000)
            public int milkTicks = 24_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Zebu {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 108;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 686;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 32;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 1;

            @Config.Comment("How many ticks it is needed for this animal give milk?.")
            @Config.RangeInt(min = 1_000)
            public int milkTicks = 48_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Goat {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 96;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 420;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 28;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 2;

            @Config.Comment("How many ticks it is needed for this animal give milk?.")
            @Config.RangeInt(min = 1_000)
            public int milkTicks = 72_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 240;
        }

        public static final class Chicken {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 24;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 92;

            @Config.Comment("How many days it is needed for this animal finish hatching?")
            @Config.RangeInt(min = 1)
            public int hatch = 8;

            @Config.Comment("How many ticks it is needed for this animal to lay eggs?")
            @Config.RangeInt(min = 1_000)
            public int eggTicks = 30_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 240;
        }

        public static final class Grouse {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 26;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 98;

            @Config.Comment("How many days it is needed for this animal finish hatching?")
            @Config.RangeInt(min = 1)
            public int hatch = 10;

            @Config.Comment("How many ticks it is needed for this animal to lay eggs?")
            @Config.RangeInt(min = 1_000)
            public int eggTicks = 30_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Quail {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 22;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 88;

            @Config.Comment("How many days it is needed for this animal finish hatching?")
            @Config.RangeInt(min = 1)
            public int hatch = 8;

            @Config.Comment("How many ticks it is needed for this animal to lay eggs?")
            @Config.RangeInt(min = 1_000)
            public int eggTicks = 28_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 240;
        }

        public static final class Duck {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 32;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 140;

            @Config.Comment("How many days it is needed for this animal finish hatching?")
            @Config.RangeInt(min = 1)
            public int hatch = 12;

            @Config.Comment("How many ticks it is needed for this animal to lay eggs?")
            @Config.RangeInt(min = 1_000)
            public int eggTicks = 32_000;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 240;
        }

        public static final class Pig {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 80;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 320;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 19;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 10;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 240;
        }

        public static final class Camel {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 192;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 768;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 59;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 1;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Llama {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 160;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 640;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 55;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 1;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Horse {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 200;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 800;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 43;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 1;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Donkey {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 200;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 800;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 43;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 1;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Mule {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 200;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 800;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 0;
        }

        public static final class Parrot {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;
        }

        public static final class Ocelot {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 59;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 236;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 8;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 2;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:pheasanttfc", "tfc:chickentfc", "tfc:ducktfc", "tfc:rabbittfc" };
        }

        public static final class Wolf {

            @Config.Comment("How many days until this animal is a full grown adult?")
            @Config.RangeInt(min = 1)
            public int adulthood = 70;

            @Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
            @Config.RangeInt(min = 0)
            public int elder = 280;

            @Config.Comment("How many days until this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int gestation = 10;

            @Config.Comment("How many babies are born when this animal gives birth?")
            @Config.RangeInt(min = 1)
            public int babies = 2;

            @Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
            @Config.RangeDouble(min = 0)
            public double oldDeathChance = 0;

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 250;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:sheeptfc", "tfc:rabbittfc", "tfc:haretfc" };
        }

        public static final class GrizzlyBear {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:deertfc", "tfc:haretfc", "tfc:rabbittfc" };
        }

        public static final class BlackBear {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:deertfc", "tfc:haretfc", "tfc:rabbittfc" };
        }

        public static final class PolarBear {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 120;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:deertfc", "tfc:haretfc", "tfc:rabbittfc" };
        }

        public static final class Lion {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:gazelletfc", "tfc:wildebeesttfc" };
        }

        public static final class SaberTooth {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:deertfc", "tfc:boartfc" };
        }

        public static final class DireWolf {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 150;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:horsetfc", "tfc:donkeytfc", "tfc:muletfc", "tfc:turkeytfc" };
        }

        public static final class Cougar {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 100;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:boartfc", "tfc:haretfc" };
        }

        public static final class Coyote {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 100;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:mongoosetfc", "tfc:haretfc" };
        }

        public static final class Panther {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 100;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:boartfc", "tfc:haretfc" };
        }

        public static final class Jackal {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 120;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:pheasanttfc", "tfc:rabbittfc", "tfc:haretfc" };
        }

        public static final class Hyena {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 100;

            @Config.Comment({ "This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
                    "You must specify by 'modid:entity'",
                    "Invalid entries will be ignored." })
            public String[] huntCreatures = { "tfc:gazalletfc", "tfc:rabbittfc", "tfc:haretfc" };
        }

        public static final class Mongoose {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 120;
        }

        public static final class Deer {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 150;
        }

        public static final class Turkey {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 140;
        }

        public static final class Pheasant {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 120;
        }

        public static final class Rabbit {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 100;
        }

        public static final class Hare {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 100;
        }

        public static final class Boar {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 160;
        }

        public static final class Gazelle {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 150;
        }

        public static final class Wildebeest {

            @Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
            @Config.RangeInt(min = 0)
            public int rarity = 150;
        }

    }

    public static class MiscCategory {

        @Config.Name("Search distance")
        @Config.Comment("The distance for animals to search for food")
        public double searchDistance = 10;

    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleAnimal.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}
