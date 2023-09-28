package net.dries007.tfc.api.capability.size;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

public enum Size implements IStringSerializable {
    TINY, // Fits in anything
    VERY_SMALL, // Fits in anything
    SMALL, // Fits in small vessels
    NORMAL, // Fits in large vessels
    LARGE, // Fits in chests, Pit kilns can hold four
    VERY_LARGE, // Pit kilns can only hold one
    HUGE; // Counts towards overburdened, fits in nothing


    public boolean isSmallerThan(Size other) {
        return this.ordinal() < other.ordinal();
    }

    public String getLocalizedName() {
        return I18n.format("tfc.enum.size." + getName() + ".name");
    }

    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
