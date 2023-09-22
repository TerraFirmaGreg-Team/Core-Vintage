package net.dries007.tfc.module.rock.api.variant.block;

import gregtech.api.util.LocalizationUtils;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.module.rock.api.category.RockCategory;
import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;

/**
 * Интерфейс, представляющий блок породы.
 */
public interface IRockBlock extends IHasModel, IItemProvider {

    /**
     * Возвращает вариант блока породы.
     *
     * @return Вариант блока породы.
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
        return String.format("rock.%s.%s", getBlockVariant(), getType());
    }

    /**
     * Возвращает расположение в реестре для данного подтипа блока.
     *
     * @return Расположение в реестре.
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return TerraFirmaCraft.getID(String.format("rock.%s.%s", getBlockVariant(), getType()));
    }

    /**
     * Возвращает местоположение ресурса блока породы.
     *
     * @return Расположение ресурса.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return TerraFirmaCraft.getID(String.format("rock/%s", getBlockVariant()));
    }

    @Nonnull
    default String getTranslationName() {
        return String.format(getBlockVariant().getLocalizedName(), getType().getLocalizedName());
    }
}
