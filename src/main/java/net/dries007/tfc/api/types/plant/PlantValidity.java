package net.dries007.tfc.api.types.plant;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum PlantValidity implements IStringSerializable {
    COLD,
    HOT,
    DRY,
    WET,
    VALID;

    /**
     * Возвращает имя перечисления в нижнем регистре.
     */
    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
