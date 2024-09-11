package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.data.lib.types.variant.Variant;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.util.text.TextComponentTranslation;


import gregtech.api.unification.ore.StoneType;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification;

@Getter
public class RockBlockVariant extends Variant<RockBlockVariant> {

  @Getter
  private static final Set<RockBlockVariant> blockVariants = new ObjectOpenHashSet<>();
  private static final AtomicInteger idCounter = new AtomicInteger(16);

  private final Map<RockType, Block> map = new Object2ObjectOpenHashMap<>();
  private float baseHardness;
  private Specification specification;
  private BiFunction<RockBlockVariant, RockType, Block> factory;
  private boolean hasStoneType;
  private StoneType stoneType;

  private RockBlockVariant(String name) {
    super(name);

    if (!blockVariants.add(this)) {
      throw new RuntimeException(String.format("RockBlockVariant: [%s] already exists!", name));
    }

  }

  public static RockBlockVariant builder(String name) {

    return new RockBlockVariant(name);
  }

  public RockBlockVariant baseHardness(float baseHardness) {
    this.baseHardness = baseHardness;
    return this;
  }

  public RockBlockVariant factory(BiFunction<RockBlockVariant, RockType, Block> factory) {
    this.factory = factory;
    return this;
  }

  public RockBlockVariant fallingSpecification(Specification specification) {
    this.specification = specification;
    return this;
  }

  public RockBlockVariant isStoneType() {
    this.hasStoneType = true;
    return this;
  }

  public RockBlockVariant build(RegistryManager registry) {
    RockType.getTypes().forEach(type -> {
      if (map.put(type, factory.apply(this, type)) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      if (hasStoneType) {
        createStoneType(idCounter.getAndIncrement(), type);
      }
    });
    registry.blocks(map.values());
    return this;
  }

  private void createStoneType(int id, RockType type) {
    stoneType = new StoneType(id, type + "_" + this.getName(), SoundType.STONE, type.getOrePrefix(), type.getMaterial(),
            () -> this.get(type).getDefaultState(), state -> state.getBlock() == this.get(type), false
    );
  }

  public Block get(RockType type) {
    var block = this.map.get(type);
    if (block != null) {
      return block;
    }
    throw new RuntimeException(String.format("Block rock is null: %s, %s", this, type));
  }

  public boolean canFall() {
    return specification != null;
  }

  public float getHardness(RockType type) {
    return this.getBaseHardness() + type.getCategory().getHardnessModifier();
  }

  public String getRegistryKey(RockType type) {
    return String.format("rock/%s/%s", this.getName(), type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(
            String.format("rock.variant.%s.name", this)).getFormattedText();
  }
}
