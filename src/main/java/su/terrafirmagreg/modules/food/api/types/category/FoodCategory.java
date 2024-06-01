package su.terrafirmagreg.modules.food.api.types.category;

import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;


import net.dries007.tfc.util.OreDictionaryHelper;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс, представляющий категории еды.
 */
@Getter
public class FoodCategory {

    @Getter
    private static final Set<FoodCategory> categories = new LinkedHashSet<>();

    private final String name;
    private final TextFormatting textFormatting;

    private FoodCategory(Builder builder) {
        this.name = builder.name;
        this.textFormatting = builder.textFormatting;

        if (name.isEmpty()) throw new RuntimeException(String.format("FoodCategory name must contain any character: [%s]", name));

        if (!categories.add(this)) throw new RuntimeException(String.format("FoodCategory: [%s] already exists!", name));
    }

    /**
     * Проверяет, соответствует ли предмет указанным категориям еды.
     *
     * @param stack      предмет для проверки
     * @param categories категории еды
     * @return true, если предмет соответствует хотя бы одной из категорий, иначе false
     */
    public static boolean doesStackMatchCategories(ItemStack stack, FoodCategory... categories) {
        for (FoodCategory category : categories) {
            if (OreDictionaryHelper.doesStackMatchOre(stack, OreDictUtils.toString("category", category.getName()))) {
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

    public static class Builder {

        private final String name;
        private TextFormatting textFormatting = TextFormatting.RESET;

        /**
         * Конструктор класса Builder.
         *
         * @param name Название категории.
         */
        public Builder(@NotNull String name) {

            this.name = name;
        }

        public Builder setTextFormatting(TextFormatting textFormatting) {
            this.textFormatting = textFormatting;
            return this;
        }

        public FoodCategory build() {

            return new FoodCategory(this);
        }
    }

}
