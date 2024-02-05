package su.terrafirmagreg.modules.rock.api.types.variant.block;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IAutoRegistry;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.api.util.IHasModel;
import su.terrafirmagreg.modules.rock.api.types.type.IRockType;

/**
 * Интерфейс, представляющий блок породы.
 */
public interface IRockBlock extends IRockType, IAutoRegistry, IItemSize {

    /**
     * Возвращает вариант блока.
     *
     * @return Вариант блока.
     */
    @NotNull
    RockBlockVariant getBlockVariant();

    /**
     * Возвращает окончательную твердость блока породы.
     *
     * @return Окончательная твердость блока породы.
     */
    default float getFinalHardness() {
        return getBlockVariant().getBaseHardness() + getType().getRockCategory().getHardnessModifier();
    }

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @NotNull
    default String getName() {
        return String.format("rock/%s/%s", getBlockVariant(), getType());
    }

}
