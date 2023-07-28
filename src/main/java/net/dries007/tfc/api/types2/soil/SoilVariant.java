package net.dries007.tfc.api.types2.soil;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.util.FallingBlockManager.Specification;
import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.objects.blocks.soil.BlockSoil;
import net.dries007.tfc.objects.blocks.soil.BlockSoilFarmland;
import net.dries007.tfc.objects.blocks.soil.BlockSoilGrass;
import net.dries007.tfc.objects.blocks.soil.BlockSoilPath;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_AND_HORIZONTAL;
import static net.dries007.tfc.api.util.FallingBlockManager.Specification.VERTICAL_ONLY;

@MethodsReturnNonnullByDefault
public enum SoilVariant implements IStringSerializable {
    DIRT(BlockSoil::new, VERTICAL_AND_HORIZONTAL),
    GRASS(BlockSoilGrass::new, VERTICAL_AND_HORIZONTAL),
    DRY_GRASS(BlockSoilGrass::new, VERTICAL_AND_HORIZONTAL),
    PATH(BlockSoilPath::new, VERTICAL_ONLY),
    CLAY(BlockSoil::new, VERTICAL_ONLY),
    CLAY_GRASS(BlockSoilGrass::new, VERTICAL_ONLY),
    FARMLAND(BlockSoilFarmland::new, VERTICAL_ONLY);
//	ROOTED_DIRT(VERTICAL_ONLY),
//	MUD(VERTICAL_ONLY),
//	MUD_BRICKS(VERTICAL_ONLY),
//	DRYING_BRICKS(VERTICAL_ONLY);


    public static final SoilVariant[] VALUES = SoilVariant.values();
    private final BiFunction<SoilVariant, SoilType, ISoilTypeBlock> blockFactory;
    @Nullable
    private final Specification fallingSpecification;

    SoilVariant(BiFunction<SoilVariant, SoilType, ISoilTypeBlock> blockFactory, @Nullable Specification fallingSpecification) {
        this.blockFactory = blockFactory;
        this.fallingSpecification = fallingSpecification;

    }

    public static SoilVariant valueOf(int i) {
        return i >= 0 && i < VALUES.length ? VALUES[i] : DIRT;
    }

    public ISoilTypeBlock createBlock(SoilType soilType) {
        return this.blockFactory.apply(this, soilType);
    }

    @Nullable
    public Specification getFallingSpecification() {
        return fallingSpecification;
    }

    public boolean canFall() {
        return fallingSpecification != null;
    }


    public SoilVariant getNonGrassVersion() {
        switch (this) {
            case GRASS:
            case DRY_GRASS:
                return DIRT;
            case CLAY_GRASS:
                return CLAY;
        }
        throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
    }

    public SoilVariant getGrassVersion(SoilVariant spreader) {
        if (false) throw new IllegalArgumentException("Non-grass can't spread.");
        switch (this) {
            case DIRT:
                return spreader == DRY_GRASS ? DRY_GRASS : GRASS;
            case CLAY:
                return CLAY_GRASS;
        }
        throw new IllegalArgumentException("You cannot get grass from |" + spreader.getName() + "| types.");
    }

    public SoilVariant transform() {
        return switch (this) {
            case DIRT -> GRASS;
            case GRASS, DRY_GRASS, PATH, FARMLAND -> DIRT;
            case CLAY -> CLAY_GRASS;
            case CLAY_GRASS -> CLAY;
        };
    }


    /**
     * Возвращает имя перечисления в нижнем регистре.
     */
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
