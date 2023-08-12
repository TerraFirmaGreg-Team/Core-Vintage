package net.dries007.tfc.api.types.food.category;

import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class FoodCategory {

    private static final Set<FoodCategory> FOOD_CATEGORY = new LinkedHashSet<>();

    private final String name;
    @Nonnull
    private final TextFormatting textFormatting;

    public FoodCategory(String name, @Nonnull TextFormatting textFormatting) {
        this.name = name;
        this.textFormatting = textFormatting;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("FoodCategory name must contain any character: [%s]", name));
        }

        if (!FOOD_CATEGORY.add(this)) {
            throw new RuntimeException(String.format("FoodCategory: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех категорий еды.
     *
     * @return Набор всех категорий еды.
     */
    public static Set<FoodCategory> getAllFoodCategories() {
        return FOOD_CATEGORY;
    }

    /**
     * Возвращает строковое представление категории еды.
     *
     * @return Строковое представление категории еды.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает форматирование текста для отображения категории.
     *
     * @return Форматирование текста для отображения категории.
     */
    @Nonnull
    public TextFormatting getTextFormatting() {
        return textFormatting;
    }

    public static boolean doesStackMatchCategories(ItemStack stack, FoodCategory... categories) {
        for (FoodCategory cat : categories) {
            if (OreDictionaryHelper.doesStackMatchOre(stack, OreDictionaryHelper.upperCaseToCamelCase("category", cat.toString()))) {
                return true;
            }
        }
        return false;
    }
}
