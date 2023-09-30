package net.dries007.tfc.module.rock.api.recipes;

import net.dries007.tfc.module.core.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.module.core.api.recipes.knapping.KnappingType;
import net.minecraft.item.ItemStack;

public class RecipeRockKnapping extends KnappingRecipeSimple {


    public RecipeRockKnapping(ItemStack output, String... pattern) {
        super(KnappingType.STONE, false, output, pattern);
    }
}
