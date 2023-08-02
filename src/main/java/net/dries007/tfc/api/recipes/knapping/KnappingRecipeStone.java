/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.api.recipes.knapping;

import net.minecraft.item.ItemStack;

public class KnappingRecipeStone extends KnappingRecipeSimple
{
    public KnappingRecipeStone(ItemStack output, String... pattern) {
        super(KnappingType.STONE, false, output, pattern);
    }
}
