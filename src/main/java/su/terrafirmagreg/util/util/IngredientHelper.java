package su.terrafirmagreg.util.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.common.crafting.IngredientNBT;

public final class IngredientHelper {

    private IngredientHelper() {
        //
    }

    public static IngredientNBT fromStackWithNBT(ItemStack itemStack) {

        return new IngredientNBTExposed(itemStack);
    }

    public static class IngredientNBTExposed
            extends IngredientNBT {

        public IngredientNBTExposed(ItemStack stack) {

            super(stack);
        }
    }
}
