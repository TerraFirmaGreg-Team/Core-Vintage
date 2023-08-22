package net.dries007.tfc.api.capability.food;

import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.ICalendar;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

/**
 * Класс CapabilityFood представляет возможность для работы с едой в игре.
 */
public class CapabilityFood {
    /**
     * Уникальный идентификатор возможности.
     */
    public static final ResourceLocation KEY = new ResourceLocation(MOD_ID, "food");

    /**
     * Список пользовательских еды.
     */
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_FOODS = new HashMap<>();

    /**
     * Количество тиков по умолчанию для разложения еды.
     * Большинство еды разлагается в диапазоне [1, 4] (большие значения - быстрее разложение).
     * Это значение соответствует 25% - 100% от общего времени разложения.
     * Например, мясо/фрукты разлагаются примерно за 5 дней, зерновые - за 20 дней.
     */
    public static final int DEFAULT_ROT_TICKS = ICalendar.TICKS_IN_DAY * 22;

    /**
     * Возможность IFood.
     */
    @CapabilityInject(IFood.class)
    public static Capability<IFood> CAPABILITY;

    /**
     * Регистрация возможности IFood.
     */
    public static void preInit() {
        CapabilityManager.INSTANCE.register(IFood.class, new DumbStorage<>(), FoodHandler::new);
    }

    /**
     * Инициализация пользовательской еды.
     */
    public static void init() {
        // Добавление пользовательских экземпляров еды
        CUSTOM_FOODS.put(IIngredient.of(Items.ROTTEN_FLESH), () -> new FoodHandler(null, FoodData.ROTTEN_FLESH));
        CUSTOM_FOODS.put(IIngredient.of(Items.GOLDEN_APPLE), () -> new FoodHandler(null, FoodData.GOLDEN_APPLE));
        CUSTOM_FOODS.put(IIngredient.of(Items.GOLDEN_CARROT), () -> new FoodHandler(null, FoodData.GOLDEN_CARROT));
        CUSTOM_FOODS.put(IIngredient.of(Items.EGG), () -> new FoodHandler(null, FoodData.RAW_EGG));
    }

    /**
     * Метод для применения характеристики к предмету с едой.
     * Расчет даты создания происходит между полным сохранением (если еда новая) и отсутствием сохранения (если еда испорчена).
     */
    public static void applyTrait(IFood instance, FoodTrait trait) {
        if (!instance.getTraits().contains(trait)) {
            if (!instance.isRotten()) {
                // Применение модификатора даты разложения = 1 / модификатор разложения
                instance.setCreationDate(calculateNewCreationDate(instance.getCreationDate(), 1f / trait.getDecayModifier()));
            }
            instance.getTraits().add(trait);
        }
    }

    /**
     * Метод для применения характеристики к предмету с едой.
     */
    public static void applyTrait(ItemStack stack, FoodTrait trait) {
        IFood food = stack.getCapability(CAPABILITY, null);
        if (!stack.isEmpty() && food != null) {
            applyTrait(food, trait);
        }
    }

    /**
     * Метод для удаления характеристики из предмета с едой.
     */
    public static void removeTrait(IFood instance, FoodTrait trait) {
        if (instance.getTraits().contains(trait)) {
            if (!instance.isRotten()) {
                // Удаление модификатора = 1 / примененный модификатор
                instance.setCreationDate(calculateNewCreationDate(instance.getCreationDate(), trait.getDecayModifier()));
            }
            instance.getTraits().remove(trait);
        }
    }

    /**
     * Метод для удаления характеристики из предмета с едой.
     */
    public static void removeTrait(ItemStack stack, FoodTrait trait) {
        IFood food = stack.getCapability(CAPABILITY, null);
        if (!stack.isEmpty() && food != null) {
            removeTrait(food, trait);
        }
    }

    /**
     * Этот метод используется для обновления стека из старого стека в случае, когда еда создается из другой.
     * Любой метод, который создает производную еду, должен вызывать этот метод, так как он позволяет избежать увеличения срока годности предмета.
     * Если вызывается с непищевыми предметами, ничего не происходит.
     *
     * @param oldStack старый стек
     * @param newStack новый стек
     * @return измененный стек для цепочки вызовов
     */
    public static ItemStack updateFoodFromPrevious(ItemStack oldStack, ItemStack newStack) {
        IFood oldCap = oldStack.getCapability(CapabilityFood.CAPABILITY, null);
        IFood newCap = newStack.getCapability(CapabilityFood.CAPABILITY, null);
        if (oldCap != null && newCap != null) {
            // Копирование характеристик из старого стека в новый стек
            newCap.getTraits().addAll(oldCap.getTraits());
            // Применение модификатора срока годности DATE decay = new / old
            float decayDelta = newCap.getDecayDateModifier() / oldCap.getDecayDateModifier();
            newCap.setCreationDate(calculateNewCreationDate(oldCap.getCreationDate(), decayDelta));
        }
        return newStack;
    }

    /**
     * Вызывайте этот метод из любой функции, которая должна создавать новый стек предметов.
     * В большинстве случаев следует использовать {@link CapabilityFood#updateFoodFromPrevious(ItemStack, ItemStack)}, так как срок годности должен передаваться от ввода к выводу.
     * Этот метод используется только там, где нет ввода (например, при прямом {@code stack.copy()} из непищевых входных данных).
     *
     * @param stack новый стек
     * @return входной стек для цепочки вызовов
     */
    @SuppressWarnings("unused")
    public static ItemStack updateFoodDecayOnCreate(ItemStack stack) {
        IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
        if (cap != null) {
            cap.setCreationDate(CalendarTFC.PLAYER_TIME.getTicks());
        }
        return stack;
    }

