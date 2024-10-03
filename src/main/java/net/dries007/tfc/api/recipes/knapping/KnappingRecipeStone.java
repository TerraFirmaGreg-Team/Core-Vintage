package net.dries007.tfc.api.recipes.knapping;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.item.ItemStack;

import net.dries007.tfc.api.util.IRockObject;

import java.util.function.Function;

public class KnappingRecipeStone extends KnappingRecipe {

  private final Function<RockType, ItemStack> supplier;

  public KnappingRecipeStone(KnappingType type, Function<RockType, ItemStack> supplier, String... pattern) {
    super(type, false, pattern);
    this.supplier = supplier;
  }

  @Override
  public ItemStack getOutput(ItemStack input) {
    if (input.getItem() instanceof IRockObject rockItem) {
      return supplier.apply(rockItem.getRock(input));
    }
    return ItemStack.EMPTY;
  }
}
