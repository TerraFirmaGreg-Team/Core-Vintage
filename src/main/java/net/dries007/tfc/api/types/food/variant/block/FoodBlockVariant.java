package net.dries007.tfc.api.types.food.variant.block;

import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.api.types.food.variant.Item.IFoodItem;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class FoodBlockVariant {
    private static final Set<FoodBlockVariant> FOOD_BLOCK_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<FoodBlockVariant, FoodType, IFoodItem> factory;

    /**
     * Создает экземпляр класса FoodBlockVariant с указанными параметрами.
     *
     * @param name    Имя варианта еды.
     * @param factory Фабричная функция для создания металлического блока.
     */
    public FoodBlockVariant(@Nonnull String name, @Nonnull BiFunction<FoodBlockVariant, FoodType, IFoodItem> factory) {
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
    public static Set<FoodBlockVariant> getFoodBlockVariants() {
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
