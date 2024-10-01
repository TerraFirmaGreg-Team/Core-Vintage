package su.terrafirmagreg.modules.rock.api.types.variant.block;

import su.terrafirmagreg.data.lib.types.variant.block.VariantBlock;
import su.terrafirmagreg.modules.rock.ModuleRock;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.block.SoundType;
import net.minecraft.util.text.TextComponentTranslation;

import gregtech.api.unification.ore.StoneType;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification;

@Getter
public class RockBlockVariant extends VariantBlock<RockBlockVariant, RockType> {

  @Getter
  private static final Set<RockBlockVariant> blockVariants = new ObjectOpenHashSet<>();
  private static final AtomicInteger idCounter = new AtomicInteger(16);

  private final float baseHardness;
  private final Specification specification;
  private final boolean hasStoneType;
  private StoneType stoneType;

  private RockBlockVariant(Builder builder) {
    super(builder.name);

    this.baseHardness = builder.baseHardness;
    this.specification = builder.specification;
    this.hasStoneType = builder.hasStoneType;

    if (!blockVariants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    RockType.getTypes().forEach(type -> {
      var block = builder.factory.apply(this, type);
      if (map.put(type, block.getBlock()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      if (hasStoneType) {
        createStoneType(type);
      }
      ModuleRock.REGISTRY.block(block);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  private void createStoneType(RockType type) {
    this.stoneType = new StoneType(
      idCounter.getAndIncrement(), type + "_" + this.getName(),
      SoundType.STONE,
      type.getOrePrefix(), type.getMaterial(),
      () -> this.get(type).getDefaultState(),
      state -> state.getBlock() == this.get(type), false
    );
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
    return new TextComponentTranslation(String.format("rock.variant.%s.name", this)).getFormattedText();
  }

  public static class Builder {

    private final String name;

    private float baseHardness;
    private Specification specification;
    private BiFunction<RockBlockVariant, RockType, IRockBlock> factory;
    private boolean hasStoneType;

    public Builder(String name) {
      this.name = name;
    }

    public Builder baseHardness(float baseHardness) {
      this.baseHardness = baseHardness;
      return this;
    }

    public Builder factory(BiFunction<RockBlockVariant, RockType, IRockBlock> factory) {
      this.factory = factory;
      return this;
    }

    public Builder fallingSpecification(Specification specification) {
      this.specification = specification;
      return this;
    }

    public Builder isStoneType() {
      this.hasStoneType = true;
      return this;
    }

    public RockBlockVariant build() {
      return new RockBlockVariant(this);
    }

  }
}
