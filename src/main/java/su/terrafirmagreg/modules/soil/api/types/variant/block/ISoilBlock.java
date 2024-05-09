package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.block.Block;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий блок почвы.
 */
public interface ISoilBlock extends IType<SoilType>, IVariant<SoilBlockVariant>, IAutoReg {

    /**
     * Возвращает местоположение регистрации блока почвы.
     *
     * @return Местоположение регистрации блока почвы.
     */
    @NotNull
    @Override
    default String getName() {
        return String.format("soil/%s/%s", getVariant(), getType());
    }

    @Override
    default void onRegisterOreDict() {
        OreDictUtils.register((Block) this, getVariant());
    }
}