    /**
     * Устанавливает стек без учета срока годности.
     *
     * @param stack стек предметов
     */
    public static void setStackNonDecaying(ItemStack stack) {
        IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
        if (cap != null) {
            cap.setNonDecaying();
        }
    }

    /**
     * Возвращает пользовательский интерфейс возможностей для указанного предмета.
     *
     * @param stack Предмет, для которого нужно получить пользовательский интерфейс возможностей
     * @return Пользовательский интерфейс возможностей или null, если не найдено соответствующего пользовательского интерфейса
     */
    @Nullable
    public static ICapabilityProvider getCustomFood(ItemStack stack) {
        Set<IIngredient<ItemStack>> itemFoodSet = CUSTOM_FOODS.keySet();
        for (IIngredient<ItemStack> ingredient : itemFoodSet) {
            if (ingredient.testIgnoreCount(stack)) {
                return CUSTOM_FOODS.get(ingredient).get();
            }
        }
        return null;
    }

    /**
     * @param stackToMergeInto стек, который будет увеличиваться
     * @param stackToMerge     стек, который будет уменьшаться. Будет изменен.
     * @return результат stackToMergeInto
     */
    public static ItemStack mergeItemStacksIgnoreCreationDate(ItemStack stackToMergeInto, ItemStack stackToMerge) {
        if (!stackToMerge.isEmpty()) {
            if (!stackToMergeInto.isEmpty()) {
                if (CapabilityFood.areStacksStackableExceptCreationDate(stackToMergeInto, stackToMerge)) {
                    IFood mergeIntoFood = stackToMergeInto.getCapability(CapabilityFood.CAPABILITY, null);
                    IFood mergeFood = stackToMerge.getCapability(CapabilityFood.CAPABILITY, null);
                    if (mergeIntoFood != null && mergeFood != null) {
                        int mergeAmount = Math.min(stackToMerge.getCount(), stackToMergeInto.getMaxStackSize() - stackToMergeInto.getCount());
                        if (mergeAmount > 0) {
                            mergeIntoFood.setCreationDate(Math.min(mergeIntoFood.getCreationDate(), mergeFood.getCreationDate()));
                            stackToMerge.shrink(mergeAmount);
                            stackToMergeInto.grow(mergeAmount);
                        }
                    }
                }
            } else {
                ItemStack stackToMergeCopy = stackToMerge.copy();
                stackToMerge.setCount(0);
                return stackToMergeCopy;
            }
        }
        return stackToMergeInto;
    }

    /**
     * Этот метод предоставляет способ проверить, могут ли два стека быть объединены в один, игнорируя дату создания: копируйте оба стека, установите для них одинаковую дату создания, затем проверьте их совместимость.
     * Это также не позволит объединять стеки с разными характеристиками, что является преднамеренным.
     *
     * @return true, если стеки могут быть объединены, игнорируя их дату создания
     */
    public static boolean areStacksStackableExceptCreationDate(ItemStack stack1, ItemStack stack2) {
        // Этот метод предоставляет способ проверить, могут ли два стека быть объединены в один, игнорируя дату создания: копируйте оба стека, установите для них одинаковую дату создания, затем проверьте их совместимость.
        // Это также не позволит объединять стеки с разными характеристиками, что является преднамеренным.
        ItemStack stack1Copy = stack1.copy();
        IFood food1 = stack1Copy.getCapability(CapabilityFood.CAPABILITY, null);
        if (food1 != null) {
            food1.setCreationDate(0);
        }
        ItemStack stack2Copy = stack2.copy();
        IFood food2 = stack2Copy.getCapability(CapabilityFood.CAPABILITY, null);
        if (food2 != null) {
            food2.setCreationDate(0);
        }
        return ItemHandlerHelper.canItemStacksStackRelaxed(stack1Copy, stack2Copy);
    }

    /**
     * @return Получает дату создания для установки еды, чтобы объединить предметы, созданные близко по времени
     */
    public static long getRoundedCreationDate() {
        return (CalendarTFC.PLAYER_TIME.getTotalHours() / ConfigTFC.General.FOOD.decayStackTime) * ICalendar.TICKS_IN_HOUR * ConfigTFC.General.FOOD.decayStackTime;
    }

    /**
     * T = текущее время, Ci / Cf = начальная / конечная дата создания, Ei / Ef = начальная / конечная дата истечения срока годности, d = время разложения, p = модификатор сохранности
     * <p>
     * Чтобы применить сохранность p в момент времени T: хотим, чтобы оставшаяся доля разложения была неизменной при сохранности.
     * Пусть Ri = (T - Ci) / (Ei - Ci) = (T - Ci) / d, Rf = (T - Cf) / (d * p)
     * Тогда, если Ri = Rf
     * => d * p * (T - Ci) = d * (T - Cf)
     * => Cf = (1 - p) * T + p * Ci (аффинное сочетание)
     * <p>
     * Чтобы показать, что E > T неизменно при сохранности: (см. TerraFirmaCraft#352)
     * Пусть T, Ci, Ei, d, p > 0 такие, что Ei > T (1.), и Ei = Ci + d
     * Cf = (1 - p) * T + p * Ci
     * => Ef = Cf + p * d
     * = (1 - p) * T + p * Ci + p * d
     * = (1 - p) * T + p * (Ci + d)
     * через 1. > (1 - p) * T + p * T = T
     * QED
     *
     * @param ci Начальная дата создания
     * @param p  Модификатор даты разложения (1 / стандартный модификатор разложения)
     * @return Конечная дата создания
     */
    private static long calculateNewCreationDate(long ci, float p) {
        // Cf = (1 - p) * T + p * Ci
        return (long) ((1 - p) * CalendarTFC.PLAYER_TIME.getTicks() + p * ci);
    }
}
