package net.dries007.tfc.module.wood.plugin.dynamictrees;

import com.ferreusveritas.dynamictrees.api.seasons.ISeasonManager;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SeasonManager implements ISeasonManager {

    public SeasonManager () {}

    /**
     * Обновляет состояние сезона.
     *
     * @param world      доступ к миру
     * @param worldTicks количество тиков в мире
     */
    @Override
    public void updateTick(World world, long worldTicks) {}

    /**
     * Сбрасывает все отображения.
     */
    @Override
    public void flushMappings() {}

    /**
     * Возвращает коэффициент роста для указанного месяца.
     *
     * @param world   доступ к миру
     * @param rootPos позиция корней растения
     * @param offset  смещение
     * @return коэффициент роста
     */
    @Override
    public float getGrowthFactor(World world, BlockPos rootPos, float offset) {
        return getMonthModifier(CalendarTFC.CALENDAR_TIME.getMonthOfYear());
    }

    /**
     * Возвращает коэффициент сброса семян для указанного месяца.
     *
     * @param world   доступ к миру
     * @param rootPos позиция корней растения
     * @param offset  смещение
     * @return коэффициент сброса семян
     */
    @Override
    public float getSeedDropFactor(World world, BlockPos rootPos, float offset) {
        return getMonthModifier(CalendarTFC.CALENDAR_TIME.getMonthOfYear()) + 0.05F;
    }

    /**
     * Возвращает коэффициент производства плодов для указанного месяца.
     *
     * @param world   доступ к миру
     * @param rootPos позиция корней растения
     * @param offset  смещение
     * @return коэффициент производства плодов
     */
    @Override
    public float getFruitProductionFactor(World world, BlockPos rootPos, float offset) {
        float mod = getMonthModifier(CalendarTFC.CALENDAR_TIME.getMonthOfYear());
        return mod < 0.25F ? 0 : mod; // не производить плоды, если это самая холодная часть года
    }

    /**
     * Возвращает значение сезона для указанной позиции.
     *
     * @param world    доступ к миру
     * @param blockPos позиция блока
     * @return значение сезона
     */
    @Override
    public Float getSeasonValue(World world, BlockPos blockPos) {
        return (float) Math.min(getMonthModifier(CalendarTFC.CALENDAR_TIME.getMonthOfYear().next().next().next()) * 4, 3.999);
    }

    /**
     * Проверяет, является ли указанная позиция тропической.
     *
     * @param world    доступ к миру
     * @param blockPos позиция блока
     * @return true, если позиция тропическая, иначе false
     */
    @Override
    public boolean isTropical(World world, BlockPos blockPos) {
        return ClimateTFC.getRainfall(world, blockPos) > 300 && ClimateTFC.getAvgTemp(world, blockPos) > 20;
    }

    /**
     * Проверяет, должен ли снег таять на указанной позиции.
     *
     * @param world    доступ к миру
     * @param blockPos позиция блока
     * @return true, если снег должен таять, иначе false
     */
    @Override
    public boolean shouldSnowMelt(World world, BlockPos blockPos) {
        return ClimateTFC.getDailyTemp(world, blockPos) > 0;
    }

    /**
     * Возвращает модификатор месяца.
     * Если обратить внимание, какие значения принимают месяцы в TFC, где максимальный модификатор температуры равен 66.5,
     * а минимальный 27, и что этот модификатор достигает своего максимума в самые холодные месяцы,
     * масштабирование его до значения от 0 до 1 и затем инвертирование позволяет обеспечить наибольший рост в самые теплые месяцы.
     *
     * @param month месяц
     * @return модификатор месяца
     */
    private float getMonthModifier(Month month) {
        return 1 - ((month.getTemperatureModifier() - 27.0F) / 66.5F);
    }
}
