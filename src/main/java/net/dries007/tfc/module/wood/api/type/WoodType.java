package net.dries007.tfc.module.wood.api.type;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс Wood представляет тип дерева с определенными характеристиками.
 */
public class WoodType {
    private static final Set<WoodType> WOOD_TYPES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    private final int color;
    private final float burnTemp;
    private final int burnTicks;
    private final boolean canMakeTannin;

    private WoodType(Builder builder) {
        this.name = builder.name;
        this.color = builder.color;

        this.burnTemp = builder.burnTemp;
        this.burnTicks = builder.burnTicks;
        this.canMakeTannin = builder.canMakeTannin;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("WoodType name must contain any character: [%s]", name));
        }

        if (!WOOD_TYPES.add(this)) {
            throw new RuntimeException(String.format("WoodType: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает список всех доступных типов дерева.
     *
     * @return список типов дерева
     */
    public static Set<WoodType> getWoodTypes() {
        return WOOD_TYPES;
    }

    /**
     * Возвращает строковое представление древесины.
     *
     * @return Строковое представление древесины.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает цвет дерева.
     *
     * @return цвет дерева
     */
    public int getColor() {
        return color;
    }

    /**
     * Возвращает температуру, при которой дерево будет гореть в костре или подобном устройстве.
     *
     * @return температура горения
     */
    public float getBurnTemp() {
        return burnTemp;
    }

    /**
     * Возвращает количество тиков, в течение которых дерево будет гореть в костре или подобном устройстве.
     *
     * @return количество тиков горения
     */
    public int getBurnTicks() {
        return burnTicks;
    }

    /**
     * Проверяет, может ли дерево производить дубильные вещества.
     *
     * @return true, если дерево может производить дубильные вещества, иначе false
     */
    public boolean canMakeTannin() {
        return canMakeTannin;
    }


    public static class Builder {
        private final String name;
        private int color;
        private float burnTemp;
        private int burnTicks;
        private boolean canMakeTannin;

        public Builder(@Nonnull String name) {
            this.name = name;
            this.color = 0xFFFFFF;
            this.burnTemp = 0;
            this.burnTicks = 0;
            this.canMakeTannin = false;
        }

        public Builder setColor(int color) {
            this.color = color;
            return this;
        }

        // Установить температуру и количество тиков горения
        public Builder setBurnInfo(float burnTemp, int burnTicks) {
            this.burnTemp = burnTemp;
            this.burnTicks = burnTicks;
            return this;
        }

        // Установить возможность производить танин
        public Builder setTannin() {
            canMakeTannin = true;
            return this;
        }

        // Метод для построения объекта WoodType
        public WoodType build() {
            return new WoodType(this);
        }
    }
}
