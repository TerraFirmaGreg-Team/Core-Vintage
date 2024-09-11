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

  private Specification specification;
  private BiFunction<WoodBlockVariant, WoodType, ? extends Block> factory;
  private int encouragement;
  private int flammability;

  private WoodBlockVariant(String name) {
    super(name);

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }
  }

  public static WoodBlockVariant builder(String name) {
    return new WoodBlockVariant(name);
  }

  public Block get(WoodType type) {
    var block = this.map.get(type);
    if (block != null) {
      return block;
    }
    throw new RuntimeException(String.format("Block wood is null: %s, %s", this, type));
  }

  public WoodBlockVariant setFactory(BiFunction<WoodBlockVariant, WoodType, ? extends Block> factory) {
    this.factory = factory;
    return this;
  }

  public WoodBlockVariant setFireInfo(int encouragement, int flammability) {
    this.encouragement = encouragement;
    this.flammability = flammability;
    return this;
  }

  public WoodBlockVariant setFallingSpecification(Specification specification) {
    this.specification = specification;
    return this;
  }

  public WoodBlockVariant build(RegistryManager registry) {
    WoodType.getTypes().forEach(type -> {
      if (map.put(type, factory.apply(this, type)) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
    });
    registry.blocks(map.values());
    return this;
  }

  public String getRegistryKey(WoodType type) {
    return String.format("wood/%s/%s", this.getName(), type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(
            String.format("wood.variant.%s.name", this)).getFormattedText();
  }

}
