package su.terrafirmagreg.modules.soil.api.types.type;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.lib.types.type.Type;
import su.terrafirmagreg.data.lib.types.variant.Variant;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Set;

@Getter
public class SoilType extends Type<SoilType> {

  @Getter
  private static final Set<SoilType> types = new ObjectOpenHashSet<>();

  private SoilType(Builder builder) {
    super(builder.name);

    if (!types.add(this)) {
      throw new RuntimeException(String.format("Type: [%s] already exists!", name));
    }

  }

  /**
   * Возвращает тип почвы по индексу.
   *
   * @param i Индекс типа почвы.
   * @return Тип почвы.
   */
  @NotNull
  public static SoilType valueOf(int i) {
    var values = new SoilType[types.size()];
    values = types.toArray(values);

    return i >= 0 && i < values.length ? values[i] : values[i % values.length];
  }

  /**
   * Возвращает индекс породы в списке.
   *
   * @param type Порода.
   * @return Индекс породы.
   */
  public static int indexOf(SoilType type) {
    return new ArrayList<>(types).indexOf(type);
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }


  public ResourceLocation getTexture(Variant<?, SoilType> variant) {
    return ModUtils.resource(String.format("textures/blocks/soil/%s/%s.png", variant, this));
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("soil.type.%s.name", this)).getFormattedText();
  }

  public String getRegistryKey(Variant<?, SoilType> variant) {
    return String.format("soil/%s/%s", variant, this);
  }

  public static class Builder {

    private final String name;

    /**
     * Создает экземпляр Builder с указанным именем.
     *
     * @param name Имя породы.
     */
    public Builder(@NotNull String name) {
      this.name = name;
    }

    public SoilType build() {
      return new SoilType(this);
    }
  }
}
