package net.dries007.tfc.module.rock.api.recipes;

import net.dries007.tfc.api.recipes.knapping.KnappingRecipeSimple;
import net.dries007.tfc.api.recipes.knapping.KnappingType;
import net.minecraft.item.ItemStack;

public class KnappingRecipeRock extends KnappingRecipeSimple {


    public KnappingRecipeRock(ItemStack output, String... pattern) {
        super(KnappingType.STONE, false, output, pattern);
    }
}
