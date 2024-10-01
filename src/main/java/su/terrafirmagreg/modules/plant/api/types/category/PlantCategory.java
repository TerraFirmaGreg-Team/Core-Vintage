package su.terrafirmagreg.modules.plant.api.types.category;

import su.terrafirmagreg.data.lib.types.category.Category;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Set;

import static su.terrafirmagreg.modules.world.classic.ChunkGenClassic.FRESH_WATER;

@Getter
public class PlantCategory extends Category<PlantCategory> {

  @Getter
  private static final Set<PlantCategory> categories = new ObjectOpenHashSet<>();

  private final Material material;
  private final IBlockState waterType;
  private final boolean canBePotted;

  protected PlantCategory(Builder builder) {
    super(builder.name);

    this.material = builder.material;
    this.waterType = builder.waterType;
    this.canBePotted = builder.canBePotted;

    if (!categories.add(this)) {
      throw new RuntimeException(String.format("Category: [%s] already exists!", name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public static class Builder {

    private final String name;

    private Material material;
    private IBlockState waterType;
    private boolean canBePotted;

    public Builder(@NotNull String name) {
      this.name = name;

      this.material = Material.PLANTS;
      this.waterType = FRESH_WATER;
      this.canBePotted = false;
    }

    public Builder material(Material material) {
      this.material = material;
      return this;
    }

    public Builder canBePotted() {
      this.canBePotted = true;
      return this;
    }

    public Builder waterType(IBlockState waterType) {
      this.waterType = waterType;
      return this;
    }

    public PlantCategory build() {
      return new PlantCategory(this);
    }
  }
}
