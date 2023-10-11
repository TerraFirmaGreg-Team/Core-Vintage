package net.dries007.tfc.module.core.api.capability.size;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

/**
 * Перечисление для определения веса.
 */
public enum Weight implements IStringSerializable {
    VERY_LIGHT(64),
    LIGHT(32),
    MEDIUM(16),
    HEAVY(4),
    VERY_HEAVY(1);

    private final int stackSize;

    /**
     * Конструктор с параметром.
     *
     * @param stackSize размер стека предметов
     */
    Weight(int stackSize) {
        this.stackSize = stackSize;
    }

    /**
     * Возвращает размер стека предметов.
     *
     * @return размер стека предметов
     */
    public int getStackSize() {
        return stackSize;
    }

    /**
     * Возвращает локализованное имя.
     *
     * @return локализованное имя
     */
    public String getLocalizedName() {
        return I18n.format("tfc.enum.weight." + getName() + ".name");
    }

    /**
     * Возвращает имя.
     *
     * @return имя
     */
    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
