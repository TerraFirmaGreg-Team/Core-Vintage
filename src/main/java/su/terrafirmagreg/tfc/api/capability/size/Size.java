package su.terrafirmagreg.tfc.api.capability.size;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;

/**
 * Перечисление для определения размера.
 */
public enum Size implements IStringSerializable {

    /**
     * Подходит для чего угодно
     */
    TINY,

    /**
     * Подходит для чего угодно
     */
    VERY_SMALL,

    /**
     * Подходит для небольших сосудов
     */
    SMALL,

    /**
     * Подходит для больших сосудов
     */
    NORMAL,

    /**
     * Помещается в сундуки, ямные печи вмещают четыре штуки.
     */
    LARGE,

    /**
     * Ямные печи могут вмещать только одну.
     */
    VERY_LARGE,

    /**
     * Засчитывается как перегруженный, ни во что не кладется.
     */
    HUGE;

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
