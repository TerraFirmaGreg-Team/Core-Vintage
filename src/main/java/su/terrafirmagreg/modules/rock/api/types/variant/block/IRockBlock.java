package su.terrafirmagreg.modules.rock.api.types.variant.block;

import net.dries007.tfc.api.capability.size.IItemSize;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.modules.rock.api.types.type.IRockType;

/**
 * Интерфейс, представляющий блок породы.
 */
public interface IRockBlock extends IRockType, IAutoReg, IItemSize {

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
