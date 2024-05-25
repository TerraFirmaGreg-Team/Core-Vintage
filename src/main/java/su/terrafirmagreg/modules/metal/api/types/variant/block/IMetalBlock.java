package su.terrafirmagreg.modules.metal.api.types.variant.block;

import su.terrafirmagreg.api.client.model.CustomStateMap;
import su.terrafirmagreg.api.registry.provider.IModelProvider;
import su.terrafirmagreg.api.spi.block.IBlockSettings;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;

import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс, представляющий блок металла.
 */
public interface IMetalBlock extends IType<MetalType>, IVariant<MetalBlockVariant>, IBlockSettings, IModelProvider {

    /**
     * Возвращает местоположение регистрации блока почвы.
     *
     * @return Местоположение регистрации блока почвы.
     */
    @NotNull
    default String getRegistryKey() {
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
    default IStateMapper getStateMapper() {
        return new CustomStateMap.Builder().customResource(getResourceLocation()).build();
    }
}
