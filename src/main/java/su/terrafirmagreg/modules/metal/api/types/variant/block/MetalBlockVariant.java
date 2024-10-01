package su.terrafirmagreg.modules.metal.api.types.variant.block;

import su.terrafirmagreg.data.lib.types.variant.block.VariantBlock;
import su.terrafirmagreg.modules.metal.ModuleMetal;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification;

@Getter
public class MetalBlockVariant extends VariantBlock<MetalBlockVariant, MetalType> {

  @Getter
  private static final Set<MetalBlockVariant> variants = new ObjectOpenHashSet<>();

  private final Specification specification;

  public MetalBlockVariant(Builder builder) {
    super(builder.name);

    this.specification = builder.specification;

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    MetalType.getTypes().forEach(type -> {
      var block = builder.factory.apply(this, type);
      if (map.put(type, block.getBlock()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleMetal.REGISTRY.block(block);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public String getRegistryKey(MetalType type) {
    return String.format("metal/%s/%s", this, type);
  }

  public String getCustomResource() {
    return String.format("metal/%s", this);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("metal.variant.%s.name", this)).getFormattedText();
  }

  public static class Builder {

    private final String name;

    private Specification specification;
    private BiFunction<MetalBlockVariant, MetalType, IMetalBlock> factory;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<MetalBlockVariant, MetalType, IMetalBlock> factory) {
      this.factory = factory;
      return this;
    }

    public Builder fallingSpecification(Specification specification) {
      this.specification = specification;
      return this;
    }

    public MetalBlockVariant build() {
      return new MetalBlockVariant(this);
    }
  }
}
