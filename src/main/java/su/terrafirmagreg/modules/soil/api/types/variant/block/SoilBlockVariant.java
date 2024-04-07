package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.api.lib.Pair;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.data.BlocksSoil;

import net.minecraft.block.Block;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Getter;

import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.function.BiFunction;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification;
import static su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariants.*;

/**
 * Класс, представляющий вариант блока почвы.
 */
public class SoilBlockVariant implements Comparable<SoilBlockVariant> {

    private static final Set<SoilBlockVariant> SOIL_BLOCK_VARIANTS = new ObjectOpenHashSet<>();

    @NotNull
    private final String name;
    @Getter
    private final Specification specification;

    private SoilBlockVariant(Builder builder) {
        this.name = builder.name;
        this.specification = builder.specification;

        if (name.isEmpty())
            throw new RuntimeException(String.format("SoilBlockVariant name must contain any character: [%s]", name));

        if (!SOIL_BLOCK_VARIANTS.add(this))
            throw new RuntimeException(String.format("SoilBlockVariant: [%s] already exists!", name));

        for (var type : SoilType.getTypes()) {
            if (BlocksSoil.SOIL_BLOCKS.put(Pair.of(this, type), builder.factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
    }

    /**
     * Возвращает набор всех вариантов блока почвы.
     *
     * @return Набор всех вариантов блока почвы.
     */
    public static Set<SoilBlockVariant> getBlockVariants() {
        return SOIL_BLOCK_VARIANTS;
    }

    public Block get(SoilType type) {
        var block = BlocksSoil.SOIL_BLOCKS.get(Pair.of(this, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block soil is null: %s, %s", this, type));
    }

    /**
     * Возвращает строковое представление варианта блока почвы.
     *
     * @return Строковое представление варианта блока почвы.
     */
    @NotNull
    @Override
    public String toString() {
        return name;
    }

    /**
     * Проверяет, может ли блок почвы падать.
     *
     * @return true, если блок почвы может падать, иначе false.
     */
    public boolean canFall() {
        return specification != null;
    }

    /**
     * Возвращает вариант блока почвы без травы.
     *
     * @return Вариант блока почвы без травы.
     */
    public SoilBlockVariant getNonGrassVersion() {
        if (this == GRASS || this == DRY_GRASS) return DIRT;

        if (this == CLAY_GRASS) return CLAY;

        throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    /**
     * Возвращает вариант блока почвы с травой.
     *
     * @param spreader Вариант блока почвы, который распространяет траву.
     * @return Вариант блока почвы с травой.
     */
    public SoilBlockVariant getGrassVersion(SoilBlockVariant spreader) {
        if (this == DIRT) return CLAY_GRASS;

        if (this == CLAY) {
            return spreader == DRY_GRASS ? DRY_GRASS : GRASS;
        }

        throw new IllegalArgumentException(String.format("You cannot get grass from [%s] types.", spreader.toString()));
    }

    /**
     * Проверяет, является ли вариант блока почвы с травой.
     *
     * @return true, если вариант блока почвы с травой, иначе false.
     */
    public boolean isGrass() {
        return this == GRASS || this == DRY_GRASS ||
                this == CLAY_GRASS;
    }

    @Override
    public int compareTo(@NotNull SoilBlockVariant blockVariant) {
        return this.name.compareTo(blockVariant.toString());
    }

    public static class Builder {

        private final String name;
        private BiFunction<SoilBlockVariant, SoilType, ? extends Block> factory;
        private Specification specification = null;

        /**
         * Создает экземпляр Builder с указанным именем.
         *
         * @param name Имя породы.
         */
        public Builder(@NotNull String name) {
            this.name = name;
        }

        public Builder setFactory(BiFunction<SoilBlockVariant, SoilType, ? extends Block> factory) {
            this.factory = factory;
            return this;
        }

        public Builder setFallingSpecification(Specification specification) {
            this.specification = specification;
            return this;
        }

        public SoilBlockVariant build() {
            return new SoilBlockVariant(this);
        }
    }
}
