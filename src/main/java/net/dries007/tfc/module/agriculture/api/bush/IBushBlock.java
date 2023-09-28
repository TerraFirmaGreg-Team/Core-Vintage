package net.dries007.tfc.module.agriculture.api.bush;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.agriculture.api.bush.type.BushType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public interface IBushBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает тип блока куста.
     *
     * @return Тип блока культуры.
     */
    @Nonnull
    BushType getType();


    /**
     * Возвращает местоположение регистрации блока.
     *
     * @return Местоположение регистрации блока.
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.getID(String.format("berry_bush/%s", getType()));
    }

    /**
     * Возвращает местоположение ресурса блока.
     *
     * @return Местоположение ресурса блока.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.getID(String.format("berry_bush/%s", getType()));
    }

    /**
     * Возвращает ключ перевода блока.
     *
     * @return Ключ перевода блока.
     */
    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase()
                .replace(":", ".")
                .replace("/", ".");
    }

    /**
     * Размеры куста.
     */
    enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }
}
