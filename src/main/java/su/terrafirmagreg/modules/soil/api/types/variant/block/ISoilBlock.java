package su.terrafirmagreg.modules.soil.api.types.variant.block;

import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.api.util.IHasModel;
import su.terrafirmagreg.api.util.IItemProvider;
import su.terrafirmagreg.modules.soil.api.types.type.ISoilType;

/**
 * Интерфейс, представляющий блок почвы.
 */
public interface ISoilBlock extends ISoilType, IHasModel, IItemProvider, IItemSize {

    /**
     * Возвращает вариант блока почвы.
     *
     * @return Вариант блока почвы.
     */
    @NotNull
    SoilBlockVariant getBlockVariant();

    /**
     * Возвращает местоположение регистрации блока почвы.
     *
     * @return Местоположение регистрации блока почвы.
     */
    @NotNull
    default String getName() {
        return String.format("soil.%s.%s", getBlockVariant(), getType()).replaceAll("/", ".");
    }

    /**
     * Возвращает местоположение ресурса блока почвы.
     *
     * @return Местоположение ресурса блока почвы.
     */
    @NotNull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("soil/%s", getBlockVariant()));
    }
}
