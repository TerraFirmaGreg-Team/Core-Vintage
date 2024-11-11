package su.terrafirmagreg.modules.rock.api.types.type;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.modules.rock.api.types.category.RockCategory;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Set;


@Getter
public class RockType extends Type<RockType> {

  @Getter
  private static final Set<RockType> types = new ObjectOpenHashSet<>();

  private final RockCategory category;
  private final OrePrefix orePrefix;
  private final Material material;
  private final boolean isFlux;

  private RockType(Builder builder) {
    super(builder.name);

    this.category = builder.category;
    this.orePrefix = builder.orePrefix;
    this.material = builder.material;
    this.isFlux = builder.isFlux;

    if (!types.add(this)) {
      throw new RuntimeException(String.format("Type: [%s] already exists!", name));
    }
  }

  @Nullable
  public static RockType getByName(@NotNull String name) {
    return types
      .stream()
      .filter(s -> s.getName().equals(name))
      .findFirst()
      .orElse(null);
  }

  /**
   * Возвращает экземпляр породы по индексу.
   *
   * @param i Индекс породы.
   * @return Экземпляр породы.
   */
  public static RockType valueOf(int i) {
    var values = new RockType[types.size()];
    values = types.toArray(values);

    return i >= 0 && i < values.length ? values[i] : values[i % values.length];
  }

  /**
   * Возвращает индекс породы в списке.
   *
   * @param type Порода.
   * @return Индекс породы.
   */
  public static int indexOf(RockType type) {
    return new ArrayList<>(types).indexOf(type);
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public ResourceLocation getTexture(Variant<?, RockType> variant) {
    return ModUtils.resource(String.format("textures/blocks/rock/%s/%s.png", variant, this));
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("rock.type.%s.name", this)).getFormattedText();
  }

  public String getRegistryKey(Variant<?, RockType> variant) {
    return String.format("rock/%s/%s", variant, this);
  }

  public static class Builder {

    private final String name;
    private RockCategory category;
    private OrePrefix orePrefix;
    private Material material;
    private boolean isFlux;

    /**
     * Создает экземпляр Builder с указанным именем.
     *
     * @param name Имя породы.
     */
    public Builder(@NotNull String name) {
      this.name = name;

      this.isFlux = false;
    }

    /**
     * Устанавливает орпрефикс для типа породы.
     *
     * @param orePrefix Орпрефикс для типа породы.
     * @return Builder.
     */
    public Builder orePrefix(@NotNull OrePrefix orePrefix) {
      this.orePrefix = orePrefix;
      return this;
    }

    /**
     * Устанавливает материал для типа породы.
     *
     * @param material Материал для типа породы.
     * @return Builder.
     */
    public Builder material(@NotNull Material material) {
      this.material = material;
      return this;
    }

    /**
     * Устанавливает категорию породы.
     *
     * @param rockCategory Категория породы.
     * @return Builder.
     */
    public Builder rockCategory(@NotNull RockCategory rockCategory) {
      this.category = rockCategory;
      return this;
    }

    /**
     * Устанавливает флаг, указывающий, является ли порода флюсом.
     *
     * @return Builder.
     */
    public Builder isFlux() {
      this.isFlux = true;
      return this;
    }

    /**
     * Создает экземпляр RockType на основе Builder.
     *
     * @return Экземпляр RockType.
     */
    public RockType build() {
      return new RockType(this);
    }
  }
}
