package su.terrafirmagreg.modules.plant.api.types.type;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

import java.util.Set;

@Getter
public class PlantType implements Comparable<PlantType> {

  @Getter
  private static final Set<PlantType> types = new ObjectOpenHashSet<>();

  private final String name;

  private PlantType(Builder builder) {
    this.name = builder.name;

    if (name.isEmpty()) {
      throw new RuntimeException(
          String.format("Plant name must contain any character: [%s]", name));
    }

    if (!types.add(this)) {
      throw new RuntimeException(String.format("Plant: [%s] already exists!", name));
    }
  }

  @Override
  public int compareTo(@NotNull PlantType type) {
    return this.name.compareTo(type.getName());
  }

  public static class Builder {

    private final String name;

    public Builder(@NotNull String name) {
      this.name = name;
    }
  }
}
