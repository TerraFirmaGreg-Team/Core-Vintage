package net.dries007.tfc.api.types.plant;

import net.minecraft.util.IStringSerializable;

public enum PlantValidity implements IStringSerializable {
    COLD,
    HOT,
    DRY,
    WET,
    VALID;

    /**
     * Возвращает имя перечисления в нижнем регистре.
     */
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
