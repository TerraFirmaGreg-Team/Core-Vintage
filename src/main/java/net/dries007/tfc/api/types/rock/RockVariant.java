package net.dries007.tfc.api.types.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.types.rock.util.IRockBlock;
import net.dries007.tfc.api.util.FallingBlockManager.Specification;
import net.dries007.tfc.objects.blocks.rock.*;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nullable;
import java.util.function.BiFunction;

import static net.dries007.tfc.api.util.FallingBlockManager.Specification.*;

/**
 * Перечисление, которое представляет различные варианты камня.
 */
@MethodsReturnNonnullByDefault
public enum RockVariant implements IStringSerializable {
    RAW(BlockRockRaw::new, 6.5f, COLLAPSABLE_ROCK),
    COBBLE(BlockRockFallable::new, 5.5f, VERTICAL_AND_HORIZONTAL_ROCK),
    BRICK(BlockRock::new, 6.5f, null),
    CRACKED(BlockRock::new, 6.5f, null),
    CHISELED(BlockRock::new, 6.5f, null),
    SMOOTH(BlockRock::new, 6.5f, COLLAPSABLE_ROCK),
    GRAVEL(BlockRockFallable::new, 2.0f, VERTICAL_AND_HORIZONTAL),
    SAND(BlockRockFallable::new, 0.5f, VERTICAL_AND_HORIZONTAL),
    ANVIL(BlockRockAnvil::new, 2.0f, COLLAPSABLE_ROCK),
    PRESSURE_PLATE(BlockRockPressurePlate::new, 4f, null),
    BUTTON(BlockRockButton::new, 4f, null),
    LOOSE(BlockRockLoose::new, 4f, null),
    SPELEOTHEM(BlockRockSpeleothem::new, 4f, null);


    public static final RockVariant[] VALUES = RockVariant.values();
    public final BiFunction<RockVariant, Rock, IRockBlock> blockFactory;
    private final float hardnessBase;
    @Nullable
    private final Specification fallingSpecification;

    RockVariant(BiFunction<RockVariant, Rock, IRockBlock> blockFactory, float hardnessBase, @Nullable Specification fallingSpecification) {
        this.blockFactory = blockFactory;
        this.hardnessBase = hardnessBase;
        this.fallingSpecification = fallingSpecification;
    }

    public static RockVariant valueOf(int i) {
        return i >= 0 && i < VALUES.length ? VALUES[i] : RAW;
    }

    public IRockBlock create(Rock rock) {
        return this.blockFactory.apply(this, rock);
    }

    public float getHardnessBase() {
        return hardnessBase;
    }

    @Nullable
    public Specification getFallingSpecification() {
        return fallingSpecification;
    }

    public boolean canFall() {
        return fallingSpecification != null;
    }


    /**
     * Возвращает имя перечисления в нижнем регистре.
     */
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
