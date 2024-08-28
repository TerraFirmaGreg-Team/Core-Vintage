package su.terrafirmagreg.modules.core;

import su.terrafirmagreg.modules.core.capabilities.temperature.ProviderTemperature;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import net.dries007.tfc.util.config.OreTooltipMode;

import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "core")
public class ConfigCore {

    @Config.Name("Blocks")
    @Config.Comment("Block settings")
    public static final BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Items")
    @Config.Comment("Items settings")
    public static final ItemsCategory ITEMS = new ItemsCategory();

    @Config.Name("Entities")
    @Config.Comment("Entities settings")
    public static final EntitiesCategory ENTITY = new EntitiesCategory();

    @Config.Name("Misc")
    @Config.Comment("Misc settings")
    public static final MiscCategory MISC = new MiscCategory();

    public static final class BlocksCategory {

        @Config.Comment("Puddle")
        public final Puddle PUDDLE = new Puddle();

        public static final class Puddle {

            @Config.Name("Puddle Rate")
            @Config.Comment({
                    "The game will pick a random block every tick for every active chunk",
                    "Then it will check if a puddle can be placed there",
                    "Then it generates a random number between 0-99",
                    "And if that number is less than this puddle rate number, it puts a puddle",
                    "That means any value over 99 will flood your world with puddles"
            })
            @Config.RangeInt(min = 0, max = 99)
            public int puddleRate = 15;

            @Config.Name("Can Use Glass Bottle")
            @Config.Comment({ "Toggles filling glass bottles with puddle water" })
            public boolean canUseGlassBottle = true;

        }

    }

    public static final class ItemsCategory {

        @Config.Comment("Chance for the fire starter to be successful. Default = 0.5")
        @Config.RangeDouble(min = 0d, max = 1d)
        public double fireStarterChance = 0.5;

    }

    public static final class EntitiesCategory {

        @Config.Comment("Player settings")
        public final Player PLAYER = new Player();

        public static final class Player {

            @Config.RequiresMcRestart
            @Config.Comment("The hunger value with which a player respawns. Default = 100")
            @Config.RangeInt(min = 0, max = 100)
            public int respawnHungerLevel = 100;

            @Config.RequiresMcRestart
            @Config.Comment("The thirst value with which a player respawns. Default = 100")
            @Config.RangeInt(min = 0, max = 100)
            public int respawnThirstLevel = 100;
        }

    }

    public static final class MiscCategory {

        public final Weight WEIGHT = new Weight();
        public final Heat HEAT = new Heat();
        public final Temperature TEMPERATURE = new Temperature();
        public final Damage DAMAGE = new Damage();
        public final Debug DEBUG = new Debug();

        public static final class Weight {

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Very Heavy items. I wouldn't change this one. Default = 1")
            public int veryHeavy = 1;

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Heavy items. Default = 4")
            public int heavy = 4;

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Medium items. Default = 16")
            public int medium = 16;

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Light items. Default = 32")
            public int light = 32;

            @Config.RequiresMcRestart
            @Config.Comment("Stack size of Very Light items. Default = 64")
            public int veryLight = 64;
        }

        public static final class Heat {

            @Config.Comment("Ore tooltip info mode.")
            public OreTooltipMode oreTooltipMode = OreTooltipMode.ALL_INFO;

            @Config.Comment("Modifier for how quickly items will gain or lose heat. Smaller number = slower temperature changes.")
            @Config.RangeDouble(min = 0, max = 10)
            public double globalModifier = 0.5;

            @Config.Comment(
                    "Modifier for how quickly devices (i.e. charcoal forge, fire pit) will gain or lose heat. Smaller number = slower temperature changes.")
            @Config.RangeDouble(min = 0, max = 10)
            public double heatingModifier = 1;

            @Config.Comment("Can heatable items be cooled down in the world? Such as putting it in a pool of water or on top of some snow?")
            public boolean coolHeatablesInWorld = true;

            @Config.Comment("If heatable items can be cooled down in world, after how many ticks should the item attempt to be cooled down?")
            @Config.RangeInt(min = 1, max = 5999)
            public int ticksBeforeAttemptToCool = 10;
        }

        public static final class Temperature {

            @Config.Comment("If true, temperature is displayed in Celsius instead of Farhenheit.")
            public boolean celsius = true;

            @Config.Comment("If true, you will get extra details about your temperature when sneaking, when false they are always visible.")
            public boolean sneakyDetails = true;

            @Config.Comment("How quickly temperature rises and decreases. Default = 1.0")
            public float temperatureMultiplier = 1.0f;

            @Config.Comment("How fast does temperature change when it's going towards the average. Default = 5")
            public float positiveModifier = 5f;

            @Config.Comment({ "How fast does temperature change when it's going away from the average. " +
                    "If you think you are giving yourself a challenge by increasing this number, think twice. " +
                    "It makes it so that you have to warm yourself up every so often. Default = 1" })
            public float negativeModifier = 1f;

            @Config.Comment({ "How many ticks between modifier calculations. " +
                    "Too high values help performance but behave weirdly. " +
                    "20 = 1 second means modifiers are checked every second. " +
                    "Also affects the packet sending interval. Default = 20" })
            public int tickInterval = 20;

