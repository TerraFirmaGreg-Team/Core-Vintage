package su.terrafirmagreg.modules.wood.api.types.variant.item;

import su.terrafirmagreg.api.model.ICustomModel;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.item.IColorfulItem;
import su.terrafirmagreg.api.spi.types.IType;
import su.terrafirmagreg.api.spi.types.IVariant;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.util.ResourceLocation;


import org.jetbrains.annotations.NotNull;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface IWoodItem extends IType<WoodType>, IVariant<WoodItemVariant>, IAutoReg, ICustomModel, IColorfulItem {

    /**
     * Возвращает расположение в реестре для данного подтипа предмета.
     *
     * @return Расположение в реестре
     */
    @NotNull
    default String getName() {
        return String.format("wood/%s/%s", getVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @return Расположение ресурса
     */
    @NotNull
    default ResourceLocation getResourceLocation() {
        return ModUtils.id(String.format("wood/%s", getVariant()));
    }
}
