package net.dries007.tfc.api.recipes;

import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import net.dries007.tfc.types.TechRegistries;
import net.dries007.tfc.util.SimpleCraftMatrix;

public class GlassworkingRecipe extends IForgeRegistryEntry.Impl<GlassworkingRecipe> {

  private final SimpleCraftMatrix matrix;
  private final ItemStack output;

  public GlassworkingRecipe(ItemStack output, String... pattern) {
    this.matrix = new SimpleCraftMatrix(false, pattern);
    this.output = output;
  }

  public static GlassworkingRecipe get(SimpleCraftMatrix input) {
    return TechRegistries.GLASSWORKING.getValuesCollection().stream().filter(x -> x.matches(input)).findFirst().orElse(null);
  }

  public SimpleCraftMatrix getMatrix() {
    return matrix;
  }

  public ItemStack getOutput() {
    return output.copy();
  }

  private boolean matches(SimpleCraftMatrix other) {
    return other.matches(this.matrix);
  }
}
