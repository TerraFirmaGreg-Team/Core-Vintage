package net.dries007.tfc.api.types.bush;

import net.dries007.tfc.api.types.bush.type.BushType;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

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
        return new ResourceLocation(MOD_ID, String.format("berry_bush/%s", getType()));
    }

    /**
     * Возвращает местоположение ресурса блока.
     *
     * @return Местоположение ресурса блока.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(MOD_ID, String.format("berry_bush/%s", getType()));
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
