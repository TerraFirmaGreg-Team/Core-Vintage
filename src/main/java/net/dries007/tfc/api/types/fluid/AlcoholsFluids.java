package net.dries007.tfc.api.types.fluid;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum AlcoholsFluids implements IStringSerializable {
    RUM(0xFF6E0123),
    BEER(0xFFC39E37),
    WHISKEY(0xFF583719),
    RYE_WHISKEY(0xFFC77D51),
    CORN_WHISKEY(0xFFD9C7B7),
    SAKE(0xFFB7D9BC),
    VODKA(0xFFDCDCDC),
    CIDER(0xFFB0AE32);

    private static final AlcoholsFluids[] VALUES = values();

    private final int color;

    AlcoholsFluids(int color) {
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
