package su.terrafirmagreg.modules.wood.object.recipe.nut;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.init.RegistryWood;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NutRecipe extends IForgeRegistryEntry.Impl<NutRecipe> {

  protected final Block inputLog;
  protected final Block inputLeaves;
  protected final ItemStack outputItem;

  protected NutRecipe(Builder builder) {

    this.inputLog = builder.inputLog;
    this.inputLeaves = builder.inputLeaves;
    this.outputItem = builder.outputItem;

    if (builder.inputLog == null || builder.inputLeaves == null || builder.outputItem == null) {
      throw new IllegalArgumentException("Sorry, something was null in your nut tree registry.");
    }

    if (builder.name.isEmpty()) {
      throw new RuntimeException(
        String.format("Recipe name must contain any character: [%s]", builder.name));
    }

    RegistryWood.NUT.register(this.setRegistryName(ModUtils.resource(builder.name)));
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  @Nullable
  public static NutRecipe get(Block block) {
    return RegistryWood.NUT.getValuesCollection()
                           .stream()
                           .filter(x -> x.isValidInput(block))
                           .findFirst()
                           .orElse(null);
  }

  private boolean isValidInput(Block input) {
    return this.inputLog == input;
  }

  @NotNull
  public Block getLeaves() {
    return inputLeaves;
  }

  @NotNull
  public ItemStack getOutputItem() {
    return outputItem;
  }

  public static class Builder {

    private final String name;
    private Block inputLog;
    private Block inputLeaves;
    private ItemStack outputItem;

    public Builder(String name) {
      this.name = name;
    }

    public Builder setInputLog(Block inputLog) {
      this.inputLog = inputLog;
      return this;
    }

    public Builder setInputLeaves(Block inputLeaves) {
      this.inputLeaves = inputLeaves;
      return this;
    }

    public Builder setOutputItem(ItemStack outputItem) {
      this.outputItem = outputItem;
      return this;
    }

    public NutRecipe build() {
      return new NutRecipe(this);
    }
  }
}
