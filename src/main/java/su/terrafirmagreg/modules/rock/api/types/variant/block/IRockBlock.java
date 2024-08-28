package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import org.jetbrains.annotations.NotNull;

public interface IRockBlock extends IType<RockType>, IVariant<RockBlockVariant>, IBlockSettings {

    default float getBlockHardness(@NotNull IBlockState blockState, @NotNull World worldIn, @NotNull BlockPos pos) {
        return getVariant().getBaseHardness() + getType().getRockCategory().getHardnessModifier();
    }

    @NotNull
    @Override
    default String getRegistryKey() {
        return String.format("rock/%s/%s", getVariant(), getType());
    }
}
