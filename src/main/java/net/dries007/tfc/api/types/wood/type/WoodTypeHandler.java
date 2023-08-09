package net.dries007.tfc.api.types.wood.type;

import static net.dries007.tfc.api.types.wood.type.WoodTypes.*;
import static net.dries007.tfc.types.DefaultTrees.*;

public class WoodTypeHandler {

    public static void init() {
        ACACIA = new WoodType
                .Builder("acacia", 0x8B3929, 19f, 31f, 30f, 210f)
                .setGenerator(GEN_ACACIA)
                .setHeight(12)
                .setGrowthTime(11)
                .setDensity(0.1f, 0.6f)
                .setBurnInfo(650f, 1000)
                .build();

        ASH = new WoodType
                .Builder("ash", 0xAE604E, -6f, 12f, 60f, 140f)
                .setGenerator(GEN_NORMAL)
                .setBurnInfo(696f, 1250)
                .build();

        ASPEN = new WoodType
                .Builder("aspen", 0x373727, -10f, 16f, 10f, 80f)
                .setGenerator(GEN_MEDIUM)
                .setRadius(1)
                .setGrowthTime(8)
                .setBurnInfo(611f, 1000)
                .build();

        BIRCH = new WoodType
                .Builder("birch", 0x897658, -15f, 7f, 20f, 180f)
                .setGenerator(GEN_TALL)
                .setRadius(1)
                .setTannin()
                .setBurnInfo(652f, 1750)
                .build();

        BLACKWOOD = new WoodType
                .Builder("blackwood", 0x1A1A1A, 4f, 33f, 0f, 120f)
                .setGenerator(GEN_MEDIUM)
                .setHeight(12)
                .setGrowthTime(8)
                .setBurnInfo(720f, 1750)
                .build();

        CHESTNUT = new WoodType
                .Builder("chestnut", 0x642C1E, 11f, 35f, 160f, 320f)
                .setGenerator(GEN_NORMAL)
                .setTannin()
                .setBurnInfo(651f, 1500)
                .build();

        DOUGLAS_FIR = new WoodType
                .Builder("douglas_fir", 0xD7BC8D, -2f, 14f, 280f, 480f)
                .setGenerator(GEN_TALL)
                .setDominance(5.2f)
                .setHeight(16)
                .setBushes()
                .setTannin()
                .setDensity(0.25f, 2f)
                .setBurnInfo(707f, 1500)
                .build();

        HICKORY = new WoodType
                .Builder("hickory", 0x4E3418, 7f, 29f,  80f, 250f)
                .setGenerator(GEN_TALL)
                .setGrowthTime(10)
                .setTannin()
                .setBurnInfo(762f, 2000)
                .build();

        MAPLE = new WoodType
                .Builder("maple", 0xC3782F, 3f, 20f, 140f, 360f)
                .setGenerator(GEN_MEDIUM)
                .setDominance(6.3f)
                .setRadius(1)
                .setTannin()
                .setBurnInfo(745f, 2000)
                .build();

        OAK = new WoodType
                .Builder("oak", 0xC29D62, -8f, 12f, 180f, 430f)
                .setGenerator(GEN_TALL)
                .setHeight(16)
                .setGrowthTime(10)
                .setTannin()
                .setBurnInfo(728f, 2250)
                .build();

        PALM = new WoodType
                .Builder("palm", 0xB56F38, 16f, 35f, 280f, 500f)
                .setGenerator(GEN_TROPICAL)
                .setDecayDist(6)
                .setBurnInfo(730f, 1250)
                .build();

        PINE = new WoodType
                .Builder("pine", 0xD1BD9A, -15f, 7f, 60f, 250f)
                .setGenerator(GEN_CONIFER)
                .setRadius(1)
                .setConifer()
                .setDensity(0.1f, 0.8f)
                .setBurnInfo(627f, 1250)
                .build();

        ROSEWOOD = new WoodType
                .Builder("rosewood", 0x912222, 8f, 18f, 10f, 190f)
                .setGenerator(GEN_MEDIUM)
                .setHeight(12)
                .setGrowthTime(8)
                .setBurnInfo(640f, 1500)
                .build();

        SEQUOIA = new WoodType
                .Builder("sequoia", 0x965B3B, -5f, 12f, 250f, 420f)
                .setGenerator(GEN_SEQUOIA)
                .setRadius(3)
                .setHeight(24)
                .setDecayDist(6)
                .setGrowthTime(18)
                .setConifer()
                .setBushes()
                .setTannin()
                .setDensity(0.4f, 0.9f)
                .setBurnInfo(612f, 1750)
                .build();

        SPRUCE = new WoodType
                .Builder("spruce", 0xBF806F, -11f, 6f, 120f, 380f)
                .setGenerator(GEN_CONIFER)
                .setRadius(1)
                .setConifer()
                .setDensity(0.1f, 0.8f)
                .setBurnInfo(608f, 1500)
                .build();

        SYCAMORE = new WoodType
                .Builder("sycamore", 0xDCA448, 17f, 33f, 120f, 290f)
                .setGenerator(GEN_MEDIUM)
                .setGrowthTime(8)
                .setBushes()
                .setDensity(0.25f, 2f)
                .setBurnInfo(653f, 1750)
                .build();

        WHITE_CEDAR = new WoodType
                .Builder("white_cedar", 0xD4D4D4, -8f, 17f, 10f, 240f)
                .setGenerator(GEN_TALL)
                .setHeight(16)
                .setBurnInfo(625f, 1500)
                .build();

        WILLOW = new WoodType
                .Builder("willow", 0x3A430B, 15f, 32f, 230f, 400f)
                .setGenerator(GEN_WILLOW)
                .setRadius(1)
                .setGrowthTime(11)
                .setBushes()
                .setDensity(0.7f, 2f)
                .setBurnInfo(603f, 1000)
                .build();

        KAPOK = new WoodType
                .Builder("kapok", 0xAD879F, 15f, 35f, 210f, 500f)
                .setGenerator(GEN_ACACIA)
                .setDominance(8.5f)
                .setRadius(3)
                .setHeight(24)
                .setDecayDist(6)
                .setGrowthTime(18)
                .setBushes()
                .setDensity(0.6f, 2f)
                .setBurnInfo(645f, 1000)
                .build();
    }
}
