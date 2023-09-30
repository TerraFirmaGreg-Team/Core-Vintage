package net.dries007.tfc.module.rock.api.types.variant.item;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.rock.api.types.category.RockCategory;
import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IRockItem extends IHasModel {

    /**
     * Возвращает вариант предмета.
     *
     * @return Вариант предмета.
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
    default RockCategory getCategory() {
        return getType().getCategory();
    }

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @Nonnull
    default String getName() {
        return String.format("rock.%s.%s", getItemVariant(), getType());
    }

    /**
     * Возвращает расположение в реестре для данного подтипа предмета.
     *
     * @return Расположение в реестре
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.getID(String.format("rock.%s.%s", getItemVariant(), getType()));
    }

    /**
     * Возвращает расположение ресурса для данного подтипа предмета.
     *
     * @return Расположение ресурса.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.getID(String.format("rock/%s/%s", getItemVariant(), getType()));
    }
}
