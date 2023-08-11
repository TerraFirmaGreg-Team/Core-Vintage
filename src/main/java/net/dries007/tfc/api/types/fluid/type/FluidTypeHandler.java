package net.dries007.tfc.api.types.fluid.type;

import static net.dries007.tfc.api.types.fluid.type.FluidTypes.*;

public class FluidTypeHandler {


    public static void init() {

        // Варианты воды
        HOT_WATER = new FluidType
                .Builder("hot_water", 0xFF345FDA)
                .build();

        FRESH_WATER = new FluidType
                .Builder("fresh_water", 0xFF296ACD)
                .build();

        SALT_WATER = new FluidType
                .Builder("salt_water", 0xFF1F5099)
                .build();

        // Алкоголь
        RUM = new FluidType
                .Builder("rum", 0xFF6E0123)
                .build();

        BEER = new FluidType
                .Builder("beer", 0xFFC39E37)
                .build();

        WHISKEY = new FluidType
                .Builder("whiskey", 0xFF583719)
                .build();

        RYE_WHISKEY = new FluidType
                .Builder("rye_whiskey", 0xFFC77D51)
                .build();

        CORN_WHISKEY = new FluidType
                .Builder("corn_whiskey", 0xFFD9C7B7)
                .build();

        SAKE = new FluidType
                .Builder("sake", 0xFFB7D9BC)
                .build();

        VODKA = new FluidType
                .Builder("vodka", 0xFFDCDCDC)
                .build();

        CIDER = new FluidType
                .Builder("cider", 0xFFB0AE32)
                .build();

        // Другие жидкости
        LIMEWATER = new FluidType
                .Builder("limewater", 0xFFB4B4B4)
                .build();

        TANNIN = new FluidType
                .Builder("tannin", 0xFF63594E)
                .build();

        VINEGAR = new FluidType
                .Builder("vinegar", 0xFFC7C2AA)
                .build();

        BRINE = new FluidType
                .Builder("brine", 0xFFDCD3C9)
                .build();

        MILK = new FluidType
                .Builder("milk", 0xFFFFFFFF)
                .build();

        CURDLED_MILK = new FluidType
                .Builder("curdled_milk", 0xFFFFFBE8)
                .build();

        MILK_VINEGAR = new FluidType
                .Builder("milk_vinegar", 0xFFFFFBE8)
                .build();

        OLIVE_OIL = new FluidType
                .Builder("olive_oil", 0xFF6A7537)
                .build();

        OLIVE_OIL_WATER = new FluidType
                .Builder("olive_oil_water", 0xFF4A4702)
                .build();

        LYE = new FluidType
                .Builder("lye", 0XFFFEFFDE)
                .build();
    }
}
