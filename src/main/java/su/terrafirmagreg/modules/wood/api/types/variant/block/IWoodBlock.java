package su.terrafirmagreg.modules.wood.api.types.variant.block;

import su.terrafirmagreg.api.client.model.CustomStateMap;
import su.terrafirmagreg.api.registry.provider.IBlockColorProvider;
import su.terrafirmagreg.api.registry.provider.IModelProvider;
import su.terrafirmagreg.api.spi.block.IBlockSettings;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс IWoodBlock представляет деревянный блок.
 */
public interface IWoodBlock extends IType<WoodType>, IVariant<WoodBlockVariant>, IBlockSettings, IModelProvider, IBlockColorProvider {

    /**
     * Возвращает расположение в реестре для данного деревянного блока.
     *
     * @return расположение в реестре
     */
    @NotNull
    default String getRegistryKey() {
        return String.format("wood/%s/%s", getVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного деревянного блока.
     *
     * @return расположение ресурса
     */
    default ResourceLocation getResourceLocation() {
        return ModUtils.id(String.format("wood/%s", getVariant()));
    }

    @Override
    default IBlockColor getBlockColor() {
        return (s, w, p, i) -> this.getType().getColor();
    }

    @Override
    default IItemColor getItemColor() {
        return (s, i) -> this.getType().getColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    default IStateMapper getStateMapper() {
        return new CustomStateMap.Builder().customResource(getResourceLocation()).build();
    }
}
