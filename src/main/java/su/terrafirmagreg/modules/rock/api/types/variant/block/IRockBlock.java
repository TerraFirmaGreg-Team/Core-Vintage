package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.api.spi.block.ISettingsBlock;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий блок породы.
 */
public interface IRockBlock extends IType<RockType>, IVariant<RockBlockVariant>, ISettingsBlock {

    /**
     * Возвращает окончательную твердость блока породы.
     *
     * @return Окончательная твердость блока породы.
     */
    default float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return this.getVariant().getBaseHardness() + this.getType().getRockCategory().getHardnessModifier();
    }

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @NotNull
    default String getName() {
        return String.format("rock/%s/%s", this.getVariant(), this.getType());
    }

    @Override
    default void onRegisterOreDict() {
        OreDictUtils.register((Block) this, getVariant());
    }

}
