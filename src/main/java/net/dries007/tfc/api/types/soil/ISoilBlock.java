package net.dries007.tfc.api.types.soil;

import net.dries007.tfc.api.types.soil.type.SoilType;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

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
    SoilBlockVariant getSoilBlockVariant();

    /**
     * Возвращает тип почвы.
     *
     * @return Тип почвы.
     */
    @Nonnull
    SoilType getSoilType();

    /**
     * Возвращает местоположение регистрации блока почвы.
     *
     * @return Местоположение регистрации блока почвы.
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return new ResourceLocation(MOD_ID, String.format("soil/%s/%s", getSoilBlockVariant(), getSoilType()));
    }

    /**
     * Возвращает местоположение ресурса блока почвы.
     *
     * @return Местоположение ресурса блока почвы.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(MOD_ID, String.format("soil/%s", getSoilBlockVariant()));
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
