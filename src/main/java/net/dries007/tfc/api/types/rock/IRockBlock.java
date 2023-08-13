package net.dries007.tfc.api.types.rock;

import net.dries007.tfc.api.types.rock.category.RockCategory;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

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
    RockBlockVariant getRockBlockVariant();

    /**
     * Возвращает тип породы.
     *
     * @return Тип породы.
     */
    @Nonnull
    RockType getRockType();

    /**
     * Возвращает категорию породы.
     *
     * @return Категория породы.
     */
    @Nonnull
    default RockCategory getRockCategory() {
        return getRockType().getRockCategory();
    }

    /**
     * Возвращает местоположение регистрации блока породы.
     *
     * @return Местоположение регистрации блока породы.
     */
    @Nonnull
    default ResourceLocation getRegistryLocation() {
        return new ResourceLocation(MOD_ID, String.format("rock/%s/%s", getRockBlockVariant(), getRockType()));
    }

    /**
     * Возвращает местоположение ресурса блока породы.
     *
     * @return Местоположение ресурса блока породы.
     */
    @Nonnull
    default ResourceLocation getResourceLocation() {
        return new ResourceLocation(MOD_ID, String.format("rock/%s", getRockBlockVariant()));
    }

    /**
     * Возвращает имя перевода блока породы.
     *
     * @return Имя перевода блока породы.
     */
    @Nonnull
    default String getTranslationName() {
        return getRegistryLocation().toString().toLowerCase().replace(":", ".").replace("/", ".");
    }

    /**
     * Возвращает окончательную твердость блока породы.
     *
     * @return Окончательная твердость блока породы.
     */
    default float getFinalHardness() {
        return getRockBlockVariant().getBaseHardness() + getRockCategory().getHardnessModifier();
    }
}
