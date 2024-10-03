package su.terrafirmagreg.modules.plant.api.types.category;

import su.terrafirmagreg.data.lib.types.category.Category;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;

import net.minecraft.block.material.Material;
import net.minecraftforge.common.EnumPlantType;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.dries007.tfc.objects.blocks.plants.BlockPlant;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

@Getter
public class PlantCategory extends Category<PlantCategory> {

  @Getter
  private static final Set<PlantCategory> categories = new ObjectOpenHashSet<>();

  private final Material material;
  private final EnumPlantType enumPlantType;
  private final Optional<String> waterType;
  private final boolean canBePotted;
  private final Function<PlantType, BlockPlant> factory;

  protected PlantCategory(Builder builder) {
    super(builder.name);

    this.material = builder.material;
    this.enumPlantType = builder.enumPlantType;
    this.waterType = builder.waterType;
    this.canBePotted = builder.canBePotted;
    this.factory = builder.factory;

    if (!categories.add(this)) {
      throw new RuntimeException(String.format("Category: [%s] already exists!", name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public BlockPlant create(PlantType plant) {
    return factory.apply(plant);
  }

  public static class Builder {

    private final String name;

    private boolean canBePotted;
    private Material material;
    private EnumPlantType enumPlantType;
    private Optional<String> waterType;
    private Function<PlantType, BlockPlant> factory;

    public Builder(@NotNull String name) {
      this.name = name;

      this.material = Material.PLANTS;
      this.enumPlantType = EnumPlantType.Plains;
      this.waterType = Optional.of("fresh_water");
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

    public Builder enumPlantType(EnumPlantType enumPlantType) {
      this.enumPlantType = enumPlantType;
      return this;
    }

    public Builder waterType(String waterType) {
      this.waterType = Optional.of(waterType);
      return this;
    }

//    public Builder waterType(IBlockState waterType) {
//      this.waterType = Optional.ofNullable(waterType);
//      return this;
//    }

    public Builder factory(Function<PlantType, BlockPlant> factory) {
      this.factory = factory;
      return this;
    }

    public PlantCategory build() {
      return new PlantCategory(this);
    }
  }
}
