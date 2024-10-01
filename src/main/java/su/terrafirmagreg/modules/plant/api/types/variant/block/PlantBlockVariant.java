package su.terrafirmagreg.modules.plant.api.types.variant.block;

import su.terrafirmagreg.data.lib.types.variant.block.VariantBlock;
import su.terrafirmagreg.modules.plant.ModulePlant;
import su.terrafirmagreg.modules.plant.api.types.type.PlantType;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class PlantBlockVariant extends VariantBlock<PlantBlockVariant, PlantType> {

  @Getter
  private static final Set<PlantBlockVariant> variants = new ObjectOpenHashSet<>();


  protected PlantBlockVariant(Builder builder) {
    super(builder.name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    PlantType.getTypes().forEach(type -> {
      var block = builder.factory.apply(this, type);
      if (map.put(type, block.getBlock()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModulePlant.REGISTRY.block(block);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public String getRegistryKey(SoilType type) {
    return String.format("plant/%s/%s", this.getName(), type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("plant.variant.%s.name", this)).getFormattedText();
  }

  public static class Builder {

    private final String name;

    private BiFunction<PlantBlockVariant, PlantType, IPlantBlock> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<PlantBlockVariant, PlantType, IPlantBlock> factory) {
      this.factory = factory;
      return this;
    }


    public PlantBlockVariant build() {
      return new PlantBlockVariant(this);
    }
  }

}
