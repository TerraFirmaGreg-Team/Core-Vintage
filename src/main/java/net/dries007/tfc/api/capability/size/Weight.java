package net.dries007.tfc.api.capability.size;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

/**
 * Перечисление Weight реализует интерфейс IStringSerializable.
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
     * @param stackSize размер стопки предметов
     */
    Weight(int stackSize) {
        this.stackSize = stackSize;
    }

    /**
     * Метод для получения размера стопки предметов.
     *
     * @return размер стопки предметов
     */
    public int getStackSize() {
        return stackSize;
    }

    /**
     * Метод для получения локализованного имени.
     *
     * @return локализованное имя
     */
    public String getLocalizedName() {
        return I18n.format("tfc.enum.weight." + getName() + ".name");
    }

    /**
     * Метод для получения имени.
     *
     * @return имя
     */
    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
