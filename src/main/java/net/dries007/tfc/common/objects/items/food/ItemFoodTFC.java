package net.dries007.tfc.common.objects.items.food;

import net.dries007.tfc.api.capability.food.*;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.food.category.FoodCategories;
import net.dries007.tfc.api.types.food.type.FoodVariant;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class ItemFoodTFC extends ItemFood implements IItemSize, IItemFoodTFC {
    private static final Map<FoodVariant, ItemFoodTFC> MAP = new HashMap<>();
    protected final FoodVariant foodOld;

    public ItemFoodTFC(@Nonnull FoodVariant foodOld) {
        super(0, 0, foodOld.getFoodCategory() == FoodCategories.MEAT || foodOld.getFoodCategory() == FoodCategories.COOKED_MEAT);
        this.foodOld = foodOld;
        if (MAP.put(foodOld, this) != null) {
            throw new IllegalStateException("There can only be one.");
        }

        // Use "category" here as to not conflict with actual items, i.e. grain
        OreDictionaryHelper.register(this, "category", foodOld.getFoodCategory().toString());
        if (foodOld.getOreDictNames() != null) {
            for (String name : foodOld.getOreDictNames()) {
                OreDictionaryHelper.register(this, name);
            }
        }
    }

    public static ItemFoodTFC get(FoodVariant foodOld) {
        return MAP.get(foodOld);
    }

    public static ItemStack get(FoodVariant foodOld, int amount) {
        return new ItemStack(MAP.get(foodOld), amount);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            // Makes creative items not decay (like JEI)
            ItemStack stack = new ItemStack(this);
            IFood cap = stack.getCapability(CapabilityFood.CAPABILITY, null);
            if (cap != null) {
                cap.setNonDecaying();
            }
            items.add(stack);
        }
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return getStackSize(stack);
    }

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.VERY_LIGHT;
    }

    @Override
    public ICapabilityProvider getCustomFoodHandler() {
        return foodOld.isHeatable() ? new FoodHeatHandler(null, foodOld) : new FoodHandler(null, foodOld);
    }
}
