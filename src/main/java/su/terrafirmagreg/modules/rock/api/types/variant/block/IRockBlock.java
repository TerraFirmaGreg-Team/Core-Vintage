package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.api.spi.block.IBlockSettings;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий блок породы.
 */
public interface IRockBlock extends IType<RockType>, IVariant<RockBlockVariant>, IBlockSettings {

    /**
     * Возвращает окончательную твердость блока породы.
     *
     * @return Окончательная твердость блока породы.
     */
    default float getBlockHardness(@NotNull IBlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos) {
        return getVariant().getBaseHardness() + getType().getRockCategory().getHardnessModifier();
    }

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @NotNull
    @Override
    default String getRegistryKey() {
        return String.format("rock/%s/%s", getVariant(), getType());
    }
}
