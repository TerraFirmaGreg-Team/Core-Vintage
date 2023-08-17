package net.dries007.tfc.api.types.crop;

import net.dries007.tfc.common.objects.blocks.agriculture.crop_old.BlockCropTFC;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Это "модель", которая используется классом {@link BlockCropTFC}.
 * В оригинальном TFC они все используются как экземпляры перечисления Crop.
 */
public interface ICrop {
    /**
     * @return минимальное время, которое требуется для роста одной стадии урожая (в тиках)
     */
    long getGrowthTicks();

    /**
     * @return максимальная стадия роста (когда текущая стадия равна максимальной, урожай полностью вырос)
     */
    int getMaxStage();

    /**
     * Проверяет, являются ли условия подходящими для генерации в мире / жизни
     *
     * @param temperature температура, от -30 до +30
     * @param rainfall    осадки, от 0 до 500
     * @return true, если урожай должен появиться здесь
     */
    boolean isValidConditions(float temperature, float rainfall);

    /**
     * Более строгая версия предыдущей проверки. Позволяет урожаю расти (переходить на следующую стадию)
     *
     * @param temperature температура, от -30 до +30
     * @param rainfall    осадки, от 0 до 500
     * @return true, если урожай может расти (false не означает смерть)
     */
    boolean isValidForGrowth(float temperature, float rainfall);

    /**
     * Получить предмет с едой, выпадающий при сборе / срыве урожая
     *
     * @param growthStage текущая стадия роста
     */
    ItemStack getFoodDrop(int growthStage);

    /**
     * Добавить информацию в подсказку
     */
    @SideOnly(Side.CLIENT)
    default void addInfo(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    }
}
