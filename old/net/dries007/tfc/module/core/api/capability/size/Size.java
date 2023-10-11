package net.dries007.tfc.module.core.api.capability.size;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

/**
 * Перечисление для определения размера.
 */
public enum Size implements IStringSerializable {
    TINY, // Подходит для чего угодно
    VERY_SMALL, // Подходит для чего угодно
    SMALL, // Подходит для небольших сосудов
    NORMAL, // Подходит для больших сосудов
    LARGE, // Помещается в сундуки, ямные печи вмещают четыре штуки.
    VERY_LARGE, // Ямные печи могут вмещать только одну
    HUGE; // Засчитывается как перегруженный, ни во что не вписывается

    /**
     * Проверяет, является ли данный размер меньше, чем другой размер.
     *
     * @param other другой размер
     * @return true, если данный размер меньше, чем другой размер, иначе false
     */
    public boolean isSmallerThan(Size other) {
        return this.ordinal() < other.ordinal();
    }

    /**
     * Возвращает локализованное имя размера.
     *
     * @return локализованное имя размера
     */
    public String getLocalizedName() {
        return I18n.format("tfc.enum.size." + getName() + ".name");
    }

    /**
     * Возвращает имя размера.
     *
     * @return имя размера
     */
    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
