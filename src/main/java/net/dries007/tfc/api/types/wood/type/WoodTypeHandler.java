package net.dries007.tfc.api.types.wood.type;

import static net.dries007.tfc.api.types.trees.TreeGenerators.*;
import static net.dries007.tfc.api.types.wood.type.WoodTypes.*;

public class WoodTypeHandler {

    public static void init() {
        ACACIA = new WoodType
                .Builder("acacia")
                .setColor(0x8B3929).setTemp(19f, 31f).setRain(30f, 210f)
                .setGenerator(GEN_ACACIA)
                .setHeight(12).setGrowthTime(11).setDensity(0.1f, 0.6f)
                .setBurnInfo(650f, 1000)
                .build();

        ASH = new WoodType
                .Builder("ash")
                .setColor(0xAE604E).setTemp(-6f, 12f).setRain(60f, 140f)
                .setGenerator(GEN_NORMAL)
                .setBurnInfo(696f, 1250)
                .build();

        ASPEN = new WoodType
                .Builder("aspen")
                .setColor(0x373727).setTemp(-10f, 16f).setRain(10f, 80f)
                .setGenerator(GEN_MEDIUM)
                .setRadius(1).setGrowthTime(8)
                .setBurnInfo(611f, 1000)
                .build();

        BIRCH = new WoodType
                .Builder("birch")
                .setColor(0x897658).setTemp(-15f, 7f).setRain(20f, 180f)
                .setGenerator(GEN_TALL)
                .setRadius(1)
                .setTannin()
                .setBurnInfo(652f, 1750)
                .build();

        BLACKWOOD = new WoodType
                .Builder("blackwood")
                .setColor(0x1A1A1A).setTemp(4f, 33f).setRain(0f, 120f)
                .setGenerator(GEN_MEDIUM)
                .setHeight(12).setGrowthTime(8)
                .setBurnInfo(720f, 1750)
                .build();

        CHESTNUT = new WoodType
                .Builder("chestnut")
                .setColor(0x642C1E).setTemp(11f, 35f).setRain(160f, 320f)
                .setGenerator(GEN_NORMAL)
                .setTannin()
                .setBurnInfo(651f, 1500)
                .build();

        DOUGLAS_FIR = new WoodType
                .Builder("douglas_fir")
                .setColor(0xD7BC8D).setTemp(-2f, 14f).setRain(280f, 480f)
                .setDominance(5.2f).setHeight(16).setDensity(0.25f, 2f)
                .setGenerator(GEN_TALL)
                .setBushes().setTannin()
                .setBurnInfo(707f, 1500)
                .build();

        HICKORY = new WoodType
                .Builder("hickory")
                .setColor(0x4E3418).setTemp(7f, 29f).setRain(80f, 250f)
                .setGrowthTime(10)
                .setGenerator(GEN_TALL)
                .setTannin()
                .setBurnInfo(762f, 2000)
                .build();

        MAPLE = new WoodType
                .Builder("maple")
                .setColor(0xC3782F).setTemp(3f, 20f).setRain(140f, 360f)
                .setDominance(6.3f).setRadius(1)
                .setBurnInfo(745f, 2000)
                .setGenerator(GEN_MEDIUM)
                .setTannin()
                .build();

        OAK = new WoodType
                .Builder("oak")
                .setColor(0xC29D62).setTemp(-8f, 12f).setRain(180f, 430f)
                .setBurnInfo(728f, 2250)
                .setHeight(16).setGrowthTime(10)
                .setGenerator(GEN_TALL)
                .setTannin()
                .build();

        PALM = new WoodType
                .Builder("palm")
                .setColor(0xB56F38).setTemp(16f, 35f).setRain(280f, 500f)
                .setBurnInfo(730f, 1250)
                .setDecayDist(6)
                .setGenerator(GEN_TROPICAL)
                .build();

        PINE = new WoodType
                .Builder("pine")
                .setColor(0xD1BD9A).setTemp(-15f, 7f).setRain(60f, 250f)
                .setBurnInfo(627f, 1250)
                .setRadius(1).setDensity(0.1f, 0.8f)
                .setGenerator(GEN_CONIFER)
                .setConifer()
                .build();

        ROSEWOOD = new WoodType
                .Builder("rosewood")
                .setColor(0x912222).setTemp(8f, 18f).setRain(10f, 190f)
                .setBurnInfo(640f, 1500)
                .setHeight(12).setGrowthTime(8)
                .setGenerator(GEN_MEDIUM)
                .build();

        SEQUOIA = new WoodType
                .Builder("sequoia")
                .setColor(0x965B3B).setTemp(-5f, 12f).setRain(250f, 420f)
                .setBurnInfo(612f, 1750)
                .setRadius(3).setHeight(24).setDecayDist(6).setGrowthTime(18).setDensity(0.4f, 0.9f)
                .setGenerator(GEN_SEQUOIA)
                .setConifer().setBushes().setTannin()
                .build();

        SPRUCE = new WoodType
                .Builder("spruce")
                .setColor(0xBF806F).setTemp(-11f, 6f).setRain(120f, 380f)
                .setBurnInfo(608f, 1500)
                .setRadius(1).setDensity(0.1f, 0.8f)
                .setGenerator(GEN_CONIFER)
                .setConifer()
                .build();

        SYCAMORE = new WoodType
                .Builder("sycamore")
                .setColor(0xDCA448).setTemp(17f, 33f).setRain(120f, 290f)
                .setBurnInfo(653f, 1750)
                .setGrowthTime(8).setDensity(0.25f, 2f)
                .setGenerator(GEN_MEDIUM)
                .setBushes()
                .build();

        WHITE_CEDAR = new WoodType
                .Builder("white_cedar")
                .setColor(0xD4D4D4).setTemp(-8f, 17f).setRain(10f, 240f)
                .setBurnInfo(625f, 1500)
                .setHeight(16)
                .setGenerator(GEN_TALL)
                .build();

        WILLOW = new WoodType
                .Builder("willow")
                .setColor(0x3A430B).setTemp(15f, 32f).setRain(230f, 400f)
                .setBurnInfo(603f, 1000)
                .setRadius(1).setGrowthTime(11).setDensity(0.7f, 2f)
                .setGenerator(GEN_WILLOW)
                .setBushes()
                .build();

        KAPOK = new WoodType
                .Builder("kapok")
                .setColor(0xAD879F).setTemp(15f, 35f).setRain(210f, 500f)
                .setBurnInfo(645f, 1000)
                .setDominance(8.5f).setRadius(3).setHeight(24).setDecayDist(6)
                .setGrowthTime(18).setDensity(0.6f, 2f)
                .setGenerator(GEN_ACACIA)
                .setBushes()
                .build();
    }
}
