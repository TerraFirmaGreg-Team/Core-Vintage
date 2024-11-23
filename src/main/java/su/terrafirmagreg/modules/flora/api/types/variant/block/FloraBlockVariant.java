package su.terrafirmagreg.modules.flora.api.types.variant.block;

import su.terrafirmagreg.api.library.types.variant.block.VariantBlock;
import su.terrafirmagreg.modules.flora.ModuleFlora;
import su.terrafirmagreg.modules.flora.api.types.type.FloraType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class FloraBlockVariant extends VariantBlock<IFloraBlock, FloraBlockVariant, FloraType> {

  @Getter
  private static final Set<FloraBlockVariant> variants = new ObjectOpenHashSet<>();

  protected FloraBlockVariant(Builder builder) {
    super(builder.name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    FloraType.getTypes().forEach(type -> {
      var block = builder.factory.apply(this, type);
      if (map.put(type, block) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleFlora.REGISTRY.block(block);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public String getRegistryKey(FloraType type) {
    return String.format("flora/%s/%s", this, type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("plant.variant.%s.name", this)).getFormattedText();
  }

  public static class Builder {

    private final String name;

    private BiFunction<FloraBlockVariant, FloraType, IFloraBlock> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<FloraBlockVariant, FloraType, IFloraBlock> factory) {
      this.factory = factory;
      return this;
    }


    public FloraBlockVariant build() {
      return new FloraBlockVariant(this);
    }
  }

}
