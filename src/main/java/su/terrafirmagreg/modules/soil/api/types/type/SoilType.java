package su.terrafirmagreg.modules.soil.api.types.type;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.data.lib.types.type.Type;

import net.minecraft.util.ResourceLocation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Set;

@Getter
public class SoilType extends Type<SoilType> {

  @Getter
  private static final Set<SoilType> types = new ObjectOpenHashSet<>();

  private final String name;

  private SoilType(Builder builder) {
    super(builder.name);
    this.name = builder.name;

    if (!types.add(this)) {
      throw new RuntimeException(String.format("SoilType: [%s] already exists!", name));
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

  /**
   * Возвращает ресурсное расположение текстуры породы.
   *
   * @return Ресурсное расположение текстуры породы.
   */
  @NotNull
  public ResourceLocation getTexture() {
    return ModUtils.resource("textures/blocks/soil/mud/" + this + ".png");
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
