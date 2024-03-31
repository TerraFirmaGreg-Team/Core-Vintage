package su.terrafirmagreg.modules.animal;

import com.cleanroommc.configanytime.ConfigAnytime;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "Animal")
public class ModuleAnimalConfig {

	@Config.Name("Blocks")
	@Config.Comment("Block settings")
	@Config.LangKey("config." + MOD_ID + ".animal.blocks")
	public static final BlocksCategory BLOCKS = new BlocksCategory();

	public static final class BlocksCategory {}

	@Config.Name("Items")
	@Config.Comment("Items settings")
	@Config.LangKey("config." + MOD_ID + ".animal.items")
	public static final ItemsCategory ITEMS = new ItemsCategory();

	public static final class ItemsCategory {}

	@Config.Name("Entities")
	@Config.Comment("Entities settings")
	@Config.LangKey("config." + MOD_ID + ".animal.entities")
	public static final EntitiesCategory ENTITIES = new EntitiesCategory();

	public static final class EntitiesCategory {
		@Config.Comment("Alpaca")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.alpaca")
		public final Alpaca ALPACA = new Alpaca();

		@Config.Comment("Sheep")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.sheep")
		public final Sheep SHEEP = new Sheep();

		@Config.Comment("Cow")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.cow")
		public final Cow COW = new Cow();

		@Config.Comment("Goat")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.goat")
		public final Goat GOAT = new Goat();

		@Config.Comment("Chicken")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.chicken")
		public final Chicken CHICKEN = new Chicken();

		@Config.Comment("Duck")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.duck")
		public final Duck DUCK = new Duck();

		@Config.Comment("Pig")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.pig")
		public final Pig PIG = new Pig();

		@Config.Comment("Camel")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.camel")
		public final Camel CAMEL = new Camel();

		@Config.Comment("Llama")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.llama")
		public final Llama LLAMA = new Llama();

		@Config.Comment("Horse")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.horse")
		public final Horse HORSE = new Horse();

		@Config.Comment("Donkey")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.donkey")
		public final Donkey DONKEY = new Donkey();

		@Config.Comment("Mule")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.mule")
		public final Mule MULE = new Mule();

		@Config.Comment("Ocelot")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.ocelot")
		public final Ocelot OCELOT = new Ocelot();

		@Config.Comment("Wolf")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.wolf")
		public final Wolf WOLF = new Wolf();

		@Config.Comment("GrizzlyBear")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.grizzly_bear")
		public final GrizzlyBear GRIZZLY_BEAR = new GrizzlyBear();

		@Config.Comment("Polar Bear")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.polar_bear")
		public final PolarBear POLAR_BEAR = new PolarBear();

		@Config.Comment("Lion")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.lion")
		public final Lion LION = new Lion();

		@Config.Comment("Panther")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.panther")
		public final Panther PANTHER = new Panther();

		@Config.Comment("Saber Tooth")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.saber_tooth")
		public final SaberTooth SABER_TOOTH = new SaberTooth();

		@Config.Comment("Hyena")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.hyena")
		public final Hyena HYENA = new Hyena();

		@Config.Comment("Deer")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.deer")
		public final Deer DEER = new Deer();

		@Config.Comment("Parrot")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.parrot")
		public final Parrot PARROT = new Parrot();

		@Config.Comment("Pheasant")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.pheasant")
		public final Pheasant PHEASANT = new Pheasant();

		@Config.Comment("Rabbit")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.rabbit")
		public final Rabbit RABBIT = new Rabbit();

		@Config.Comment("DireWolf")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.direwolf")
		public final DireWolf DIREWOLF = new DireWolf();

		@Config.Comment("Hare")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.hare")
		public final Hare HARE = new Hare();

		@Config.Comment("Boar")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.boar")
		public final Boar BOAR = new Boar();

		@Config.Comment("Zebu")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.zebu")
		public final Zebu ZEBU = new Zebu();

		@Config.Comment("Gazelle")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.gazelle")
		public final Gazelle GAZELLE = new Gazelle();

