package su.terrafirmagreg.modules.rock.api.types.variant.item;

import su.terrafirmagreg.api.library.types.variant.item.VariantItem;
import su.terrafirmagreg.modules.rock.ModuleRock;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;

import net.minecraft.util.text.TextComponentTranslation;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import lombok.Getter;

import java.util.Set;
import java.util.function.BiFunction;

@Getter
public class RockItemVariant extends VariantItem<RockItemVariant, RockType> {

  @Getter
  private static final Set<RockItemVariant> itemVariants = new ObjectOpenHashSet<>();

  private RockItemVariant(Builder builder) {
    super(builder.name);

    if (!itemVariants.add(this)) {
      throw new RuntimeException(String.format("Variant: [%s] already exists!", name));
    }

    RockType.getTypes().forEach(type -> {
      var item = builder.factory.apply(this, type);
      if (map.put(type, item.getItem()) != null) {
        throw new RuntimeException(String.format("Duplicate registry detected: %s, %s", this, type));
      }
      ModuleRock.REGISTRY.item(item);
    });
  }

  public static Builder builder(String name) {
    return new Builder(name);
  }

  public String getRegistryKey(RockType type) {
    return String.format("rock/%s/%s", this, type);
  }

  public String getLocalizedName() {
    return new TextComponentTranslation(String.format("rock.variant.%s.name", this)).getFormattedText();
  }

  public static class Builder {

    private final String name;

    private BiFunction<RockItemVariant, RockType, IRockItem> factory;

    public Builder(String name) {
      this.name = name;
    }


    public Builder setFactory(BiFunction<RockItemVariant, RockType, IRockItem> factory) {
      this.factory = factory;
      return this;
    }

    public RockItemVariant build() {
      return new RockItemVariant(this);
    }
  }
}
