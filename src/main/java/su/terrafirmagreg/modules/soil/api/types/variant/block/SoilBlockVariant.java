package su.terrafirmagreg.modules.soil.api.types.variant.block;

import su.terrafirmagreg.data.lib.types.variant.block.VariantBlock;
import su.terrafirmagreg.modules.soil.ModuleSoil;
import su.terrafirmagreg.modules.soil.api.spi.IGrass;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

import static su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager.Specification;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.COARSE_DIRT;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DIRT;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.DRY_GRASS;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.FARMLAND;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.GRASS;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.GRASS_PATH;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.MYCELIUM;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.PODZOL;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.ROOTED_DIRT;
import static su.terrafirmagreg.modules.soil.init.BlocksSoil.SPARSE_GRASS;

@Getter
public class SoilBlockVariant extends VariantBlock<SoilBlockVariant, SoilType> {

  @Getter
  private static final Set<SoilBlockVariant> variants = new ObjectOpenHashSet<>();

  private final Specification specification;

  private SoilBlockVariant(Builder builder) {
    super(builder.name);

    this.specification = builder.specification;

    if (!variants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", getName()));
    }

    SoilType.getTypes().forEach(type -> {
      var block = builder.factory.apply(this, type);
      if (map.put(type, block.getBlock()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleSoil.REGISTRY.block(block);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  @Override
  public String getRegistryKey(SoilType type) {
    return String.format("soil/%s/%s", this.getName(), type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("soil.variant.%s.name", this)).getFormattedText();
  }

  /**
   * Проверяет, может ли блок почвы падать.
   *
   * @return true, если блок почвы может падать, иначе false.
   */
  public boolean canFall() {
    return specification != null;
  }

  /**
   * Возвращает вариант блока почвы без травы.
   *
   * @return Вариант блока почвы без травы.
   */
  public SoilBlockVariant getNonGrassVersion() {
    if (this == GRASS ||
        this == DRY_GRASS ||
        this == SPARSE_GRASS ||
        this == PODZOL ||
        this == MYCELIUM) {
      return DIRT;
    }

    throw new IllegalStateException("Someone forgot to add enum constants to this switch case...");
  }

  /**
   * Возвращает вариант блока почвы с травой.
   *
   * @param spreader Вариант блока почвы, который распространяет траву.
   * @return Вариант блока почвы с травой.
   */
  public SoilBlockVariant getGrassVersion(SoilBlockVariant spreader) {
    if (this instanceof IGrass) {
      return this;
    }
    if (this == DIRT) {
      return spreader == DRY_GRASS ? DRY_GRASS : GRASS;
    }
    if (this == ROOTED_DIRT || this == COARSE_DIRT) {
      return spreader == SPARSE_GRASS ? SPARSE_GRASS : DRY_GRASS;
    }

    throw new IllegalArgumentException(
      String.format("You cannot get grass from [%s] types.", this)
    );
  }

  private SoilBlockVariant transform() {
    if (this == DIRT) {
      return GRASS;
    } else if (this == COARSE_DIRT) {
      return SPARSE_GRASS;
    } else if (this == GRASS ||
               this == DRY_GRASS ||
               this == SPARSE_GRASS ||
               this == PODZOL ||
               this == MYCELIUM ||
               this == FARMLAND ||
               this == GRASS_PATH ||
               this == ROOTED_DIRT) {
      return DIRT;
    } else {
      throw new IllegalArgumentException(
        String.format("You cannot get grass from [%s] types.", this)
      );
    }
  }

  public static class Builder {

    private final String name;

    private BiFunction<SoilBlockVariant, SoilType, ISoilBlock> factory;
    private Specification specification;

    public Builder(String name) {
      this.name = name;
    }

    public Builder factory(BiFunction<SoilBlockVariant, SoilType, ISoilBlock> factory) {
      this.factory = factory;
      return this;
    }

    public Builder fallingSpecification(Specification specification) {
      this.specification = specification;
      return this;
    }


    public SoilBlockVariant build() {
      return new SoilBlockVariant(this);
    }
  }

}
