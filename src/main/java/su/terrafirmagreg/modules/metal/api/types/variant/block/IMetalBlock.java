package su.terrafirmagreg.modules.metal.api.types.variant.block;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.model.ICustomModel;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.block.IStateMapperProvider;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий блок металла.
 */
public interface IMetalBlock extends IType<MetalType>, IVariant<MetalBlockVariant>, IAutoReg, ICustomModel, IStateMapperProvider {

    /**
     * Возвращает местоположение регистрации блока почвы.
     *
     * @return Местоположение регистрации блока почвы.
     */
    @NotNull
    default String getName() {
        return String.format("metal/%s/%s", getVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного деревянного блока.
     *
     * @return расположение ресурса
     */
    @NotNull
    default ResourceLocation getResourceLocation() {
        return ModUtils.id(String.format("metal/%s", getVariant()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    default void onModelRegister() {
        ModelUtils.registerBlockInventoryModel((Block) this, getResourceLocation());
    }

    @Override
    @SideOnly(Side.CLIENT)
    default void onRegisterState() {
        ModelUtils.registerStateMapper((Block) this, new CustomStateMap.Builder().customResource(getResourceLocation()).build());
    }

}
