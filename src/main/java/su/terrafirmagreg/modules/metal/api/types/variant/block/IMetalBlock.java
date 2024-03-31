package su.terrafirmagreg.modules.metal.api.types.variant.block;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.model.ICustomModel;
import su.terrafirmagreg.api.model.ICustomStateMapper;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.modules.metal.api.types.type.IMetalType;

import javax.annotation.Nonnull;

/**
 * Интерфейс, представляющий блок металла.
 */
public interface IMetalBlock extends IMetalType, IAutoReg, IItemSize, ICustomModel, ICustomStateMapper {

	/**
	 * Возвращает вариант металлического блока.
	 *
	 * @return Вариант металлического блока.
	 */
	MetalBlockVariant getBlockVariant();


	/**
	 * Возвращает местоположение регистрации блока почвы.
	 *
	 * @return Местоположение регистрации блока почвы.
	 */
	@Nonnull
	default String getName() {
		return String.format("metal/%s/%s", getBlockVariant(), getType());
	}

	/**
	 * Возвращает расположение ресурса для данного деревянного блока.
	 *
	 * @return расположение ресурса
	 */
	@NotNull
	default ResourceLocation getResourceLocation() {
		return ModUtils.getID(String.format("metal/%s", getBlockVariant()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	default void onModelRegister() {
		ModelUtils.registerBlockInventoryModel((Block) this, getResourceLocation());
	}

	@Override
	@SideOnly(Side.CLIENT)
	default void onStateMapperRegister() {
		ModelUtils.registerStateMapper((Block) this, new CustomStateMap.Builder().customResource(getResourceLocation()).build());
	}

}
