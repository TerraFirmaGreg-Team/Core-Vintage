package net.dries007.tfc.api.types.crop.category;

import net.minecraft.util.text.TextFormatting;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;

public class CropCategory {

    private static final Set<CropCategory> CROP_CATEGORIES = new LinkedHashSet<>();

    private final String name;
    @Nonnull
    private final TextFormatting textFormatting;

    public CropCategory(String name, @Nonnull TextFormatting textFormatting) {
        this.name = name;
        this.textFormatting = textFormatting;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("CropCategory name must contain any character: [%s]", name));
        }

        if (!CROP_CATEGORIES.add(this)) {
            throw new RuntimeException(String.format("CropCategory: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех типов культур.
     *
     * @return Набор всех типов культур.
     */
    public static Set<CropCategory> getCropCategories() {
        return CROP_CATEGORIES;
    }

    /**
     * Возвращает строковое представление типа культур.
     *
     * @return Строковое представление типа культур.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Возвращает форматирование текста для отображения типа.
     *
     * @return Форматирование текста для отображения типа.
     */
    @Nonnull
    public TextFormatting getTextFormatting() {
        return textFormatting;
    }
}
