package net.dries007.tfc.module.food.api.variant.Item;

import net.dries007.tfc.module.food.api.type.FoodType;
import net.dries007.tfc.api.util.Pair;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.common.objects.items.TFCItems.FOOD_ITEMS;
import static net.dries007.tfc.common.objects.items.TFCItems.ITEMS;

/**
 * Класс, представляющий тип блока породы.
 */
public class FoodItemVariant {
    private static final Set<FoodItemVariant> FOOD_ITEM_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;


    @Nonnull
    private final BiFunction<FoodItemVariant, FoodType, IFoodItem> factory;

    /**
     * Создает экземпляр класса RockType с указанными параметрами.
     *
     * @param name    Название типа блока породы.
     * @param factory Функция-фабрика для создания блока породы по умолчанию.
     */
    public FoodItemVariant(@Nonnull String name, @Nonnull BiFunction<FoodItemVariant, FoodType, IFoodItem> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("FoodItemVariant name must contain any character: [%s]", name));
        }

        if (!FOOD_ITEM_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("FoodItemVariant: [%s] already exists!", name));
        }

        for (var type : FoodType.getFoodType()) {
            var foodItem = this.create(type);

            if (FOOD_ITEMS.put(new Pair<>(this, type), foodItem) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
            ITEMS.add((Item) foodItem);
        }
    }

    /**
     * Возвращает набор всех типов блоков породы.
     *
     * @return Набор всех типов блоков породы.
     */
    public static Set<FoodItemVariant> getFoodItemVariants() {
        return FOOD_ITEM_VARIANTS;
    }

    /**
     * Возвращает строковое представление типа блока породы.
     *
     * @return Строковое представление типа блока породы.
     */
    @Override
    public String toString() {
        return name;
    }


    /**
     * Применяет функцию-фабрику к типу блока породы и возвращает созданный блок породы.
     *
     * @param type Тип породы.
     * @return Созданный блок породы.
     */
    @Nonnull
    public IFoodItem create(FoodType type) {
        return factory.apply(this, type);
    }
}