		@Config.Comment("Wildebeest")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.wildebeest")
		public final Wildebeest WILDEBEEST = new Wildebeest();

		@Config.Comment("Quail")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.quail")
		public final Quail QUAIL = new Quail();

		@Config.Comment("Grouse")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.grouse")
		public final Grouse GROUSE = new Grouse();

		@Config.Comment("Mongoose")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.mongoose")
		public final Mongoose MONGOOSE = new Mongoose();

		@Config.Comment("Turkey")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.turkey")
		public final Turkey TURKEY = new Turkey();

		@Config.Comment("Jackal")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.jackal")
		public final Jackal JACKAL = new Jackal();

		@Config.Comment("MuskOx")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.muskox")
		public final MuskOx MUSKOX = new MuskOx();

		@Config.Comment("Yak")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.yak")
		public final Yak YAK = new Yak();

		@Config.Comment("Black Bear")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.black_bear")
		public final BlackBear BLACK_BEAR = new BlackBear();

		@Config.Comment("Cougar")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.cougar")
		public final Cougar COUGAR = new Cougar();

		@Config.Comment("Coyote")
		@Config.LangKey("config." + MOD_ID + ".animal.entities.coyote")
		public final Coyote COYOTE = new Coyote();

		public static final class Alpaca {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 98;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 392;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 36;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 1;

			@Config.Comment("How many ticks are needed for this animal grow back wool?")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.woolTicks")
			public int woolTicks = 120_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Sheep {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 64;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 256;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 28;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 2;

			@Config.Comment("How many ticks are needed for this animal grow back wool?")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.woolTicks")
			public int woolTicks = 168_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 240;
		}

		public static final class MuskOx {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 192;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 768;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 59;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 1;

			@Config.Comment("How many ticks are needed for this animal grow back wool?")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.woolTicks")
			public int woolTicks = 96_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Cow {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 192;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 768;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 58;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 1;

			@Config.Comment("How many ticks it is needed for this animal give milk?")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.milkTicks")
			public int milkTicks = 24_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 240;
		}

		public static final class Yak {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 180;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 720;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 62;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 1;

