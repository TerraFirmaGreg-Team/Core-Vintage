package su.terrafirmagreg.modules.wood.api.types.variant.block;


import net.dries007.tfc.api.capability.size.IItemSize;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.models.CustomStateMap;
import su.terrafirmagreg.api.models.ICustomModel;
import su.terrafirmagreg.api.models.ICustomStateMapper;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.api.registry.IAutoReg;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.api.types.type.IWoodType;


/**
 * Интерфейс IWoodBlock представляет деревянный блок.
 */
public interface IWoodBlock extends IWoodType, IAutoReg, IItemSize, ICustomModel, ICustomStateMapper, IColorfulBlock {

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

	@Override
	@SideOnly(Side.CLIENT)
	default void onModelRegister() {
		ModelManager.registerBlockInventoryModel((Block) this, getResourceLocation());
	}

	@Override
	@SideOnly(Side.CLIENT)
	default void onStateMapperRegister() {
		ModelManager.registerStateMapper((Block) this, new CustomStateMap.Builder().customResource(getResourceLocation()).build());
	}

	@Override
	default IBlockColor getColorHandler() {
		return (s, w, p, i) -> this.getType().getColor();
	}

	@Override
	default IItemColor getItemColorHandler() {
		return (s, i) -> this.getType().getColor();
	}
}
