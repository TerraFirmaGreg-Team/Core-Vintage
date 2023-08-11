package net.dries007.tfc.api.types.plant;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum PlantTypeEnum implements IStringSerializable {
    CLAY,
    DESERT_CLAY,
    DRY_CLAY,
    DRY,
    FRESH_BEACH,
    SALT_BEACH,
    FRESH_WATER,
    SALT_WATER,
    NONE;

    /**
     * Возвращает имя перечисления в нижнем регистре.
     */
    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
