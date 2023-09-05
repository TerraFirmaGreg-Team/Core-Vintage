package net.dries007.tfc.api.types.tree;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.api.types.tree.variant.block.TreeBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;


/**
 * Интерфейс IWoodBlock представляет деревянный блок.
 */
public interface ITreeBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает вариант деревянного блока.
     *
     * @return вариант деревянного блока
     */
    TreeBlockVariant getBlockVariant();

    /**
     * Возвращает тип дерева блока.
     *
     * @return тип дерева
     */
    TreeType getType();

    /**
     * Возвращает расположение в реестре для данного деревянного блока.
     *
     * @return расположение в реестре
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.identifier(String.format("tree/%s/%s", getBlockVariant(), getType()));
    }

    /**
     * Возвращает расположение ресурса для данного деревянного блока.
     *
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.identifier(String.format("tree/%s", getBlockVariant()));
    }

    /**
     * Возвращает локализованное имя для данного деревянного блока.
     *
     * @return локализованное имя
     */
    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
