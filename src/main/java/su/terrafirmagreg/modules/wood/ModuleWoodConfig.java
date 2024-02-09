package su.terrafirmagreg.modules.wood;


import net.minecraftforge.common.config.Config;

import static su.terrafirmagreg.Tags.MOD_ID;
import static su.terrafirmagreg.Tags.MOD_NAME;


@Config(modid = MOD_ID, name = MOD_NAME + "/ModuleWood")
public class ModuleWoodConfig {

    @Config.RequiresMcRestart
    @Config.RangeDouble(min = -1.0D, max = 0.0D)
    public static double SPEED_MODIFIER = -0.65D;

    public static SupplyCart SUPPLY_CART = new SupplyCart();
    public static Plow PLOW = new Plow();
    public static AnimalCart ANIMAL_CART = new AnimalCart();

    public static class SupplyCart {

        public String[] canPull = {
                "minecraft:donkey",
                "minecraft:horse",
                "minecraft:mule",
                "minecraft:pig",
                "minecraft:player",
                "tfc:cameltfc",
                "tfc:donkeytfc",
                "tfc:horsetfc",
                "tfc:muletfc"
        };
    }

    public static class Plow {

        public String[] canPull = {
                "minecraft:donkey",
                "minecraft:horse",
                "minecraft:mule",
                "minecraft:pig",
                "minecraft:player",
                "tfc:cameltfc",
                "tfc:donkeytfc",
                "tfc:horsetfc",
                "tfc:muletfc"
        };
    }

    public static class AnimalCart {

        public String[] canPull = {
                "minecraft:donkey",
                "minecraft:horse",
                "minecraft:mule",
                "minecraft:pig",
                "minecraft:player",
                "tfc:cameltfc",
                "tfc:donkeytfc",
                "tfc:horsetfc",
                "tfc:muletfc"
        };
    }
}
