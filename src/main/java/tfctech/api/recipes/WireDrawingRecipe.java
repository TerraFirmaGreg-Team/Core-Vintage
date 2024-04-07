package tfctech.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.compat.jei.IJEISimpleRecipe;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import tfctech.objects.items.TechItems;

public class WireDrawingRecipe extends IForgeRegistryEntry.Impl<WireDrawingRecipe> implements IJEISimpleRecipe {

    private final IIngredient<ItemStack> input;
    private final ItemStack output;
    private final int wireColor;
    private final Metal.Tier minTier;

    public WireDrawingRecipe(ResourceLocation name, IIngredient<ItemStack> input, Metal.Tier minTier, ItemStack output, int wireColor) {
        this.input = input;
        this.minTier = minTier;
        this.output = output;
        this.wireColor = wireColor;
        setRegistryName(name);
    }

    public Metal.Tier getTier() {
        return minTier;
    }

    public int getWireColor() {
        return wireColor;
    }

    public boolean matches(ItemStack input) {
        return this.input.test(input);
    }

    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public NonNullList<IIngredient<ItemStack>> getIngredients() {
        NonNullList<IIngredient<ItemStack>> list = NonNullList.create();
        list.add(input);
        if (minTier.isAtMost(Metal.Tier.TIER_III)) {
            list.add(IIngredient.of(new ItemStack(TechItems.IRON_DRAW_PLATE)));
        } else if (minTier.isAtMost(Metal.Tier.TIER_IV)) {
            list.add(IIngredient.of(new ItemStack(TechItems.STEEL_DRAW_PLATE)));
        } else if (minTier.isAtMost(Metal.Tier.TIER_V)) {
            list.add(IIngredient.of(new ItemStack(TechItems.BLACK_STEEL_DRAW_PLATE)));
        }
        return list;
    }

    @Override
    public NonNullList<ItemStack> getOutputs() {
        return NonNullList.withSize(1, output);
    }
}
