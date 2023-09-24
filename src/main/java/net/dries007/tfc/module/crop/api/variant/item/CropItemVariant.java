package net.dries007.tfc.module.crop.api.variant.item;

import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.crop.api.type.CropType;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.module.crop.common.CropStorage.CROP_ITEMS;

/**
 * Класс CropItemVariant представляет вариант деревянного блока.
 */
public class CropItemVariant {

    private static final Set<CropItemVariant> CROP_ITEM_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<CropItemVariant, CropType, ICropItem> factory;

    /**
     * Создает новый вариант деревянного блока с заданным именем и фабрикой.
     *
     * @param name    имя варианта деревянного блока
     * @param factory фабрика, создающая деревянный блок
     * @throws RuntimeException если имя варианта пустое или вариант с таким именем уже существует
     */
    public CropItemVariant(@Nonnull String name, @Nonnull BiFunction<CropItemVariant, CropType, ICropItem> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("CropItemVariant name must contain any character: [%s]", name));
        }

        if (!CROP_ITEM_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("CropItemVariant: [%s] already exists!", name));
        }

        for (var type : CropType.getCropTypes()) {
            if (CROP_ITEMS.put(new Pair<>(this, type), this.create(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
    }

    /**
     * Возвращает множество всех созданных вариантов деревянных блоков.
     *
     * @return множество вариантов деревянных блоков
     */
    public static Set<CropItemVariant> getCropItemVariants() {
        return CROP_ITEM_VARIANTS;
    }

    /**
     * Возвращает строковое представление варианта деревянного блока (его имя).
     *
     * @return имя варианта деревянного блока
     */
    @Nonnull
    @Override
    public String toString() {
        return name;
    }

    /**
     * Применяет вариант деревянного блока к фабрике, чтобы получить соответствующий деревянный блок.
     *
     * @param type тип дерева
     * @return объект IWoodBlock, созданный фабрикой
     */
    @Nonnull
    public ICropItem create(@Nonnull CropType type) {
        return factory.apply(this, type);
    }
}
