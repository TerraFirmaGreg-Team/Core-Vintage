package net.dries007.tfc.module.rock.api.variant.item;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.rock.api.category.RockCategory;
import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IRockItem extends IHasModel {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
     */
    @Nonnull
    RockItemVariant getItemVariant();

    /**
     * Возвращает тип породы.
     *
     * @return Тип породы.
     */
    @Nonnull
    RockType getType();

    /**
     * Возвращает категорию породы.
     *
     * @return Категория породы.
     */
    @Nonnull
    default RockCategory getRockCategory() {
        return getType().getCategory();
    }

    /**
     * Возвращает расположение в реестре для данного подтипа деревянного предмета.
     *
     * @return расположение в реестре
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.identifier(String.format("rock/%s/%s", getItemVariant(), getType()));
    }

    /**
     * Возвращает расположение ресурса для данного подтипа деревянного предмета.
     *
     * @return расположение ресурса
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.identifier(String.format("rock/%s/%s", getItemVariant(), getType()));
    }

    /**
     * Возвращает локализованное имя для данного подтипа деревянного предмета.
     *
     * @return локализованное имя
     */
    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase().replace(":", ".").replace("/", ".");
    }
}
