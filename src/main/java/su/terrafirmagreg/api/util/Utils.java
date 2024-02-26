package su.terrafirmagreg.api.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Utils {

	private Utils() {
		throw new IllegalAccessError("Utility class");
	}

	/**
	 * Устанавливает цвет дерева.
	 *
	 * @param color цвет в формате RGB
	 */
	public static void setWoodColor(int color) {
		float red = ((color >> 16) & 0xFF) / 255.0F;
		float green = ((color >> 8) & 0xFF) / 255.0F;
		float blue = (color & 0xFF) / 255.0F;
		GlStateManager.color(red, green, blue, 1.0F);
	}


	/**
	 * Возвращает экземпляр типизированного объекта TileEntity по его классу.
	 *
	 * @param world  игровой мир
	 * @param pos    позиция блока
	 * @param aClass класс типизированного объекта TileEntity
	 * @param <T>    тип типизированного объекта TileEntity
	 * @return экземпляр типизированного объекта TileEntity
	 */
	@SuppressWarnings("unchecked")
	public static <T extends TileEntity> T getTE(IBlockAccess world, BlockPos pos, Class<T> aClass) {
		TileEntity te = world.getTileEntity(pos);
		if (!aClass.isInstance(te)) return null;
		return (T) te;
	}

	/**
	 * Возвращает экземпляр типизированного объекта Entity по его классу.
	 *
	 * @param world  игровой мир
	 * @param pos    позиция блока
	 * @param aClass класс типизированного объекта Entity
	 * @param <T>    тип типизированного объекта Entity
	 * @return экземпляр типизированного объекта Entity
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Entity> T getEntity(World world, BlockPos pos, Class<T> aClass) {
		Entity entity = world.getEntityByID(pos.getX());
		if (!aClass.isInstance(entity)) return null;
		return (T) entity;
	}

}
