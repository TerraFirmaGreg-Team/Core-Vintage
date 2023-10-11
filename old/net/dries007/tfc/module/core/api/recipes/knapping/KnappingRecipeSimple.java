package net.dries007.tfc.module.core.api.recipes.knapping;

import net.minecraft.item.ItemStack;

public class KnappingRecipeSimple extends KnappingRecipe {
    private final ItemStack output;

    public KnappingRecipeSimple(KnappingType type, boolean outsideSlotRequired, ItemStack output, String... pattern) {
        super(type, outsideSlotRequired, pattern);
        this.output = output;
    }

    @Override
    public ItemStack getOutput() {
        return output;
    }
}
