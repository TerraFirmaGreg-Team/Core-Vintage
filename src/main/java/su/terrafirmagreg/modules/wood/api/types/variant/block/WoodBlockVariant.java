package su.terrafirmagreg.modules.wood.api.types.variant.block;

import su.terrafirmagreg.data.lib.types.variant.block.VariantBlock;
import su.terrafirmagreg.modules.wood.ModuleWood;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification;

@Getter
public class WoodBlockVariant extends VariantBlock<WoodBlockVariant, WoodType> {

  @Getter
  private static final Set<WoodBlockVariant> variants = new ObjectOpenHashSet<>();

  private final Specification specification;
  private final int encouragement;
  private final int flammability;

  private WoodBlockVariant(Builder builder) {
    super(builder.name);

    this.specification = builder.specification;
    this.encouragement = builder.encouragement;
    this.flammability = builder.flammability;

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    WoodType.getTypes().forEach(type -> {
      var block = builder.factory.apply(this, type);
      if (map.put(type, block.getBlock()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleWood.REGISTRY.block(block);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }


  public String getCustomResource() {
    return String.format("wood/%s", this.getName());
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("wood.variant.%s.name", this)).getFormattedText();
  }


  public static class Builder {

    private final String name;

    private Specification specification;
    private BiFunction<WoodBlockVariant, WoodType, IWoodBlock> factory;
    private int encouragement;
    private int flammability;


    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<WoodBlockVariant, WoodType, IWoodBlock> factory) {
      this.factory = factory;
      return this;
    }

    public Builder fireInfo(int encouragement, int flammability) {
      this.encouragement = encouragement;
      this.flammability = flammability;
      return this;
    }

    public Builder fallingSpecification(Specification specification) {
      this.specification = specification;
      return this;
    }

    public WoodBlockVariant build() {
      return new WoodBlockVariant(this);
    }

  }

}
