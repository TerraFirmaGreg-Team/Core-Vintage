package su.terrafirmagreg.modules.wood.api.types.variant.block;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.types.variant.Variant;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.block.Block;
import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification;

@Getter
public class WoodBlockVariant extends Variant<WoodBlockVariant> {

  @Getter
  private static final Set<WoodBlockVariant> variants = new ObjectOpenHashSet<>();

  private final Map<WoodType, Block> map = new Object2ObjectOpenHashMap<>();

  private final Specification specification;
  private final BiFunction<WoodBlockVariant, WoodType, ? extends Block> factory;
  private final int encouragement;
  private final int flammability;

  private WoodBlockVariant(Builder builder) {
    super(builder.name);

    this.specification = builder.specification;
    this.encouragement = builder.encouragement;
    this.flammability = builder.flammability;
    this.factory = builder.factory;

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public Block get(WoodType type) {
    var block = this.map.get(type);
    if (block != null) {
      return block;
    }
    throw new RuntimeException(String.format("Block is null: %s, %s", this, type));
  }

  public String getRegistryKey(WoodType type) {
    return String.format("wood/%s/%s", this.getName(), type);
  }

  public String getCustomResource() {
    return String.format("wood/%s", this.getName());
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(
      String.format("wood.variant.%s.name", this)).getFormattedText();
  }


  public static class Builder {

    private final String name;

    private Specification specification;
    private BiFunction<WoodBlockVariant, WoodType, ? extends Block> factory;
    private int encouragement;
    private int flammability;


    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<WoodBlockVariant, WoodType, ? extends Block> factory) {
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

    public WoodBlockVariant build(RegistryManager registry) {

      var variant = new WoodBlockVariant(this);
      WoodType.getTypes().forEach(type -> {
        if (variant.getMap().put(type, factory.apply(variant, type)) != null) {
          throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
        }
      });
      registry.blocks(variant.getMap().values());

      return variant;
    }

  }

}
