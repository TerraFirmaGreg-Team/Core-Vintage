package net.dries007.tfc.api.types.soil;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс, представляющий блок почвы.
 */
public interface ISoilBlock extends IHasModel, IItemProvider {
    /**
     * Возвращает вариант блока почвы.
     *
     * @return Вариант блока почвы.
     */
    @Nonnull
    SoilBlockVariant getBlockVariant();

    /**
     * Возвращает тип почвы.
     *
     * @return Тип почвы.
     */
    @Nonnull
    SoilType getType();

    /**
     * Возвращает местоположение регистрации блока почвы.
     *
     * @return Местоположение регистрации блока почвы.
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.identifier(String.format("soil/%s/%s", getBlockVariant(), getType()));
    }

    /**
     * Возвращает местоположение ресурса блока почвы.
     *
     * @return Местоположение ресурса блока почвы.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.identifier(String.format("soil/%s", getBlockVariant()));
    }

    /**
     * Возвращает имя перевода блока почвы.
     *
     * @return Имя перевода блока почвы.
     */
    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
