package su.terrafirmagreg.modules.wood.api.types.variant.block;


import net.dries007.tfc.api.capability.size.IItemSize;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.models.ICustomModel;
import su.terrafirmagreg.api.models.ICustomStateMapper;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.types.type.IWoodType;


/**
 * Интерфейс IWoodBlock представляет деревянный блок.
 */
public interface IWoodBlock extends IWoodType, IAutoReg, IItemSize, ICustomModel, ICustomStateMapper {

	/**
	 * Возвращает вариант деревянного блока.
	 *
	 * @return вариант деревянного блока
	 */
	WoodBlockVariant getBlockVariant();

	/**
	 * Возвращает расположение в реестре для данного деревянного блока.
	 *
	 * @return расположение в реестре
	 */
	@NotNull
	default String getName() {
		return String.format("wood/%s/%s", getBlockVariant(), getType());
	}

	/**
	 * Возвращает расположение ресурса для данного деревянного блока.
	 *
	 * @return расположение ресурса
	 */
	@NotNull
	default ResourceLocation getResourceLocation() {
		return ModUtils.getID(String.format("wood/%s", getBlockVariant()));
	}
}
