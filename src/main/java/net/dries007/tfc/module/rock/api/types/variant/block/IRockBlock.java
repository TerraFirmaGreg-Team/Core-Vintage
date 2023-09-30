package net.dries007.tfc.module.rock.api.types.variant.block;

import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.rock.api.types.category.RockCategory;
import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Интерфейс, представляющий блок породы.
 */
public interface IRockBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает вариант блока.
     *
     * @return Вариант блока.
     */
    @Nonnull
    RockBlockVariant getBlockVariant();

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
     * Возвращает окончательную твердость блока породы.
     *
     * @return Окончательная твердость блока породы.
     */
    default float getFinalHardness() {
        return getBlockVariant().getBaseHardness() + getCategory().getHardnessModifier();
    }

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @Nonnull
    default String getName() {
        return String.format("rock.%s.%s", getBlockVariant(), getType()).replaceAll("/", ".");
    }

    /**
     * Возвращает расположение в реестре для данного подтипа блока.
     *
     * @return Расположение в реестре.
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return Helpers.getID(String.format("rock.%s.%s", getBlockVariant(), getType()));
    }

    /**
     * Возвращает местоположение ресурса блока породы.
     *
     * @return Расположение ресурса.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return Helpers.getID(String.format("rock/%s", getBlockVariant()));
    }

    @Nonnull
    default String getTranslationName() {
        return String.format(getBlockVariant().getLocalizedName(), getType().getLocalizedName());
    }
}
