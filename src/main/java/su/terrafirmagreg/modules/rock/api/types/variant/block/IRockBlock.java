package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.modules.rock.api.types.type.IRockType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий блок породы.
 */
public interface IRockBlock extends IRockType, IAutoReg {

    /**
     * Возвращает вариант блока.
     *
     * @return Вариант блока.
     */
    @NotNull
    RockBlockVariant getBlockVariant();

    /**
     * Возвращает окончательную твердость блока породы.
     *
     * @return Окончательная твердость блока породы.
     */
    default float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return this.getBlockVariant().getBaseHardness() + this.getType().getRockCategory().getHardnessModifier();
    }

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @NotNull
    default String getName() {
        return String.format("rock/%s/%s", this.getBlockVariant(), this.getType());
    }

}
