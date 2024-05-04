package su.terrafirmagreg.modules.wood.api.types.variant.item;

import net.minecraft.util.ResourceLocation;


import org.jetbrains.annotations.NotNull;


import su.terrafirmagreg.api.model.ICustomModel;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.item.IColorfulItem;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.types.type.IWoodType;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface IWoodItem extends IWoodType, IAutoReg, ICustomModel, IColorfulItem {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @NotNull
    WoodItemVariant getItemVariant();

    /**
     * Возвращает расположение в реестре для данного подтипа предмета.
     *
     * @return Расположение в реестре
     */
    @NotNull
    default String getName() {
        return String.format("wood/%s/%s", getItemVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @return Расположение ресурса
     */
    @NotNull
    default ResourceLocation getResourceLocation() {
        return ModUtils.id(String.format("wood/%s", getItemVariant()));
    }
}
