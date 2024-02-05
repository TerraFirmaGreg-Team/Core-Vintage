package su.terrafirmagreg.modules.rock.api.types.variant.item;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IAutoRegistry;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.api.util.IHasModel;
import su.terrafirmagreg.modules.rock.api.types.type.IRockType;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IRockItem extends IRockType, IAutoRegistry, IItemSize {

    /**
     * Возвращает вариант предмета.
     *
     * @return Вариант предмета.
     */
    @NotNull
    RockItemVariant getItemVariant();

    /**
     * Возвращает имя объекта.
     *
     * @return Имя объекта.
     */
    @NotNull
    default String getName() {
        return String.format("rock/%s/%s", getItemVariant(), getType());
    }

}
