package net.dries007.tfc.module.core.api.capability.size;

import net.dries007.tfc.module.core.api.objects.block.itemblocks.ItemBlockBase;
import net.dries007.tfc.module.core.api.objects.item.ItemBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Интерфейс для определения размера предмета.
 * Чтобы реализовать этот интерфейс, вы можете (предпочтительно) реализовать его в классе Item / Block и возвращать размер.
 * Вы также можете предоставить эту возможность через Item#initCapabilities().
 * Примечание: если вы реализуете это через интерфейс,
 * вы также должны изменить максимальный размер стека предмета в соответствии с методом {@link IItemSizeAndWeight#getStackSize}.
 * Если вы реализуете эту возможность, TFC попытается автоматически настроить максимальный размер стека предмета для вас.
 * В противном случае вашему предмету будет назначена стандартная возможность при создании.
 *
 * @see ItemBase
 * @see ItemBlockBase
 */
public interface IItemSizeAndWeight {
    /**
     * Получает размер предмета.
     *
     * @param stack предмет
     * @return размер предмета
     */
    @Nonnull
    Size getSize(@Nonnull ItemStack stack);

    /**
     * Получает вес предмета.
     *
     * @param stack предмет
     * @return вес предмета
     */
    @Nonnull
    Weight getWeight(@Nonnull ItemStack stack);

    /**
     * Проверяет, может ли предмет быть стакнут.
     *
     * @param stack предмет
     * @return true, если предмет может быть стакнут, иначе false
     */
    default boolean canStack(@Nonnull ItemStack stack) {
        return true;
    }

    /**
     * Добавляет информацию о размере предмета в список текста.
     *
     * @param stack предмет
     * @param text  список текста
     */
    @SideOnly(Side.CLIENT)
    default void addSizeInfo(@Nonnull ItemStack stack, @Nonnull List<String> text) {
        text.add(1, "\u2696 " + getWeight(stack).getLocalizedName() + " \u21F2 " + getSize(stack).getLocalizedName());
    }

    /**
     * Возвращает максимальный размер стека предмета.
     * Должен быть вызван из метода {@link Item#getItemStackLimit(ItemStack)}.
     *
     * @param stack предмет
     * @return максимальный размер стека предмета
     */
    default int getStackSize(@Nonnull ItemStack stack) {
        return canStack(stack) ? getWeight(stack).getStackSize() : 1;
    }
}
