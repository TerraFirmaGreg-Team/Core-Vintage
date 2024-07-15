package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.api.registry.provider.IItemColorProvider;
import su.terrafirmagreg.api.registry.provider.IModelProvider;
import su.terrafirmagreg.api.spi.item.IItemSettings;
import su.terrafirmagreg.api.spi.types.type.IType;
import su.terrafirmagreg.api.spi.types.variant.IVariant;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.util.ResourceLocation;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface IWoodItem extends IType<WoodType>, IVariant<WoodItemVariant>, IItemSettings, IModelProvider, IItemColorProvider {

    /**
     * Возвращает расположение в реестре для данного подтипа предмета.
     *
     * @return Расположение в реестре
     */
    @NotNull
    default String getRegistryKey() {
        return String.format("wood/%s/%s", getVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @return Расположение ресурса
     */
    default ResourceLocation getResourceLocation() {
        return ModUtils.resource(String.format("wood/%s", getVariant()));
    }
}
