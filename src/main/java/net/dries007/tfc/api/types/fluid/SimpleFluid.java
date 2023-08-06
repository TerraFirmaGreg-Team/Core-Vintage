package net.dries007.tfc.api.types.fluid;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum SimpleFluid implements IStringSerializable {
    VINEGAR(0xFFC7C2AA),
    BRINE(0xFFDCD3C9),
    MILK(0xFFFFFFFF),
    OLIVE_OIL(0xFF6A7537),
    OLIVE_OIL_WATER(0xFF4A4702),
    TANNIN(0xFF63594E),
    LIMEWATER(0xFFB4B4B4),
    CURDLED_MILK(0xFFFFFBE8),
    MILK_VINEGAR(0xFFFFFBE8),
    LYE(0XFFFEFFDE);
    private static final SimpleFluid[] VALUES = values();

    private final int color;

    SimpleFluid(int color) {
        this.color = color;
    }

    /**
     * Возвращает цвет данного типа жидкости.
     *
     * @return цвет
     */
    public int getColor() {
        return color;
    }

    /**
     * Возвращает имя перечисления в нижнем регистре.
     *
     * @return имя перечисления
     */
    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