            @Config.Comment("How potent are multipliers with more than one instance. (Eg. 2 fire pits nearby means they have 2 * this effectiveness). Default = 0.7")
            public float diminishedModifierMultiplier = 0.7f;

            @Config.Comment("If true, you will start taking damage when below freezing or above burning temperatures. Default = true")
            public boolean takeDamage = true;

            @Config.Comment("If true, you will start losing hunger when below cold temperatures and losing thirst when above hot temperatures. Default = true")
            public boolean loseHungerThirst = true;

            @Config.Comment("How many modifiers of the same type until they stop adding together. Default = 4")
            public int modifierCap = 4;

            @Config.Comment("If true, temperate areas won't be as mild. Default = true")
            public boolean harsherTemperateAreas = true;

            @Config.Comment({ "If harsherTemperateAreas is true, environmental temperatures going away from the average are multiplied by this number. " +
                    "(The less temperate an area is, the less the modifier affects it). Default = 1.2 " })
            public float harsherMultiplier = 1.20f;

            @Config.Comment({ "The temperature at which you are at equilibrium. " +
                    "It's advisable to not change this by a lot since the entire ecosystem revolves around this. Default = 15" })
            public float averageTemperature = 15f;

            @Config.Comment({ "The temperature at which your screen starts heating. " +
                    "It's advisable to not change this by a lot since the entire ecosystem revolves around this. Default = 20" })
            public float hotTemperature = 20f;

            @Config.Comment({ "The temperature at which your screen starts freezing. " +
                    "It's advisable to not change this by a lot since the entire ecosystem revolves around this. Default = 10" })
            public float coldTemperature = 10f;

            @Config.Comment({ "The temperature at which you start burning and taking damage. " +
                    "It's advisable to not change this by a lot since the entire ecosystem revolves around this. Default = 25" })
            public float burningTemperature = 25f;

            @Config.Comment({ "The temperature at which you start freezing and taking damage. " +
                    "It's advisable to not change this by a lot since the entire ecosystem revolves around this. Default = 5" })
            public float freezingTemperature = 5f;

        }

        public static final class Damage {

            @Config.Comment("Damage Source Types that will default to Slashing damage.")
            public String[] slashingSources = new String[] {

            };

            @Config.Comment("Damage Source Types that will default to Piercing damage.")
            public String[] piercingSources = new String[] {
                    "arrow", "cactus", "thorns"
            };

            @Config.Comment("Damage Source Types that will default to Crushing damage.")
            public String[] crushingSources = new String[] {
                    "anvil", "falling_block"
            };

            @Config.Comment("Damage Source Entities that will default to Slashing damage.")
            public String[] slashingEntities = new String[] {
                    "minecraft:wither_skeleton", "minecraft:vex", "minecraft:vindication_illager",
                    "minecraft:zombie_pigman", "minecraft:wolf", "minecraft:polar_bear"
            };

            @Config.Comment("Damage Source Entities that will default to Piercing damage.")
            public String[] piercingEntities = new String[] {
                    "minecraft:stray", "minecraft:skeleton"
            };

            @Config.Comment("Damage Source Entities that will default to Crushing damage.")
            public String[] crushingEntities = new String[] {
                    "minecraft:husk", "minecraft:skeleton_horse", "minecraft:zombie_horse", "minecraft:spider", "minecraft:giant",
                    "minecraft:zombie", "minecraft:slime", "minecraft:cave_spider", "minecraft:silverfish", "minecraft:villager_golem", "minecraft:zombie_villager"
            };
        }

        public static final class Debug {

            @Config.Name("Debug Mode")
            @Config.Comment("When enabled, prints debug values to console. Activates some extra wand features. Enables extra item tooltips.")
            public boolean enable = true;

            @Config.Comment("Debug pathfinding")
            @Config.RequiresWorldRestart
            public boolean debugCreatePath = false;

            @Config.Comment({ "Debug worldgen (the danger part) " +
                    "This will glass maps at max world height to help debug world gen. " +
                    "THIS WILL MESS UP YOUR WORLD!" })
            @Config.RequiresWorldRestart
            public boolean debugWorldGenDanger = false;

            @Config.Comment("Debug worldgen (safe part) " +
                    "This will output map images of world gen steps and print some debug info. " +
                    "This is safe to use.")
            @Config.RequiresWorldRestart
            public boolean debugWorldGenSafe = false;
        }
    }

    static {
        ConfigAnytime.register(ConfigCore.class);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleCore.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);

                ProviderTemperature.AVERAGE = ConfigCore.MISC.TEMPERATURE.averageTemperature;
                ProviderTemperature.HOT_THRESHOLD = ConfigCore.MISC.TEMPERATURE.hotTemperature;
                ProviderTemperature.COOL_THRESHOLD = ConfigCore.MISC.TEMPERATURE.coldTemperature;
                ProviderTemperature.BURN_THRESHOLD = ConfigCore.MISC.TEMPERATURE.burningTemperature;
                ProviderTemperature.FREEZE_THRESHOLD = ConfigCore.MISC.TEMPERATURE.freezingTemperature;
            }
        }
    }

}
