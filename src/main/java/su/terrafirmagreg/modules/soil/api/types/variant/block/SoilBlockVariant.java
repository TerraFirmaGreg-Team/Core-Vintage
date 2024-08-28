package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.data.lib.Pair;
import su.terrafirmagreg.data.lib.types.variant.Variant;
import su.terrafirmagreg.modules.soil.api.spi.IGrass;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.init.BlocksSoil;

import net.minecraft.block.Block;


import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.features.falling.FallingBlockManager.Specification;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.*;

@Getter
public class SoilBlockVariant extends Variant<SoilBlockVariant> {

    @Getter
    private static final Set<SoilBlockVariant> variants = new ObjectOpenHashSet<>();

    private Specification specification;
    private BiFunction<SoilBlockVariant, SoilType, ? extends Block> factory;

    private SoilBlockVariant(String name) {
        super(name);
    }

    public static SoilBlockVariant builder(String name) {
        return new SoilBlockVariant(name);
    }

    public SoilBlockVariant setFactory(BiFunction<SoilBlockVariant, SoilType, ? extends Block> factory) {
        this.factory = factory;
        return this;
    }

    public SoilBlockVariant setFallingSpecification(Specification specification) {
        this.specification = specification;
        return this;
    }

    public SoilBlockVariant build() {
        if (!variants.add(this)) throw new RuntimeException(String.format("SoilBlockVariant: [%s] already exists!", getName()));

        for (var type : SoilType.getTypes()) {
            if (BlocksSoil.SOIL_BLOCKS.put(Pair.of(this, type), factory.apply(this, type)) != null)
                throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
        return this;
    }

    public Block get(SoilType type) {
        var block = BlocksSoil.SOIL_BLOCKS.get(Pair.of(this, type));
        if (block != null) return block;
        throw new RuntimeException(String.format("Block soil is null: %s, %s", this, type));
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
        if (this == GRASS ||
                this == DRY_GRASS ||
                this == SPARSE_GRASS ||
                this == PODZOL ||
                this == MYCELIUM)
            return DIRT;

        throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    /**
     * Возвращает вариант блока почвы с травой.
     *
     * @param spreader Вариант блока почвы, который распространяет траву.
     * @return Вариант блока почвы с травой.
     */
    public SoilBlockVariant getGrassVersion(SoilBlockVariant spreader) {
        if (this instanceof IGrass) return this;
        if (this == DIRT) return spreader == DRY_GRASS ? DRY_GRASS : GRASS;
        if (this == ROOTED_DIRT || this == COARSE_DIRT) return spreader == SPARSE_GRASS ? SPARSE_GRASS : DRY_GRASS;

        throw new IllegalArgumentException(String.format("You cannot get grass from [%s] types.", this));
    }

    private SoilBlockVariant transform() {
        if (this == DIRT) {
            return GRASS;
        } else if (this == COARSE_DIRT) {
            return SPARSE_GRASS;
        } else if (this == GRASS ||
                this == DRY_GRASS ||
                this == SPARSE_GRASS ||
                this == PODZOL ||
                this == MYCELIUM ||
                this == FARMLAND ||
                this == GRASS_PATH ||
                this == ROOTED_DIRT) {
            return DIRT;
        } else {
            throw new IllegalArgumentException(String.format("You cannot get grass from [%s] types.", this));
        }
    }

}
