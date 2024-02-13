package su.terrafirmagreg.api.util;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static su.terrafirmagreg.Tags.MOD_ID;

public class Helpers {

    /**
     * Возвращает идентификатор ресурса на основе строки.
     *
     * @param string строка идентификатора ресурса
     * @return идентификатор ресурса
     */
    public static ResourceLocation getID(String string) {
        return new ResourceLocation(MOD_ID, string);
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
     * Проверяет, включен ли мод JEI (Just Enough Items).
     *
     * @return true, если мод JEI включен; в противном случае - false
     */
    public static boolean isJEIEnabled() {
        return Loader.isModLoaded("jei");
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


    /**
     * Используется, поскольку {@link Collections#singletonList(Object)} является неизменяемым.
     */
    public static <T> List<T> listOf(T element) {
        List<T> list = new ArrayList<>(1);
        list.add(element);
        return list;
    }

    /**
     * Используется, поскольку {@link Arrays#asList(Object[])} является неизменяемым.
     */
    @SafeVarargs
    public static <T> List<T> listOf(T... elements) {
        List<T> list = new ArrayList<>(elements.length);
        Collections.addAll(list, elements);
        return list;
    }

}
