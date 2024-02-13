package su.terrafirmagreg.modules.wood.api.types.variant.block;


import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IAutoRegistry;
import su.terrafirmagreg.api.registry.IHasModel;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.modules.wood.api.types.type.IWoodType;


/**
 * Интерфейс IWoodBlock представляет деревянный блок.
 */
public interface IWoodBlock extends IWoodType, IAutoRegistry, IItemSize, IHasModel {

    /**
     * Возвращает вариант деревянного блока.
     *
     * @return вариант деревянного блока
     */
    WoodBlockVariant getBlockVariant();

    /**
     * Возвращает расположение в реестре для данного деревянного блока.
     *
     * @return расположение в реестре
     */
    @NotNull
    default String getName() {
        return String.format("wood/%s/%s", getBlockVariant(), getType());
    }

    /**
     * Возвращает расположение ресурса для данного деревянного блока.
     *
     * @return расположение ресурса
     */
    @NotNull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("wood/%s", getBlockVariant()));
    }
}
