package su.terrafirmagreg.modules.device;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.cleanroommc.configanytime.ConfigAnytime;


import net.dries007.tfc.util.Alloy;

import static su.terrafirmagreg.data.Constants.MOD_ID;
import static su.terrafirmagreg.data.Constants.MOD_NAME;

@Config(modid = MOD_ID, name = MOD_NAME + "/" + "Device")
public class ConfigDevice {

    @Config.Name("Block")
    @Config.Comment("Block settings")
    public static BlocksCategory BLOCKS = new BlocksCategory();

    @Config.Name("Item")
    @Config.Comment("Item settings")
    public static Items ITEMS = new Items();

    @Config.Name("Misc")
    @Config.Comment("Misc settings")
    public static MiscCategory MISC = new MiscCategory();

    public static class BlocksCategory {

        @Config.Comment("Bellows")
        public Bellows BELLOWS = new Bellows();

        @Config.Comment("Crucible")
        public Crucible CRUCIBLE = new Crucible();

        @Config.Comment("Bloomery")
        public Bloomery BLOOMERY = new Bloomery();

        @Config.Comment("Blast Furnace")
        public BlastFurnace BLAST_FURNACE = new BlastFurnace();

        @Config.Comment("Charcoal Pit")
        public CharcoalPit CHARCOAL_PIT = new CharcoalPit();

        @Config.Comment("Charcoal Forge")
        public CharcoalForge CHARCOAL_FORGE = new CharcoalForge();

        @Config.Comment("Fire Pit")
        public FirePit FIRE_PIT = new FirePit();

        @Config.Comment("Pit Kiln")
        public PitKiln PIT_KILN = new PitKiln();

        public static final class Bellows {

            @Config.Comment(
                    "The max number of air ticks, devices get a temperature bonus up to this amount. (1000 = 1 in game hour = 50 seconds), default is 600 ticks.")
            @Config.RangeInt(min = 0)
            public int maxTicks = 600;

            @Config.Comment("Amount of air ticks given to the connected devices. (1000 = 1 in game hour = 50 seconds), default is 200 ticks.")
            @Config.RangeInt(min = 0)
            public int ticks = 200;
        }

        public static final class Crucible {

            @Config.Comment("How much metal (units / mB) can a crucible hold?")
            @Config.RangeInt(min = 100, max = Alloy.SAFE_MAX_ALLOY)
            public int tank = 3_000;

            @Config.Comment("Let crucibles accept pouring metal (from small vessels / molds) from all 9 input slots at the same time.")
            public boolean enableAllSlots = false;

            @Config.Comment("How fast should crucibles accept fluids from molds / small vessel?")
            @Config.RangeInt(min = 1)
            public int pouringSpeed = 1;
        }

        public static final class Bloomery {

            @Config.Comment("Number of ticks required for a bloomery to complete. (1000 = 1 in game hour = 50 seconds), default is 15 hours.")
            @Config.RangeInt(min = 20)
            public int ticks = 15000;
        }

        public static final class BlastFurnace {

            @Config.Comment({ "How fast the blast furnace consume fuels (compared to the charcoal forge).",
                    "Example: Charcoal (without bellows) lasts for 1800 ticks in forge while 1800 / 4 = 450 ticks in blast furnace." })
            @Config.RangeDouble(min = 0.1D)
            public double consumption = 4;
        }

        public static final class CharcoalPit {

            @Config.Comment("Number of ticks required for charcoal pit to complete. (1000 = 1 in game hour = 50 seconds), default is 18 hours.")
            public int ticks = 18_000;

            @Config.Comment("Can charcoal pits take glass (or stained glass) as a valid cover block?")
            public boolean canAcceptGlass = true;
        }

        public static final class CharcoalForge {

            @Config.Comment({ "Number of burning ticks that is removed when the charcoal forge is on rain (random ticks).",
                    "This effectively makes the charcoal forge consumes more fuel when it is raining above it." })
            @Config.RangeInt(min = 0)
            public int rainTicks = 600;
        }

        public static final class FirePit {

            @Config.Comment(
                    "Number of ticks required for a cooking pot on a fire pit to boil before turning into soup, per serving. (1000 = 1 in game hour = 50 seconds). Default is 1 hour.")
            @Config.RangeInt(min = 20)
            public int ticks = 1000;

            @Config.Comment({ "Number of burning ticks that is removed when the fire pit is on rain (random ticks).",
                    "This effectively makes the fire pit consumes more fuel when it is raining above it." })
            @Config.RangeInt(min = 0)
            public int rainTicks = 1000;
        }

        public static final class PitKiln {

            @Config.Comment("Number of ticks required for a pit kiln to burn out. (1000 = 1 in game hour = 50 seconds), default is 8 hours.")
            @Config.RangeInt(min = 20)
            public int ticks = 8000;
        }
    }

    public static class Items {

        @Config.Comment("Water Flasks")
        public WaterFlasks WATER_FLASKS = new WaterFlasks();

        public static final class WaterFlasks {

            @Config.Comment("Liquid Capacity of Leather Flask (500 = 1/2 bucket = 5 drinks or 2 water bars)")
            @Config.RangeInt(min = 100)
            public int leatherCap = 500;

            @Config.Comment("Liquid Capacity of Iron Flask (1000 = 1 bucket = 10 drinks or 4 water bars)")
            @Config.RangeInt(min = 100)
            public int ironCap = 2000;

            @Config.Comment("Damage Capability of Flasks are Capacity/(this value) 0 = MAXINT uses")
            @Config.RangeInt(min = 0)
            public int damageFactor = 5;

        }

    }

    public static class MiscCategory {

    }

    static {
        ConfigAnytime.register(ConfigDevice.class);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class EventHandler {

        @SubscribeEvent
        public static void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(MOD_ID)) {
                ModuleDevice.LOGGER.warn("Config changed");
                ConfigManager.sync(MOD_ID, Config.Type.INSTANCE);
            }
        }
    }

}
