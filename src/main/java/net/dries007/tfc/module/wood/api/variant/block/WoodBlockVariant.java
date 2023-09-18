package net.dries007.tfc.module.wood.api.variant.block;

import net.dries007.tfc.module.wood.ModuleWood;
import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.module.wood.common.WoodStorage;
import net.minecraft.block.Block;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.common.objects.blocks.TFCBlocks.*;

/**
 * Класс CropItemVariant представляет вариант деревянного блока.
 */
public class WoodBlockVariant {

    private static final Set<WoodBlockVariant> WOOD_BLOCK_VARIANTS = new LinkedHashSet<>();

    @Nonnull
    private final String name;
    @Nonnull
    private final BiFunction<WoodBlockVariant, WoodType, IWoodBlock> factory;

    /**
     * Создает новый вариант деревянного блока с заданным именем и фабрикой.
     *
     * @param name    имя варианта деревянного блока
     * @param factory фабрика, создающая деревянный блок
     * @throws RuntimeException если имя варианта пустое или вариант с таким именем уже существует
     */
    public WoodBlockVariant(@Nonnull String name, @Nonnull BiFunction<WoodBlockVariant, WoodType, IWoodBlock> factory) {
        this.name = name;
        this.factory = factory;

        if (name.isEmpty()) {
            throw new RuntimeException(String.format("WoodBlockVariant name must contain any character: [%s]", name));
        }

        if (!WOOD_BLOCK_VARIANTS.add(this)) {
            throw new RuntimeException(String.format("WoodBlockVariant: [%s] already exists!", name));
        }

        for (var type : WoodType.getWoodTypes()) {
            var woodBlock = this.create(type);

            if (WoodStorage.WOOD_BLOCKS.put(new Pair<>(this, type), woodBlock) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }

    }

    /**
     * Возвращает множество всех созданных вариантов деревянных блоков.
     *
     * @return множество вариантов деревянных блоков
     */
    public static Set<WoodBlockVariant> getWoodBlockVariants() {
        return WOOD_BLOCK_VARIANTS;
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
     * @param woodType тип дерева
     * @return объект IWoodBlock, созданный фабрикой
     */
    @Nonnull
    public IWoodBlock create(@Nonnull WoodType woodType) {
        return factory.apply(this, woodType);
    }
}
