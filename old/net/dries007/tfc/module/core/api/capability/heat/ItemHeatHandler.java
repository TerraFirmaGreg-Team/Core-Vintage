package net.dries007.tfc.module.core.api.capability.heat;

import net.dries007.tfc.util.calendar.CalendarTFC;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Это реализация интерфейса ItemHeat, которая автоматически охлаждается со временем,
 * рекомендуется наследоваться от этого класса или использовать его, вместо того чтобы реализовывать IHeatCapability напрямую
 * Исключение - если вы хотите наследоваться от другого объекта Capability (см. SmallVessel), но вы все равно должны реализовать эту функциональность где-то
 */
public class ItemHeatHandler implements ICapabilitySerializable<NBTTagCompound>, IItemHeat {
    // Это "константы". Некоторые реализации могут захотеть изменить их на основе других факторов. (См. ItemMold)
    protected float heatCapacity;
    protected float meltTemp;

    // Это значения из последнего обновления. Они обновляются при чтении из NBT или при установке температуры вручную.
    // Обратите внимание, что если температура == 0, lastUpdateTick должен установить себя в -1, 
    // чтобы сохранить совместимость Capability - т.е. возможность складывания.
    protected float temperature;
    protected long lastUpdateTick;

    /**
     * Реализация по умолчанию класса ItemHeatHandler
     *
     * @param nbt          NBT элемента. (Передается в Item#initCapabilities())
     * @param heatCapacity Теплоемкость
     * @param meltTemp     Температура плавления
     */
    public ItemHeatHandler(@Nullable NBTTagCompound nbt, float heatCapacity, float meltTemp) {
        this.heatCapacity = heatCapacity;
        this.meltTemp = meltTemp;

        deserializeNBT(nbt);
    }

    public ItemHeatHandler() {
    } // Здесь находится кастомная реализация

    /**
     * Возвращает текущую температуру, отображаемую наружу. Она может отличаться от внутреннего значения температуры или значения, сохраненного в NBT.
     * Примечание: если вы проверяете температуру внутри, НЕ используйте temperature, используйте этот метод, так как temperature не представляет текущую температуру.
     *
     * @return Текущая температура
     */
    @Override
    public float getTemperature() {
        return CapabilityItemHeat.adjustTemp(temperature, heatCapacity, CalendarTFC.PLAYER_TIME.getTicks() - lastUpdateTick);
    }

    /**
     * Обновляет температуру и сохраняет метку времени последнего обновления
     *
     * @param temperature Температура для установки. Между 0 и 1600
     */
    @Override
    public void setTemperature(float temperature) {
        this.temperature = temperature;
        this.lastUpdateTick = CalendarTFC.PLAYER_TIME.getTicks();
    }

    /**
     * Возвращает теплоемкость объекта
     *
     * @return Теплоемкость
     */
    @Override
    public float getHeatCapacity() {
        return heatCapacity;
    }

    /**
     * Возвращает температуру плавления объекта
     *
     * @return Температура плавления
     */
    @Override
    public float getMeltTemp() {
        return meltTemp;
    }

    /**
     * Проверяет, находится ли объект в расплавленном состоянии
     *
     * @return true, если объект находится в расплавленном состоянии; false в противном случае
     */
    @Override
    public boolean isMolten() {
        return getTemperature() >= meltTemp;
    }

    /**
     * Проверяет, имеет ли объект указанную возможность
     *
     * @param capability Возможность для проверки
     * @param facing     Направление, в котором возможность проверяется
     * @return true, если объект имеет указанную возможность; false в противном случае
     */
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHeat.ITEM_HEAT_CAPABILITY;
    }

    /**
     * Возвращает указанную возможность объекта
     *
     * @param capability Возможность для получения
     * @param facing     Направление, в котором возможность запрашивается
     * @param <T>        Тип Capability
     * @return Возможность объекта, если объект имеет указанную возможность; null в противном случае
     */
    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return hasCapability(capability, facing) ? (T) this : null;
    }

    /**
     * Сериализует объект в NBTTagCompound
     *
     * @return NBTTagCompound, представляющий объект
     */
    @Override
    @Nonnull
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        if (getTemperature() <= 0) {
            // Сбросить температуру на ноль
            nbt.setLong("ticks", -1);
            nbt.setFloat("heat", 0);
        } else {
            // Сериализация существующих значений - это намеренно лениво (и не использует результат getTemperature())
            // Почему? Чтобы мы не обновляли сериализацию без необходимости. Важно для предотвращения ненужной синхронизации с клиентом.
            nbt.setLong("ticks", lastUpdateTick);
            nbt.setFloat("heat", temperature);
        }
        return nbt;
    }

    /**
     * Десериализует объект из NBTTagCompound
     *
     * @param nbt NBTTagCompound для десериализации
     */
    @Override
    public void deserializeNBT(@Nullable NBTTagCompound nbt) {
        if (nbt != null) {
            temperature = nbt.getFloat("heat");
            lastUpdateTick = nbt.getLong("ticks");
        }
    }
}
