package net.dries007.tfc.module.food.common.items;

import net.dries007.tfc.api.capability.food.*;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.module.food.api.category.FoodCategories;
import net.dries007.tfc.module.food.api.type.FoodType;
import net.dries007.tfc.module.food.api.variant.Item.FoodItemVariant;
import net.dries007.tfc.module.food.api.variant.Item.IFoodItem;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class ItemFoodIngredient extends ItemFood implements IItemSize, IItemFoodTFC, IFoodItem {
    protected final FoodType type;
    private final FoodItemVariant variant;

    public ItemFoodIngredient(FoodItemVariant variant, FoodType type) {
        super(0, 0, type.getFoodCategory() == FoodCategories.MEAT || type.getFoodCategory() == FoodCategories.COOKED_MEAT);
        this.type = type;
        this.variant = variant;

        // Use "category" here as to not conflict with actual items, i.e. grain
        OreDictionaryHelper.register(this, "category", this.type.getFoodCategory().toString());
        if (type.getOreDictNames().isPresent()) {
            for (var name : type.getOreDictNames().get()) {
                OreDictionaryHelper.register(this, name);
            }
        }
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

    @Nonnull
    @Override
    public FoodItemVariant getItemVariant() {
        return variant;
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

    @Override
    public void onModelRegister() {
        ModelLoader.setCustomModelResourceLocation(this, 0,
                new ModelResourceLocation(getResourceLocation(), "normal"));
    }
}
