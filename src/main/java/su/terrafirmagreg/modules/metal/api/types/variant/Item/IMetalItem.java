package su.terrafirmagreg.modules.metal.api.types.variant.Item;

import gregtech.api.unification.material.Material;
import net.dries007.tfc.api.capability.size.IItemSize;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.modules.metal.api.types.type.IMetalType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Интерфейс, представляющий предмет породы.
 */
public interface IMetalItem extends IMetalType, IItemSize, IAutoReg {

	/**
	 * Возвращает вариант блока породы.
	 *
	 * @return Вариант блока породы.
	 */
	@Nonnull
	MetalItemVariant getItemVariant();

	/**
	 * Возвращает тип породы.
	 *
	 * @return Тип породы.
	 */
	@Nullable
	Material getMaterial();


	/**
	 * Возвращает расположение в реестре для данного подтипа предмета.
	 *
	 * @return Расположение в реестре
	 */
	@Nonnull
	default String getName() {
		return String.format("metal.%s.%s", getItemVariant(), getMaterial());
	}
	
}