			@Config.Comment("How many ticks it is needed for this animal give milk?")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.milkTicks")
			public int milkTicks = 24_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Zebu {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 108;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 686;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 32;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 1;

			@Config.Comment("How many ticks it is needed for this animal give milk?.")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.milkTicks")
			public int milkTicks = 48_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Goat {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 96;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 420;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 28;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 2;

			@Config.Comment("How many ticks it is needed for this animal give milk?.")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.milkTicks")
			public int milkTicks = 72_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 240;
		}

		public static final class Chicken {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 24;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 92;

			@Config.Comment("How many days it is needed for this animal finish hatching?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.hatch")
			public int hatch = 8;

			@Config.Comment("How many ticks it is needed for this animal to lay eggs?")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.eggTicks")
			public int eggTicks = 30_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 240;
		}

		public static final class Grouse {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 26;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 98;

			@Config.Comment("How many days it is needed for this animal finish hatching?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.hatch")
			public int hatch = 10;

			@Config.Comment("How many ticks it is needed for this animal to lay eggs?")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.eggTicks")
			public int eggTicks = 30_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Quail {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 22;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 88;

			@Config.Comment("How many days it is needed for this animal finish hatching?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.hatch")
			public int hatch = 8;

			@Config.Comment("How many ticks it is needed for this animal to lay eggs?")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.eggTicks")
			public int eggTicks = 28_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 240;
		}

		public static final class Duck {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 32;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 140;

			@Config.Comment("How many days it is needed for this animal finish hatching?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.hatch")
			public int hatch = 12;

			@Config.Comment("How many ticks it is needed for this animal to lay eggs?")
			@Config.RangeInt(min = 1_000)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.eggTicks")
			public int eggTicks = 32_000;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 240;
		}

		public static final class Pig {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 80;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 320;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 19;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 10;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 240;
		}

		public static final class Camel {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 192;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 768;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 59;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 1;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Llama {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 160;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 640;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 55;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 1;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Horse {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 200;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 800;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 43;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 1;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Donkey {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 200;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 800;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 43;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 1;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Mule {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 200;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 800;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 0;
		}

		public static final class Parrot {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;
		}

		public static final class Ocelot {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 59;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 236;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 8;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 2;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:pheasanttfc", "tfc:chickentfc", "tfc:ducktfc", "tfc:rabbittfc"};
		}

		public static final class Wolf {
			@Config.Comment("How many days until this animal is a full grown adult?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.adulthood")
			public int adulthood = 70;

			@Config.Comment("How many days after becoming an adult until this animal is old? 0 = Disable")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.elder")
			public int elder = 280;

			@Config.Comment("How many days until this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.gestation")
			public int gestation = 10;

			@Config.Comment("How many babies are born when this animal gives birth?")
			@Config.RangeInt(min = 1)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.babies")
			public int babies = 2;

			@Config.Comment("Chance that old animal.entities will die at the start of a new day. 0 = Disable")
			@Config.RangeDouble(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.oldDeathChance")
			public double oldDeathChance = 0;

			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 250;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:sheeptfc", "tfc:rabbittfc", "tfc:haretfc"};
		}

		public static final class GrizzlyBear {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 150;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:deertfc", "tfc:haretfc", "tfc:rabbittfc"};
		}

		public static final class BlackBear {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 150;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:deertfc", "tfc:haretfc", "tfc:rabbittfc"};
		}

		public static final class PolarBear {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 120;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:deertfc", "tfc:haretfc", "tfc:rabbittfc"};
		}

		public static final class Lion {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 150;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:gazelletfc", "tfc:wildebeesttfc"};
		}

		public static final class SaberTooth {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 150;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:deertfc", "tfc:boartfc"};
		}

		public static final class DireWolf {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 150;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:horsetfc", "tfc:donkeytfc", "tfc:muletfc", "tfc:turkeytfc"};
		}

		public static final class Cougar {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 100;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:boartfc", "tfc:haretfc"};
		}

		public static final class Coyote {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 100;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:mongoosetfc", "tfc:haretfc"};
		}

		public static final class Panther {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 100;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:boartfc", "tfc:haretfc"};
		}

		public static final class Jackal {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 120;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:pheasanttfc", "tfc:rabbittfc", "tfc:haretfc"};
		}

		public static final class Hyena {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 100;

			@Config.Comment({"This controls which registered entities will be hunted by this animal (unless tamed), in priority order.",
					"You must specify by 'modid:entity'",
					"Invalid entries will be ignored."})
			@Config.LangKey("config." + MOD_ID + ".animal.entities.huntCreatures")
			public String[] huntCreatures = {"tfc:gazalletfc", "tfc:rabbittfc", "tfc:haretfc"};
		}

		public static final class Mongoose {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 120;
		}

		public static final class Deer {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 150;
		}

		public static final class Turkey {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 140;
		}

		public static final class Pheasant {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 120;
		}

		public static final class Rabbit {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 100;
		}

		public static final class Hare {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 100;
		}

		public static final class Boar {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 160;
		}

		public static final class Gazelle {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 150;
		}

		public static final class Wildebeest {
			@Config.Comment("How rare this animal should be, in 1 / N chunks, on valid biomes (this is used on chunk generation only)? 0 = Disable.")
			@Config.RangeInt(min = 0)
			@Config.LangKey("config." + MOD_ID + ".animal.entities.rarity")
			public int rarity = 150;
		}

	}

	@Config.Name("Misc")
	@Config.Comment("Miscellaneous")
	@Config.LangKey("config." + MOD_ID + ".animal.misc")
	public static final MiscCategory MISC = new MiscCategory();

	public static final class MiscCategory {

		@Config.Name("Search distance")
		@Config.Comment("The distance for animals to search for food")
		public double searchDistance = 10;

	}

	static {
		ConfigAnytime.register(ModuleAnimalConfig.class);
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
