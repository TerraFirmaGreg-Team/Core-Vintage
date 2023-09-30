package net.dries007.tfc.module.soil.api.types.variant.item;

import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.dries007.tfc.module.core.api.util.Pair;
import net.dries007.tfc.module.soil.StorageSoil;
import net.dries007.tfc.module.soil.api.types.type.SoilType;

import javax.annotation.Nonnull;
import java.util.Set;
import java.util.function.BiFunction;

/**
 * Класс CropItemVariant представляет вариант деревянного блока.
 */
public class SoilItemVariant {

    private static final Set<SoilItemVariant> SOIL_ITEM_VARIANTS = new ObjectLinkedOpenHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<SoilItemVariant, SoilType, ISoilItem> factory;

    /**
     * Создает новый вариант деревянного блока с заданным именем и фабрикой.
     *
     * @param name    имя варианта деревянного блока
     * @param factory фабрика, создающая деревянный блок
     * @throws RuntimeException если имя варианта пустое или вариант с таким именем уже существует
     */
    public SoilItemVariant(@Nonnull String name, @Nonnull BiFunction<SoilItemVariant, SoilType, ISoilItem> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("CropItemVariant name must contain any character: [%s]", name));
        }

        if (!SOIL_ITEM_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("CropItemVariant: [%s] already exists!", name));
        }

        for (var type : SoilType.getSoilTypes()) {
            if (StorageSoil.SOIL_ITEMS.put(new Pair<>(this, type), this.create(type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
    }

    /**
     * Возвращает множество всех созданных вариантов деревянных блоков.
     *
     * @return множество вариантов деревянных блоков
     */
    public static Set<SoilItemVariant> getSoilItemVariants() {
        return SOIL_ITEM_VARIANTS;
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
    public ISoilItem create(@Nonnull SoilType type) {
        return factory.apply(this, type);
    }
}
