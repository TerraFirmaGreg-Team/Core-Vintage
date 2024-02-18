package su.terrafirmagreg.modules.rock.api.types.variant.item;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.modules.rock.api.types.type.IRockType;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IRockItem extends IRockType, IAutoReg, IItemSize {

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
