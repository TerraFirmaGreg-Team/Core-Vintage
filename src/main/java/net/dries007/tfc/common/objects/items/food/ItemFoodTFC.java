package net.dries007.tfc.common.objects.items.food;

import net.dries007.tfc.api.capability.food.*;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.food.IFoodItem;
import net.dries007.tfc.api.types.food.category.FoodCategories;
import net.dries007.tfc.api.types.food.type.FoodType;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemFoodTFC extends ItemFood implements IItemSize, IItemFoodTFC, IFoodItem {
    protected final FoodType type;

    public ItemFoodTFC(@Nonnull FoodType type) {
        super(0, 0, type.getFoodCategory() == FoodCategories.MEAT || type.getFoodCategory() == FoodCategories.COOKED_MEAT);
        this.type = type;

        // Use "category" here as to not conflict with actual items, i.e. grain
        OreDictionaryHelper.register(this, "category", this.type.getFoodCategory().toString());
        if (this.type.getOreDictNames() != null) {
            for (String name : this.type.getOreDictNames()) {
                OreDictionaryHelper.register(this, name);
            }
        }

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.FOOD);
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

    @Nonnull
    @Override
    public FoodType getType() {
        return type;
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
        return type.isHeatable() ? new FoodHeatHandler(null, type) : new FoodHandler(null, type);
    }
}
