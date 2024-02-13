package su.terrafirmagreg.modules.wood.api.types.variant.item;

import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IAutoRegistry;
import su.terrafirmagreg.api.registry.IHasModel;
import su.terrafirmagreg.api.spi.item.IColorfulItem;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.modules.wood.api.types.type.IWoodType;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface IWoodItem extends IWoodType, IAutoRegistry, IItemSize, IHasModel, IColorfulItem {

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
        return Helpers.getID(String.format("wood/%s", getItemVariant()));
    }
}
