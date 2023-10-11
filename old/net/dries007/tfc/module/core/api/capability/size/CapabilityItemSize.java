package net.dries007.tfc.module.core.api.capability.size;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.dries007.tfc.api.capability.DumbStorage;
import net.dries007.tfc.api.capability.ItemStickCapability;
import net.dries007.tfc.common.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.minecraft.block.BlockLadder;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Supplier;

public final class CapabilityItemSize {
    public static final ResourceLocation KEY = Helpers.getID("item_size");
    public static final Map<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> CUSTOM_ITEMS = new Object2ObjectLinkedOpenHashMap<>(); // Используется в CT, устанавливает пользовательский IItemSizeAndWeight для предметов вне TFC
    @CapabilityInject(IItemSizeAndWeight.class)
    public static Capability<IItemSizeAndWeight> ITEM_SIZE_CAPABILITY;

    /**
     * Производит предварительную инициализацию капабилити
     */
    public static void preInit() {
        // Регистрация капабилити
        CapabilityManager.INSTANCE.register(IItemSizeAndWeight.class, new DumbStorage<>(), ItemSizeHandler::getDefault);
    }

    /**
     * Добавляет захардкоженные значения размеров для ванильных предметов
     */
    public static void init() {
        CUSTOM_ITEMS.put(IIngredient.of(Items.COAL), () -> ItemSizeHandler.get(Size.SMALL, Weight.LIGHT, true)); // Хранится в любом месте, размер стопки = 32
        CUSTOM_ITEMS.put(IIngredient.of(Items.STICK), ItemStickCapability::new); // Хранится в любом месте, размер стопки = 64
        CUSTOM_ITEMS.put(IIngredient.of(Items.CLAY_BALL), () -> ItemSizeHandler.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Хранится в любом месте, размер стопки = 64
        CUSTOM_ITEMS.put(IIngredient.of(Items.BED), () -> ItemSizeHandler.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Хранится только в сундуках, размер стопки = 1
        CUSTOM_ITEMS.put(IIngredient.of(Items.MINECART), () -> ItemSizeHandler.get(Size.LARGE, Weight.VERY_HEAVY, false)); // Хранится только в сундуках, размер стопки = 1
        CUSTOM_ITEMS.put(IIngredient.of(Items.ARMOR_STAND), () -> ItemSizeHandler.get(Size.LARGE, Weight.HEAVY, true)); // Хранится только в сундуках, размер стопки = 4
        CUSTOM_ITEMS.put(IIngredient.of(Items.CAULDRON), () -> ItemSizeHandler.get(Size.LARGE, Weight.LIGHT, true)); // Хранится только в сундуках, размер стопки = 32
        CUSTOM_ITEMS.put(IIngredient.of(Blocks.TRIPWIRE_HOOK), () -> ItemSizeHandler.get(Size.SMALL, Weight.VERY_LIGHT, true)); // Хранится в любом месте, размер стопки = 64
    }

    /**
     * Проверяет, имеет ли предмет заданный размер и вес
     *
     * @param stack  Предмет
     * @param size   Размер
     * @param weight Вес
     * @return true, если предмет имеет заданный размер и вес, иначе false
     */
    public static boolean checkItemSize(ItemStack stack, Size size, Weight weight) {
        IItemSizeAndWeight cap = getIItemSize(stack);
        if (cap != null) return cap.getWeight(stack) == weight && cap.getSize(stack) == size;
        return false;
    }

    /**
     * Получает экземпляр IItemSizeAndWeight из предмета, либо через капабилити, либо через интерфейс
     *
     * @param stack Предмет
     * @return IItemSizeAndWeight, если он существует, или null, если его нет
     */
    @Nullable
    public static IItemSizeAndWeight getIItemSize(ItemStack stack) {
        if (!stack.isEmpty()) {
            IItemSizeAndWeight size = stack.getCapability(ITEM_SIZE_CAPABILITY, null);
            if (size != null) {
                return size;
            } else if (stack.getItem() instanceof IItemSizeAndWeight) {
                return (IItemSizeAndWeight) stack.getItem();
            } else if (stack.getItem() instanceof ItemBlock && ((ItemBlock) stack.getItem()).getBlock() instanceof IItemSizeAndWeight) {
                return (IItemSizeAndWeight) ((ItemBlock) stack.getItem()).getBlock();
            }
        }
        return null;
    }

    /**
     * Получает пользовательский размер для предмета
     *
     * @param stack Предмет
     * @return ICapabilityProvider с пользовательским размером предмета, если он существует, иначе используется общий размер предмета
     */
    @Nonnull
    public static ICapabilityProvider getCustomSize(ItemStack stack) {
        for (Map.Entry<IIngredient<ItemStack>, Supplier<ICapabilityProvider>> entry : CUSTOM_ITEMS.entrySet()) {
            if (entry.getKey().testIgnoreCount(stack)) {
                return entry.getValue().get();
            }
        }
        // Проверяем общие типы предметов
        Item item = stack.getItem();
        if (item instanceof ItemTool || item instanceof ItemSword) {
            return ItemSizeHandler.get(Size.LARGE, Weight.MEDIUM, true); // Хранится только в сундуках, размер стопки должен быть ограничен 1, так как это инструмент
        } else if (item instanceof ItemArmor) {
            return ItemSizeHandler.get(Size.LARGE, Weight.VERY_HEAVY, true); // Хранится только в сундуках, размер стопки = 1
        } else if (item instanceof ItemBlock && ((ItemBlock) item).getBlock() instanceof BlockLadder) {
            return ItemSizeHandler.get(Size.SMALL, Weight.VERY_LIGHT, true); // Подходит для маленьких сосудов, размер стопки = 64
        } else if (item instanceof ItemBlock) {
            return ItemSizeHandler.get(Size.SMALL, Weight.LIGHT, true); // Подходит для маленьких сосудов, размер стопки = 32
        } else {
            return ItemSizeHandler.get(Size.VERY_SMALL, Weight.VERY_LIGHT, true); // Хранится в любом месте, размер стопки = 64
        }
    }
}
