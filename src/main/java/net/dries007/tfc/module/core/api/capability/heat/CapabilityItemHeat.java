package net.dries007.tfc.module.core.api.capability.heat;

import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.init.ItemsCore;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Класс CapabilityItemHeat представляет возможность работы с тепловыми свойствами предметов.
 */
public final class CapabilityItemHeat {
    /**
     * Ключ для определения возможности работы с тепловыми свойствами предметов.
     */
    public static final ResourceLocation KEY = Helpers.getID("item_heat");

    /**
     * Карты, содержащие пользовательские предметы и их тепловые свойства.
     */
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new HashMap<>();

    /**
     * Переменная для работы с возможностью работы с тепловыми свойствами предметов.
     */
    @CapabilityInject(IItemHeat.class)
    public static Capability<IItemHeat> ITEM_HEAT_CAPABILITY;

    /**
     * Метод для регистрации возможности работы с тепловыми свойствами предметов.
     */
    public static void preInit() {
        CapabilityManager.INSTANCE.register(IItemHeat.class, new DumbStorage<>(), ItemHeatHandler::new);
    }

    /**
     * Метод для инициализации пользовательских предметов и их тепловых свойств.
     */
    public static void init() {
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(Items.EGG), () -> new ItemHeatHandler(null, 1, 480));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of("blockClay"), () -> new ItemHeatHandler(null, 1, 600));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsCore.GLASS_SHARD), () -> new ItemHeatHandler(null, 1, 1000));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(ItemsCore.STICK_BUNCH), () -> new ItemHeatHandler(null, 1, 200));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of("terracotta"), () -> new ItemHeatHandler(null, 1, 1200));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(Blocks.IRON_BARS), () -> new ItemHeatHandler(null, 1, 1535));
        CapabilityItemHeat.CUSTOM_ITEMS.put(IIngredient.of(Items.IRON_INGOT), () -> new ItemHeatHandler(null, 0.35F, 1535));
    }

    /**
     * Вспомогательный метод для изменения температуры к определенному значению без перегрева и дрожания.
     *
     * @param temp   текущая температура
     * @param target целевая температура
     * @param delta  положительное изменение температуры
     * @return новая температура
     */
    public static float adjustTempTowards(float temp, float target, float delta) {
        return adjustTempTowards(temp, target, delta, delta);
    }

    /**
     * Вспомогательный метод для изменения температуры к определенному значению без перегрева и дрожания.
     *
     * @param temp          текущая температура
     * @param target        целевая температура
     * @param deltaPositive положительное изменение температуры
     * @param deltaNegative отрицательное изменение температуры
     * @return новая температура
     */
    public static float adjustTempTowards(float temp, float target, float deltaPositive, float deltaNegative) {
        if (temp < target) {
            return Math.min(temp + deltaPositive, target);
        } else if (temp > target) {
            return Math.max(temp - deltaNegative, target);
        } else {
            return target;
        }
    }

    /**
     * Метод для изменения температуры на основе теплоемкости и времени обновления.
     *
     * @param temp             текущая температура
     * @param heatCapacity     теплоемкость
     * @param ticksSinceUpdate время с последнего обновления
     * @return новая температура
     */
    public static float adjustTemp(float temp, float heatCapacity, long ticksSinceUpdate) {
        if (ticksSinceUpdate <= 0) return temp;
        final float newTemp = temp - heatCapacity * (float) ticksSinceUpdate * (float) ConfigTFC.Devices.TEMPERATURE.globalModifier;
        return newTemp < 0 ? 0 : newTemp;
    }

    /**
     * Метод для увеличения температуры предмета.
     *
     * @param instance экземпляр предмета
     */
    public static void addTemp(IItemHeat instance) {
        // Default modifier = 3 (2x normal cooling)
        addTemp(instance, 3);
    }

    /**
     * Метод для увеличения температуры предмета с заданным модификатором.
     *
     * @param instance экземпляр предмета
     * @param modifier модификатор увеличения температуры
     */
    public static void addTemp(IItemHeat instance, float modifier) {
        final float temp = instance.getTemperature() + modifier * instance.getHeatCapacity() * (float) ConfigTFC.Devices.TEMPERATURE.globalModifier;
        instance.setTemperature(temp);
    }

    /**
     * Метод для приближения температуры предмета к целевой температуре.
     *
     * @param temp         текущая температура
     * @param burnTemp     температура горения
     * @param airTicks     количество тиков нахождения предмета на воздухе
     * @param maxTempBonus максимальное дополнительное значение температуры
     * @return новая температура
     */
    public static float adjustToTargetTemperature(float temp, float burnTemp, int airTicks, int maxTempBonus) {
        boolean hasAir = airTicks > 0;
        float targetTemperature = burnTemp + (hasAir ? MathHelper.clamp(burnTemp, 0, maxTempBonus) : 0);

        if (targetTemperature > 1601)
            targetTemperature = 1601;

        if (temp != targetTemperature) {
            float delta = (float) ConfigTFC.Devices.TEMPERATURE.heatingModifier;
            return adjustTempTowards(temp, targetTemperature, delta * (hasAir ? 2 : 1), delta * (hasAir ? 0.5f : 1));
        }
        return temp;
    }

    /**
     * Метод для получения пользовательских тепловых свойств предмета.
     *
     * @param stack предмет
     * @return тепловые свойства предмета
     */
    @Nullable
    public static ICapabilityProvider getCustomHeat(ItemStack stack) {
        Set<IIngredient<ItemStack>> itemItemSet = CUSTOM_ITEMS.keySet();
        for (IIngredient<ItemStack> ingredient : itemItemSet) {
            if (ingredient.testIgnoreCount(stack)) {
                return CUSTOM_ITEMS.get(ingredient).get();
            }
        }

        return null;
    }
}
