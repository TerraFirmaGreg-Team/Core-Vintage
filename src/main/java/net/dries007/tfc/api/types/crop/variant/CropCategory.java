package net.dries007.tfc.api.types.crop.variant;

import net.dries007.tfc.api.types.crop.ICropBlock;
import net.dries007.tfc.api.types.crop.type.CropBlockVariant;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Function;

public class CropCategory {

    private static final Set<CropCategory> CROP_CATEGORIES = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final Function<CropBlockVariant, ICropBlock> factory;

    /**
     * Создает экземпляр класса CropCategory с указанными параметрами.
     *
     * @param name    Имя категории блока культур.
     * @param factory Фабричная функция для создания блока культур.
     */
    public CropCategory(@Nonnull String name, @Nonnull Function<CropBlockVariant, ICropBlock> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("CropCategory name must contain any character: [%s]", name));
        }

        if (!CROP_CATEGORIES.add(this)) {
            throw new RuntimeException(String.format("CropCategory: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех категорий культур.
     *
     * @return Набор всех категории культур.
     */
    public static Set<CropCategory> getCropCategories() {
        return CROP_CATEGORIES;
    }

    /**
     * Возвращает строковое представление категории культур.
     *
     * @return Строковое представление категории культур.
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }
}
