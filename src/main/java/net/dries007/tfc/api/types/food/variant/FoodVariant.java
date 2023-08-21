package net.dries007.tfc.api.types.food.variant;

import net.dries007.tfc.api.types.food.IFoodItem;
import net.dries007.tfc.api.types.food.type.FoodType;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class FoodVariant {
    private static final Set<FoodVariant> FOOD_BLOCK_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<FoodVariant, FoodType, IFoodItem> factory;

    /**
     * Создает экземпляр класса FoodVariant с указанными параметрами.
     *
     * @param name    Имя варианта еды.
     * @param factory Фабричная функция для создания металлического блока.
     */
    public FoodVariant(@Nonnull String name, @Nonnull BiFunction<FoodVariant, FoodType, IFoodItem> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("MetalBlockVariant name must contain any character: [%s]", name));
        }

        if (!FOOD_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("MetalBlockVariant: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех вариантов металлического блока.
     *
     * @return Набор всех вариантов металлического блока.
     */
    public static Set<FoodVariant> getMetalBlockVariants() {
        return FOOD_BLOCK_VARIANTS;
    }

    /**
     * Возвращает строковое представление варианта металлического блока.
     *
     * @return Строковое представление варианта металлического блока.
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }

    /**
     * Применяет вариант металлического блока к фабрике для создания металлического блока.
     *
     * @param type Тип еды.
     * @return еда.
     */
    @Nonnull
    public IFoodItem create(FoodType type) {
        return this.factory.apply(this, type);
    }
}
