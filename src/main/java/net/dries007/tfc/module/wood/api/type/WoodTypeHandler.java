package net.dries007.tfc.module.wood.api.type;

public class WoodTypeHandler {

    public static void init() {
        WoodTypes.ACACIA = new WoodType.Builder("acacia")
                .setColor(0x8B3929)
                .setBurnInfo(650f, 1000)
                .build();

        WoodTypes.ASH = new WoodType.Builder("ash")
                .setColor(0xAE604E)
                .setBurnInfo(696f, 1250)
                .build();

        WoodTypes.ASPEN = new WoodType.Builder("aspen")
                .setColor(0x373727)
                .setBurnInfo(611f, 1000)
                .build();

        WoodTypes.BIRCH = new WoodType.Builder("birch")
                .setColor(0x897658)
                .setBurnInfo(652f, 1750)
                .setTannin()
                .build();

        WoodTypes.BLACKWOOD = new WoodType.Builder("blackwood")
                .setColor(0x1A1A1A)
                .setBurnInfo(720f, 1750)
                .build();

        WoodTypes.CHESTNUT = new WoodType.Builder("chestnut")
                .setColor(0x642C1E)
                .setBurnInfo(651f, 1500)
                .setTannin()
                .build();

        WoodTypes.DOUGLAS_FIR = new WoodType.Builder("douglas_fir")
                .setColor(0xD7BC8D)
                .setBurnInfo(707f, 1500)
                .setTannin()
                .build();

        WoodTypes.HICKORY = new WoodType.Builder("hickory")
                .setColor(0x4E3418)
                .setBurnInfo(762f, 2000)
                .setTannin()
                .build();

        WoodTypes.MAPLE = new WoodType.Builder("maple")
                .setColor(0xC3782F)
                .setBurnInfo(745f, 2000)
                .setTannin()
                .build();

        WoodTypes.OAK = new WoodType.Builder("oak")
                .setColor(0xC29D62)
                .setBurnInfo(728f, 2250)
                .setTannin()
                .build();

        WoodTypes.PALM = new WoodType.Builder("palm")
                .setColor(0xB56F38)
                .setBurnInfo(730f, 1250)
                .build();

        WoodTypes.PINE = new WoodType.Builder("pine")
                .setColor(0xD1BD9A)
                .setBurnInfo(627f, 1250)
                .build();

        WoodTypes.ROSEWOOD = new WoodType.Builder("rosewood")
                .setColor(0x912222)
                .setBurnInfo(640f, 1500)
                .build();

        WoodTypes.SEQUOIA = new WoodType.Builder("sequoia")
                .setColor(0x965B3B)
                .setBurnInfo(612f, 1750)
                .setTannin()
                .build();

        WoodTypes.SPRUCE = new WoodType.Builder("spruce")
                .setColor(0xBF806F)
                .setBurnInfo(608f, 1500)
                .build();

        WoodTypes.SYCAMORE = new WoodType.Builder("sycamore")
                .setColor(0xDCA448)
                .setBurnInfo(653f, 1750)
                .build();

        WoodTypes.WHITE_CEDAR = new WoodType.Builder("white_cedar")
                .setColor(0xD4D4D4)
                .setBurnInfo(625f, 1500)
                .build();

        WoodTypes.WILLOW = new WoodType.Builder("willow")
                .setColor(0x3A430B)
                .setBurnInfo(603f, 1000)
                .build();

        WoodTypes.KAPOK = new WoodType.Builder("kapok")
                .setColor(0xAD879F)
                .setBurnInfo(645f, 1000)
                .build();


//        BANANA = new WoodType
//                .Builder("banana")
//                .setColor(0xBB9C7D)
//                .setBurnInfo(720f, 1750)
//                .build();
//
//
//        CHERRY = new WoodType
//                .Builder("cherry")
//                .setColor(0xDC5E2C)
//                .setBurnInfo(720f, 1750)
//                .build();
//
//
//        GREEN_APPLE = new WoodType
//                .Builder("green_apple")
//                .setColor(0xCFC498)
//                .setBurnInfo(720f, 1750)
//                .build();
//
//
//        LEMON = new WoodType
//                .Builder("lemon")
//                .setColor(0xE8DD8C)
//                .setBurnInfo(720f, 1750)
//                .build();
//
//
//        OLIVE = new WoodType
//                .Builder("olive")
//                .setColor(0x97492B)
//                .setBurnInfo(720f, 1750)
//                .build();
//
//
//        ORANGE = new WoodType
//                .Builder("orange")
//                .setColor(0xC18C52)
//                .setBurnInfo(720f, 1750)
//                .build();
//
//
//        PEACH = new WoodType
//                .Builder("peach")
//                .setColor(0x9A5B37)
//                .setBurnInfo(720f, 1750)
//                .build();
//
//
//        PLUM = new WoodType
//                .Builder("plum")
//                .setColor(0x9F573B)
//                .setBurnInfo(720f, 1750)
//                .build();
//
//
//        RED_APPLE = new WoodType
//                .Builder("red_apple")
//                .setColor(0x6E150E)
//                .setBurnInfo(720f, 1750)
//                .build();

        // TODO доделать
//        HEVEA = new WoodType
//                .Builder("hevea")
//                .setColor(0x8B3929)
//                .setBurnInfo(650f, 1000)
//                .build();

    }
}
