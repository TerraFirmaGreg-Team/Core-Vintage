package net.dries007.tfc.api.types.wood.category;

import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Класс, представляющий категорию породы.
 */
public class WoodCategory {
    private static final Set<WoodCategory> WOOD_CATEGORIES = new LinkedHashSet<>();

    private final String name;

    @Nonnull
    private final TextFormatting textFormatting;


    /**
     * Создает экземпляр класса WoodCategory с указанными параметрами.
     *
     * @param name           Название категории породы.
     * @param textFormatting Форматирование текста для отображения категории.
     */
    WoodCategory(@Nonnull String name, @Nonnull TextFormatting textFormatting) {
        this.name = name;
        this.textFormatting = textFormatting;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("WoodCategory name must contain any character: [%s]", name));
        }

        if (!WOOD_CATEGORIES.add(this)) {
            throw new RuntimeException(String.format("WoodCategory: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех категорий пород.
     *
     * @return Набор всех категорий пород.
     */
    public static Set<WoodCategory> getWoodCategories() {
        return WOOD_CATEGORIES;
    }

    /**
     * Возвращает строковое представление категории породы.
     *
     * @return Строковое представление категории породы.
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
