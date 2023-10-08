package net.dries007.tfc.module.core.api.capability.size;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;

/**
 * Класс ItemSizeHandler представляет собой реализацию интерфейсов ICapabilityProvider и IItemSizeAndWeight.
 * Он предоставляет функциональность, связанную с размером и весом предметов в моде Minecraft.
 */
public class ItemSizeHandler implements ICapabilityProvider, IItemSizeAndWeight {

    private static final EnumMap<Size, EnumMap<Weight, ItemSizeHandler[]>> CACHE = new EnumMap<>(Size.class);
    private final Size size;
    private final Weight weight;
    private final boolean canStack;

    /**
     * Конструктор класса ItemSizeHandler.
     *
     * @param size     Размер предмета.
     * @param weight   Вес предмета.
     * @param canStack Флаг, указывающий, может ли предмет быть складирован в стопках.
     */
    public ItemSizeHandler(Size size, Weight weight, boolean canStack) {
        this.size = size;
        this.weight = weight;
        this.canStack = canStack;
    }

    /**
     * Получает экземпляр ItemSizeHandler на основе указанного размера, веса и возможности складирования в стопках.
     *
     * @param size     Размер предмета.
     * @param weight   Вес предмета.
     * @param canStack Флаг, указывающий, может ли предмет быть складирован в стопках.
     * @return Экземпляр ItemSizeHandler.
     */
    public static ItemSizeHandler get(Size size, Weight weight, boolean canStack) {
        EnumMap<Weight, ItemSizeHandler[]> nested = CACHE.get(size);
        if (nested == null) {
            CACHE.put(size, nested = new EnumMap<>(Weight.class));
        }
        ItemSizeHandler[] handlers = nested.computeIfAbsent(weight, k -> new ItemSizeHandler[2]);
        if (handlers[canStack ? 1 : 0] == null) {
            handlers[canStack ? 1 : 0] = new ItemSizeHandler(size, weight, canStack);
        }
        return handlers[canStack ? 1 : 0];
    }

    /**
     * Получает экземпляр ItemSizeHandler с размером Size.SMALL, весом Weight.LIGHT и возможностью складирования в стопках true.
     *
     * @return Экземпляр ItemSizeHandler по умолчанию.
     */
    public static ItemSizeHandler getDefault() {
        return get(Size.SMALL, Weight.LIGHT, true); // Default to fitting in small vessels and stacksize = 32
    }

    /**
     * Возвращает флаг, указывающий, имеет ли объект указанную возможность.
     *
     * @param capability Возможность.
     * @param facing     Направление.
     * @return true, если объект имеет указанную возможность, в противном случае - false.
     */
    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemSize.ITEM_SIZE_CAPABILITY;
    }

    /**
     * Возвращает экземпляр указанной возможности для объекта, если доступно.
     *
     * @param capability Возможность.
     * @param facing     Направление.
     * @param <T>        Тип возможности.
     * @return Экземпляр указанной возможности для объекта, если доступно, в противном случае - null.
     */
    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemSize.ITEM_SIZE_CAPABILITY ? (T) this : null;
    }

    /**
     * Возвращает размер предмета.
     *
     * @param stack Предмет.
     * @return Размер предмета.
     */
    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return this.size;
    }

    /**
     * Возвращает вес предмета.
     *
     * @param stack Предмет.
     * @return Вес предмета.
     */
    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return this.weight;
    }

    /**
     * Возвращает флаг, указывающий, может ли предмет быть складирован в стопках.
     *
     * @param stack Предмет.
     * @return true, если предмет может быть складирован в стопках, в противном случае - false.
     */
    @Override
    public boolean canStack(@Nonnull ItemStack stack) {
        return canStack;
    }

    /**
     * Должен вызываться из метода getItemStackLimit класса net.minecraft.item.Item
     */
    @Override
    public int getStackSize(@Nonnull ItemStack stack) {
        return this.canStack ? this.weight.getStackSize() : 1;
    }
}
