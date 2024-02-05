package su.terrafirmagreg.modules.soil.api.types.variant.item;

import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IAutoRegistry;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.api.util.IHasModel;
import su.terrafirmagreg.modules.soil.api.types.type.ISoilType;

/**
 * Интерфейс ICropItem представляет деревянный предмет.
 */
public interface ISoilItemVariant extends ISoilType, IItemSize, IAutoRegistry {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @NotNull
    SoilItemVariant getItemVariant();

    /**
     * Возвращает расположение в реестре для данного подтипа деревянного предмета.
     *
     * @return расположение в реестре
     */
    @NotNull
    default String getName() {
        return String.format("soil/%s/%s", getItemVariant(), getType());
    }
}
