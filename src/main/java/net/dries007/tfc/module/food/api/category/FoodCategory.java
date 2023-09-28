package net.dries007.tfc.module.food.api.category;

import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс, представляющий категории еды.
 */
public class FoodCategory {

    private static final Set<FoodCategory> FOOD_CATEGORY = new LinkedHashSet<>();

    private final String name;
    @Nonnull
    private final TextFormatting textFormatting;

    /**
     * Конструктор класса FoodCategory.
     *
     * @param name           имя категории еды
     * @param textFormatting форматирование текста для отображения категории
     * @throws RuntimeException если имя категории пустое или уже существует
     */
    public FoodCategory(String name, @Nonnull TextFormatting textFormatting) {
        this.name = name;
        this.textFormatting = textFormatting;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("Имя категории еды не может быть пустым: [%s]", name));
        }

        if (!FOOD_CATEGORY.add(this)) {
            throw new RuntimeException(String.format("Категория еды уже существует: [%s]", name));
        }
    }

    /**
     * Возвращает набор всех категорий еды.
     *
     * @return Набор всех категорий еды.
     */
    public static Set<FoodCategory> getFoodCategories() {
        return FOOD_CATEGORY;
    }

    /**
     * Проверяет, соответствует ли предмет указанным категориям еды.
     *
     * @param stack      предмет для проверки
     * @param categories категории еды
     * @return true, если предмет соответствует хотя бы одной из категорий, иначе false
     */
    public static boolean doesStackMatchCategories(ItemStack stack, FoodCategory... categories) {
        for (FoodCategory cat : categories) {
            if (OreDictionaryHelper.doesStackMatchOre(stack, OreDictionaryHelper.upperCaseToCamelCase("category", cat.toString()))) {
                return true;
            }
        }
        return false;
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
}
