package net.dries007.tfc.module.metal.api.types.variant.block;

import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.metal.api.types.type.MetalType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Интерфейс, представляющий блок металла.
 */
public interface IMetalBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает вариант металлического блока.
     *
     * @return Вариант металлического блока.
     */
    MetalBlockVariant getBlockVariant();

    /**
     * Возвращает материал металлического блока.
     *
     * @return Материал металлического блока или null, если материал не определен.
     */
    @Nullable
    MetalType getType();


    /**
     * Возвращает местоположение регистрации блока почвы.
     *
     * @return Местоположение регистрации блока почвы.
     */
    @Nonnull
    default String getName() {
        return String.format("metal.%s.%s", getBlockVariant(), getType());
    }

    /**
     * Возвращает местоположение ресурса блока почвы.
     *
     * @return Местоположение ресурса блока почвы.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("metal/%s", getBlockVariant()));
    }
}
