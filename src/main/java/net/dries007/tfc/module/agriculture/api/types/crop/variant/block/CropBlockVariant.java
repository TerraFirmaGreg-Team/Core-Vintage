package net.dries007.tfc.module.agriculture.api.types.crop.variant.block;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.agriculture.api.types.crop.type.CropType;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.module.agriculture.StorageAgriculture.CROP_BLOCKS;

public class CropBlockVariant {
    private static final Set<CropBlockVariant> CROP_BLOCK_VARIANTS = new ObjectLinkedOpenHashSet<>();
    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<CropBlockVariant, CropType, ICropBlock> factory;

    /**
     * Создает экземпляр класса CropBlockVariant с указанными параметрами.
     *
     * @param name Имя категории блока культур.
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

        for (var type : CropType.getCropTypes()) {
            if (CROP_BLOCKS.put(new Pair<>(this, type), this.create(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
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
