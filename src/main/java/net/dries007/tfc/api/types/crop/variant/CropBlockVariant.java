package net.dries007.tfc.api.types.crop.variant;

import net.dries007.tfc.api.types.crop.ICropBlock;
import net.dries007.tfc.api.types.crop.type.CropType;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class CropBlockVariant {
    private static final Set<CropBlockVariant> CROP_BLOCK_VARIANTS = new LinkedHashSet<>();
    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<CropBlockVariant, CropType, ICropBlock> factory;

    /**
     * Создает экземпляр класса CropBlockVariant с указанными параметрами.
     *
     * @param name    Имя категории блока культур.
     */
    public CropBlockVariant(@Nonnull String name, @Nonnull BiFunction<CropBlockVariant, CropType, ICropBlock> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("CropBlockVariant name must contain any character: [%s]", name));
        }
        if (!CROP_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("CropBlockVariant: [%s] already exists!", name));
        }
    }

    /**
     * Возвращает набор всех категорий культур.
     *
     * @return Набор всех категории культур.
     */
    public static Set<CropBlockVariant> getCropBlockVariants() {
        return CROP_BLOCK_VARIANTS;
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


    @Nonnull
    public ICropBlock create(CropType type) {
        return factory.apply(this, type);
    }
}
